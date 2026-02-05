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
             junit testResults: '**/target/surefire-reports/*.xml',
                          allowEmptyResults: true
            emailext(
                            subject: "Jenkins Job '${env.JOB_NAME}' #${env.BUILD_NUMBER} finished",
                            body: """
            Job: ${env.JOB_NAME}
            Build: #${env.BUILD_NUMBER}
            Status: ${currentBuild.currentResult}

            Check console output at:
            ${env.BUILD_URL}
            """,
                            to: '$DEFAULT_RECIPIENTS'
                        )
        }
    }
}
