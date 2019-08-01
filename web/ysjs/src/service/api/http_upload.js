import axios from 'axios'
import { Message } from 'element-ui'
import router from '@/router/index.js'

// 创建axios实例
const ax = axios.create({
    baseURL: process.env.BASE_API,
    timeout: 100000000
})

// 请求拦截
ax.interceptors.request.use(config => {
    if (window.sessionStorage.getItem('token')) {
        config.headers.Authorization = window.sessionStorage.getItem('token')
    }
    //   设置headers
    config.headers['Content-Type'] = 'multipart/form-data';
    return config
}, error => {
    console.log('request error: ' + error)
    return Promise.reject(error)
})

ax.interceptors.response.use(res => {
    if (res.status !== 200) {
        return
        Message({
            message: '请求错误,请重试!',
            type: 'error',
            duration: 5 * 1000
        })
        router.replace({ name: 'login' })
    }
    return res
}, error => {
    if (String(error).indexOf('timeout') !== -1) { // 请求超时处理
        Message({
            message: '请求超时，请刷新重试！',
            duration: 5 * 1000,
            type: 'error'
        })
        return Promise.reject(error)
    }

    if (String(error).indexOf('500') !== -1) { // 500错误处理
        Message({
            message: '服务器错误！',
            duration: 5 * 1000,
            type: 'error'
        })
        return Promise.reject(error)
    }
    return Promise.reject(error)
})

export default ax
