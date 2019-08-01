import { appRoute } from '@/router/routes'
// import Vue from 'vue'

const app = {
  state: {
    // 保存项目中用到的各种状态
    isCollapse: false, // 左侧菜单是否最大化
    breadcrumbList: [
      { title: '首页', path: '/' }
    ],
    submitBtnLoading: false, // 保存表单按钮loading状态
    device_id: null,  // 操作设备：设备编号
    id: null,          // 操作设备：设备标识
    devModel: null,   // 设备模型
    devStatus: null,  // 设备在线状态
  },
  mutations: {
    toggleSubmitBtnLoading(state, status) {
      state.submitBtnLoading = status
    },
    toggleCollapse(state) {
      state.isCollapse = !state.isCollapse
      console.log(state.isCollapse)
    },
    setBreadcrumbList(state, breadcrumbList) { // 传入当前路由应显示的breadcrumbList
      state.breadcrumbList = [{ title: '首页', path: '/content/bdmap' }, ...breadcrumbList]
    },
    setDeviceId(state, device_id) { // 设置设备编号
      state.device_id = device_id;
    },
    setId(state, id) {              // 设置设备标识
      state.id = id
    },
    setDevModel(state, model) {
      state.devModel = model
    },
    setDevStatus(state, status) {
      state.devStatus = status;
    }
  }
}

export default app
