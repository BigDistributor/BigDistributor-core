package com.bigdistributor.core.blockmanagement.block;

public class BlockFunctions {
    public static int[] halfkernelsizes(double[] sigma) {
        int n = sigma.length;
        int[] size = new int[n];

        for(int i = 0; i < n; ++i) {
            size[i] = Math.max(2, (int)(3.0D * sigma[i] + 0.5D) + 1);
        }

        return size;
    }
}
