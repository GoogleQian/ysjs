import ax from '@/service/api/ax';

// 获取数据
export function getData(params) {
    return ax({
        url: `/consumeInfo/consume`,
        method: 'get',
        params
    })
}

// 获取列表
export function getDevList(params) {
    return ax({
        url: `/device/ownDev`,
        method: 'get',
        params
    })
}

// 获取rank数据
export function getRankData(params) {
    return ax({
        url: `/consumeInfo/rank`,
        method: 'get',
        params
    })
}