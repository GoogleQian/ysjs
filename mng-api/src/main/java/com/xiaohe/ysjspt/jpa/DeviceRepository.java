package com.xiaohe.ysjspt.jpa;


import com.xiaohe.ysjspt.entity.DeviceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 设备Dao
 *
 * @version <pre>
 * @Author Version        Date		Changes
 * admin 	1.0  		2018年05月03日 	Created
 *
 * </pre>
 * @since 1.
 */


public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {

    @Query(value = "select count(*) from tb_device t where t.device_id = :deviceId", nativeQuery = true)
    int findDevice(@Param("deviceId") String deviceId);


    @Modifying
    @Query(value = "select * from tb_device t where t.device_id = :deviceId", nativeQuery = true)
    DeviceEntity findCustInfoByDevId(@Param("deviceId") String deviceId);


    Page<DeviceEntity> findAll(Specification<DeviceEntity> spec, Pageable pageable);

    DeviceEntity findTopByDeviceIdOrderByUpdateTimeDesc(String deviceId);


    @Modifying
    @Query(value = "select * from tb_device t where t.brandname = :brandname", nativeQuery = true)
    List<DeviceEntity> findByBrandname(@Param("brandname") String parentBrandname);


    @Modifying
    @Query(value = "SELECT COUNT(province),province FROM tb_device GROUP BY province", nativeQuery = true)
    List<Object[]> findProvinceCount();


    @Modifying
    @Query(value = "select device_x,device_y,device_id from tb_device", nativeQuery = true)
    List<Object[]> findDeviceLatLng();

    @Modifying
    @Query(value = "select * from tb_device t where t.province = :province", nativeQuery = true)
    List<DeviceEntity> findByProvince(@Param("province") String province);

    DeviceEntity findByImei(String imei);

    @Transactional(rollbackFor = {RuntimeException.class})
    @Modifying
    @Query(value = "update tb_device set model=?2 where id=?1", nativeQuery = true)
    void updateDevice(Long devId, Integer model);

    @Transactional(rollbackFor = {RuntimeException.class})
    @Modifying
    @Query(value = "update tb_device set machine_status=?2 where id=?1", nativeQuery = true)
    void updateStatus(Long devId, Integer status);


    /**
     * 获取未绑定的经销商的设备
     *
     * @param pageable
     * @return Page<DeviceEntity>
     */
    Page<DeviceEntity> findByUserIdIsNull(Pageable pageable);

    Page<DeviceEntity> findByUserIdIn(List<Long> userId, Pageable pageable);

    /**
     * 获取用户的帮定的设备列表
     *
     * @param userId
     * @return
     */
    List<DeviceEntity> findDeviceIdByUserId(Long userId);

    /**
     * 获取用户的帮定的设备列表
     *
     * @param userId
     * @return
     */
    List<DeviceEntity> findByUserId(Long userId);

    /**
     * 查询ID和设备编号
     *
     * @return
     */
    @Query(value = "select id,device_id from tb_device", nativeQuery = true)
    List<Object[]> findIdAndDevId();

    /**
     * 根据ID集合查询设备编号
     *
     * @param list
     * @return
     */
    List<DeviceEntity> findAllByIdIn(List<Long> list);

    /**
     * 根据设备编号模糊查询设备
     */
    List<DeviceEntity> findAllByDeviceIdLike(String devNo);

    DeviceEntity findById(Long aLong);

    /**
     * 是否存在方案ID
     *
     * @return
     */
    boolean existsBySchemaIdIn(List<Integer> integers);

    /**
     * 通过用户id 解除绑定
     *
     * @param userIds
     */
    @Transactional(rollbackFor = {RuntimeException.class})
    @Modifying
    @Query(value = "update  tb_device set user_id=1  where user_id in :userIds", nativeQuery = true)
    void updateByUserIds(@Param(value = "userIds") List<Long> userIds);

    /**
     * 根据维修工ID查询设备列表
     *
     * @return
     */
    List<DeviceEntity> findAllByRepairerId(Integer integer);
    /**
     * 根据维修工ID查询设备列表分页
     * @return
     */
    Page<DeviceEntity> findAllByRepairerId(Integer integer,Pageable pageable);

    /**
     * 根据维修工ID集合查询设备列表
     *
     * @param repairedIds 维修工ID集合
     * @return
     */
    List<DeviceEntity> findByRepairerIdIn(List<Integer> repairedIds);

    /**
     * 查询自己已绑定和未绑定
     * @param integer
     * @param integer2
     * @return
     */
    Page<DeviceEntity> findAllByRepairerIdEqualsOrRepairerIdEqualsAndUserId(Integer integer, Integer integer2,Long repId,Pageable pageable);

    /**
     * 根据维修工ID查询设备列表
     *
     * @return
     */
    List<DeviceEntity> findAllByRepairerIdAndDeviceIdLike(Integer integer,String devNo);
}