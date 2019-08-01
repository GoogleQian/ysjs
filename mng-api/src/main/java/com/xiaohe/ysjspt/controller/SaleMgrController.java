package com.xiaohe.ysjspt.controller;

import com.xiaohe.ysjspt.config.aspect.LogOperate;
import com.xiaohe.ysjspt.entity.*;
import com.xiaohe.ysjspt.modules.sys.controller.AbstractController;
import com.xiaohe.ysjspt.service.CouponService;
import com.xiaohe.ysjspt.service.DeviceService;
import com.xiaohe.ysjspt.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @program: ysjsApi
 * @description: 营销管理控制类
 * @author: Gmq
 * @date: 2018-11-07 11:20
 **/
@RestController
@RequestMapping(value = "/sale")
public class SaleMgrController extends AbstractController {
    private static Logger log = LoggerFactory.getLogger(SaleMgrController.class);
    @Autowired
    private CouponService couponService;
    @Autowired
    private DeviceService deviceService;

    /**
     * 修改设备优惠券
     * @param id 优惠券Id
     * @return
     */
    @PutMapping(value = "/coupons/{id}")
    @RequiresPermissions("sys:coupons:update")
    @LogOperate(description = "修改设备优惠券")
    public Result updateCoupon(@PathVariable Integer id,@Validated @RequestBody Coupon coupon){
        coupon.setId(id);
        Result result = null;
        try{
            result=insertCoupon(coupon);
        }catch (Exception e){
            log.error(e.getMessage());
            return result.error(2000, "更新失败");
        }
        return result;
    }

    /**
     * 新增优惠券
     * @param coupon
     * @return
     */
    @PostMapping(value = "/coupons")
    @RequiresPermissions("sys:coupons:save")
    @LogOperate(description = "新增优惠券")
    public Result insertCoupon( @Validated @RequestBody Coupon coupon) {
        Result result = new Result();
        try {
            coupon.setCreateTime(new Date());
            //校验时间范围
            long dateInterval = DateUtil.getDateInterval(coupon.getStartTime(), coupon.getEndTime());
            if(dateInterval<1){
                return result.error(2000, "截止时间必须大于起始时间");
            }
            couponService.save(coupon);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2000, "新建优惠券失败，错误码 2000");
        }
        result.ok();
        return result;
    }
    /**
     * 查询优惠券列表
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/coupons")
    @RequiresPermissions("sys:coupons:list")
    public Result queryCouponList(@RequestParam(required = false,defaultValue = "1") Integer page,@RequestParam(required = false,defaultValue = "10")Integer pageSize,
                                  @RequestParam(required = false,defaultValue = "",value = "dev_no") String devNo){
        CommonResult result=new CommonResult();
        try {
            PageBean<Coupon> datas=new PageBean<Coupon>();
            Page<Coupon> all = couponService.findAll(page-1, pageSize,devNo);
            Long count;
            List<Coupon> coupons=new ArrayList<>();
            if(all!=null){
                coupons = all.getContent();
                count = all.getTotalElements();
                result.ok();
                datas.setItemCounts(count==null?0:(count.intValue()));
                datas.setPageSize(pageSize);
                datas.setCurPage(page);
                datas.setList(deviceService.getCoupon(coupons));
                result.setDatas(datas);
            }else {
                datas.setItemCounts(0);
                datas.setPageSize(pageSize);
                datas.setCurPage(page);
                datas.setList(coupons);
                result.setDatas(datas);
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            return result.error(2000, "查询列表出错 错误码2000");
        }
        return result;
    }

    /**
     * 删除优惠券
     * @param id 优惠券ID
     * @return
     */
    @DeleteMapping(value = "/coupons/{id}")
    @RequiresPermissions("sys:coupons:delete")
    @LogOperate(description = "删除优惠券")
    public Result deleteCoupon(@PathVariable Integer id){
        Result result=new Result();
        try {
            couponService.delete(id);
        }catch (Exception e){
            log.error(e.getMessage());
            return result.error(2000, "查询列表出错 错误码2000");
        }
        result.ok();
        return result;
    }

    /**
     * 获取设备ID列表
     * @return
     */
    @GetMapping(value = "/coupons/devIds")
    public Result getDevIds(@RequestParam(value = "id",required = false)Long id,@RequestParam(value = "device_id",required = false)String devNo){
        CommonResult result=new CommonResult();
        try {
            //查询当前用户下的dev列表
            List<DeviceEntity> ownDevList = deviceService.findDeviceByUser();
            //过滤是否绑定优惠券
            List<BigInteger> devIdList = couponService.findDevIdList();
            List<DeviceEntity> entities = new LinkedList<>();
            //加入自身设备
            if(StringUtils.isNotBlank(devNo)&&id!=null){
                DeviceEntity deviceEntity = new DeviceEntity();
                deviceEntity.setId(id);
                deviceEntity.setDeviceId(devNo);
                entities.add(deviceEntity);
            }
            //加入未绑定优惠券设备
            for (DeviceEntity deviceEntity : ownDevList) {
                if (!devIdList.contains(BigInteger.valueOf(deviceEntity.getId()))) {
                    entities.add(deviceEntity);
                }
            }
            result.ok();
            result.setDatas(entities);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            return result.error(2000, "查询列表出错 错误码2000");
        }
        return result;
    }


}
