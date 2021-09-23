var Share = {
    hasConfig: false,
    host: 'http://' + window.location.host,
    title: '',
    imgUrl: '',
    desc: '微信投保，三步到位\r\n关注公众号“货运保平台”',
    init: function () {

    },
    config: function (accountId, userId) {
        this.link = location.href;
        this.title = '货运保' + ' - ' + document.title;
        this.imgUrl = this.host + '/images/logo_share.png';
        var url = encodeURI(encodeURI(location.href));
        $.post("/baoxian/user/getShareConfig.jhtml", {
            url: url
        }, function (result) {
            if (result.success == true) {
                ctimestamp = result.timeStamp;
                cnonceStr = result.nonceStr;
                csignature = result.signature;
                cappId = result.appId;
                wx.config({
//                    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                    appId: cappId, // 必填，公众号的唯一标识
                    timestamp: ctimestamp, // 必填，生成签名的时间戳
                    nonceStr: cnonceStr, // 必填，生成签名的随机串
                    signature: csignature,// 必填，签名，见附录1
                    jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage'/*, 'onMenuShareQQ', 'onMenuShareWeibo', 'onMenuShareQZone'*/] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                });

                wx.ready(function () {
                    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
                    Share.hasConfig = true;
                    Share.menuShare(accountId, userId);
                });

                wx.error(function (res) {

                    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。

                    alert(res.toString);
                });

            } else {
                alert("无法获取配置信息");
            }

        });

    },


    menuShare: function (accountId, userId) {
        wx.onMenuShareTimeline({
            title: Share.title, // 分享标题
            link: Share.link, // 分享链接
            imgUrl: Share.imgUrl,
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });

        wx.onMenuShareAppMessage({
            title: Share.title, // 分享标题
            desc: Share.desc, // 分享描述
            link: Share.link, // 分享链接
            imgUrl: Share.imgUrl,
            type: '', // 分享类型,music、video或link，不填默认为link
            dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
        wx.onMenuShareQQ({
            title: Share.title, // 分享标题
            desc: Share.desc, // 分享描述
            link: Share.link, // 分享链接
            imgUrl: Share.imgUrl,
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
        wx.onMenuShareWeibo({
            title: Share.title, // 分享标题
            desc: Share.desc, // 分享描述
            link: Share.link, // 分享链接
            imgUrl: Share.imgUrl,
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
        wx.onMenuShareQZone({
            title: Share.title, // 分享标题
            desc: Share.desc, // 分享描述
            link: Share.link, // 分享链接
            imgUrl: Share.imgUrl,
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
    },

    doShare: function (accountId, userId) {
        if (Share.hasConfig) {
            Share.menuShare(accountId, userId);
        } else {
            Share.config(accountId, userId);
        }
    }
};