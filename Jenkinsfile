pipeline {
    agent any

    environment {
        CI = 'true'
    }

    tools {
        jdk 'JDK21'
    }

    options {
        timeout(time: 1, unit: 'HOURS')
        ansiColor('xterm')
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Grant Execute Permissions') {
            steps {
                echo 'Making gradlew executable for Linux environment...'
                sh 'chmod +x gradlew'
            }
        }

        stage('Run UI & API Tests') {
            steps {
                echo 'Starting Gradle test execution...'
                sh './gradlew clean test --no-daemon -Dgradle.continue=true'
            }
        }
    }

    post {
        always {
            echo 'Generating Allure Report...'
            allure includeProperties: false, jdk: '', results: [[path: 'build/allure-results']]
        }
        success {
            echo '=================================================='
            echo 'Pipeline finished successfully! All tests passed.'
            echo '=================================================='
        }
        failure {
            echo '=================================================='
            echo 'Pipeline failed! Check test failures or compilation.'
            echo '=================================================='
        }
    }
}
