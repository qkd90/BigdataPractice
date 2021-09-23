/**
 * Created by zzl on 2016/1/6.
 */
var rootData = {};
var recplanId = 0;
var isNewRecplan = false;
var isNewDay = false;
$(function () {
    commonEvent();
    recplanId = $("#recplanId").val();
    if (recplanId != null && recplanId > 0) {
        getData();
    } else {
        // 新建一个游记
        newRecommendPlan();
    }
});
function getData() {
    promptMessage("正在加载游记数据...", null, false);
    $("#left_day_list").html('');
    $("#right_recommendPlan_index").html('');
    $.ajax({
        url: "/lvxbang/recommendPlan/editRecommendPlan.jhtml",
        type: "post",
        dataType: "json",
        data: {
            'recplanId': recplanId
        },
        success: function (data) {
            if (data.result != null && data.result == "nologin") {
                changeMsg("请先登录!");
                setTimeout(function() {
                    return window.location.href = $('#index_path').val() + "/lvxbang/login/login.jhtml";
                },2000);
            }
            if (data.result != null && data.result == "user_not_match") {
                changeMsg("只可以编辑自己的游记哦!");
                setTimeout(function() {
                    return window.location.href = "/lvxbang/recommendPlan/index.jhtml";
                },2000);
            }
            if (data.result != null && data.result == "not_exists") {
                changeMsg("不存在的游记!");
                setTimeout(function() {
                    return window.location.href = "/lvxbang/recommendPlan/index.jhtml";
                },2000);
            }
            //console.log(data);
            // 保存原始数据
            rootData = data;
            loadCoverImg(data.coverPath);
            //加载游记数据
            loadData(data);
            // 获取人物标签
            getLabels();
            // 最后加载按钮区域
            if($("#left_recommendPlan_list").find('.Upload_hr_bot').length <= 0) {
                var left_btn_area = template("left_btn_area");
                $("#left_recommendPlan_list").append(left_btn_area);
            }
            //图片懒加载
            $("img").lazyload({
                effect: "fadeIn"
            });
            //右侧滑动/点击事件
            //regScrollEvent();
            //绑定封面上传组件
            regCoverUploader();
            closeMsgBox();
        },
        error: function (data) {
            //console.log(data);
            changeMsg("请求游记时候, 发送错误! 请稍候重试!");
            setTimeout(function () {
                return window.location.href = '/lvxbang/recommendPlan/index.jhtml';
            },2000);
        }
    });
}
// 加载游记数据
function loadData(data) {
    $.each(data.recommendPlanDays, function (day_index, day) {
        day['day_index'] = day_index;
        day['day_id'] = day.id;
        //console.log(day.day);
        var left_recommendPlan_day_item = template("left_edit_recommendPlan_day_item", day);
        var right_recommendPlan_day_item = template("right_edit_recommendPlan_day_item", day);
        //$(left_recommendPlan_day_item).insertAfter($("#left_recommendPlan_list"));
        $("#left_day_list").append(left_recommendPlan_day_item);
        $("#right_recommendPlan_index").append(right_recommendPlan_day_item);
        // 加载当天描述文本编辑器
        //var ue = UM.getEditor('left_day_' + day_index + '_description');
        //ue.destroy();
        //ue = UM.getEditor('left_day_' + day_index + '_description');
        //if (isNull(day.description)) {
        //    ue.setContent("<span style='color: silver'>说说这一天过的咋样......</span>");
        //}
        //ue.addListener('focus blur', function () {
        //    if (ue.getContentTxt() == '说说这一天过的咋样......') {
        //        ue.setContent('');
        //    } else if (ue.getContentTxt() == '') {
        //        ue.setContent("<span style='color: silver'>说说这一天过的咋样......</span>");
        //    }
        //
        //});
        loadDayData(day_index, day);
    });
    closeMsgBox();
}
// 加载游记天数据
function loadDayData(day_index, day) {
    $.each(day.recommendPlanTrips, function (trip_index, trip) {
        //console.log(trip);
        trip['day_index'] = day_index;
        trip['trip_index'] = trip_index;
        trip['r_id'] = recplanId;
        trip['day_id'] = day.id;
        trip['trip_id'] = trip.id;
        var left_recommendPlan_trip_item = template("left_edit_recommendPlan_trip_item", trip);
        var right_recommendPlan_trip_item = template("right_edit_recommendPlan_trip_item", trip);
        var left_day_trip_element_id = "left_day_" + day_index + "_trip_list";
        var right_day_trip_element_id = "right_day_" + day_index + "_dd";
        $("#" + left_day_trip_element_id).append(left_recommendPlan_trip_item);
        $("#" + right_day_trip_element_id).append(right_recommendPlan_trip_item);
        // 加载当天某个行程节点的编辑器
        var ue = UM.getEditor('left_day_' + day_index + '_trip_' + trip_index + '_exa');
        ue.destroy();
        ue = UM.getEditor('left_day_' + day_index + '_trip_' + trip_index + '_exa');
        if (isNull(trip.exa)) {
            ue.setContent("<span style='color: silver'>游记写的屌，旅行才完美......</span>");
        }
        ue.addListener('focus blur', function () {
            if (ue.getContentTxt() == '游记写的屌，旅行才完美......') {
                ue.setContent('');
            } else if (ue.getContentTxt() == '') {
                ue.setContent("<span style='color: silver'>游记写的屌，旅行才完美......</span>");
            }

        });
        // 加载左侧游记图片模板部分
        loadTripImage(day_index, trip_index, trip);

    });
}

