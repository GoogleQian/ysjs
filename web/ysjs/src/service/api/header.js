import ax from './ax'

export function getNav(params) {
    return ax({
        url: '/sys/menu/nav',
        method: 'get',
        params
    })
}