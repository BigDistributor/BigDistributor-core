package com.bigdistributor.core.tools;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ArrayHelpers {
	public static int[] array(int unit, int size) {
		int[] array = new int[size];
		Arrays.fill(array, unit);
		return array;
	}
	
	public static long[] fill(long val, int length) {
		long[] arr = new long[length];
		Arrays.fill(arr, val);
		return arr;
	}

	public static long[] sum(long[] offset, long[] blocksize) {
		long[] result = new long[offset.length];
		for (int i = 0; i < offset.length; i++) {
			result[i] = offset[i] + blocksize[i];
		}
		return result;
	}

	public static<T>  String toString(Object array) {
		String string = "{ ";
		for (int i = 0, n = Array.getLength(array); i < n; i++) {
			string +=  String.valueOf(Array.get(array, i));
			if(i<Array.getLength(array)-1)  string +=  " - ";
		}
		string+= " }";
		return string;
	}

	public static String printCoordinates(long[] value) {
		String out = "(Array empty)";
		if (value != null && value.length != 0) {
			out = "(" + value[0];

			for(int i = 1; i < value.length; ++i) {
				out = out + ", " + value[i];
			}

			out = out + ")";
			return out;
		} else {
			return out;
		}
	}
}
