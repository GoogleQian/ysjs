package com.xiaohe.ysjspt.modules.repairedrecord.service;

import com.xiaohe.ysjspt.modules.repairedrecord.entity.RepairRecord;
import com.xiaohe.ysjspt.modules.repairedrecord.entity.RepairRecordVo;
import com.xiaohe.ysjspt.service.IBaseService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * <p>
 * 维修详情表 RepairRecord接口
 * </p>
 *
 * @author gmq
 * @since 2018-12-10
 */

public interface RepairRecordService extends IBaseService<RepairRecord, Integer> {

    /**
     * 按条件查询
     *
     * @param page         页数
     * @param pageSize     数量
     * @param repairRecord 查询条件
     * @param repairerIds  维修员id集合
     * @return Page
     */
    Page<RepairRecord> findAll(int page, int pageSize, RepairRecordVo repairRecord, List<Integer> repairerIds);

}


