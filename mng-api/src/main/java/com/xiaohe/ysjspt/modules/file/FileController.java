package com.xiaohe.ysjspt.modules.file;

import com.xiaohe.ysjspt.config.aspect.LogOperate;
import com.xiaohe.ysjspt.entity.CommonResult;
import com.xiaohe.ysjspt.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;

/**
 * @author hzh
 * @date 2018/10/17
 */
@RestController
@RequestMapping(value = "znss/file")
public class FileController {
    private static Logger log = LoggerFactory.getLogger(FileController.class);

    /**
     * 支持图片的类型
     **/
    private String[] types = {".p12"};
    /**
     * 图片保存路径
     */
    @Value("${file.uploadPath}")
    private String uploadPath;
    /**
     * 图片重命名前缀
     */
    @Value("${file.prefix}")
    private String prefix;

    @Resource
    private HttpServletRequest request;

    /**
     * 证书上传
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/cert")
    @LogOperate(description = "证书上传")
    public Result upload(@RequestParam(value = "file") MultipartFile file) {
        CommonResult result = new CommonResult();
        String fileName;
        if (file == null || file.isEmpty()) {
            return result.error(9001, "证书为空");
        }
        fileName = file.getOriginalFilename();
        //比较证书格式
        String type = fileName.substring(fileName.lastIndexOf("."));
        if (!Arrays.asList(types).contains(type)) {
            return result.error(9002, "上传证书格式错误");
        }
        BufferedOutputStream out;
        File fileSourcePath = new File(uploadPath);
        if (!fileSourcePath.exists()) {
            fileSourcePath.mkdirs();
        }
        //重命名，防重复
        fileName = prefix + System.currentTimeMillis() + type;
        log.info("上传的文件名为：" + fileName);
        try {
            out = new BufferedOutputStream(
                    new FileOutputStream(new File(fileSourcePath, fileName)));
            out.write(file.getBytes());
            out.flush();
        } catch (Exception e) {
            log.info(e.getMessage());
            return result.error(9003, "证书保存错误");
        }
        result.ok();
        result.setDatas(uploadPath + fileName);
        return result;
    }


}
