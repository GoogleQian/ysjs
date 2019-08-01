import ax from './ax'
let qs = require('qs')

export async function getDevList(data) {
    const result = await ax({
        url: `/device/findAll`,
        method: 'post',
        data: qs.stringify({ ...data })
    })
    return result
}
