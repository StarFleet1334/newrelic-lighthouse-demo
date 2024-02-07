#!/bin/bash

# if you have node modules remove them
rm -rf node_modules

# if you have package-lock.json remote them
#rm package-lock.json

# Set up Lighthouse
npm install  lighthouse

source .env

npx lighthouse $LIGHTHOUSE_URL --output=json --output-path=./lighthouse_report.json --chrome-flags="--no-sandbox --headless --disable-gpu --disable-dev-shm-usage --disable-software-rasterizer --disable-extensions"


docker-compose build --no-cache

docker-compose up --build