pipeline {
    agent any
    environment {
        NPM_CONFIG_CACHE = "${WORKSPACE}/.npm"
    }
    stages {
        stage('Build') {
            steps {
                script {
                    docker.image('node:lts').inside {
                        sh 'yarn install --verbose'
                        sh 'node --version'
                        sh 'ls -la'
                    } 
                }
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
            }
        }
    }
}