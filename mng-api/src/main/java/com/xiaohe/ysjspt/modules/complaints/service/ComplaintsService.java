package com.xiaohe.ysjspt.modules.complaints.service;

import com.xiaohe.ysjspt.modules.complaints.entity.Complaints;
import com.xiaohe.ysjspt.modules.complaints.entity.ComplaintsVo;
import com.xiaohe.ysjspt.service.IBaseService;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 投诉建议表 Complaints接口
 * </p>
 *
 * @author gmq
 * @since 2018-12-10
 */

public interface ComplaintsService extends IBaseService<Complaints, Integer> {

    /**
     * 按条件查询
     *
     * @param page     页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<Complaints> findAll(int page, int pageSize, ComplaintsVo complaintsVo, List<String> devCode);

//    /**
//     * 修改上报建议的处理状态
//     *
//     * @param devCode 设备code
//     * @param date    更新时间
//     * @param status  处理结果
//     */
//    void updateStatusByDevCode(String devCode, Integer status, Date date);


    boolean existsByDevCode(String devCode);
}


