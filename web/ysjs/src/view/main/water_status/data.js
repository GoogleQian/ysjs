// 搜索条件
export const searchItems = [
    {
        label: 'IMEI',
        value: 'imei',
        type: 'input',
        placeholder: '设备编号'
    }, {
        label: '开始时间',
        value: 'start_time',
        type: 'datetime'
    }, {
        label: '结束时间',
        value: 'end_time',
        type: 'datetime'
    }
]

// 功能按钮
export const funcs = [];

// 表格项
export const tableColumns = [
    { label: 'IMEI', value: 'imei' },
    { label: '水量状态（ML）', value: 'amount_status' },
    {
        label: '换水状态', value: 'change_water_status', formatter(row) {
            switch (row.change_water_status) {
                case 0:
                    return '未换水';
                    break;
                case 1:
                    return '正在换水';
                    break;
                default:
                    return '-'
            }
        }
    },
    {
        label: '冲洗状态', value: 'rinse_status', formatter(row) {
            switch (row.rinse_status) {
                case 0:
                    return '未冲洗';
                    break;
                case 1:
                    return '正在冲洗';
                    break;
                default:
                    return '-'
            }
        }
    },
    {
        label: '消毒状态', value: 'disinfection_status', formatter(row) {
            switch (row.disinfection_status) {
                case 0:
                    return '未消毒';
                    break;
                case 1:
                    return '正在消毒';
                    break;
                default:
                    return '-'
            }
        }
    },
    {
        label: '童锁状态', value: 'child_lock_status', formatter(row) {
            switch (row.child_lock_status) {
                case 0:
                    return '关';
                    break;
                case 1:
                    return '开';
                    break;
                default:
                    return '-'
            }
        }
    },
    { label: '水温', value: 'temp' },
    {
        label: '水位', value: 'water_level_status', formatter(row) {
            switch (row.water_level_status) {
                case 0:
                    return '0';
                    break;
                case 1:
                    return '低水位';
                    break;
                case 2:
                    return '中水位';
                    break;
                case 3:
                    return '高水位';
                    break;
                default:
                    return '-'
            }
        }
    },
    {
        label: '进水状态', value: 'in_water_status', formatter(row) {
            switch (row.in_water_status) {
                case 0:
                    return '进水';
                    break;
                case 1:
                    return '进水关';
                    break;
                default:
                    return '-'
            }
        }
    },
    {
        label: '出水1', value: 'pass_one', formatter(row) {
            switch (row.pass_one) {
                case 0:
                    return '关';
                    break;
                case 1:
                    return '开';
                    break;
                default:
                    return '-'
            }
        }
    },
    {
        label: '出水2', value: 'pass_two', formatter(row) {
            switch (row.pass_two) {
                case 0:
                    return '关';
                    break;
                case 1:
                    return '开';
                    break;
                default:
                    return '-'
            }
        }
    },
    {
        label: '出水3', value: 'pass_third', formatter(row) {
            switch (row.pass_third) {
                case 0:
                    return '关';
                    break;
                case 1:
                    return '开';
                    break;
                default:
                    return '-'
            }
        }
    },
    {
        label: '出水4', value: 'pass_four', formatter(row) {
            switch (row.pass_four) {
                case 0:
                    return '关';
                    break;
                case 1:
                    return '开';
                    break;
                default:
                    return '-'
            }
        }
    },
    {
        label: '加热状态', value: 'heating_status', formatter(row) {
            switch (row.heating_status) {
                case 0:
                    return '停止加热';
                    break;
                case 1:
                    return '加热';
                    break;
                default:
                    return '-'
            }
        }
    },
    {
        label: '制冷状态', value: 'refrigeration_status', formatter(row) {
            switch (row.refrigeration_status) {
                case 0:
                    return '停止制冷';
                    break;
                case 1:
                    return '制冷';
                    break;
                default:
                    return '-'
            }
        }
    },
    {
        label: '入水TDS', value: 'in_tds'
    },
    {
        label: '出水TDS', value: 'out_tds'
    },
    {
        label: '故障代码', value: 'erro_code', formatter(row) {
            switch (row.erro_code) {
                case 224:
                    return '滤芯防伪';
                    break;
                case 225:
                    return '超温故障';
                    break;
                case 226:
                    return '顶部故障';
                    break;
                case 227:
                    return '底部故障';
                    break;
                case 229:
                    return '进水故障';
                    break;
                case 230:
                    return '溢出故障';
                    break;
                case 231:
                    return '结构故障';
                    break;
                default:
                    return '-'
            }
        }
    },
    {
        label: '信号强度', value: 'model_strength', formatter(row) {
            switch (row.model_strength) {
                case 1:
                    return '低信号强度';
                    break;
                case 2:
                    return '中信号强度';
                    break;
                case 3:
                    return '高信号强度';
                    break;
                default:
                    return '-'
            }
        }
    },
    {
        label: '时控', value: 'time_control', formatter(row) {
            switch (row.time_control) {
                case 0:
                    return '已设置';
                    break;
                case 1:
                    return '已取消';
                    break;
                default:
                    return '-'
            }
        }
    },
    { label: '上报时间', value: 'create_time' }
]
