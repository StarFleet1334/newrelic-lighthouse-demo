import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition

// Directory where your DSL scripts are stored
def scriptsDirPath = "/var/jenkins_home/job-dsl-scripts"
def scriptsDir = new File(scriptsDirPath)

if (scriptsDir.exists() && scriptsDir.isDirectory()) {
    scriptsDir.eachFile { file ->
        if (file.name.endsWith(".groovy")) {
            // For simplicity, using the file name without extension as the job name
            def jobName = file.name.split('\\.')[0]
            Jenkins jenkins = Jenkins.instance
            // Check if the job already exists to avoid duplication
            if (jenkins.getItem(jobName) == null) {
                // Create a new Pipeline job
                WorkflowJob project = jenkins.createProject(WorkflowJob.class, jobName)
                // Read the content of the Groovy file
                def scriptContent = file.text
                // Set the Pipeline script to the content of the Groovy file
                project.setDefinition(new CpsFlowDefinition(scriptContent, true))
                println("Created Pipeline job: ${jobName} with script from ${file.name}")
            } else {
                println("Job already exists: ${jobName}")
            }
        }
    }
} else {
    println("Directory not found or is not a directory: $scriptsDirPath")
}
