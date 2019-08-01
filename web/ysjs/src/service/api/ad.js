import ax from './ax';
import qs from 'qs';

// 新增广告
export function uploadAd(data) {
    return ax({
        url: `/adv/upload`,
        method: 'post',
        "Content-Type": 'application/x-www-form-urlencoded',
        data
    })
}
// 修改广告
export function editAd({ id, data }) {
    return ax({
        url: `/adv/${id}`,
        method: 'put',
        "Content-Type": 'application/x-www-form-urlencoded',
        data
    })
}
// 获取广告列表
export function getList(params) {
    return ax({
        url: `/adv/findAll`,
        method: 'get',
        params
    })
}

// 删除广告
export function delAd({adId}) {
    return ax({
        url: `/adv/${adId}`,
        method: 'delete'
    })
}

// 获取广告详情
export function getAdDetail({adId}){
    return ax({
        url: `/adv/${adId}`,
        method: 'get'
    })
}