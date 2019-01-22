import jenkins.model.Jenkins;
import hudson.model.FreeStyleProject;
import hudson.triggers.*;
import hudson.plugins.git.*;
import javaposse.jobdsl.plugin.*;

def instance = Jenkins.getInstance()

def job = instance.createProject(FreeStyleProject, 'Seed-Job')
job.setDisplayName('Seed-Job')

// Uses hudson.triggers.*
job.addTrigger(new TimerTrigger('H/15 * * * *'))

// Uses hudson.plugins.git.*
def scm = new GitSCM('https://github.com/harvey1327/JenkinsBuild')
scm.branches = [new BranchSpec("*/master")];
job.setScm(scm)

// Uses javaposse.jobdsl.plugin.*
def builder = new ExecuteDslScripts()
builder.setIgnoreExisting(false)
builder.setRemovedConfigFilesAction(RemovedConfigFilesAction.DELETE)
builder.setRemovedJobAction(RemovedJobAction.DELETE)
builder.setRemovedViewAction(RemovedViewAction.DELETE)
builder.setTargets('**/job_dsl/*.groovy')
job.buildersList.add(builder)

job.save()
instance.save()
