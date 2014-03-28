package com.github.damagecontrol.report.htmlgenerator

class TestResults {

    final specs = new TreeMap()

    def spec(specName) {
        specs[specName] = specs[specName] ?: new Spec(name: specName)
    }

    def getSummary() {
        def features = []
        specs.each { name, spec ->
            features.addAll(spec.features.values())
        }
        new FeaturesSummary(features: features)
    }
}
