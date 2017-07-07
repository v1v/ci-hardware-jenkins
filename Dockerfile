FROM jenkins:2.46.3-alpine

### configure a list of plugins
COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/plugins.sh /usr/share/jenkins/plugins.txt

### Disable jenkins2 wizard
RUN echo 2.46.3-alpine > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state
RUN echo 2.46.3-alpine > /usr/share/jenkins/ref/jenkins.install.InstallUtil.lastExecVersion

### Prerequisite to configure the Jenkins instance
COPY init.groovy.d/seed.groovy /usr/share/jenkins/ref/init.groovy.d/seed.groovy
