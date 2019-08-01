package com.he.water.jpa;


import com.he.water.entity.DeviceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 设备Dao
 *
 * @version <pre>
 * @Author Version        Date		Changes
 * admin 	1.0  		2018年05月03日 	Created
 *
 * </pre>
 * @since 1.
 */


public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {

    /**
     * 通过Imei查询
     *
     * @param imei
     * @return
     */
    DeviceEntity findByImei(String imei);

    /**
     * 通过deviceId查询
     *
     * @param deviceId
     * @return
     */
    DeviceEntity findByDeviceId(String deviceId);
}