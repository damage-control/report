package com.github.damagecontrol.report.htmlgenerator

class HtmlFeaturesSummaryTemplateTest extends BaseSpec {

    HtmlPage summaryHtml

    def setup() {
        Spec spec = new Spec(name: 'SampleSpec')
        spec.passed('Feature 1').duration = '0.2'
        spec.failed('Feature 2').duration = '0.525'
        spec.failed('Feature 3').duration = '0.375'

        FeaturesSummary summary = new FeaturesSummary(features: spec.features.values())

        HtmlFeaturesSummaryTemplate template = new HtmlFeaturesSummaryTemplate()
        summaryHtml = new HtmlPage(template.generate(summary))
    }

    def 'should show number of features'() {
        expect:
        summaryHtml.findElementById('featureCount').text() == '3'
    }

    def 'should show number of failed features'() {
        expect:
        summaryHtml.findElementById('failedFeatureCount').text() == '2'
    }

    def 'should show number of skipped features'() {
        expect:
        summaryHtml.findElementById('skippedFeatureCount').text() == '0'
    }

    def 'should show duration for specification'() {
        expect:
        summaryHtml.findElementById('specDuration').text() == '1.0999999s'
    }

    def 'should show success percentage'() {
        expect:
        summaryHtml.findElementById('successPercentage').text() == '33%'
    }
}
