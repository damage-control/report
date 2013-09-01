package com.github.damagecontrol.report.htmlgenerator

class TestResultsCollectorTest extends BaseSpec {

    TestResultsCollector collector

    def setup() {
        collector = new TestResultsCollector()
    }

    def 'should collect all features for the same specification'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
        <testsuite name="samples.results.AnotherTestResultsCollectorTest" time="0.005">
            <testcase classname="samples.results.AnotherTestResultsCollectorTest" name="shouldParseXml" time="0.002" />
            <testcase classname="samples.results.AnotherTestResultsCollectorTest" name="shouldFail" time="0.001" />
        </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        then:
        specs['samples.results.AnotherTestResultsCollectorTest'].features['shouldParseXml']
        specs['samples.results.AnotherTestResultsCollectorTest'].features['shouldFail']
    }

    def 'should collect duration for each feature'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="samples.results.AnotherTestResultsCollectorTest" time="0.005">
                <testcase classname="samples.results.AnotherTestResultsCollectorTest" name="shouldFail" time="0.001"/>
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        and:
        def feature = specs.'samples.results.AnotherTestResultsCollectorTest'.features.'shouldFail'

        then:
        feature.duration == '0.001'
    }

    def 'should collect feature name'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="samples.results.AnotherTestResultsCollectorTest" time="0.005">
                <testcase classname="samples.results.AnotherTestResultsCollectorTest" name="shouldFail" time="0.001"/>
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        then:
        specs['samples.results.AnotherTestResultsCollectorTest'].features['shouldFail'].name == 'shouldFail'
    }

    def 'should collect feature name with backslash'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
          <testsuite name="samples.results.AnotherTestResultsCollectorTest" time="0.005">
            <testcase classname="samples.results.AnotherTestResultsCollectorTest" name="Spock\\&apos;s" time="0.001"/>
          </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        then:
        specs['samples.results.AnotherTestResultsCollectorTest'].features["Spock's"].name == "Spock's"
    }

    def 'should collect specification duration time'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="samples.results.AnotherTestResultsCollectorTest" time="0.005">
                <testcase classname="samples.results.AnotherTestResultsCollectorTest" name="shouldFail" time="0.001"/>
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        then:
        specs['samples.results.AnotherTestResultsCollectorTest'].duration == '0.005'
    }

    def 'should collect ignored features'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="samples.results.TestResultsWithIgnoredTestCase" time="0.005">
                <ignored-testcase
                    classname="samples.results.TestResultsWithIgnoredTestCase"
                    name="ignored feature" time="0.0" />
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        and:
        def feature = specs.'samples.results.TestResultsWithIgnoredTestCase'.features.'ignored feature'

        then:
        feature.ignored
    }

    def 'should collect skipped features'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="samples.results.TestResultsWithIgnoredTestCase" time="0.005">
                <testcase
                    classname="samples.results.TestResultsWithIgnoredTestCase"
                    name="skipped feature"
                    time="0.126">
                    <skipped/>
                </testcase>
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        and:
        def feature = specs.'samples.results.TestResultsWithIgnoredTestCase'.features.'skipped feature'

        then:
        feature.ignored
    }

    def 'should collect failure details for each feature'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="samples.results.AnotherTestResultsCollectorTest" time="0.005">
                <testcase classname="samples.results.AnotherTestResultsCollectorTest" name="shouldFail" time="0.001">
                    <failure message="failure message">
                        <![CDATA[ TestResultsCollectorTest.shouldFail(TestResultsParserTest.groovy:19) ]]>
                    </failure>
                </testcase>
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        and:
        def feature = specs.'samples.results.AnotherTestResultsCollectorTest'.features.'shouldFail'

        then:
        feature.details == 'TestResultsCollectorTest.shouldFail(TestResultsParserTest.groovy:19)'
    }

    def 'should collect error details for each feature'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="samples.results.AnotherTestResultsCollectorTest" time="0.005">
                <testcase classname="samples.results.AnotherTestResultsCollectorTest" name="shouldFail" time="0.001">
                    <error message="failure message">
                        <![CDATA[ TestResultsCollectorTest.shouldFail(TestResultsParserTest.groovy:19) ]]>
                    </error>
                </testcase>
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        and:
        def feature = specs.'samples.results.AnotherTestResultsCollectorTest'.features.'shouldFail'

        then:
        feature.details == 'TestResultsCollectorTest.shouldFail(TestResultsParserTest.groovy:19)'
    }

    def 'should collect standard output for spec'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="samples.results.TestResultsWithSysOut" time="0.005">
                <testcase classname="samples.results.TestResultsWithSysOut" name="feature name" time="0.001"/>
                <system-out><![CDATA[ standard output message ]]></system-out>
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        then:
        specs['samples.results.TestResultsWithSysOut'].output.standard == 'standard output message'
    }

    def 'should collect results without standard output'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="samples.results.TestResultsWithoutSysOut" time="0.005">
                <testcase classname="samples.results.TestResultsWithoutSysOut" name="feature name" time="0.001"/>
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        then:
        specs['samples.results.TestResultsWithoutSysOut'].output.standard == ''
    }

    def 'should collect error output for spec'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="samples.results.TestResultsWithSysOut" time="0.005">
                <testcase classname="samples.results.TestResultsWithSysOut" name="feature name" time="0.001"/>
                <system-err><![CDATA[ error output message ]]></system-err>
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        then:
        specs['samples.results.TestResultsWithSysOut'].output.error == 'error output message'
    }

    def 'should collect results without error output'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="samples.results.TestResultsWithoutSysOut" time="0.005">
                <testcase classname="samples.results.TestResultsWithoutSysOut" name="feature name" time="0.001"/>
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        then:
        specs['samples.results.TestResultsWithoutSysOut'].output.error == ''
    }

    def 'should collect no feature when result file has no test cases'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="samples.results.NoTests" time="0.005">
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))

        then:
        collector.results.spec('samples.results.NoTests').features.size() == 0
    }
}