// 获取人物标签
function getLabels() {
    $.ajax({
        url: "/lvxbang/labels/getRecplanListLabels.jhtml?parentId=44",
        type: "get",
        dataType: "json",
        success: function (data) {
            var $ul = $('#user_tag').find('div.Identity_c').find('ul');
            $ul.html('');
            $.each(data, function (i, item) {
                $ul.append("<li id = lid_" + item.id + "><span class=\"checkbox\" input=\"options\" lid="+item.id+"><i></i></span>"+item.name+"</li>");
            });
            var index = 0;
            if (rootData.labelItemsVos.length > 0) {
                $(".Identity_t").children('b').html('');
                $("#user_tag").css("border", "1px solid #EFEEEE")
            }
            $.each(rootData.labelItemsVos, function(i, item) {
                //console.log(item.id);
                var data = {
                    lid : item.labelId,
                    recplanId: $("#recplanId").val(),
                    index: index
                }
                var input_items = template("left_identity_input", data);
                $("#label_items").append(input_items);
                index += 1;
                // 勾选已经存在的标签条目
                $("#lid_" + item.labelId).addClass("checked");
            });
            $ul.find('li').click(function () {
                $(this).hasClass("checked") ? $(this).removeClass("checked") : $(this).addClass("checked");
                if($ul.find('li.checked').length <= 0) {
                    $(".Identity_t").children('b').html('').html('(你还没有选择身份)');
                    $("#user_tag").css("border", "1px solid #f55")
                } else {
                    $("#user_tag").css("border", "1px solid #EFEEEE")
                    $(".Identity_t").children('b').html('');
                }
                $("#label_items").html("");
                var index = 0
                $.each($ul.find('li'), function(i, item) {
                    if($(this).hasClass("checked")) {
                        var data = {
                            lid : $(this).children('span').attr("lid"),
                            recplanId: $("#recplanId").val(),
                            index: index
                        }
                        var input_items = template("left_identity_input", data);
                        $("#label_items").append(input_items);
                        index += 1;
                    }
                });
            });
        }
    });
}
// 事件
$(function () {
    //
    //预先绑定图片删除事件
    $("#left_day_list").delegate('div.img a.close', 'click', function (event) {
        if (confirm("确定删除该图片?")) {
            //promptMessage("正在删除照片...", null, false);
            // 获取触发事件的元素
            event = event ? event : window.event;
            var a = event.srcElement ? event.srcElement : event.target;
            // 转为jquery对象
            var $a = $(a);
            var day_index = $a.attr('day-index');
            var trip_index = $a.attr('trip-index');
            var p_id = $a.attr('data-id');
            if (p_id == -1 || p_id <= 0) {
                $("#left_day_" + day_index + "_trip_" + trip_index + "_coverImg").val(null);
                $a.parent('div').remove();
                promptMessage("删除成功! 保存游记后生效!...", 1000, true);
                //changeMsg("删除成功! 保存游记后生效!");
                //closeMsgBox({
                //    timeout: 2000,
                //});
                return;
            }
            //console.log(p_id);
            $.ajax({
                url: '/lvxbang/recommendPlan/deleteOnePhoto.jhtml',
                type: 'post',
                dataType: 'json',
                data: {
                    id: p_id
                },
                success: function (result) {
                    // 图片地址
                    var addr = $a.parent('div.img').find('img').attr('data-original');
                    // 存储的地址
                    var now_addr = $("#left_day_" + day_index + "_trip_" + trip_index + "_coverImg").val();
                    if (now_addr.indexOf("http") < 0) {
                        addr = addr.substring(addr.indexOf("qiniucdn.com"), addr.indexOf("?imageView2")).replace("qiniucdn.com/","");
                    }
                    if (now_addr == addr) {
                        $("#left_day_" + day_index + "_trip_" + trip_index + "_coverImg").val(null);
                        promptMessage("删除成功! 封面已被删除!...", 1000, true);
                        //changeMsg("照片删除成功! 封面已被删除!");
                        //closeMsgBox({
                        //    timeout: 1000,
                        //});
                    } else {
                        promptMessage("删除成功!...", 1000, true);
                        //changeMsg("照片删除成功!");
                        //closeMsgBox({
                        //    timeout: 1000,
                        //});
                    }
                    $a.parent('div').remove();
                    //$a.prev('img').remove();
                    //$a.remove();
                    if ($("#day_" + day_index + "_trip_" + trip_index + "_img_list").find("div img").length <= 0) {
                        $("#day_" + day_index + "_trip_" + trip_index + "_img_area").removeClass("up_img");
                    }
                },
                error: function (result) {
                    changeMsg("照片删除失败! 请稍后重试! ");
                    closeMsgBox({
                        timeout: 2000,
                    });
                }
            });
        }
    });

    //预先绑定设置封面事件
    $("#left_day_list").delegate('div.img a.setcover', 'click', function(event) {
        var day_index = $(this).attr('day-index');
        var trip_index = $(this).attr('trip-index');
        // 图片地址
        var addr = $(this).parent('div.img').find('img').attr('data-original');
        // 存储的地址
        var now_addr = $("#left_day_" + day_index + "_trip_" + trip_index + "_coverImg").val();
        if (now_addr.indexOf("http") < 0) {
            addr = addr.substring(addr.indexOf("qiniucdn.com"), addr.indexOf("?imageView2")).replace("qiniucdn.com/","");
        }
        if (now_addr != addr) {
            $("#left_day_" + day_index + "_trip_" + trip_index + "_coverImg").val(addr);
            // 去除现有的封面标记
            $("#coverflag" + day_index + "_" + trip_index).remove();
            $(this).before('<a href="javaScript:;" id="coverflag' + day_index + '_' + trip_index + '" class="coverflag oval4">封面</a>')
            promptMessage("封面设置成功! 保存游记后生效!",1000);
        } else {
            promptMessage("已经是封面!",1000);
        }
        $(this).parent('div.img').find('a.close').attr('iscover', 1);
    });

    // 预先绑定行程天删除事件
    $("#left_day_list").delegate('.title a.close', 'click', function(ecvent) {
        if (confirm("确认删除该天?")) {
            promptMessage("正在删除该天...", null, false);
            var day = $(this).attr("day");
            var recommendPlanDayId = $(this).attr("d-id");
            var scenics = $(this).attr("scenics");
            $("#status").val("1");
            submitRecommendPlan({
                onReturn: function(result) {
                    if (result) {
                        $.ajax({
                            url: '/lvxbang/recommendPlan/deleteOneDay.jhtml',
                            type: 'post',
                            dataType: 'json',
                            data: {
                                day: day,
                                scenics: scenics,
                                recplanId: $("#recplanId").val(),
                                recommendPlanDayId: recommendPlanDayId
                            },
                            success: function(result) {
                                if (result.msg = "success") {
                                    // 重新加载数据
                                    getData();
                                    // 天数文本框减少一天
                                    $("#days").val(Number($("#days").val() - 1));
                                    $("#t_days").val(Number($("#days").val() - 1));
                                    changeMsg("成功删除该天!");
                                    closeMsgBox({
                                        timeout: 2000,
                                        afterclose: doAfterSubmit()
                                    });
                                }
                            },
                            error: function(result) {
                                changeMsg("无法删除该天!");
                                closeMsgBox({
                                    timeout: 2000,
                                    afterclose: doAfterSubmit()
                                });
                            }
                        });
                    } else {
                        changeMsg("游记保存失败!不能删除!");
                        closeMsgBox({
                            timeout: 2000,
                            //afterclose: doAfterSubmit()
                        });
                    }
                }
            });
        }
    });
    // 预先绑定行程节点删除事件
    $("#left_day_list").delegate('div.close a', 'click', function (event) {
        if (confirm("确定删除?")) {
            promptMessage("正在删除...", null, false);
            var day_id = $(this).attr("d-id");
            var trip_id = $(this).attr("t-id");
            var sort = $(this).attr("sort");
            $("#status").val("1");
            submitRecommendPlan({
                onReturn: function(result) {
                    if (result) {
                        $.ajax({
                            url: '/lvxbang/recommendPlan/deleteOneTrip.jhtml',
                            type: 'post',
                            dataType: 'json',
                            data: {
                                recplanId: $("#recplanId").val(),
                                recommendPlanDayId: day_id,
                                recommendPlanTripId: trip_id,
                                sort: sort
                            },
                            success: function(result) {
                                if (result.msg = "success") {
                                    // 重新加载数据
                                    changeMsg("删除成功!");
                                    closeMsgBox({
                                        timeout: 2000,
                                        afterclose: getData()
                                    });
                                }
                            },
                            error: function(result) {
                                changeMsg("删除失败!");
                                closeMsgBox({
                                    timeout: 2000,
                                    afterclose: doAfterSubmit()
                                });
                            }
                        });
                    } else {
                        changeMsg("游记保存失败!不能删除!");
                        closeMsgBox({
                            timeout: 2000,
                            //afterclose: doAfterSubmit()
                        });
                    }
                }
            });
        }
    });
    // 节点类型选择事件
    regTripTypeSelector();
});
// 节点类型选择事件
function regTripTypeSelector() {
    $("#left_day_list").delegate('div.type_sel', 'click', function (event) {
        var display = $(this).children('ul.Upload_add_ul').css("display");
        var $ul = $(this).children('ul.Upload_add_ul')
        if (display == 'block') {
            $ul.hide();
        } else {
            $ul.show();
        }
    });
    $("#left_day_list").delegate('ul.Upload_add_ul li', 'click', function(event) {
        var $ul = $(this).parent("ul");
        var tripType = Number($(this).attr("data-type"));
        var day_index = $ul.attr("day-index");
        var trip_index = $ul.attr("trip-index");
        var $div = $("#day_" + day_index + "_trip_" + trip_index + "_typeDiv");
        var $span = $("#day_" + day_index + "_trip_" + trip_index + "_typeSpan");
        $span.removeClass();
        $span.addClass("span_u type" + tripType);
        $div.css("border", "3px solid #efeeee");
        $("#left_day_" + day_index + "_trip_" + trip_index + "_tripType").val(tripType);
    });
}
// 绑定封面图片上传
function regCoverUploader() {
    var options = {
        url: '/lvxbang/upload/imageUpload.jhtml',
        picker: '#coverUpload',
        formData: {
            section: "recommendPlan"
        },
        singleSuccessHandler: function (response) {
            //TODO 游记照片后续保存方式 编辑时? 新建时?
            loadCoverImg(response.path);
            return true;
        },
        allSuccessHandler: function () {
            //TODO 游记照片后续保存方式 编辑时...? 新建时...?
            return true;
        }
    };
    // 初始化上传器
    initCoverUploader(options);
}
// 绑定节点图片上传
function regTripImgUploader(recplanId, recommendPlanDayId, recommendPlanTripId, day_index, trip_index) {
    //TODO 游记照片后续保存方式 编辑时? 新建时?
    var picker = "#day_" + day_index +"_trip_" + trip_index + "_tripImg_picker";
    var options = {
        url: '/lvxbang/upload/imageUpload.jhtml',
        picker: picker,
        formData: {
            section: "recommendPlan"
        },
        singleSuccessHandler: function (response, file) {
            //console.log(response);
            var $li = $("#f_" + file.id);
            var $percent = $li.find("span");
            var sort = file.id.split("_")[2];
            $.ajax({
                url: '/lvxbang/recommendPlan/saveImg.jhtml;',
                type: 'post',
                dataType: 'json',
                data: {
                    'recommendPlanPhoto.recommendPlan.id': recplanId,
                    'recommendPlanPhoto.recommendPlanDay.id': recommendPlanDayId,
                    'recommendPlanPhoto.recommendPlanTrip.id': recommendPlanTripId,
                    'recommendPlanPhoto.sort': sort,
                    'recommendPlanPhoto.photoLarge': response.path,
                    'recommendPlanPhoto.width': response.width,
                    'recommendPlanPhoto.height': response.height
                },
                success: function (result) {
                    if (result.msg == "success") {
                        $percent.html('').css('width', '100%').html('上传成功');
                        loadUploadImg(day_index, trip_index, result.data);
                    }
                },
                error: function (result) {
                    $percent.css('background-color', '#F44336');
                    $percent.css('width', '100%');
                    $percent.html('').html('上传失败!');
                }
            });
            return true;
        },
        allSuccessHandler: function () {
            //TODO 游记照片后续保存方式 编辑时保存方式? 新建时保存方式?
            return true;
        }
    }
    //初始化上传器
    initTripImgUploader(options);
    // 修正上传按钮样式
    if ($("#day_" + day_index + "_trip_" + trip_index + "_img_list").find("div img").length <= 0) {
        $("#day_" + day_index + "_trip_" + trip_index + "_img_area").removeClass("up_img");
    }
}
// 加载游记封面图(背景设置需要兼容IE!低版本)
function loadCoverImg(coverPath) {
    if (!isNull(coverPath)) {
        $("#coverPath").val(coverPath);
        if (coverPath.substring(0, 4) == 'http' || coverPath.substring(0, 11) == 'data:image/') {
            $("div.SetTravels").css('background-image', 'url("' + coverPath + '")');
            $("div.SetTravels").css('filter',
                'progid:DXImageTransform.Microsoft.AlphaImageLoader(src="' + coverPath + '",sizingMethod="scale")');
        } else {
            $("div.SetTravels").css('background-image', 'url("http://7u2inn.com2.z0.glb.qiniucdn.com/' + coverPath + '")');
            $("div.SetTravels").css('filter', 'progid:DXImageTransform.Microsoft.AlphaImageLoader(' +
                'src="http://7u2inn.com2.z0.glb.qiniucdn.com/' + coverPath + '",sizingMethod="scale")');
        }
    }

}
// 加载上传图片
function loadUploadImg(day_index, trip_index, photo) {
    var data = {
        day_index: day_index,
        trip_index: trip_index,
        imgAddr: photo.photoLarge,
        p_id: photo.id
    };
    var left_img_area = template("left_img_area", data);
    $("#day_" + day_index + "_trip_" + trip_index + "_img_list").append(left_img_area);
    $("#day_" + day_index + "_trip_" + trip_index + "_img_list").last('div.img').find('img').lazyload({
        effect: "fadeIn"
    });
    $("#day_" + day_index + "_trip_" + trip_index + "_img_area").addClass("up_img");
}
// 加载左侧游记图模板部分
function loadTripImage(day_index, trip_index, trip) {
    var coverImg = trip.coverImg;
    var coverId = -1;
    if (trip.photos.length <= 0 && (coverImg == null || coverImg == '')) {
        $("#day_" + day_index + "_trip_" + trip_index + "_img_area").removeClass("up_img");
    }
    $.each(trip.photos, function(i, item) {
        // 在节点相册中搜索封面图片的id
        if (item.photoLarge == coverImg) {
            coverId = item.id;
        } else {
            var data = {
                day_index: day_index,
                trip_index: trip_index,
                //width: item.width,
                //height: (680 / item.width) * item.height,
                imgAddr: item.photoLarge,
                p_id: item.id
            };
            if (item.width > 0 && item.height > 0) {
                data['height'] = (680 / item.width) * item.height;
            }
            var left_img_area = template("left_img_area", data);
            $("#day_" + day_index + "_trip_" + trip_index + "_img_list").append(left_img_area);
        }
    });
    // 最后载封面, 以获取封面图片的ID
    if (coverImg != null && coverImg != '') {
        var data = {
            day_index: day_index,
            trip_index: trip_index,
            imgAddr: coverImg,
            p_id: coverId
        };
        var left_cover_area = template("left_cover_area", data);
        $("#left_day_" + day_index + "_trip_" + trip_index + "_coverImg").val(coverImg);
        $("#day_" + day_index + "_trip_" + trip_index + "_img_list").prepend(left_cover_area);
    }
    // 应用图片懒加载
    $(".img").lazyload({
        effect: "fadeIn"
    });
    regTripImgUploader(trip.recplanId, trip.recdayId, trip.id, day_index, trip_index);
}
// 编辑页面综合搜索框
function complexSearch(day_index, trip_index) {
    var keyword = $.trim($("#left_day_" + day_index + "_trip_" + trip_index + "_scenicName").val());
    var tripType = $("#left_day_" + day_index + "_trip_" + trip_index + "_tripType").val();
    var type = tripTypeToString(tripType);
    if (tripType == 5 || tripType == null || !tripType > 0) {
        return;
    }
    //console.log(keyword);
    var regex = /[a-zA-Z]+/;
    if (regex.test(keyword) || keyword == "" || keyword == null || event.keyCode==38 || event.keyCode==40 || event.keyCode==13) {
        return;
    }

    $.ajax({
        url: '/lvxbang/recommendPlan/multipleNameList.jhtml',
        type: 'post',
        dataType: 'json',
        data: {
            'complexSearchRequest.name': keyword,
            'complexSearchRequest.type': type
        },
        success: function (data) {
            var complex_search_item = '';
            $.each(data, function (i, item) {
                item['day_index'] = day_index;
                item['trip_index'] = trip_index;
                item['keyword'] = item.name.replace(keyword, "<strong>" + keyword + "</strong>");
                if (item.type == "scenic_info") {
                    item['typeName'] = "景点";
                    item['type'] = 1;
                } else if (item.type == "delicacy") {
                    item['typeName'] = "美食";
                    item['type'] = 2;
                } else if (item.type == "hotel") {
                    item['typeName'] = '酒店';
                    item['type'] = 3;
                } else if (item.type == "transportation") {
                    item['typeName'] = transportationTypeToString(item.transportationType);
                    item['type'] = 4;
                }
                complex_search_item += template("complex_search_item", item);
            });
            $("#day_" + day_index + "_trip_" + trip_index + "_complex_name_list ul").html("");
            $("#day_" + day_index + "_trip_" + trip_index + "_complex_name_list ul").append(complex_search_item);
            $("#day_" + day_index + "_trip_" + trip_index + "_complex_name_list ul").find("li").click(function() {
                $("#left_day_" + day_index + "_trip_" + trip_index + "_scenicName").val($(this).attr("data-name"));
                $("#left_day_" + day_index + "_trip_" + trip_index + "_tripType").val($(this).attr("data-type"));
                $("#left_day_" + day_index + "_trip_" + trip_index + "_cityCode").val($(this).attr("data-city"));
                // 美食行程的scenicId为餐厅id, delicacyId为美食id
                if ($(this).attr("data-type") == 2) {
                    $("#left_day_" + day_index + "_trip_" + trip_index + "_delicacyId").val($(this).attr("data-id"));
                    $("#left_day_" + day_index + "_trip_" + trip_index + "_scenicId").val(null);
                } else {
                    $("#left_day_" + day_index + "_trip_" + trip_index + "_delicacyId").val(null);
                    $("#left_day_" + day_index + "_trip_" + trip_index + "_scenicId").val($(this).attr("data-id"));
                }
                // 点击完成后解绑点击绑定事件, 避免再次搜索时候重复绑定
                $("#day_" + day_index + "_trip_" + trip_index + "_complex_name_list ul").find("li").unbind('click');
                // 清空本次搜索列表内容
                $("#day_" + day_index + "_trip_" + trip_index + "_complex_name_list ul").html("");
            });
            // 显示搜索结果
            $("#day_" + day_index + "_trip_" + trip_index + "_complex_name_list").addClass('checked').show();
        }
    });
}
function tripTypeToString(tripType) {
    if (tripType == 1) {
        return "scenic_info";
    } else if (tripType == 2) {
        return "delicacy";
    } else if (tripType == 3) {
        return "hotel";
    } else if (tripType == 4) {
        return "transportation";
    } else if (tripType == 5) {
        return "other";
    }
}
function transportationTypeToString(transportationType) {
    if (transportationType == 1) {
        return "火车站";
    } else if (transportationType == 2) {
        return "机场";
    } else if (transportationType == 3) {
        return "汽车站";
    } else if (transportationType == 0) {
        return "交通";
    } else {
        return "交通";
    }
}
/**
 * 新建一个游记
 */
