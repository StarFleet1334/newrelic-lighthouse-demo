pipeline {
    agent any
    stages {
        stage('Checkout and Build') {
            steps {
                script {
                    sh '''
                        cd /var/jenkins_home
                        git clone https://github.com/StarFleet1334/newrelic-lighthouse-demo.git
                        cd newrelic-lighthouse-demo/java-app
                        sh pre_start.sh
                    '''
                }
            }
        }
    }
}
