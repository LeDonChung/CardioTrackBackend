pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                // Lấy code từ Git từ nhánh jenkins
                git branch: 'jenkins', url: 'https://github.com/LeDonChung/CardioTrackBackend.git'
                // Build toàn bộ dự án (giả sử gradlew ở gốc build tất cả các module)
                sh './gradlew clean build'
            }
        }
        
        stage('List Artifacts') {
            steps {
                // In danh sách file trong thư mục build/libs của DiscoveryService để kiểm tra file jar đã được tạo chưa
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