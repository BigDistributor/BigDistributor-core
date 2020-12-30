package com.bigdistributor.io.mvrecon;

import com.bigdistributor.controllers.blockmanagement.blockinfo.BasicBlockInfo;
import com.bigdistributor.io.DataExtension;
import mpicbg.spim.data.SpimDataException;
import net.imglib2.util.Util;
import net.preibisch.mvrecon.fiji.spimdata.SpimData2;
import net.preibisch.mvrecon.fiji.spimdata.XmlIoSpimData2;
import net.preibisch.mvrecon.fiji.spimdata.boundingbox.BoundingBox;

public class SpimHelpers {
    public static SpimData2 getSpimData(String input) throws SpimDataException {
        if (DataExtension.fromURI(input) != DataExtension.XML)
            throw new SpimDataException("input " + input + " is not XML");
        return new XmlIoSpimData2("").load(input);
    }
    public static BoundingBox getBb(BasicBlockInfo binfo) {
        return new BoundingBox(Util.long2int(binfo.getMin()), Util.long2int(binfo.getMax()));
    }
}
