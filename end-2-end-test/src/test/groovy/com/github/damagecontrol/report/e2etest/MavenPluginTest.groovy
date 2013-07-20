package com.github.damagecontrol.report.e2etest

import spock.lang.Specification

class MavenPluginTest extends Specification {

    private static final File SAMPLE_MVN_PROJECT = new File("sample-spock-project")

    def "should generate reports using Spock specifications"() {
        given: "Maven is available"
        def mvnPath

        if (System.getenv('M2_HOME')) {
            def ext = System.getProperty('os.name').contains('Win') ? '.bat' : ''
            mvnPath = System.getenv('M2_HOME') + '/bin/mvn' + ext
        } else {
            mvnPath = System.getenv('MVN_COMMAND')
        }

        when: "I run a Maven build"
        def mvn = ["$mvnPath", "-e", "clean", "test"].execute([], SAMPLE_MVN_PROJECT)

        def standardOutput = new StringBuffer()
        def errorOutput = new StringBuffer()
        mvn.consumeProcessOutput(standardOutput, errorOutput)
        mvn.waitFor()
        println standardOutput

        then: "I see an index page"
        new File(SAMPLE_MVN_PROJECT.absolutePath + "/target/damage-control-reports/index.html").exists()

        and: "I see a nice HTML report for a specification"
        new File(SAMPLE_MVN_PROJECT.absolutePath + "/target/damage-control-reports/MultipleFeaturesTest.html").exists()
    }
}
