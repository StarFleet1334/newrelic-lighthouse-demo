FROM jenkins/jenkins:lts

# Set environment variables
ENV JENKINS_HOME /var/jenkins_home
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false

# Copy plugins.txt
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
COPY security.groovy /usr/share/jenkins/ref/init.groovy.d/security.groovy

USER root

# Use jenkins-plugin-cli to install plugins
#RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt
USER root
RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt && \
apt-get -y update && \
apt-get -y install sudo && \
apt-get -y install jq && \
sudo usermod -aG staff jenkins

# Install Node.js and NPM
RUN curl -sL https://deb.nodesource.com/setup_16.x | bash - && \
    apt-get -y install nodejs

# Expose the default port for Jenkins
EXPOSE 8080

# Expose the port for attached slave agents.
EXPOSE 50000
