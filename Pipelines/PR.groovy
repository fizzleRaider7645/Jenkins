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
                        sh 'echo ${WORKSPACE}'
                    } 
                }
            }
        }
        stage('Deploy') {
            steps {
                sh 'aws s3 cp $WORKSPACE/build s3://avocado-blue/'
            }
        }
    }
}