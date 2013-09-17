package com.github.damagecontrol.report.plugins.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import com.github.damagecontrol.report.htmlgenerator.Report;

import java.io.File;
import java.util.List;

/**
 * @goal report
 */
public class DamageControlMojo extends AbstractMojo {

    /**
     * @parameter default-value="target/surefire-reports"
     */
    private File testResultsFolder;

    /**
     * @parameter
     */
    private List<File> testResultsFolders;

    /**
     * @parameter default-value="src/test/groovy"
     */
    private File specDefinitionsFolder;

    /**
     * @parameter default-value="target/damage-control-reports"
     */
    private File outputFolder;

    /**
     * @parameter default-value="false"
     */
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
