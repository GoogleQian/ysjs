// 开发环境不进行路由懒加载，提高运行速度
module.exports = file => require('@/view/' + file + '.vue').default
