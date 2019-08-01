import ax from './ax';

// 获取设备列表(尚未绑定)
export function unbindDevs(params){
    return ax({
        url: `/device/unbind`,
        method: 'get',
        params
    })
}

// 获取所有用户
export function users(params){
    return ax({
        url: `/sys/user/list`,
        method: 'get',
        params
    })
}

// 新增用户
export function addUser(data){
    return ax({
        url: `/sys/user/save`,
        method: 'post',
        data
    })
}

// 修改用户
export function editUser(data){
    return ax({
        url: `/sys/user/update`,
        method: 'post',
        data
    })
}

// 删除用户
export function delUser(data){
    return ax({
        url: `/sys/user/delete`,
        method: 'post',
        data
    })
}

// 获取用户详情
export function getUser({user_id}){
    return ax({
        url: `sys/user/info/${user_id}`,
        method: 'get'
    })
}