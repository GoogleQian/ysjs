package com.he.water.service.complaints.impl;

import com.he.water.entity.complaints.Complaints;
import com.he.water.jpa.complaints.ComplaintsRepository;
import com.he.water.service.complaints.ComplaintsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * <p>
 * 投诉建议表 Complaints服务类
 * </p>
 *
 * @author gmq
 * @since 2018-12-10
 */

@Service
public class ComplaintsServiceImpl implements ComplaintsService {

    @Autowired
    private ComplaintsRepository complaintsRepository;

    /**
     * 保存对象
     *
     * @param complaints 对象
     *                   持久对象，或者对象集合
     */
    @Override
    public Complaints save(Complaints complaints) {
        return complaintsRepository.save(complaints);
    }

    /**
     * 通过id集合删除对象
     *
     * @param integer
     */
    @Override
    public void delete(Integer integer) {
        complaintsRepository.delete(integer);
    }

    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean exists(Integer id) {
        return complaintsRepository.exists(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return complaintsRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return Complaints对象
     */
    @Override
    public Complaints findById(Integer id) {
        return complaintsRepository.findOne(id);
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<Complaints>对象
     */
    @Override
    public Page<Complaints> findAll(int page, int pageSize) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        return complaintsRepository.findAll(pageable);
    }

}


