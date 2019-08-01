export const tableColumns = [
    { label: '商户名', value: 'mchName' },
    { label: '账户名', value: 'username' },
    { label: '手机号', value: 'mobile' },
    { label: '设备数', value: 'devNum' },
    { label: '创建时间', value: 'createTime' },
    { label: '合作开始日期', value: 'cooperationTime' },
    { label: '备注', value: 'remark' },
    { label: '公众账号ID', value: 'remark', formatter(row){
        const wxConfig = row.wxConfig || {};
        return wxConfig.appId
    } },
    { label: '微信商户ID', value: 'remark', formatter(row){
        const wxConfig = row.wxConfig || {};
        return wxConfig.mchId
    } },
]