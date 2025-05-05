pipeline {
    agent any
    tools {
        gradle 'Gradle'
    }
    environment {
        DOCKER_HUB_CREDENTIALS = credentials('docker-hub-credentials')
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
                    withCredentials([file(credentialsId: 'env-ct', variable: 'ENV_FILE')]) {
                        if (fileExists(env.ENV_FILE)) {
                            def envVars = readFile(env.ENV_FILE).split('\n')
                            envVars.each { line ->
                                if (line.trim() && !line.startsWith('#')) {
                                    def (key, value) = line.split('=', 2)
                                    env[key.trim()] = value.trim()
                                }
                            }
                        }
                    }

                    env.DOCKER_HUB_USERNAME = DOCKER_HUB_CREDENTIALS_USR
                    env.DOCKER_HUB_PASSWORD = DOCKER_HUB_CREDENTIALS_PSW
                }
            }
        }
        stage('Run Tests') {
            steps {
                script {
                    def services = env.SERVICES.split()
                    parallel services.collectEntries { service ->
                        ["Test ${service}", {
                            dir(service) {
                                sh 'chmod +x gradlew'
                                sh './gradlew clean test'
                            }
                        }]
                    }
                }
            }
        }
        stage('Build JARs') {
            steps {
                script {
                    def services = env.SERVICES.split()
                    parallel services.collectEntries { service ->
                        ["Build ${service}", {
                            dir(service) {
                                sh 'chmod +x gradlew'
                                sh './gradlew clean build -x test'
                            }
                        }]
                    }
                }
            }
        }
        stage('Build Docker Images') {
            steps {
                script {
                    sh 'docker-compose build'
                    def services = env.SERVICES.split()
                    services.each { service ->
                        def imageName = "${DOCKER_HUB_REPO}/${service.toLowerCase()}:${env.BUILD_NUMBER}"
                        def latestImageName = "${DOCKER_HUB_REPO}/${service.toLowerCase()}:latest"
                        sh "docker tag ${service.toLowerCase()}:${env.BUILD_NUMBER} ${imageName}"
                        sh "docker tag ${service.toLowerCase()}:${env.BUILD_NUMBER} ${latestImageName}"
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
                            def imageName = "${DOCKER_HUB_REPO}/${service.toLowerCase()}:${env.BUILD_NUMBER}"
                            def latestImageName = "${DOCKER_HUB_REPO}/${service.toLowerCase()}:latest"
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