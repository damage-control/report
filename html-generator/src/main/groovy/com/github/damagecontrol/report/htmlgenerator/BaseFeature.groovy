package com.github.damagecontrol.report.htmlgenerator

import static java.lang.Integer.parseInt

abstract class BaseFeature {

    final steps = []

    def name
    def duration
    def startLineNumber = 0
    def endLineNumber = 0
    def details

    private final featureDefinitionParser = new FeatureDefinitionParser()
    private final featureStepsParser = new StepDefinitionParser()

    def parseDefinition(lineNumberAnnotatedSourceCode) {
        String featureDefinition = featureDefinitionParser.parse(name, lineNumberAnnotatedSourceCode)
        setLineNumbers(featureDefinition)
        setSteps(featureDefinition)
    }

    def hasDetails() {
        !steps.isEmpty() || details
    }

    private setLineNumbers(featureDefinition) {
        def match = featureDefinition =~ /(?m)^#([0-9]*)#/
        if (match.count > 0) {
            startLineNumber = parseInt(match[0][1])
            endLineNumber = parseInt(match[match.count - 1][1])
        }
    }

    private setSteps(featureDefinition) {
        steps.clear()
        steps.addAll(featureStepsParser.parse(featureDefinition))
    }
}
