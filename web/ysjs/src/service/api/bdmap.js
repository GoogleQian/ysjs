import ax from './ax'
import qs from 'qs'

// 获取省份列表及各省数量
export async function getProList(data) {
  const result = await ax({
    url: '/device/province_count',
    method: 'post',
    data: qs.stringify({ ...data })
  })
  return result
}

// 获取某省所有设备
export async function getDevList(data) {
  const result = await ax({
    url: '/device/province_list',
    method: 'post',
    data: qs.stringify({ ...data })
  })
  return result
}
