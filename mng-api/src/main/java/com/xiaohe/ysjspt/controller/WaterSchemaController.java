package com.xiaohe.ysjspt.controller;

import com.xiaohe.ysjspt.config.aspect.LogOperate;
import com.xiaohe.ysjspt.entity.*;
import com.xiaohe.ysjspt.modules.sys.controller.AbstractController;
import com.xiaohe.ysjspt.service.DeviceService;
import com.xiaohe.ysjspt.service.SchemaDetailService;
import com.xiaohe.ysjspt.service.WaterSchemaService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @program: ysjsApi
 * @description: 售水方案
 * @author: Gmq
 * @date: 2018-11-12 14:45
 **/
@RestController
@RequestMapping("/schema")
public class WaterSchemaController extends AbstractController {
    private static Logger log = LoggerFactory.getLogger(WaterSchemaController.class);
    @Autowired
    private WaterSchemaService waterSchemaService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private SchemaDetailService detailService;

    /**
     * 新增方案
     *
     * @param schema
     * @return
     */
    @PostMapping(value = "/")
    @RequiresPermissions("sys:sale-plan:save")
    @LogOperate(description = "新增售水方案")
    public Result insertCoupon(@Validated @RequestBody WaterSchema schema) {
        Result result = new Result();
        try {
            schema.setCreateTime(new Date());
            schema.setUserId(getUserId());
            Result result1 = waterSchemaService.saveSchema(schema);
            if(result1.getRet()!=0){
                return result1;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2000, "新建方案失败，错误码 2000");
        }
        result.ok();
        return result;
    }

    /**
     * 查询方案列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/")
    @RequiresPermissions("sys:sale-plan:list")
    public Result queryCouponList(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        CommonResult result = new CommonResult();
        try {
            PageBean<WaterSchema> datas = new PageBean<WaterSchema>();
            Page<WaterSchema> all = waterSchemaService.findAll(page - 1, pageSize,null,getUserId());
            Long count = all.getTotalElements();
            List<WaterSchema> schemaList = new ArrayList<>();
            if (all != null) {
                schemaList = all.getContent();
                //查询方案详情集合
                List<SchemaDetail> detailList = new LinkedList<>();
                //方案ID
                List<Integer> integers = new LinkedList<>();
                for (WaterSchema coupon : schemaList) {
                    integers.add(coupon.getId());
                }
                if(integers.size()!=0){
                    detailList=detailService.findAllBySchemaIdIn(integers);
                }
                for (WaterSchema coupon : schemaList) {
                    List<SchemaDetail> schemas = new LinkedList<>();
                    for (SchemaDetail schemaDetail : detailList) {
                        if (schemaDetail.getSchemaId().equals(coupon.getId())) {
                            schemas.add(schemaDetail);
                        }
                    }
                    coupon.setSchemalist(schemas);
                }
            }
            result.ok();
            datas.setItemCounts(count == null ? 0 : (count.intValue()));
            datas.setPageSize(pageSize);
            datas.setCurPage(page);
            datas.setList(schemaList);
            result.setDatas(datas);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return result.error(2000, "查询列表出错 错误码2000");
        }
        return result;
    }

    /**
     * 修改方案
     *
     * @param id 方案Id
     * @return
     */
    @PutMapping(value = "/{id}")
    @RequiresPermissions("sys:sale-plan:upadte")
    @LogOperate(description = "修改售水方案")
    public Result updateCoupon(@PathVariable Integer id, @Validated @RequestBody WaterSchema schema) {
        schema.setId(id);
        Result result = new Result();
        try {
            result = waterSchemaService.update(schema);
            if(result.getRet()!=0){
                return result;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2000, "更新失败");
        }
        return result.ok();
    }

    /**
     * 删除方案
     *
     * @param id 方案ID
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @RequiresPermissions("sys:sale-plan:delete")
    @LogOperate(description = "删除售水方案")
    public Result deleteCoupon(@PathVariable Integer id) {
        Result result = new Result();
        try {
            //校验已经绑定此方案 不能删除
            ArrayList<Integer> integers = new ArrayList<>();
            integers.add(id);
            boolean b = deviceService.existsBySchemaIdIn(integers);
            if (b) {
                return result.error(2100, "此方案已分配,不能删除");
            }
            waterSchemaService.delete(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2000, "删除出错 错误码2000");
        }
        result.ok();
        return result;
    }
}
