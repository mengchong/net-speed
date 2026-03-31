package com.suzao.net.speed.netspeed.controller.network;


import com.alibaba.fastjson.JSONObject;
import com.suzao.net.speed.netspeed.service.network.ThresholdSettingApi;
import com.suzao.net.speed.netspeed.vo.network.DownloadUploadVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

import static com.suzao.net.speed.netspeed.util.ResponseUtil.toSuccess;


/**
 * @author mc
 * @version 1.0
 * @name PassValueController
 * @date 2022/4/9 20:37
 **/
@Api(tags = "文件上传、下载")
@RestController
@RequestMapping({"/vesapp/documentParers"})
@Slf4j
public class DocumentParseController {

    @Autowired
    private ThresholdSettingApi thresholdSettingApi;

    @Value("${document.download.path}")
    private String documentDownloadPath;

    @Value("${document.upload.path}")
    private String documentUploadPath;


    @ApiOperation(value = "下载文件")
    @GetMapping({"/download.do"})
    public void download(HttpServletResponse response) {
        try {
            // path是指想要下载的文件的路径
            File file = new File(documentDownloadPath);
            // 获取文件名
            String filename = file.getName();

            // 将文件写入输入流
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStream fis = new BufferedInputStream(fileInputStream);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            // 清空response
            response.reset();
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
            //attachment表示以附件方式下载 inline表示在线打开 "Content-Disposition: inline; filename=文件名.mp3"
            // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            // 告知浏览器文件的大小
            response.addHeader("Content-Length", "" + file.length());
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @ApiOperation(value = "上传文件")
    @PostMapping({"/upload.do"})
    public void batchUpload(@RequestParam(value = "file", required = true) MultipartFile file) {
        try {
            String fileName = file.getName();
            String path = documentUploadPath + fileName;
            // getCanonicalFile 可解析正确各种路径
            File dest = new File(path).getCanonicalFile();
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            // 文件写入
            file.transferTo(dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "获取上传下载rul")
    @GetMapping("/url.do")
    public JSONObject getUrl(Long size) {
        //下载：https://speedtest2.niutk.com:8080/download?size=200000000&r=0.9323305783548046
        //上传：https://speedtest02.js165.com.prod.hosts.ooklaserver.net:8080/upload?r=0.6885793154932645
        if (ObjectUtils.isEmpty(size)) {
            size = 200000000L;
        }
        DownloadUploadVo vo = new DownloadUploadVo();
        vo.setDownloadUrl("https://speedtest2.niutk.com:8080/download?size=" + size + "&r=0.9323305783548046");
        vo.setUploadUrl("https://speedtest02.js165.com.prod.hosts.ooklaserver.net:8080/upload?r=0.6885793154932645");
        return toSuccess(vo);

    }

}
