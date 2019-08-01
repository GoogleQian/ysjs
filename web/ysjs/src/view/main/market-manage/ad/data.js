import Moment from 'moment';
export const searchItems = [
    {
        label: '名称',
        value: 'name',
        type: 'input',
        placeholder: '名称'
    }
]

export const tableColumns = [
    {
        label: '标题', value: 'name', formatter(row) {
            return `${localStorage.getItem('username')}-${row.name}`
        }
    },
    {
        label: '图片', value: 'img_url', formatter(row) {
            // return `<img src=${row.img_url || '/api' +row.img_path} style="width: 80px;height: 40px;" />`
            return `<img src="${row.img_url}" style="width: 80px;height: 40px;" />`
        }
    },
    {
        label: '链接', value: 'url'
    },
    { label: '上传时间', value: 'upload_time' }
]