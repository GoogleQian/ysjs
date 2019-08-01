package com.xiaohe.ysjspt.config;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
@EnableAutoConfiguration
public class ApplicationContextUtils implements ApplicationContextAware {

    public static ApplicationContext applicationContext=null;

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        applicationContext = arg0;
    }

    public  static Cache getCache(){
        EhCacheCacheManager cacheCacheManager= ApplicationContextUtils.applicationContext.getBean(EhCacheCacheManager.class);
        CacheManager cacheManager=cacheCacheManager.getCacheManager();
        Cache cache=cacheManager.getCache("captcha");
        return  cache;
    }
    public  static Cache getCache2(){
        EhCacheCacheManager cacheCacheManager= ApplicationContextUtils.applicationContext.getBean(EhCacheCacheManager.class);
        CacheManager cacheManager=cacheCacheManager.getCacheManager();
        Cache cache=cacheManager.getCache("repairer");
        return  cache;
    }

}
