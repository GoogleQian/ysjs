export class DataForm {
    constructor() {
        // this.
    }
}

export const rules = {
    hangeTime: [
        {
            validator: (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入换水时长'))
                } else if (value < 1 || value > 60) {
                    callback(new Error('换水时长在1~60之间'))
                } else {
                    callback();
                }
            }, trigger: 'change'
        }
    ],
    changeCycle: [
        {
            validator: (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入换水周期'))
                } else if (value < 1 || value > 30) {
                    callback(new Error('换水周期在1~30之间'))
                } else {
                    callback();
                }
            }, trigger: 'change'
        }
    ],
    filterLife: [
        {
            validator: (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入滤芯寿命'))
                } else if (value < 0 || value > 40) {
                    callback(new Error('滤芯寿命在0~40之间'))
                } else {
                    callback();
                }
            }, trigger: 'change'
        }
    ],
    showTemp: [
        {
            validator: (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入显示温度'))
                } else if (value < 0 || value > 100) {
                    callback(new Error('显示温度在0~100之间'))
                } else {
                    callback();
                }
            }, trigger: 'change'
        }
    ],
    actualTemp: [
        {
            validator: (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入实际温度'))
                } else if (value < 0 || value > 100) {
                    callback(new Error('实际温度在0~100之间'))
                } else {
                    callback();
                }
            }, trigger: 'change'
        }
    ],
    flushTime: [
        {
            validator: (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请设置定时冲洗时间'))
                } else {
                    callback();
                }
            }, trigger: 'change'
        }
    ],
    flushDuration: [
        {
            validator: (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请设置定时冲洗时长'))
                } else if (value < 0 || value > 100) {
                    callback(new Error('定时冲洗时长在30~300s之间'))
                } else {
                    callback();
                }
            }, trigger: 'change'
        }
    ],
    cleanDuration: [
        {
            validator: (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请设置定时消毒时长'))
                } else if (value < 0 || value > 100) {
                    callback(new Error('定时消毒时长在30~300s之间'))
                } else {
                    callback();
                }
            }, trigger: 'change'
        }
    ]
}