pipeline {
    agent any
    environment {
        NPM_CONFIG_CACHE = "${WORKSPACE}/.npm"
    }
    stages {
        stage('Build') {
            steps {
                docker.image('node:lts') {
                    sh '''
                        yarn --frozen-lockfile
                        node --version
                        ls -la
                        yarn run build
                        ls -la ${WORKSPACE}/build
                    '''
                }
            }
        }
    }
}
