import Moment from "moment";
export const addForm = { // 生成新增表单所依赖数据
    btn: {
        text: '新增',
        loading: false
    },
    form: { // 储存数据
        id: null,
        sell_amount: null,
        sell_name: null,
        sell_money: null,
        water_temp: null,
        sell_type: null,
        program_type: null,
    },
    rules: {
        // id: [{ required: true, message: '请输入ID', trigger: 'blur' }]
    },
    formItems: [
        { label: 'id', value: 'id', type: 'text' },
        { label: '产品名', value: 'sell_name', type: 'text' },
        { label: '温度', value: 'water_temp', type: 'text' },
        { label: '水量', value: 'sell_amount', type: 'text' },
        { label: '金额', value: 'sell_money', type: 'map' },
        {
            label: '售水类型', value: 'sell_type', type: 'text'
        },
        {
            label: '方案类型', value: 'program_type', type: 'text'
        },
    ]
}

export const searchData = {
    searchItem: [ // 条件生成查询表单所依赖数据
        {
            label: '售水类型',
            value: 'type',
            type: 'text',
            placeholder: '售水类型'
        }
    ],
    searchParams: { // 条件生成查询表单所依赖数据
        id: ''
    }
}

export const tableItems = {
    items:
        [
            // { label: 'id', value: 'id' },
            { label: '售水类型', value: 'type' },
        ],
    actions: ['编辑', '删除']
}
