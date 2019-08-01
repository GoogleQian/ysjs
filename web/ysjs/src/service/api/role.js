import ax from './ax';

// 获取所有角色
export function roleList() {
    return ax({
        url: `/sys/role/list`,
        method: 'get'
    })
}

// 获取角色详情
export function role({ roleId }) {
    return ax({
        url: `/sys/role/info/${roleId}`,
        method: 'get'
    })
}

// 新增角色
export function addRole(data) {
    return ax({
        url: `/sys/role/save`,
        method: 'post',
        data
    })
}

// 修改角色
export function editRole(data) {
    return ax({
        url: `/sys/role/update`,
        method: 'post',
        data
    })
}

// 修改角色
export function delRole(data) {
    return ax({
        url: `/sys/role/delete`,
        method: 'post',
        data
    })
}

// 获取所有菜单
export function getAllMenu() {
    return ax({
        url: `/sys/menu/list`,
        method: 'get'
    })
}