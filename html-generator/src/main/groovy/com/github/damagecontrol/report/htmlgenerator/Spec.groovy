package com.github.damagecontrol.report.htmlgenerator

class Spec {

    final output = new SpecOutput()
    final features = [:]

    def name
    def duration

    def passed(featureName) {
        features[featureName] = new PassedFeature(name: featureName)
    }

    def skipped(featureName) {
        features[featureName] = new SkippedFeature(name: featureName)
    }

    def failed(featureName) {
        features[featureName] = new FailedFeature(name: featureName)
    }

    @SuppressWarnings('CatchException')
    def parseEachFeatureDefinition(sourceCode) {
        String annotatedSourceCode = new LineNumberAnnotator().annotate(sourceCode)
        features.each {featureName, feature ->
            try {
                feature.parseDefinition(annotatedSourceCode)
                if (feature.failed) {
                    feature.identifyStepsResult(name)
                }
            } catch (Exception e) {
                throw new ParseFeatureDefinitionException(name, featureName, e)
            }
        }
    }

    def getFeatureCount() {
        features.size()
    }

    def getFailedFeatureCount() {
        int count = 0
        features.each { featureName, feature ->
            if (feature.failed) {
                count += 1
            }
        }
        count
    }

    def getSkippedFeatureCount() {
        int count = 0
        features.each { featureName, feature ->
            if (feature.ignored) {
                count += 1
            }
        }
        count
    }

    def getResult() {
        if (failedFeatureCount > 0) {
            return 'failed'
        }
        if (skippedFeatureCount == featureCount) {
            return 'skipped'
        }
        'passed'
    }

    int getSuccessPercentage() {
        def successfulFeatureCount = featureCount - failedFeatureCount
        float successPercentage = successfulFeatureCount/featureCount * 100
        successPercentage.round(0)
    }
}
