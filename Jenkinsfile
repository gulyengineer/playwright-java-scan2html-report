pipeline {
    agent any 
    
    environment {
        TEST_REPORT_URL = 'https://gulyengineer.github.io/playwright-java-scan2html-report/'
    }
    triggers {
            cron('H H * * *')
        }

    stages {
        stage('Install Playwright Browsers') {
            steps {
                bat 'mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install --with-deps"'
            }
        }
        stage('Run Tests') {
            steps {
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
