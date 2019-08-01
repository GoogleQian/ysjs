import ax from './ax'
let qs = require('qs')

export async function getHistoryInfo(data) {
  const result = await ax({
    url: `/history/get_info`,
    method: 'post',
    data: qs.stringify({ ...data })
  })
  return result
}
