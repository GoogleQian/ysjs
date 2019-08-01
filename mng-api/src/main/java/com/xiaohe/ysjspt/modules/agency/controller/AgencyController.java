package com.xiaohe.ysjspt.modules.agency.controller;

import com.xiaohe.ysjspt.config.aspect.LogOperate;
import com.xiaohe.ysjspt.entity.CommonResult;
import com.xiaohe.ysjspt.entity.PageBean;
import com.xiaohe.ysjspt.entity.Result;
import com.xiaohe.ysjspt.modules.agency.entity.Agency;
import com.xiaohe.ysjspt.modules.agency.entity.QueryAgency;
import com.xiaohe.ysjspt.modules.agency.service.AgencyService;
import com.xiaohe.ysjspt.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * 经销商表 AgencyController层
 *
 * @author gmq
 * @since 2019-01-09
 */
@RestController
@RequestMapping(value = "/agencys")
public class AgencyController extends AbstractController {
    private static Logger log = LoggerFactory.getLogger(AgencyController.class);
    @Autowired
    private AgencyService agencyService;


    /**
     * 保存对象<br/>
     *
     * @param agency 对象
     */
    @PostMapping
    @RequiresPermissions("sys:agency:save")
    @LogOperate(description = "新增Agency")
    public Result save(@Validated @RequestBody Agency agency) {
        Result result = new Result();
        try {
            agencyService.save(agency);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2001, "新增失败");
        }
        return result.ok();
    }

    /**
     * 更新
     *
     * @param agency
     * @return
     */
    @PutMapping(value = "/{id}")
    @RequiresPermissions("sys:agency:update")
    @LogOperate(description = "更新Agency")
    public Result updateBanner(@Validated @RequestBody Agency agency) {
        Result result = new Result();
        try {
            boolean exists = agencyService.exists(agency.getId());
            if (!exists) {
                return result.error(2002, "修改失败，未找到");
            }
            agencyService.save(agency);
        } catch (Exception e) {
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
    @RequiresPermissions("sys:agency:delete")
    @LogOperate(description = "删除Agency")
    public Result deleteById(@PathVariable("id") Integer id) {
        Result result = new Result();
        try {
            agencyService.delete(id);
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
     * @return Agency 对象
     */
    @GetMapping(value = "/{id}")
    @RequiresPermissions("sys:agency:info")
    public Result findById(@PathVariable("id") Integer id) {
        CommonResult result = new CommonResult();
        try {
            result.setDatas(agencyService.findById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2004, "不存在");
        }
        return result.ok();
    }

    /**
     * 分页查询
     *
     * @return Page<Agency> 对象
     */
    @PostMapping(value = "/pages")
    @RequiresPermissions("sys:agency:list")
    public Result findByPage(@RequestBody QueryAgency queryAgency) {

        CommonResult result = new CommonResult();
        try {
            int page = queryAgency.getPage();
            int pageSize = queryAgency.getPageSize();
            queryAgency.setUserId(getUserId());
            Page<Agency> all = agencyService.findAll(page - 1, pageSize, queryAgency);
            PageBean<Agency> pageBean = new PageBean<>();
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