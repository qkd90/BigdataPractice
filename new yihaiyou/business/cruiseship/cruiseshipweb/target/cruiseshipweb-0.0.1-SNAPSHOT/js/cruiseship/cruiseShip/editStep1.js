var editStep1 = {
    showBrandPanel: false,
    showRoutePanel: false,
    // 初始化
    init: function () {
        editStep1.initComp();
        editStep1.initListener();
    },
    // 初始控件
    initComp: function () {
        //
        // 加载邮轮品牌
        $('#brand').combotree({
            url: '/goods/goods/getComboCatgoryData.jhtml?type=cruise_ship_brand',
            onBeforeSelect: function(node) {
                //var $target = $(node.target);
                var tree = $(this).tree;
                var isLeaf = tree('isLeaf', node.target);
                if (!isLeaf) {
                    editStep1.showBrandPanel = true;
                    return false;
                }
                editStep1.showBrandPanel = false;
                return true;
            },
            onHidePanel: function(data) {
                if (editStep1.showBrandPanel) {
                    $('#brand').combotree('showPanel');
                }
            },
            onShowPanel: function() {
                editStep1.showBrandPanel = false;
            },
            onLoadSuccess: function(node, data) {
                $.each(data, function(i, node){
                    if (node.children && node.children.length > 0) {
                        $('#' + node.domId).css('cursor', 'not-allowed');
                    }
                });
            }
        });
        // 加载邮轮路线
        $('#route').combotree({
            url: '/goods/goods/getComboCatgoryData.jhtml?type=cruise_ship_route',
            onBeforeSelect: function(node) {
                //var $target = $(node.target);
                var tree = $(this).tree;
                var isLeaf = tree('isLeaf', node.target);
                if (!isLeaf) {
                    editStep1.showRoutePanel = true;
                    return false;
                }
                editStep1.showRoutePanel = false;
                return true;
            },
            onHidePanel: function(data) {
                if (editStep1.showRoutePanel) {
                    $('#route').combotree('showPanel');
                }
            },
            onShowPanel: function() {
                editStep1.showRoutePanel = false;
            },
            onLoadSuccess: function(node, data) {
                $.each(data, function(i, node){
                    if (node.children && node.children.length > 0) {
                        $('#' + node.domId).css('cursor', 'not-allowed');
                    }
                });
            }
        });


        // 富文本-签证信息
        KindEditor.ready(function (K) {
            var visaInfoK = K.create(('#visaInfo'), {
                resizeType : 1,
                allowPreviewEmoticons : false,
                //uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
                //fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
                //allowImageUpload : true,
                //allowFileManager : true,
                //filePostName: 'resource',
                afterChange: function() {
                    this.sync();
                    var hasNum = this.count('text');
                    $('#visaInfo').next().children('.green-bold').html(hasNum);
                },
                afterBlur: function() {
                    this.sync();
                },
                items : [ 'fontsize', 'forecolor',  'bold', 'fullscreen']
            });
        });
        // 富文本-推荐理由
        KindEditor.ready(function(K) {
            var recommendK = K.create('#recommend', {
                resizeType : 1,
                allowPreviewEmoticons : false,
                //uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
                //fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
                //allowImageUpload : true,
                //allowFileManager : true,
                //filePostName: 'resource',
                afterChange: function() {
                    this.sync();
                    var hasNum = this.count('text');
                    $('#recommend').next().children('.green-bold').html(hasNum);
                },
                afterBlur: function() {
                    this.sync();
                },
                items : [ 'fontsize', 'forecolor',  'bold', 'fullscreen']
            });
        });
        // 富文本-行程特色
        KindEditor.ready(function(K) {
            var lightPointK = K.create('#lightPoint', {
                resizeType : 1,
                allowPreviewEmoticons : false,
                //uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
                //fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
                //allowImageUpload : true,
                //allowFileManager : true,
                //filePostName: 'resource',
                afterChange: function() {
                    this.sync();
                    var hasNum = this.count('text');
                    $('#lightPoint').next().children('.green-bold').html(hasNum);
                },
                afterBlur: function() {
                    this.sync();
                },
                items : [ 'fontsize', 'forecolor',  'bold', 'fullscreen']
            });
        });
        // 富文本-费用不含
        KindEditor.ready(function(K) {
            var quoteNoContainDescK = K.create('#quoteNoContainDesc', {
                resizeType : 1,
                allowPreviewEmoticons : false,
                //uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
                //fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
                //allowImageUpload : true,
                //allowFileManager : true,
                //filePostName: 'resource',
                afterChange: function() {
                    this.sync();
                    var hasNum = this.count('text');
                    $('#quoteNoContainDesc').next().children('.green-bold').html(hasNum);
                },
                afterBlur: function() {
                    this.sync();
                },
                items : [ 'fontsize', 'forecolor',  'bold', 'fullscreen']
            });
        });
        // 富文本-费用包含
        KindEditor.ready(function(K) {
            var quoteContainDescK = K.create('#quoteContainDesc', {
                resizeType : 1,
                allowPreviewEmoticons : false,
                //uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
                //fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
                //allowImageUpload : true,
                //allowFileManager : true,
                //filePostName: 'resource',
                afterChange: function() {
                    this.sync();
                    var hasNum = this.count('text');
                    $('#quoteContainDesc').next().children('.green-bold').html(hasNum);
                },
                afterBlur: function() {
                    this.sync();
                },
                items : [ 'fontsize', 'forecolor',  'bold', 'fullscreen']
            });
        });
        // 富文本-预订须知
        KindEditor.ready(function(K) {
            var orderKnowK = K.create('#orderKnow', {
                resizeType : 1,
                allowPreviewEmoticons : false,
                //uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
                //fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
                //allowImageUpload : true,
                //allowFileManager : true,
                //filePostName: 'resource',
                afterChange: function() {
                    this.sync();
                    var hasNum = this.count('text');
                    $('#orderKnow').next().children('.green-bold').html(hasNum);
                },
                afterBlur: function() {
                    this.sync();
                },
                items : [ 'fontsize', 'forecolor',  'bold', 'fullscreen']
            });
        });
        // 富文本-如何预订
        KindEditor.ready(function(K) {
            var howToOrderK = K.create('#howToOrder', {
                resizeType : 1,
                allowPreviewEmoticons : false,
                //uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
                //fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
                //allowImageUpload : true,
                //allowFileManager : true,
                //filePostName: 'resource',
                afterChange: function() {
                    this.sync();
                    var hasNum = this.count('text');
                    $('#howToOrder').next().children('.green-bold').html(hasNum);
                },
                afterBlur: function() {
                    this.sync();
                },
                items : [ 'fontsize', 'forecolor',  'bold', 'fullscreen']
            });
        });
        // 富文本-签约方式
        KindEditor.ready(function(K) {
            var signWayK = K.create('#signWay', {
                resizeType : 1,
                allowPreviewEmoticons : false,
                //uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
                //fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
                //allowImageUpload : true,
                //allowFileManager : true,
                //filePostName: 'resource',
                afterChange: function() {
                    this.sync();
                    var hasNum = this.count('text');
                    $('#signWay').next().children('.green-bold').html(hasNum);
                },
                afterBlur: function() {
                    this.sync();
                },
                items : [ 'fontsize', 'forecolor',  'bold', 'fullscreen']
            });
        });
        // 富文本-付款方式
        KindEditor.ready(function(K) {
            var payWayK = K.create('#payWay', {
                resizeType : 1,
                allowPreviewEmoticons : false,
                //uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
                //fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
                //allowImageUpload : true,
                //allowFileManager : true,
                //filePostName: 'resource',
                afterChange: function() {
                    this.sync();
                    var hasNum = this.count('text');
                    $('#payWay').next().children('.green-bold').html(hasNum);
                },
                afterBlur: function() {
                    this.sync();
                },
                items : [ 'fontsize', 'forecolor',  'bold', 'fullscreen']
            });
        });
        // 图片上传
        $('#imagePanel').diyUpload({
            url: "/line/lineImg/uploadLineImg.jhtml",
            success: function (data, $fileBox) {
                console.log(data);
                var address = BizConstants.QINIU_DOMAIN + data.path;
                $fileBox.remove();
                showImage($('#imagePanel'), address, data.id);
                $('#fileBox_' + data.id).append('<div class="diyCover"></div><div class="coverSuccess"></div>');
                $('#fileBox_' + data.id).find('.diyCover').click(function () {//增加事件
                    $('#coverParent').html('<div id="coverBox"></div>');
                    $('#coverPath').prop('value', data.path);
                    $('#imageBox').find('.coverSuccess').hide();
                    $('#fileBox_' + data.id).find('.coverSuccess').show();
                    showImageWithoutCancel($('#coverBox'), address, data.id);
                });
                $('#fileBox_' + data.id).find('.diyCancel').click(function () {
                    if (data.path == $('#coverPath').val()) {
                        showMsgPlus('提示', '封面已经被删除!!', '3000');
                        $('#coverBox').next('div.parentFileBox').remove();
                        $('#coverPath').val(null);
                        $('#coverImgId').val(null);
                    }
                    $("#input_" + data.id).remove();
                });
                $("#imageContent").append("<input id='input_" + data.id + "' type='hidden' name='imgPaths' value='" + data.path + "'>");
            },
            error: function (err) {
                console.info(err);
            },
            buttonText: '上传图片',
            chunked: true,
            // 分片大小
            chunkSize: 512 * 1024,
            //最大上传的文件数量, 总文件大小,单个文件大小(单位字节);
            fileNumLimit: 50,
            fileSizeLimit: 5000 * 1024,
            fileSingleSizeLimit: 2048 * 1024,
            formData: {
                section: CruiseShipConstants.infoImg
            }
        });
    },
    // 监听器
    initListener: function() {
        // 出发城市查询条件
        $('#startCity').textbox({
            onClickButton:function() {
                $('#startCity').textbox('setValue', '');
                $('#startCity').attr('data-country', '');
                $('#startCity').attr('data-province', '');
                $('#startCity').attr('data-city', '');
                // 特殊处理，为了结合原来代码
                $('#startCityId').val('');
            }
        });
        $("#startCity").next('span').children('input').click(function() {
            //$(this).blur(); // 移开焦点，否则事件会一直触发
            var country = $('#startCity').attr('data-country');
            var province = $('#startCity').attr('data-province');
            var city = $('#startCity').attr('data-city');
            AreaSelectDg.open(country, province, city, function(countryId, provinceId, cityId, fullName) {
                $('#startCity').textbox('setValue', fullName);
                if (countryId) {
                    $('#startCity').attr('data-country', countryId);
                    $('#startCityId').val(countryId);
                } else {
                    $('#startCity').attr('data-country', '');
                }
                if (provinceId) {
                    $('#startCity').attr('data-province', provinceId);
                    $('#startCityId').val(provinceId);
                } else {
                    $('#startCity').attr('data-province', '');
                }
                if (cityId) {
                    $('#startCity').attr('data-city', cityId);
                    $('#startCityId').val(cityId);
                } else {
                    $('#startCity').attr('data-city', '');
                }
            });
        });
        // 到达城市查询条件
        $('#arriveCity').textbox({
            onClickButton:function() {
                $('#arriveCity').textbox('setValue', '');
                $('#arriveCity').attr('data-country', '');
                $('#arriveCity').attr('data-province', '');
                $('#arriveCity').attr('data-city', '');
                // 特殊处理，为了结合原来代码
                $('#arriveCityId').val('');
            }
        });
        $("#arriveCity").next('span').children('input').click(function() {
            //$(this).blur(); // 移开焦点，否则事件会一直触发
            var country = $('#arriveCity').attr('data-country');
            var province = $('#arriveCity').attr('data-province');
            var city = $('#arriveCity').attr('data-city');
            AreaSelectDg.open(country, province, city, function(countryId, provinceId, cityId, fullName) {
                $('#arriveCity').textbox('setValue', fullName);
                if (countryId) {
                    $('#arriveCity').attr('data-country', countryId);
                    $('#arriveCityId').val(countryId);
                } else {
                    $('#arriveCity').attr('data-country', '');
                }
                if (provinceId) {
                    $('#arriveCity').attr('data-province', provinceId);
                    $('#arriveCityId').val(provinceId);
                } else {
                    $('#arriveCity').attr('data-province', '');
                }
                if (cityId) {
                    $('#arriveCity').attr('data-city', cityId);
                    $('#arriveCityId').val(cityId);
                } else {
                    $('#arriveCity').attr('data-city', '');
                }
            });
        });
    },
    // 下一步
    nextGuide: function (winId) {
        // 邮轮是否有图片校验
        var newImgLength = $("#imageContent").find('input[name = "lineImgPaths"]').length;
        var existImgLength = $("#imageBox").find('.viewThumb').children('img').length;
        if (newImgLength + existImgLength <= 0) {
            show_msg("请上传邮轮图片!");
            return;
        }
        // 邮轮图片封面校验
        if ($("#coverParent").find('.viewThumb').children('img').length <= 0) {
            showMsgPlus('提示', '请设置邮轮封面图片!', 3000);
            return;
        }
        // 保存表单
        $('#editForm').form('submit', {
            url: "/cruiseship/cruiseShip/saveInfo.jhtml",
            onSubmit: function () {
                var isValid = $(this).form('validate');
                if ($(this).form('validate')) {
                    $.messager.progress({
                        title: '温馨提示',
                        text: '数据处理中,请耐心等待...'
                    });
                } else {
                    show_msg("请完善当前页面数据");
                }
                return isValid;
            },
            success: function (result) {
                $.messager.progress("close");
                var result = eval('(' + result + ')');
                if (result.success == true) {
                    $('#productId').val(result.id);
                    parent.window.productId = result.id;
                    parent.window.showGuide(winId, true);
                } else {
                    show_msg("保存失败");
                }
            }
        });
    }
};
// 返回本页面数据
function getIfrData() {
    var data = {};
    data.productId = $('#productId').val();
    data.productName = $('#name').textbox('getValue');
    data.productAttr = $(':checked[name="productAttr"]').val();
    return data;
}

