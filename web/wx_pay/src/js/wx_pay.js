var config = {
    pageIndex: 'http://iotsvr.he-live.com/wx_pay/index.html',
    pageSuccess: './success.html',
    api: {
        '_BASEAPI': 'http://iotsvr.he-live.com:6518/',
        '_POSTCODE': 'home/getAppId',  // 提交code
        '_POSTGOODSTYPE': 'home/unifiedOrder1', // 提交所选商品
        '_GETDEVSTATUS': 'home/devOnline',      // 获取设备在线状态
        '_GETCOUPONS': 'home/coupons',           // 获取优惠券信息
    },
    appid: 'wx5d146b5184c317e9'
}

var price = 0;  // 原价
var couponPrice = 0; // 优惠券金额
var cost = 0;   // 实际支付
var couponUsageId = null;   // 优惠券id

// actionsheet操作
var $iosActionsheet = $('#iosActionsheet');
var $iosMask = $('#iosMask');
$iosMask.on('click', hideActionSheet);
$('#iosActionsheetCancel').on('click', hideActionSheet);
$(function () {
    // 判断是否有携带code参数
    var urlParams = location.search.substr(1);
    var urlHash = location.hash.substr(1);
    if (urlParams) {  // 微信重定向：有参数(code,state)，将参数提交后台，后台返回openId，将openId存入localstoreage
        var data = decodeParams(urlParams);    // 根据url组装code
        showInfo('code&state', data)
        showInfo('mchInfo', JSON.parse(localStorage.getItem('mchInfo')))
        data = $.extend(JSON.parse(localStorage.getItem('mchInfo')), data)  // 将商户信息写入data
        postCode(data); // 将code提交到后台，后台保存至session
    } else {  // 无参数有hash，保存DevId到localstorage，然后跳转微信链接
        showInfo('devId: ', urlHash)
        // 将存在hash参数内的deviceId传给后台，然后跳转微信链接
        localStorage.setItem('devId', urlHash)
        postDeviceId({ devId: urlHash || localStorage.getItem('devId') })
        getBanner(localStorage.getItem('devId'));
        getGoodsList(localStorage.getItem('devId'));
    }
})

// 提交产品类型
$('.pay_box').on('click', '.item', function () {
    var that = this;
    price = JSON.parse($(that).attr('data-id')).price;  // 原价
    $('#price').text('¥' + price / 100);    // 写入原价
    // 弹出提示窗口
    showActionSheet();
    // 点击商品时查询优惠券(传入deviceId和原价)
    getCoupons({ deviceId: localStorage.getItem('devId'), price: price })
    $('#iosActionsheetOk').on('click', function () {
        post_goods_type($(that).attr('data-id'), couponUsageId, couponPrice);
    });
})


// 点击商品时查询优惠券
function getCoupons(data) {
    showInfo('开始获取优惠券:', data)
    $.ajax({
        url: config.api._BASEAPI + config.api._GETCOUPONS,
        method: 'GET',
        data,
        success: function (res) {
            showInfo('获取优惠券完成:', res)
            if (res.ret === 0) {
                couponPrice = res.datas.price || 0; // 优惠券
                $('#coupon').text('- ¥' + couponPrice / 100);  // 优惠券金额
                $('#cost').text('¥' + (price - couponPrice) / 100); // 实付金额
                couponUsageId = res.datas.coupon_usage_id;
            } else {
                couponPrice = 0;    // 优惠券金额
                $('#coupon').text('- ¥0');  // 优惠券文本
                $('#cost').text('¥' + (price - couponPrice) / 100); // 实付金额
            }
        }
    })
}

