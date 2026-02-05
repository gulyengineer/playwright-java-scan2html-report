pipeline {
    agent any 
    
    environment {
        // Defined here, it's available to all stages and your Java code
        TEST_REPORT_URL = 'https://gulyengineer.github.io/playwright-java-scan2html-report/'
    }

    stages {
        stage('Install Playwright Browsers') {
            steps {
                // Installs binaries to your local Windows Jenkins node
                bat 'mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install --with-deps"'
            }
        }
        stage('Run Tests') {
            steps {
                // Maven will automatically pick up TEST_REPORT_URL from the environment
                bat 'mvn test'
            }
        }
    }
    
    post {
        always {
            // Publishes the test results in the Jenkins UI
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
