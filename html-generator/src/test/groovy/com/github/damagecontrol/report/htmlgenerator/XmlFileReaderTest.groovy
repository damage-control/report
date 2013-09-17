package com.github.damagecontrol.report.htmlgenerator

import static org.apache.commons.io.FileUtils.copyFileToDirectory

import org.apache.commons.io.IOUtils

class XmlFileReaderTest extends BaseFileHandlingSpec {

    static final SAMPLES = 'src/test/resources/samples/results'

    static final XML_SUMMARY = new File("${SAMPLES}/summary.xml")
    static final XML_WITH_ONE_TEST_CASE = new File("${SAMPLES}/TEST-spock.damagecontrol.TestResultsParserTest.xml")
    static final XML_WITH_TWO_TEST_CASES = new File("${SAMPLES}/TEST-spock.damagecontrol.TestResultsWith2TestCases.xml")

    def xmlFileReader
    def testResultsFolders

    def setup() {
        testResultsFolders = [
            new File("${testFolder.absolutePath}/unit-tests-results"),
            new File("${testFolder.absolutePath}/integration-tests-results")
        ]

        testResultsFolders.each { it.mkdirs() }

        xmlFileReader = new XmlFileReader(inputFolders: testResultsFolders)
    }

    def 'should read all XML files in the folder'() {
        given:
        copyFileToDirectory(XML_WITH_ONE_TEST_CASE, testResultsFolders[0])
        copyFileToDirectory(XML_WITH_TWO_TEST_CASES, testResultsFolders[1])

        int count = 0

        when:
        xmlFileReader.forEach { count++ }

        then:
        count == 2
    }

    def 'should ignore non-result files in the folder'() {
        given:
        copyFileToDirectory(XML_SUMMARY, testResultsFolders[0])
        copyFileToDirectory(XML_WITH_ONE_TEST_CASE, testResultsFolders[0])

        int count = 0

        when:
        xmlFileReader.forEach { count++ }

        then:
        count == 1
    }

    def 'should provide reader for each XML file in the folder'() {
        given:
        copyFileToDirectory(XML_WITH_ONE_TEST_CASE, testResultsFolders[0])

        def lines

        when:
        xmlFileReader.forEach { lines = IOUtils.readLines(it) }

        then:
        lines[1] =~ /samples.results.TestResultsCollectorTest/
    }

    def 'should always close each reader after used'() {
        given:
        copyFileToDirectory(XML_WITH_ONE_TEST_CASE, testResultsFolders[0])

        when:
        def reader
        xmlFileReader.forEach {
            reader = it
            throw new IOException()
        }
        reader.read()

        then:
        thrown(IOException)
    }
}
