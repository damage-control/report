package com.github.damagecontrol.report.e2etest

class MvnWrapper {

    private static final String MVN_PATH = mvnPath()

    def project

    private static String mvnPath() {
        String mvnPath
        if (System.getenv('M2_HOME')) {
            String ext = System.getProperty('os.name').contains('Win') ? '.bat' : ''
            mvnPath = System.getenv('M2_HOME') + '/bin/mvn' + ext
        } else {
            mvnPath = System.getenv('MVN_COMMAND')
        }
        mvnPath
    }

    def run(String ... extraArgs) {
        def mvnCommand = ["$MVN_PATH", "-e", "clean", "test"]
        mvnCommand.addAll(Arrays.asList(extraArgs))
        println "Maven Command=$mvnCommand"

        def mvnProcess = mvnCommand.execute([], project)
        consumeOutput(mvnProcess)

        def exitValue = mvnProcess.exitValue()
        println "Exit Value=$exitValue"
        println ''
    }

    private void consumeOutput(Process mvnProcess) {
        def standardOutput = new StringBuffer()
        def errorOutput = new StringBuffer()

        mvnProcess.consumeProcessOutput(standardOutput, errorOutput)
        mvnProcess.waitFor()

        println standardOutput
        println errorOutput
    }
}
