package com.xiaohe.ysjspt.service;

import org.springframework.data.domain.Page;

/**
 * @author hzh
 * @date 2018/9/17
 */
public interface IBaseService<T, ID> {
    /**
     * 保存对象
     *
     * @param t 持久对象，或者对象集合
     * @throws Exception
     */
    T save(T t);

    /**
     * 通过devId查询
     *
     * @param devId
     * @return
     */
    T findByDevId(Long devId);

    /**
     * 通过id删除对象
     *
     * @param id
     * @throws Exception
     */
    void delete(ID id);

    /**
     * 通过id判断是否存在
     *
     * @param id
     * @throws Exception
     */
    boolean exists(ID id);

    /**
     * 返回可用实体的数量
     *
     * @throws Exception
     */
    long count();

    /**
     * 通过id查询
     *
     * @param id
     * @return T
     * @throws Exception
     */
    T findById(ID id);

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return Page<T>
     */
    Page<T> findAll(int page, int pageSize);


}
