package com.github.damagecontrol.report.plugins.maven;

import com.github.damagecontrol.report.htmlgenerator.Report;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.List;

@Mojo(
    name = "report",
    requiresProject = true
)
public class DamageControlMojo extends AbstractMojo {

    @Parameter(defaultValue = "target/surefire-reports")
    private File testResultsFolder;

    @Parameter
    private List<File> testResultsFolders;

    @Parameter(defaultValue = "src/test/groovy")
    private File specDefinitionsFolder;

    @Parameter(defaultValue = "target/damage-control-reports")
    private File outputFolder;

    @Parameter(defaultValue = "false")
    private boolean skip;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skipTests()) {
            getLog().info("Damage Control, Report: skipped.");
            return;
        }

        getLog().info("Damage Control, Report:");
        getLog().info("test results folder=" + testResultsFolder);
        getLog().info("test results folder list=" + testResultsFolders);
        getLog().info("specs definition folder=" + specDefinitionsFolder);
        getLog().info("reports target folder=" + outputFolder);

        Report report = new Report();
        report.setTestResultsFolder(testResultsFolder);
        report.setTestResultsFolders(testResultsFolders);
        report.setSpecDefinitionsFolder(specDefinitionsFolder);
        report.setOutputFolder(outputFolder);
        report.run();
    }

    private boolean skipTests() {
        return skip || System.getProperty("skipTests") != null;
    }
}
