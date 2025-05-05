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
                script {
                    def envVarsList = []
                    withCredentials([file(credentialsId: 'env-ct', variable: 'ENV_FILE')]) {
                        if (fileExists(env.ENV_FILE)) {
                            def envVars = readFile(env.ENV_FILE).split('\n')
                            envVars.each { line ->
                                if (line.trim() && !line.startsWith('#')) {
                                    def (key, value) = line.split('=', 2)
                                    envVarsList << "${key.trim()}=${value.trim()}"
                                }
                            }
                        }
                    }
                    withEnv(envVarsList) {
                        echo "Loaded environment variables"
                    }
                }
            }
        }

        // Optional: Enable this if needed
        // stage('Run Tests') {
        //     steps {
        //         script {
        //             def services = env.SERVICES.split()
        //             services.each { service ->
        //                 stage("Test ${service}") {
        //                     dir(service) {
        //                         sh 'chmod +x gradlew'
        //                         sh './gradlew clean test'
        //                     }
        //                 }
        //             }
        //         }
        //     }
        // }

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
                    sh 'docker-compose up -d'

                    def services = env.SERVICES.split()
                    services.each { service ->
                        def serviceName = service.toLowerCase()
                        def imageName = "${DOCKER_HUB_REPO}/${serviceName}:${env.BUILD_NUMBER}"
                        def latestImageName = "${DOCKER_HUB_REPO}/${serviceName}:latest"
                        sh "docker tag ${serviceName} ${imageName}"
                        sh "docker tag ${serviceName} ${latestImageName}"
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
                            def serviceName = service.toLowerCase()
                            def imageName = "${DOCKER_HUB_REPO}/${serviceName}:${env.BUILD_NUMBER}"
                            def latestImageName = "${DOCKER_HUB_REPO}/${serviceName}:latest"
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
