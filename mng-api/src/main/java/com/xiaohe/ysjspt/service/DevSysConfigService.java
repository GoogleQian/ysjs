package com.xiaohe.ysjspt.service;


import com.xiaohe.entity.DevSysConfig;
import com.xiaohe.ysjspt.entity.config.SysConfigs;

/**
 * <p>
 * SysConfigImpl接口
 * </p>
 *
 * @author hzh
 * @since 2018-09-26
 */

public interface DevSysConfigService extends IBaseService<SysConfigs, Integer> {

    /**
     * 通过devId和机型查询
     *
     * @param devId
     * @param state
     * @return
     */
    SysConfigs findByDevIdAndState(Long devId, Integer state);

    /**
     * 通过devId 机型判断是否存在
     *
     * @param devId
     * @param state
     * @return
     */
    boolean existByDevIdAndState(Long devId, Integer state);

    /**
     * 通过sysConfig  保存
     *
     * @param devUserConfig
     * @return
     */
    int saveBySysConfig(DevSysConfig devUserConfig);


}


