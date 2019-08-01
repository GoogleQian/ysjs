package com.xiaohe.ysjspt.modules.errorLog.controller;

import com.xiaohe.ysjspt.config.aspect.LogOperate;
import com.xiaohe.ysjspt.entity.CommonResult;
import com.xiaohe.ysjspt.entity.PageBean;
import com.xiaohe.ysjspt.entity.Result;
import com.xiaohe.ysjspt.modules.errorLog.entity.ErrorLog;
import com.xiaohe.ysjspt.modules.errorLog.entity.QueryErrorLog;
import com.xiaohe.ysjspt.modules.errorLog.service.ErrorLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * ErrorLogController层
 *
 * @author gmq
 * @since 2018-12-25
 */
@RestController
@RequestMapping(value = "/errorLogs")
public class ErrorLogController {
    private static Logger log = LoggerFactory.getLogger(ErrorLogController.class);
    @Autowired
    private ErrorLogService errorLogService;


    /**
     * 保存对象<br/>
     *
     * @param errorLog 对象
     */
    @PostMapping(value = "/")
    public Result save(@Validated @RequestBody ErrorLog errorLog) {
        Result result = new Result();
        try {
            errorLogService.save(errorLog);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2001, "新增失败");
        }
        return result.ok();
    }

    /**
     * 更新
     *
     * @param errorLog
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result updateBanner(@Validated @RequestBody ErrorLog errorLog) {
        Result result = new Result();
        try {
            boolean exists = errorLogService.exists(errorLog.getId());
            if (!exists) {
                return result.error(2002, "修改失败，未找到");
            }
            errorLogService.save(errorLog);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2002, "修改失败");
        }
        return result.ok();
    }

    /**
     * 通过id集合删除对象
     *
     * @param id id集合
     */
    @DeleteMapping(value = "/{id}")
    public Result deleteById(@PathVariable("id") Integer id) {
        Result result = new Result();
        try {
            errorLogService.delete(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2003, "删除失败");
        }
        return result.ok();
    }


    /**
     * 通过id查找对象
     *
     * @param id id
     * @return ErrorLog 对象
     */
    @GetMapping(value = "/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        CommonResult result = new CommonResult();
        try {
            result.setDatas(errorLogService.findById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2004, "不存在");
        }
        return result.ok();
    }

    /**
     * 分页查询
     *
     * @return Page<ErrorLog> 对象
     */
    @PostMapping(value = "")
    @RequiresPermissions("sys:errorLogs:list")
    public Result findByPage(@RequestBody QueryErrorLog queryErrorLog) {

        CommonResult result = new CommonResult();
        try {
            int page = queryErrorLog.getPage();
            int pageSize = queryErrorLog.getPageSize();
            Page<ErrorLog> all = errorLogService.findAll(page - 1, pageSize, queryErrorLog);
            PageBean<ErrorLog> pageBean = new PageBean<>();
            if (all == null) {
                pageBean.setList(new ArrayList<>());
                result.setDatas(pageBean);
                return result.ok();
            }
            pageBean.setCurPage(page);
            pageBean.setItemCounts((int) all.getTotalElements());
            pageBean.setPageSize(pageSize);
            pageBean.setList(all.getContent());
            result.setDatas(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return result.error(2005, "查询出错");
        }
        return result.ok();
    }
}