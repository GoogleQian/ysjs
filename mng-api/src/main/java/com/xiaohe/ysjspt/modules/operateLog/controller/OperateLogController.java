package com.xiaohe.ysjspt.modules.operateLog.controller;

import com.xiaohe.ysjspt.entity.CommonResult;
import com.xiaohe.ysjspt.entity.PageBean;
import com.xiaohe.ysjspt.entity.Result;
import com.xiaohe.ysjspt.modules.operateLog.entity.OperateLog;
import com.xiaohe.ysjspt.modules.operateLog.entity.QueryOperateLog;
import com.xiaohe.ysjspt.modules.operateLog.service.OperateLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 *
 *  OperateLogController层
 *
 * @author gmq
 * @since 2018-12-25
 */
@RestController
@RequestMapping(value = "/operateLogs")
public class OperateLogController {
    private static Logger log = LoggerFactory.getLogger(OperateLogController.class);
    @Autowired
    private OperateLogService operateLogService;


   	/**
	 * 保存对象<br/>
	 *
	 * @param operateLog 对象
	 */
     @PostMapping(value = "/")
	public Result save(@Validated @RequestBody OperateLog operateLog) {
        Result result =new Result();
        try{
		    operateLogService.save(operateLog);
        }catch (Exception e){
            log.error(e.getMessage());
            return result.error(2001, "新增失败");
        }
        return result.ok();
	}

	 /**
     * 更新
     *
     * @param operateLog
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result updateBanner( @Validated @RequestBody OperateLog operateLog) {
        Result result =new Result();
        try{
            boolean exists = operateLogService.exists(operateLog.getId());
            if (!exists) {
            return result.error(2002, "修改失败，未找到");
            }
            operateLogService.save(operateLog);
        }catch (Exception e){
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
        Result result =new Result();
        try{
             operateLogService.delete(id);
        }catch (Exception e){
            log.error(e.getMessage());
            return result.error(2003, "删除失败");
        }
        return result.ok();
	}


	/**
	 * 通过id查找对象
	 *
	 * @param id id
	 * @return LogOperate 对象
	 */
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable("id") Integer id) {
        CommonResult result=new CommonResult();
        try {
            result.setDatas(operateLogService.findById(id));
        }catch (Exception e){
            log.error(e.getMessage());
            return result.error(2004, "不存在");
        }
        return result.ok();
	}

	/**
	 * 分页查询
	 *
	 * @return Page<LogOperate> 对象
	 */
	@PostMapping(value = "")
    @RequiresPermissions("sys:operateLogs:list")
	public Result findByPage(@RequestBody QueryOperateLog queryOperateLog) {

        CommonResult result=new CommonResult();
        try {
            int page = queryOperateLog.getPage();
            int pageSize = queryOperateLog.getPageSize();
            Page<OperateLog> all =  operateLogService.findAll(page-1, pageSize,queryOperateLog);
            PageBean<OperateLog> pageBean = new PageBean<>();
            if (all == null) {
            pageBean.setList(new ArrayList<>());
            result.setDatas(pageBean);
            return result.ok();
            }
            pageBean.setCurPage(page);
            pageBean.setItemCounts((int)all.getTotalElements());
            pageBean.setPageSize(pageSize);
            pageBean.setList(all.getContent());
            result.setDatas(pageBean);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            return result.error(2005, "查询出错");
        }
        return result.ok();
	}
}