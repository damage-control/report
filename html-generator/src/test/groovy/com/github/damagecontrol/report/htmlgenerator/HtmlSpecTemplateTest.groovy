package com.github.damagecontrol.report.htmlgenerator

import static com.github.damagecontrol.report.htmlgenerator.Results.FAILED
import static com.github.damagecontrol.report.htmlgenerator.Results.PASSED

@SuppressWarnings('LineLength')
class HtmlSpecTemplateTest extends BaseSpec {

    HtmlSpecTemplate template
    HtmlPage specHtml
    Spec spec

    def setup() {
        spec = new Spec(name: 'samples.definitions.SampleSpecTest')
        spec.output.standard = 'standard output message'
        spec.output.error = 'error output message'
        spec.duration = '0.355'

        spec.failed('feature 1')
        spec.features.'feature 1'.duration = '0.250'
        spec.features.'feature 1'.details = 'at SampleSpecificationTest.shouldFail(SampleSpecTest.groovy:14)'
        spec.features.'feature 1'.steps.add(new Step(type: 'expect', description: '"something"', result: FAILED, details: 'step failure details'))

        spec.passed('feature 2')
        spec.features.'feature 2'.steps.add(new Step(type: 'expect', description: '"something"', result: PASSED))

        spec.failed('feature 3')

        template = new HtmlSpecTemplate()
        specHtml =  new HtmlPage(template.generate(spec))
    }

    def 'should present standard output'() {
        expect:
        specHtml.findElementById('spec-standard-output').pre.text() == 'standard output message'
    }

    def 'should present error output'() {
        expect:
        specHtml.findElementById('spec-error-output').pre.text() == 'error output message'
    }

    def 'should be expandable if feature has steps or details'() {
        expect:
        specHtml.findElementById('feature 1').'@expand' == 'feature_0_steps'
    }

    def 'should not be expandable if feature does not have steps and do not have details'() {
        expect:
        specHtml.findElementById('feature 3').'@expand' == null
    }

    def 'should list all features'() {
        expect:
        specHtml.findElementById('feature 1').text() == 'feature 1'
        and:
        specHtml.findElementById('feature 2').text() == 'feature 2'
    }

    def 'should show result for feature'() {
        expect:
        specHtml.findElementById('feature 1_result').text() == 'failed'
        and:
        specHtml.findElementById('feature 1_result').'@class' == 'result_FAILED'
    }

    def 'should show spec duration'() {
        expect:
        specHtml.findElementById('feature 1_duration').text() == '0.250s'
    }

    def 'should show number of features'() {
        expect:
        specHtml.findElementById('featureCount').text() == '3'
    }

    def 'should show number of failed features'() {
        expect:
        specHtml.findElementById('failedFeatureCount').text() == '2'
    }

    def 'should show number of skipped features'() {
        expect:
        specHtml.findElementById('skippedFeatureCount').text() == '0'
    }

    def 'should show duration for specification'() {
        expect:
        specHtml.findElementById('specDuration').text() == '0.355s'
    }

    def 'should show success percentage'() {
        expect:
        specHtml.findElementById('successPercentage').text() == '33%'
    }

    def 'should show feature steps'() {
        expect:
        specHtml.findElementById('feature 1_steps').span.text() == 'failed'
        and:
        specHtml.findElementById('feature 2_steps').span.text() == 'passed'
    }

    def 'should show step failure details'() {
        expect:
        specHtml.findElementById('feature 1_steps').div.pre.text() == 'step failure details'
    }

    def 'should hide steps section if there is no step'() {
        expect:
        !specHtml.hasElementWithId('feature 3_steps')
    }

    def 'should show feature failure details'() {
        expect:
        specHtml.findElementById('feature 1_details').pre.text() == 'at SampleSpecificationTest.shouldFail(SampleSpecTest.groovy:14)'
    }

    def 'should hide feature failure details if there is no error details'() {
        expect:
        !specHtml.hasElementWithId('feature 3_details')
    }
}
