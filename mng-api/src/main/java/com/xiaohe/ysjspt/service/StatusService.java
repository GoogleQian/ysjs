package com.xiaohe.ysjspt.service;

import com.xiaohe.ysjspt.entity.Status;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StatusService extends IBaseService<Status,Integer>{
    /**
     * 按条件查询
     * @param page 页数
     * @param pageSize 数量
     * @param status 对象
     * @return Page
     */
    Page<Status> findAll(int page, int pageSize, Status status);

    /**
     * 根据Imei查询
     * @return
     */
    List<Status> findAllByImeiIn(List<String> strings);
}
