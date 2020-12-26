package com.bigdistributor.core.task.items;

import com.bigdistributor.controllers.blockmanagement.blockinfo.BasicBlockInfo;
import com.bigdistributor.io.GsonIO;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import net.imglib2.Interval;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class Metadata {
	private String jobID;
	private String input;
	private String output;
	private Interval bb;
	private long[] blocksize;
	private Map<Integer, BasicBlockInfo> blocksInfo;

	
	public Metadata(String jobID, String input, String output,Interval bb, long[] blocksize,
			Map<Integer, BasicBlockInfo> blocksInfo) {
		super();
		this.bb = bb;
		this.jobID = jobID;
		this.input = input;
		this.output = output;
		this.blocksize = blocksize;
		this.blocksInfo = blocksInfo;
	}

	public Interval getBb() {
		return bb;
	}
	
	public String getJobID() {
		return jobID;
	}

	public String getInput() {
		return input;
	}

	public String getOutput() {
		return output;
	}

	public long[] getBlocksize() {
		return blocksize;
	}

	public Map<Integer, BasicBlockInfo> getBlocksInfo() {
		return blocksInfo;
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
	
	
	public static Metadata fromJson(String path) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		return GsonIO.fromJson(path, Metadata.class);
	}
}