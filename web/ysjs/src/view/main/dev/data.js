// 搜索项
export const searchItems = [
    {
        label: '设备号',
        value: 'device_id',
        type: 'input',
        placeholder: '设备号'
    }
]

// 表格项
export const tableColumns = [
    { label: '设备编号', width: 120, value: 'device_id', },
    // 根据出水TDS判断
    {
        label: '最新水质状况', value: 'waterStatus',
        formatter(row) {
            const waterStatus = row.waterStatus || {}
            let out_tds = waterStatus.out_tds
            if (out_tds === undefined) {
                return ''
            } else if (out_tds <= 50) {
                return `<span style="color: #67C23A;">优</span>`
            } else if (out_tds <= 100 && out_tds > 50) {
                return `<span style="color: #409EFF;">良</span>`
            } else if (out_tds <= 300 && out_tds > 100) {
                return `<span style="color: #E6A23C;">中</span>`
            } else if (out_tds > 100) {
                return `<span style="color: #F56C6C;">差</span>`
            }
            return waterStatus.out_tds;
        }
    },
    {
        label: '在线状态', value: 'isOnline', formatter(row) {
            switch (row.isOnline) {
                case 0:
                    return '<span style="color: #F56C6C;">离线</span>'
                case 1:
                    return '<span style="color: #67C23A;">在线</span>'
            }
        }
    },
    {
        label: '设备最新状态', mul: true, subColumns: [
            {
                label: '水位', width: 50, formatter(row) {
                    const waterStatus = row.waterStatus || {};
                    let text = ''
                    if (waterStatus.water_level_status === 0) {
                        text = '无'
                    } else if (waterStatus.water_level_status === 1) {
                        text = '低'
                    } else if (waterStatus.water_level_status === 2) {
                        text = '中'
                    } else if (waterStatus.water_level_status === 3) {
                        text = '高'
                    }
                    return text
                }
            },
            {
                label: '信号强度', width: 80, formatter(row) {
                    const waterStatus = row.waterStatus || {};
                    let text = '';
                    if (waterStatus.model_strength === 0) {
                        text = '极低'
                    } else if (waterStatus.model_strength === 1) {
                        text = '低'
                    } else if (waterStatus.model_strength === 2) {
                        text = '中'
                    } else if (waterStatus.model_strength === 3) {
                        text = '高'
                    }
                    return text;
                }
            },
            {
                label: '故障代码', width: 80, formatter(row) {
                    const waterStatus = row.waterStatus || {};
                    if (waterStatus.error_code) {
                        return waterStatus.error_code.toString(16)
                    } else {
                        return ''
                    }
                    // let text = '';
                    // if (waterStatus.error_code === 224) {
                    //     text = '滤芯防伪'
                    // } else if (waterStatus.error_code === 225) {
                    //     text = '超温故障'
                    // } else if (waterStatus.error_code === 226) {
                    //     text = '顶部故障'
                    // } else if (waterStatus.error_code === 227) {
                    //     text = '底部故障'
                    // } else if (waterStatus.error_code === 229) {
                    //     text = '进水故障'
                    // } else if (waterStatus.error_code === 230) {
                    //     text = '溢出故障'
                    // } else if (waterStatus.error_code === 231) {
                    //     text = '结垢故障'
                    // }
                    // return text;
                }
            }, {
                label: '换水状态', width: 70, formatter(row) {
                    const change_water_status = row.change_water_status || {};
                    let text = ''
                    if (change_water_status === 0) {
                        text = '-'
                    } else if (change_water_status === 1) {
                        text = '正在换水'
                    } else {
                        text = '-'
                    }
                    return text
                }
            }, {
                label: '冲洗状态', width: 70, formatter(row) {
                    const rinse_status = row.rinse_status || {};
                    let text = ''
                    if (rinse_status === 0) {
                        text = '-'
                    } else if (rinse_status === 1) {
                        text = '正在冲洗'
                    } else {
                        text = '-'
                    }
                    return text
                }
            }, {
                label: '消毒状态', width: 70, formatter(row) {
                    const disinfection_status = row.disinfection_status || {};
                    let text = ''
                    if (disinfection_status === 0) {
                        text = '-'
                    } else if (disinfection_status === 1) {
                        text = '正在消毒'
                    } else {
                        text = '-'
                    }
                    return text
                }
            },{
                label: '加热状态', width: 70, formatter(row) {
                    const heating_status = row.heating_status || {};
                    let text = ''
                    if (heating_status === 0) {
                        text = '-'
                    } else if (heating_status === 1) {
                        text = '正在加热'
                    } else {
                        text = '-'
                    }
                    return text
                }
            },{
                label: '进水状态', width: 70, formatter(row) {
                    const in_water_status = row.in_water_status || {};
                    let text = ''
                    if (in_water_status === 0) {
                        text = '关进水'
                    } else if (in_water_status === 1) {
                        text = '进水'
                    } else {
                        text = '-'
                    }
                    return text
                }
            },{
                label: '出水状态', width: 150, formatter(row) {
                    const pass_one = row.pass_one || {};
                    const pass_two = row.pass_two || {};
                    const pass_third = row.pass_third || {};
                    const pass_four = row.pass_four || {};
                    let text1 = ''
                    let text2 = ''
                    let text3 = ''
                    let text4 = ''
                    if (pass_one === 0) {
                        text1 = '关'
                    } else if (pass_one === 1) {
                        text1 = '开'
                    } else {
                        text1 = '-'
                    }
                    
                    if (pass_two === 0) {
                        text2 = '关'
                    } else if (pass_two === 1) {
                        text2 = '开'
                    } else {
                        text2 = '-'
                    }
                    
                    if (pass_third === 0) {
                        text3 = '关'
                    } else if (pass_third === 1) {
                        text3 = '开'
                    } else {
                        text3 = '-'
                    }
                    
                    if (pass_four === 0) {
                        text4 = '关'
                    } else if (pass_four === 1) {
                        text4 = '开'
                    } else {
                        text4 = '-'
                    }
                    return `通道1：${text1}&nbsp; 通道2：${text2}<br />通道3：${text3}&nbsp; 通道4：${text4}`
                }
            },
            {
                label: '显示温度(上报时间)', width: 170, formatter(row) {
                    let shakeHandTime = row.shakeHandTime;
                    if (shakeHandTime) {
                        return row.show_temp + ' (' + row.shakeHandTime + ')'
                    } else {
                        return row.show_temp
                    }
                }
            },
        ]
    },
    {
        label: '换芯提醒阀值', value: 'filter_fazhi', formatter() {
            return '40'
        }
    },
    {
        label: '是否到期更换滤芯', value: 'need_reinstall', formatter() {
            return '否'
        }
    },
    { label: '备注', width: 100, value: 'remark' },
    { label: '更新时间', value: 'update_time' }
]