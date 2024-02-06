#!/bin/bash


# Set up Lighthouse
npm install -g lighthouse
# if permission denied, use sudo

# Source the .env file to load the environment variables
source .env

# Run Lighthouse audit and save the report in JSON format at the root of the java-app folder
/usr/local/bin/lighthouse $LIGHTHOUSE_URL --output=json --output-path=./lighthouse_report.json

## Move the Lighthouse report to the target directory

mvn package

mv ./lighthouse_report.json ./target/lighthouse_report.json

# Go to the target directory
cd ./target

# To run the app with instrumentation:
java -javaagent:./newrelic/newrelic.jar -jar newrelic-lighthouse-1.0-SNAPSHOT.jar
