package com.bigdistributor.core.blockmanagement.block;

import com.bigdistributor.core.blockmanagement.multithreading.Threads;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.view.Views;

import java.util.ArrayList;

public class TestBlock {
    public static void main( String[] args )
    {
        // define the blocksize so that it is one single block
        final RandomAccessibleInterval<FloatType> block = ArrayImgs.floats( 384, 384 );
        final long[] blockSize = new long[ block.numDimensions() ];
        block.dimensions( blockSize );

        final RandomAccessibleInterval< FloatType > image = ArrayImgs.floats( 1024, 1024 );
        final long[] imgSize = new long[ image.numDimensions() ];
        image.dimensions( imgSize );

        // whatever the kernel size is (extra size/2 in general)
        final long[] kernelSize = new long[]{ 16, 32 };

        final BlockGeneratorFixedSizePrecise blockGenerator = new BlockGeneratorFixedSizePrecise( Threads.createExService(), blockSize );
        final ArrayList<Block> blocks = blockGenerator.divideIntoBlocks( imgSize, kernelSize);

        int i = 0;

        for ( final Block b : blocks )
        {
            // copy data from the image to the block (including extra space for outofbounds/real image data depending on kernel size)
            b.copyBlock( Views.extendMirrorDouble( image ), block );

            // do something with the block (e.g. also multithreaded, cluster, ...)
            for ( final FloatType f : Views.iterable( block ) )
                f.set( i );

            ++i;

            // write the block back (use a temporary image if multithreaded or in general not all are copied first)
            b.pasteBlock( image, block );
        }

        ImageJFunctions.show( image );
    }
}
