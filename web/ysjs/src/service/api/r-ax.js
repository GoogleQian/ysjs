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
    config.headers.token = localStorage.getItem('token');
    config.headers['x-forwarded-for'] = returnCitySN.cip;
    return config
}, error => {
    return Promise.reject(error)
})

ax.interceptors.response.use(res => {
    if (res.status !== 200) {
        return;
        Message({
            message: '请求错误,请重试!',
            type: 'error',
            duration: 5 * 1000
        })
        router.replace({ name: 'login' })
    }
    return res
}, error => {
    if (error.response) {
        if (error.response.status === 401) {
            Message({
                message: '登录失效！',
                duration: 2 * 1000,
                type: 'error'
            })
            if (localStorage.getItem('isRepairer') == '1') {
                // 维修员
                router.push({ path: '/r-login' })
            } else {
                router.push({ path: '/login' })
            }
            return Promise.reject(error)
        }
    }
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

    // Message({ // 其他错误处理
    //   message: '登录认证失败，请重新登录！',
    //   type: 'error',
    //   duration: 5 * 1000
    // })
    // router.replace({ name: 'login' })
    return Promise.reject(error)
})

export default ax
