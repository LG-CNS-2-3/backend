pipeline {
	agent any

    tools{
        gradle 'gradle'
        jdk 'openJDK17'
    }

    environment{
        DOCKER_USERNAME = "khw73850"
        EC2_HOST = "ubuntu@10.0.0.20"
    }

    stages{
        stage('Checkout') {
            steps {
                echo "Checking out source..."
                checkout scm
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
//                 stage('map: Test'){
//                     steps{
//                         echo "==================== Testing backend_map ===================="
//                         dir('backend_map'){
//                             sh './gradlew test --no-daemon'
//                         }
//                     }
//                 }
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
                                   docker build -t ${DOCKER_USER}/backend-map:${currentBuild.number} ./backend_map
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

                stage('map: Deploy'){
                    steps{
                        echo "==================== Deploying to EC2 (backend-map) ===================="
                        sshagent(credentials: ['EC2_SSH_CREDENTIALS']) {
                            withCredentials([
                                string(credentialsId: 'TMAP_API_KEY', variable: 'TMAP_API_KEY'),
                                string(credentialsId: 'EUREKA_SERVICE_URL', variable: 'EUREKA_SERVICE_URL')
                            ]){
                                sh """
                                    ssh -o StrictHostKeyChecking=no ${EC2_HOST} '''

                                         docker pull ${DOCKER_USERNAME}/backend-map:latest

                                         docker stop backend-map-container || true
                                         docker rm backend-map-container || true


                                         export HOST_IP=\$(TOKEN=\$(curl -s -X PUT "http://169.254.169.254/latest/api/token" -H "X-aws-ec2-metadata-token-ttl-seconds: 21600") && \
                                         curl -s -H "X-aws-ec2-metadata-token: \$TOKEN" http://169.254.169.254/latest/meta-data/local-ipv4)

                                         docker run -d --name backend-map-container \
                                            -p 8080:8080 \
                                            -e EUREKA_INSTANCE_IP_ADDRESS=\${HOST_IP} \
                                            -e TMAP_API_KEY=${TMAP_API_KEY} \
                                            -e EUREKA_SERVICE_URL=${EUREKA_SERVICE_URL} \
                                            ${DOCKER_USERNAME}/backend-map:latest

                                         docker image prune -f
                                    '''
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
