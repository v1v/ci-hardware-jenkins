FROM jenkins:1.651.3-alpine

### configure a list of plugis
COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/plugins.sh /usr/share/jenkins/plugins.txt
