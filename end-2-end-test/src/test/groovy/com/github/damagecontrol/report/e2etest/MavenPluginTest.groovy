package com.github.damagecontrol.report.e2etest

import spock.lang.Specification

class MavenPluginTest extends Specification {

    private static final String EXT = System.getProperty('os.name').contains('Win') ? '.bat' : ''
    private static final String M2_HOME = System.getenv("M2_HOME")
    private static final String MVN = "${M2_HOME}/bin/mvn${EXT}"

    private static final File SAMPLE_MVN_PROJECT = new File("sample-spock-project")

    def "should generate reports using Spock specifications"() {
        when: "I run a Maven build"
        def mvn = ["$MVN", "-e", "clean", "test"].execute(["M2_HOME=${M2_HOME}"], SAMPLE_MVN_PROJECT)

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
