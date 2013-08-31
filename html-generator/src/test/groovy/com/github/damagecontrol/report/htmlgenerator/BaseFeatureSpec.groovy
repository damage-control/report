package com.github.damagecontrol.report.htmlgenerator

import spock.lang.Shared

abstract class BaseFeatureSpec extends BaseSpec {

    final featureName = 'feature name'

    @Shared
    def feature

    def lineAnnotatedSourceCode = '''#1#
#2#class Spec1 {
#3#    def 'feature name'() {
#4#        expect: 'some block'
#5#        // some blocks
#6#    }
#7#}
'''
    def lineAnnotatedSourceCodeThatDoesNotMatch = '''#1#
#2#class Spec1 {
#3#    def 'feature name that does not match'() {
#4#        expect: 'some block'
#5#        // some blocks
#6#    }
#7#}
'''

    def 'should have a name'() {
        expect:
        feature.name == featureName
    }

    def 'should not have details by default'() {
        expect:
        feature.steps == []
        and:
        feature.details == null
        and:
        !feature.hasDetails()
    }

    def 'should indicate presence of details if there is at least one step'() {
        given:
        feature.steps[0] = new Step(lineNumber: 10)

        expect:
        feature.hasDetails()
    }

    def 'should indicate presence of details if there is error details'() {
        given:
        feature.details = 'error details'

        expect:
        feature.hasDetails()
    }

    def 'should parse feature definition'() {
        when:
        feature.parseDefinition(lineAnnotatedSourceCode)

        then:
        feature.steps[0].type == 'expect'
    }

    def 'should have start line number'() {
        when:
        feature.parseDefinition(lineAnnotatedSourceCode)

        then:
        feature.startLineNumber == 4
    }

    def 'should have end line number'() {
        when:
        feature.parseDefinition(lineAnnotatedSourceCode)

        then:
        feature.endLineNumber == 6
    }

    def 'should not have start line number if feature definition can not be found'() {
        when:
        feature.parseDefinition(lineAnnotatedSourceCodeThatDoesNotMatch)

        then:
        feature.startLineNumber == 0
    }

    def 'should not have end line number if feature definition can not be found'() {
        when:
        feature.parseDefinition(lineAnnotatedSourceCodeThatDoesNotMatch)

        then:
        feature.endLineNumber == 0
    }
}
