package com.xiaohe.ysjspt.controller;

import com.xiaohe.entity.PassageEntitys;
import com.xiaohe.hservice.HService;
import com.xiaohe.ysjspt.config.aspect.LogOperate;
import com.xiaohe.ysjspt.entity.CommonResult;
import com.xiaohe.ysjspt.entity.DeviceEntity;
import com.xiaohe.ysjspt.entity.PassageEntity;
import com.xiaohe.ysjspt.entity.Result;
import com.xiaohe.ysjspt.entity.config.SysConfigs;
import com.xiaohe.ysjspt.entity.config.UserConfigs;
import com.xiaohe.ysjspt.modules.agency.service.AgencyService;
import com.xiaohe.ysjspt.modules.sys.controller.AbstractController;
import com.xiaohe.ysjspt.modules.sys.entity.SysUserEntity;
import com.xiaohe.ysjspt.modules.sys.service.SysUserService;
import com.xiaohe.ysjspt.service.DevSysConfigService;
import com.xiaohe.ysjspt.service.DeviceService;
import com.xiaohe.ysjspt.service.PassageService;
import com.xiaohe.ysjspt.service.UserConfigService;
import com.xiaohe.ysjspt.utils.DevUtils;
import com.xiaohe.ysjspt.utils.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.POST;
import java.util.*;

/**
 * 设备控制
 *
 * @author hzh
 * @date 2018/9/2518:58
 */
@RestController
@RequestMapping(value = "/dev")
@Validated
public class DevController extends AbstractController {
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private UserConfigService userConfigService;
    @Autowired
    private DevSysConfigService devsysConfigService;
    @Autowired
    private HService hService;
    @Autowired
    private PassageService passageService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private AgencyService agencyService;

    private static final Logger log = LoggerFactory.getLogger(DevController.class);

    /**
     * 查询机型
     *
     * @param devId
     * @return
     */
    @GetMapping(value = "/model")
    @RequiresPermissions("sys:dev:model")
    public CommonResult model(Long devId) {
        Map map = new HashMap(1);
        CommonResult com = new CommonResult();
        //获取模型
        try {
            Integer model = deviceService.getModel(devId);
            if (model == null) {
                com.error(1113, "设备该机型不存在，错误码：1113");
                return com;
            }
            map.put("type", model);
        } catch (Exception e) {
            log.info(e.getMessage());
            com.error(1113, "设备该机型不存在，错误码：1113");
            return com;
        }
        try {
            hService.getUserSetting(1, deviceService.getImei(devId));
        } catch (Exception e) {
            log.info("设备不在线，未能执行查询显示温度");
            e.printStackTrace();
        }

        com.ok();
        com.setDatas(map);
        return com;
    }


