package com.xiaohe.ysjspt.modules.repairer.service.impl;

import com.xiaohe.ysjspt.entity.DeviceEntity;
import com.xiaohe.ysjspt.entity.Status;
import com.xiaohe.ysjspt.jpa.DeviceRepository;
import com.xiaohe.ysjspt.jpa.StatusRepository;
import com.xiaohe.ysjspt.modules.repairer.entity.QueryRepairer;
import com.xiaohe.ysjspt.modules.repairer.entity.Repairer;
import com.xiaohe.ysjspt.modules.repairer.jpa.RepairerRepository;
import com.xiaohe.ysjspt.modules.repairer.service.RepairerService;
import com.xiaohe.ysjspt.modules.sys.untils.Constant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * Repairer服务类
 * </p>
 *
 * @author gmq
 * @since 2018-12-10
 */

@Service
public class RepairerServiceImpl implements RepairerService {

    @Autowired
    private RepairerRepository repairerRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private StatusRepository statusRepository;

    /**
     * 保存对象
     *
     * @param repairer 对象
     *                 持久对象，或者对象集合
     */
    @Override
    public Repairer save(Repairer repairer) {
        return repairerRepository.save(repairer);
    }


    /**
     * 通过id删除对象
     *
     * @param integer
     */
    @Override
    public void delete(Integer integer) {
        repairerRepository.delete(integer);

    }

    @Override
    public Repairer findByDevId(Long devId) {
        return null;
    }

    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean exists(Integer id) {
        return repairerRepository.exists(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return repairerRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return Repairer对象
     */
    @Override
    public Repairer findById(Integer id) {
        return repairerRepository.findOne(id);
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<Repairer>对象
     */
    @Override
    public Page<Repairer> findAll(int page, int pageSize) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        return repairerRepository.findAll(pageable);
    }

    @Override
    public Page<Repairer> findAll(int page, int pageSize, QueryRepairer queryRepairer) {
        if (queryRepairer == null) {
            return findAll(page, pageSize);
        }
        //过滤自己的广告
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        //查询条件构造
        Specification<Repairer> spec = new Specification<Repairer>() {
            @Override
            public Predicate toPredicate(Root<Repairer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (StringUtils.isNotBlank(queryRepairer.getPhone())) {
                    predicate.getExpressions().add(cb.like(root.get("phone").as(String.class), "%" + queryRepairer.getPhone() + "%"));
                }
                if (StringUtils.isNotBlank(queryRepairer.getLoginName())) {
                    predicate.getExpressions().add(cb.like(root.get("loginName").as(String.class), "%" + queryRepairer.getLoginName() + "%"));
                }
                if (StringUtils.isNotBlank(queryRepairer.getRealName())) {
                    predicate.getExpressions().add(cb.like(root.get("realName").as(String.class), "%" + queryRepairer.getRealName() + "%"));
                }
                if (queryRepairer.getManagerId() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("managerId").as(Integer.class), queryRepairer.getManagerId()));
                }
                return predicate;
            }

        };
        return repairerRepository.findAll(spec, pageable);
    }

    @Override
    public List<String> findAllName() {
        return repairerRepository.findAllName();
    }

    @Override
    public Page<DeviceEntity> findAllDev(Integer page, Integer pageSize, QueryRepairer queryRepairer) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.ASC, "deviceId");
        //查询条件构造
        Specification<DeviceEntity> spec = new Specification<DeviceEntity>() {
            @Override
            public Predicate toPredicate(Root<DeviceEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (queryRepairer != null) {
                    if (StringUtils.isNotBlank(queryRepairer.getDevNo())) {
                        predicate.getExpressions().add(cb.like(root.get("deviceId").as(String.class), "%" + queryRepairer.getDevNo() + "%"));
                    }
                    //过滤自己的维修设备
                    if (queryRepairer.getId() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("repairerId").as(Integer.class), queryRepairer.getId()));
                    }
                }
                return predicate;
            }
        };
        return deviceRepository.findAll(spec, pageable);
    }

    @Override
    public Repairer findByLoginNameAndPwd(String name, String pwd) {
        return repairerRepository.findByLoginNameAndPwd(name, pwd);
    }

    @Override
    public List<Repairer> findAllByManagerId(Integer integer) {
        return repairerRepository.findAllByManagerId(integer);
    }

    @Override
    public List<Repairer> findList() {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        return repairerRepository.findAll(sort);
    }

    @Override
    public List<DeviceEntity> findAllByRepairerId(Integer integer) {
        return deviceRepository.findAllByRepairerId(integer);
    }

    @Override
    public List<DeviceEntity> findAllDevFaultByRepairerId(Integer integer,String devNo) {
        List<String> stringlist = new LinkedList<>();
        //自己所有的故障设备
        List<DeviceEntity> errorList = new LinkedList<>();
        //获取自己的所有设备
        List<DeviceEntity> deviceEntities2 = findAllByRepairerIdAndDeviceId(integer,devNo);
        for (DeviceEntity deviceEntity : deviceEntities2) {
            String imei = deviceEntity.getImei();
            if (StringUtils.isNotBlank(imei)) {
                stringlist.add(imei);
            }
        }
        //查询水质设备信息
        List<Status> allByImeiIn = null;
        if (!CollectionUtils.isEmpty(stringlist)) {
            allByImeiIn = statusRepository.findGroupByImeiIn(stringlist);
        }
        //校验水质标准
        long now = System.currentTimeMillis();
        for (DeviceEntity deviceEntity : deviceEntities2) {
            int q;
            int i = deviceEntity.getPurifyTds() != null ? deviceEntity.getPurifyTds() : 0;
            if (i <= 50) {
                q = 1;
            } else if (i <= 100) {
                q = 2;
            } else if (i <= 300) {
                q = 3;
            } else {
                q = 4;
            }
            deviceEntity.setQuality(q);

            // 在离线状态
            Date shakeHandTime = deviceEntity.getShakeHandTime();
            if (shakeHandTime != null) {
                // 30秒以内视为在线，超过30秒，认为离线
                if ((now - shakeHandTime.getTime()) > (1000 * 30)) {
                    deviceEntity.setIsOnline(0);
                } else {
                    deviceEntity.setIsOnline(1);
                }
            } else {
                deviceEntity.setIsOnline(0);
            }
            //增加水质状态
            if (!CollectionUtils.isEmpty(allByImeiIn)) {
                for (Status status : allByImeiIn) {
                    if (deviceEntity.getImei().equals(status.getImei())) {
                        deviceEntity.setWaterStatus(status);
                    }
                }
            }
            //过滤只有异常的设备Constant.NORMAL ==status.getErroCode()
            Status o = deviceEntity.getWaterStatus();
            if (o != null && Constant.NORMAL != o.getErroCode()) {
                errorList.add(deviceEntity);
            }
        }
        return errorList;
    }

    /**
     * 根据编号获取自己所有设备
     *
     * @return
     */
    @Override
    public List<DeviceEntity> findAllByRepairerIdAndDeviceId(Integer integer, String devNo) {
        return deviceRepository.findAllByRepairerIdAndDeviceIdLike(integer, StringUtils.isBlank(devNo) ? "%%" : "%" + devNo + "%");
    }
}


