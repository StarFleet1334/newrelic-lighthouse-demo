#!/bin/bash

# Name of your docker image
IMAGE_NAME="my-jenkins-image"

# Name of your docker container
CONTAINER_NAME="my-jenkins-container"

# Port on your machine that will forward to Jenkins
HOST_PORT=8080

# Build the Docker image
docker build -t $IMAGE_NAME .

# Stop the container if it's already running and remove it
docker stop $CONTAINER_NAME || true
docker rm $CONTAINER_NAME || true

# Run the Jenkins container
docker run -d --name $CONTAINER_NAME -p $HOST_PORT:8080 -p 50000:50000 $IMAGE_NAME

echo "Jenkins should be available at http://localhost:$HOST_PORT"
