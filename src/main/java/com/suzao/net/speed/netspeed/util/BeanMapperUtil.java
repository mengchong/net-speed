package com.suzao.net.speed.netspeed.util;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @name BeanMapperUtil
 * @author mc
 * @date 2022/4/9 22:26
 * @version 1.0
 **/
public class BeanMapperUtil {
    private static DozerBeanMapper dozer = new DozerBeanMapper();

    private BeanMapperUtil() {
    }


    public static <T> T map(Object source, Class<T> destinationClass) {
        return source == null ? null : dozer.map(source, destinationClass);
    }

    public static <T> List<T> mapList(Collection<?> sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList();
        if (sourceList != null && sourceList.size() != 0) {
            Iterator var3 = sourceList.iterator();

            while(var3.hasNext()) {
                Object sourceObject = var3.next();
                T destinationObject = dozer.map(sourceObject, destinationClass);
                destinationList.add(destinationObject);
            }

            return destinationList;
        } else {
            return destinationList;
        }
    }

    public static void copy(Object source, Object destinationObject) {
        dozer.map(source, destinationObject);
    }

    public static Mapper getMapper() {
        return dozer;
    }
}
