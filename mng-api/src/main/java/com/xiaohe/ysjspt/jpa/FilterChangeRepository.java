package com.xiaohe.ysjspt.jpa;

import com.xiaohe.ysjspt.entity.FilterChangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author
 * wzq
 */

public interface FilterChangeRepository extends JpaRepository<FilterChangeEntity,Long> {
    /**
     * findChange
     * @param deviceId
     * @param date
     * @param replaceFinshed
     * @return
     */
    @Modifying
    @Query(value = "select * from tb_change_list  where device_id like %?1% and plan_replace_time like %?2% and replace_finshed = ?3", nativeQuery = true)
    List<FilterChangeEntity> findChange(@Param("deviceId")String deviceId,@Param("date")String date,@Param("replaceFinshed")Integer replaceFinshed);


    /**
     * findChangeList
     * @param devId
     * @return
     */
    @Modifying
    @Query(value = "select plan_replace_time from tb_change_list where device_id = :devId", nativeQuery = true)
    List findChangeList(@Param("devId")String devId);
}
