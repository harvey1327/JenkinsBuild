def gitArray = ['https://github.com/harvey1327/KotlinMicroService.git']
def groupName = 'service'

gitArray.each { gitAddress ->
  setupPipelineJob(gitAddress)
}

createBuildMonitorView()

def createBuildMonitorView(){
  buildMonitorView("$groupName View"){
    jobs {
      regex("$groupName-.*")
    }
    recurse(true)
  }
}

def setupPipelineJob(String gitAddress) {
  def jobName = gitAddress.substring(gitAddress.lastIndexOf("/")+1,gitAddress.lastIndexOf("."))
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
