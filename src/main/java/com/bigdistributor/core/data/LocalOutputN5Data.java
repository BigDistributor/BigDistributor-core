package com.bigdistributor.core.data;

import org.janelia.saalfeldlab.n5.N5FSReader;
import org.janelia.saalfeldlab.n5.N5FSWriter;
import org.janelia.saalfeldlab.n5.N5Reader;
import org.janelia.saalfeldlab.n5.N5Writer;

import java.io.IOException;

public class LocalOutputN5Data implements OutputData {
    private final String path;
    private final String dataset;

    public LocalOutputN5Data(String path, String dataset) {
        this.path = path;
        this.dataset = dataset;
    }

    @Override
    public N5Reader getReader() throws IOException {
        return new N5FSReader(path);
    }

    @Override
    public N5Writer getWriter() throws IOException {
        return new N5FSWriter(path);
    }
}
