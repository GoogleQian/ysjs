package com.xiaohe.ysjspt.modules.cache;

import com.xiaohe.ysjspt.modules.repairer.entity.Repairer;

public interface CaptchaService {

    /**
     * 设置验证码
     */
    void setCaptcha(CaptchaInfo info);

    /**
     * 获取验证码
     *
     * @param key
     * @return
     */
    String getCaptcha(String key);

    /**
     * 存入登录账号
     */
    void setRepairer(Repairer repairer);

    /**
     * 获取登录账号
     *
     * @param key
     * @return
     */
    String getRepairer(Integer key);

    /**
     * 清除缓存
     * @param key
     * @return
     */
    boolean remove(Integer key);


}
