pipeline {
    agent any
    tools {
        gradle 'Gradle'
    }
    environment {
        BRANCH_DEPLOY = 'production'
        OCEAN_HOST = "178.128.104.212"
        DOCKER_HUB_REPO = 'ledonchung'
        SERVICES = 'DiscoveryService APIGateway AuthService ChatService InventoryService NotificationService OrderService PayService PostService ProductService UserService ConsultService HealthCheckService RecommendService'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: env.BRANCH_DEPLOY, url: 'https://github.com/LeDonChung/CardioTrackBackend.git'
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
                        if (service != 'RecommendService') {
                            stage("Build ${service}") {
                                dir(service) {
                                    sh 'chmod +x gradlew'
                                    sh './gradlew clean build -x test'
                                }
                            }
                        } else {
                            echo "Skipping build for ${service} (non-Java service)"
                        }
                    }
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    sh 'docker-compose --env-file .env build'
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
                            def kebab = service.replaceAll(/(?<=[a-z])(?=[A-Z])/, '-').toLowerCase()
                            def imageBase = "${DOCKER_HUB_REPO}/${kebab}"
                            sh "docker tag ${imageBase} ${imageBase}:${env.BUILD_NUMBER}"
                            sh "docker push ${imageBase}:${env.BUILD_NUMBER}"
                            sh "docker push ${imageBase}:latest"
                        }
                    }
                }
            }
        }

        stage('Deploy to Ocean') {
            steps {
                withCredentials([
                    sshUserPrivateKey(credentialsId: 'ocean-ssh', keyFileVariable: 'KEY', usernameVariable: 'USER'),
                    usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')
                ]) {
                    script {
                        def remoteHost = env.OCEAN_HOST
                        def deployDir = "/home/$USER/cardio-track"

                        // Gửi file .env từ Jenkins sang Ocean
                        sh """
                            scp -i $KEY -o StrictHostKeyChecking=no .env $USER@$remoteHost:${deployDir}/.env || true
                        """

                        // SSH vào server để deploy
                        sh """
                            ssh -i $KEY -o StrictHostKeyChecking=no $USER@$remoteHost << EOF
                            set -e

                            if [ ! -d "${deployDir}" ]; then
                                git clone -b ${BRANCH_DEPLOY} https://github.com/LeDonChung/CardioTrackBackend.git ${deployDir}
                            else
                                cd ${deployDir}
                                git fetch origin
                                git checkout ${BRANCH_DEPLOY}
                                git pull origin ${BRANCH_DEPLOY}
                            fi

                            cd ${deployDir}

                            echo "$DOCKER_PASSWORD" | docker login --username "$DOCKER_USERNAME" --password-stdin

                            docker-compose -f docker-compose.deploy.yml --env-file .env pull
                            docker-compose -f docker-compose.deploy.yml --env-file .env up -d
                        """
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
