pipeline {
    environment {
        dockerHubRepositoryName = 'viniciuscoimbra1995/mapping-mind-back'
        containerName = "mapping-mind-back"
        dockerImage = ''
    }
    agent {
        docker {
            image 'maven:3.8.7-eclipse-temurin-17'
        }
    }
    stages {
        stage('Clone Repository') {
            steps {
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: '8867c671-bca7-4f1e-8934-b492830db32d', url: 'https://github.com/ViniciusCoimbra1995/mapping-mind-back.git']])
            }
        }
        stage('Build Maven Project') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Testes') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    // Adiciona a tag do build atual no Docker
                    dockerImage = docker.build("${dockerHubRepositoryName}:$BUILD_NUMBER")
                }
            }
        }
        stage('Deploy') {
            steps {
                // Para o container se ele estiver rodando
                sh script: 'docker stop $containerName || true', returnStatus: true
                // Remove o container antigo
                sh script: 'docker rm $containerName || true', returnStatus: true
                // Executa o novo container
                sh 'docker run -d --name $containerName -p 8080:8081 $dockerHubRepositoryName:$BUILD_NUMBER'
            }
        }
        stage('Push image to DockerHub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhub', variable: 'dockerhub')]) {
                        // Realiza o login de forma segura
                        sh 'echo $dockerhub | docker login -u viniciuscoimbra1995 --password-stdin'
                        // Faz o push da imagem com a tag correta
                        sh "docker push ${dockerHubRepositoryName}:$BUILD_NUMBER"
                    }
                }
            }
        }
    }
    post {
        always {
            sh 'docker logout'
            deleteDir()
        }
    }
}