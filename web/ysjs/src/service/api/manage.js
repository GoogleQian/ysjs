import ax from './ax';

// 获取所有经销商
export function userlist() {
    return ax({
        url: `/dev/configs/user2`,
        method: 'get',
    })
}

// 给设备绑定经销商
export function bindUser({id, params}){
    return ax({
        url: `dev/configs/user/${id}`,
        method: 'get',
        params
    })
}

// 获取通道列表
export function getPips({id}){
    return ax({
        url: `/dev/configs/passage/${id}`,
        method: 'get'
    })
}

// 新增通道列表
export function setPips({id, data}){
    return ax({
        url: `/dev/configs/passages/${id}`,
        method: 'post',
        data
    })
}

// 修改通道列表
export function setPips1({id, data}){
    return ax({
        url: `/dev/configs/passages/${id}`,
        method: 'put',
        data
    })
}

// 获取价格方案
export function getPrice(params){
    return ax({
        url: `/solution/getPrice`,
        method: 'get',
        params
    })
}
