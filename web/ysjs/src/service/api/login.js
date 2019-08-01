import ax from './ax'

// 登录
export function login(query) {
    return ax({
        url: '/access/login',
        method: 'get',
        // params: query
        params: query
    })
}

// 图形验证码
export function captcha(query) {
    return ax({
        url: '/access/captcha',
        method: 'get',
        // params: query
        // data: query,
        params: query,
        // headers: {
        //     'Content-Type': 'iamge/jpeg;'
        // }
    })
}

// 退出
export function logout(query) {
    return ax({
        url: '/access/logout',
        method: 'get',
        // params: query
        data: query
    })
}

// 获取菜单项
export function getNav(params) {
    return ax({
        url: '/sys/menu/nav',
        method: 'get',
        params
    })
}