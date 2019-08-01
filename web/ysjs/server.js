// express框架开启node服务器 ES6写法
const express = require('express');
const proxy = require('http-proxy-middleware');

const app = express();
// __dirname获取到绝对路径,如果server.js文件在dist文件夹下，则要去除/dist
app.use('/static', express.static(`${__dirname}/dist/static`));
// 开启http代理实现跨域
app.use('/*', proxy({
  target: 'http://10.0.1.181:9191',
  changeOrigin: true,
  pathRewrite: {
    '^/proxy': ''
  }})
);

app.get('/*', (req, res) => {
	// 响应跳转页面
  res.sendFile(`${__dirname}/dist/index.html`);
});
// 开启服务器
let server = app.listen(6040, function () {
  let host = server.address().address;
  let port = server.address().port;
  console.log('app listening at http://localhost:%s', port);
});

// ES5写法
// const express = require('express');
// const proxy = require('http-proxy-middleware');

// const app = express();
// app.use('/static', express.static(__dirname+'/static'));
// app.use('/proxy', proxy({
//    target: 'http://test.he-live.com:9002',
//    changeOrigin: true,
//    pathRewrite: {
//      '^/proxy': ''
//    }
//  }));

// app.get('/*', function(req, res) {
//   res.sendFile(__dirname+'/index.html');
// });
// app.listen(6050);