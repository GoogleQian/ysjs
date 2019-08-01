package com.he.water.service.ad;

import com.he.water.entity.ad.AdvertisingEntity;
import com.he.water.jpa.ad.AdvertisingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 广告表 TbAdvertising服务类
 * </p>
 *
 * @author wzq
 * @since 2018-11-19
 */
@Service
public class AdvertisingService {
    @Autowired
    AdvertisingRepository advertisingRepository;

    /**
     * 获取所有广告
     *
     * @return
     */
    public List<AdvertisingEntity> findByUserId(Integer userId) {

        return advertisingRepository.findByUserId(userId);
    }

}
