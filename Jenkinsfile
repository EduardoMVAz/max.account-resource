import groovy.json.JsonBuilder

pipeline {
    agent any
    stages {
        stage('Build Interface') {
            steps {
                build job: 'Account', wait: true
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Post Security Scan') {
            steps {
                script {
                    // Retrieve the Git URL
                    def gitUrl = sh(script: 'git config --get remote.origin.url', returnStdout: true).trim()

                    // Hardcoded id_service
                    def idService = 'your_id_service_value'

                    // Create a new JSON payload with the Git URL and id_service
                    def newPayload = new JsonBuilder([
                        repo_url: gitUrl,
                        id_service: idService
                    ]).toPrettyString()

                    // Post the new JSON payload to the API
                    sh "curl -X POST -H 'Content-Type: application/json' -d '${newPayload.replace("'", "'\"'\"'")}' https://scan-api-44s6izf3qa-uc.a.run.app/scan"
                }
            }
        }

        stage('Build Image') {
            steps {
                script {
                    account = docker.build("pejassinaturasdocker/account:${env.BUILD_ID}", "-f Dockerfile .")
                }
            }
        }
        stage('Push Image'){
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub-credential') {
                        account.push("${env.BUILD_ID}")
                        account.push("latest")
                    }
                }
            }
        }
        stage('Deploy on k8s') {
            steps {
                withCredentials([ string(credentialsId: 'minikube-credential', variable: 'api_token') ]) {
                    sh 'kubectl --token $api_token --server https://host.docker.internal:32769 --insecure-skip-tls-verify=true apply -f ./k8s/deployment.yaml'
                    sh 'kubectl --token $api_token --server https://host.docker.internal:32769 --insecure-skip-tls-verify=true apply -f ./k8s/service.yaml'
                }
            }
        }
    }
}