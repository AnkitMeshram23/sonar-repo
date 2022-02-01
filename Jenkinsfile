pipeline {
    agent any
    tools {
	maven 'java_maven'
    }  
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',		    
		credentialsId: 'bb059738-bc0c-439c-af36-4c5aeeb1128e',
			url: "https://github.com/AnkitMeshram23/sonar-repo.git"    
	    }
	}	
        stage('Building') {
            steps {
                sh 'mvn clean install'
            }
        }		    
	    
        stage('Sonar Analysis') {
environment {
SCANNER_HOME = tool 'Anksonar'
PROJECT_NAME = "test"
}
steps {
withSonarQubeEnv('sonar1') {
sh '''$SCANNER_HOME/bin/sonar-scanner \
-Dsonar.java.binaries=build/classes/java/ \
-Dsonar.projectKey=$PROJECT_NAME \
-Dsonar.sources=.'''
}
}
}
         stage('Quality Gate') {
           steps {
              timeout(time: 1, unit: 'MINUTES') {
                waitForQualityGate abortPipeline:true
              }
            }
          }
	    stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
	stage('Deploy') {
            steps {
		    sh 'cp /root/.jenkins/workspace/Java-pipeline/target/*.war /opt/apache-tomcat-9.0.58/webapps/'
            }
        }
  }
}
