import store from '@/store'
// import Main from '@/components/main/main'
import Content from '@/components/content/content'

// 根据不同环境切换是否开启懒加载
const _import = require('./_import_' + process.env.NODE_ENV)

// 这部分路由不作为main的子路由
const otherRoutes = [
  { path: '/', redirect: '/content/dev' },
  { path: '/login', name: 'login', meta: { title: '登录' }, component: _import('login/login') },
  { path: '/print', name: '打印', component: () => import("@/components/z-print/z-print") },
]

export const _routes = [
  // name  icon  path
  { path: 'dev-status', name: 'dev-status', icon: 'icon-error', meta: { title: '设备运行状态', icon: 'icon-error', breadcrumbList: [{ title: '设备运行状态' }] }, component: _import('main/dev-status/dev-status') },
  { path: 'dev', name: 'dev', icon: "icon-shebei", meta: { title: '设备管理', icon: 'icon-xiangmuguanli', breadcrumbList: [{ title: '设备管理' }] }, component: _import('main/dev/dev') },
  { path: 'water', name: 'water', icon: "icon-huanjingxinxi-", meta: { title: '水质管理', icon: 'icon-guzhangguanli', breadcrumbList: [{ title: '水质管理' }] }, component: _import('main/water/water') },
  { path: 'user-statistics', name: 'statictics', icon: "icon-caiwufenxi", meta: { title: '财务分析', icon: 'icon-caiwufenxi', breadcrumbList: [{ title: '财务分析' }] }, component: _import('main/user-statistics/index') },
  { path: 'pay-log', name: 'paylog', icon: "icon-ziyuan", meta: { title: '扫码记录', icon: 'icon-ziyuan', breadcrumbList: [{ title: '扫码记录' }] }, component: _import('main/pay-log/index') },
  { path: 'water-standard', name: 'water-standard', icon: "icon-cf-c11", meta: { title: '水质等级标准', icon: 'icon-cf-c11', breadcrumbList: [{ title: '水质等级标准' }] }, component: _import('main/water-standard/water-standard') },
  { path: 'sale-plan2', name: 'sale-plan', icon: "icon-fangan-", meta: { title: '售水方案', icon: 'icon-fangan-', breadcrumbList: [{ title: '售水方案' }] }, component: _import('main/sale-plan/sale-plan') },
  { path: 'role', name: 'role', icon: "icon-navicon-jsgl", meta: { title: '权限管理', icon: 'icon-navicon-jsgl', breadcrumbList: [{ title: '权限管理' }] }, component: _import('main/role/role') },
  { path: 'account', name: 'account', icon: "icon-zhanghaoguanli", meta: { title: '商户管理', icon: 'icon-zhanghaoguanli', breadcrumbList: [{ title: '商户管理' }] }, component: _import('main/account/account') },
  { path: 'coupon', name: 'coupon', icon: "icon-zhanghaoguanli", meta: { title: '优惠券管理', icon: 'icon-zhanghaoguanli', breadcrumbList: [{ title: '优惠券管理' }] }, component: _import('main/coupon/coupon') },
  { path: 'ad', name: 'ads', icon: "icon-zhanghaoguanli", meta: { title: '广告管理', icon: 'icon-zhanghaoguanli', breadcrumbList: [{ title: '广告管理' }] }, component: _import('main/market-manage/ad/ads') },
  { path: 'editAd', name: 'editAd', icon: "icon-zhanghaoguanli", meta: { title: '广告编辑', icon: 'icon-zhanghaoguanli', breadcrumbList: [{ title: '广告编辑' }] }, component: _import('main/market-manage/ad/editAd/editAd') },
  { path: 'sale-type', name: 'sale-type', icon: "icon-zhanghaoguanli", meta: { title: '售水类型', icon: 'icon-zhanghaoguanli', breadcrumbList: [{ title: '促销管理' }] }, component: _import('main/sale-type/sale-type') },
  { path: 'complaints', name: 'complaints', icon: "icon-zhanghaoguanli", meta: { title: '投诉与建议', icon: 'icon-zhanghaoguanli', breadcrumbList: [{ title: '投诉与建议' }] }, component: _import('main/complaints/complaints') },
  { path: 'repair-record', name: 'repair-record', icon: "icon-zhanghaoguanli", meta: { title: '维修记录', icon: 'icon-zhanghaoguanli', breadcrumbList: [{ title: '维修记录' }] }, component: _import('main/repair-record/repair-record') },
  { path: 'repairers', name: 'repairers', icon: "icon-zhanghaoguanli", meta: { title: '维修员管理', icon: 'icon-zhanghaoguanli', breadcrumbList: [{ title: '维修员管理' }] }, component: _import('main/repairers/repairers') },
  { path: 'banner-editer', name: 'banner-editer', icon: "icon-ZHicon-", meta: { title: '富文本', icon: 'icon-ZHicon-', breadcrumbList: [{ title: '富文本' }] }, component: _import('main/banner-editer/banner-editer') },
  { path: 'water_status', name: 'water_status', icon: "icon-ZHicon-", component: _import('main/water_status/water_status') },
  { path: 'loginLog', name: 'loginLog', component: _import("main/log/loginLog/loginLog") },
  { path: 'operateLog', name: 'operateLog', component: _import("main/log/operateLog/operateLog") },
  { path: 'errorLog', name: 'errorLog', component: _import("main/log/errorLog/errorLog") }
]

