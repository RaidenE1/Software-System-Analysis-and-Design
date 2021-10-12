pipeline {
    agent any

    tools {
        maven 'mvn'
    }
    stages {
        stage('Build') {
            steps {
                sh "cd demo"
                sh "mvn clean package"
                sh "printenv"
            }
        }
    }
}