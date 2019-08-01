package com.xiaohe.ysjspt.jpa;


import com.xiaohe.ysjspt.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author
 * wzq
 */
public interface ServiceRepository extends JpaRepository<ServiceEntity,Long> {
    @Transactional(rollbackFor = { RuntimeException.class })
    @Query(value = "select count(id) from tb_service d where d.service_id = :serviceId and d.key = :key", nativeQuery = true)
    int selectService(@Param("serviceId")Integer serviceId,@Param("key")String key);

    /**
     * selectReplaceFilterInfo
     * @param devId
     * @return
     */
    @Query(value = "select count(id) from tb_change_list d where d.device_id = :devId", nativeQuery = true)
    int selectReplaceFilterInfo(@Param("devId")String devId);

}
