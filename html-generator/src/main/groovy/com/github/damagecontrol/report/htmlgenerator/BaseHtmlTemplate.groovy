package com.github.damagecontrol.report.htmlgenerator

import groovy.text.GStringTemplateEngine
import java.text.SimpleDateFormat

class BaseHtmlTemplate {

    private static final CONFIG_URL = getResource('/damage-control-config.groovy')
    private static final CONFIG = new ConfigSlurper().parse(CONFIG_URL)

    private static final BASE_URL = getResource('/com/github/damagecontrol/htmlreport/templates/base.html')
    private static final BASE_TEMPLATE = new GStringTemplateEngine().createTemplate(BASE_URL)

    @SuppressWarnings('SimpleDateFormatMissingLocale')
    def decorate(title, contents) {
        BASE_TEMPLATE.make(
            [
                title: title,
                contents: contents.toString(),
                currentVersion: CONFIG.version,
                timestamp: new SimpleDateFormat('yyyy-MM-dd HH:mm:ss Z').format(new Date())
            ]
        ).toString()
    }
}
