pipeline {
    agent any

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
                echo 'Making gradlew executable...'
                sh 'chmod +x gradlew'
            }
        }

        stage('Run UI & API Tests') {
            steps {
                echo 'Starting Gradle test execution...'
                // Запуск Gradle 8.8 через встроенную Java 21
                sh './gradlew test --no-daemon -Dgradle.continue=true'
            }
        }
    }

    post {
        always {
            echo 'Generating Allure Report...'
            allure includeProperties: false, jdk: '', results: [[path: 'build/allure-results']]
        }
        success {
            echo 'All tests passed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check compilation errors or test failures.'
        }
    }
}
