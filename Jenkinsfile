pipeline {
    agent any
    tools {
        maven 'sonarmaven' // Ensure this matches the Maven installation name in Jenkins
    }
    environment {
        // SONAR_SCANNER_PATH = 'C:\\Users\\91844\\Downloads\\sonar-scanner-cli-6.2.1.4610-windows-x64\\sonar-scanner-6.2.1.4610-windows-x64\\bin\\sonar-scanner.bat'
        // SONAR_HOST_URL = 'http://localhost:9000'
        // SONAR_PROJECT_KEY = 'Maven-java'
        // SONAR_PROJECT_NAME = 'Maven-java'
        SONAR_TOKEN = credentials('sonar-token')
    }
    stages {
        stage('Checkout') {
            steps {
                // git branch: 'main', url: 'https://github.com/Siddhant40/java-maven.git' // Replace with your GitHub repository URL
                checkout scm
            }
        }
        stage('Build') {
            steps {
                bat 'mvn -X clean install' // Clean and build the project
            }
        }
        stage('Unit Tests') {
            steps {
                bat 'mvn test' // Run unit tests separately
            }
        }
        stage('Compile') {
            steps {
                bat 'mvn compile' // compiles the project
            }
        }
        stage('SonarQube Analysis') {
    steps {
        withSonarQubeEnv('sonarqube') {
            bat """
            mvn clean verify sonar:sonar \
            -Dsonar.projectKey=Ass-2-maven-project \
            -Dsonar.projectName='Ass-2-maven-project' \
            -Dsonar.host.url=http://localhost:9000 \
            -Dsonar.token=$SONAR_TOKEN \
            -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
            """
        }
    }
}
    }
    post {
        success {
            echo 'Build, testing, and analysis completed successfully!'
        }
        failure {
            echo 'Build, testing, or analysis failed!'
        }
        always {
            echo 'Pipeline execution finished.'
        }
    }
}
