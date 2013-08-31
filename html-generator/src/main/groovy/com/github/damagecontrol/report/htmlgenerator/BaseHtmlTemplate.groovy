package com.github.damagecontrol.report.htmlgenerator
import groovy.text.GStringTemplateEngine

import java.text.SimpleDateFormat

class BaseHtmlTemplate {

    private static final BASE_URL = getResource('/com/github/damagecontrol/htmlreport/templates/base.html')
    private static final BASE_TEMPLATE = new GStringTemplateEngine().createTemplate(BASE_URL)

    @SuppressWarnings('SimpleDateFormatMissingLocale')
    def decorate(contents) {
        BASE_TEMPLATE.make(
            [
                contents: contents.toString(),
                timestamp: new SimpleDateFormat('yyyy-MM-dd HH:mm:ss Z').format(new Date())
            ]
        ).toString()
    }
}
