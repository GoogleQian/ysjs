package com.xiaohe.ysjspt.modules.operateLog.service;

import com.xiaohe.ysjspt.modules.operateLog.entity.OperateLog;
import com.xiaohe.ysjspt.modules.operateLog.entity.QueryOperateLog;
import com.xiaohe.ysjspt.service.IBaseService;
import org.springframework.data.domain.Page;

/**
 * <p>
 *  OperateLog接口
 * </p>
 *
 * @author gmq
 * @since 2018-12-25
 */

public interface OperateLogService  extends IBaseService<OperateLog,Integer> {

    /**
     * 按条件查询
     * @param page 页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<OperateLog> findAll(int page, int pageSize, QueryOperateLog queryOperateLog);

}


