package com.bigdistributor.core.blockmanagement;

import net.imglib2.FinalInterval;
import net.imglib2.Interval;
import net.imglib2.iterator.LocalizingZeroMinIntervalIterator;
import net.imglib2.util.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BlockSupplier implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 9068252106055534926L;

    final long[] min, max;
    final int id;

    public BlockSupplier( final int id, final long[] min, final long[] max )
    {
        this.min = min;
        this.max = max;
        this.id = id;
    }

    public long[] min() { return min; }
    public long[] max() { return max; }
    public int id() { return id; }
    public FinalInterval createInterval()
    {
        return new FinalInterval( min, max );
    }

    public static ArrayList< BlockSupplier > splitIntoBlocks(final Interval interval, final int[] blockSize )
    {
        if ( blockSize.length != interval.numDimensions() )
            throw new RuntimeException( "Mismatch between interval dimension and blockSize length." );

        final int[] numBlocks = new int[ blockSize.length ];

        final List<List< Long >> mins = new ArrayList<>();
        final List< List< Long > > maxs = new ArrayList<>();

        for ( int d = 0; d < blockSize.length; ++d )
        {
            List< Long > min = new ArrayList<>();
            List< Long > max = new ArrayList<>();

            long bs = blockSize[ d ];
            long pos = interval.min( d );

            while ( pos < interval.max( d ) - 1 )
            {
                min.add( pos );
                max.add( pos + Math.min( bs - 1, interval.max( d ) - pos ) );

                pos += bs - 2; // one overlap, starts at the max - 2 since the most outer pixels are not evaluated with DoG
                ++numBlocks[ d ];
            }

            mins.add( min );
            maxs.add( max );
        }

        final ArrayList< BlockSupplier > blocks = new ArrayList<>();
        final LocalizingZeroMinIntervalIterator cursor = new LocalizingZeroMinIntervalIterator( numBlocks );
        int id = 0;

        while ( cursor.hasNext() )
        {
            cursor.fwd();

            final long[] min = new long[ blockSize.length ];
            final long[] max = new long[ blockSize.length ];

            for ( int d = 0; d < blockSize.length; ++d )
            {
                min[ d ] = mins.get( d ).get( cursor.getIntPosition( d ) );
                max[ d ] = maxs.get( d ).get( cursor.getIntPosition( d ) );
            }

            blocks.add( new BlockSupplier(id++, min, max) );
        }

        return blocks;
    }

    public static void main( String[] args )
    {
        ArrayList< BlockSupplier > blocks = splitIntoBlocks( new FinalInterval( new long[] { 19, -5 }, new long[] { 1000, 100 } ), new int[] { 100, 100 } );

        for ( final BlockSupplier b : blocks )
            System.out.println( Util.printInterval( b.createInterval() ) );
    }
}
