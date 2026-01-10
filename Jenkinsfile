pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9'
    }
    
    environment {
        APP_NAME = 'projet-devops'
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Récupération du code source...'
                checkout scm
                echo 'Code récupéré avec succès'
            }
        }
        
        stage('Build') {
            steps {
                echo 'Compilation du projet...'
                sh 'mvn clean compile'
                echo 'Compilation terminée'
            }
        }
        
        stage('Test') {
            steps {
                echo 'Exécution des tests...'
                sh 'mvn test'
                echo 'Tests terminés'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Package') {
            steps {
                echo 'Packaging de l\'application...'
                sh 'mvn package -DskipTests'
                echo 'Package créé'
            }
        }
        
        stage('Archive') {
            steps {
                echo 'Archivage des artifacts...'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                echo 'Archivage terminé'
            }
        }
        
        stage('Deploy') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                echo 'Déploiement de l\'application...'
                sh '''
                    echo "Simulation du déploiement"
                    java -jar target/${APP_NAME}-1.0-SNAPSHOT.jar
                '''
            }
        }
        
        stage('Notify Slack') {
            steps {
                script {
                    def status = currentBuild.result ?: 'SUCCESS'
                    def color = status == 'SUCCESS' ? 'good' : 'danger'
                    def message = status == 'SUCCESS' ? 
                        " Pipeline exécuté avec succès!" : 
                        " Le pipeline a échoué"
                    
                    slackSend(
                        channel: '#jenkins-notifications',
                        color: color,
                        message: "${message}\nJob: ${env.JOB_NAME}\nBuild: ${env.BUILD_NUMBER}\nURL: ${env.BUILD_URL}"
                    )
                }
            }
        }
    }
    
    post {
        success {
            echo 'Pipeline exécuté avec succès!'
            slackSend(
                channel: '#jenkins-notifications',
                color: 'good',
                message: " Succès: ${env.JOB_NAME} #${env.BUILD_NUMBER}\n${env.BUILD_URL}"
            )
        }
        failure {
            echo 'Le pipeline a échoué.'
            slackSend(
                channel: '#jenkins-notifications',
                color: 'danger',
                message: " Échec: ${env.JOB_NAME} #${env.BUILD_NUMBER}\n${env.BUILD_URL}"
            )
        }
    }
}