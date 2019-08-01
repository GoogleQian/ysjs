
package com.xiaohe.ysjspt.modules.sys.controller;

import com.xiaohe.ysjspt.config.aspect.LogOperate;
import com.xiaohe.ysjspt.config.validate.ValidatorUtils;
import com.xiaohe.ysjspt.modules.sys.entity.SysRoleEntity;
import com.xiaohe.ysjspt.modules.sys.service.SysRoleMenuService;
import com.xiaohe.ysjspt.modules.sys.service.SysRoleService;
import com.xiaohe.ysjspt.modules.sys.untils.PageUtils;
import com.xiaohe.ysjspt.modules.sys.untils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;


    /**
     * 角色列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysRoleService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 角色列表
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:role:select")
    public R select() {
        List<SysRoleEntity> list = sysRoleService.selectList(null);

        return R.ok().put("list", list);
    }

    /**
     * 角色信息
     */
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public R info(@PathVariable("roleId") Long roleId) {
        SysRoleEntity role = sysRoleService.selectById(roleId);

        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);



        return R.ok().put("role", role);
    }

    /**
     * 保存角色
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    @LogOperate(description = "保存角色")
    public R save(@RequestBody SysRoleEntity role) {
        ValidatorUtils.validateEntity(role);

        sysRoleService.save(role);

        return R.ok();
    }

    /**
     * 修改角色
     */

    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    @LogOperate(description = "修改角色")
    public R update(@RequestBody SysRoleEntity role) {
        ValidatorUtils.validateEntity(role);

        sysRoleService.update(role);

        return R.ok();
    }

    /**
     * 删除角色
     */

    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    @LogOperate(description = "删除角色")
    public R delete(@RequestBody Long[] roleIds) {
        sysRoleService.deleteBatch(roleIds);

        return R.ok();
    }
}
