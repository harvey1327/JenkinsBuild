def gitArray = ['https://github.com/harvey1327/KotlinMicroService.git']

gitArray.each { gitAddress ->
  setupPipelineJob(gitAddress)
}

def setupPipelineJob(String gitAddress) {
  def jobName = gitAddress.substring(gitAddress.lastIndexOf("/")+1,gitAddress.lastIndexOf("."))
  multibranchPipelineJob("$jobName"){
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
