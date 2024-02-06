FROM jenkins/jenkins:lts

# Set environment variables
ENV JENKINS_HOME /var/jenkins_home
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false

# Copy plugins.txt
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
COPY security.groovy /usr/share/jenkins/ref/init.groovy.d/security.groovy


# Use jenkins-plugin-cli to install plugins
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt
RUN apt-get update && apt-get install -y sudo nodejs npm maven

# Expose the default port for Jenkins
EXPOSE 8080

# Expose the port for attached slave agents.
EXPOSE 50000
