package com.bigdistributor.core.metadata;

import com.bigdistributor.core.blockmanagement.BoundingBoxManager;
import com.bigdistributor.core.blockmanagement.blockinfo.BasicBlockInfoGenerator;
import com.bigdistributor.core.blockmanagement.blockinfo.BlockInfoGenerator;
import com.bigdistributor.core.spim.SpimDataLoader;
import com.bigdistributor.core.task.items.Metadata;
import com.bigdistributor.io.GsonIO;
import net.preibisch.mvrecon.fiji.spimdata.boundingbox.BoundingBox;

import java.io.File;

public class MetadataGenerator {

    private final SpimDataLoader loader;
    private BoundingBox bb;
    private Metadata md;

    public MetadataGenerator(SpimDataLoader loader) {
        this.loader = loader;
    }

    public void setBb(BoundingBox bb) {
        this.bb = bb;
    }

    public MetadataGenerator generate() {
        if (bb == null) {
            BoundingBoxManager manager = new BoundingBoxManager(loader.getSpimdata());
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
