import Moment from "moment";

export const searchItems = [
    {
        label: '方案名',
        value: 'pro_name',
        type: 'input',
        placeholder: '方案名'
    }
]

export const tableColumns =  [
    { label: '方案名', value: 'pro_name' },
    {
        label: '创建时间', value: 'create_time', width: 200, formatter: (row) => {
            if(row.create_time){
                return Moment(row.create_time).format(
                    "YYYY-MM-DD HH:mm:ss"
                );
            }else{
                return '-'
            }
        }
    },
]