def gitArray = ['https://github.com/harvey1327/KotlinMicroService.git']

gitArray.each { gitAddress ->
  setupPipelineJob(gitAddress)
}

createBuildMonitorView()

def createBuildMonitorView(){
  buildMonitorView('Overall View'){
    jobs {
      regex('service-.*')
    }
    recurse(true)
  }
}

def setupPipelineJob(String gitAddress) {
  def jobName = gitAddress.substring(gitAddress.lastIndexOf("/")+1,gitAddress.lastIndexOf("."))
  multibranchPipelineJob("service-$jobName"){
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
