import ax from './ax'
let qs = require('qs')
// 获取滤芯更换计划列表
export async function getFilterList(data) {
  const result = await ax({
    url: `/filter/get_replace_plan`,
    method: 'post',
    data: qs.stringify({ ...data })
  })
  return result
}

// 生成当月滤芯更换计划
export async function addPlan(data) {
  const result = await ax({
    url: `/filter/gen_replace_plan`,
    method: 'post',
    data: qs.stringify({ ...data })
  })
  return result
}

// 回写滤芯更换信息
export async function replaceFilter(data) {
  const result = await ax({
    url: `/filter/replace_filter`,
    method: 'post',
    data: qs.stringify({ ...data })
  })
  return result
}

// 设置单个设备每根滤芯预计更换时间
export async function addReplaceTime(data) {
  const result = await ax({
    url: `/filter/add_replace_time`,
    method: 'post',
    data: qs.stringify({ ...data })
  })
  return result
}