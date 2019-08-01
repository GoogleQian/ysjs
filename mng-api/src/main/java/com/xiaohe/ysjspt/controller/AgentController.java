package com.xiaohe.ysjspt.controller;


import com.xiaohe.ysjspt.entity.*;
import com.xiaohe.ysjspt.log.ControllerLog;
import com.xiaohe.ysjspt.service.DeviceService;
import com.xiaohe.ysjspt.service.DevSysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * @author
 * Administrator
 */
@RestController
@RequestMapping("/agent")
public class AgentController{

    @Value("${super.name}")
    private String superName;

    @Autowired
    DevSysUserService devSysUserService;

    @Autowired
    DeviceService deviceService;


    @RequestMapping(value = "/add")
    @ControllerLog(description = "add")
    public Result add(@RequestParam(value="parent_id",required = false) Integer parentId,
                      @RequestParam(value="brandname",required = false) String brandname,
                      @RequestParam(value="username",required = false) String username,
                      @RequestParam(value="password",required = false,defaultValue = "123456") String password
                      ){

        Result result = new Result();
        SysUser sysUser = new SysUser();

        SysUser sysUser1 = devSysUserService.findByBrandname(brandname);
        if(sysUser1!=null){
            result.setMsg("该商户名已存在");
            result.setRet(207);
            return result;
        }
        Integer maxId = devSysUserService.findMostId();
        Integer id = Math.toIntExact(maxId);
        sysUser.setSubId(id+1);
        sysUser.setParentId(id+1);
        sysUser.setName(username);
        sysUser.setBrandname(brandname);
        sysUser.setPwd(password);
        devSysUserService.save(sysUser);

        result.ok();
        return result;

    }

    @RequestMapping(value = "/delete")
    @ControllerLog(description = "delete")
    public Result delete(@RequestParam(value="id",required = true) Long id){

        Result result = new Result();
        SysUser sysUser = devSysUserService.findOne(id);
        String parentBrandname = sysUser.getBrandname();

        if(parentBrandname!=null){
            List<DeviceEntity> list = deviceService.findByBrandname(parentBrandname);
            if(list!=null){
                for (DeviceEntity deviceEntity : list) {
                    deviceEntity.setBrandname(superName);
                    deviceService.saveDevice(deviceEntity);
                }
            }
        }

        List<SysUser> list = devSysUserService.findByParentId(sysUser.getParentId());
        for (SysUser user : list) {
            Long id1 = user.getId();
            devSysUserService.deleteById(id1);
        }

        result.ok();
        return result;
    }


    @RequestMapping(value = "/update")
    @ControllerLog(description = "update")
    public Result update(@RequestParam(value="id",required = false) Long id,
                         @RequestParam(value="brandname",required = false) String brandname,
                         @RequestParam(value="username",required = false) String username,
                         @RequestParam(value="password",required = false,defaultValue = "123456") String password){

        SysUser sysUser= devSysUserService.findOne(id);
        String brandname1 = sysUser.getBrandname();
        Result result = new Result();
        SysUser sysUser1 = devSysUserService.findByBrandname(brandname);
        if(sysUser1!=null&&sysUser1.getBrandname()!=brandname1){
            result.setMsg("该商户名已存在");
            result.setRet(207);
            return result;
        }
        sysUser.setPwd(password);
        sysUser.setBrandname(brandname);
        List<DeviceEntity> list = deviceService.findByBrandname(sysUser.getBrandname());
        for (DeviceEntity deviceEntity : list) {
            deviceEntity.setBrandname(brandname);
            deviceService.saveDevice(deviceEntity);
        }
        sysUser.setName(username);
        devSysUserService.save(sysUser);
        result.ok();
        return result;
    }

    @RequestMapping(value = "/get_agent_list")
    @ControllerLog(description = "get_agent_list")
    public Result getAgentList(@RequestParam(value="parent_id",required = true) Integer parentId,
                               @RequestParam(value="page",required = false,defaultValue = "1") Integer page,
                               @RequestParam(value="page_size",required = false,defaultValue = "10") Integer pageSize){

        CommonResult commonResult = new CommonResult();

        if (page < 1 ){
            commonResult.setMsg("参数非法");
            commonResult.setRet(2007);
            return commonResult;
        }
        page--;

        Page<SysUser> all;
        long count;

        all =  devSysUserService.findAll(page,pageSize);

        count = all.getTotalElements();
        List<SysUser> sysUsers = all.getContent();

        commonResult.setRet(0);
        commonResult.setMsg("success");

        PageBean<SysUser> datas = new PageBean<SysUser>();
        datas.setCurPage(page + 1);
        datas.setItemCounts((int)count);
        datas.setPageSize(pageSize);
        datas.setList(sysUsers);

        commonResult.setDatas(datas);
        return commonResult;
    }

}
