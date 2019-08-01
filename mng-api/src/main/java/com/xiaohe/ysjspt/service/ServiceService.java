package com.xiaohe.ysjspt.service;

import com.xiaohe.ysjspt.jpa.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @author
 * wzq
 */
@Service("serviceService")
public class ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;

    public int selectService(Integer serviceId,String key){
        return serviceRepository.selectService(serviceId,key);
    }
    public int selectReplaceFilterInfo(String devId){
        return serviceRepository.selectReplaceFilterInfo(devId);
    }


}
