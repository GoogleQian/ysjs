package com.xiaohe.ysjspt.modules.complaints.service.impl;

import com.xiaohe.ysjspt.config.validate.XException;
import com.xiaohe.ysjspt.entity.DeviceEntity;
import com.xiaohe.ysjspt.modules.complaints.entity.ComplaintsVo;
import com.xiaohe.ysjspt.modules.repairer.service.RepairerService;
import com.xiaohe.ysjspt.service.DeviceService;
import com.xiaohe.ysjspt.utils.DevUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.ysjspt.modules.complaints.service.ComplaintsService;
import com.xiaohe.ysjspt.modules.complaints.entity.Complaints;
import com.xiaohe.ysjspt.modules.complaints.jpa.ComplaintsRepository;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

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
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private RepairerService repairerService;

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

    @Override
    public Complaints findByDevId(Long devId) {
        return null;
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

    @Override
    public Page<Complaints> findAll(int page, int pageSize, ComplaintsVo complaintsVo, List<String> devCodes) {


        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "reportTime");
        //查询条件构造
        Specification<Complaints> spec = new Specification<Complaints>() {
            @Override
            public Predicate toPredicate(Root<Complaints> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (StringUtils.isNotBlank(complaintsVo.getDevCode())) {
                    predicate.getExpressions().add(cb.like(root.get("devCode").as(String.class), "%" + complaintsVo.getDevCode() + "%"));
                }
                if (StringUtils.isNotBlank(complaintsVo.getPhoneNum())) {
                    predicate.getExpressions().add(cb.like(root.get("phoneNum").as(String.class), "%" + complaintsVo.getPhoneNum() + "%"));
                }
                if (StringUtils.isNotBlank(complaintsVo.getMsg())) {
                    predicate.getExpressions().add(cb.like(root.get("msg").as(String.class), "%" + complaintsVo.getMsg() + "%"));
                }
                if (complaintsVo.getStatus() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("status").as(Integer.class), complaintsVo.getStatus()));
                }
                //根据创建时间范围 start_date end_date
                if (StringUtils.isNotBlank(complaintsVo.getStartTime())) {
                    predicate.getExpressions().add(cb.greaterThan(root.get("reportTime").as(String.class), complaintsVo.getStartTime()));
                }
                if (StringUtils.isNotBlank(complaintsVo.getEndTime())) {
                    predicate.getExpressions().add(cb.lessThan(root.get("reportTime").as(String.class), complaintsVo.getEndTime()));
                }
                if (!CollectionUtils.isEmpty(devCodes)) {
                    CriteriaBuilder.In<Object> in = cb.in(root.get("devCode").as(String.class));
                    for (String devCode : devCodes) {
                        in.value(devCode);
                    }
                    predicate.getExpressions().add(cb.and(in));
                }
                return predicate;
            }

        };
        return complaintsRepository.findAll(spec, pageable);
    }


    @Override
    public boolean existsByDevCode(String devCode) {
        return complaintsRepository.existsByDevCode(devCode);
    }
}


