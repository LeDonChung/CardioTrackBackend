pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                // 1. Lấy code từ Git (nhánh jenkins)
                git branch: 'jenkins', url: 'https://github.com/LeDonChung/CardioTrackBackend.git'
            }
        }
        
        stage('Build All Services') {
            steps {
                script {
                    // 2. Lần lượt build từng service
                    dir('DiscoveryService') {
                        sh 'chmod +x gradlew'
                        sh './gradlew clean build'
                    }
                    dir('APIGateway') {
                        sh 'chmod +x gradlew'
                        sh './gradlew clean build'
                    }
                    dir('AuthService') {
                        sh 'chmod +x gradlew'
                        sh './gradlew clean build'
                    }
                    dir('ChatService') {
                        sh 'chmod +x gradlew'
                        sh './gradlew clean build'
                        sh './gradlew clean build -x test'
                    }
                    dir('InventoryService') {
                        sh 'chmod +x gradlew'
                        sh './gradlew clean build'
                    }
                    dir('NotificationService') {
                        sh 'chmod +x gradlew'
                        sh './gradlew clean build'
                    }
                    dir('OrderService') {
                        sh 'chmod +x gradlew'
                        sh './gradlew clean build'
                    }
                    dir('PayService') {
                        sh 'chmod +x gradlew'
                        sh './gradlew clean build'
                    }
                    dir('PostService') {
                        sh 'chmod +x gradlew'
                        sh './gradlew clean build'
                    }
                    dir('ProductService') {
                        sh 'chmod +x gradlew'
                        sh './gradlew clean build'
                    }
                    dir('UserService') {
                        sh 'chmod +x gradlew'
                        sh './gradlew clean build'
                    }
                }
            }
        }
        
        stage('Build Docker Images') {
            steps {
                script {
                    // 3. Build image bằng docker-compose (sẽ gọi Dockerfile trong từng service)
                    sh 'docker-compose build'
                }
            }
        }
        
        stage('Deploy') {
            steps {
                script {
                    // 4. Triển khai
                    sh 'docker-compose down'
                    sh 'docker-compose up -d --remove-orphans'
                }
            }
        }
    }
}
