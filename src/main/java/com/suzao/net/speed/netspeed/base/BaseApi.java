package com.suzao.net.speed.netspeed.base;


import java.util.List;


public interface BaseApi<Bo, Vo> {

    int insert(List<Bo> bo);

    List<Vo> selectAll(Vo vo);

    void selectPage(Page<Bo, Vo> page);

    Vo findById(Long id);
}
