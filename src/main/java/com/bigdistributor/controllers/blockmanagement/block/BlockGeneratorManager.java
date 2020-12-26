package com.bigdistributor.controllers.blockmanagement.block;

import com.bigdistributor.controllers.blockmanagement.multithreading.Threads;

import net.imglib2.util.Util;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class BlockGeneratorManager {

	public static <T> List<Block> generateBlocks(final long[] dims, final double overlap, final long[] blocksize) {
		final ExecutorService service = Threads.createExService(1);
		final BlockGenerator<Block> generator = new BlockGeneratorFixedSizePrecise(service, blocksize);
		final double[] sigmas = Util.getArrayFromValue(overlap, dims.length);
		final int[] halfKernelSizes = BlockFunctions.halfkernelsizes(sigmas);
		final long[] kernelSize = new long[dims.length];
		final long[] imgSize = new long[dims.length];
		for (int d = 0; d < dims.length; ++d) {
			kernelSize[d] = halfKernelSizes[d] * 2 - 1;
			imgSize[d] = dims[d];
		}
		final List<Block> blocks = generator.divideIntoBlocks(imgSize, kernelSize);
		return blocks;
	}
	
}
