package com.bigdistributor.core.data;

import org.janelia.saalfeldlab.n5.N5Reader;
import org.janelia.saalfeldlab.n5.N5Writer;

import java.io.IOException;

public interface OutputData {
    public N5Reader getReader() throws IOException;

    public N5Writer getWriter() throws IOException;
}
