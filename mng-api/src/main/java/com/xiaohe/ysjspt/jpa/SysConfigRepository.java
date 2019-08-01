package com.xiaohe.ysjspt.jpa;

import com.xiaohe.ysjspt.entity.config.SysConfigs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * SysConfigs jpa 接口
 *
 * @author hzh
 * @since 2018-09-26
 */

@Transactional(rollbackFor = Exception.class)
public interface SysConfigRepository extends JpaRepository<SysConfigs, Integer> {
    /**
     * 通过devId查询
     *
     * @param devId
     * @return
     */
    SysConfigs findByDevId(Long devId);


    /**
     * 通过devId和机型查询
     *
     * @param devId
     * @param state
     * @return
     */
    SysConfigs findByDevIdAndState(Long devId, Integer state);

}