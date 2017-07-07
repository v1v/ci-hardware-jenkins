# 1. Build your Jenkins image

For details about the use cases demonstrated in this project, please read the wiki entry first.

```bash
docker build --rm=true -t ci-hardware-jenkins .
```

# 2. Run your Jenkins containers locally
Once you have build your local image then run the container locally

```bash
docker run --name ci-hardware-jenkins -p 8080:8080 -v `pwd`/fs:/var/jenkins_home ci-hardware-jenkins
```

# 3. Run the seed job

[Tutorial - Using the Jenkins Job DSL - section 3](https://github.com/jenkinsci/job-dsl-plugin/wiki/Tutorial---Using-the-Jenkins-Job-DSL#3-run-the-seed-job-and-generate-the-new-jobs-from-the-script).

# Requirements
- [Docker](https://docs.docker.com/installation/)

# Further reading
- https://registry.hub.docker.com/_/jenkins/
- https://github.com/jenkinsci/job-dsl-plugin/wiki
