package com.bigdistributor.io.mvn;

import com.bigdistributor.biglogger.adapters.Log;
import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class MavenCompiler {
    private static final Log logger = Log.getLogger(MavenCompiler.class.getName());

    private final String outputPath;
    private final List<String> dependencies;
    private final String mainProject;

    public MavenCompiler(List<String> dependencies, String mainProject, String outputPath) {
        this.dependencies = dependencies;
        this.mainProject = mainProject;
        this.outputPath = outputPath;
    }

    public int start() throws MavenInvocationException {
        File outputFile = new File(outputPath);
        if (!outputFile.exists()) {
            outputFile.mkdirs();
        }
        Invoker invoker = getInvoker(outputFile);

        invoker.setOutputHandler(s -> logger.info(s));
        invoker.setErrorHandler(s -> logger.error(s));
        for (String dep : dependencies) {

            File pomFile = (new File(dep).isDirectory()) ? new File(dep, "pom.xml") : new File(dep);
            compile(invoker, pomFile);
        }
        InvocationResult result = compile(invoker, new File(mainProject));
        return result.getExitCode();
    }


    private InvocationResult compile(Invoker invoker, File file) throws MavenInvocationException {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(file);
        request.setGoals(Collections.singletonList("install"));
        return invoker.execute(request);
    }

    private Invoker getInvoker(File outputFolder) throws MavenInvocationException {
        Invoker invoker = new DefaultInvoker();

        invoker.setLocalRepositoryDirectory(outputFolder);

        String mavenHome = System.getenv("M3_HOME");
        if (mavenHome == null) {
            logger.error("$M3_HOME is null, init it before");
            throw new MavenInvocationException("$M3_HOME is null, init it before");
        }
        invoker.setMavenHome(new File(mavenHome));
        return invoker;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public String getMainProject() {
        return mainProject;
    }
}
