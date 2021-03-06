import ax from '@/service/api/ax'

// 列表
export function getList(params) {
    return ax({
        url: `status/`,
        method: 'get',
        params
    })
}

// 新增
export function add(data) {
    return ax({
        url: `/adv/upload`,
        method: 'post',
        data
    })
}

// 修改
export function edit({ id, data }) {
    return ax({
        url: `/adv/${id}`,
        method: 'put',
        data
    })
}

// 删除
export function del({ id }) {
    return ax({
        url: `/adv/${id}`,
        method: 'delete'
    })
}

// 详情
export function getDetail({ id }) {
    return ax({
        url: `/adv/${id}`,
        method: 'get'
    })
}
