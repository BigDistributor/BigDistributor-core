package com.bigdistributor.core.task;

import net.imglib2.Interval;
import net.imglib2.RandomAccessibleInterval;

import java.io.Serializable;

public interface BlockTask<T, D, K> extends Serializable {

    RandomAccessibleInterval<T> blockTask(D data, K params, Interval bb);


}
