package com.xiaohe.ysjspt.modules.loginLog.service;

import com.xiaohe.ysjspt.modules.loginLog.entity.LoginLog;
import com.xiaohe.ysjspt.modules.loginLog.entity.QueryLoginLog;
import com.xiaohe.ysjspt.service.IBaseService;
import org.springframework.data.domain.Page;

/**
 * <p>
 *  LoginLog接口
 * </p>
 *
 * @author gmq
 * @since 2018-12-25
 */

public interface LoginLogService  extends IBaseService<LoginLog,Integer> {

    /**
     * 按条件查询
     * @param page 页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<LoginLog> findAll(int page, int pageSize, QueryLoginLog queryLoginLog);

}


