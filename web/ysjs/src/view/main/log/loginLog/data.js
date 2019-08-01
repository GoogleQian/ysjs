// 搜索框项目
export const searchItems = [
      {
            label: "用户名",
            value: "userName",
            type: "input",
            placeholder: "用户名",
      },
      {
            label: "操作类型",
            value: "operateType",
            type: "input",
            placeholder: "操作类型",
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
      {
            label: "操作类型", value: "operateType", formatter(row) {
                  let text = '-';
                  if (row.operateType === 1) {
                        text = '登录'
                  }
                  return text
            }
      },
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

