package com.xiaohe.ysjspt.jpa;

import com.xiaohe.ysjspt.entity.config.UserConfigs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * jpa 接口
 *
 * @author hzh
 * @since 2018-09-26
 */

@Transactional(rollbackFor = Exception.class)
public interface UserConfigRepository extends JpaRepository<UserConfigs, Integer> {
    /**
     * 通过devId查询
     *
     * @param devId
     * @return
     */
    UserConfigs findByDevId(Long devId);

    /**
     * 通过设备id 和机型查找
     *
     * @param devId
     * @param state
     * @return
     */
    UserConfigs findByDevIdAndState(Long devId, Integer state);

    @Transactional(rollbackFor = { RuntimeException.class })
    @Modifying
    @Query(value = "update dev_user_config set show_temp=?3 where dev_id=?1 and state =?2", nativeQuery = true)
    int updateShowTemp(Long imei,int type,int showTemp);
}