function newRecommendPlan() {
    //promptMessage("正在为您创建游记, 请稍候...", null, false);
    $.ajax({
        url: '/lvxbang/recommendPlan/newRecommendPlan.jhtml',
        type: 'post',
        dataType: 'json',
        success: function (result) {
            $("#recplanId").val(result.recplanId);
            isNewRecplan = true;
            // 新建游记, 默认添加一天
            addOneDay();
            // 最后加载按钮区域
            var left_btn_area = template("left_btn_area");
            $("#left_recommendPlan_list").append(left_btn_area);
        },
        error: function (result) {
            promptWarn("网络异常! 请稍候重试!");
            setTimeout(function() {
                return window.location.href = '/lvxbang/recommendPlan/index.jhtml';
            },2000);
        }
    });
}
/**
 * 增加一天
 */
function addOneDay() {
    $("#add_day").css("border", "");
    isNewDay = true;
    if (!isNewRecplan) {
        promptMessage("正在增加天,请稍候...", null, false);
    }
    var recplanId = $("#recplanId").val();
    // 获取当前已有游记天数
    var day_index = $("#left_day_list").children("div").length;
    if (recplanId > 0 || recplanId != null ) {
        // 新建一天
        $.ajax({
            url: '/lvxbang/recommendPlan/addOneDay.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                recplanId: recplanId,
                day: day_index + 1
            },
            beforeSend: function(XMLHttpRequest) {
            },
            success: function(result) {
                if (isNull(day_index)) {
                    day_index = 0; // 天数序号, 从0开始
                }
                $("#days").val(day_index + 1);
                $("#t_days").val(day_index + 1);
                var data = {
                    day_id: result.recommendPlanDayId,
                    day_index: day_index,
                    day: day_index + 1,
                    scenics: 0
                };
                var left_recommendPlan_day_item = template("left_edit_recommendPlan_day_item", data);
                $("#left_day_list").append(left_recommendPlan_day_item);
                // 加载当天描述文本编辑器
                //UM.getEditor('left_day_' + day_index + '_description');
                // 当新增一天时候, 默认为其增加一个行程节点
                addOneTrip(day_index, result.recommendPlanDayId);
                // 滚动至该天
                var day_top = $("#day_" + day_index).offset().top - 75;
                $(window).scrollTop(day_top);
            },
            error: function(result) {
                promptWarn("网络异常! 请稍候重试!");
                $a.attr("isuse", 0);
            }
        });
    }
}
/**
 * 添加一个节点
 */
