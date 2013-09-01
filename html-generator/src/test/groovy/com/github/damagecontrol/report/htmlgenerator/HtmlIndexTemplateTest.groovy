package com.github.damagecontrol.report.htmlgenerator

class HtmlIndexTemplateTest extends BaseSpec {

    HtmlIndexTemplate indexTemplate
    HtmlPage indexHtml
    def specs

    def setup() {
        given:
        Spec spec1 = new Spec(name: 'Spec1')
        spec1.duration = '0.155'
        spec1.passed('feature 1')
        spec1.failed('feature 2')
        spec1.features.'feature 2'.details = 'error detail'
        spec1.skipped('feature 3')

        Spec spec2 = new Spec(name: 'Spec2')

        specs = [spec1, spec2]

        indexTemplate = new HtmlIndexTemplate()
        indexHtml =  new HtmlPage(indexTemplate.generate(specs))
    }

    def 'should have page title'() {
        expect:
        indexHtml.html.head.title.text() == 'Specifications - Damage Control Report'
    }

    def 'should generate list of links to spec details'() {
        expect:
        indexHtml.findElementById('Spec1').a.'@href'[0] == 'Spec1.html'
        and:
        indexHtml.findElementById('Spec1').a.text() == 'Spec1'

        and:
        indexHtml.findElementById('Spec2').a.'@href'[0] == 'Spec2.html'
        and:
        indexHtml.findElementById('Spec2').a.text() == 'Spec2'
    }

    def 'should show number of features for each specification'() {
        expect:
        indexHtml.findElementById('Spec1_featureCount').text() == '3'
    }

    def 'should show number of failed features for each specification'() {
        expect:
        indexHtml.findElementById('Spec1_failedFeatureCount').text() == '1'
    }

    def 'should show number of skipped features for each specification'() {
        expect:
        indexHtml.findElementById('Spec1_skippedFeatureCount').text() == '1'
    }

    def 'should show result for each specification'() {
        expect:
        indexHtml.findElementById('Spec1_result').text() == 'failed'
    }

    def 'should show duration for each specification'() {
        expect:
        indexHtml.findElementById('Spec1_duration').text() == '0.155s'
    }
}
