import ax from './ax';

// 获取设备列表(尚未绑定)
export function getList(params){
    return ax({
        url: `status/`,
        method: 'get',
        params
    })
}