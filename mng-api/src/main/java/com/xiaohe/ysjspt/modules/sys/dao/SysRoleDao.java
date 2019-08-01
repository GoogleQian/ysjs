
package com.xiaohe.ysjspt.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xiaohe.ysjspt.modules.sys.entity.SysRoleEntity;

import java.util.List;

/**
 * 角色管理
 */
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {
    /**
     * 通过userID查询角色
     *
     * @param userId
     * @return
     */
    List<SysRoleEntity> selectByUserId(Long userId);


}
