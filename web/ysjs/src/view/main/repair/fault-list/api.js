import ax from '@/service/api/ax'

// 列表
export function getList(data) {
    return ax({
        url: `/repairers/devFaults`,
        method: 'post',
        data
    })
}

// 新增
export function add(data) {
    return ax({
        url: `/repairRecord/`,
        method: 'post',
        data
    })
}

// 修改
export function edit({ id, data }) {
    return ax({
        url: `/repairRecord/${id}`,
        method: 'put',
        data
    })
}

// 删除
export function del({ id }) {
    return ax({
        url: `/repairRecord/${id}`,
        method: 'delete'
    })
}

// 详情
export function getDetail({ id }) {
    return ax({
        url: `/repairRecord/${id}`,
        method: 'get'
    })
}

// 获取全部维修员
export function getAllRepairs() {
    return ax({
        url: `/repairer/own`,
        method: 'get'
    })
}