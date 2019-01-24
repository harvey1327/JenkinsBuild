def gitArray = ['https://github.com/harvey1327/KotlinMicroService.git']
def groupName = 'service'

gitArray.each { gitAddress ->
  setupPipelineJob(gitAddress, groupName)
}

createBuildMonitorView(groupName)

def createBuildMonitorView(String groupName){
  buildMonitorView("$groupName View"){
    jobs {
      regex("$groupName-.*")
    }
    recurse(true)
  }
}

def setupPipelineJob(String gitAddress, String groupName) {
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
