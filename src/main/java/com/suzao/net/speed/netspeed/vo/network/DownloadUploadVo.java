package com.suzao.net.speed.netspeed.vo.network;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DownloadUploadVo
 *
 * @author mc
 * @version 1.0
 * @date 2022/7/12 13:50
 **/
@Data
@ApiModel
public class DownloadUploadVo {
    @ApiModelProperty(value = "下载url", example = "https://speedtest2.niutk.com:8080/download?size=200000000&r=0.9323305783548046")
    private String downloadUrl;
    @ApiModelProperty(value = "上传url", example = "https://speedtest02.js165.com.prod.hosts.ooklaserver.net:8080/upload?r=0.6885793154932645")
    private String uploadUrl;
}
