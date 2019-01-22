import jenkins.model.Jenkins;
import hudson.model.FreeStyleProject;
import hudson.triggers.*;

def instance = Jenkins.getInstance()

def job = instance.createProject(FreeStyleProject, 'Seed-Job')
job.setDisplayName('Seed-Job')
job.addTrigger(new TimerTrigger('H/15 * * * *'))

def scm = new hudson.plugins.git.GitSCM('https://github.com/harvey1327/JenkinsBuild')
job.setScm(scm)

def builder = new javaposse.jobdsl.plugin.ExecuteDslScripts()
builder.setIgnoreExisting(false)
builder.setRemovedConfigFilesAction(javaposse.jobdsl.plugin.RemovedConfigFilesAction.DELETE)
builder.setRemovedJobAction(javaposse.jobdsl.plugin.RemovedJobAction.DELETE)
builder.setRemovedViewAction(javaposse.jobdsl.plugin.RemovedViewAction.DELETE)
builder.setTargets('**/job_dsl/*.groovy')
job.buildersList.add(builder)

job.save()
instance.save()
