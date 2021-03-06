FROM jenkins/jenkins:alpine

ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false -Djenkins.CLI.disabled=true"

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

USER root
RUN apk update && \
    apk add docker sudo
RUN echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers
USER jenkins

ADD groovy_scripts /usr/share/jenkins/ref/init.groovy.d

EXPOSE 8080
EXPOSE 50000
