pipeline {
    agent any
    tools {
        gradle 'Gradle'
    }
    environment {
        DOCKER_HUB_REPO = 'ledonchung'
        SERVICES = 'DiscoveryService APIGateway AuthService ChatService InventoryService NotificationService OrderService PayService PostService ProductService UserService ConsultService HealthCheckService'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'deploy', url: 'https://github.com/LeDonChung/CardioTrackBackend.git'
            }
        }

        stage('Load .env') {
            steps {
                withCredentials([file(credentialsId: 'env-ct', variable: 'ENV_FILE')]) {
                    sh 'rm -f .env'
                    sh 'cp "$ENV_FILE" .env'
                }
            }
        }

        stage('Build JARs') {
            steps {
                script {
                    def services = env.SERVICES.split()
                    services.each { service ->
                        stage("Build ${service}") {
                            dir(service) {
                                sh 'chmod +x gradlew'
                                sh './gradlew clean build -x test'
                            }
                        }
                    }
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    sh 'docker-compose --version'
                    sh 'docker-compose --env-file .env build'

                    def services = env.SERVICES.split()
                    services.each { service ->
                        def kebabName = service.replaceAll(/([a-z])([A-Z])/, '$1-$2').toLowerCase()
                        def actualImageName = "cardiotrack-${kebabName}"
                        def imageName = "${DOCKER_HUB_REPO}/${kebabName}:${env.BUILD_NUMBER}"
                        def latestImageName = "${DOCKER_HUB_REPO}/${kebabName}:latest"
                        sh "docker tag ${actualImageName} ${imageName}"
                        sh "docker tag ${actualImageName} ${latestImageName}"
                    }
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh 'echo $DOCKER_PASSWORD | docker login --username $DOCKER_USERNAME --password-stdin'
                    script {
                        def services = env.SERVICES.split()
                        services.each { service ->
                            def kebabName = service.replaceAll(/([a-z])([A-Z])/, '$1-$2').toLowerCase()
                            def imageName = "${DOCKER_HUB_REPO}/${kebabName}:${env.BUILD_NUMBER}"
                            def latestImageName = "${DOCKER_HUB_REPO}/${kebabName}:latest"
                            sh "docker push ${imageName}"
                            sh "docker push ${latestImageName}"
                        }
                    }
                }
            }
        }
    }
    post {
        always {
            sh 'docker logout'
        }
    }
}
