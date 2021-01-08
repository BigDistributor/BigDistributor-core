package com.bigdistributor.core.blockmanagement.blockinfo;

import com.bigdistributor.core.blockmanagement.block.BlockFunctions;
import com.bigdistributor.core.tools.ArrayHelpers;
import net.imglib2.Localizable;
import net.imglib2.iterator.LocalizingZeroMinIntervalIterator;
import net.imglib2.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ComplexBlocInfoGenerator implements BlockInfoGenerator<ComplexBlockInfo> {

	final long[] imgSize;
	final long[] kernelSize;
	final long[] blockSize;

	public ComplexBlocInfoGenerator(long[] imgSize, long[] kernelSize, long[] blockSize) {
		this.imgSize = imgSize;
		this.kernelSize = kernelSize;
		this.blockSize = blockSize;
	}

	public ComplexBlocInfoGenerator(long[] dims, long overlap,long blockSize) {
		final double[] sigmas = Util.getArrayFromValue((double) overlap, dims.length);
		final int[] halfKernelSizes = BlockFunctions.halfkernelsizes(sigmas);
		final long[] kernelSize = new long[dims.length];
		final long[] imgSize = new long[dims.length];
		for (int d = 0; d < dims.length; ++d) {
			kernelSize[d] = (overlap == 0) ? 0 : (halfKernelSizes[d] * 2 - 1);
			imgSize[d] = dims[d];
		}
		long[] blockSizes = ArrayHelpers.fill(blockSize, imgSize.length);

		this.imgSize = imgSize;
		this.kernelSize = kernelSize;
		this.blockSize = blockSizes;

	}

	@Override
	public List<ComplexBlockInfo> getBlockInfos() {
		return null;
	}

	@Override
	public List<ComplexBlockInfo> divideIntoBlockInfo() {

		final int numDimensions = imgSize.length;

		// compute the effective size & local offset of each block
		// this is the same for all blocks
		final long[] effectiveSizeGeneral = new long[numDimensions];
		final long[] effectiveLocalOffset = new long[numDimensions];

		for (int d = 0; d < numDimensions; ++d) {
//			if (kernelSize[d] > 0) {
				effectiveSizeGeneral[d] = blockSize[d] - kernelSize[d] ;
//			} else {
//				effectiveSizeGeneral[d] = blockSize[d];
//			}

			if (effectiveSizeGeneral[d] <= 0) {
				logger.error("Blocksize in dimension " + d + " (" + blockSize[d] + ") is smaller than the kernel ("
						+ kernelSize[d] + ") which results in an negative effective size: " + effectiveSizeGeneral[d]
						+ ". Quitting.");
				return null;
			}

			effectiveLocalOffset[d] = kernelSize[d] / 2;
		}

		// compute the amount of blocks needed
		final long[] numBlocks = new long[numDimensions];

		for (int d = 0; d < numDimensions; ++d) {
			numBlocks[d] = imgSize[d] / effectiveSizeGeneral[d];

			// if the modulo is not 0 we need one more that is only partially useful
			if (imgSize[d] % effectiveSizeGeneral[d] != 0)
				++numBlocks[d];
		}

		logger.info("imgSize " + Util.printCoordinates(imgSize));
		logger.info("kernelSize " + Util.printCoordinates(kernelSize));
		logger.info("blockSize " + Util.printCoordinates(blockSize));
		logger.info("numBlocks " + Util.printCoordinates(numBlocks));
		logger.info("effectiveSize of blocks" + Util.printCoordinates(effectiveSizeGeneral));
		logger.info("effectiveLocalOffset " + Util.printCoordinates(effectiveLocalOffset));

		// now we instantiate the individual blocks iterating over all dimensions
		// we use the well-known ArrayLocalizableCursor for that
		final LocalizingZeroMinIntervalIterator cursor = new LocalizingZeroMinIntervalIterator(numBlocks);
		final List<ComplexBlockInfo> blockinfosList = new ArrayList<>();

		final int[] currentBlock = new int[numDimensions];
		int i = 0;
		while (cursor.hasNext()) {
			cursor.fwd();
			cursor.localize(currentBlock);
			final long[] gridOffset = Util.int2long(currentBlock);
			
			// compute the current offset
			final long[] offset = new long[numDimensions];
			final long[] x1 = new long[numDimensions];
			final long[] x2 = new long[numDimensions];
			final long[] effectiveSize = effectiveSizeGeneral.clone();

			for (int d = 0; d < numDimensions; d++) {
				x1[d] = currentBlock[d] * effectiveSize[d] + 1;
				offset[d] = x1[d] - kernelSize[d] / 2;

				if (x1[d] + effectiveSize[d] > imgSize[d])
					effectiveSize[d] = imgSize[d] - x1[d];
				x2[d] = x1[d]+effectiveSize[d]-1;
			}

			blockinfosList.add(
					new ComplexBlockInfo(gridOffset, blockSize, effectiveSize,x1, x2, offset, effectiveLocalOffset,effectiveLocalOffset, true));
			i++;
		}

		return blockinfosList;
	}

	@Override
	public ComplexBlockInfo getBlockForPosition(Localizable l) {
		return null;
	}
}
