pipeline {
    agent any

    tools {
        maven 'mvn'
    }
    stages {
        stage('Build') {
            steps {
                sh "mvn clean package"
                sh "printenv"
            }
        }
    }
}