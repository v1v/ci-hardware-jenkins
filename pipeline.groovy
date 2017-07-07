// Default pipeline
//   build - deploy
//
// Optional phases in the pipeline
//   build - unit - top - ver - deploy
//
// Those phases are managed by the folder branch name
//
// For intance:
//  branch name => ver/feature23456
//    Pipeline : build - unit - top - ver - deploy
//
//  branch name => unit/feature23456
//    Pipeline : build - unit - deploy
//

def gitProject = 'ci-hardware-project'

folder(gitProject) {
  displayName(gitProject)
  description("Folder for project ${gitProject}")
}

def repo = 'v1v/' + gitProject
def branchApi = new URL("https://api.github.com/repos/${repo}/branches")
def branches = new groovy.json.JsonSlurper().parse(branchApi.newReader())
branches.each {
  def branchName = it.name
  def minBranchName = branchName.replaceAll('.*/','')

  // Dynamic pipeline creation
  switch (branchName) {
    case ~/^unit\/.*/:
      unit = true
      break
    case ~/^top\/.*/:
      unit = true
      top  = true
      break
    case ~/^ver\/.*/:
      unit = true
      top  = true
      ver  = true
      break
    default:
      unit = false
      top  = false
      ver  = false
      break
  }

  // Subfolder creation
  def project = "${gitProject}/${minBranchName}"
  folder(project)

  // Jobs/stages creation
  if (ver) {
    jobName = "${project}/${minBranchName}-ver"
    job(jobName) {
      publishers {
        downstream("${project}/${minBranchName}-deploy", 'UNSTABLE')
      }
    }
  }

  if (top) {
    jobName = "${project}/${minBranchName}-top"
    job(jobName) {
      publishers {
        if (ver) {
          downstream("${project}/${minBranchName}-ver", 'UNSTABLE')
        } else {
          downstream("${project}/${minBranchName}-deploy", 'UNSTABLE')
        }
      }
    }
  }

  if (unit) {
    jobName = "${project}/${minBranchName}-unit"
    job(jobName) {
      publishers {
        if (top) {
          downstream("${project}/${minBranchName}-top", 'UNSTABLE')
        } else {
          downstream("${project}/${minBranchName}-deploy", 'UNSTABLE')
        }
      }
    }
  }

  jobName = "${project}/${minBranchName}"

  job("${jobName}-deploy")
  job(jobName) {
    publishers {
      if (unit) {
        downstream("${project}/${minBranchName}-unit", 'UNSTABLE')
      } else {
        downstream("${project}/${minBranchName}-deploy", 'UNSTABLE')
      }
    }
  }


  // View section
  nestedView(jobName) {
    views {
        listView('overview') {
            jobs {
              regex(/${minBranchName}.*/)
            }
            columns {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
            }
        }
        buildPipelineView('pipeline') {
          selectedJob(minBranchName)
        }
    }
  }
}
