pipeline {
    agent {
        docker { 
            image 'node:lts' 
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'node --version'
                sh 'pwd'
                echo 'Building...'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
            }
        }
    }
}