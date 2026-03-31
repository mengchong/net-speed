package com.suzao.net.speed.netspeed.service.network;

import com.suzao.net.speed.netspeed.bo.network.PassValueBo;

import java.util.List;


/**
 * @author mengc
 */
public interface PassValueApi {

    int insert(List<PassValueBo> bo);

    PassValueBo selectAll();
}
