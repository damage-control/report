package com.github.damagecontrol.report.htmlgenerator

import static org.apache.commons.io.FileUtils.copyFileToDirectory

class ReportTest extends BaseFileHandlingSpec {

    static final SAMPLES = 'src/test/resources/samples'
    static final SPEC_DEFINITION = new File(SAMPLES + '/definitions/SampleSpecDefinitionTest.groovy')
    static final SPEC_RESULT = new File(SAMPLES + '/results/TEST-samples.definitions.SampleSpecDefinitionTest.xml')

    def specsDefinitionPackage
    def report
    def outputFolder

    def setup() {
        specsDefinitionPackage = new File("${testFolder.absolutePath}/samples/definitions")
        outputFolder = new File("${testFolder.absolutePath}/report")

        report = new Report(
            testResultsFolder: testFolder,
            specDefinitionsFolder: testFolder,
            outputFolder: outputFolder
        )
    }

    def 'should copy resources'() {
        given:
        copyFileToDirectory(SPEC_DEFINITION, specsDefinitionPackage)
        copyFileToDirectory(SPEC_RESULT, testFolder)

        when:
        report.run()

        then:
        new File("${outputFolder.absolutePath}/style/damage-control.css").exists()
        and:
        new File("${outputFolder.absolutePath}/js/jquery.min.js").exists()
        and:
        new File("${outputFolder.absolutePath}/js/damage-control.js").exists()
    }

    def 'should create index file'() {
        when:
        report.run()

        then:
        new File("${outputFolder.absolutePath}/index.html").exists()
    }

    def 'should create index file when folder list is provided'() {
        given:
        report = new Report(
            testResultsFolders: [testFolder],
            specDefinitionsFolders: [testFolder],
            outputFolder: outputFolder
        )

        when:
        report.run()

        then:
        new File("${outputFolder.absolutePath}/index.html").exists()
    }

    def 'should create html file for each spec'() {
        given:
        copyFileToDirectory(SPEC_DEFINITION, specsDefinitionPackage)
        copyFileToDirectory(SPEC_RESULT, testFolder)

        when:
        report.run()

        then:
        new File("${outputFolder.absolutePath}/samples.definitions.SampleSpecDefinitionTest.html").exists()
    }
}
