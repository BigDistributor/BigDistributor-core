package com.bigdistributor.io.mvn;

import org.apache.maven.shared.invoker.MavenInvocationException;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompileWorkflow {
    static List<String> dependenciesProjects = new ArrayList<>(Arrays.asList(
            "/Users/Marwan/Desktop/BigDistributer/all/BigDistributor-core",
            "/Users/Marwan/Desktop/BigDistributer/BigDistributor_AWS"));
    static final String mainProject = "/Users/Marwan/Desktop/BigDistributer/BigDistributor_ImplementedTasks/pom.xml";
static final String output = "/Users/Marwan/Desktop/TestOutput";
    public static void main(String[] args) throws MavenInvocationException, IOException, XmlPullParserException {
        new MavenCompiler(dependenciesProjects,mainProject,output).start();
        File jar = new JarLooker(output,mainProject).lookForMainJar();
        System.out.println(jar.getAbsolutePath());
    }
}
