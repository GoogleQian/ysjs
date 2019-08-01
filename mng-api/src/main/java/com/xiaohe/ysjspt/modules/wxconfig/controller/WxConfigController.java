package com.xiaohe.ysjspt.modules.wxconfig.controller;

import com.xiaohe.ysjspt.config.aspect.LogOperate;
import com.xiaohe.ysjspt.entity.CommonResult;
import com.xiaohe.ysjspt.entity.PageBean;
import com.xiaohe.ysjspt.entity.Result;
import com.xiaohe.ysjspt.modules.wxconfig.entity.WxConfig;
import com.xiaohe.ysjspt.modules.wxconfig.service.WxConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author hzh
 * @date 2018/11/20
 */
@RestController
@RequestMapping(value = "/wxConfig")
public class WxConfigController {

    private static Logger log = LoggerFactory.getLogger(WxConfigController.class);
    @Autowired
    private WxConfigService wxConfigService;


    /**
     * 保存对象<br/>
     *
     * @param wxConfig 对象
     */
    @PostMapping
    @LogOperate(description = "新增商户信息")
    public Result save(@Validated @RequestBody WxConfig wxConfig) {
        try {
            wxConfig.setModifyTime(new Date());
            wxConfigService.save(wxConfig);
        } catch (Exception e) {
            log.error("保存商户信息失败");
            return new Result().error(6004, "保存失败");
        }
        return new Result().ok();
    }

    /**
     * 更新
     *
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    @LogOperate(description = "更新商户微信配置")
    public Result updateBanner(@PathVariable("id") Integer id, @RequestBody WxConfig wxConfig) {
        log.info("更新商户微信配置");
        Result result = new Result();
        boolean exists = wxConfigService.exists(id);
        if (!exists) {
            return result.error(6001, "商户微信配置不存在");
        }
        wxConfig.setModifyTime(new Date());
        int ret = wxConfigService.update(wxConfig);
        if (ret == 0) {
            return result.ok();
        } else {
            return result.error(6002, "更新失败");
        }
    }

    /**
     * 通过id删除对象
     *
     * @param id id
     */
    @DeleteMapping(value = "/{id}")
    @LogOperate(description = "删除商户微信配置")
    public Result deleteById(@PathVariable("id") Integer id) {
        try {
            wxConfigService.delete(id);
        } catch (Exception e) {
            return new Result().error(6003, "删除失败");
        }
        return new Result().ok();
    }


    /**
     * 通过id查找对象
     *
     * @param id id
     * @return WxConfig 对象
     */
    @GetMapping(value = "/{id}")
    public WxConfig findById(@RequestParam(value = "id") Integer id) {
        return wxConfigService.findById(id);
    }

    /**
     * 分页查询
     *
     * @param page     第几页
     * @param pageSize 页面大小
     * @return Page<WxConfig> 对象
     */
    @GetMapping
    public Result findByPage(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        CommonResult result = new CommonResult();
        Page<WxConfig> all = wxConfigService.findAll(page - 1, pageSize);
        List<WxConfig> content = all.getContent();
        PageBean<WxConfig> pageBean = new PageBean<>();
        pageBean.setCurPage(page + 1);
        Long totalElements = all.getTotalElements();
        pageBean.setItemCounts(totalElements.intValue());
        pageBean.setPageSize(pageSize);
        pageBean.setList(content);
        result.ok();
        result.setDatas(pageBean);
        return result;
    }


}
