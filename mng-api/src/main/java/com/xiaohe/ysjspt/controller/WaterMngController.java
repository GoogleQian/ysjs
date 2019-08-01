package com.xiaohe.ysjspt.controller;


import com.xiaohe.ysjspt.entity.Result;
import com.xiaohe.ysjspt.entity.WaterMngEntity;
import com.xiaohe.ysjspt.service.WaterMngService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 直饮水管理
 * @author wzq
 * @since 2018-10-16
 */
@RestController
@RequestMapping(value = "/waterMng")
public class WaterMngController {
    @Autowired
    private WaterMngService watermngService;
    /**
     * 保存对象<br/>
     *
     * @param watermng
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public Result save(WaterMngEntity watermng) {
        Result result = new Result();
        if(watermng.getId() == null){
            Date now = new Date();
            watermng.setCreateTime(now);
            watermngService.save(watermng);
            result.setMsg("插入成功");
        }
        else{
            watermngService.save(watermng);
            result.setMsg("更新成功");
        }
        return result;
    }

    /**
     * 删除对象
     *
     * @param watermng
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public void delete(WaterMngEntity watermng) {
        watermngService.delete(watermng);
    }



    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return Page<TbWaterMng>
     * @throws Exception
     */
    @RequestMapping(value = "/findAll")
    public Page<WaterMngEntity> findAll(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "1") Integer pageSize) {
        return watermngService.findAll(page, pageSize);
    }
}
