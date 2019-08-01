package com.xiaohe.ysjspt.modules.loginLog.controller;


import com.xiaohe.ysjspt.config.aspect.LogOperate;
import com.xiaohe.ysjspt.entity.CommonResult;
import com.xiaohe.ysjspt.entity.PageBean;
import com.xiaohe.ysjspt.entity.Result;
import com.xiaohe.ysjspt.modules.loginLog.entity.LoginLog;
import com.xiaohe.ysjspt.modules.loginLog.entity.QueryLoginLog;
import com.xiaohe.ysjspt.modules.loginLog.service.LoginLogService;
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
 *  LoginLogController层
 *
 * @author gmq
 * @since 2018-12-25
 */
@RestController
@RequestMapping(value = "/loginLogs")
public class LoginLogController {
    private static Logger log = LoggerFactory.getLogger(LoginLogController.class);
    @Autowired
    private LoginLogService loginLogService;


   	/**
	 * 保存对象<br/>
	 *
	 * @param loginLog 对象
	 */
     @PostMapping(value = "/")
	public Result save(@Validated @RequestBody LoginLog loginLog) {
        Result result =new Result();
        try{
		    loginLogService.save(loginLog);
        }catch (Exception e){
            log.error(e.getMessage());
            return result.error(2001, "新增失败");
        }
        return result.ok();
	}

	 /**
     * 更新
     *
     * @param loginLog
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result updateBanner( @Validated @RequestBody LoginLog loginLog) {
        Result result =new Result();
        try{
            boolean exists = loginLogService.exists(loginLog.getId());
            if (!exists) {
            return result.error(2002, "修改失败，未找到");
            }
            loginLogService.save(loginLog);
        }catch (Exception e){
            log.error(e.getMessage());
            return result.error(2002, "修改失败");
        }
        return result.ok();
     }

	/**
	 * 通过id删除对象
	 *
	 * @param id id
	 */
	@DeleteMapping(value = "/{id}")
	public Result deleteById(@PathVariable("id") Integer id) {
        Result result =new Result();
        try{
             loginLogService.delete(id);
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
	 * @return LoginLog 对象
	 */
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable("id") Integer id) {
        CommonResult result=new CommonResult();
        try {
            result.setDatas(loginLogService.findById(id));
        }catch (Exception e){
            log.error(e.getMessage());
            return result.error(2004, "不存在");
        }
        return result.ok();
	}

	/**
	 * 分页查询
	 *
	 * @return Page<LoginLog> 对象
	 */
	@PostMapping(value = "")
    @RequiresPermissions("sys:loginLogs:list")
    public Result findByPage(@RequestBody QueryLoginLog queryLoginLog) {

        CommonResult result=new CommonResult();
        try {
            int page = queryLoginLog.getPage();
            int pageSize = queryLoginLog.getPageSize();
            Page<LoginLog> all =  loginLogService.findAll(page-1, pageSize,queryLoginLog);
            PageBean<LoginLog> pageBean = new PageBean<>();
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