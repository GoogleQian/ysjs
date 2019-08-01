import { renderX } from '@/common/js/unit'
export const devOverview = [
    {
        id: 1,
        name: "总设备数",
        value: 12003,
        icon: "el-icon-success",
        color: "#2d8cf0"
    },
    {
        id: 2,
        name: "正常运行设备",
        value: 12000,
        icon: "el-icon-success",
        color: "#19be6b"
    },
    {
        id: 3,
        name: "异常设备",
        value: 3,
        icon: "el-icon-success",
        color: "#ed3f14"
    }
]

export const errorOptions = {
    grid: {
        left: "3%",
        right: "4%",
        top: "3%",
        bottom: "10%",
        containerLabel: true
    },
    tooltip: {
        trigger: "item",
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient: "vertical",
        left: "right",
        data: ["出水量小", "TDS值高", "净水机漏水", "水压过大"]
    },
    series: [
        {
            name: "设备使用情况",
            type: "pie",
            radius: "80%",
            center: ["40%", "50%"],
            data: [
                { value: 200, name: "出水量小" },
                { value: 326, name: "TDS值高" },
                { value: 485, name: "净水机漏水" },
                { value: 301, name: "水压过大" }
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: "rgba(0, 0, 0, 0.5)"
                }
            }
        }
    ]
}

export const devOptions = {
    grid: {
        left: "3%",
        right: "4%",
        top: "3%",
        bottom: "10%",
        containerLabel: true
    },
    tooltip: {
        trigger: "item",
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient: "vertical",
        left: "right",
        data: ["正常运行设备", "异常设备"]
    },
    series: [
        {
            name: "设备使用情况",
            type: "pie",
            radius: "80%",
            center: ["30%", "50%"],
            data: [
                { value: 12000, name: "正常运行设备" },
                { value: 3, name: "异常设备" }
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: "rgba(0, 0, 0, 0.5)"
                }
            }
        }
    ],
    color: ["#91c7ae", "#d48265"]
}

export const tableData = [
    {
        deviceId: "X-01-1232451236",
        filterType: "PP棉滤芯",
        alertInfo: "滤芯老化",
        alertTime: "2018-08-22",
        changeTime: "2018-08-29",
        contactPerson: "王二仁",
        contactNumber: "13211111111"
    },
    {
        deviceId: "X-01-1232451235",
        filterType: "PP棉滤芯",
        alertInfo: "滤芯老化",
        alertTime: "2018-08-21",
        changeTime: "2018-08-28",
        contactPerson: "张大花",
        contactNumber: "17611167892"
    },
    {
        deviceId: "X-01-1232451232",
        filterType: "PP棉滤芯",
        alertInfo: "滤芯老化",
        alertTime: "2018-08-20",
        changeTime: "2018-08-27",
        contactPerson: "李三",
        contactNumber: "15632659896"
    }
]

export const abnormalOptions = {
    // color: ['#003366', '#006699', '#4cabce', '#e5323e'],
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    legend: {
        data: ['出水量小', 'TDS值高', '净水机漏水', '水压过大']
    },
    toolbox: {
        show: true,
        orient: 'vertical',
        left: 'right',
        top: 'center',
        feature: {
            mark: { show: true },
            dataView: { show: true, readOnly: false },
            magicType: { show: true, type: ['line', 'bar', 'stack', 'tiled'] },
            restore: { show: true },
            saveAsImage: { show: true }
        }
    },
    calculable: true,
    xAxis: [
        {
            type: 'category',
            axisTick: { show: false },
            data: renderX("week").reverse()
        }
    ],
    yAxis: [
        {
            type: 'value'
        }
    ],
    series: [
        {
            name: '出水量小',
            type: 'bar',
            barGap: 0,
            // label: labelOption,
            data: [320, 332, 301, 334, 390, 354, 356]
        },
        {
            name: 'TDS值高',
            type: 'bar',
            // label: labelOption,
            data: [220, 182, 191, 234, 290, 276, 135]
        },
        {
            name: '净水机漏水',
            type: 'bar',
            // label: labelOption,
            data: [150, 232, 201, 154, 190, 261, 123]
        },
        {
            name: '水压过大',
            type: 'bar',
            // label: labelOption,
            data: [98, 77, 101, 99, 40, 101, 32]
        }
    ]
};

export const dataTypeOptions = [
    { label: "最近一周", value: "week" },
    { label: "最近一月", value: "month" },
    { label: "最近一年", value: "year" },
    { label: "地区", value: "area" }
]