// zzl add 2016.07.01
function loadImage() {
    var productId = $('#productId').val();
    $.ajax({
        url: '/cruiseship/cruiseShip/getImageList.jhtml',
        dataType: 'json',
        data: {
            'productimage.product.id' : productId,
            'productimage.childFolder': $('#childFolder').val(),
            'productimage.proType': 'cruiseship'
        },
        success: function(result) {
            if (result.success) {
                $.each(result.data, function (i, img) {
                    var address = BizConstants.QINIU_DOMAIN + img.path;
                    showImage($('#imagePanel'), address, img.id);
                    $('#fileBox_' + img.id).append('<div class="diyCover"></div><div class="coverSuccess"></div>');
                    $('#fileBox_' + img.id).find('.diyCover').click(function () {//增加事件
                        $('#coverParent').html('<div id="coverBox"></div>');
                        $('#coverPath').prop('value', img.path);
                        $('#coverImgId').prop('value', img.id);
                        $('#imageBox').find('.coverSuccess').hide();
                        $('#fileBox_' + img.id).find('.coverSuccess').show();
                        showImageWithoutCancel($('#coverBox'), address, img.id);
                    });
                    if (img.coverFlag) {
                        $('#fileBox_' + img.id).find('.coverSuccess').show();
                        showImageWithoutCancel($('#coverBox'), address, img.id);
                    }
                    $('#fileBox_' + img.id).find('.diyCancel').click(function () {
                        if (img.coverFlag) {
                            showMsgPlus('提示', '封面已经被删除!!', '3000');
                            $('#coverBox').next('div.parentFileBox').remove();
                            $('#coverPath').val(null);
                            $('#coverImgId').val(null);
                        }
                        $("#imageContent").append("<input type='hidden' name='imgDeleteIds' value='" + img.id + "'>");
                    });
                });
            }
        },
        error: function (result) {
            //
            console.log(result);
        }
    });
}
$(function () {
    editStep1.init();
    // 加载线路已有图片
    loadImage();
});