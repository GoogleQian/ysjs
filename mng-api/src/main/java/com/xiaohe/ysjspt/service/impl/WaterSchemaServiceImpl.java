package com.xiaohe.ysjspt.service.impl;

import com.xiaohe.ysjspt.entity.Result;
import com.xiaohe.ysjspt.entity.SchemaDetail;
import com.xiaohe.ysjspt.entity.WaterSchema;
import com.xiaohe.ysjspt.jpa.SchemaDetailRepository;
import com.xiaohe.ysjspt.jpa.WaterSchemaRepository;
import com.xiaohe.ysjspt.service.WaterSchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @program: ysjsApi
 * @description: 方案实现
 * @author: Gmq
 * @date: 2018-11-12 14:29
 **/
@Service
public class WaterSchemaServiceImpl implements WaterSchemaService {
    @Override
    public WaterSchema save(WaterSchema waterSchema) {
        return null;
    }

    @Autowired
    private WaterSchemaRepository waterSchemaRepository;
    @Autowired
    private SchemaDetailRepository schemaDetailRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result saveSchema(WaterSchema waterSchema) {
        Result result=verify(waterSchema);
        //校验是否方案名是否重复
        if(waterSchemaRepository.existsByProNameEquals(waterSchema.getProName())){
            return result.error(2100, "方案名称不能重复");
        }
        if(result.getRet()!=0){
            return result;
        }
        List<SchemaDetail> schemalist = waterSchema.getSchemalist();
        WaterSchema save = waterSchemaRepository.save(waterSchema);
        //赋值ID
        for (SchemaDetail detail : schemalist) {
            detail.setSchemaId(save.getId());
        }
        schemaDetailRepository.save(schemalist);
        return result.ok();
    }

    @Override
    public WaterSchema findByDevId(Long devId) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer integer) {
        //先删除方案详细表
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(integer);
        schemaDetailRepository.deleteAllBySchemaIdIn(integers);
        waterSchemaRepository.delete(integer);
    }

    @Override
    public boolean exists(Integer integer) {
        return waterSchemaRepository.exists(integer);
    }

    @Override
    public long count() {
        return waterSchemaRepository.count();
    }

    @Override
    public WaterSchema findById(Integer integer) {
        return waterSchemaRepository.findOne(integer);
    }

    @Override
    public Page<WaterSchema> findAll(int page, int pageSize) {
        Pageable pageable = new PageRequest(page, pageSize);
        return waterSchemaRepository.findAll(pageable);
    }

    @Override
    public List<WaterSchema> findAllByUserId(Integer integer) {
        return waterSchemaRepository.findAllByUserId(integer);
    }

    @Override
    public boolean existsByProNameEquals(String s) {
        return waterSchemaRepository.existsByProNameEquals(s);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result update(WaterSchema schema) {
        Result result = verify(schema);
        if(result.getRet()!=0){
            return result;
        }
        Integer id = schema.getId();
        //方案表修改
        waterSchemaRepository.save(schema);
        //详情表修改
        List<Integer> integers=new ArrayList<>();
        integers.add(schema.getId());
        //旧数据
        List<SchemaDetail> schemaDetails = schemaDetailRepository.findAllBySchemaIdIn(integers);
        //要修改的数据
        List<SchemaDetail> details = schema.getSchemalist();
        //要修改主键集合
        List<Integer> integers1 = new LinkedList<>();
        for (SchemaDetail schemaDetail : details) {
            if(schemaDetail.getId()!=null){
                integers1.add(schemaDetail.getId());
            }
        }
        //1. 找出新增的
        List<SchemaDetail> detailsInsert=new LinkedList<>();
        //2. 找出删除的
        List<SchemaDetail> detailsDelete=new LinkedList<>();
        //3 找出修改的
        List<SchemaDetail> detailsUpdate=new LinkedList<>();
        for (SchemaDetail detail : details) {
            detail.setSchemaId(id);
            if(detail.getId()!=null){
                detailsUpdate.add(detail);
            }else if(detail.getId()==null){
                detailsInsert.add(detail);
            }
        }
        for (SchemaDetail schemaDetail : schemaDetails) {
            if(!integers1.contains(schemaDetail.getId())){
                detailsDelete.add(schemaDetail);
            }
        }
        if(detailsInsert.size()!=0){
            schemaDetailRepository.save(detailsInsert);
        }
        if(detailsUpdate.size()!=0) {
            schemaDetailRepository.save(detailsUpdate);
        }
        if(detailsDelete.size()!=0) {
            schemaDetailRepository.deleteInBatch(detailsDelete);
        }
        return result.ok();
    }

    /**
     * 校验名称不能重复
     * @return
     */
    public Result verify(WaterSchema waterSchema){
        Result result=new Result(0,"success");
        //校验详情中水量和水量名称不能重复
        List<SchemaDetail> schemalist = waterSchema.getSchemalist();
        List<String> names = new LinkedList<>();
        List<Integer> integers = new LinkedList<>();
        for (SchemaDetail schemaDetail : schemalist) {
            String name = schemaDetail.getName();
            Integer waterAmount = schemaDetail.getWaterAmount();
            if(StringUtils.isEmpty(name)){
                return result.error(2101, "水量名称不能为空");
            }
            if(waterAmount==null){
                return result.error(2101, "水量不能为空");
            }
            if(waterAmount<100){
                return result.error(2101, "水量必须大于100ml");
            }
            if(names.contains(name)){
                return result.error(2103, "水量名称不能重复");
            }
            if(integers.contains(waterAmount)){
                return result.error(2104, "水量不能重复");
            }
            names.add(name);
            integers.add(waterAmount);
        }
        return result;
    }

    @Override
    public Page<WaterSchema> findAll(int page, int pageSize, WaterSchema waterSchema, Long userId) {
        Pageable pageable =  new PageRequest(page, pageSize,Sort.Direction.DESC, "id");
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate=criteriaBuilder.conjunction();
                if(userId!=null){
                    //管理员可以看所有
                    if(1!=userId){
                        predicate.getExpressions().add(criteriaBuilder.equal(root.get("userId").as(Long.class), userId));
                    }
                }
                //后续对象参数过滤
                return predicate;
            }
        };
        return waterSchemaRepository.findAll(specification, pageable);
    }
}
