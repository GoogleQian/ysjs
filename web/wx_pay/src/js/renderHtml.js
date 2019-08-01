var baseURL = 'http://iotsvr.he-live.com:6518/';

function showInfoText(name, text) {
    var text = JSON.stringify(text) || '';
    $('#info').append(`<p>${name}:<span  style="color: red;">${text}</span></p>`)
}
function getGoodsList(devId) {
    showInfoText('开始请求商品列表', { devId: devId })
    $.ajax({
        url: baseURL + 'home/lists',
        method: 'get',
        data: { devId: devId },
        success: function (res) {
            showInfoText('商品列表获取成功', res)
            // alert(res)
            var arr = [];
            res.datas.map(function (item) {
                arr = arr.concat(item.amountList)
            })
            // 根据后台返回的dataList生成水产品种类列表
            $.each(arr, function (idx, item) {
                var color = null;
                if (item.passageType === 1) {
                    // 开水
                    color = '#F56C6C';
                } else if (item.passageType === 2) {
                    // 温水
                    color = '#E6A23C';
                } else if (item.passageType === 3) {
                    // 直饮水
                    color = '#67C23A';
                } else {
                    // 冰水
                    color = '#409eff';
                }
                $('.pay_box').append('\
                        <div class="item" style="color: '+ color + '; border-color: ' + color + ';" data-id=' + JSON.stringify(item) + '>\
                            <p class="title">'+ item.name + '</p>\
                            <p class="price">'+ item.price / 100 + '元</p>\
                            <p class="detail">'+ item.amount + 'ML</p>\
                        </div>')
            })
        },
        error: function (err) {
            alert('获取商品列表失败')
        }
    })
}

function getBanner(devId) {
    showInfoText('开始获取banner，参数为', devId)
    $.ajax({
        url: baseURL + '/home/advs/' + devId,
        type: 'get',
        // data: { devId: devId },
        success: function (res) {
            showInfoText('获取banner完成', res)
            console.log(res);
            var bannerList = res.datas;
            initSwiper(bannerList)
        }
    })
}
function initSwiper(list) {
    var bannerHTML = '';
    if (!list || list.length === 0) {
        // 如果没有banner，隐藏swiper
        $('.swiper-container').css('display', 'none')
        return;
    }
    list.map(function (item) {
        bannerHTML += '<a class="swiper-slide" href="' + item.linkUrl + '"><img src="' + item.imgUrl + '" style="width: 100%;height: 200px;" \></a>'
    })
    $('.swiper-wrapper').html(bannerHTML);
    var mySwiper = new Swiper('.swiper-container', {
        // direction: 'vertical', // 垂直切换选项
        loop: true, // 循环模式选项

        // 如果需要分页器
        pagination: {
            el: '.swiper-pagination',
        },

        // 如果需要前进后退按钮
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },

        // 如果需要滚动条
        scrollbar: {
            el: '.swiper-scrollbar',
        },
        // 自动播放
        autoplay: {
            delay: 5000,
            stopOnLastSlide: false,
            disableOnInteraction: false,
        }
    })
}