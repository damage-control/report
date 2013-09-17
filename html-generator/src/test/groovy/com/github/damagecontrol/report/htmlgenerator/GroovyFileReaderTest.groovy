package com.github.damagecontrol.report.htmlgenerator

import static org.apache.commons.io.FileUtils.copyFileToDirectory

class GroovyFileReaderTest extends BaseFileHandlingSpec {

    static final SAMPLE_FOLDER = 'src/test/resources'
    static final SPEC_DEFINITION = new File("${SAMPLE_FOLDER}/samples/definitions/SampleSpecDefinitionTest.groovy")

    GroovyFileReader groovyFileReader
    def specsDefinitionPackages
    def specDefinitionsFolders

    def setup() {
        specDefinitionsFolders = [
            new File("${testFolder.absolutePath}/unit-tests"),
            new File("${testFolder.absolutePath}/integration-tests")
        ]
        specDefinitionsFolders.each { it.mkdirs() }

        specsDefinitionPackages = [
            new File("${specDefinitionsFolders[0].absolutePath}/samples/definitions0"),
            new File("${specDefinitionsFolders[1].absolutePath}/samples/definitions1")
        ]

        groovyFileReader = new GroovyFileReader(inputFolders: specDefinitionsFolders)
    }

    def 'should read file contents inside a package'() {
        given:
        copyFileToDirectory(SPEC_DEFINITION, specsDefinitionPackages[0])
        copyFileToDirectory(SPEC_DEFINITION, specsDefinitionPackages[1])

        expect:
        groovyFileReader.read(className) =~ fileContents

        where:
        className                                       | fileContents
        'samples.definitions0.SampleSpecDefinitionTest' | /class SampleSpecDefinitionTest/
        'samples.definitions1.SampleSpecDefinitionTest' | /class SampleSpecDefinitionTest/
    }

    def 'should read file contents inside the default package'() {
        given:
        copyFileToDirectory(SPEC_DEFINITION, specDefinitionsFolders[0])

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
