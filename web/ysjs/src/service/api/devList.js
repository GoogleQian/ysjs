import ax from './ax'
let qs = require('qs')

export async function getDevList(data) {
  const result = await ax({
    url: `/device/get_dev_list`,
    method: 'post',
    data: qs.stringify({ ...data })
  })
  return result
}

export async function addDev(data) {
  const result = await ax({
    url: `/device/add`,
    method: 'post',
    data: qs.stringify({ ...data })
  })
  return result
}

export async function editDevDetail(data) {
  const result = await ax({
    url: `/device/update_dev`,
    method: 'post',
    data: qs.stringify({ ...data })
  })
  return result
}

export async function delDev(data) {
  const result = await ax({
    url: `/device/delete`,
    method: 'post',
    data: qs.stringify({ ...data })
  })
  return result
}

export async function getAgentList(data) {
  const result = await ax({
    url: `/agent/get_agent_list`,
    method: 'post',
    data: qs.stringify({ ...data })
  })
  return result
}

export async function setAgent(data) {
  const result = await ax({
    url: `/device/assignment_dev`,
    method: 'post',
    data: qs.stringify({ ...data })
  })
  return result
}

export function allotRepairer({repairId, params}){
  return ax({
    url: `dev/allotRepairer/${repairId}`,
    method: 'get',
    params
  })
}

export function getAllRepairs(){
  return ax({
    url: `/repairers/own`,
    method: 'get'
  })
}