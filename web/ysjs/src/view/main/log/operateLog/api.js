import ax from '@/service/api/ax'

// 列表
export function getList(data) {
    return ax({
        url: `/operateLogs`,
        method: 'post',
        data
    })
}

// 新增
export function add(data) {
    return ax({
        url: `/operateLogs/`,
        method: 'post',
        data
    })
}

// 修改
export function edit({ id, data }) {
    return ax({
        url: `/operateLogs/${id}`,
        method: 'put',
        data
    })
}

// 删除
export function del({ id }) {
    return ax({
        url: `/operateLogs/${id}`,
        method: 'delete'
    })
}

// 详情
export function getDetail({ id }) {
    return ax({
        url: `/operateLogs/${id}`,
        method: 'get'
    })
}
