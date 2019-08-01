const express = require('express');
const proxy = require('http-proxy-middleware');

const app = express();
// __dirname获取到绝对路径,如果server.js文件在dist文件夹下，则要去除/dist
app.use('/static', express.static(`${__dirname}/static`));
// 开启http代理实现跨域
app.use('/api', proxy({
    target: 'http://10.0.75.1:9191',
    changeOrigin: true,
    pathRewrite: {
        '^/api': ''
    }
})
);

app.get('/*', (req, res) => {
    // 响应跳转页面
    res.sendFile(`${__dirname}/index.html`);
});
// 开启服务器

let server = app.listen(8192, function () {
    let host = server.address().address;
    let port = server.address().port;
    console.log('app listening at http://localhost:%s', port);
});
