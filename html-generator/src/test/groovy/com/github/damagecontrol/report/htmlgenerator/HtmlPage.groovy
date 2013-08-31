package com.github.damagecontrol.report.htmlgenerator

class HtmlPage {

    final html

    HtmlPage(contents) {
        html = new XmlParser().parseText(contents)
    }

    @SuppressWarnings('ThrowRuntimeException')
    def findElementById(id) {
        def nodes = html.depthFirst().findAll { it.'@id' == id }

        if (nodes.size() == 0) {
            throw new RuntimeException("No element found where id == '${id}'.")
        }

        nodes[0]
    }
}
