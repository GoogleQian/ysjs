package com.xiaohe.ysjspt.service;


import com.xiaohe.ysjspt.entity.FilterEntity;
import com.xiaohe.ysjspt.jpa.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
    *
    * 滤芯service服务类
    *
    * @version
    * <pre>
    * @Author	Version		Date		Changes
    * wzq    1.0  2018年05月25日 Created
    *
    * </pre>
    * @since 1.
    */
@Service("filterService")
public class FilterService {
    @Autowired
    private FilterRepository filterRepository;

    public List<FilterEntity> findSetDate(){
        return  filterRepository.findSetDate();
    }

    public void updateSetDateByDeviceId(String setDate, String deviceId) {
        filterRepository.updateSetDateByDeviceId(setDate,deviceId);
    }
    public void updateChangeList(String repairer, String repairerPhoneNumber,String remark,String devId,String planReplaceTime) {
        filterRepository.updateChangeList(repairer,repairerPhoneNumber,remark,devId,planReplaceTime);
    }



    public void insertFilterList(String deviceId,String firstFilter,String secondFilter,String thirdFilter,String fourthFilter,String customer,String address,String phoneNumber) {
        //,customer,address,tel
        filterRepository.insertFilterList(deviceId,firstFilter,secondFilter,thirdFilter,fourthFilter,customer,address,phoneNumber);
    }
    public List<FilterEntity> findFilter(){
        return  filterRepository.findFilter();
    }

    public void insertChangeList(String deviceId,int firstFilter,int secondFilter,int thirdFilter,int fourthFilter,String customer,String address,String tel,String needTime,int status) {
        filterRepository.insertChangeList(deviceId,firstFilter,secondFilter,thirdFilter,fourthFilter,customer,address,tel,needTime,status);
    }

    public void updateFilterByDevId(String deviceId,String firstFilter,String secondFilter,String thirdFilter,String fourthFilter){
        filterRepository.updateFilterByDevId(deviceId,firstFilter,secondFilter,thirdFilter,fourthFilter);
    }

    public void updateFirstFilterByDevId(String deviceId,String firstFilter){
        filterRepository.updateFirstFilterByDevId(deviceId,firstFilter);
    }

    public void updateSecondFilterByDevId(String deviceId,String secondFilter){
        filterRepository.updateSecondFilterByDevId(deviceId,secondFilter);
    }

    public void updateThirdFilterByDevId(String deviceId,String thirdFilter){
        filterRepository.updateThirdFilterByDevId(deviceId,thirdFilter);
    }

    public void updateFourthFilterByDevId(String deviceId,String fourthFilter){
        filterRepository.updateFourthFilterByDevId(deviceId,fourthFilter);
    }
    public void updateRepairerInfo(String repairer,String repairerPhoneNumber,String deviceId){
        filterRepository. updateRepairerInfo(repairer,repairerPhoneNumber,deviceId);
    }



    public int selectDevice(String deviceId){
       return filterRepository.selectDevice(deviceId);
    }

}
