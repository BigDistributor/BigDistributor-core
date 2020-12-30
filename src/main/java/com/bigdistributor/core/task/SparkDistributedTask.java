package com.bigdistributor.core.task;

import com.bigdistributor.biglogger.adapters.Log;
import com.bigdistributor.controllers.blockmanagement.blockinfo.BasicBlockInfo;
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
    private String output;

    @Option(names = {"-i", "--input"}, required = true, description = "The path of the Data")
    private String input;

    @Option(names = {"-id", "--jobid"}, required = true, description = "The path of the Data")
    private String jobId;

    @Option(names = {"-m", "--meta"}, required = true, description = "The path of the MetaData file")
    private String metadataPath;

    @Option(names = {"-p", "--param"}, required = true, description = "The path of the MetaData file")
    private String paramPath;

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
        JobID.set(jobId);
        logger.info(jobId + "started!");
        SerializableParams<K> params = new SerializableParams<K>().fromJson(new File(paramPath));
        SpimData2 spimdata = new XmlIoSpimData2("").load(input);
        SparkConf sparkConf = new SparkConf().setAppName(jobId);
        N5Writer n5 = new N5FSWriter(output);
        final JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);
        sparkContext.parallelize(md.getBlocksInfo(), md.getBlocksInfo().size()).foreach(new VoidFunction<BasicBlockInfo>() {
            @Override
            public void call(BasicBlockInfo binfo) throws Exception {
                int blockID = binfo.getBlockId();
                logger.blockStarted(blockID, "start processing..");
                SpimHelpers.getBb(binfo);
                RandomAccessibleInterval<T> result = mainTask.blockTask(spimdata, params, SpimHelpers.getBb(binfo));
                logger.blockLog(blockID, " Got processed image");
                N5Utils.saveBlock(result,n5,dataset,binfo.getGridOffset());
                logger.blockDone(blockID,"Task done.");
            }
        });
        return null;
}

}
