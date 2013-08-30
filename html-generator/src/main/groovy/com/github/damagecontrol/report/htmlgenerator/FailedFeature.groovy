package com.github.damagecontrol.report.htmlgenerator

import static com.github.damagecontrol.report.htmlgenerator.Results.NOT_PERFORMED
import static com.github.damagecontrol.report.htmlgenerator.Results.UNKNOWN
import static com.github.damagecontrol.report.htmlgenerator.Results.FAILED
import static com.github.damagecontrol.report.htmlgenerator.Results.PASSED

import java.util.regex.Pattern

class FailedFeature extends BaseFeature {

    final failed = true
    final ignored = false
    final result = FAILED

    def identifyStepsResult(specName) {
        def errorLine = errorLines(specName).find { startLineNumber <= it && it <= endLineNumber }

        if (errorLine) {
            steps.findAll { it.lineNumber > errorLine }.each { it.result = NOT_PERFORMED }
            steps.findAll { it.result == UNKNOWN && it.lineNumber < errorLine }.last().result = FAILED
            steps.findAll { it.result == UNKNOWN }.each { it.result = PASSED }
            moveDetailsFromFeatureToStep()
        }
    }

    private errorLines(specName) {
        def lines = []

        def shortName = specName.split('\\.').last()
        def match = details =~ ".*\\(${Pattern.quote(shortName)}\\.groovy\\:([0-9]+)\\)"
        match.each { lines.add(it[1].toInteger()) }

        lines
    }

    private moveDetailsFromFeatureToStep() {
        def failedStep = steps.find { it.result == FAILED }
        if (failedStep) {
            failedStep.details = details
            details = ''
        }
    }
}
