package com.bigdistributor.core.task;

import com.bigdistributor.biglogger.adapters.Log;
import com.bigdistributor.core.blockmanagement.blockinfo.BasicBlockInfo;
import com.bigdistributor.core.task.items.Metadata;
import com.bigdistributor.core.task.items.SerializableParams;
import com.bigdistributor.io.mvrecon.SpimHelpers;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.NativeType;
import net.preibisch.mvrecon.fiji.spimdata.SpimData2;
import net.preibisch.mvrecon.fiji.spimdata.XmlIoSpimData2;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import org.janelia.saalfeldlab.n5.N5FSWriter;
import org.janelia.saalfeldlab.n5.N5Writer;
import org.janelia.saalfeldlab.n5.imglib2.N5Utils;
import picocli.CommandLine.Option;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.concurrent.Callable;

// T: DataType exmpl FloatType
// K: Task Param
public class SparkDistributedTask<T extends NativeType<T>, K extends SerializableParams> implements Callable<Void> {
    //<D extends SpimData2, K extends SerializableParams>
    @Option(names = {"-o", "--output"}, required = true, description = "The path of the Data")
    String output;

    @Option(names = {"-i", "--input"}, required = true, description = "The path of the Data")
    String input;

    @Option(names = {"-id", "--jobid"}, required = true, description = "The path of the Data")
    String jobId;

    @Option(names = {"-m", "--meta"}, required = true, description = "The path of the MetaData file")
    String metadataPath;

    @Option(names = {"-p", "--param"}, required = false, description = "The path of the MetaData file")
    String paramPath;

    private static final Log logger = Log.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private BlockTask mainTask;

    private Metadata md;
    private static String dataset = "/volumes/raw";

    public SparkDistributedTask(BlockTask task) {
        this.mainTask = task;
    }

    @Override
    public Void call() throws Exception {
        md = Metadata.fromJson(metadataPath);
        if (md == null) {
            logger.error("Error metadata file !");
            return null;
        }
        JobID.set(jobId);
        logger.info(jobId + " started!");
        SerializableParams<K> params = null;
        if (paramPath != null) {
            File paramFile = new File(paramPath);
            params = new SerializableParams<K>().fromJson(paramFile);
        }
        SpimData2 spimdata = new XmlIoSpimData2("").load(input);
        SparkConf sparkConf = new SparkConf().setAppName(jobId).set( "spark.serializer", "org.apache.spark.serializer.KryoSerializer" );;
        N5Writer n5 = new N5FSWriter(output);
        final JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);


        SerializableParams<K> finalParams = params;
        sparkContext.parallelize(md.getBlocksInfo(), md.getBlocksInfo().size()).foreach((VoidFunction<BasicBlockInfo>) binfo -> {
            int blockID = binfo.getBlockId();
            logger.blockStarted(blockID, " start processing..");
            SpimHelpers.getBb(binfo);
            RandomAccessibleInterval<T> result = mainTask.blockTask(spimdata, finalParams, SpimHelpers.getBb(binfo));
            logger.blockLog(blockID, " Got processed image");
            N5Utils.saveBlock(result, n5, dataset, binfo.getGridOffset());
            logger.blockDone(blockID, " Task done.");
        });
        return null;
    }

}
