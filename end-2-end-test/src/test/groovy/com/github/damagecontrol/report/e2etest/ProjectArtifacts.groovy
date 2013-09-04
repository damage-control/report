package com.github.damagecontrol.report.e2etest

class ProjectArtifacts {

    def rootDirectory

    def has(artifact) {
        new File("${rootDirectory.absolutePath}/${artifact}").exists()
    }

    def doesNotHave(artifact) {
        !has(artifact)
    }
}
