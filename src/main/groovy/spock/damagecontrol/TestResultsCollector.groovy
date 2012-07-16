package spock.damagecontrol

import static org.apache.commons.io.FileUtils.iterateFiles

class TestResultsCollector {

    static final boolean INCLUDE_SUB_FOLDERS = true
    static final String[] XML = ['xml']

    def resultsFolder

    @SuppressWarnings('CatchException')
    @SuppressWarnings('Println')
    def collect() {
        TestResults results = new TestResults()

        iterateFiles(resultsFolder, XML, INCLUDE_SUB_FOLDERS).each {file ->
            try {
                collectSpecs(file, results)
            } catch (Exception e) {
                println "Error reading file '${file}': ${e.message}"
            }
        }

        results
    }

    def collectSpecs(file, results) {
        Node testSuite = parse(file)

        def sysout = testSuite.'system-out' ? testSuite.'system-out'[0].text() : ''
        def syserr = testSuite.'system-err' ? testSuite.'system-err'[0].text() : ''
        
        SpecOutput output = new SpecOutput(sysout, syserr)

        testSuite.'testcase'.each { testCase ->
            Feature feature = results.addFeature testCase.'@classname', testCase.'@name', output
            feature.duration = testCase.'@time'

            if (testCase.skipped) {
                feature.ignore()
            }

            if (testCase.failure) {
                feature.fail testCase.failure[0].'@message', testCase.failure[0].text()
            }
        }

        testSuite.'ignored-testcase'.each { testCase ->
            Feature feature = results.addFeature testCase.'@classname', testCase.'@name', output
            feature.ignore()
        }

        results.specs[testSuite.'@name'].duration = testSuite.'@time'
    }

    def parse(file) {
        new XmlParser().parse(file)
    }
}
