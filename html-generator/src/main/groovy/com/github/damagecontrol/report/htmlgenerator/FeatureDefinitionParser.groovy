package com.github.damagecontrol.report.htmlgenerator

import java.util.regex.Pattern

class FeatureDefinitionParser {

    def parse(name, sourceCode) {
        def match = sourceCode =~ "(?s)def\\s?('|\")${Pattern.quote(name)}('|\")\\s*\\(\\s*\\)\\s*\\{(.*)"
        if (match.size() == 0) {
            return ''
        }

        StringBuilder featureSourceCode = new StringBuilder()
        String partialSourceCode = match[0][3]
        int curlyBracketsToClose = 1

        for (int i = 0; i < partialSourceCode.length() && curlyBracketsToClose > 0; i++) {
            char c = partialSourceCode.charAt(i)
            if (c == '}') {
                curlyBracketsToClose--
            } else if (c == '{') {
                curlyBracketsToClose++
            } else {
                featureSourceCode.append(c)
            }
        }

        featureSourceCode.toString()
    }
}
