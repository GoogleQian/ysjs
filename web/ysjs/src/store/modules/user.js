import { getNav } from "@/service/api/header";
import store from '@/store'
const user = {
    state: {
        token: '',
        currentNav: '',     // 当前目录
        menus: [],
        funcs: [],          // 当前菜单功能按钮
        useragent: 'pc',    // 浏览器平台标识

    },
    mutations: {
        setToken(state, token) {
            state.token = token;
        },
        setCurrentNav(state, currentNav) {
            state.currentNav = currentNav;
        },
        setUseragent(state, useragent) {
            state.useragent = useragent;
        },
        setMenus(state, menus) {
            state.menus = menus
        },
        setfuncs(state, route) {
            let path = route.fullPath.split('/')[2];
            // 手机端dev页面不包含以下四个功能
            if (path === 'dev' && state.useragent === 'phone') {
                let _funcs = state.menus[0].list.map(item => item.name)
                _funcs = _funcs.filter(item => !['新增', '修改', '删除', '商户管理'].includes(item))
                state.funcs = _funcs
            } else {
                // 二级菜单特殊处理
                console.log(state.menus)
                let funcs = state.menus.filter(item => {
                    if (path === 'role') {
                        return item.url === 'system'
                    } else if (path === 'ad') {
                        return item.url === 'market-manage'
                    } else if (path === 'coupon') {
                        return item.url === 'sales'
                    } else if (path === 'loginLog') {
                        return item.url === 'log'
                    } else {
                        let flag = item.url === path;
                        return flag
                    }
                })
                if (path === 'coupon' || path === 'role' || path === 'ad' || path === 'loginLog') {
                    let _funcs = funcs[0].list.filter(item => {
                        return item.url === path
                    })
                    state.funcs = _funcs[0].list.map(item => item.name)
                } else {
                    if (funcs[0]) {
                        state.funcs = funcs[0].list.map(item => item.name);
                    } else {
                        state.funcs = []
                    }
                }
            }
        }
    },
    actions: {
        setMenus({ commit, state }) {
            // 不是维修工
            if (localStorage.getItem('isRepairer') != 1) {
                getNav().then(res => {
                    commit('setMenus', res.data.menuList)
                })
            }
        },
        setfuncs({ commit, state }, route) {
            // 在此监测是否已获取menus，若未获取menus，一秒后重试
            fn(route)
        }
    }
}
function fn(route) {
    if (store.state.user.menus.length === 0) {
        window.s = setTimeout(() => {
            fn(route)
        }, 500);
    } else {
        store.commit('setfuncs', route)
    }
}
export default user