export const _setRoutes = [
  { path: 'set-panel', name: 'set-panel', icon: "icon-shebei", meta: { title: '设备设置', icon: 'icon-xiangmuguanli', breadcrumbList: [{ title: '设备设置' }] }, component: _import('main/dev/set-panel/set-panel') },
  { path: 'setting', name: 'setting', icon: "icon-shebei", meta: { title: '设置', icon: 'icon-xiangmuguanli', breadcrumbList: [{ title: '设置' }] }, component: _import('main/dev/set-panel/setting/setting') },
  { path: 'merchant', name: 'merchant', icon: "icon-shebei", meta: { title: '商户管理', icon: 'icon-xiangmuguanli', breadcrumbList: [{ title: '商户管理' }] }, component: _import('main/dev/manage/Merchant') },
  { path: 'pip', name: 'pip', icon: "icon-shebei", meta: { title: '通道管理', icon: 'icon-xiangmuguanli', breadcrumbList: [{ title: '通道管理' }] }, component: _import('main/dev/manage/pip') },
  { path: 'dev-ctrl', name: 'dev-ctrl', icon: "icon-shebei", meta: { title: '手动操作', icon: 'icon-xiangmuguanli', breadcrumbList: [{ title: '手动操作' }] }, component: _import('main/dev/dev-ctrl/dev-ctrl') },
  { path: 'default-set', name: 'default-set', icon: "icon-shebei", meta: { title: '手动操作', icon: 'icon-xiangmuguanli', breadcrumbList: [{ title: '手动操作' }] }, component: _import('main/dev/dev-ctrl/default-set') },
  { path: 'model-set', name: 'model-set', icon: "icon-shebei", meta: { title: '手动操作', icon: 'icon-xiangmuguanli', breadcrumbList: [{ title: '手动操作' }] }, component: _import('main/dev/dev-ctrl/model-set') },
  { path: 'sys-set', name: 'sys-set', icon: "icon-shebei", meta: { title: '手动操作', icon: 'icon-xiangmuguanli', breadcrumbList: [{ title: '手动操作' }] }, component: _import('main/dev/dev-ctrl/sys-set') },
  { path: 'timing-set', name: 'timing-set', icon: "icon-shebei", meta: { title: '手动操作', icon: 'icon-xiangmuguanli', breadcrumbList: [{ title: '手动操作' }] }, component: _import('main/dev/dev-ctrl/timing-set') },
  { path: 'user-set', name: 'user-set', icon: "icon-shebei", meta: { title: '手动操作', icon: 'icon-xiangmuguanli', breadcrumbList: [{ title: '手动操作' }] }, component: _import('main/dev/dev-ctrl/user-set') },
  // { path: 'manage', name: 'manage', icon: "icon-shebei", meta: { title: '设备管理', icon: 'icon-xiangmuguanli', breadcrumbList: [{ title: '设备管理' }] }, component: _import('main/dev/manage/index') },
]
// content的子路由，并且在leftBar展示
export const appRoute = {
  path: '/content',
  component: Content,
  name: 'Content',
  redirect: '/content/dev',
  children: [..._routes, ..._setRoutes]
}

