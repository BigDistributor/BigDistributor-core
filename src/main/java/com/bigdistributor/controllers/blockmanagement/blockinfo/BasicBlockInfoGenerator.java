package com.bigdistributor.controllers.blockmanagement.blockinfo;

import net.imglib2.iterator.LocalizingZeroMinIntervalIterator;
import net.imglib2.util.Util;

import java.util.HashMap;
import java.util.Map;

public class BasicBlockInfoGenerator  implements BlockInfoGenerator<BasicBlockInfo>{

	final long[] imgSize;
	final long[] blockSize;

	public BasicBlockInfoGenerator(long[] imgSize, long[] blockSize) {
		this.imgSize = imgSize;
		this.blockSize = blockSize;
	}

	public Map<Integer, BasicBlockInfo> divideIntoBlockInfo( ) {
		
		final int numDimensions = imgSize.length;

		// compute the amount of blocks needed
		final long[] numBlocks = new long[numDimensions];

		
		for (int d = 0; d < numDimensions; ++d) {
			numBlocks[d] = imgSize[d] / blockSize[d];

			// if the modulo is not 0 we need one more that is only partially useful
			if (imgSize[d] % blockSize[d] != 0)
				++numBlocks[d];
		}

		logger.info("imgSize " + Util.printCoordinates(imgSize));
		logger.info("blockSize " + Util.printCoordinates(blockSize));
		logger.info("numBlocks " + Util.printCoordinates(numBlocks));

		// now we instantiate the individual blocks iterating over all dimensions
		// we use the well-known ArrayLocalizableCursor for that
		final LocalizingZeroMinIntervalIterator cursor = new LocalizingZeroMinIntervalIterator(numBlocks);
		final Map<Integer, BasicBlockInfo> blockinfosList = new HashMap<Integer, BasicBlockInfo>();

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
				min[d] = currentBlock[d] * blockSize[d];

				if (min[d] + blockSize[d] > imgSize[d])
					effectiveBlockSize[d] = imgSize[d] - min[d];

				max[d] = min[d]+effectiveBlockSize[d]-1;
			}

			blockinfosList.put(i,
					new BasicBlockInfo(gridOffset,blockSize, effectiveBlockSize, min,max));
			i++;
		}

		return blockinfosList;
	}
}