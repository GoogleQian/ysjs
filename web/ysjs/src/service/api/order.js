import ax from './ax'
let qs = require('qs')

export async function getOrderList(data) {
  const result = await ax({
    url: `/order/findAll`,
    method: 'post',
    data: qs.stringify({ ...data })
  })
  return result
}
