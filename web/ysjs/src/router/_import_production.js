// 生产环境进行路由懒加载
module.exports = file => () => import('@/view/' + file + '.vue')
