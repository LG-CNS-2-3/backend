pipeline {
	agent any

    tools{
        gradle 'gradle'
        jdk 'openJDK17'
    }

    stages{
        stage('Checkout') {
            steps {
                echo "Checking out source..."
                checkout scm
            }
        }

        stage('Check File Structure') {
            steps {
                echo "==================== Checking project structure ===================="
                sh '''
                    echo "현재 위치: $(pwd)"
                    ls -al
                    ls -al backend-map || echo "backend-map 폴더가 없습니다."
                '''
            }
        }

        stage('backend-map Pipeline'){
            when {
                anyOf{
                    changeset "backend_map/**"
                    branch 'develop'
                }
            }
            stages{
                stage('map: Build'){
                    steps{
                        echo "==================== Building backend-map ===================="
                        dir('backend_map'){
                            sh '''
                                chmod +x gradlew
                                ./gradlew build -x test
                            '''

                        }
                    }
                }
                stage('map: Test'){
                    steps{
                        echo "==================== Testing backend_map ===================="
                        dir('backend_map'){
                            sh './gradlew test --no-daemon'
                        }
                    }
                }
                stage('map: Docker Build and Push'){
                    steps{
                        echo "==================== Building & Pushing Docker Image(backend_map) ===================="

                        script{
                            withCredentials([usernamePassword(
                                credentialsId: 'DOCKERHUB_PASSWORD',
                                usernameVariable: 'DOCKER_USER',
                                passwordVariable: 'DOCKER_PASS'
                            )]){
                                sh """
                                   docker build -t ${DOCKER_USER}/backend-map:${currentBuild.number} ./backend-map
                                   docker tag ${DOCKER_USER}/backend-map:${currentBuild.number} ${DOCKER_USER}/backend-map:latest
                                   echo ${DOCKER_PASS} | docker login -u ${DOCKER_USER} --password-stdin
                                   docker push ${DOCKER_USER}/backend-map:${currentBuild.number}
                                   docker push ${DOCKER_USER}/backend-map:latest
                                   docker logout
                                """
                            }
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        success {
            echo "Pipeline completed successfully!"
        }
        failure {
            echo "Pipeline failed!"
        }
    }
}
