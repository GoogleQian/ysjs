import ax from './ax'
let qs = require('qs')

export async function getList(data) {
    const result = await ax({
        // url: `/sellProgram/findAll`,
        url: `/schema/`,
        method: 'get',
        params: data
    })
    return result
}

export async function add(data) {
    const result = await ax({
        // url: `/sellProgram/save`,
        url: `/schema/`,
        method: 'post',
        data: { ...data }
    })
    return result
}

export async function editDetail(data) {
    const result = await ax({
        // url: `/sellProgram/save`,
        url: `/schema/${data.id}`,
        method: 'put',
        data: { ...data }
    })
    return result
}

export async function del(data) {
    const result = await ax({
        // url: `/sellProgram/delete`,
        url: `/schema/${data.id}`,
        method: 'delete',
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
