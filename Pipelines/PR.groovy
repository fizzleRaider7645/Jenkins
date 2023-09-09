pipeline {
    agent any
    environment {
        NPM_CONFIG_CACHE = "${WORKSPACE}/.npm"
    }
    stages {
        stage('Build') {
            steps {
                script {
                    docker.image('douglasuretsky/pipeline-images:pr-env-image-1.0.0').inside('-u 0:0') {
                        sh '''
                            yarn --frozen-lockfile
                            node --version
                            ls -la
                            yarn run build
                        '''
                    }
                }
            }
        }
    }
     post {
        always {
            script {
                sh '''
                    docker rm $(docker ps -a -q) -f
                '''
            }
        }
    }
}
