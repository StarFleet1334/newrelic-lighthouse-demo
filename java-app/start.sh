#!/bin/bash

# Set up Lighthouse
sudo npm install -g lighthouse
# if permission denied, use sudo

# Source the .env file to load the environment variables
source .env

# Run Lighthouse audit and save the report in JSON format
/usr/local/bin/lighthouse $LIGHTHOUSE_URL --output=json --output-path=./lighthouse_report.json
# if everything good instead of full path use prometheus

#mvn clean package
# shellcheck disable=SC2164

cd ./target

# To run the app with no instrumentation:
# java -jar app-1.0-SNAPSHOT.jar

# To run the app with instrumentation:
 java -javaagent:./newrelic/newrelic.jar -jar test_integration-1.0-SNAPSHOT.jar


