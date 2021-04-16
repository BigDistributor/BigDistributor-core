package com.bigdistributor.core.metadata;

import com.bigdistributor.core.blockmanagement.BoundingBoxManager;
import com.bigdistributor.core.blockmanagement.blockinfo.BasicBlockInfoGenerator;
import com.bigdistributor.core.blockmanagement.blockinfo.BlockInfoGenerator;
import com.bigdistributor.core.spim.SpimDataLoader;
import com.bigdistributor.core.task.items.Metadata;
import com.bigdistributor.io.GsonIO;
import net.preibisch.mvrecon.fiji.spimdata.SpimData2;
import net.preibisch.mvrecon.fiji.spimdata.boundingbox.BoundingBox;

import java.io.File;

public class MetadataGenerator {

//    private final SpimDataLoader loader;
    private SpimData2 spimdata;
    private BoundingBox bb;
    private Metadata md;

    @Deprecated
    public MetadataGenerator(SpimDataLoader loader) {
        this.spimdata = loader.getSpimdata();
    }

    public MetadataGenerator(SpimData2 spimdata) {
        this.spimdata = spimdata;
    }

    public void setBb(BoundingBox bb) {
        this.bb = bb;
    }

    public MetadataGenerator generate() {
        if (bb == null) {
            BoundingBoxManager manager = new BoundingBoxManager(spimdata);
            bb = manager.getMax();
        }

        long[] blockSize = BlockInfoGenerator.getBlockSizes(bb.numDimensions());
        BasicBlockInfoGenerator generator = new BasicBlockInfoGenerator(bb, blockSize);

        this.md = new Metadata(bb, blockSize, generator.divideIntoBlockInfo());
        return this;
    }

    public void save(File f) {
        if (md == null) {
            generate();
        }
        GsonIO.toJson(md, f);
        reset();
    }

    public Metadata getMetadata() {
        return md;
    }

    private void reset() {
        md = null;
        bb = null;
    }
}
