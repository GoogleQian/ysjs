// 搜索框项目
export const searchItems = [
      {
            label: "用户名",
            value: "userName",
            type: "input",
            placeholder: "用户名",
      },
      {
            label: "操作信息",
            value: "operateInfo",
            type: "input",
            placeholder: "操作信息",
      },
      {
            label: "请求url",
            value: "reqUrl",
            type: "input",
            placeholder: "请求url",
      },
      {
            label: "请求方式",
            value: "reqType",
            type: "input",
            placeholder: "请求方式",
      },
      {
            label: "请求参数",
            value: "reqParam",
            type: "input",
            placeholder: "请求参数",
      },
      {
            label: "状态",
            value: "status",
            type: "select",
            options:[{label:'成功', value: 1}, {label: '失败', value: 0}],
            placeholder: "请选择",
      },
      {
            label: "操作IP",
            value: "ip",
            type: "input",
            placeholder: "操作IP",
      },
      {
            label: "用户代理",
            value: "userAgent",
            type: "input",
            placeholder: "用户代理",
      },
      {
            label: '开始时间',
            value: 'startTime',
            type: 'datetime'
      },
      {
            label: '结束时间',
            value: 'endTime',
            type: 'datetime'
      },
]

// 功能按钮
export const funcs = ['新增', '修改', '删除'];

// 表格项目
export const tableColumns = [
      { label: "用户名", value: "userName" },
      { label: "操作信息", value: "operateInfo" },
      { label: "请求url", value: "reqUrl" },
      { label: "请求方式", value: "reqType" },
      { label: "请求参数", value: "reqParam" },
      { label: "请求时间(ms)", value: "reqTime" },
      {
            label: "状态", value: "status", formatter(row) {
                  let text = '-';
                  if (row.status === 1) {
                        text = '成功'
                  } else {
                        text = '失败'
                  }
                  return text
            }
      },
      { label: "操作IP", value: "ip" },
      { label: "用户代理", value: "userAgent" },
      { label: "创建时间", value: "createTime" },
]

