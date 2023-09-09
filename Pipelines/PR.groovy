pipeline {
    agent {
        label 'build-agent-2'
    }
    environment {
        NPM_CONFIG_CACHE = "${WORKSPACE}/.npm"
        AWS_ACCESS_KEY_ID = credentials('my-aws-access-key-id')
        AWS_SECRET_ACCESS_KEY = credentials('my-aws-secret-access-key')
    }
    stages {
        stage('Build') {
            steps {
                script {
                    docker.image('node:lts').inside {
                        sh 'apt-get update && apt-get install -y awscli'
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