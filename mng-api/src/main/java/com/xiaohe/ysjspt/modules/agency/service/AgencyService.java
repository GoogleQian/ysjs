package com.xiaohe.ysjspt.modules.agency.service;

import com.xiaohe.ysjspt.modules.agency.entity.Agency;
import com.xiaohe.ysjspt.modules.agency.entity.QueryAgency;
import com.xiaohe.ysjspt.modules.sys.entity.SysUserEntity;
import com.xiaohe.ysjspt.service.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
/**
 * <p>
 * 经销商表 Agency接口
 * </p>
 *
 * @author gmq
 * @since 2019-01-09
 */

public interface AgencyService  extends IBaseService<Agency,Integer> {

    /**
     * 按条件查询
     * @param page 页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<Agency> findAll(int page, int pageSize, QueryAgency queryAgency);

    /**
     * 根据Id查询list
     * @return
     */
    List<Agency> findAllById(List<Integer> ids);

    List<Long> findUserIdList(Long userId);



}


