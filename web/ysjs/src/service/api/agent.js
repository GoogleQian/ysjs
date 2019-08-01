import ax from './ax'
import qs from 'qs'

// 新增代理商
export async function addAgent(data) {
  const result = await ax({
    url: '/agent/add',
    method: 'post',
    data: qs.stringify({ ...data })
  })
  return result
}

// 删除代理商
export async function deleteAgent(data) {
  const result = await ax({
    url: '/agent/delete',
    method: 'post',
    data: qs.stringify({ ...data })
  })
  return result
}

// 修改代理商密码
export async function updateAgent(data) {
  const result = await ax({
    url: '/agent/update',
    method: 'post',
    data: qs.stringify({ ...data })
  })
  return result
}

// 获取二级代理商列表
export async function getAgentList(data) {
  const result = await ax({
    url: '/agent/get_agent_list',
    method: 'post',
    data: qs.stringify({ ...data })
  })
  return result
}
