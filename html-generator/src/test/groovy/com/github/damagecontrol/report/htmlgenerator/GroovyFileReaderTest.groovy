package com.github.damagecontrol.report.htmlgenerator

import static org.apache.commons.io.FileUtils.copyFileToDirectory

class GroovyFileReaderTest extends BaseFileHandlingSpec {

    static final SAMPLE_FOLDER = 'src/test/resources'
    static final SPEC_DEFINITION = new File(SAMPLE_FOLDER + '/samples/definitions/SampleSpecDefinitionTest.groovy')

    GroovyFileReader groovyFileReader
    def specsDefinitionPackage

    def setup() {
        specsDefinitionPackage = new File(testFolder.absolutePath + '/samples/definitions')
        groovyFileReader = new GroovyFileReader(inputFolder: testFolder)
    }

    def 'should read file contents inside a package'() {
        given:
        copyFileToDirectory(SPEC_DEFINITION, specsDefinitionPackage)

        when:
        def fileContents = groovyFileReader.read('samples.definitions.SampleSpecDefinitionTest')

        then:
        fileContents =~ /class SampleSpecDefinitionTest/
    }

    def 'should read file contents inside the default package'() {
        given:
        copyFileToDirectory(SPEC_DEFINITION, testFolder)

        when:
        def fileContents = groovyFileReader.read('SampleSpecDefinitionTest')

        then:
        fileContents =~ /class SampleSpecDefinitionTest/
    }

    def 'should ignore missing definition'() {
        when:
        def fileContents = groovyFileReader.read('SampleJUnitThatShouldBeIgnoredTest')

        then:
        fileContents == ''
    }
}
