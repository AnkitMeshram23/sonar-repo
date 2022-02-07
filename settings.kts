package Javaaap.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Javaaap_Build : BuildType({
    id("Build")
    name = "Build"

    vcs {
        root(Javaaap_HttpsGithubComAnkitMeshram23sonarRepoGitRefsHeadsMain)
    }
    steps {
        maven {
            name = "install"
            goals = "clean install"
        }
        step {
            name = "sonar test"
            type = "sonar-plugin"
            param("sonarProjectName", "teamcity")
            param("sonarServer", "449fcbc5-b1b4-4c47-b2c0-9b0d00034f61")
        }
        script {
            name = "quality gate"
            scriptContent = """
                ERR_COUNT=`curl -s -u admin:admin@1234 http://65.1.92.176:9000/api/qualitygates/project_status?projectKey=Javaaap -L | grep -c 'ERROR'`
                
                #Check whether they are equal 
                if [ ${'$'}ERR_COUNT -eq 0 ] 
                then 
                    echo "QUALITY GATE PASSED!"
                    echo ${'$'}ERR_COUNT
                
                elif [ ${'$'}ERR_COUNT != 0 ] 
                then 
                    echo "QUALITY GATE NOT PASSED!"
                    echo ${'$'}ERR_COUNT
                    exit 2
                fi
            """.trimIndent()
        }
    }
    triggers {
        vcs {
        }
    }
})
