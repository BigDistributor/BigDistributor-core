package com.bigdistributor.core.task;

import net.imglib2.Interval;
import net.imglib2.RandomAccessibleInterval;

public interface BlockTask<T,D,K> {
//	void blockTask(String inputPath, String metadataPath, String paramPath, String outputPath, Integer blockID) ;
	RandomAccessibleInterval<T> blockTask(D data, K params, Interval bb) ;
}
