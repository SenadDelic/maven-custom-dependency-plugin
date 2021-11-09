package com.mind.benders;

import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
@Mojo(name = "dependency", defaultPhase = LifecyclePhase.INSTALL)
public class DependencyPlugin extends AbstractMojo {

    @Parameter(defaultValue = "${project}")
    private MavenProject mavenProject;

    @Parameter(property = "file")
    private File file;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        printHeaderAndPluginInformation();

        getListOfDependencies();

        readFIle();
    }

    private void readFIle() {
        if (file != null) {
            final String fileAbsolutePath = file.getAbsolutePath();
            log.info("fileAbsolutePath = {}", fileAbsolutePath);
            try (BufferedReader bufferedReader =
                         new BufferedReader(new BufferedReader(new FileReader(fileAbsolutePath)))) {
                String tempLine = "";

                while ((tempLine = bufferedReader.readLine()) != null) {
                    System.out.println(tempLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getListOfDependencies() {
        System.out.println("\nNumber of dependencies = " + getNumberOfDependencies() + "\nList of Dependencies =");
        listOfDependencies();
    }

    private void listOfDependencies() {
        mavenProject.getDependencies().forEach(System.out::println);
    }

    private Long getNumberOfDependencies() {
        return (long) mavenProject.getDependencies().size();
    }

    private void printHeaderAndPluginInformation() {
        System.out.println("******************************************************************************");
        System.out.println("*************************** DEPENDENCY_LIST_PLUGIN ***************************");
        System.out.println("******************************************************************************");

        printVersion();
    }

    private void printVersion() {
        System.out.println("Name= " + mavenProject.getName());
        System.out.println("Group id= " + mavenProject.getGroupId());
        System.out.println("Artifact ID= " + mavenProject.getArtifactId());
        System.out.println("Project version= " + mavenProject.getVersion());
        System.out.println("Project Description = " + mavenProject.getDescription());
    }
}
