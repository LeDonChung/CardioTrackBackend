pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                 sh 'ls -la'
                // Lấy code từ Git từ nhánh jenkins
                git branch: 'jenkins', url: 'https://github.com/LeDonChung/CardioTrackBackend.git'
                // Đảm bảo gradlew có quyền thực thi
                sh 'chmod +x gradlew'
                // Chạy build toàn bộ dự án
                sh './gradlew clean build'
            }
        }
        
        stage('List Artifacts') {
            steps {
                // Kiểm tra file jar của DiscoveryService được tạo ra chưa (nếu có)
                dir('DiscoveryService/build/libs') {
                    sh 'ls -la'
                }
            }
        }
        
        stage('Build Images') {
            steps {
                script {
                    // Build image bằng docker-compose (docker-compose.yml ở gốc repo)
                    sh 'docker-compose build'
                }
            }
        }
        
        stage('Deploy') {
            steps {
                script {
                    sh 'docker-compose down'
                    sh 'docker-compose up -d --remove-orphans'
                }
            }
        }
    }
}