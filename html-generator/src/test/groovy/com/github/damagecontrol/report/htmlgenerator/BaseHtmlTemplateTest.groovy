package com.github.damagecontrol.report.htmlgenerator

class BaseHtmlTemplateTest extends BaseSpec {

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
}
