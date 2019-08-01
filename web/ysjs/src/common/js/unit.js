import Moment from 'moment'
const time = new Date();
// m,n之间的随机数
export function randomNum(m, n) {
    return Math.ceil(Math.random() * 10000) % (n - m + 1) + m;
}

// 生成X轴
export function renderX(type) {
    if (type === 'week') {
        return [
            Moment(time).format("M-DD"),
            Moment(time - 1 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 2 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 3 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 4 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 5 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 6 * 24 * 60 * 60 * 1000).format("M-DD")]
    } else if (type === 'month') {
        return [
            Moment(time).format("M-DD"),
            Moment(time - 1 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 2 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 3 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 4 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 5 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 6 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 7 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 8 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 9 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 10 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 11 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 12 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 13 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 14 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 15 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 16 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 17 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 18 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 19 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 20 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 21 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 22 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 23 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 24 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 25 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 26 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 27 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 28 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 29 * 24 * 60 * 60 * 1000).format("M-DD"),
            Moment(time - 30 * 24 * 60 * 60 * 1000).format("M-DD")
        ]
    } else if (type === 'year') {
        return ['2018-09', '2018-08', '2018-07', '2018-06', '2018-05', '2018-04', '2018-03', '2018-02', '2018-01', '2017-12', '2017-11', '2017-10']
    } else if (type === 'area') {
        return ["北京", "深圳", "上海", "重庆", "西安", "广州"].reverse()
    }
}