pipeline {
    agent any

    stages {
        stage ('Run tests') {

            steps {
                scmSkip(deleteBuild: true, skipPattern:'.*\\[CI-SKIP\\].*')

                withMaven(maven: 'Maven v3.8.3', jdk: 'JDK v8u221') {
                  //sh 'mvn clean package'
                  sh 'mvn clean test'
                }
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml'
                }
                failure {
                    cleanWs()
                }
            }
        }

        stage('SonarQube Analysis') {
          tools {
              nodejs 'Node v16.13.0'
          }
            steps {
              scmSkip(deleteBuild: true, skipPattern:'.*\\[CI-SKIP\\].*')

                withSonarQubeEnv('SonarQube-Panosru') {
                  withMaven(maven: 'Maven v3.8.3', jdk: 'JDK v17') {
                    sh "mvn clean verify ${SONAR_MAVEN_GOAL} -Dsonar.host.url=${SONAR_HOST_URL} -Dmaven.exec.skip=true -Dmaven.jar.skip=true -Dmaven.dependency.skip=true -Dsonar.login=${SONAR_AUTH_TOKEN}"
                  }
                }
                timeout(time: 1, unit: 'HOURS') {
                  waitForQualityGate abortPipeline: true
                }
            }
        }
    }

    post {
      cleanup {
        catchError(buildResult: null, stageResult: 'FAILURE', message: 'Cleanup Failure') {
            echo '...Cleaning up workspace'
            cleanWs()
        }
      }
    }
}
