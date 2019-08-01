package com.xiaohe.ysjspt.controller;

import com.xiaohe.ysjspt.entity.CommonResult;
import com.xiaohe.ysjspt.entity.PageBean;
import com.xiaohe.ysjspt.entity.Result;
import com.xiaohe.ysjspt.entity.WaterType;
import com.xiaohe.ysjspt.service.WaterTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: ysjsApi
 * @description: 售水类型
 * @author: Gmq
 * @date: 2018-11-08 17:09
 **/
@RestController
@RequestMapping(value = "/waterTypes")
public class WaterTypeController {
    private static Logger log = LoggerFactory.getLogger(WaterTypeController.class);
    @Autowired
    private WaterTypeService waterTypeService;
    /**
     * 新增类型
     * @param waterType
     * @return
     */
    @PostMapping(value = "/")
    public Result insertWaterType(@Validated @RequestBody WaterType waterType) {
        Result result = new Result();
        try {
            waterTypeService.save(waterType);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2000, "新建类型，错误码 2000");
        }
        result.ok();
        return result;
    }

    /**
     * 查询类型列表
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/")
    @RequiresPermissions("sys:sale-type:list")
    public Result queryWaterTypeList(@RequestParam(required = false,defaultValue = "1") Integer page, @RequestParam(required = false,defaultValue = "10")Integer pageSize){
        CommonResult result=new CommonResult();
        try {
            PageBean<WaterType> datas=new PageBean<WaterType>();
            Page<WaterType> all = waterTypeService.findAll(page-1, pageSize);
            Long count = waterTypeService.count();
            List<WaterType> coupons=new ArrayList<>();
            if(all!=null){
                coupons = all.getContent();
            }
            result.ok();
            datas.setItemCounts(count.intValue());
            datas.setPageSize(pageSize);
            datas.setCurPage(page);
            datas.setList(coupons);
            result.setDatas(datas);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            return result.error(2000, "查询列表出错 错误码2000");
        }
        return result;
    }
    /**
     * 修改类型
     * @param id 类型Id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result updateWaterType(@PathVariable Integer id, @RequestBody WaterType waterType){
        waterType.setId(id);
        Result result = null;
        try{
            result=insertWaterType(waterType);
        }catch (Exception e){
            log.error(e.getMessage());
            return result.error(2000, "更新失败");
        }
        return result;
    }

    /**
     * 删除类型
     * @param id 类型ID
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result deleteWaterType(@PathVariable Integer id){
        Result result=new Result();
        try {
            waterTypeService.delete(id);
        }catch (Exception e){
            log.error(e.getMessage());
            return result.error(2000, "删除出错 错误码2000");
        }
        result.ok();
        return result;
    }
}
