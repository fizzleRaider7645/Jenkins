pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                docker.image('node:lts').inside {
                    sh 'npm install'
                    sh 'node --version'
                    sh 'ls -la'
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