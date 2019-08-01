export const loginForm = {
  btn: {
    text: '登录',
    loading: false
  },
  form: {
    username: '',
    password: '',
    brandname: ''
  },
  rules: {
    username: [
      { required: true, message: '请输入用户名', trigger: 'blur' }
    ],
    password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
    brandname: [{ required: true, message: '请输入商户名称', trigger: 'blur' }]
  },
  formItems: [
    // { label: '商户名称', value: 'brandname', type: 'text'},
    { label: '账号', value: 'username', type: 'text' },
    { label: '密码', value: 'password', type: 'pwd' }
  ]
}
