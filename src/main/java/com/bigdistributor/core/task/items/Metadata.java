package com.bigdistributor.core.task.items;

import com.bigdistributor.core.blockmanagement.blockinfo.BasicBlockInfo;
import com.bigdistributor.io.GsonIO;
import net.imglib2.Interval;
import net.preibisch.mvrecon.fiji.spimdata.boundingbox.BoundingBox;

import java.io.IOException;
import java.util.List;

public class Metadata {
    private Integer totalBlocks;
    private BoundingBox bb;
    private long[] blocksize;
    private List<BasicBlockInfo> blocksInfo;


    public Metadata(BoundingBox bb, long[] blocksize,
                    List<BasicBlockInfo> blocksInfo) {
        this(blocksInfo.size(), bb, blocksize, blocksInfo);
    }

    public Metadata(Integer totalBlocks, BoundingBox bb, long[] blocksize,
                    List<BasicBlockInfo> blocksInfo) {
        super();
        this.bb = bb;
        this.totalBlocks = totalBlocks;
        this.blocksize = blocksize;
        this.blocksInfo = blocksInfo;
    }

    public Interval getBb() {
        return bb;
    }

    public long[] getBlocksize() {
        return blocksize;
    }

    public List<BasicBlockInfo> getBlocksInfo() {
        return blocksInfo;
    }

    public Integer getTotalBlocks() {
        return totalBlocks;
    }

    public int size() {
        return blocksInfo.size();
    }

    @Override
    public String toString() {
        return GsonIO.toString(this);
    }

    public void toJson(String path) throws IOException {
        GsonIO.toJson(this, path);
    }


    public static Metadata fromJson(String path) throws Exception {
        return GsonIO.fromJson(path, Metadata.class);
    }
}