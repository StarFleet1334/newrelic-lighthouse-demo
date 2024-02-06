#!/bin/bash


# Set up Lighthouse
npm install -g lighthouse

# if permission denied, use sudo

# Source the .env file to load the environment variables
source .env

# Run Lighthouse audit and save the report in JSON format at the root of the java-app folder
#/usr/local/bin/lighthouse $LIGHTHOUSE_URL --output=json --output-path=./lighthouse_report.json

echo "Running Lighthouse..."
npx lighthouse $LIGHTHOUSE_URL --output=json --output-path=./lighthouse_report.json


# Check if Maven is installed, install if not
if ! command -v mvn > /dev/null; then
    echo "Maven is not installed. Installing Maven..."
    # Add your Maven installation command here. For example, for Ubuntu it might be:
    apt-get install -y maven
fi


## Move the Lighthouse report to the target directory
echo "Packaging the application..."
mvn package

echo "Moving the Lighthouse report..."
mv ./lighthouse_report.json ./target/lighthouse_report.json

# Go to the target directory
cd ./target

# To run the app with instrumentation:
echo "Running the application with New Relic instrumentation..."

java -javaagent:./newrelic/newrelic.jar -jar newrelic-lighthouse-1.0-SNAPSHOT.jar
