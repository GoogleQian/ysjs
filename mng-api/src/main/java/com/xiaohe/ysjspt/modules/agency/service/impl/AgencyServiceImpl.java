package com.xiaohe.ysjspt.modules.agency.service.impl;

import com.xiaohe.ysjspt.modules.agency.entity.Agency;
import com.xiaohe.ysjspt.modules.agency.entity.QueryAgency;
import com.xiaohe.ysjspt.modules.agency.jpa.AgencyRepository;
import com.xiaohe.ysjspt.modules.agency.service.AgencyService;
import com.xiaohe.ysjspt.modules.sys.entity.SysUserEntity;
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
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 经销商表 Agency服务类
 * </p>
 *
 * @author gmq
 * @since 2019-01-09
 */

@Service
public class AgencyServiceImpl implements AgencyService {

    @Autowired
    private AgencyRepository agencyRepository;

    /**
     * 保存对象
     *
     * @param agency 对象
     *               持久对象，或者对象集合
     */
    @Override
    public Agency save(Agency agency) {
        return agencyRepository.save(agency);
    }

    /**
     * 通过id删除对象
     *
     * @param integer
     */
    @Override
    public void delete(Integer integer) {
        agencyRepository.delete(integer);
    }

    @Override
    public Agency findByDevId(Long devId) {
        return null;
    }

    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean exists(Integer id) {
        return agencyRepository.exists(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return agencyRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return Agency对象
     */
    @Override
    public Agency findById(Integer id) {
        return agencyRepository.findOne(id);
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<Agency>对象
     */
    @Override
    public Page<Agency> findAll(int page, int pageSize) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        return agencyRepository.findAll(pageable);
    }

    @Override
    public Page<Agency> findAll(int page, int pageSize, QueryAgency queryAgency) {
        //过滤
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        //查询条件构造
        Specification<Agency> spec = new Specification<Agency>() {
            @Override
            public Predicate toPredicate(Root<Agency> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (queryAgency.getUserId() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("userId").as(Long.class), queryAgency.getUserId()));
                }

                if (queryAgency.getSuperAgentId() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("superAgentId").as(Long.class), queryAgency.getSuperAgentId()));
                }

                return predicate;
            }

        };
        return agencyRepository.findAll(spec, pageable);
    }

    /**
     * 根据Id查询list
     *
     * @param ids id集合
     * @return list
     */
    @Override
    public List<Agency> findAllById(List<Integer> ids) {
        return agencyRepository.findAllById(ids);
    }

    @Override
    public List<Long> findUserIdList(Long userId) {
        List<Long> longs = new LinkedList<>();
        List<BigInteger> userIdList = agencyRepository.findUserIdList(userId);
        for (BigInteger bigInteger : userIdList) {
            longs.add(bigInteger.longValue());
        }
        return longs;
    }
}


