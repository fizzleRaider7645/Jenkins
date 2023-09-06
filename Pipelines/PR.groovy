pipeline {
    agent {
        label 'build-agent-1'
    }
    environment {
        NPM_CONFIG_CACHE = "${WORKSPACE}/.npm"
    }
    stages {
        stage('Build') {
            steps {
                script {
                    docker.image('node:lts').inside {
                        sh 'yarn --frozen-lockfile --verbose'
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