import ax from './ax'
let qs = require('qs')

// 获取优惠券列表
export function getList(params) {
    return ax({
        url: `/sale/coupons`,
        method: 'get',
        params
    })
}

// 新增优惠券
export function addCoupons(data) {
    return ax({
        url: `/sale/coupons`,
        method: 'post',
        data
    })
}

// 修改优惠券
export function editCoupons({ couponId, data }) {
    return ax({
        url: `/sale/coupons/${couponId}`,
        method: 'put',
        data
    })
}

// 删除优惠券
export function delCoupons({ couponId }) {
    return ax({
        url: `/sale/coupons/${couponId}`,
        method: 'delete'
    })
}

// 获取未选设备编号
export function getUnbindDevs(params) {
    return ax({
        url: `sale/coupons/devIds`,
        method: 'get',
        params
    })
}