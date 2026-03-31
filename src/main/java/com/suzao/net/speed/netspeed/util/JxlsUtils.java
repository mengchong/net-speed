package com.suzao.net.speed.netspeed.util;

import net.sf.jxls.transformer.XLSTransformer;

import java.util.Map;

/**
 * Created by Administrator on 2018/6/19.
 *
 */
public class JxlsUtils {
    public static void testExportExcel(String src, Map map, String dst) {
        XLSTransformer former = new XLSTransformer();
        try {
            former.transformXLS(src, map, dst);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


