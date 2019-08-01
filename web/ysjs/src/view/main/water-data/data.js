import { renderX, randomNum } from '@/common/js/unit'
export const averageData = [
    { name: "用水量", id: 0, value: 40, unit: "L", color: "#2d8cf0" },
    {
        name: "单次用水时长",
        id: 1,
        value: 0.5,
        unit: "H",
        color: "#19be6b"
    },
    { name: "用水频次", id: 2, value: 95, unit: "次", color: "#3a71a8" }
]

export const dateTypeOptions = [
    { label: "最近一周", value: "week" },
    { label: "最近一月", value: "month" },
    { label: "最近一年", value: "year" },
    { label: "地区", value: "area" }
]

// 用水总量
export const totalOptions = {
    tooltip: {
        trigger: "axis",
        axisPointer: {
            type: "line"
        }
    },
    grid: {
        left: "50",
        right: "4%",
        top: "3%",
        bottom: "10%",
        containerLabel: true
    },
    xAxis: {
        type: "category",
        data: renderX("week").reverse()
    },
    yAxis: {
        type: "value"
    },
    series: [
        {
            name: "用水量",
            data: [10, 12, 8, 9, 8, 10, 15],
            type: "line",
            smooth: true
        }
    ],
    color: "#4ecb73"
}

// 单次用水时长
export const timeOptions = {
    color: ["#a04"],
    tooltip: {
        trigger: "axis",
        axisPointer: {
            type: "shadow"
        }
    },
    grid: {
        left: "50",
        right: "4%",
        top: "3%",
        bottom: "10%",
        containerLabel: true
    },
    xAxis: [
        {
            type: "category",
            data: ["小于1小时", "1-2小时", "2-3小时", "3-4小时", "4小时以上"],
            axisTick: {
                alignWithLabel: true
            }
        }
    ],
    yAxis: [
        {
            type: "value"
        }
    ],
    series: [
        {
            name: "使用时长",
            type: "bar",
            data: [651, 308, 193, 51, 12]
        }
    ],
    color: "#36cbcb"
}


// 用水频率
export const frequencyOptions = {
    tooltip: {
        trigger: "axis",
        axisPointer: {
            type: "line"
        }
    },
    grid: {
        left: "50",
        right: "4%",
        top: "3%",
        bottom: "10%",
        containerLabel: true
    },
    xAxis: {
        type: "category",
        data: renderX("week").reverse()
    },
    yAxis: {
        type: "value"
    },
    series: [
        {
            name: "次数",
            data: [15, 12, 20, 9, 18, 10, 15],
            type: "line",
            smooth: true
        }
    ],
    color: "#3aa1ff"
}

// 水数据分析
export const waterOptions = {
    // color: ['#F56C6C', '#67C23A'],
    tooltip: {
        trigger: 'axis',
    },
    legend: {
        left: 'left',
        data: ['入水TDS', '出水TDS', '入水色度', '出水色度', '入水温度', '出水温度', '入水浊度', '出水浊度']
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
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: renderX("week").reverse(),
    },
    grid: {
        left: '50',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    yAxis: {
        type: 'value',
        name: '水质指标',
        scale: true,
        minInterval: 1,
        axisTick: {
            interval: 1
        },
        splitArea: {
            show: true
        },
        axisLabel: {
            interval: function (index, value) {
                console.log(index, value)
            }
        }
    },
    series: [
        {
            name: '入水TDS',
            type: 'line',
            data: [randomNum(80, 120), randomNum(80, 120), randomNum(80, 120), randomNum(80, 120), randomNum(80, 120), randomNum(80, 120), randomNum(80, 120)],
            smooth: true
        },
        {
            name: '出水TDS',
            type: 'line',
            data: [randomNum(1, 5), randomNum(1, 5), randomNum(1, 5), randomNum(1, 5), randomNum(1, 5), randomNum(1, 5), randomNum(1, 5)],
            smooth: true
        },
        {
            name: '入水浊度',
            type: 'line',
            data: [randomNum(40, 80), randomNum(40, 80), randomNum(40, 80), randomNum(40, 80), randomNum(40, 80), randomNum(40, 80), randomNum(40, 80)],
            smooth: true
        },
        {
            name: '出水浊度',
            type: 'line',
            data: [randomNum(1, 10), randomNum(1, 10), randomNum(1, 10), randomNum(1, 10), randomNum(1, 10), randomNum(1, 10), randomNum(1, 10)],
            smooth: true
        },
        {
            name: '入水温度',
            type: 'line',
            data: [randomNum(20, 30), randomNum(20, 30), randomNum(20, 30), randomNum(20, 30), randomNum(20, 30), randomNum(20, 30), randomNum(20, 30)],
            smooth: true
        },
        {
            name: '出水温度',
            type: 'line',
            data: [randomNum(20, 30), randomNum(20, 30), randomNum(20, 30), randomNum(20, 30), randomNum(20, 30), randomNum(20, 30), randomNum(20, 30)],
            smooth: true
        },
        {
            name: '入水色度',
            type: 'line',
            data: [randomNum(20, 30), randomNum(20, 30), randomNum(20, 30), randomNum(20, 30), randomNum(20, 30), randomNum(20, 30), randomNum(20, 30)],
            smooth: true
        },
        {
            name: '出水色度',
            type: 'line',
            data: [randomNum(5, 12), randomNum(5, 12), randomNum(5, 12), randomNum(5, 12), randomNum(5, 12), randomNum(5, 12), randomNum(5, 12)],
            smooth: true
        },
    ]
}

// 设备数量统计
export const devNumOptions = {
    color: ['#F56C6C', '#67C23A'],
    tooltip: {
        trigger: 'axis',
    },
    legend: {
        left: 'left',
        data: ['在线设备', '离线设备']
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
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: renderX("week").reverse()
    },
    grid: {
        left: '50',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    yAxis: {
        type: 'value',
        name: '设备数量'
    },
    series: [
        {
            name: '离线设备',
            type: 'line',
            data: [randomNum(1, 100), randomNum(1, 100), randomNum(1, 100), randomNum(1, 100), randomNum(1, 100), randomNum(1, 100), randomNum(1, 100)],
            smooth: true
        },
        {
            name: '在线设备',
            type: 'line',
            data: [randomNum(800, 1000), randomNum(800, 1000), randomNum(800, 1000), randomNum(800, 1000), randomNum(800, 1000), randomNum(800, 1000), randomNum(800, 1000)],
            smooth: true
        }
    ]
}