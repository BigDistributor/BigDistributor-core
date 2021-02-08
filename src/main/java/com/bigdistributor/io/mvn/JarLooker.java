package com.bigdistributor.io.mvn;

import com.bigdistributor.biglogger.adapters.Log;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class JarLooker {
    private static final Log logger = Log.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private String project;
    private String version ;
    private String folder ;

    public JarLooker(String folder, String project, String version) {
        this.project = project;
        this.version = version;
        this.folder = folder;
    }

    public JarLooker(String folder, String pomFile) throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader(pomFile));
        this.project = model.getArtifactId();
        this.version = model.getVersion();
        this.folder = folder;
    }

    public File lookForMainJar() {
        File maven = new File(folder);
        File f = getProjectFolder(maven, project);
        logger.info("Project Folder: "+f.getAbsolutePath());
        f = new File(f, version);
        if (f.exists()) {
            File jar = getJar(f);
            return jar;
        }
        return null;
    }

    private File getJar(File folder) {
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".jar"));
        long maxSize = 0;
        int index = 0;
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.length() > maxSize) {
                maxSize = f.length();
                index = i;
            }
        }
        if (maxSize > 0)
            return files[index];
        else
            return null;
    }

    private File getProjectFolder(File folder, String project) {
        for (File f : folder.listFiles()) {
            if (f.isDirectory())
                if (f.getName().equalsIgnoreCase(project))
                    return f;
                else {
                    File newFile = getProjectFolder(f, project);
                    if (newFile != null)
                        return newFile;
                }
        }
        return null;
    }

    public static void main(String[] args) {
        String project = "bigdistributor_tasks";
        String version = "0.2-SNAPSHOT";
        String folder = "/Users/Marwan/Desktop/TestOutput";
        File jar = new JarLooker(folder, project, version).lookForMainJar();
        System.out.println(jar.getAbsolutePath());
    }
}
