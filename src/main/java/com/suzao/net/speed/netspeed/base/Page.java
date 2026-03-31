package com.suzao.net.speed.netspeed.base;

import lombok.Data;

import java.util.List;

/**
 * <p>Page</p>
 * @author mc
 * @version 1.0
 * @date 2022/4/8 14:33
 **/
@Data
public class Page<T,E> {
    private List<E> datas;
    private int pages;
    private Integer totalRecord;
    private Integer startRow;
    private Integer rowCount;


    private E condition;
    private int pageNumber;
    private int pageSize;
}
