import ax from './ax'
let qs = require('qs')

// 查询机型
export async function getModel(data) {
    const result = await ax({
        url: '/dev/model',
        method: 'get',
        params: data
    })
    return result
}
// 设置机型
export async function setModel(data) {
    const result = await ax({
        url: '/dev/models',
        method: 'post',
        data: qs.stringify({ ...data })
    })
    return result
}
// 恢复默认设置
export async function reset(data) {
    const result = await ax({
        url: `/dev/configs`,
        method: 'post',
        data: qs.stringify({ ...data })
    })
    return result
}
// 查询定时启动和取消
export async function getTimeModel(data) {
    const result = await ax({
        url: '/dev/configs/state',
        method: 'get',
        params: { ...data }
    })
    return result
}
// 设置定时启动和取消
export async function setTimeModel(data) {
    const result = await ax({
        url: '/dev/configs/timer',
        method: 'post',
        data: qs.stringify({ ...data })
    })
    return result
}
// 手动换水
export async function changeWater(data) {
    const result = await ax({
        url: '/dev/configs/changeWater',
        method: 'post',
        data: qs.stringify({ ...data })
    })
    return result
}
// 手动消毒
export async function manualClean(data) {
    const result = await ax({
        url: '/dev/configs/manualClean',
        method: 'post',
        data: qs.stringify({ ...data })
    })
    return result
}
// 手动冲洗
export async function wash(data) {
    const result = await ax({
        url: '/dev/configs/manualWash',
        method: 'post',
        data: qs.stringify({ ...data })
    })
    return result
}
// 手动复位净水量
export async function resetWater(data) {
    const result = await ax({
        url: '/dev/configs/resetWater',
        method: 'post',
        data: qs.stringify({ ...data })
    })
    return result
}
// 获取系统参数
export async function getSysParams(data) {
    const result = await ax({
        url: '/dev/params/getSys',
        method: 'get',
        params: { ...data }
    })
    return result
}
// 设置系统参数
export async function setSysParams(data) {
    const result = await ax({
        url: '/dev/params/sys',
        method: 'post',
        data: { ...data }
    })
    return result
}
// 用户参数设置
export async function setUserParams(data) {
    const result = await ax({
        url: '/dev/params/users',
        method: 'post',
        data: { ...data }
    })
    return result
}
// 获取参数设置
export async function getUserParams(data) {
    const result = await ax({
        url: '/dev/params/getUsers',
        method: 'get',
        params: { ...data }
    })
    return result
}

// 手动开关机状态控制
export function ctlMachineStatus(data){
    return ax({
        url: `/dev/configs/ctlMachineStatus`,
        method: 'post',
        data: qs.stringify(data)
    })
}