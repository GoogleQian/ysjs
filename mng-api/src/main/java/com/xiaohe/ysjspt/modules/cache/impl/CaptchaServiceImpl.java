package com.xiaohe.ysjspt.modules.cache.impl;

import com.xiaohe.ysjspt.config.ApplicationContextUtils;
import com.xiaohe.ysjspt.modules.cache.CaptchaInfo;
import com.xiaohe.ysjspt.modules.cache.CaptchaService;
import com.xiaohe.ysjspt.modules.repairer.entity.Repairer;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.stereotype.Service;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Override
    public void setCaptcha(CaptchaInfo captchaInfo){

        Cache cache = ApplicationContextUtils.getCache();
        try {
            cache.put(new Element(captchaInfo.getId(), captchaInfo.getCaptcha()));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public String getCaptcha(String key){
        Cache cache = ApplicationContextUtils.getCache();
        Element element = cache.get(key);
        if (element != null){
            return  (String)element.getObjectValue();
        }else {
            return  "";
        }
    }

    @Override
    public void setRepairer(Repairer repairer) {
        Cache cache = ApplicationContextUtils.getCache2();
        try {
            cache.put(new Element(repairer.getId(), repairer.getToken()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String getRepairer(Integer key) {
        Cache cache = ApplicationContextUtils.getCache2();
        Element element = cache.get(key);
        if (element != null){
            return  (String)element.getObjectValue();
        }else {
            return  "";
        }
    }

    @Override
    public boolean remove(Integer key) {
        Cache cache = ApplicationContextUtils.getCache2();
        return cache.remove(key);
    }
}