function addOneTrip(day_index, day_id) {
    if (!isNewRecplan && !isNewDay) {
        promptMessage("正在增加节点...请稍后", null, false);
    }
    isNewDay = false;
    var recommendPlanTripId = -1;
    var $day = $("#left_day_" + day_index + "_trip_list");
    // 计算已有的行程节点数
    var trip_index = Number($day.find("textarea").length);
    $.ajax({
        url: '/lvxbang/recommendPlan/addOneTrip.jhtml',
        type: 'post',
        dataType: 'json',
        data: {
            recplanId: $("#recplanId").val(),
            recommendPlanDayId: day_id,
        },
        beforeSend: function(XMLHttpRequest) {
        },
        success: function(result) {
            recommendPlanTripId = result.recommendPlanTripId;
            var data = {
                r_id: $("#recplanId").val(),
                day_id: day_id,
                trip_id: recommendPlanTripId,
                day_index: day_index,
                trip_index: trip_index,
                sort: trip_index + 1
            };
            var left_recommendPlan_trip_item = template("left_edit_recommendPlan_trip_item", data);
            $day.append(left_recommendPlan_trip_item);
            // 加载当天某个行程节点的编辑器
            UM.getEditor('left_day_' + day_index + '_trip_' + trip_index + '_exa');
            //绑定封面上传组件
            regCoverUploader();
            closeMsgBox();
            // 初始化节点图片上传
            regTripImgUploader($("#recplanId").val(), day_id, recommendPlanTripId, day_index, trip_index);
            // 滚动至节点 (新增天带起的节点增加不滚动!)
            if (!isNewDay) {
                var trip_top = $("#day_" + day_index + "_trip_" + trip_index ).offset().top - 55;
            }
            $(window).scrollTop(trip_top);
            if (isNewRecplan) {
                isNewRecplan = false;
                // 跳转至持久化的页面 !
                promptMessage("创建成功!", 2000, true);
                closeMsgBox({
                    timeout: 2000,
                    afterclose: redirectToNew()
                });
            }
        },
        error: function(result) {
            promptWarn("网络异常! 请稍候重试!");
            $a.attr("isuse", 0);
        }
    });
}

