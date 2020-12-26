package com.bigdistributor.controllers.blockmanagement.blockinfo;

import com.bigdistributor.biglogger.adapters.Log;

import java.lang.invoke.MethodHandles;
import java.util.Map;

public interface BlockInfoGenerator<T extends BasicBlockInfo> {

    final static long BLOCK_SIZE = 32;

    Log logger = Log.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    Map<Integer, T> divideIntoBlockInfo();

}
