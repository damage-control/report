package com.github.damagecontrol.report.htmlgenerator

import static com.github.damagecontrol.report.htmlgenerator.Results.FAILED
import static com.github.damagecontrol.report.htmlgenerator.Results.PASSED
import static com.github.damagecontrol.report.htmlgenerator.Results.SKIPPED

class FeaturesSummaryTest extends BaseSpec {

    def summary
    def spec

    def setup() {
        spec = new Spec(name: 'SampleSpec')
    }

    def 'should count number of features'() {
        given:
        spec.passed('feature name')

        when:
        summary = new FeaturesSummary(features: spec.features.values())

        then:
        summary.count == 1
    }

    def 'should count number of failed features'() {
        given:
        spec.failed('failed feature')

        when:
        summary = new FeaturesSummary(features: spec.features.values())

        then:
        summary.failedCount == 1
    }

    def 'should count number of skipped features'() {
        given:
        spec.skipped('feature name')

        when:
        summary = new FeaturesSummary(features: spec.features.values())

        then:
        summary.skippedCount == 1
    }

    def 'should show "failed" for specification with at least 1 failed feature'() {
        given:
        spec.passed('feature 1')
        spec.failed('feature 2')

        when:
        summary = new FeaturesSummary(features: spec.features.values())

        then:
        summary.result == FAILED
    }

    def 'should show "skipped" for specification with all skipped features'() {
        given:
        spec.skipped('feature name')

        when:
        summary = new FeaturesSummary(features: spec.features.values())

        then:
        summary.result == SKIPPED
    }

    def 'should show "passed" for specification with no failures and at least 1 success feature'() {
        given:
        spec.passed('feature 1')

        when:
        summary = new FeaturesSummary(features: spec.features.values())

        then:
        summary.result == PASSED
    }

    def 'should indicate 100% successful features for no failures'() {
        given:
        spec.passed('feature 1')

        when:
        summary = new FeaturesSummary(features: spec.features.values())

        then:
        summary.successPercentage == 100
    }

    def 'should indicate percentage of successful features'() {
        given:
        spec.passed('passed feature')
        spec.failed('a failed feature')
        spec.failed('another failed feature')

        when:
        summary = new FeaturesSummary(features: spec.features.values())

        then:
        summary.successPercentage == 33
    }

    def 'should indicate 0% successful features for all features failed'() {
        given:
        spec.failed('a failed feature')

        when:
        summary = new FeaturesSummary(features: spec.features.values())

        then:
        summary.successPercentage == 0
    }

    def 'should indicate 0% successful features if there is no feature'() {
        when:
        summary = new FeaturesSummary(features: [])

        then:
        summary.successPercentage == 0
    }

    def 'should summarize duration of all features'() {
        given:
        spec.passed('feature 1').duration = '0.725'
        spec.passed('feature 2').duration = '0.270'
        spec.passed('feature 3').duration = ''
        spec.passed('feature 4').duration = 'unexpected value'

        when:
        summary = new FeaturesSummary(features: spec.features.values())

        then:
        summary.duration == 0.995f
    }
}
