import ax from '@/service/api/ax';

// 获取列表
export function getList(params){
    return ax({
        url: `status/`,
        method: 'get',
        params
    })
}