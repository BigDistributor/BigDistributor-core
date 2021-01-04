package com.bigdistributor.core.blockmanagement.blockinfo;

import com.bigdistributor.biglogger.adapters.Log;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;

public interface BlockInfoGenerator<T extends BasicBlockInfo> {

    final static long BLOCK_SIZE = 64;

    Log logger = Log.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    List<T> divideIntoBlockInfo();

    static long[] getBlockSizes(int dims){
        long[] blockSizes = new long[dims];
        Arrays.fill(blockSizes,BLOCK_SIZE);
        return blockSizes;
    }

}
