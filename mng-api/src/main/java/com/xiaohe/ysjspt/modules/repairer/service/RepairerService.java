package com.xiaohe.ysjspt.modules.repairer.service;

import com.xiaohe.ysjspt.entity.DeviceEntity;
import com.xiaohe.ysjspt.modules.repairer.entity.QueryRepairer;
import com.xiaohe.ysjspt.modules.repairer.entity.Repairer;
import com.xiaohe.ysjspt.service.IBaseService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * <p>
 * Repairer接口
 * </p>
 *
 * @author gmq
 * @since 2018-12-10
 */

public interface RepairerService extends IBaseService<Repairer, Integer> {

    /**
     * 按条件查询
     *
     * @param page     页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<Repairer> findAll(int page, int pageSize, QueryRepairer queryRepairer);

    /**
     * 查询所有姓名
     *
     * @return
     */
    List<String> findAllName();

    /**
     * 按条件查询设备故障
     *
     * @param page     页
     * @param pageSize 页数量
     * @param repairer 维修
     * @return
     */
    Page<DeviceEntity> findAllDev(Integer page, Integer pageSize, QueryRepairer repairer);

    /**
     * 查询用户信息
     *
     * @return
     */
    Repairer findByLoginNameAndPwd(String name, String pwd);

    /**
     * 通过商户id查询
     *
     * @param managerId 商户id
     * @return 查询结果
     */
    List<Repairer> findAllByManagerId(Integer managerId);

    /**
     * 查找所有
     *
     * @return 查询结果
     */
    List<Repairer> findList();

    /**
     * 获取自己的所有设备
     * @return
     */
    List<DeviceEntity> findAllByRepairerId(Integer integer);
    /**
     * 维修工获取自己的故障设备(附带设备实时状态信息)
     * @return
     */
    List<DeviceEntity> findAllDevFaultByRepairerId(Integer integer,String devNo);


    /**
     * 根据编号获取自己所有设备
     * @return
     */
    List<DeviceEntity> findAllByRepairerIdAndDeviceId(Integer integer,String devNo);


}


