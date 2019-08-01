export const searchItems = [ // 条件生成查询表单所依赖数据
  {
    label: '设备号',
    value: 'device_id',
    type: 'input',
    placeholder: '设备号'
  }
]

export const tableColumns = [
  { label: '订单号', value: 'order_no' },
  { label: '设备号', value: 'dev_id' },
  { label: '创建时间', value: 'create_time', type: 'stamp' },
  { label: '出水类型', value: 'water_temp', type: 'water_temp', formatter(row){
    if(row.water_temp === 1){
      return `<span style="color: #F56C6C">开水</span>`
    }else if(row.water_temp === 2){
      return `<span style="color: #E6A23C">温水</span>`
    }else if(row.water_temp === 3){
      return `<span style="color: #67C23A">直饮水</span>`
    }else if(row.water_temp === 4){
      return `<span style="color: #409eff">冰水</span>`
    }else{
      return `-`
    }
  } },
  { label: '售水量（ml）', value: 'water_amount' },
  { label: '脉冲数', value: 'pulse' },
  { label: '订单金额（元）', value: 'money_amount', type: 'money', formatter(row){
    return row.money_amount / 100
  } },
  // { label: '交易号', value: 'trade_no', width: 300 },
  { label: '支付状态', value: 'pay_status', type: 'pay_status', formatter(row){
    if(row.pay_status === 1){
      return `<span style="color: #E6A23C">待支付</span>`
    }else if(row.pay_status === 2){
      return `<span style="color: #67C23A">已支付</span>`
    }else if(row.pay_status === 3){
      return `<span style="color: #409eff">已退款</span>`
    }else{
      return `-`
    }
  } },
  { label: '出水状态', value: 'sell_status', type: 'sell_status', formatter(row){
    if(row.sell_status === 0){
      return `<span style="color: #409eff">订单已取消</span>`
    }else if(row.sell_status === 1){
      return `<span style="color: #67C23A">已出水</span>`
    }else if(row.sell_status === 2){
      return `<span style="color: #E6A23C">待出水</span>`
    }else if(row.sell_status === 3){
      return `<span style="color: #F56C6C">售卖失败</span>`
    }else{
      return `-`
    }
  }  },
]
