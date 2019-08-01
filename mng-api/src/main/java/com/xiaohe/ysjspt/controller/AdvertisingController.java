package com.xiaohe.ysjspt.controller;

import com.xiaohe.ysjspt.config.aspect.LogOperate;
import com.xiaohe.ysjspt.entity.AdvRich;
import com.xiaohe.ysjspt.entity.AdvertisingEntity;
import com.xiaohe.ysjspt.entity.CommonResult;
import com.xiaohe.ysjspt.entity.Result;
import com.xiaohe.ysjspt.modules.sys.controller.AbstractController;
import com.xiaohe.ysjspt.modules.sys.untils.Constant;
import com.xiaohe.ysjspt.service.AdvRichService;
import com.xiaohe.ysjspt.service.AdvertisingService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.weaver.ast.Var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * 广告表 TbAdvertisingController层
 *
 * @author wzq
 * @since 2018-11-19
 */
@RestController
@RequestMapping(value = "/adv")
public class AdvertisingController extends AbstractController {
    private static Logger log = LoggerFactory.getLogger(AdvertisingController.class);
    @Autowired
    private AdvertisingService advertisingService;
    @Autowired
    private Environment env;
    @Autowired
    private AdvRichService richService;

    @PostMapping("/upload")
    @LogOperate(description = "上传广告")
    public Result handleFileUpload(@RequestParam("file") MultipartFile file
            , @RequestParam("name") String name, @RequestParam("user_id") Long userId
            , @RequestParam(value = "url", required = false) String url, @RequestParam(value = "content", required = false) String content
            ,@RequestParam(value = "use_hyper_link") Integer useHyperLink) {
        Result result = new Result();
        try {
            AdvertisingEntity adv = new AdvertisingEntity(name, userId, url, content,useHyperLink);
            List<String> titleByUserId = advertisingService.findTitleByUserId(userId);
            if (titleByUserId.contains(adv.getTitle())) {
                return result.error(2001, "名称不能重复");
            }
            Result result1 = advertisingService.saveAdv(adv, env.getProperty("cbs.uploadPath"), file);
            if (result1.getRet() != 0) {
                return result1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return result.error(2000, "上传文件失败");
        }
        return result.ok();
    }

    /**
     * 查看广告详情
     *
     * @param id 广告Id
     * @return
     */
    @GetMapping(value = "/{id}")
    @RequiresPermissions("sys:ad:info")
    public Result queryAds(@PathVariable Integer id) {
        CommonResult result = new CommonResult();
        try {
            AdvertisingEntity byId = advertisingService.findById(id);
            result.setDatas(byId);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return result.error(2000, "查询失败");
        }
        return result.ok();
    }

    @PutMapping("/{id}")
    @RequiresPermissions("sys:ad:upadte")
    @LogOperate(description = "更新广告")
    public Result update(@PathVariable Integer id, @RequestParam(value = "file", required = false) MultipartFile file
            , @RequestParam("name") String name, @RequestParam("user_id") Long userId,@RequestParam(value = "use_hyper_link") Integer useHyperLink
            , @RequestParam(value = "url", required = false) String url, @RequestParam(value = "content", required = false) String content) {
        Result result = new Result();
        try {
            AdvertisingEntity adv = advertisingService.findById(id);
            if (adv == null) {
                return result.error(2000, "参数非法，没有此广告");
            }
            adv.setTitle(name);
            adv.setUserId(userId);
            adv.setLinkUrl(url);
            adv.setContent(content);
            adv.setUseHyperLink(useHyperLink);
            Result result1 = advertisingService.saveAdv(adv, env.getProperty("cbs.uploadPath"), file);
            if (result1.getRet() != 0) {
                return result1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return result.error(2000, "更新失败");
        }
        return result.ok();
    }

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return Page<TbAdvertising>
     * @throws Exception
     */
    @RequestMapping(value = "/findAll")
    @RequiresPermissions("sys:ad:list")
    public Page<AdvertisingEntity> findAll(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @RequestParam(value = "pageSize", required = false, defaultValue = "1") Integer pageSize) {
        return advertisingService.findAll(page - 1, pageSize,getUserId());
    }

    /**
     * 删除广告
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @RequiresPermissions("sys:ad:delete")
    @LogOperate(description = "删除广告")
    public Result delete(@PathVariable Integer id) {
        Result result = new Result();
        try {
            //先删除图片
            AdvertisingEntity adv = advertisingService.findById(id);
            Result result1 = advertisingService.deleteImge(adv);
            if(result1.getRet()!=0){
                return result1;
            }
            advertisingService.delete(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2000, "删除失败");
        }
        return result.ok();
    }

    /**
     * 富文本上传图片
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadRichTextImage")
    @LogOperate(description = "富文本上传图片")
    public Result uploadRichImg(@RequestParam("file") MultipartFile file) {
        CommonResult commonResult = new CommonResult();
        UUID uuid = UUID.randomUUID();
        //上传图片
        Result upload = null;
        AdvRich advRich = new AdvRich();
        try {
                Long userId=getUserId();
                upload = advertisingService.upload(file, env.getProperty("cbs.uploadPath") + uuid + userId);
                if (upload.getRet() == 0) {
                    //图片访问路径
                    String url = Constant.ADV_PREFIX + uuid + userId + file.getOriginalFilename();
                    advRich.setUserId(userId.intValue());
                    advRich.setImgUrl(url);
                }else{
                    return commonResult.error(upload.getRet(), upload.getMsg());
                }
            richService.save(advRich);
        } catch (Exception e) {
            log.error(e.getMessage());
            return commonResult.error(2000, "上传失败");
        }
        commonResult.setDatas(advRich);
        return commonResult.ok();
    }

}