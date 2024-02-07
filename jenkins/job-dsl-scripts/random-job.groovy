pipeline {
    agent any
    environment {
        // Define your environment variables here, if they're static
        // For sensitive data, use Jenkins credentials and 'withCredentials'
        LIGHTHOUSE_URL = 'https://www.youtube.com/'
    }
    stages {
        stage('Install Node.js') {
            steps {
                script {
                    // Install Node.js for this job run
                    sh '''
                        curl -sL https://deb.nodesource.com/setup_20.x | bash -
                        apt-get install -y nodejs
                    '''
                }
            }
        }
        stage('Install Chromium') {
            steps {
                script {
                    // Install Chromium before running the tests
                    sh 'apt-get update && apt-get install -y chromium'
                }
            }
        }
        stage('Checkout and Build') {
            steps {
                script {
                    sh '''
                        export CHROME_PATH=$(which chromium)
                        cd /var/jenkins_home
                        git clone https://github.com/StarFleet1334/newrelic-lighthouse-demo.git
                        cd newrelic-lighthouse-demo/java-app
                        bash pre_start.sh
                    '''
                }
            }
        }
    }
}
