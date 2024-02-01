# Use an official Jenkins LTS (Long Term Support) image as the base image
FROM jenkins/jenkins:lts

# Set environment variables
ENV JENKINS_HOME /var/jenkins_home

# Disable setup wizard during Jenkins setup
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false

# Expose the default port for Jenkins
EXPOSE 8080

# Expose the port for attached slave agents.
EXPOSE 50000