// TODO 刷新右侧目录 方法过时, 需要研究新方法//2015.01.15
//function refreshDayInfos() {
//    if ($("#recplanId") > 0) {
//        var days = Number($("#days").val());
//        for (var i = 1; i <= days; i++) {
//            $("#")
//        }
//    } else {
//
//    }
//}

function previewRecommendPlan() {
    var valid = validateData();
    if (!valid) {
        return;
    }
    valid = buildDetailData();
    if (!valid) {
        return;
    }
    promptMessage("正在生成预览...", null, false);
    $("#status").val("1");
    submitRecommendPlan({
        onReturn: function(result) {
            if (result) {
                closeMsgBox({
                    timeout: 2000,
                    afterclose: toPreview()
                });
            } else {
                changeMsg("游记保存失败!无法预览!");
                closeMsgBox({
                    timeout: 2000
                });
            }
        }
    });
}

/**
 * 提交保存行程表单
 */
function saveRecommendPlan() {
    var valid = validateData();
    if (!valid) {
        return;
    }
    valid = buildDetailData();
    if (!valid) {
        return;
    }
    promptMessage("发布成功...", null, false);
    $("#status").val("2");
    submitRecommendPlan({
        onReturn: function(result) {
            if (result) {
                changeMsg("保存成功!");
                closeMsgBox({
                    timeout: 2000,
                    afterclose: doAfterSubmit()
                });
            } else {
                changeMsg("游记保存失败!");
                closeMsgBox({
                    timeout: 2000,
                });
            }
        }
    });
}