    /**
     * 修改机型
     *
     * @param devId
     * @return
     */
    @PostMapping(value = "/models")
    @RequiresPermissions("sys:dev:model")
    @LogOperate(description = "修改机型")
    public Result models(Long devId, Integer state) {
        Result result = deviceService.getDevStuWithMsg(devId, state, 0);
        if (result.getRet() != 0) {
            return result;
        }
        try {
            int res = deviceService.setModel(devId, state);
            if (0 == res) {
                //更新
                deviceService.updateDeviceAndSaveSysConfigAndUserConfig(devId, state);
                result.ok();
            } else {
                result.error(1111, "下发命令失败，请稍后重试，错误码：1111");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(1112, "下发命令失败，请稍后重试，错误码：1112");
        }


        return result;
    }

    /**
     * 恢复默认
     *
     * @param devId
     * @return
     */
    @PostMapping(value = "/configs")
    @RequiresPermissions("sys:dev:reset")
    @LogOperate(description = "恢复默认")
    public Result configs(Long devId, Integer state) {
        //判断设备相关参数
        Result result = deviceService.getDevStuWithMsg(devId, state, 1);
        if (result.getRet() != 0) {
            return result;
        }
        try {
            int res = deviceService.restDefaults(devId);
            DevUtils.getResult(result, res);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(1113, "下发命令失败，请稍后重试，错误码：1113");
        }
        return result;
    }


    /**
     * 定时启动与取消
     *
     * @param devId
     * @return
     */
    @PostMapping(value = "/configs/timer")
    @RequiresPermissions("sys:dev:timer")
    @LogOperate(description = "定时启动与取消")
    public Result timer(Long devId, Integer type, Integer state) {
        Result result = deviceService.getDevStuWithMsg(devId, state, 1);
        if (result.getRet() != 0) {
            return result;
        }
        try {
            int res = deviceService.timedStartAndCancel(devId, type);
            if (0 == res) {
                deviceService.updateTimeType(devId, type, state);
                result.ok();
            } else {
                result.error(1114, "下发命令失败，请稍后重试，错误码：1114");
            }
            DevUtils.getResult(result, res);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(1115, "下发命令失败，请稍后重试，错误码：1115");
        }


        return result;
    }

    /**
     * 获取定时启动与取消状态
     *
     * @param devId
     * @return
     */
    @GetMapping(value = "/configs/state")
    @RequiresPermissions("sys:dev:timer")
    public Result state(Long devId, Integer state) {
        Map map = new HashMap(1);
        CommonResult result = new CommonResult();
        SysConfigs sysConfigs = devsysConfigService.findByDevIdAndState(devId, state);
        if (sysConfigs == null) {
            return result.error(1113, "设备该机型不存在，错误码：1113");
        }
        map.put("type", sysConfigs.getTimingState());
        result.ok();
        result.setDatas(map);
        return result;
    }


    /**
     * 手动换水
     *
     * @param devId
     * @return
     */
    @PostMapping(value = "/configs/changeWater")
    @RequiresPermissions("sys:dev:hander")
    @LogOperate(description = "手动换水")
    public Result changeWater(Long devId, Integer state) {
        Result result = deviceService.getDevStuWithMsg(devId, state, 1);
        if (result.getRet() != 0) {
            return result;
        }
        try {
            int res = deviceService.changeWater(devId);
            DevUtils.getResult(result, res);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(1116, "下发命令失败，请稍后重试，错误码：1116");
        }
        return result;
    }

    /**
     * 手动消毒
     *
     * @param devId
     * @return
     */
    @PostMapping(value = "/configs/manualClean")
    @RequiresPermissions("sys:dev:hander")
    @LogOperate(description = "手动消毒")
    public Result manualClean(Long devId, Integer state) {
        Result result = deviceService.getDevStuWithMsg(devId, state, 1);
        if (result.getRet() != 0) {
            return result;
        }
        try {
            int res = deviceService.manualClean(devId);
            DevUtils.getResult(result, res);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(1117, "下发命令失败，请稍后重试，错误码：1117");
        }
        return result;
    }


    /**
     * 手动清洗
     *
     * @param devId
     * @return
     */
    @PostMapping(value = "/configs/manualWash")
    @RequiresPermissions("sys:dev:hander")
    @LogOperate(description = "手动清洗")
    public Result manualWash(Long devId, Integer state) {
        Result result = deviceService.getDevStuWithMsg(devId, state, 1);
        if (result.getRet() != 0) {
            return result;
        }
        try {
            int res = deviceService.manualWash(devId);
            DevUtils.getResult(result, res);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(1118, "下发命令失败，请稍后重试，错误码：1118");
        }
        return result;
    }

    /**
     * 手动复位净水量
     *
     * @param devId
     * @return
     */
    @PostMapping(value = "/configs/resetWater")
    @RequiresPermissions("sys:dev:hander")
    @LogOperate(description = "手动复位净水量")
    public Result resetWater(Long devId, Integer state) {
        Result result = deviceService.getDevStuWithMsg(devId, state, 1);
        if (result.getRet() != 0) {
            return result;
        }
        try {
            int res = deviceService.resetWaterVolume(devId);
            DevUtils.getResult(result, res);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(1119, "下发命令失败，请稍后重试，错误码：1119");
        }
        return result;
    }

    /**
     * 系统参数设置
     *
     * @param sysConfig
     * @return
     */
    @PostMapping(value = "/params/sys", produces = "application/json;charset=UTF-8")
    @RequiresPermissions("sys:dev:params")
    @LogOperate(description = "系统参数设置")
    public Result paras(@Validated @RequestBody SysConfigs sysConfig) {
        sysConfig.setSendTime(new Date());
        Result result = deviceService.getDevStuWithMsg(sysConfig.getDevId(), sysConfig.getState(), 1);
        if (result.getRet() != 0) {
            return result;
        }
        try {
            int res = deviceService.updateSysConfig(sysConfig);
            if (0 == res) {
                devsysConfigService.save(sysConfig);
            }
            DevUtils.getResult(result, res);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(1120, "下发命令失败，请稍后重试，错误码：1120");
        }
        return result;
    }

    /**
     * 获取系统参数
     *
     * @param devId
     * @return
     */
    @GetMapping(value = "/params/getSys")
    public Result getSys(Long devId, Integer state) {
        CommonResult com = new CommonResult();
        try {
            SysConfigs sysConfigs = devsysConfigService.findByDevIdAndState(devId, state);
            if (sysConfigs == null) {
                return com.error(1116, "设备该机型下系统参数不存在，错误码：1116");
            }
            com.setDatas(sysConfigs);
            com.ok();
        } catch (Exception e) {
            log.error(e.getMessage());
            return com.error(1121, "下发命令失败，请稍后重试，错误码：1121");
        }
        return com;

    }

    /**
     * 用户参数设置
     *
     * @param userConfig
     * @return
     */
    @PostMapping(value = "params/users", produces = "application/json;charset=UTF-8")
    @RequiresPermissions("sys:dev:users")
    @LogOperate(description = "用户参数设置")
    public Result user(@Validated @RequestBody UserConfigs userConfig) {
        userConfig.setSendTime(new Date());
        Result result = deviceService.getDevStuWithMsg(userConfig.getDevId(), userConfig.getState(), 1);
        if (result.getRet() != 0) {
            return result;
        }
        try {
            int ret = deviceService.updateUserConfigs(userConfig);
            if (0 == ret) {
                userConfigService.save(userConfig);
            }
            DevUtils.getResult(result, ret);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(1122, "下发命令失败，请稍后重试，错误码：1122");
        }
        return result;
    }

    /**
     * 获取用户参数
     *
     * @param devId
     * @return
     */
    @GetMapping(value = "/params/getUsers")
    public Result getUsers(Long devId, Integer state) {
        CommonResult result = new CommonResult();
        UserConfigs userConfig = userConfigService.findByDevIdAndState(devId, state);
        if (userConfig == null) {
            return result.error(1116, "设备该机型下用户参数不存在，错误码：1116");
        }
        result.ok();
        result.setDatas(userConfig);
        return result;
    }


    /**
     * 设置经销商
     *
     * @param userId 经销商ID
     * @return
     */
    @GetMapping(value = "/configs/user/{devId}")
    @RequiresPermissions("sys:dev:merch")
    @LogOperate(description = "设置经销商")
    public Result setAgency(@PathVariable Long devId, @RequestParam(value = "userId") Long userId) {
        Result result = new Result();
        try {
            DeviceEntity deviceEntity = deviceService.getDevice(devId);
            deviceEntity.setUserId(userId);
            if (!userId.equals(getUserId())) {
                //解绑方案 清楚备注
                deviceEntity.setSchemaId(null);
                deviceEntity.setRemark(null);
            }
            deviceService.saveDevice(deviceEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(1120, "设置失败，错误码：1122");
        }
        return result.ok();
    }

    /**
     * 获取经销商 超级管理员可以看所有  用户只能看自己下级所有的经销商  经销商只能看自己
     *
     * @return
     */
    @GetMapping(value = "/configs/user2")
    @RequiresPermissions("sys:dev:merch")
    public Result getAgency() {
        CommonResult result = new CommonResult();
        try {
            List<SysUserEntity> sysUserEntities = new LinkedList<>();
            Long userId = getUserId();
            if (1 == userId) {
                sysUserEntities = sysUserService.findAll();
            } else {
                //查看下级经销商列表
//                List<Long> userIdList = agencyService.findUserIdList(userId);
//                if(!CollectionUtils.isEmpty(userIdList)){
//                    userIdList.add(userId);
//                    sysUserEntities=sysUserService.findAllByUserIdIn(userIdList);
//                }else{
                    //没有下级经销商
                    SysUserEntity byUsername = sysUserService.findByUsername(getUser().getUsername());
                    sysUserEntities.add(byUsername);
//                }
            }
            result.setDatas(sysUserEntities);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(1120, "设置失败，错误码：1122");
        }
        return result.ok();
    }

    /**
     * 新增通道列表
     *
     * @param deviceEntity 设备对象
     * @return
     */
    @PostMapping(value = "/configs/passages/{devId}")
    @LogOperate(description = "新增通道列表")
    public Result setPassage(@PathVariable Long devId, @RequestBody DeviceEntity deviceEntity) {
        Result result = new Result();
        try {
            result = passageService.save(deviceEntity);
            if (result.getRet() != 0) {
                return result;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(1123, "通道设置失败，错误码：1123");
        }
        return result.ok();
    }

    /**
     * 获取设备的通道列表
     *
     * @param devId
     * @return
     */
    @GetMapping(value = "/configs/passage/{devId}")
    @RequiresPermissions("sys:dev:passage")
    public Result getPassages(@PathVariable Long devId) {
        CommonResult result = new CommonResult();
        DeviceEntity byId;
        try {
            //查询设备的通道列表
             List<PassageEntity> passList = passageService.getPassList(devId);
            //查询设备方案ID
            byId = deviceService.findById(devId);
            byId.setPassList(passList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return result.error(1123, "通道列表获取失败，错误码：1123");
        }
        result.setDatas(byId);
        result.ok();
        return result;
    }

    /**
     * 修改通道
     *
     * @param devId        设备ID
     * @param deviceEntity 设备
     * @return
     */
    @PutMapping(value = "/configs/passages/{devId}")
    @LogOperate(description = "修改通道")
    public Result updatePassage(@PathVariable Long devId, @RequestBody DeviceEntity deviceEntity) {
        Result result = new Result();
        try {
            result = passageService.update(deviceEntity);
            if (result.getRet() != 0) {
                return result;
            }

            List<PassageEntity> passageEntities = passageService.getPassList(devId);
            List<PassageEntitys> passageInfo = new ArrayList<>();

            if (passageEntities.size() > 0) {
                for (PassageEntity pe : passageEntities) {
                    PassageEntitys ps = new PassageEntitys();
                    BeanUtils.copyProperties(pe, ps);
                    passageInfo.add(ps);
                }
            }
            //下发通道信息
            hService.setPassageInfo(deviceEntity.getImei(), passageInfo);

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return result.error(1123, "更新通道失败，错误码：1123");
        }
        return result.ok();
    }

    /**
     * 手动开关机
     *
     * @param devId
     * @param machineStatus
     * @return
     */
    @PostMapping(value = "/configs/ctlMachineStatus")
    @LogOperate(description = "手动开关机")
    public Result ctlMachineStatus(Long devId, Integer machineStatus) {
        Result result = new Result();
        result.ok();
        try {
            int ret = deviceService.setMachineStatus(devId, machineStatus);
            if (ret == 0) {
                deviceService.updateStatus(devId, machineStatus);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return result.error(1124, "手动开关机失败，错误码：1124");
        }
        return result;
    }

    /**
     * 分配维修工
     *
     * @param repairerId 维修工Id
     * @param devIds     设备Id集合
     * @return
     */
    @GetMapping(value = "/allotRepairer/{repairerId}")
    @LogOperate(description = "分配维修工")
    public Result allotRepairer(@PathVariable Integer repairerId, @RequestParam String devIds) {
        Result result = new Result();
        try {
            List<Long> longList = StringUtil.stringToLongList(devIds);
            Result result1 = deviceService.allotRepairer(repairerId, longList);
            if (result1.getRet() != 0) {
                return result1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return result.error(1123, "分配失败，错误码：1123");
        }
        return result.ok();
    }
}

