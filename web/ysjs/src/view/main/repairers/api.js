import ax from '@/service/api/ax'

// 列表
export function getList(data) {
    return ax({
        url: `/repairers`,
        method: 'post',
        data
    })
}

// 新增
export function add(data) {
    return ax({
        url: `/repairers/`,
        method: 'post',
        data
    })
}

// 修改
export function edit({ id, data }) {
    return ax({
        url: `/repairers/${id}`,
        method: 'put',
        data
    })
}

// 删除
export function del({ id }) {
    return ax({
        url: `/repairers/${id}`,
        method: 'delete'
    })
}

// 详情
export function getDetail({ id }) {
    return ax({
        url: `/repairers/${id}`,
        method: 'get'
    })
}
// 获取维修工设备列表
export function getDevDetail({ id }) {
    return ax({
        url: `/repairers/dev/${id}`,
        method: 'get'
    })
}
// 绑定维修员
export function allotRepairer({ repairId, params }) {
    return ax({
        url: `dev/allotRepairer/${repairId}`,
        method: 'get',
        params
    })
}

// 获取所有设备
export function getAllDev({id, params}) {
    return ax({
        url: `/repairers/devBind/${id}`,
        method: 'get',
        params
    })
}
// 获取维修员名下设备
export function getRepairDev({id, params}) {
    return ax({
        url: `/repairers/dev/${id}`,
        method: 'get',
        params
    })
}