/**
 * 提交保存草稿游记
 */
function saveDraftRecommendPlan() {
    var valid = validateData();;
    if (!valid) {
        return;
    }
    valid = buildDetailData();
    if (!valid) {
        return;
    }
    promptMessage("正在生成预览...", null, false);
    $("#status").val("1");
    // 构建详细数据
    //buildDetailData();
    submitRecommendPlan({
        onReturn: function(result) {
            if (result) {
                changeMsg("保存成功!");
                toPreview();
                closeMsgBox({
                    timeout: 2000,
                    afterclose: doAfterSubmit()
                });
            } else {
                changeMsg("游记保存失败!");
                closeMsgBox({
                    timeout: 2000
                });
            }
        }
    });
}
function doAfterSubmit() {
    return window.location.reload(true);
}
function redirectToNew() {
    return window.location.href = '/lvxbang/recommendPlan/edit.jhtml?recplanId=' + $("#recplanId").val();
}
function toPreview() {
    return window.location.href = '/lvxbang/recommendPlan/preview.jhtml?recplanId=' + $("#recplanId").val();
}
/**
 * 游记数据异步提交
 */
function submitRecommendPlan(options) {
    var form_data_array = $("#recommend_plan_form").serializeArray();
    var data = {};
    $.each(form_data_array, function(i, item) {
        if (item.value.indexOf("游记写的屌，旅行才完美...") > 0) {
            data[item.name] = null;
        } else {
            data[item.name] = item.value;
        }
    });
    //window.console.log(data);
    //return;
    $.ajax({
        url: '/lvxbang/recommendPlan/doEdit.jhtml',
        type: 'post',
        dataType: 'json',
        data: data,
        success: function (result) {
            options.onReturn(true);
        },
        error: function (result) {
            options.onReturn(false);
        }
    });
}

