package com.suzao.net.speed.netspeed.bo.network;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @name ExperienceSpeedListBo
 * @author mc
 * @date 2022/4/9 22:01
 * @version 1.0
 **/
@Data
@ApiModel
public class ExperienceSpeedListBo {

    private List<ExperienceSpeedBo> experienceSpeedBoList;
}
