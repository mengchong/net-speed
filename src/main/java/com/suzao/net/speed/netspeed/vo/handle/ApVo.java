package com.suzao.net.speed.netspeed.vo.handle;

import com.suzao.net.speed.netspeed.bo.handle.ApBo;
import lombok.Data;

/**
 * @name ApVo
 * @author mc
 * @date 2022/4/9 22:27
 * @version 1.0
 **/
@Data
public class ApVo extends ApBo {
    private String areaName;

    private Integer sceneId;

    private String sceneName;
}
