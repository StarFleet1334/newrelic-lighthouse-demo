FROM jenkins/jenkins:lts

USER root


# Set environment variables
ENV JENKINS_HOME /var/jenkins_home
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false
ENV CASC_JENKINS_CONFIG /var/jenkins_home/casc_configs

# Copy necessary files
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
COPY security.groovy /usr/share/jenkins/ref/init.groovy.d/security.groovy
COPY create-jobs.groovy /usr/share/jenkins/ref/init.groovy.d/create-jobs.groovy
COPY jenkins.yaml /var/jenkins_home/casc_configs/jenkins.yaml
COPY job-dsl-scripts /var/jenkins_home/job-dsl-scripts


# Use jenkins-plugin-cli to install plugins
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt
RUN apt-get update && apt-get install -y bash


# Expose the default port for Jenkins
EXPOSE 8080

# Expose the port for attached slave agents.
EXPOSE 50000
