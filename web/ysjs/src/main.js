// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App'
import router from './router'
import store from './store'
import vFilters from './filter/filter'
import '@/common/css/index.less'

Object.keys(vFilters).forEach(key => {
  Vue.filter(key, vFilters[key])
})

Vue.config.productionTip = false
console.log(returnCitySN)
Vue.use(ElementUI, { size: 'mini' })
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
