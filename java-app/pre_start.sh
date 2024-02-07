#!/bin/bash

# Exit script on any error
set -e

# Check if Node.js and npm are installed, install them if they are not
if ! command -v node > /dev/null 2>&1; then
  echo "Node.js is not installed. Attempting to install Node.js."
  # Install Node.js (You might want to specify a version or use nvm for version management)
  curl -sL https://deb.nodesource.com/setup_14.x | sudo -E bash -
  sudo apt-get install -y nodejs
fi

# Check if Docker is installed, install it if it is not
if ! command -v docker > /dev/null 2>&1; then
  echo "Docker is not installed. Attempting to install Docker."
  # Install Docker
  sudo apt-get update
  sudo apt-get install -y apt-transport-https ca-certificates curl software-properties-common
  curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
  sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
  sudo apt-get update
  sudo apt-get install -y docker-ce docker-ce-cli containerd.io
fi

# Check if Docker Compose is installed, install it if it is not
if ! command -v docker-compose > /dev/null 2>&1; then
  echo "Docker Compose is not installed. Attempting to install Docker Compose."
  # Install Docker Compose
  sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
  sudo chmod +x /usr/local/bin/docker-compose
fi

# Remove node_modules if it exists
rm -rf node_modules

# Uncomment this line if you want to remove package-lock.json
#rm package-lock.json

# Install Lighthouse
npm install lighthouse

# Source environment variables
if [ -f .env ]; then
  source .env
else
  echo ".env file not found, ensure your environment variables are set"
  exit 1
fi

# Run Lighthouse
npx lighthouse $LIGHTHOUSE_URL --output=json --output-path=./lighthouse_report.json

# Build and run Docker containers without cache
docker-compose build --no-cache
docker-compose up --build
