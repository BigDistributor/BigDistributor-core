package com.bigdistributor.controllers.blockmanagement.blockinfo;

import net.imglib2.Interval;
import net.imglib2.iterator.LocalizingZeroMinIntervalIterator;
import net.imglib2.util.Util;

import java.util.ArrayList;
import java.util.List;

public class BasicBlockInfoGenerator implements BlockInfoGenerator<BasicBlockInfo> {

    private final long[] maxs;
    private final long[] blockSize;
    private final long[] mins;

    public BasicBlockInfoGenerator(Interval interval, long[] blockSize) {
        this(getMins(interval), getMaxs(interval), blockSize);
    }

    public BasicBlockInfoGenerator(long[] mins, long[] maxs, long[] blockSize) {
        this.mins = mins;
        this.maxs = maxs;
        this.blockSize = blockSize;
    }


    public BasicBlockInfoGenerator(long[] sizes, long[] blockSize) {
        this(new long[sizes.length], sizes, blockSize);
    }

    private static long[] getMins(Interval interval) {
        long[] mins = new long[interval.numDimensions()];
        for (int i = 0; i < interval.numDimensions(); i++) {
            mins[i] = interval.min(i);
        }
        return mins;
    }

    private static long[] getMaxs(Interval interval) {
        long[] maxs = new long[interval.numDimensions()];
        for (int i = 0; i < interval.numDimensions(); i++) {
            maxs[i] = interval.max(i);
        }
        return maxs;
    }

    public List<BasicBlockInfo> divideIntoBlockInfo() {

        final int numDimensions = maxs.length;

        // compute the amount of blocks needed
        final long[] numBlocks = new long[numDimensions];


        for (int d = 0; d < numDimensions; ++d) {
            long size = maxs[d] - mins[d];
            numBlocks[d] = size / blockSize[d];

            // if the modulo is not 0 we need one more that is only partially useful
            if (size % blockSize[d] != 0)
                ++numBlocks[d];
        }

        logger.info("Img dims: " + Util.printCoordinates(mins) + "  -> " + Util.printCoordinates(maxs));
        logger.info("blockSize " + Util.printCoordinates(blockSize));
        logger.info("numBlocks " + Util.printCoordinates(numBlocks));

        // now we instantiate the individual blocks iterating over all dimensions
        // we use the well-known ArrayLocalizableCursor for that
        final LocalizingZeroMinIntervalIterator cursor = new LocalizingZeroMinIntervalIterator(numBlocks);
        final List<BasicBlockInfo> blockinfosList = new ArrayList<BasicBlockInfo>();

        final int[] currentBlock = new int[numDimensions];
        int i = 0;
        while (cursor.hasNext()) {
            cursor.fwd();
            cursor.localize(currentBlock);
            final long[] gridOffset = Util.int2long(currentBlock);

            // compute the current offset
            //final long[] offset = new long[numDimensions];
            final long[] min = new long[numDimensions];
            final long[] max = new long[numDimensions];
            final long[] effectiveBlockSize = blockSize.clone();

            for (int d = 0; d < numDimensions; d++) {
                long size = maxs[d] - mins[d];
                min[d] = currentBlock[d] * blockSize[d] + mins[d];

                if (min[d] + blockSize[d] > size)
                    effectiveBlockSize[d] = size - min[d];

                max[d] = min[d] + effectiveBlockSize[d] - 1;
            }

            blockinfosList.add(new BasicBlockInfo(gridOffset, blockSize, effectiveBlockSize, min, max));
            i++;
        }

        return blockinfosList;
    }
}
