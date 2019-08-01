// 搜索框项目
export const searchItems = [
    {
        label: "设备编码",
        value: "devCode",
        type: "input",
        placeholder: "设备编码",
    }, {
        label: "上报时间",
        value: "reportTime",
        type: "datetimerange",
        placeholder: "上报时间",
        dateFormat: 'yyyy-MM-dd HH:mm:ss'
    }
    // {
    //     label: "联系电话",
    //     value: "phoneNum",
    //     type: "input",
    //     placeholder: "联系电话",
    // },
    // {
    //     label: "处理状态",
    //     value: "status",
    //     type: "select",
    //     options: [{label: '未处理', value: 0}, {label: '已处理', value: 1}],
    // },
]

// 功能按钮
export const funcs = ['修改', '删除'];

// 表格项目
export const tableColumns = [
    { label: "设备编码", value: "devCode" },
    { label: "上报人", value: "reportName" },
    { label: "联系电话", value: "phoneNum" },
    { label: "具体内容", value: "msg" },
    {
        label: "处理状态", value: "status", formatter(row) {
            if (row.status === 0) {
                return '未处理'
            } else if (row.status === 1) {
                return '已处理'
            } else {
                return '-'
            }
        }
    },
    { label: "上报时间", value: "reportTime" },
    { label: "修改时间", value: "updateTime" },
    { label: "备注", value: "remark" },
]

