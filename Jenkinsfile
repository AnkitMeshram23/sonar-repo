pipeline {
    agent any
    tools {
	maven 'My_Maven'
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
PROJECT_NAME = "New test"
}
steps {
withSonarQubeEnv('Anksonar') {
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
		    sh 'cp /root/.jenkins/workspace/Sonar-test/target/*.war /opt/tomcat/webapps/'
            }
        }
  }
}
