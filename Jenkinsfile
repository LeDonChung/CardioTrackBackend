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

        // stage('Build JARs') {
        //     steps {
        //         script {
        //             def services = env.SERVICES.split()
        //             services.each { service ->
        //                 stage("Build ${service}") {
        //                     dir(service) {
        //                         sh 'chmod +x gradlew'
        //                         sh './gradlew clean build -x test'
        //                     }
        //                 }
        //             }
        //         }
        //     }
        // }

        // stage('Build Docker Images') {
        //     steps {
        //         script {
        //             sh 'docker-compose --env-file .env build'
        //         }
        //     }
        // }

        // stage('Push to Docker Hub') {
        //     steps {
        //         withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
        //             sh 'echo $DOCKER_PASSWORD | docker login --username $DOCKER_USERNAME --password-stdin'
        //             script {
        //                 def services = env.SERVICES.split()
        //                 services.each { service ->
        //                     def kebab = service.replaceAll(/(?<=[a-z])(?=[A-Z])/, '-').toLowerCase()
        //                     def imageBase = "${DOCKER_HUB_REPO}/${kebab}"
        //                     sh "docker tag ${imageBase} ${imageBase}:${env.BUILD_NUMBER}"
        //                     sh "docker push ${imageBase}:${env.BUILD_NUMBER}"
        //                     sh "docker push ${imageBase}:latest"
        //                 }
        //             }
        //         }
        //     }
        // }
        stage('Deploy to Ocean') {
            steps {
                withCredentials([
                    sshUserPrivateKey(credentialsId: 'ocean-ssh', keyFileVariable: 'KEY', usernameVariable: 'USER'),
                    usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')
                ]) {
                    script {
                        def remoteHost = "159.65.13.19"
                        def deployDir = "/home/$USER/cardio-track"
        
                        // Gửi file .env từ Jenkins sang Ocean
                        sh """
                            scp -i $KEY -o StrictHostKeyChecking=no .env $USER@$remoteHost:${deployDir}/.env || true
                        """
        
                        // SSH vào server để login Docker, pull và chạy container
                        sh """
                            ssh -i $KEY -o StrictHostKeyChecking=no $USER@$remoteHost << 'EOF'
                            set -e
        
                            if [ ! -d "${deployDir}" ]; then
                                git clone -b deploy https://github.com/LeDonChung/CardioTrackBackend.git ${deployDir}
                            else
                                cd ${deployDir}
                                git fetch origin
                                git checkout deploy
                                git pull origin deploy
                            fi
        
                            cd ${deployDir}
        
                            echo "$DOCKER_PASSWORD" | docker login --username "$DOCKER_USERNAME" --password-stdin
        
                            docker-compose -f docker-compose.deploy.yml --env-file .env pull
                            docker-compose -f docker-compose.deploy.yml --env-file .env up -d
                            EOF
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
