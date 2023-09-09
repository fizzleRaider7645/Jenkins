pipeline {
    agent any
    environment {
        NPM_CONFIG_CACHE = "${WORKSPACE}/.npm"
    }
    stages {
        stage('Build') {
            steps {
                script {
                    sh 'yarn --frozen-lockfile'
                    sh 'node --version'
                    sh 'ls -la'
                    sh 'yarn run build'
                }
            }
        }
    }
}
