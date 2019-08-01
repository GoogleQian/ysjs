import http from './http_upload'
import qs from 'qs'

// 上传formData
export async function uploadForm(data) {
    const result = await http({
        url: '/agent/add',
        method: 'post',
        data: data
    })
    return result
}

// 删除代理商
export async function deleteAgent(data) {
    const result = await http({
        url: '/agent/delete',
        method: 'post',
        data: qs.stringify({ ...data })
    })
    return result
}