/**
 * 校验数据
 */
function validateData() {
    // TODO 待增加一些数据校验
    var isVerify = false;
    var title = $("#planName").val();
    var startTime = $("#startTime").val();
    var day_list = $("#left_day_list").children("div[id^='day_']");
    if (isNull(title)) {
        promptWarn("请填写游记标题!", 1000, true);
        var title_top = $("#planName").offset().top - 30;
        $(".SetTravels_c_p").css("border", "2px solid #f55");
        $(window).scrollTop(title_top);
        $("#planName").focus();
        return false;
    }
    if (isNull(startTime)) {
        promptWarn("请选择出发时间!", 1000, true);
        var title_top = $("#planName").offset().top - 30;
        $("li.time").css("border", "2px solid #f55");
        $(window).scrollTop(title_top);
        return false;
    }
    if (Number(day_list.length) <= 0) {
        promptWarn("请至少填写一天行程!", 1000, true);
        var add_day_top = $("#add_day").offset().top - 30;
        $("#add_day").css("border", "2px solid #f55");
        $(window).scrollTop(add_day_top);
        return false;
    }
    return true;
}

function commonEvent() {
    $("#planName").bind("blur input propertychange", function(event) {
        var title = $("#planName").val();
        if (event.type == "blur") {
            if (!isNull(title)) {
                $(".SetTravels_c_p").css("border", "");
            } else {
                $(".SetTravels_c_p").css("border", "2px solid #f55");
            }
        }
        if (event.type == "input" || event.type == "propertychange") {
            if (!isNull(title)) {
                $(".SetTravels_c_p").css("border", "");
            } else {
                $(".SetTravels_c_p").css("border", "2px solid #f55");
            }
        }
    });
    $("#startTime").bind("blur input propertychange", function(event) {
        var startTime = $("#startTime").val();
        if (event.type == "blur") {
            if (!isNull(startTime)) {
                $("li.time").css("border", "");
            } else {
                $("li.time").css("border", "2px solid #f55");
            }
        }
        if (event.type == "input" || event.type == "propertychange") {
            if (!isNull(startTime)) {
                $("li.time").css("border", "");
            } else {
                $("li.time").css("border", "2px solid #f55");
            }
        }
    });
    $("#left_day_list").delegate("input.validate_scenicName", "blur input propertychange", function(event) {
        var scenicName = $(this).val();
        if (event.type == "focusout") {
            if (!isNull(scenicName)) {
                $(this).parent("p").css("border", "3px solid #efeeee");
            } else {
                $(this).parent("p").css("border", "3px solid #f55");
            }
        }
        if (event.type == "input" || event.type == "propertychange") {
            if (!isNull(scenicName)) {
                $(this).parent("p").css("border", "3px solid #efeeee");
            } else {
                $(this).parent("p").css("border", "3px solid #f55");
            }
        }
    });
}
function startTimeEvent() {
    $("li.time").css("border", "");
}
/**
 * 生成更多游记详情数据
 */
