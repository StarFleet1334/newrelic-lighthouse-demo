#!/bin/bash

# Name of your docker image
IMAGE_NAME="my-jenkins-image"

# Name of your docker container
CONTAINER_NAME="my-jenkins-container"

# Port on your machine that will forward to Jenkins
HOST_PORT=8080

# Jenkins username and token/password
JENKINS_USER="admin"
JENKINS_TOKEN="11a3f2e7bd33026be44af11250728aa85a"  # Use an actual API token in a real scenario

# Build the Docker image
docker build -t $IMAGE_NAME .

# Run the Jenkins container
docker run -d --name $CONTAINER_NAME -p $HOST_PORT:8080 -p 50000:50000 $IMAGE_NAME

echo "Jenkins should be available at http://localhost:$HOST_PORT"

# Wait for Jenkins to fully start
sleep 20  # Adjust this based on your Jenkins setup

# Path to your beta.xml file
CONFIG_XML_PATH="./jobs/beta.xml"

# Jenkins URL
JENKINS_URL="http://localhost:$HOST_PORT"

# Job Name
JOB_NAME="random-job"

# Create the Jenkins job
curl -X POST -H "Content-Type: application/xml" --data-binary "@$CONFIG_XML_PATH" "$JENKINS_URL/createItem?name=$JOB_NAME" --user $JENKINS_USER:$JENKINS_TOKEN

echo "Job $JOB_NAME should be created"

# Trigger the job
curl -X POST "$JENKINS_URL/job/$JOB_NAME/build" \
     --user $JENKINS_USER:$JENKINS_TOKEN

echo "Job $JOB_NAME should be triggered"
