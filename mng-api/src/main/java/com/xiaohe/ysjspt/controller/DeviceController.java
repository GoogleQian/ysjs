package com.xiaohe.ysjspt.controller;

import com.alibaba.fastjson.JSONObject;
import com.xiaohe.ysjspt.config.aspect.LogOperate;
import com.xiaohe.ysjspt.entity.*;
import com.xiaohe.ysjspt.modules.sys.shiro.ShiroUtils;
import com.xiaohe.ysjspt.service.DevStatusService;
import com.xiaohe.ysjspt.service.StatusService;
import com.xiaohe.ysjspt.utils.ArchivesLog;
import com.xiaohe.ysjspt.log.ControllerLog;
import com.xiaohe.ysjspt.log.SystemLogAspect;
import com.xiaohe.ysjspt.service.DeviceService;
import com.xiaohe.ysjspt.service.DevSysUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hzh
 */
@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @Autowired
    DevSysUserService devSysUserService;

    @Autowired
    private DevStatusService devstatusService;
    @Autowired
    private StatusService statusService;

    @Value("${super.name}")
    private String superName;
    private static final Logger log = LoggerFactory.getLogger(SystemLogAspect.class);

    @RequestMapping(value = "/fileQuery")
    @ArchivesLog(operationType = "查询操作:", operationName = "查询文件")
    public ModelAndView fileQuery(HttpServletRequest request, HttpServletResponse response) {

        return new ModelAndView("archives/fileQuery");
    }

    /**
     * 获取自己的设备
     *
     * @return
     */
    @RequestMapping(value = "/ownDev")
    public Result getAvaDev() {
        CommonResult result = new CommonResult();
        try {
            List<DeviceEntity> deviceEntities = deviceService.findDeviceByUser();
            result.setDatas(deviceEntities);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2001, "查询出错");
        }
        return result.ok();
    }


    @RequestMapping(value = "/get_dev_list")
    @ControllerLog(description = "get_dev_list")
    @RequiresPermissions("sys:dev:list")
    public Result getdDevList(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                              @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize,
                              @RequestParam(value = "device_id", required = false, defaultValue = "") String deviceId) {

        CommonResult commonResult = new CommonResult();

        if (page < 1) {
            commonResult.setMsg("参数非法");
            commonResult.setRet(2007);
            return commonResult;
        }
        page--;

        Page<DeviceEntity> all;
        long count;

        all = deviceService.findAll(page, pageSize, deviceId);

        count = all.getTotalElements();
        List<DeviceEntity> deviceEntities = all.getContent();
        List<String> stringlist = new LinkedList<>();
        for (DeviceEntity deviceEntity : deviceEntities) {
            String imei = deviceEntity.getImei();
            if (StringUtils.isNotBlank(imei)) {
                stringlist.add(imei);
            }
        }
        //查询水质设备信息
        List<Status> allByImeiIn = null;
        if (!CollectionUtils.isEmpty(stringlist)) {
            allByImeiIn = statusService.findAllByImeiIn(stringlist);
        }
        //校验水质标准
        long now = System.currentTimeMillis();
        for (DeviceEntity deviceEntity : deviceEntities) {
            int q;
            int i = deviceEntity.getPurifyTds() != null ? deviceEntity.getPurifyTds() : 0;
            if (i <= 50) {
                q = 1;
            } else if (i <= 100) {
                q = 2;
            } else if (i <= 300) {
                q = 3;
            } else {
                q = 4;
            }
            deviceEntity.setQuality(q);

            // 在离线状态
            Date shakeHandTime = deviceEntity.getShakeHandTime();
            if (shakeHandTime != null) {
                // 30秒以内视为在线，超过30秒，认为离线
                if ((now - shakeHandTime.getTime()) > (1000 * 30)) {
                    deviceEntity.setIsOnline(0);
                } else {
                    deviceEntity.setIsOnline(1);
                }
            } else {
                deviceEntity.setIsOnline(0);
            }
            //增加水质状态
            if (!CollectionUtils.isEmpty(allByImeiIn)) {
                for (Status status : allByImeiIn) {
                    if (deviceEntity.getImei().equals(status.getImei())) {
                        deviceEntity.setWaterStatus(status);
                    }
                }
            }
        }
        commonResult.setRet(0);
        commonResult.setMsg("success");

        PageBean<DeviceEntity> datas = new PageBean<>();
        datas.setCurPage(page + 1);
        datas.setItemCounts((int) count);
        datas.setPageSize(pageSize);
        datas.setList(deviceEntities);

        commonResult.setDatas(datas);
        return commonResult;

    }

    @RequestMapping(value = "/assignment_dev")
    @ControllerLog(description = "assignment_dev")
    @LogOperate(description = "分配设备")
    public Result assignmentDev(@RequestParam(value = "binded_user_id", required = false) Integer bindedUserId,
                                @RequestParam(value = "device_id", required = false, defaultValue = "") String deviceId) {
        Result result = new Result();
        DeviceEntity deviceEntity = deviceService.findByDeviceId(deviceId);

        deviceEntity.setBindedUserId(bindedUserId);
        deviceEntity.setBindedSubUserId(bindedUserId);
        SysUser sysUser = devSysUserService.findBySubId(bindedUserId);
        String brandname = sysUser.getBrandname();
        deviceEntity.setBrandname(brandname);

        deviceService.saveDevice(deviceEntity);
        result.ok();
        return result;
    }


    @RequestMapping(value = "/update_dev")
    @ControllerLog(description = "update_dev")
    @RequiresPermissions("sys:dev:upadte")
    @LogOperate(description = "更新设备")
    public Result updateDev(@RequestParam(value = "id", required = false, defaultValue = "") String id,
                            @RequestParam(value = "device_id", required = false, defaultValue = "") String deviceId,
                            @RequestParam(value = "address", required = false, defaultValue = "") String address,
                            @RequestParam(value = "phone_number", required = false, defaultValue = "") String phoneNumber,
                            @RequestParam(value = "customer", required = false, defaultValue = "") String customer,
                            @RequestParam(value = "remark", required = false, defaultValue = "") String remark,
                            @RequestParam(value = "passage_id", required = false) String passageId,
                            @RequestParam(value = "lat") double lat,
                            @RequestParam(value = "lng") double lng,
                            @RequestParam(value = "imei", required = false) String imei) {
        Result result = new Result();
        DeviceEntity deviceEntity = deviceService.findByDeviceId(deviceId);
        DeviceEntity byImei = deviceService.findByImei(imei);
        if (byImei != null) {
            //两个id不相等，则表示imei存在
            if (byImei.getId().compareTo(deviceEntity.getId()) != 0) {
                result.setMsg("该设备IMEI已存在");
                result.setRet(208);
                return result;
            }
        }
        deviceEntity.setLng(lng);
        deviceEntity.setLat(lat);
        deviceEntity.setRemark(remark);
        deviceEntity.setCustomer(customer);
        deviceEntity.setPhoneNumber(phoneNumber);
        deviceEntity.setAddress(address);
        deviceEntity.setPassageId(passageId);
        deviceEntity.setImei(imei);
        deviceEntity.setUpdateTime(new Date());
        deviceService.saveDevice(deviceEntity);
        result.ok();
        return result;
    }

    @RequestMapping(value = "/add")
    @ControllerLog(description = "add")
    @RequiresPermissions("sys:dev:save")
    @LogOperate(description = "新增设备")
    public Result add(@RequestParam(value = "device_id", required = false) String deviceId,
                      @RequestParam(value = "remark", required = false, defaultValue = "") String remark,
                      @RequestParam(value = "lat", required = false, defaultValue = "114.2564552") Double lat,
                      @RequestParam(value = "lng", required = false, defaultValue = "22.365478") Double lng,
                      @RequestParam(value = "address", required = false) String address,
                      @RequestParam(value = "phone_number", required = false) String phoneNumber,
                      @RequestParam(value = "customer", required = false) String customer,
                      @RequestParam(value = "passage_id", required = false) String passageId,
                      @RequestParam(value = "imei", required = false) String imei) {

        Result result = new Result();

        String smsUrl = "http://api.map.baidu.com/geocoder/v2/?ak=ZXNpDG0ONOKWw9EGpVUBPmmkvzUWG5Y0&location=" + lng + "," + lat + "&output=json";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(smsUrl);
        HttpResponse execute;
        JSONObject jsonResult;
        String s = "";
        try {
            execute = httpclient.execute(httpGet);
            HttpEntity entity = execute.getEntity();
            s = EntityUtils.toString(entity);
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

        jsonResult = JSONObject.parseObject(s);
        JSONObject jsonObject = jsonResult.getJSONObject("result");
        String formattedAddress = jsonObject.get("formatted_address").toString();
        JSONObject addressComponent = jsonObject.getJSONObject("addressComponent");
        String province = addressComponent.get("province").toString();
        String city = addressComponent.get("city").toString();
        String district = addressComponent.get("district").toString();

        System.out.println(formattedAddress);

        DeviceEntity deviceEntity = new DeviceEntity();
        int d = deviceService.findDevice(deviceId);
        if (d >= 1) {
            result.setMsg("该设备已存在");
            result.setRet(207);
            return result;
        }
        DeviceEntity byImei = deviceService.findByImei(imei);
        if (byImei != null) {
            result.setMsg("该设备IMEI已存在");
            result.setRet(208);
            return result;
        }
        deviceEntity.setRemark(remark);
        deviceEntity.setDeviceId(deviceId);
        deviceEntity.setBindedUserId(0);
        deviceEntity.setBindedSubUserId(1);
        deviceEntity.setPassageId(passageId);
        deviceEntity.setImei(imei);
        deviceEntity.setUpdateTime(new Date());
        if (address == null || "".equals(address)) {
            deviceEntity.setAddress(formattedAddress);
        } else {
            deviceEntity.setAddress(address);
        }
        if (customer != null) {
            deviceEntity.setCustomer(customer);
        }
        if (phoneNumber != null) {
            deviceEntity.setPhoneNumber(phoneNumber);
        }

        deviceEntity.setProvince(province);
        deviceEntity.setCity(city);
        deviceEntity.setArea(district);
        deviceEntity.setLat(lat);
        deviceEntity.setLng(lng);
        deviceEntity.setUpdateTime(new Date());
        deviceEntity.setUserId(ShiroUtils.getUserId());
        deviceService.saveDevice(deviceEntity);
        result.ok();
        return result;
    }

    @RequestMapping(value = "/delete")
    @ControllerLog(description = "delete")
    @LogOperate(description = "删除设备")
    public Result delete(@RequestParam(value = "id", required = false, defaultValue = "") Long id) {
        Result result = new Result();
        deviceService.delete(id);
        result.ok();
        return result;
    }

//    @RequestMapping("/province_count")
//    public Result getProvinceCount(){
//        CommonResult commonResult = new CommonResult();
//        List<Object[]> provinceCount = deviceService.findProvinceCount();
//        commonResult.setMsg("ok");
//        commonResult.setRet(0);
//        commonResult.setDatas(provinceCount);
//        return commonResult;
//    }

    @ControllerLog(description = "province_count")
    @RequestMapping("/province_count")
    public Result getProvinceCount() {
        CommonResult commonResult = new CommonResult();
        List<Object[]> provinceCount = deviceService.findDeviceLatLng();
        commonResult.setMsg("ok");
        commonResult.setRet(0);
        commonResult.setDatas(provinceCount);
        return commonResult;
    }

    @ControllerLog(description = "province_list")
    @RequestMapping(value = "/province_list")
    public Result findDeviceListByProvince(@RequestParam(value = "province", required = false, defaultValue = "广东省") String province) {
        CommonResult commonResult = new CommonResult();
        List<DeviceEntity> list = deviceService.findByProvince(province);
        commonResult.setMsg("ok");
        commonResult.setRet(0);
        commonResult.setDatas(list);

        return commonResult;
    }


    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return Page<DevStatus>
     */
    @RequestMapping(value = "/findAll")
    @ControllerLog(description = "findAll")
    public Page<DevStatus> findAll(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                   @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "device_id", required = false, defaultValue = "") String devId,
                                   @RequestParam(value = "timerange[0]", required = false, defaultValue = "") String startTime,
                                   @RequestParam(value = "timerange[1]", required = false, defaultValue = "") String endTime) {
        return devstatusService.findAll(page, pageSize, devId, startTime, endTime);
    }


    /**
     * 获取未指定经销商的设备
     */
    @GetMapping(value = "/unbind")
    public CommonResult findUnbind(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                   @RequestParam(value = "page_size", required = false, defaultValue = "50") Integer pageSize,
                                   @RequestParam(value = "user_id", defaultValue = "1") Long userId) {
        CommonResult result = new CommonResult();
        if (page < 1) {
            result.setMsg("参数非法");
            result.setRet(2007);
            return result;
        }
        Page<DeviceEntity> devicePage = deviceService.findUnbindDev(page - 1, pageSize, userId);
        PageBean<DeviceEntity> pageBean = new PageBean<>();
        pageBean.setCurPage(page);
        pageBean.setPageSize(pageSize);
        pageBean.setList(devicePage.getContent());
        pageBean.setItemCounts((int) devicePage.getTotalElements());
        result.setDatas(pageBean);
        result.ok();
        return result;

    }
}
