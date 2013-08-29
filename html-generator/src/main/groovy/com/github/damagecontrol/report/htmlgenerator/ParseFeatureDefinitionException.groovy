package com.github.damagecontrol.report.htmlgenerator

class ParseFeatureDefinitionException extends RuntimeException {
    ParseFeatureDefinitionException(spec, feature, Throwable cause) {
        super("Failed to parse '${feature}' from '${spec}'.", cause)
    }
}
