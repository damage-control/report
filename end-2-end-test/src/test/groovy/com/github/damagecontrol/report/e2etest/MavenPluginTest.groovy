package com.github.damagecontrol.report.e2etest

import spock.lang.Specification

class MavenPluginTest extends Specification {

    def sampleMvnProject
    def mvn

    def setup() {
        sampleMvnProject = new ProjectArtifacts(rootDirectory: new File("sample-spock-project"))
        mvn = new MvnWrapper(project: sampleMvnProject.rootDirectory)
    }

    def 'generating HTML report'() {
        when: 'Maven project is built'
        mvn.run()

        then: 'the index page is generated'
        sampleMvnProject.has('/target/damage-control-reports/index.html')

        and: 'each specification has an HTML page'
        sampleMvnProject.has('/target/damage-control-reports/failed.MultipleFeaturesTest.html')

        and: 'integration test page is generated'
        sampleMvnProject.has('/target/damage-control-reports/integration.MultipleFeaturesIT.html')
    }

    def 'skipping report generation by providing "-DskipTests" command-line argument'() {
        when: 'Maven project is built'
        mvn.run('-DskipTests')

        then: 'no report is generated'
        sampleMvnProject.doesNotHave('/target/damage-control-reports')
    }

    def 'skipping report generation by setting "skip" configuration property'() {
        when: 'Maven project is built'
        mvn.run('-P', 'noReport')

        then: 'no report is generated'
        sampleMvnProject.doesNotHave('/target/damage-control-reports')
    }
}
