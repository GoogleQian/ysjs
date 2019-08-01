import ax from './ax'
let qs = require('qs')
// 获取用户列表
export async function getUserList(data) {
    const result = await ax({
        url: `/user/list`,
        method: 'post',
        data: qs.stringify({ ...data })
    })
    return result
}
// 权限修改
export async function update_permi(data) {
    const result = await ax({
        url: `/user/update_permi`,
        method: 'post',
        data: qs.stringify({ ...data })
    })
    return result
}

// 新增用户
export async function userAdd(data) {
    const result = await ax({
        url: `/user/add`,
        method: 'post',
        data: qs.stringify({ ...data })
    })
    return result
}

// 修改用户信息
export async function userEdit(data) {
    const result = await ax({
        url: `/user/update`,
        method: 'post',
        data: qs.stringify({ ...data })
    })
    return result
}

// 删除用户
export async function userDelete(data) {
    const result = await ax({
        url: `/user/delete`,
        method: 'post',
        data: qs.stringify({ ...data })
    })
    return result
}

// 修改备注
export async function remarkUpdate(data) {
    const result = await ax({
        url: `/user/update_remark`,
        method: 'post',
        data: qs.stringify({ ...data })
    })
    return result
}

// 修改密码
export async function psdUpdate(data) {
    const result = await ax({
        url: `/user/update_psd`,
        method: 'post',
        data: qs.stringify({ ...data })
    })
    return result
}