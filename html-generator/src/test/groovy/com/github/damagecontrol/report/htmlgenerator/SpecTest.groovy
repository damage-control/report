package com.github.damagecontrol.report.htmlgenerator

import static com.github.damagecontrol.report.htmlgenerator.Results.FAILED
import static com.github.damagecontrol.report.htmlgenerator.Results.PASSED
import static com.github.damagecontrol.report.htmlgenerator.Results.SKIPPED

class SpecTest extends BaseSpec {

    private final sourceCode = '''package samples
class SampleSpecificationTest {
    def 'some feature'() {
        expect: 'some block'
        some.code()
    }
}
'''

    private Spec spec

    def setup() {
        spec = new Spec(name: 'samples.SampleSpecificationTest')
    }

    def 'should create new passed feature'() {
        given:
        spec.passed('feature name')

        expect:
        spec.features.'feature name'.result == PASSED
    }

    def 'should create new skipped feature'() {
        given:
        spec.skipped('feature name')

        expect:
        spec.features.'feature name'.result == SKIPPED
    }

    def 'should create new failed feature'() {
        given:
        spec.failed('feature name')

        expect:
        spec.features.'feature name'.result == FAILED
    }

    def 'should have output by default'() {
        expect:
        spec.output
    }

    def 'should summarize features'() {
        given:
        spec.passed('feature name')

        expect:
        spec.summary.count == 1
    }

    def 'should parse definition for all features'() {
        given:
        spec.passed('some feature')

        when:
        spec.parseEachFeatureDefinition(sourceCode)

        then:
        spec.features.'some feature'.steps[0].type == 'expect'
        and:
        spec.features.'some feature'.steps[0].lineNumber == 4
    }

    def 'should identify steps result for failed features'() {
        given:
        def feature = spec.failed('some feature')
        feature.details = 'at samples.SampleSpecificationTest.some feature(SampleSpecificationTest.groovy:5)'

        when:
        spec.parseEachFeatureDefinition(sourceCode)

        then:
        spec.features.'some feature'.steps[0].result == FAILED
    }

    def 'should handle missing source code'() {
        given:
        def feature = spec.failed('some feature')
        feature.details = 'at samples.SampleSpecificationTest.some feature(SampleSpecificationTest.groovy:5)'

        when:
        spec.parseEachFeatureDefinition('')

        then:
        spec.features.'some feature'.steps.size() == 0
    }
}
