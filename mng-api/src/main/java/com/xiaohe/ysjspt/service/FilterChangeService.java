package com.xiaohe.ysjspt.service;

import com.xiaohe.ysjspt.entity.FilterChangeEntity;
import com.xiaohe.ysjspt.jpa.FilterChangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 *
 * 滤芯更换service服务类
 *
 * @version
 * <pre>
 * @Author	Version		Date		Changes
 * wzq    1.0  2018年05月30日 Created
 *
 * </pre>
 * @since 1.
 */

@Service("filterChangeService")
public class FilterChangeService {
    @Autowired
    private FilterChangeRepository filterChangeRepository;

    public List<FilterChangeEntity> findChange(String deviceId,String date,Integer replaceStatus){

        return  filterChangeRepository.findChange(deviceId,date,replaceStatus);
    }





    public List findChangeList(String devId){
        return  filterChangeRepository.findChangeList(devId);
    }
}
