#!/bin/bash


# Set up Lighthouse
#npm install  lighthouse

# if permission denied, use sudo

# Source the .env file to load the environment variables
#source .env

# Run Lighthouse audit and save the report in JSON format at the root of the java-app folder
#/usr/local/bin/lighthouse $LIGHTHOUSE_URL --output=json --output-path=./lighthouse_report.json

#echo "Running Lighthouse..."
#npx lighthouse $LIGHTHOUSE_URL --output=json --output-path=./lighthouse_report.json --chrome-flags="--no-sandbox --headless"



## Move the Lighthouse report to the target directory
echo "Packaging the application..."
mvn package

sleep 5
echo "Moving the Lighthouse report..."
mv ./lighthouse_report.json ./target/lighthouse_report.json

sleep 5
# Go to the target directory
echo "Moving to target folder"
cd ./target

sleep 5
# To run the app with instrumentation:
echo "Running the application with New Relic instrumentation..."

sleep 5
java -javaagent:./newrelic/newrelic.jar -jar newrelic-lighthouse-1.0-SNAPSHOT.jar
