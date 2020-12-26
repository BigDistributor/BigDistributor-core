package com.bigdistributor.controllers.blockmanagement.blockinfo;


import com.bigdistributor.core.tools.ArrayHelpers;

public class BasicBlockInfo {

	private long[] gridOffset;
	private long[] blockSize;
	private long[] effectiveSize;
	private long[] min;
	private long[] max;

	public BasicBlockInfo(long[] gridOffset, long[] blockSize, long[] effectiveSize, long[] min, long[] max) {
		super();
		this.gridOffset = gridOffset;
		this.blockSize = blockSize;
		this.effectiveSize = effectiveSize;
		this.min = min;
		this.max = max;
	}

	public long[] getGridOffset() {
		return gridOffset;
	}

	public long[] getBlockSize() {
		return blockSize;
	}

	public long[] getEffectiveSize() {
		return effectiveSize;
	}

	public long[] getMax() {
		return max;
	}

	public long[] getMin() {
		return min;
	}

	@Override
	public String toString() {
		return "BS:" + ArrayHelpers.printCoordinates(blockSize) + " GridOff:" + ArrayHelpers.printCoordinates(gridOffset) + " Eff_Size:"
				+ ArrayHelpers.printCoordinates(effectiveSize) + " min:" + ArrayHelpers.printCoordinates(min) + " max:"
				+ ArrayHelpers.printCoordinates(max);
	}

}