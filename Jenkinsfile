pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                // 1. Lấy code từ Git
                git branch: 'jenkins', url: 'https://github.com/LeDonChung/CardioTrackBackend.git'
            }
        }
        
        stage('Build Images') {
            steps {
                script {
                    // 2. Build toàn bộ service theo docker-compose.yml
                    //    Nếu muốn cập nhật base image, bạn có thể thêm --pull --no-cache
                    sh 'docker-compose build'
                }
            }
        }
        
        stage('Deploy') {
            steps {
                script {
                    // 3. Dừng container cũ và khởi chạy container mới
                    sh 'docker-compose down'
                    sh 'docker-compose up -d --remove-orphans'
                }
            }
        }
    }
}
