def gitArray = ['https://github.com/harvey1327/KotlinMicroService.git']
def groupName = 'service'

node {

  gitArray.each { gitAddress ->
    def jobName = gitAddress.substring(gitAddress.lastIndexOf("/")+1,gitAddress.lastIndexOf("."))
    stage("Set up Multibranch for $jobName"){
      setupPipelineJob(gitAddress, jobName)
    }
  }

  stage('Build Monitor View'){
    buildMonitorView("$groupName View"){
      jobs {
        regex("$groupName-.*")
      }
      recurse(true)
    }
  }
}

def setupPipelineJob(String gitAddress, String jobName) {
  multibranchPipelineJob("$groupName-$jobName"){
    branchSources {
        git {
            remote(gitAddress)
            includes('*')
        }
    }
    triggers {
      periodic(5)
    }
  }
}