// 维修员路由
// 维修员只有固定的维修记录、设备列表、投诉与建议菜单
export const repairRoutes = [
  {
    path: '/r-login',
    name: 'r-login',
    component: _import('r-login/r-login')
  },
  {
    path: '/repair',
    component: Content,
    name: 'repair',
    redirect: '/repair/repair-record',
    children: [
      { path: 'repair-record', name: 'repair-record', icon: "icon-zhanghaoguanli", meta: { title: '维修记录', icon: 'icon-zhanghaoguanli', breadcrumbList: [{ title: '维修记录' }] }, component: _import('main/repair/repair-record/repair-record') },
      { path: 'complaints', name: 'complaints', icon: "icon-zhanghaoguanli", meta: { title: '投诉与建议', icon: 'icon-zhanghaoguanli', breadcrumbList: [{ title: '投诉与建议' }] }, component: _import('main/repair/complaints/complaints') },
      { path: 'fault-list', name: 'fault-list', icon: "icon-zhanghaoguanli", meta: { title: '故障设备', icon: 'icon-zhanghaoguanli', breadcrumbList: [{ title: '故障设备' }] }, component: _import('main/repair/fault-list/fault-list') },
    ]
  }]
const phoneRouter = [
  { path: '/content/dev', name: 'dev', component: _import('main/dev/dev') }, // 设备列表
  { path: '/content/set-panel', name: 'set-panel', component: _import('main/dev/set-panel/set-panel') }, // 设备参数设置
  { path: '/content/pip', name: 'pip', icon: "icon-shebei", component: _import('main/dev/manage/pip') },  // 通道管理
  { path: '/content/dev-ctrl', name: 'dev-ctrl', icon: "icon-shebei", component: _import('main/dev/dev-ctrl/dev-ctrl') }, // 设备操作
  { path: '/content/sys-set', name: 'sys-set', icon: "icon-shebei", component: _import('main/dev/dev-ctrl/sys-set') }, // 设备操作
  { path: '/content/model-set', name: 'model-set', icon: "icon-shebei", component: _import('main/dev/dev-ctrl/model-set') }, // 机型设置
  { path: '/content/user-set', name: 'user-set', icon: "icon-shebei", component: _import('main/dev/dev-ctrl/user-set') }, // 用户参数设置
  { path: '/content/default-set', name: 'default-set', icon: "icon-shebei", component: _import('main/dev/dev-ctrl/default-set') },  // 恢复默认设置
  { path: '/content/timing-set', name: 'timing-set', icon: "icon-shebei", component: _import('main/dev/dev-ctrl/timing-set') }, // 定时启动和取消
  ...otherRoutes,
]

// pc端router
let __routes = []
function isApp() {
  var userAgent = window.navigator.userAgent.toLowerCase();
  var bIsIpad = userAgent.match(/ipad/i) == "ipad";
  var bIsIphone = userAgent.match(/iphone os/i) == "iphone os";
  var bIsMidp = userAgent.match(/midp/i) == "midp";
  var bIsUc7 = userAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
  var bIsUc = userAgent.match(/ucweb/i) == "web";
  var bIsCE = userAgent.match(/windows ce/i) == "windows ce";
  var bIsWM = userAgent.match(/windows mobile/i) == "windows mobile";
  var bIsAndroid = userAgent.match(/android/i) == "android";

  if (
    bIsIpad ||
    bIsIphone ||
    bIsMidp ||
    bIsUc7 ||
    bIsUc ||
    bIsCE ||
    bIsWM ||
    bIsAndroid
  ) {
    __routes = [...phoneRouter, ...repairRoutes, { path: '/*', name: 'error', meta: { title: '404-页面不存在' }, component: _import('error/error') }]
    store.commit('setUseragent', 'phone')
  } else {
    __routes = [appRoute, ...otherRoutes, ...repairRoutes, { path: '/*', name: 'error', meta: { title: '404-页面不存在' }, component: _import('error/error') }]
    store.commit('setUseragent', 'pc')
  }
}
isApp()
console.log(__routes)
export const routes = __routes
