package com.xiaohe.ysjspt.jpa;



import com.xiaohe.ysjspt.entity.DevStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 *
 * 设备Dao
 *
 * @version
 * <pre>
 * @Author	Version		Date		Changes
 * admin 	1.0  		2018年05月03日 	Created
 *
 * </pre>
 * @since 1.
 */
@Transactional(rollbackFor = { RuntimeException.class })
public interface DeviceStatusRepository extends JpaRepository<DevStatusEntity,Long>{

    /**
     * findAll
     * Page<DevStatusEntity> findAll(Specification<DevStatusEntity> spec, Pageable pageable);
     * @return
     */

    @Transactional(rollbackFor = { RuntimeException.class })
    @Modifying
    @Override
    @Query(value = "select * from tb_dev_status ", nativeQuery = true)
    List<DevStatusEntity> findAll();


}