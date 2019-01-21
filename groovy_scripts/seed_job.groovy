import jenkins.model.Jenkins;
import hudson.model.FreeStyleProject;
import hudson.triggers.*;

def instance = Jenkins.getInstance()

def job = instance.createProject(FreeStyleProject, 'Seed-Job')
job.setDisplayName('Seed-Job')
job.addTrigger(new TimerTrigger('H/15 * * * *'))

def builder = new javaposse.jobdsl.plugin.ExecuteDslScripts()
builder.setIgnoreExisting(false)
builder.setRemovedConfigFilesAction(javaposse.jobdsl.plugin.RemovedConfigFilesAction.DELETE)
builder.setRemovedJobAction(javaposse.jobdsl.plugin.RemovedJobAction.DELETE)
builder.setRemovedViewAction(javaposse.jobdsl.plugin.RemovedViewAction.DELETE)
builder.setScriptText(getScriptText())
job.buildersList.add(builder)

job.save()
instance.save()

def getScriptText() {
  return '''
  def gitArray = ['https://github.com/harvey1327/KotlinMicroService.git']

  gitArray.each { gitAddress ->
    setupPipelineJob(gitAddress)
  }

  createBuildMonitorView()  

  def createBuildMonitorView(){
    buildMonitorView('Overall View'){
      jobs {
        regex(".*(?<!Seed-Job)$")
      }
      recurse(true)
    }
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
  '''
}
