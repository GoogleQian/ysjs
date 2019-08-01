
package com.xiaohe.ysjspt.modules.sys.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xiaohe.ysjspt.config.validate.ValidatorUtils;
import com.xiaohe.ysjspt.modules.agency.service.AgencyService;
import com.xiaohe.ysjspt.modules.sys.dao.SysUserDao;
import com.xiaohe.ysjspt.modules.sys.entity.SysRoleEntity;
import com.xiaohe.ysjspt.modules.sys.entity.SysUserEntity;
import com.xiaohe.ysjspt.modules.sys.service.SysRoleService;
import com.xiaohe.ysjspt.modules.sys.service.SysUserRoleService;
import com.xiaohe.ysjspt.modules.sys.service.SysUserService;
import com.xiaohe.ysjspt.modules.sys.shiro.ShiroUtils;
import com.xiaohe.ysjspt.modules.sys.untils.Constant;
import com.xiaohe.ysjspt.modules.sys.untils.PageUtils;
import com.xiaohe.ysjspt.modules.sys.untils.Query;
import com.xiaohe.ysjspt.modules.sys.untils.annotation.DataFilter;
import com.xiaohe.ysjspt.modules.wxconfig.entity.WxConfig;
import com.xiaohe.ysjspt.modules.wxconfig.service.WxConfigService;
import com.xiaohe.ysjspt.service.DeviceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    DeviceService deviceService;
    @Autowired
    WxConfigService wxConfigService;
    @Autowired
    AgencyService agencyService;

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String username = (String) params.get("username");

        Wrapper<SysUserEntity> wrapper = new EntityWrapper<SysUserEntity>()
                .like(StringUtils.isNotBlank(username), "username", username)
                .notIn("user_id", 1)
                .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER));
        if (ShiroUtils.getUserId() != 1) {
            //增加二级代理商
//            List<Long> userIdList = agencyService.findUserIdList(ShiroUtils.getUserId());
//            if(!CollectionUtils.isEmpty(userIdList)){
//                wrapper.in("user_id", userIdList);
//            }else{
                wrapper.in("user_id", ShiroUtils.getUserId().toString());
//            }
        }
        Page<SysUserEntity> page = this.selectPage(
                new Query<SysUserEntity>(params).getPage(),
                wrapper
        );
        for (SysUserEntity sysUserEntity : page.getRecords()) {
            List<Long> deviceIdByUserId = deviceService.findDeviceIdByUserId(sysUserEntity.getUserId());
            //获取绑定设备数
            sysUserEntity.setDevNum(deviceIdByUserId.size());
        }

        Wrapper<SysUserEntity> entityWrapper = new EntityWrapper<SysUserEntity>()
                .notIn("user_id", 1);
        if (ShiroUtils.getUserId() != 1) {
            entityWrapper.in("user_id", ShiroUtils.getUserId().toString());
        }
        Integer total = baseMapper.selectCount(entityWrapper);
        page.setTotal(total);
        return new PageUtils(page);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(SysUserEntity user) {
        user.setCreateTime(new Date());
        //TODO 暂时去掉加盐
        //sha256加密
        /*
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setSalt(salt);
		user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
		*/

        //检查账户名是否存在
        SysUserEntity byUsername = this.findByUsername(user.getUsername());
        if (byUsername != null) {
            return 1;
        }
        user.setCooperationTime(new Date());
        this.insert(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
        //保存用户与设备关系
        deviceService.saveOrUpdate(user.getUserId(), user.getDeviceIdList());
        WxConfig wxConfig = user.getWxConfig();
        wxConfig.setMerchantId(user.getUserId().intValue());
        ValidatorUtils.validateEntity(wxConfig);
        //保存微信配置信息
        wxConfigService.saveOrUpdate(user.getUserId().intValue(), user.getWxConfig());
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(SysUserEntity user) {
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            //TODO 暂时去掉加盐
            //user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
        }
        SysUserEntity byUsername = this.findByUsername(user.getUsername());
        if (byUsername != null) {
            int i = byUsername.getUserId().compareTo(user.getUserId());
            if (i != 0) {
                return 1;
            }
        }
        this.updateById(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
        //保存用户与设备关系
        deviceService.saveOrUpdate(user.getUserId(), user.getDeviceIdList());
        //保存微信配置信息
        wxConfigService.saveOrUpdate(user.getUserId().intValue(), user.getWxConfig());
        return 0;
    }

    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new EntityWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
    }

    @Override
    public SysUserEntity findByUsername(String username) {
        return baseMapper.findByUsername(username);
    }

    /**
     * 查询所有经销商（用户）
     *
     * @return
     */
    @Override
    public List<SysUserEntity> findAll() {
        return baseMapper.selectList(new EntityWrapper<>());
    }

    @Override
    public List<SysUserEntity> findAllByUserIdIn(List<Long> longs) {
        return baseMapper.selectList(new EntityWrapper<SysUserEntity>().in("user_id", longs));
    }
}
