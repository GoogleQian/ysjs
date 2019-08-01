package com.xiaohe.ysjspt.service;

import com.xiaohe.ysjspt.entity.*;
import com.xiaohe.ysjspt.jpa.DeviceRepository;
import com.xiaohe.ysjspt.jpa.PassageRepository;
import com.xiaohe.ysjspt.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <p>
 * TbPassage服务类
 * </p>
 *
 * @author wzq
 * @since 2018-10-15
 */

@Service
public class PassageService {

    @Autowired
    private PassageRepository passageRepository;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceRepository deviceRepository;

    /**
     * 保存对象
     *
     * @param tbpassage 持久对象，或者对象集合
     * @throws Exception
     */
    public void save(PassageEntity tbpassage) {
        passageRepository.save(tbpassage);
    }

    /**
     * 删除对象
     *
     * @param tbpassage
     * @throws Exception
     */

    public void delete(PassageEntity tbpassage) {
        passageRepository.delete(tbpassage);
    }

    /**
     * 返回可用实体的数量
     *
     * @throws Exception
     */
    public long count() {
        return passageRepository.count();
    }

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return Page<TbPassage>
     * @throws Exception properties 字符串为需要排序的字段，可以传多个
     */
    public Page<PassageEntity> findAll(String ids, String merName, int page, int pageSize) {
        Pageable pageable = new PageRequest(page - 1, pageSize, Sort.Direction.DESC, "id");
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Predicate> list = new ArrayList<>();

                if (ids != null && ids.length() > 0) {
                    String[] idStr = ids.split(",");
                    CriteriaBuilder.In<Object> in = cb.in(root.get("id"));
                    for (String id : idStr) {
                        Integer idInt = Integer.parseInt(id);
                        in.value(idInt);
                    }
                    list.add(in);
                }
                if (merName != null && merName.length() > 0) {

                    predicate.getExpressions().add(cb.like(root.get("merchantName").as(String.class), "%" + merName + "%"));
                    list.add(predicate);


                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        return passageRepository.findAll(specification, pageable);
    }

    public void saveAll(List<PassageEntity> entities) {
        passageRepository.save(entities);
    }

    /**
     * 根据设备Id获取通道列表
     *
     * @param aLong id
     * @return List<PassageEntity>
     */
    public List<PassageEntity> getPassList(Long aLong) {
        return passageRepository.findAllByDevId(aLong);
    }

    /**
     * 保存通道
     * @param deviceEntity 通道
     * @return Result
     */
    @Transactional(rollbackFor = Exception.class)
    public Result save(DeviceEntity deviceEntity){
        Result result1=new Result();
        CommonResult result=verify(deviceEntity);
        if(result.getRet()!=0){
            return result1.error(result.getRet(),result.getMsg());
        }
        List<PassageEntity> passList = deviceEntity.getPassList();
        DeviceEntity save = deviceRepository.save(deviceEntity);
        //赋值ID
        for (PassageEntity detail : passList) {
            //转化price
            String strPrice = detail.getStrPrice();
            Number  number=Float.parseFloat(strPrice) * 100;
            int i = number.intValue();
            detail.setPrice(i);
            detail.setDevId(save.getId());
        }
        passageRepository.save(passList);
        return result.ok();
    }

    /**
     * 修改通道
     * @param deviceEntity 通道
     * @return Result
     */
    @Transactional(rollbackFor = Exception.class)
    public Result update(DeviceEntity deviceEntity){
        Result result=new Result();
        long devId = deviceEntity.getId();
        CommonResult verify = verify(deviceEntity);
        if(verify.getRet()!=0){
            return result.error(verify.getRet(), verify.getMsg());
        }
        //设备表修改  售水方案 ID
        deviceService.saveDevice(deviceEntity);
        //通道表修改
        List<Long> integers=new ArrayList<>();
        integers.add(deviceEntity.getId());
        //旧数据
        List<PassageEntity> schemaDetails = passageRepository.findAllByDevIdIn(integers);
        //要修改的数据
        List<PassageEntity> details = deviceEntity.getPassList();
        //要修改主键集合
        List<Integer> integers1 = new LinkedList<>();
        for (PassageEntity schemaDetail : details) {
            if(schemaDetail.getId()!=null){
                integers1.add(schemaDetail.getId());
            }
        }
        //1. 找出新增的
        List<PassageEntity> detailsInsert=new LinkedList<>();
        //2. 找出删除的
        List<PassageEntity> detailsDelete=new LinkedList<>();
        //3 找出修改的
        List<PassageEntity> detailsUpdate=new LinkedList<>();
        for (PassageEntity detail : details) {
            detail.setDevId(devId);
            //转化price
            String strPrice = detail.getStrPrice();
            Number  number=Float.parseFloat(strPrice) * 100;
            int i = number.intValue();
            detail.setPrice(i);
            if(detail.getId()!=null){
                detailsUpdate.add(detail);
            }else if(detail.getId()==null){
                detailsInsert.add(detail);
            }
        }
        for (PassageEntity schemaDetail : schemaDetails) {
            if(!integers1.contains(schemaDetail.getId())){
                detailsDelete.add(schemaDetail);
            }
        }
        if(detailsInsert.size()!=0){
            passageRepository.save(detailsInsert);
        }
        if(detailsUpdate.size()!=0) {
            passageRepository.save(detailsUpdate);
        }
        if(detailsDelete.size()!=0) {
            passageRepository.deleteInBatch(detailsDelete);
        }
        return result.ok();
    }

    /**
     * 校验通道设置参数
     * @param deviceEntity
     * @return
     */
    public CommonResult verify(DeviceEntity deviceEntity) {
        CommonResult result=new CommonResult();
        if (deviceEntity == null) {
             result.setRet(1124);
             result.setMsg("通道列表不能为空，错误码：1124");
            return result;
        }
        List<PassageEntity> passList = deviceEntity.getPassList();
        if (CollectionUtils.isEmpty(passList)) {
            result.setRet(1124);
            result.setMsg("通道列表不能为空，错误码：1124");
            return result;
        }
        //通道号
        List<Integer> integers = new ArrayList<>();
        //出水类型
        List<Integer> integers2 = new ArrayList<>();
        for (PassageEntity passageEntity : passList) {

            Integer passageNo = passageEntity.getPassageNo();
            Integer passageType = passageEntity.getPassageType();
            Integer num = passageEntity.getPulseNum();
            String price = passageEntity.getStrPrice();
            if (passageNo==null) {
                result.setRet(1124);
                result.setMsg("通道号不能为空，错误码：1124");
                return result;
            }if (passageType==null) {
                result.setRet(1124);
                result.setMsg("出水类型不能为空，错误码：1124");
                return result;
            }if (StringUtils.isEmpty(price)) {
                result.setRet(1124);
                result.setMsg("价格不能为空，错误码：1124");
                return result;
            }
            //校验脉冲必须为正整数
            String  pattern1 = "^[1-9]\\d*$";
            Pattern r1 = Pattern.compile(pattern1);
            Matcher m1 = r1.matcher(num.toString());
            if(!m1.matches()){
                result.setRet(1124);
                result.setMsg("脉冲必须为正整数：1124");
                return result;
            }
            //校验价格是否非法
            String pattern = "^(?!0+(\\.0+)?$)\\d+(\\.\\d+)?$";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(price);
            if(!m.matches()){
                result.setRet(1124);
                result.setMsg("价格非法：1124");
                return result;
            }
            if (integers.contains(passageNo)) {
                result.setRet(1124);
                result.setMsg("通道号不能重复，错误码：1124");
                return result;
            }
//            if(integers2.contains(passageType)){
//                result.setRet(1124);
//                result.setMsg("出水类型不能重复，错误码：1124");
//                return result;
//            }
            integers.add(passageNo);
            integers2.add(passageType);
        }
        result.ok();
        result.setDatas(passList);
        return result;
    }
}


