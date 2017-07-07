import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.plugin.JenkinsJobManagement

// SEED job generated automatically
def jobDslScript = '''
  job('seed') {
    description('Created automatically from https://github.com/v1v/ci-hardware-jenkins')
    scm {
      github('v1v/ci-hardware-jenkins', 'master')
    }
    properties {
      githubProjectUrl('https://github.com/v1v/ci-hardware-jenkins')
    }
    steps {
      dsl(['pipeline.groovy'], 'DELETE')
    }
  }
'''
def workspace = new File('.')

def jobManagement = new JenkinsJobManagement(System.out, [:], workspace)
new DslScriptLoader(jobManagement).runScript(jobDslScript)
