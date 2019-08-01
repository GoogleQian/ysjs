package com.xiaohe.ysjspt.controller;

import com.xiaohe.ysjspt.entity.CommonResult;
import com.xiaohe.ysjspt.entity.Result;
import com.xiaohe.ysjspt.entity.SellProgramEntity;
import com.xiaohe.ysjspt.log.ControllerLog;
import com.xiaohe.ysjspt.service.SellProgramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

/**
 * TbSellProgramController层
 *
 * @author wzq
 * @since 2018-08-28
 */
@RestController
@RequestMapping(value = "/sellProgram")
public class SellProgramController {
    private static Logger log = LoggerFactory.getLogger(SellProgramController.class);
    @Autowired
    private SellProgramService sellProgramService;

    /**
     * 保存对象<br/>
     *
     * @param sellProgram
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    @ControllerLog(description = "save")
    public Result save(@RequestBody SellProgramEntity sellProgram) {
        Integer idBefore = sellProgram.getId();
        Result result = new Result();
        result.setRet(0);
        if (idBefore == null) {
            Date now = new Date();
            sellProgram.setCreateTime(now);
            result.setMsg("插入成功");
            sellProgramService.save(sellProgram);
        } else {
            sellProgramService.save(sellProgram);
            result.setMsg("更新成功");
        }
        return result;
    }

    /**
     * 删除对象
     *
     * @param sellProgram
     */
    @RequestMapping(value = "/delete")
    @ControllerLog(description = "delete")
    public Result delete(SellProgramEntity sellProgram) {
        CommonResult commonResult = new CommonResult();
        sellProgramService.delete(sellProgram);
        commonResult.setRet(0);
        commonResult.setMsg("success");
        return commonResult;
    }


    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return Page<SellProgram>
     */
    @RequestMapping(value = "/findAll")
    @ControllerLog(description = "findAll")
    public Page<SellProgramEntity> findAll(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                     @RequestParam(value = "page_size", required = false, defaultValue = "9999") Integer pageSize) {
        Page<SellProgramEntity> all = sellProgramService.findAll(page, pageSize);
        return all;
    }



}