pipeline {
    agent {
        // Используем полный официальный образ Temurin JDK 21 (на базе Ubuntu)
        // Он гарантирует 100% совместимость с Gradle Wrapper 8.8
        docker {
            image 'eclipse-temurin:21-jdk'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
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
                // Запуск Gradle 8.8 через Wrapper
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
