pipeline {
    agent any

    environment {
        DOCKER_REGISTRY_URL = "localhost:5000" 
        IMAGE_NAME = "python-api"
        IMAGE_TAG = "latest" 
    }

    stages {
        stage('Git Clone') {
            steps {
                git url: 'https://github.com/sirartem001/python_api.git', branch: 'main'
            }
        }

        stage('Install Dependencies & Run Tests') {
            steps {
                sh """
                python -m venv venv
                . venv/bin/activate
                pip install -r requirements.txt
                pytest
                """
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def fullImageName = "${DOCKER_REGISTRY_URL}/${IMAGE_NAME}:${IMAGE_TAG}"
                    sh "docker build -t ${fullImageName} ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    def fullImageName = "${DOCKER_REGISTRY_URL}/${IMAGE_NAME}:${IMAGE_TAG}"
                    sh "docker push ${fullImageName}"
                }
            }
        }
    }
}