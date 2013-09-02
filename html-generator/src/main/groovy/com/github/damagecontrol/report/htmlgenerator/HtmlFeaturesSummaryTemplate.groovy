package com.github.damagecontrol.report.htmlgenerator

import groovy.text.GStringTemplateEngine

class HtmlFeaturesSummaryTemplate {

    private static final INDEX_URL = getResource('/com/github/damagecontrol/htmlreport/templates/features-summary.html')
    private static final TEMPLATE = new GStringTemplateEngine().createTemplate(INDEX_URL)

    def generate(summary) {
        TEMPLATE.make([summary: summary]).toString()
    }
}
