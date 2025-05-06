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

        stage('Detect Changed Services') {
            steps {
                script {
                    def diffOutput = sh(
                        script: "git diff --name-only origin/${BRANCH_DEPLOY}...HEAD",
                        returnStdout: true
                    ).trim()

                    def changedFiles = diffOutput.split('\n')
                    def allServices = env.SERVICES.split()
                    def changed = []

                    allServices.each { service ->
                        if (changedFiles.any { it.startsWith("${service}/") }) {
                            changed << service
                        }
                    }

                    if (changed.isEmpty()) {
                        echo '✅ No services changed. Skipping build/push.'
                        currentBuild.result = 'SUCCESS'
                        env.NO_CHANGES = 'true'
                    } else {
                        env.CHANGED_SERVICES = changed.join(' ')
                        echo "📦 Changed services: ${env.CHANGED_SERVICES}"
                    }
                }
            }
        }

        stage('Load .env') {
            when { expression { env.NO_CHANGES != 'true' } }
            steps {
                withCredentials([file(credentialsId: 'env-ct', variable: 'ENV_FILE')]) {
                    sh 'rm -f .env'
                    sh 'cp "$ENV_FILE" .env'
                    sh "echo 'TAG=${BUILD_NUMBER}' >> .env"
                }
            }
        }

        stage('Build JARs') {
            when { expression { env.NO_CHANGES != 'true' } }
            steps {
                script {
                    def services = env.CHANGED_SERVICES.split()
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
            when { expression { env.NO_CHANGES != 'true' } }
            steps {
                sh 'docker-compose --env-file .env build'
            }
        }

        stage('Push to Docker Hub') {
            when { expression { env.NO_CHANGES != 'true' } }
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh 'echo $DOCKER_PASSWORD | docker login --username $DOCKER_USERNAME --password-stdin'
                    script {
                        def services = env.CHANGED_SERVICES.split()
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
            when { expression { env.NO_CHANGES != 'true' } }
            steps {
                withCredentials([
                    sshUserPrivateKey(credentialsId: 'ocean-ssh', keyFileVariable: 'KEY', usernameVariable: 'USER'),
                    usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')
                ]) {
                    script {
                        def remoteHost = env.OCEAN_HOST
                        def deployDir = "/home/$USER/cardio-track"

                        // Gửi file .env sang server
                        sh """
                            scp -i \$KEY -o StrictHostKeyChecking=no .env \$USER@\$remoteHost:${deployDir}/.env || true
                        """

                        // SSH vào server và deploy
                        sh """
                            ssh -i \$KEY -o StrictHostKeyChecking=no \$USER@\$remoteHost << 'EOF'
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
                            docker-compose -f docker-compose.deploy.yml --env-file .env down
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

            // 🧹 Cleanup: Xóa các image cũ hơn BUILD_NUMBER
            sh """
            for image in \$(docker images --format '{{.Repository}}:{{.Tag}}' | grep '^ledonchung/'); do
              repo=\$(echo \$image | cut -d':' -f1)
              tag=\$(echo \$image | cut -d':' -f2)

              if [[ "\$tag" =~ ^[0-9]+\$ ]] && [ "\$tag" -lt ${BUILD_NUMBER} ]; then
                echo "🧹 Removing old image \$repo:\$tag"
                docker rmi "\$repo:\$tag" || true
              fi
            done
            """
        }
    }
}
