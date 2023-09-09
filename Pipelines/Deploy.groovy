pipeline {
    agent {
        label any
    }
    parameters {
        string(name: 'GIT_SSH_URL', defaultValue: 'git@github.com:user/repo.git', description: 'The SSH URL of the Git repository')
        string(name: 'BRANCH_NAME', defaultValue: 'main', description: 'The branch name to checkout')
    }
    environment {
        NPM_CONFIG_CACHE = "${WORKSPACE}/.npm"
        AWS_ACCESS_KEY_ID = credentials('my-aws-access-key-id')
        AWS_SECRET_ACCESS_KEY = credentials('my-aws-secret-access-key')
    }
    stages {
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: "${params.BRANCH_NAME}"]],
                    userRemoteConfigs: [[url: "${params.GIT_SSH_URL}", credentialsId: 'GitHub_SSH_Key_for_Jenkins']]
                ])
            }
        }
        stage('Build/Deploy') {
            steps {
                script {
                    docker.image('node:lts').inside('-u 0:0') {
                        sh '''
                          apt-get update && apt-get install -y awscli
                          yarn --frozen-lockfile
                          node --version
                          ls -la
                          yarn run build
                          ls -la ${WORKSPACE}/build
                          aws s3 cp ${WORKSPACE}/build s3://avocado-blue/ --recursive
                        '''
                    }
                }
            }
        }
    }
}
