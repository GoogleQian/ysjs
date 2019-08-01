import ax from './ax';

// 获取所有售水类型
export function saleTypeList(params) {
    return ax({
        url: `/waterTypes/`,
        method: 'get',
        params
    })
}
// 新增类型
export function addType(data) {
    return ax({
        url: `/waterTypes/`,
        method: 'post',
        data
    })
}
// 修改类型
export function editType({id, data}) {
    return ax({
        url: `/waterTypes/${id}`,
        method: 'put',
        data
    })
}
// 删除类型
export function delType({id}) {
    return ax({
        url: `/waterTypes/${id}`,
        method: 'delete'
    })
}