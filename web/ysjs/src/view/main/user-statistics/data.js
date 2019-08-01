export const devOptions = {
    tooltip: {
        show:true
    },
    xAxis: {
        type: "category",
        data: [
        ],
        axisLabel: {
            rotate: 30
        }
    },
    yAxis: {
        type: "value"
    },
    series: [
        {
            data: [],
            type: "bar",
            barWidth: "50px"
        }
    ]
}

export const typeOptions = {
    title: {
        show: true,
        text: '水量类型'
    },
    // 大小中分布
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
        data: ["大杯", "中杯", "小杯"]
    },
    series: [
        {
            name: "用户用水类型",
            type: "pie",
            radius: "80%",
            // center: ["30%", "'50px'%"],
            data: [
                { value: 0, name: "大杯" },
                { value: 0, name: "中杯" },
                { value: 0, name: "小杯" }
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
    color: ["#91c7ae", "#d48265", "#E6A23C"]
}

export const dataOverview = {
}