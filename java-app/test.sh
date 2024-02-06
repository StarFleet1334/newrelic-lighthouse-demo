#!/bin/bash

# Install Node.js and npm if not installed
if ! command -v node > /dev/null; then
    echo "Node.js is not installed. Installing Node.js and npm..."
    curl -sL https://deb.nodesource.com/setup_14.x | sudo bash -
    sudo apt-get install -y nodejs
fi

# Install Lighthouse
echo "Installing Lighthouse..."
sudo npm install -g lighthouse

# Source the .env file to load the environment variables
source .env

# Run Lighthouse audit and save the report in JSON format at the root of the java-app folder
echo "Running Lighthouse..."
/usr/local/bin/lighthouse $LIGHTHOUSE_URL --output=json --output-path=./lighthouse_report.json

# Check if Maven is installed, install if not
if ! command -v mvn > /dev/null; then
    echo "Maven is not installed. Installing Maven..."
    sudo apt-get install -y maven
fi

# Package the application using Maven
echo "Packaging the application..."
mvn package

# Move the Lighthouse report to the target directory
echo "Moving the Lighthouse report..."
mv ./lighthouse_report.json ./target/lighthouse_report.json

# Go to the target directory
cd ./target

# To run the app with instrumentation:
echo "Running the application with New Relic instrumentation..."
java -javaagent:./newrelic/newrelic.jar -jar newrelic-lighthouse-1.0-SNAPSHOT.jar
