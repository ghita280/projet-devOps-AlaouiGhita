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
            steps {
                echo 'Déploiement de l\'application...'
                sh '''
                    echo "Simulation du déploiement"
                    java -jar target/${APP_NAME}-1.0-SNAPSHOT.jar
                '''
            }
        }
    }
    
    post {
        success {
            echo 'Pipeline exécuté avec succès!'
        }
        failure {
            echo 'Le pipeline a échoué.'
        }
    }
}