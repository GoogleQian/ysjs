import ax from '@/service/api/ax'

// 列表
export function login(data) {
    return ax({
        url: `/repairers/login`,
        method: 'post',
        data
    })
}