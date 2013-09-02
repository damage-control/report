package com.github.damagecontrol.report.htmlgenerator

import static com.github.damagecontrol.report.htmlgenerator.Results.FAILED
import static com.github.damagecontrol.report.htmlgenerator.Results.PASSED
import static com.github.damagecontrol.report.htmlgenerator.Results.SKIPPED
import static java.lang.Float.parseFloat

class FeaturesSummary {

    def features

    def getCount() {
        features.size()
    }

    def getFailedCount() {
        features.count { it.failed }
    }

    def getSkippedCount() {
        features.count { it.ignored }
    }

    def getResult() {
        if (failedCount > 0) {
            return FAILED
        }
        if (skippedCount == count) {
            return SKIPPED
        }
        PASSED
    }

    int getSuccessPercentage() {
        if (count == 0) {
            return 0
        }

        def successfulFeatureCount = count - failedCount
        float successPercentage = successfulFeatureCount / count * 100
        successPercentage.round(0)
    }

    def getDuration() {
        float duration = 0
        features.each { duration += parseDuration(it.duration) }
        duration
    }

    @SuppressWarnings('CatchException')
    private float parseDuration(duration) {
        try {
            parseFloat(duration)
        } catch (Exception e) {
            0
        }
    }
}
