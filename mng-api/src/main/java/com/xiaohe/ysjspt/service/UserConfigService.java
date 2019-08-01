package com.xiaohe.ysjspt.service;

import com.xiaohe.entity.DevUserConfig;
import com.xiaohe.ysjspt.entity.config.UserConfigs;

/**
 * <p>
 * UserConfig接口
 * </p>
 *
 * @author hzh
 * @since 2018-09-26
 */

public interface UserConfigService extends IBaseService<UserConfigs, Integer> {

    /**
     * 通过devId和机型查询
     *
     * @param devId
     * @param state
     * @return
     */
    UserConfigs findByDevIdAndState(Long devId, Integer state);

    int saveByUserConfig(DevUserConfig devUserConfig);
}


