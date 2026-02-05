pipeline {
    agent any 

    stages {
        stage('Install Playwright') {
            steps {
                // This installs the browsers (Chromium, etc.) on your Windows machine
                bat 'mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install --with-deps"'
            }
        }
        stage('Run Tests') {
            steps {
                // Runs your Maven tests locally
                bat 'mvn test'
            }
        }
    }
    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
