package com.xiaohe.ysjspt.service;


import com.xiaohe.hservice.HService;
import com.xiaohe.ysjspt.entity.*;
import com.xiaohe.ysjspt.entity.config.SysConfigs;
import com.xiaohe.ysjspt.entity.config.UserConfigs;
import com.xiaohe.ysjspt.jpa.DeviceRepository;
import com.xiaohe.ysjspt.modules.agency.service.AgencyService;
import com.xiaohe.ysjspt.modules.repairer.entity.Repairer;
import com.xiaohe.ysjspt.modules.repairer.service.RepairerService;
import com.xiaohe.ysjspt.modules.sys.entity.SysUserEntity;
import com.xiaohe.ysjspt.modules.sys.shiro.ShiroUtils;
import com.xiaohe.ysjspt.modules.sys.untils.Constant;
import com.xiaohe.ysjspt.utils.DevUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import com.xiaohe.ysjspt.modules.sys.service.SysUserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 设备service服务类
 *
 * @version <pre>
 * @Author Version        Date		Changes
 * admin    1.0  2018年05月03日 Created
 *
 * </pre>
 * @since 1.
 */
@Service("deviceService")
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private HService hService;
    @Autowired
    private DevSysConfigService devsysConfigService;
    @Autowired
    private UserConfigService userConfigService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private RepairerService repairerService;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private SysUserService sysUserService;


    public int findDevice(String devId) {
        return deviceRepository.findDevice(devId);
    }

    public DeviceEntity findCustInfoByDevId(String devId) {
        return deviceRepository.findCustInfoByDevId(devId);
    }

    public DeviceEntity saveDevice(DeviceEntity entity) {
        return deviceRepository.save(entity);
    }

    public void saveOrUpdate(Long userId, List<Long> deviceIdList) {

        if (deviceIdList == null || deviceIdList.size() == 0) {
            return;
        }
        //获取所有设备列表
        List<DeviceEntity> deviceList = deviceRepository.findByUserId(userId);
        //保存
        //查询结果不为空，则全部解除绑定
        if (!CollectionUtils.isEmpty(deviceList)) {
            for (DeviceEntity deviceEntity : deviceList) {
                //保存关联超级管理员设备的关系
                deviceEntity.setUserId(1L);
            }
            deviceRepository.save(deviceList);
        }
        //通过devIdList,查询所有
        List<DeviceEntity> deviceEntities = deviceRepository.findAll(deviceIdList);
        for (DeviceEntity deviceEntity : deviceEntities) {
            //绑定用户与设备的关系
            deviceEntity.setUserId(userId);
        }
        deviceRepository.save(deviceEntities);
    }


    public void updateModel(Long devId, int model) {
        deviceRepository.updateDevice(devId, model);
    }

    public void updateStatus(Long devId, int status) {
        deviceRepository.updateStatus(devId, status);
    }

    public Page<DeviceEntity> findAllDevice(Integer pageIndex, Integer pageSize) {
        Pageable pageable = new PageRequest(pageIndex, pageSize, Sort.Direction.DESC, "id");
        return deviceRepository.findAll(pageable);
    }

    /**
     * 获取用户下的设备
     *
     * @return
     */
    public List<DeviceEntity> findDeviceByUser() {
        //获取用户id
        Long userId = ShiroUtils.getUserId();
        if (userId.intValue() == Constant.SUPER_ADMIN) {
            return deviceRepository.findAll();
        } else {
            return deviceRepository.findByUserId(userId);
        }
    }


    public Page<DeviceEntity> findAll(Integer page, Integer pageSize, String deviceId) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.ASC, "deviceId");
        //获取用户id
        SysUserEntity sysUserEntity = ShiroUtils.getUserEntity();
        Long userId = sysUserEntity.getUserId();
        //查询条件构造
        Specification<DeviceEntity> spec = new Specification<DeviceEntity>() {
            @Override
            public Predicate toPredicate(Root<DeviceEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (StringUtils.isNotBlank(deviceId)) {
                    predicate.getExpressions().add(cb.like(root.get("deviceId").as(String.class), "%" + deviceId + "%"));
                }
                if (userId != null && userId.intValue() != Constant.SUPER_ADMIN) {
//                    //如果是一级代理商 可以查看所有二级的经销商的设备
//                    List<Long> userIdList = agencyService.findUserIdList(userId);
//                    if(!CollectionUtils.isEmpty(userIdList)){
//                        //添加一级代理下所有设备
//                        userIdList.add(userId);
//                        CriteriaBuilder.In<Object> in = cb.in(root.get("userId").as(Integer.class));
//                        for (Long aLong : userIdList) {
//                            in.value(aLong);
//                        }
//                        predicate.getExpressions().add(cb.and(in));
//                    }else{
                        //没有下级经销商
                        predicate.getExpressions().add(cb.equal(root.get("userId").as(Integer.class), userId));
//                    }
                }
                return predicate;
            }
        };
        return deviceRepository.findAll(spec, pageable);
    }

    public List<Long> findDeviceIdByUserId(Long userId) {
        List<DeviceEntity> deviceIdByUserId = deviceRepository.findDeviceIdByUserId(userId);
        List<Long> devIds = DevUtils.getIds(deviceIdByUserId);

        return devIds;
    }

    ;

    public DeviceEntity findByDeviceId(String deviceId) {

        return deviceRepository.findTopByDeviceIdOrderByUpdateTimeDesc(deviceId);
    }

    public void delete(Long id) {

        deviceRepository.delete(id);
    }

    public List<DeviceEntity> findByBrandname(String parentBrandname) {

        return deviceRepository.findByBrandname(parentBrandname);
    }

    public List<Object[]> findProvinceCount() {
        return deviceRepository.findProvinceCount();
    }

    public List<Object[]> findDeviceLatLng() {
        return deviceRepository.findDeviceLatLng();
    }

    public List<DeviceEntity> findByProvince(String province) {
        return deviceRepository.findByProvince(province);
    }

    /**
     * 通过id获取设备IEMI
     *
     * @param id
     * @return
     */
    public Integer getModel(Long id) {
        Integer deviceId = null;
        DeviceEntity deviceEntity = deviceRepository.findOne(id);
        if (deviceEntity != null) {
            deviceId = deviceEntity.getModel();
        }
        return deviceId;
    }

    public String getImei(Long id) {
        String imei = null;
        DeviceEntity deviceEntity = deviceRepository.findOne(id);
        if (deviceEntity != null) {
            imei = deviceEntity.getImei();
        }
        return imei;
    }

    /**
     * 解除绑定的设备
     *
     * @param userIdList
     */
    public void updateByUserIds(List<Long> userIdList) {
        deviceRepository.updateByUserIds(userIdList);
    }

    public DeviceEntity findOne(Long id) {
        return deviceRepository.findOne(id);
    }


    /**
     * 判断设备是否在线
     *
     * @param devId
     * @return
     */
    public int isDevOnline(Long devId) {
        String iEmi = getImei(devId);
        if (StringUtils.isBlank(iEmi)) {
            return 1;
        }
        return 0;
    }

    /**
     * 判断设备是否存在 是否在线 设备是否有imei
     *
     * @param devId
     * @param model
     * @return 0 合法
     * getDevStu     * 1 "设备该机型不存在，错误码：1113"
     * 2"设备IEMI不存在，错误码：1112"
     * 3"设备不在线，错误码：1115"
     */
    public int getDevStu(Long devId, Integer model, int tag) {
        //设备模型数据是否存在
        if (tag != 0) {
            if (devsysConfigService.existByDevIdAndState(devId, model)) {
                return 4;
            }
        }
        //设备是否关联imei
        String iEmi = getImei(devId);
        if (StringUtils.isBlank(iEmi)) {
            return 2;
        }
        //判断设备是否在线
        int res = hService.selectDevOnline(iEmi);
        if (res != 0) {
            return 3;
        }
        return 0;
    }

    /**
     * @param devId
     * @param model
     * @param tag   区分码
     * @return
     */
    public CommonResult getDevStuWithMsg(Long devId, int model, int tag) {
        CommonResult result = new CommonResult();
        //判断设备相关参数
        int ret = getDevStu(devId, model, tag);
        switch (ret) {
            case 1:
                result.error(1113, "设备该机型不存在，错误码：1113");
                return result;
            case 2:
                result.error(1112, "设备IEMI不存在，错误码：1112");
                return result;
            case 3:
                result.error(1115, "设备不在线，错误码：1115");
                return result;
            default:
                result.ok();
                return result;
        }
    }

    /**
     * 更新设备的机型状态，如果对应模型的系统参数的数据不存在新增（参数都是默认值），存在则不做处理
     *
     * @param devId
     * @param model
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateDeviceAndSaveSysConfigAndUserConfig(Long devId, Integer model) {
        updateModel(devId, model);
        SysConfigs sysConfigs = devsysConfigService.findByDevIdAndState(devId, model);
        if (sysConfigs == null) {
            sysConfigs = new SysConfigs();
            sysConfigs.setDevId(devId);
            sysConfigs.setState(model);
            devsysConfigService.save(sysConfigs);
        }
        UserConfigs userConfig = userConfigService.findByDevIdAndState(devId, model);
        if (userConfig == null) {
            userConfig = new UserConfigs();
            userConfig.setDevId(devId);
            userConfig.setState(model);
            userConfig.setStartDay(1);
            userConfig.setStopDay(1);
            userConfig.setHangeTime(1);
            userConfig.setChangeCycle(7);
            userConfig.setDay(1);
            userConfig.setFlushDuration((short) 1);
            userConfig.setHangeTime(30);
            userConfig.setCleanDuration((short) 30);
            userConfigService.save(userConfig);
        }
    }


    public DeviceEntity findByImei(String imei) {
        return deviceRepository.findByImei(imei);
    }

    /**
     * 修改模型
     *
     * @param devId
     * @param model
     * @return 0 下发成功
     * 1.下发失败
     * 0xff imei为空
     */
    public int setModel(Long devId, Integer model) {
        String imei = getImei(devId);
        if (StringUtils.isBlank(imei)) {
            return 0xff;
        }
        return hService.setModel(imei, model);
    }

    /**
     * 恢复默认设置
     *
     * @param devId
     * @return
     */
    public int restDefaults(Long devId) {
        String imei = getImei(devId);
        if (StringUtils.isBlank(imei)) {
            return 0xff;
        }
        return hService.restoreDefaults(imei);
    }

    /**
     * 下发指令 定时启动与取消
     *
     * @param devId
     * @param type
     * @return 0 下发成
     * 1下个失败
     * 0xff imei 为空
     */
    public int timedStartAndCancel(Long devId, Integer type) {
        String imei = getImei(devId);
        if (StringUtils.isBlank(imei)) {
            return 0xff;
        }
        return hService.timedStartAndCancel(imei, type);
    }

    /**
     * 更新启动与取消（数据库）
     *
     * @param devId
     * @param type
     * @param model
     */
    public void updateTimeType(Long devId, Integer type, Integer model) {
        SysConfigs byDevIdAndState = devsysConfigService.findByDevIdAndState(devId, model);
        if (byDevIdAndState != null) {
            byDevIdAndState.setTimingState(type);
            devsysConfigService.save(byDevIdAndState);
        }
    }

    /**
     * 手动换水
     *
     * @param devId
     * @return
     */
    public int changeWater(Long devId) {
        String imei = getImei(devId);
        if (StringUtils.isBlank(imei)) {
            return 0xff;
        }
        return hService.changeWater(imei, 0);
    }

    /**
     * 手动清洗
     *
     * @param devId
     * @return
     */
    public int manualClean(Long devId) {
        String imei = getImei(devId);
        if (StringUtils.isBlank(imei)) {
            return 0xff;
        }
        return hService.disinfection(imei, 0);
    }

    /**
     * 手动清洗
     *
     * @param devId
     * @return
     */
    public int manualWash(Long devId) {
        String imei = getImei(devId);
        if (StringUtils.isBlank(imei)) {
            return 0xff;
        }
        return hService.flushing(imei, 0);
    }

    /**
     * 手动消毒
     *
     * @param devId
     * @return
     */
    public int resetWaterVolume(Long devId) {
        String imei = getImei(devId);
        if (StringUtils.isBlank(imei)) {
            return 0xff;
        }
        return hService.resetWaterVolume(imei);
    }

    /**
     * 修改系统参数
     *
     * @param sysConfig
     * @return 0 下发成功
     * 1下发失败
     * 3. imei为空
     */
    public int updateSysConfig(SysConfigs sysConfig) {
        String imei = getImei(sysConfig.getDevId());
        if (StringUtils.isBlank(imei)) {
            return 0xff;
        }

        if (1 == sysConfig.getState()) {
            return hService.setSysEnergy(sysConfig.getType(), imei, sysConfig.getStopTemp(), sysConfig.getOutType(), sysConfig.getDisinfectType(),
                    sysConfig.getChangeType(), sysConfig.getFlushType(), sysConfig.getFilterType(), sysConfig.getLogoShows(), sysConfig.getInTemp(),
                    sysConfig.getInHeight(), sysConfig.getChangeMethod(), sysConfig.getCheckWater(), sysConfig.getDelicacy());
        } else {
            return hService.setSysStep(sysConfig.getType(), imei, sysConfig.getStopTemp(), sysConfig.getEmptyType(),
                    sysConfig.getFlushType(), sysConfig.getFilterType(), sysConfig.getLogoShows()
                    , sysConfig.getInTemp(), sysConfig.getKeepTemp(), sysConfig.getKeepHeight(), sysConfig.getSpeed(), sysConfig.getDelicacy());
        }
    }

    /**
     * 更新系统参数
     *
     * @param userConfig
     * @return
     */
    public int updateUserConfigs(UserConfigs userConfig) {
        String imei = getImei(userConfig.getDevId());
        if (StringUtils.isBlank(imei)) {
            return 0xff;
        }
        return hService.setUserSetting(userConfig.getType(), imei, userConfig.getDay(), userConfig.getHours(), userConfig.getMinutes(), userConfig.getStartDay()
                , userConfig.getStopDay(), userConfig.getStartOne(), userConfig.getStopOne(), userConfig.getStartTwo(), userConfig.getStopTwo(),
                userConfig.getStartThr(), userConfig.getStopThr(), userConfig.getCleanOne(), userConfig.getCleanTwo(), userConfig.getCleanThr(),
                userConfig.getChangeTime(), userConfig.getHangeTime(), userConfig.getChangeCycle(), userConfig.getFilterLife(),
                userConfig.getFlushTime(), userConfig.getFlushDuration(), userConfig.getCleanDuration());
    }


    /**
     * 查询未绑定经销商的设备
     *
     * @param page
     * @param pageSize
     * @return
     */
    public Page<DeviceEntity> findUnbindDev(Integer page, Integer pageSize, Long userId) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "userId");
        //非超级管理员
        if (userId != 1) {
            ArrayList<Long> list = new ArrayList<>();
            list.add(userId);
            list.add(Long.valueOf(Constant.SUPER_ADMIN));
            return deviceRepository.findByUserIdIn(list, pageable);
        } else {
            //超级管理员获取全部
            return deviceRepository.findAll(pageable);
        }

    }

    /**
     * 获取设备
     *
     * @param devId
     * @return
     */
    public DeviceEntity getDevice(Long devId) {
        return deviceRepository.getOne(devId);
    }

    /**
     * 获取经销商名称
     *
     * @param devId
     * @return
     */
    public String getMerName(Long devId) {
        DeviceEntity one = deviceRepository.getOne(devId);
        if (one != null) {
            Integer bindedUserId = one.getBindedUserId();
            MerchantEntity merchantEntity = merchantService.getOne(bindedUserId);
            return merchantEntity.getMerchantName();
        }
        return null;
    }

    public List<DeviceEntity> findAll() {
        return deviceRepository.findAll();
    }

    public Map<Long, String> findIdAndDevID() {
        List<Object[]> idAndDevId = deviceRepository.findIdAndDevId();
        Map<Long, String> stringMap = new HashMap<Long, String>();
        for (Object[] objects : idAndDevId) {
            Long aLong = Long.valueOf(objects[0].toString());
            String s = objects[1] != null ? objects[1].toString() : null;
            if (s != null) {
                stringMap.put(aLong, s);
            }
        }
        return stringMap;
    }

    /**
     * 赋值设备编号
     *
     * @param coupons
     * @return
     */
    public List<Coupon> getCoupon(List<Coupon> coupons) {
        List<Long> longList = new ArrayList<>();
        for (Coupon coupon : coupons) {
            if (coupon.getDevId() != null) {
                longList.add(coupon.getDevId());
            }
        }
        List<DeviceEntity> allByIdIn = deviceRepository.findAllByIdIn(longList);
        for (Coupon coupon : coupons) {
            for (DeviceEntity deviceEntity : allByIdIn) {
                if (coupon.getDevId() == null) {
                    continue;
                }
                if (coupon.getDevId().equals(deviceEntity.getId())) {
                    coupon.setDevNo(deviceEntity.getDeviceId());
                }
            }
        }
        return coupons;
    }

    public int setMachineStatus(Long devId, Integer machineStatus) {
        String imei = getImei(devId);
        if (StringUtils.isBlank(imei)) {
            return 0xff;
        }
        return hService.setCtlMachineStatus(imei, machineStatus);
    }

    /**
     * 获取自己的设备列表
     *
     * @param userId
     * @return
     */
    public List<DeviceEntity> getOwnDevList(Long userId) {
        return deviceRepository.findByUserId(userId);
    }

    /**
     * 设备编号模糊查询
     *
     * @param devNo
     * @return
     */
    public List<DeviceEntity> findAllByDeviceIdLike(String devNo) {
        return deviceRepository.findAllByDeviceIdLike("%" + devNo + "%");
    }

    /**
     * 根据主键查询
     *
     * @param integer
     * @return
     */
    public DeviceEntity findById(Long integer) {
        return deviceRepository.findById(integer);
    }

    /**
     * 是否存在方案ID
     *
     * @return
     */
    public boolean existsBySchemaIdIn(List<Integer> integers) {
        return deviceRepository.existsBySchemaIdIn(integers);
    }

    /**
     * 分配维修工
     *
     * @param repId
     * @param list
     * @return
     */
    public Result allotRepairer(Integer repId, List<Long> list) {
        Result result = new Result();
        try {
            List<DeviceEntity> allByIdIn = deviceRepository.findAllByIdIn(list);
            if (CollectionUtils.isEmpty(allByIdIn)) {
                return result.error(2001, "设备不存在");
            }
            for (DeviceEntity deviceEntity : allByIdIn) {
                deviceEntity.setRepairerId(repId);
            }
            deviceRepository.save(allByIdIn);
        } catch (Exception e) {
            return result.error(2001, e.getMessage());
        }
        return result.ok();
    }

    /**
     * 根据维修工ID查询设备
     *
     * @param integer
     * @return
     */
    public List<DeviceEntity> getRepairerDev(Integer integer) {
        return deviceRepository.findAllByRepairerId(integer);
    }


    public List<DeviceEntity> findByRepairerIdIn(List<Integer> repairedIds) {
        return deviceRepository.findByRepairerIdIn(repairedIds);
    }

    /**
     * 获取维修设备
     * 商户登录展示商户下的
     * 维修员登录展示该维修员账户下的
     *
     * @return 查询结果
     */
    public List<DeviceEntity> getRepairDeviceList() {
        //商户登录
        SysUserEntity sysUserEntity = ShiroUtils.getUserEntity();
        List<DeviceEntity> deviceEntities = null;
        //获取维修人员列表
        if (sysUserEntity != null) {
            Long userId = sysUserEntity.getUserId();

            if (userId.intValue() == Constant.SUPER_ADMIN) {
                //超级管理员获取所有
                deviceEntities = findAll();
            } else {
                List<Repairer> repairers = repairerService.findAllByManagerId(userId.intValue());
                if (!CollectionUtils.isEmpty(repairers)) {
                    //获取设备
                    deviceEntities = findByRepairerIdIn(DevUtils.getIntIds(repairers));
                }
            }
        } else {

        }
        return deviceEntities;
    }

    /**
     * 根据维修工ID查询设备(分页)
     *
     * @param integer
     * @return
     */
    public Page<DeviceEntity> getRepairerDevByPage(Integer integer, int page, int pageSize) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        return deviceRepository.findAllByRepairerId(integer, pageable);
    }

    /**
     * 查询自己已绑定和未绑定的设备(分页)
     *
     * @param repId
     * @return
     */
    public Page<DeviceEntity> getRepairerDev(Long userId, Integer repId, int page, int pageSize) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        return deviceRepository.findAllByRepairerIdEqualsOrRepairerIdEqualsAndUserId(repId, 0, userId,pageable);
    }
    public List<DeviceEntity> saveAll(List<DeviceEntity> deviceEntities){
        return deviceRepository.save(deviceEntities);
    }

}
