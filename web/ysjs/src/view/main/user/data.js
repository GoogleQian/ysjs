export const addForm = { // 生成新增表单所依赖数据
  btn: {
    text: '新增',
    loading: false
  },
  form: { // 储存数据
    brandname: '',
    username: '',
    password: ''
  },
  rules: {
    brandname: [{ required: true, message: '请填写商户名称', trigger: 'blur' }],
    username: [{ required: true, message: '请填写用户名', trigger: 'blur' }],
    password: [{ required: true, message: '请填写密码', trigger: 'blur' }]
  },
  formItems: [
    { label: '商户名', value: 'brandname', type: 'text' },
    { label: '用户名', value: 'username', type: 'text' },
    { label: '密码', value: 'password', type: 'pwd' }
  ]
}

export const searchData = {
  searchItem: [ // 条件生成查询表单所依赖数据
  ],
  searchParams: { // 条件生成查询表单所依赖数据
  }
}

export const tableItems = {
  items:
    [
      { label: '商户名', value: 'brandname' },
      { label: '用户名', value: 'username' }
    ],
  actions: ['编辑', '删除']
}
