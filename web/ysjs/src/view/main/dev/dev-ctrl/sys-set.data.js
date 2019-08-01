export class DataForm {
    constructor() {
        this.id = null;
        this.state = null; // 0步进， 1节能
        this.stopTemp = null; // 停止加热温度   （BJ）
        this.flushType = null; // 自动清洗 0：OFF 1：ON （BJ）
        this.filterType = null; // 滤芯管理功能 0：OFF 1：ON （BJ）
        this.logoShows = null; // logo展示 0：OFF 1：ON （BJ）
        this.inTemp = null; // 进水温差  （BJ）
        this.delicacy = null; // 水位检测灵敏度（BJ）

        this.emptyType = null; // 排空功能（BJ）
        this.keepTemp = null; // 保温温差（BJ）
        this.keepHeight = null; // 保温回差（BJ）
        this.speed = null // 进水速度（BJ）
        this.addTemp = null // 补水温差（BJ）
        this.addAmount = null // 补水量（BJ）
        this.showTemp = null // 显示温度（BJ）

        this.outType = null;    // 出水方式 0：OFF 1：ON
        this.changeType = null; // 换水功能 0：OFF 1：ON
        this.inHeight = null;   // 进水回差
        this.changeMethod = null;   // 换水方式
        this.checkWater = null; // 水位监测 0：OFF 1：ON
    }
}
const baseRules = {
    stopTemp: [
        {
            validator: (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入停止加热温度'))
                } else if (value < 20 || value > 99) {
                    callback(new Error('停止加热温度在20~99之间'))
                } else {
                    callback();
                }
            }, trigger: 'change'
        }
    ],
    inTemp: [
        {
            validator: (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入进水温差'))
                } else if (value < 1 || value > 9) {
                    callback(new Error('进水温差在1~9之间'))
                } else {
                    callback();
                }
            }, trigger: 'change'
        }
    ],
    delicacy: [
        {
            validator: (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入水位检测灵敏度'))
                } else if (value < 1 || value > 25) {
                    callback(new Error('水位检测灵敏度在1~25之间'))
                } else {
                    callback();
                }
            }, trigger: 'change'
        }
    ]

}

export const stepRules = {
    ...baseRules,
    keepTemp: [
        {
            validator: (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入保温温差'))
                } else if (value < 0 || value > 9) {
                    callback(new Error('保温温差在0~9之间'))
                } else {
                    callback();
                }
            }, trigger: 'change'
        }
    ],
    keepHeight: [
        {
            validator: (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入保温回差'))
                } else if (value < 2 || value > 9) {
                    callback(new Error('保温回差在2~9之间'))
                } else {
                    callback();
                }
            }, trigger: 'change'
        }
    ],
    speed: [
        {
            validator: (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入进水速度'))
                } else if (value < 0 || value > 80) {
                    callback(new Error('进水速度在0~80之间'))
                } else {
                    callback();
                }
            }, trigger: 'change'
        }
    ]
}

export const energyRules = {
    ...baseRules,
    inHeight: [
        {
            validator: (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入进水回差'))
                } else if (value < 1 || value > 9) {
                    callback(new Error('进水回差在1~9之间'))
                } else {
                    callback();
                }
            }, trigger: 'change'
        }
    ]
}