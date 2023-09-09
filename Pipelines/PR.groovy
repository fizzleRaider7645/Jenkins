pipeline {
    agent {
        label 'build-agent-2'
    }
    environment {
        NPM_CONFIG_CACHE = "${WORKSPACE}/.npm"
    }
    stages {
        stage('Build') {
            steps {
                script {
                    docker.image('node:lts').inside {
                        sh 'yarn --frozen-lockfile'
                        sh 'node --version'
                        sh 'ls -la'
                        sh 'yarn run build'
                        sh "echo ${WORKSPACE}"
'
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