// 提交devId
function postDeviceId(data) {
    showInfo('开始提交devId,参数为：', data)
    $.ajax({
        url: config.api._BASEAPI + config.api._GETDEVSTATUS,
        method: 'POST',
        data: data,
        success: function (res) {
            showInfo('提交devId结束，返回状态码：', res.ret)
            if (res.ret == 0) {
                // 使用后台根据devId返回的appid
                showInfo('获取到appId', res)
                // showInfo('appId', res.datas.appId)
                showInfo('微信跳转链接', 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=' + res.datas.appId + '&redirect_uri=' + config.pageIndex + '&response_type=code&scope=snsapi_userinfo&state=STATE&connect_redirect=1#wechat_redirect')
                localStorage.setItem('mchInfo', JSON.stringify(res.datas));
                location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=' + res.datas.appId + '&redirect_uri=' + config.pageIndex + '&response_type=code&scope=snsapi_userinfo&state=STATE&connect_redirect=1#wechat_redirect';
            } else {
                alert('设备暂不可用：' + res.msg)
                // localStorage.removeItem('mchInfo');
                $('#mask1').show().on('click', function () {
                    alert('设备暂不可用：' + res.msg)
                });
            }
        },
        error: function (err) {
            // localStorage.removeItem('mchInfo');
            showInfo('获取设备状态接口调用出错：', err)
        }
    })
}

// 提交code
function postCode(data) {
    showInfo('开始提交code，参数为', data)
    $.ajax({
        url: config.api._BASEAPI + config.api._POSTCODE,
        method: 'POST',
        data: data,
        success: function (res) {
            showInfo('提交code结束，返回值：', res);
            localStorage.setItem('openId', res.datas.openId)
            getBanner(localStorage.getItem('devId'));
            getGoodsList(localStorage.getItem('devId'));
        },
        error: function (err) {
            showInfo('提交code接口调用出错：', err)
        }
    })
}

// 提交产品类型
function post_goods_type(data, couponUsageId, couponPrice) {
    // 提交产品类型首先弹窗显示价格信息
    $('#loadingToast').css('display', 'block');
    data = JSON.parse(data);
    data.devId = localStorage.getItem('devId');
    data.openId = localStorage.getItem('openId');
    delete data.id
    data.sell_money = data.sell_money * 100;
    data.couponPrice = couponPrice;
    data.couponUsageId = couponUsageId;
    data.mchInfo = JSON.parse(localStorage.getItem('mchInfo'));
    setTimeout(function () {
        showInfo('下订单参数：', data)
        $.ajax({
            url: config.api._BASEAPI + config.api._POSTGOODSTYPE,
            method: 'POST',
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function (res) {
                // $('#priceConfirmDialog').css('display', 'none');
                hideActionSheet();
                $('#loadingToast').css('display', 'none');
                // 获取到参数可以开始支付
                if (res.ret === 0) {
                    showInfo('下订单接口返回数据', res)
                    if (res.datas && res.datas.totalFee === 0) {
                        // TODO
                        $('#success').css('display', 'block');
                        $('#success').css('display', 'none');
                        location.href = config.pageSuccess + '#' + localStorage.getItem('devId');
                    } else {
                        if (typeof WeixinJSBridge == "undefined") {
                            if (document.addEventListener) {
                                document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
                            } else if (document.attachEvent) {
                                document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                                document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
                            }
                        } else {
                            onBridgeReady(res.datas);
                        }
                    }
                } else {
                    // error
                    alert(res.msg);
                    $('#loadingToast').css('display', 'none');
                    postDeviceId({ devId: localStorage.getItem('devId') })
                }
            },
            error: function (error) {
                $('#loadingToast').css('display', 'none');
                alert('向后台提交用户所选商品失败')
            }
        })
    }, 1000);
}

// H5调支付接口
function onBridgeReady(data) {
    WeixinJSBridge.invoke(
        'getBrandWCPayRequest', {
            "appId": data.appId,     //公众号名称，由商户传入     
            "timeStamp": data.timeStamp,         //时间戳，自1970年以来的秒数     
            "nonceStr": data.nonceStr, //随机串     
            "package": data.package,
            "signType": "MD5",         //微信签名方式：     
            "paySign": data.paySign //微信签名
        },
        function (res) {
            if (res.err_msg == "get_brand_wcpay_request:ok") {
                // 使用以上方式判断前端返回,微信团队郑重提示：
                //res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
                // 支付成功
                $('#success').css('display', 'block');
                setTimeout(function () {
                    $('#success').css('display', 'none');
                    location.href = config.pageSuccess + '#' + localStorage.getItem('devId');
                }, 1000);
            } else if (res.err_msg == "get_brand_wcpay_request:cancel") {
                $('#fail').css('display', 'block');
                setTimeout(function () {
                    $('#fail').css('display', 'none');
                }, 1000);
            } else if (res.err_msg == "调用支付JSAPI缺少参数：total_fee" || res.err_msg == "get_brand_wcpay_request:fail") {
                $('#error').css('display', 'block');
                setTimeout(function () {
                    $('#error').css('display', 'none');
                }, 1000);
            }
        });
}

// 解析urlParams获取code
function decodeParams(urlParams) {
    var data = {};
    var arr = urlParams.split('&');
    for (var i = 0; i < arr.length; i++) {
        var nArr = arr[i].split('=');
        data[nArr[0]] = nArr[1];
    }
    return data;
}
// 解析hash参数
function decodeHash(urlHash) {
    var data = {};
    var arr = urlHash.split('=');
    data[arr[0]] = arr[1]
    return arr[1];
}

function showInfo(name, text) {
    var text = JSON.stringify(text) || '';
    $('#info').append(`<p>${name}:<span  style="color: red;">${text}</span></p>`)
}

function showActionSheet() {
    $iosActionsheet.addClass('weui-actionsheet_toggle');
    $iosMask.fadeIn(200);
}
function hideActionSheet() {
    $iosActionsheet.removeClass('weui-actionsheet_toggle');
    $iosMask.fadeOut(200);
}