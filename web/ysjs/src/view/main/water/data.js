export const searchItems = [
    {
        label: '设备号',
        value: 'device_id',
        type: 'input',
        placeholder: '设备号'
    }, {
        label: '时间范围',
        value: 'daterange',
        type: 'datetimerange'
    }
]

export const tableColumns = [
    { label: '设备编号', value: 'device_id', width: '150' },
    { label: '入水TDS', value: 'tds', width: '100' },
    { label: '出水TDS', value: 'purify_tds', width: '100' },
    { label: '入水色度', value: 'color', width: '100' },
    { label: '出水色度', value: 'purify_color', width: '100' },
    { label: '入水浊度', value: 'tbdt', width: '100' },
    { label: '出水浊度', value: 'purify_tbdt', width: '100' },
    { label: '入水温度', value: 'trt', width: '100' },
    { label: '出水温度', value: 'purify_trt', width: '100' },
    { label: '记录时间', value: 'record_time' }
]