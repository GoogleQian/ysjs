package com.xiaohe.ysjspt.modules.errorLog.service;

import com.xiaohe.ysjspt.modules.errorLog.entity.ErrorLog;
import com.xiaohe.ysjspt.modules.errorLog.entity.QueryErrorLog;
import com.xiaohe.ysjspt.service.IBaseService;
import org.springframework.data.domain.Page;

/**
 * <p>
 *  ErrorLog接口
 * </p>
 *
 * @author gmq
 * @since 2018-12-25
 */

public interface ErrorLogService  extends IBaseService<ErrorLog,Integer> {

    /**
     * 按条件查询
     * @param page 页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<ErrorLog> findAll(int page, int pageSize, QueryErrorLog queryErrorLog);

}


