/**
 * Created by dy on 2016/8/25.
 */
var EditTransport = {
    init: function() {
        EditTransport.initMap("厦门");
        EditTransport.initAddCover();
        EditTransport.initAreaTextbox();
        EditTransport.initCom();
    },

    initCom: function() {
        $("#ipt_address").textbox({
            onClickButton: function() {
                $("#ipt_address").textbox("setValue", "");
            }
        });
    },

    initAreaTextbox: function() {
        var hid_startCityId = $("#hidden_startCityId").val();
        if (hid_startCityId) {
            var url = "/sys/area/getAreaInfo.jhtml";
            $.post(url,
                {cityId: hid_startCityId},
                function(result) {
                    if (result.level == 2) {
                        var fullName = result.countryName + "/" + result.provinceName + "/" + result.cityName;
                        $('#startCityId').textbox('setValue', fullName);
                        $('#startCityId').attr('data-country', result.countryId);
                        $('#startCityId').attr('data-province', result.provinceId);
                        $('#startCityId').attr('data-city', result.cityId);
                    } else if (result.level == 1) {
                        var fullName = result.countryName + "/" + result.provinceName ;
                        $('#startCityId').textbox('setValue', fullName);
                        $('#startCityId').attr('data-country', result.countryId);
                        $('#startCityId').attr('data-province', result.provinceId);
                    } else if (result.level == 0) {
                        var fullName = result.countryName;
                        $('#startCityId').textbox('setValue', fullName);
                        $('#startCityId').attr('data-country', result.countryId);
                    }

                }
            );

        }

        // 出发城市查询条件
        $('#startCityId').textbox({
            onClickButton:function() {
                $('#startCityId').textbox('setValue', '');
                $('#startCityId').attr('data-country', '');
                $('#startCityId').attr('data-province', '');
                $('#startCityId').attr('data-city', '');
                // 特殊处理，为了结合原来代码
                $('#hidden_startCityId').val('');

            }
        });
        $("#startCityId").next('span').children('input').focus(function() {

            //$(this).blur(); // 移开焦点，否则事件会一直触发
            var country = $('#startCityId').attr('data-country');
            var province = $('#startCityId').attr('data-province');
            var city = $('#startCityId').attr('data-city');
            AreaSelectDg.open(country, province, city, function(countryId, provinceId, cityId, fullName, allName) {
                $('#startCityId').textbox('setValue', fullName);
                if (countryId) {
                    $('#startCityId').attr('data-country', countryId);
                } else {
                    $('#startCityId').attr('data-country', '');
                }
                if (provinceId) {
                    $('#startCityId').attr('data-province', provinceId);
                } else {
                    $('#startCityId').attr('data-province', '');
                }
                if (cityId) {
                    $('#startCityId').attr('data-city', cityId);
                } else {
                    $('#startCityId').attr('data-city', '');
                }
                // 特殊处理，为了结合原来代码
                if (cityId) {
                    $('#hidden_startCityId').val(cityId);
                } else if (provinceId) {
                    $('#hidden_startCityId').val(provinceId);
                } else if (countryId) {
                    $('#hidden_startCityId').val(countryId);
                }
                var cityName = fullName.substr(fullName.lastIndexOf("/") + 1, fullName.length - 1);
                EditTransport.initMap(cityName);
                $("#hidden_startcityName").val(cityName);
                $("#hidden_startcityCode").val(cityId);
            });
        });



    },

    /**
     * 新增描述图片
     */
    initAddCover: function() {

        var filePath = $("#filePath").val();

        if (filePath) {
            $('#imgView img').attr('src', filePath);
            $('#imgView').show();
            $("#btn_class").hide();
        }

        // 图片上传
        KindEditor.ready(function(K) {
            var uploadbutton = K.uploadbutton({
                button : K('#add_descpic')[0],
                fieldName : 'resource',
                url : '/transportation/transportation/uploadImg.jhtml',
                extraParams : {oldFilePath:$('#filePath').val(), folder:TransportConstants.transortDescImg},
                afterUpload : function(result) {
                    $.messager.progress("close");
                    if(result.success==true) {
                        var url = K.formatUrl(result.url, 'absolute');
                        $('#filePath').val(url);
                        $('#imgView img').attr('src', url);
                        $('#imgView').show();
                        $("#btn_class").hide();
                    }else{
                        show_msg("图片上传失败");
                    }
                },
                afterError : function(str) {
                    show_msg("图片上传失败");
                }
            });
            uploadbutton.fileBox.change(function(e) {
                var filePath = uploadbutton.fileBox[0].value;
                if (!filePath) {
                    show_msg("图片格式不正确");
                    return ;
                }
                var suffix = filePath.substr(filePath.lastIndexOf("."));
                suffix = suffix.toLowerCase();
                if ((suffix!='.jpg')&&(suffix!='.gif')&&(suffix!='.jpeg')&&(suffix!='.png')&&(suffix!='.bmp')){
                    show_msg("图片格式不正确");
                    return ;
                }
                $.messager.progress({
                    title:'温馨提示',
                    text:'图片上传中,请耐心等待...'
                });
                uploadbutton.submit();
            });
        });



    },
    /**
     * 删除图片
     */
    delDescPic: function() {
        var filePath = $("#filePath").val();
        if (filePath) {
            $("#filePath").val("");
            $('#imgView').hide();
            $('#imgView img').attr('src', "");
            $("#btn_class").show();
        } else {
            return;
        }


    },

    initMap: function(name) {

        var map = new BMap.Map("baiduMap");

        map.enableScrollWheelZoom();    //启用滚轮放大缩小，默认禁用
        map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用

        var lng = $("#map_lng").textbox("getValue");
        var lat = $("#map_lat").textbox("getValue");

        if (lng && lat) {
            var pt = new BMap.Point(lng, lat);
            map.centerAndZoom(pt, 17);
            var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25));
            var marker = new BMap.Marker(pt,{icon:myIcon});
            map.addOverlay(marker);    //增加点

        } else {
            //创建小点
            map.centerAndZoom(name, 12);
            $("#map_lng").textbox("setValue", "");
            $("#map_lat").textbox("setValue", "");
        }


        //单击获取点击的经纬度
        map.addEventListener("click",function(e){
            map.clearOverlays();
            $("#map_lng").textbox("setValue", e.point.lng);
            $("#map_lat").textbox("setValue", e.point.lat);
            lng = e.point.lng;
            lat = e.point.lat;
            //创建小狐狸
            var pt = new BMap.Point(lng, lat);
            var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25));
            var marker = new BMap.Marker(pt,{icon:myIcon});
            map.addOverlay(marker);    //增加点
        });

    },
    cancelEdit: function(){
        window.parent.$("#editPanel").dialog("close");
    },
    // 下一步
    nextGuide:function(){
        // 保存表单
        $('#editForm').form('submit', {
            url : "/transportation/transportation/saveTransport.jhtml",
            onSubmit : function() {

                var isValid = $(this).form('validate');
                if($(this).form('validate')){
                    $.messager.progress({
                        title:'温馨提示',
                        text:'数据处理中,请耐心等待...'
                    });
                } else {
                    show_msg("请完善当前页面数据");
                }
                return isValid;
            },
            success : function(result) {
                $.messager.progress("close");
                var result = eval('(' + result + ')');
                if(result.success==true){
                    window.parent.$("#editPanel").dialog("close");
                    window.parent.$("#show_dg").datagrid("load", {});
                }else{
                    show_msg("保存酒店失败");
                }
            }
        });

    }
}

$(function() {
    EditTransport.init();
});