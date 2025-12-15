pipeline {
    agent { label 'slave-1' }

    environment {
        REGISTRY = "registry:5000"
        IMAGE_NAME = "python-api"
        IMAGE_TAG = "latest"
        FULL_IMAGE = "${REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/sirartem001/python_api'
            }
        }

        stage('Build image (with tests)') {
            steps {
                sh '''
                  docker build -t $FULL_IMAGE .
                '''
            }
        }

        stage('Push image') {
            steps {
                sh '''
                  docker push $FULL_IMAGE
                '''
            }
        }
    }

    post {
        success {
            echo "✅ Build & tests passed"
        }
        failure {
            echo "❌ Pipeline failed"
        }
    }
}