// cityId,cityIds,scenics, days
function buildDetailData() {
    var valid = true;
    // 所有游览节点数目
    var total_scenics = 0;
    // 当天游览节点数目
    var day_scenics = 0;
    // 主要cityId
    var cityId = 0;
    // 所有经过的cityIds
    var cityIds = "";
    // 当天经过的cityIds
    var day_cityIds = "";
    var day_list = $("#left_day_list").children("div[id^='day_']");
    // 校验身份标签
    var label_item = $("#label_items").children('input');
    if (Number(label_item.length) <= 0) {
        promptWarn("请至少选择一个身份!", 1000, true);
        valid = false;
    }
    // 获得总天数
    $("#days").val(Number(day_list.length));
    $.each(day_list, function (day_index, day) {
        var trip_list = $("#left_day_" + day_index + "_trip_list").children("div[id^='day_" + day_index + "_trip']");
        // 获得当天游览总节点数目
        day_scenics = Number(trip_list.length);
        $("#left_day_" + day_index + "_scenics").val(day_scenics);
        // 累加总计节点数目
        total_scenics += day_scenics;
        day_scenics = 0;
        $.each(trip_list, function (trip_index, trip) {
            var $scenicNameInput = $("#left_day_" + day_index + "_trip_" + trip_index + "_scenicName");
            var $tripTypeInput = $("#left_day_" + day_index + "_trip_" + trip_index + "_tripType");
            var $tripTypeDiv = $("#day_" + day_index + "_trip_" + trip_index + "_typeDiv");
            var scenicName = $scenicNameInput.val();
            var tripType = $tripTypeInput.val();
            var scenicNameTop = $scenicNameInput.offset().top - 90;
            if (isNull(tripType)) {
                promptWarn("请选择类型", 1000, true);
                $tripTypeDiv.css("border", "3px solid #f55")
                $(window).scrollTop(scenicNameTop);
                valid = false;
            }
            if (isNull(scenicName)) {
                promptWarn("请输入名称", 1000, true);
                $scenicNameInput.parent("p").css("border", "3px solid #f55");
                $scenicNameInput.focus();
                $(window).scrollTop(scenicNameTop);
                valid = false;
            }
            // 更新节点排序值
            $("#left_day_" + day_index + "_trip_" + trip_index + "_sort").val(trip_index + 1);
            // 拼接cityIds
            var trip_cityId = $("#left_day_" + day_index + "_trip_" + trip_index + "_cityCode").val();
            if (trip_cityId != null && trip_cityId != "") {
                if (cityIds.indexOf(trip_cityId) < 0) {
                    cityIds += (trip_cityId + ",");
                }
                if (day_cityIds.indexOf(trip_cityId) < 0) {
                    day_cityIds += (trip_cityId + ",");
                }
            }
        });
        $("#left_day_" + day_index + "_citys").val(day_cityIds);
        day_cityIds = "";
    });
    $("#scenics").val(total_scenics);
    $("#cityIds").val(cityIds);
    $("#cityId").val(cityIds.split(",")[0]);
    return valid;
}