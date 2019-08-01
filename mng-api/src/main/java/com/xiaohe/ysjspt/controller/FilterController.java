package com.xiaohe.ysjspt.controller;



import com.xiaohe.ysjspt.config.aspect.LogOperate;
import com.xiaohe.ysjspt.entity.*;
import com.xiaohe.ysjspt.log.ControllerLog;
import com.xiaohe.ysjspt.service.DeviceService;
import com.xiaohe.ysjspt.service.FilterChangeService;
import com.xiaohe.ysjspt.service.FilterService;
import com.xiaohe.ysjspt.service.ServiceService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/**
 * @author
 * wzq
 */

@RestController
@RequestMapping("/filter")
public class FilterController {

    @Autowired
    DeviceService deviceService;
    @Autowired
    FilterService filterService;
    @Autowired
    FilterChangeService filterChangeService;
    @Autowired
    ServiceService serviceService;


    @RequestMapping(value = "/test")
    public Result test(){
        CommonResult commonResult = new CommonResult();
        commonResult.setMsg("成功");
        commonResult.setRet(0);
        return commonResult;
    }

    /**
     *  设置单个设备每根滤芯预计更换时间
     * @param deviceId
     * @param setDate
     * @return
     */

    @RequestMapping(value = "/set_replace_date")
    @ControllerLog(description = "set_replace_date")
    @LogOperate(description = "设置滤芯更更换时间")
    public Result setReplaceDate(@RequestParam(value="device_id",required = true) String deviceId,
                                @RequestParam(value="set_date",required = true) String setDate) {

        CommonResult commonResult = new CommonResult();
        //||customer =="" || address =="" || tel ==""
        if (deviceId == "" || setDate == ""){
            commonResult.setMsg("参数非法");
            commonResult.setRet(1);
            return commonResult;
        }


        int selectDevice = filterService.selectDevice(deviceId);
        if(selectDevice > 0){
            commonResult.setMsg("该设备已生成");
            commonResult.setRet(2);
            return commonResult;
        }


        String firstFilter = "";
        String secondFilter = "";
        String thirdFilter = "";
        String fourthFilter = "";

        String customer = "";
        String phoneNumber = "";
        String address = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date date = sdf.parse(setDate);
            Date now = new Date();
            Timestamp timestamp = new Timestamp(now.getTime());
            Date sDate = date;
            Calendar cal = Calendar.getInstance();
            cal.setTime(sDate);
            cal.add(Calendar.MONTH,8);
            firstFilter =  sdf.format(cal.getTime());
            cal.add(Calendar.MONTH,8);
            secondFilter = sdf.format(cal.getTime());
            cal.add(Calendar.MONTH,8);
            thirdFilter = sdf.format(cal.getTime());
            fourthFilter = sdf.format(cal.getTime());

            //查找客户信息
            DeviceEntity  custInfo = deviceService.findCustInfoByDevId(deviceId);
            if(custInfo != null) {
                address = custInfo.getAddress();
                phoneNumber = custInfo.getPhoneNumber();
                customer = custInfo.getCustomer();
            }

            filterService.updateSetDateByDeviceId(setDate,deviceId);
            //customer,address,phone_number
            filterService.insertFilterList(deviceId,firstFilter,secondFilter,thirdFilter,fourthFilter,customer,address,phoneNumber);
        } catch (ParseException e) {
            e.printStackTrace();
      }
        commonResult.setMsg("成功");
        commonResult.setRet(0);
        return commonResult;
    }

    /**
     * 生成当月滤芯更换计划
     */
    @RequestMapping(value = "/gen_replace_plan")
    @ControllerLog(description = "gen_replace_plan")
    @LogOperate(description = "生成当月滤芯更换计划")
    public Result genReplacePlan(@RequestParam(value="service_id",required = true) Integer serviceId,
                                 @RequestParam(value="key",required = true) String key){
        CommonResult commonResult = new CommonResult();
        List<FilterEntity> filterList =  filterService.findFilter();

        String datas = insertReplacePlan(filterList);

        commonResult.setRet(0);
        commonResult.setMsg("success");
        commonResult.setDatas(datas);
        return commonResult;
    }

    public String insertReplacePlan(List<FilterEntity> filterList){
        CommonResult commonResult = new CommonResult();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String now = sdf.format(date);
        String thisYear = now.substring(0,4);
        String thisMonth = now.substring(5,7);

        long count;
        Integer successCount = 0;
        Integer page = 1;
        page--;
        count = filterList.toArray().length;

        int firstStatus;
        int secondStatus;
        int thirdStatus;
        int fourthStatus;
        //判断有无重复
        for( int i = 0 ; i < count ; i++) {
            FilterEntity allFilter = filterList.get(i);
            Boolean back = false;
            List<String> changeList =  filterChangeService.findChangeList(allFilter.getDeviceId());
            if(!changeList.isEmpty()){
                for(String needTime : changeList) {
                    String needTimeYear = needTime.substring(0,4);
                    String needTimeMonth = needTime.substring(5,7);
                    if(needTimeYear.equals(thisYear) && needTimeMonth.equals(thisMonth)){
                        back = true;
                        continue;
                    }
                }
                if(back){
                    continue;
                }
            }

            //月份差(和当前月份比较 -- 1:小与  3:等于  -1:大于)
            int firstFilter = now.compareTo(allFilter.getFirstFilter().substring(0,7));
            int secondFilter = now.compareTo(allFilter.getSecondFilter().substring(0,7));
            //3,4相同
            int thirdFilter = now.compareTo(allFilter.getThirdFilter().substring(0,7));

            if(firstFilter == 3){
                if(secondFilter == 3){
                    if(thirdFilter == 3){
                        firstStatus = 0;
                        secondStatus = 0;
                        thirdStatus = 0;
                        fourthStatus = 0;
                        filterService.insertChangeList(allFilter.getDeviceId(),firstStatus,secondStatus,thirdStatus,fourthStatus,allFilter.getCust(),allFilter.getAddress(),allFilter.getTel(), allFilter.getFirstFilter(),0);
                        successCount++;
                        continue;
                    }
                    firstStatus = 0;
                    secondStatus = 0;
                    thirdStatus = 1;
                    fourthStatus = 1;
                    filterService.insertChangeList(allFilter.getDeviceId(),firstStatus,secondStatus,thirdStatus,fourthStatus,allFilter.getCust(),allFilter.getAddress(),allFilter.getTel(), allFilter.getFirstFilter(),0);
                    successCount++;
                    continue;
                }
                firstStatus = 0;
                secondStatus = 1;
                thirdStatus = 1;
                fourthStatus = 1;
                filterService.insertChangeList(allFilter.getDeviceId(),firstStatus,secondStatus,thirdStatus,fourthStatus,allFilter.getCust(),allFilter.getAddress(),allFilter.getTel(), allFilter.getFirstFilter(),0);
                successCount++;
                continue;
            }
        }
        PageBean<FilterEntity> datas = new PageBean<FilterEntity>();
        datas.setCurPage(page + 1);
        datas.setItemCounts((int)count);
        return  "count:"+count+",successCount:"+successCount;
    }

    /**
     *  回写滤芯更换信息
     * @param deviceId
     * @param planReplaceTime
     * @param realReplaceTime
     * @param replaceFirstFilter
     * @param replaceSecondFilter
     * @param replaceThirdFilter
     * @param replaceFourthFilter
     * @param repairer
     * @param repairerPhoneNumber
     * @param serviceId
     * @param key
     * @param remark
     * @return
     */

    @RequestMapping(value = "/set_repair_info")
    @ControllerLog(description = "set_repair_info")
    @LogOperate(description = "回写滤芯更换信息")
    public Result setRepairInfo(@RequestParam(value="device_id",required = true) String deviceId,
                               @RequestParam(value="plan_replace_time",required = true) String planReplaceTime,
                               @RequestParam(value ="real_replace_time",required = true)String realReplaceTime,
                               @RequestParam(value="replace_first_filter",required = true) Integer replaceFirstFilter,
                               @RequestParam(value="replace_second_filter",required = true) Integer replaceSecondFilter,
                               @RequestParam(value="replace_third_filter",required = true) Integer replaceThirdFilter,
                               @RequestParam(value="replace_fourth_filter",required = true) Integer replaceFourthFilter,
                                @RequestParam(value="repairer",required = false) String repairer,
                                @RequestParam(value="repairer_phone_number",required = false) String  repairerPhoneNumber,
                               @RequestParam(value="service_id",required = true) Integer serviceId,
                               @RequestParam(value="key",required = true) String key,
                                @RequestParam(value="remark",required = false) String remark){
        CommonResult commonResult = new CommonResult();

        if (deviceId == "" || planReplaceTime == ""){
            commonResult.setMsg("参数非法");
            commonResult.setRet(2007);
            return commonResult;
        }



        int replaceFilterInfi = serviceService.selectReplaceFilterInfo(deviceId);
        if(replaceFilterInfi <= 0){
            commonResult.setMsg("没有对应换芯计划");
            commonResult.setRet(2);
            return commonResult;
        }

        String firstFilter = "";
        String secondFilter = "";
        String thirdFilter = "";
        String fourthFilter = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            java.util.Date date = sdf.parse(realReplaceTime);
            Date sDate = date;
            Calendar cal = Calendar.getInstance();
            cal.setTime(sDate);
            if(replaceFirstFilter == 0){
                cal.add(Calendar.MONTH,8);
                firstFilter =  sdf.format(cal.getTime());
                filterService.updateFirstFilterByDevId(deviceId,firstFilter);
        }
            if(replaceSecondFilter == 0){
                cal.add(Calendar.MONTH,8);
                secondFilter = sdf.format(cal.getTime());
                filterService.updateSecondFilterByDevId(deviceId,secondFilter);
            }
            if(replaceThirdFilter == 0){
                cal.add(Calendar.MONTH,8);
                thirdFilter = sdf.format(cal.getTime());
                filterService.updateThirdFilterByDevId(deviceId,thirdFilter);
            }
            if(replaceFourthFilter == 0){

                fourthFilter = sdf.format(cal.getTime());
                filterService.updateFourthFilterByDevId(deviceId,fourthFilter);
            }
            if(repairer!=null || repairerPhoneNumber!=null){
                filterService.updateRepairerInfo(repairer,repairerPhoneNumber,deviceId);
            }
            filterService.updateChangeList(repairer,repairerPhoneNumber,remark,deviceId,planReplaceTime);
        }
        catch (ParseException e) {
                e.printStackTrace();
            }


        commonResult.setRet(0);
        commonResult.setMsg("success");
        commonResult.setDatas("");
        return commonResult;
    }

    /**
     *  获取滤芯更换计划
     * @param deviceId
     * @param year
     * @param month
     * @param replaceStatus
     * @param serviceId
     * @param key
     * @return
     */
    @ControllerLog(description = "get_replace_plan")
    @RequestMapping(value = "/get_replace_plan")
    @LogOperate(description = "获取滤芯更换计划")
    public Result getReplacePlan(@RequestParam(value="device_id",required = false,defaultValue = "") String deviceId,
                                 @RequestParam(value="year",required = false) String year,
                                 @RequestParam(value="month",required = false) String month,
                                 @RequestParam(value="replace_status",required = false,defaultValue = "0") Integer replaceStatus,
                                 @RequestParam(value="service_id",required = true) Integer serviceId,
                                 @RequestParam(value="key",required = true) String key){

        CommonResult commonResult = new CommonResult();
        if (serviceId == null || key == ""){
            commonResult.setMsg("参数非法");
            commonResult.setRet(2007);
            return commonResult;
        }




        String date = "";
        if(month !=null || year !=null){
            date= year+"-"+month;
        }
        Integer page = 1;
        page--;
        List<FilterChangeEntity> filterList =  filterChangeService.findChange(deviceId,date,replaceStatus);

        long count;
        count = filterList.toArray().length;

        commonResult.setRet(0);
        commonResult.setMsg("success");

        PageBean<FilterChangeEntity> datas = new PageBean<>();
        datas.setCurPage(page + 1);
        datas.setItemCounts((int)count);
        datas.setPageSize(10);
        datas.setList(filterList);
        commonResult.setDatas(datas);
        return commonResult;
    }
}
