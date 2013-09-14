package com.github.damagecontrol.report.htmlgenerator

class BaseHtmlTemplateTest extends BaseSpec {

    private static final CONFIG_URL = getResource('/damage-control-config.groovy')

    def page

    def setup() {
        def baseTemplate = new BaseHtmlTemplate()
        page = new HtmlPage(baseTemplate.decorate('page title', 'some contents'))
    }

    def 'should decorate page using provided title'() {
        expect:
        page.html.head.title.text() == 'page title - Damage Control Report'
    }

    def 'should decorate page using provided content'() {
        expect:
        page.findElementById('content').text() == 'some contents'
    }

    def 'should decorate page with current version defined in the configuration file'() {
        given:
        def config = new ConfigSlurper().parse(CONFIG_URL)

        expect:
        page.findElementById('footer').a.text() == "Damage Control Report ${config.version}"
    }
}
