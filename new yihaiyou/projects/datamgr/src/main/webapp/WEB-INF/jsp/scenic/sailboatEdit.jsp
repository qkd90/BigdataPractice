<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@include file="../common/common141.jsp" %>
    <title><s:if test="scenicInfo.id != null">${scenicInfo.name}</s:if><s:else>新增港口</s:else></title>
    <script src="/js/xheditor-1.2.2/xheditor-1.2.2.min.js" type="text/javascript"></script>
    <script src="/js/xheditor-1.2.2/xheditor_lang/zh-cn.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/js/diyUpload/css/webuploader.css">
    <link rel="stylesheet" type="text/css" href="/js/diyUpload/css/diyUpload.css">
    <script type="text/javascript" src="/js/diyUpload/js/webuploader.html5only.min.js"></script>
    <script type="text/javascript" src="/js/diyUpload/js/diyUpload.js"></script>
    <script type="text/javascript" src="/js/encodeToGb2312.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=R9424rkP6oyCzex5FuLa7XIw"></script>
    <script>
        $(pageInit);
        function pageInit() {
            //富文本编辑器
            $('#introduction_text').xheditor({
                tools: 'Blocktag,Fontface,FontSize,|,Bold,Italic,Underline,|,FontColor,BackColor,' +
                '|,Removeformat,List,Outdent,Indent,|,Link,Hr,Emot,Table,|,Img,|,Source,WYSIWYG,Fullscreen,Preview',
                skin: 'default',
                width: 600,
                height: 180
            });
            //折叠默认展开
            $('#ticketDiv').css("display", "block");
            $('#contactDiv').css("display", "block");
            $('#picsDiv').css("display", "block");
            $('#submitDiv').css("display", "block");
            //交通枢纽信息
            <%--<c:if test="${scenicInfo.isStation ==1}">--%>
            <%--$('#isStation').prop('value', 1); // 初始化--%>
            <%--</c:if>--%>
            $(document).ready(function () {
                $('#stationType').change(function () {
                    var p1 = $(this).combobox('getValue');
                    if (p1 == 0) {
                        $('#isStation').prop('value', 0);
                    } else {
                        $('#isStation').prop('value', 1);
                    }
                })
            });
            $('#gp').val(1);
            <c:if test="${scenicInfo.id > 0}">
            loadPics($('#gp').val());
            </c:if>

            $('#imagePanel').diyUpload({
                url: "/scenic/scenicInfo/uploadImg.jhtml?sid=${scenicInfo.id}&photoType=scenic",
                success: function (data, $fileBox) {
                    console.log(data);
                    //上传成功
//                    $('#imagePanel');
//                    $('#galleryId').val(data.data.galleryId);
                    var address = BizConstants.QINIU_DOMAIN + data.data.imgUrl;
                    $fileBox.remove();
                    showImage($('#imagePanel'), address, data.data.id);
                    $('#fileBox_' + data.data.id).append('<div class="diyCover"></div><div class="coverSuccess"></div>');
                    $('#fileBox_' + data.data.id).find('.diyCover').click(function () {//增加事件
                        $('#coverParent').html('<div id="coverBox"></div>');
                        $('#coverLarge').prop('value', data.data.imgUrl);
                        $('#imageBox').find('.coverSuccess').hide();
                        $('#fileBox_' + data.data.id).find('.coverSuccess').show();
                        showImage($('#coverBox'), address, data.data.id);
                    });
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
                fileSizeLimit: 500000 * 1024,
                fileSingleSizeLimit: 50000 * 1024,
                accept: {}
            });
            initProvince();
        }
        function loadPics(gp) {
//            var gi = $('#galleryId').val();
//            if (gi == undefined) gi = '';
            $.get("/scenic/scenicInfo/scenicGalleryList.jhtml?rows=8&scenicIds=${scenicInfo.id}&type=scenic&page=" + $('#gp').val(), function (result) {
                if (result.rows.length == 0) {
                    $('#webuploader-more').html('已无更多');
                    $('#webuploader-more').attr('onclick', '');
                    $('#webuploader-more').css("background-color", "grey");
                    return;
                }
//                var galleryId = result.rows[0].galleryId;
//                $('#galleryId').prop('value', galleryId);
                $.each(result.rows, function (i, n) {
                    var id = n.id;
                    var address = BizConstants.QINIU_DOMAIN + n.imgUrl;
                    showImage($('#imagePanel'), address, id);
                    $('#fileBox_' + id).append('<div class="diyCover"></div><div class="coverSuccess"></div>');
                    $('#fileBox_' + id).find('.diyCover').click(function () {//增加事件
                        $('#coverParent').html('<div id="coverBox"></div>');
                        $('#coverLarge').prop('value', n.imgUrl);
                        $('#imageBox').find('.coverSuccess').hide();
                        $('#fileBox_' + id).find('.coverSuccess').show();
                        showImage($('#coverBox'), address, id);
                    });

                    $('#fileBox_' + id).find('.diyCancel').click(function () {//增加事件
                        var removed = '<input type="hidden" name="removedImgs" value="' + id + '"> ';
                        $('#imageBox').append(removed);

                    });
                });
                if (result.rows.length == 8) {
                    $('#webuploader-more').html('已无更多');
                    $('#webuploader-more').attr('onclick', '');
                    $('#webuploader-more').css("background-color", "grey");

                }
            });
            $('#gp').prop('value', parseInt(gp) + 1);
        }
        var regionCodeInit = '${scenicInfo.city.cityCode}';
        function initProvince() {
            $('#addr_province').combobox({
                url: '/sys/area/getProvinces.jhtml',
                valueField: 'cityCode',
                textField: 'name',
                onSelect: function (sel) {
                    if (sel == undefined)return;
                    $('#regionCode').prop('value', sel.cityCode);
                    initCity(sel);
                }
                <c:if test="${scenicInfo.city.cityCode != null}">,
                onLoadSuccess: function () {
                    var regionCode = '${scenicInfo.city.cityCode}';
                    $('#addr_province').combobox('select', regionCode.substring(0, 2) + '0000');
                }
                </c:if>
            });
        }
        function initCity(val) {
            var regionCode = '';
            if (val != undefined && val != 'undefined')
                regionCode = val.cityCode;
            $('#addr_city').combobox({
                url: '/sys/area/getCities.jhtml?provinceId=' + regionCode,
                valueField: 'cityCode',
                textField: 'name',
                onSelect: function (sel) {
                    if (sel == undefined)return;
                    $('#regionCode').prop('value', sel.cityCode);
                    initArea(sel);
                }
                <c:if test="${scenicInfo.city.cityCode != null && scenicInfo.city.cityCode % 10000 >0}">,
                onLoadSuccess: function () {
                    if ($('#regionCode').val() == (regionCodeInit.substring(0, 2) + '0000')) {
                        $('#addr_city').combobox('select', regionCodeInit.substring(0, 4) + '00');
                    }
                }
                </c:if>
            });
        }
        function initArea(val) {
            var regionCode = '';
            if (val != undefined && val != 'undefined')
                regionCode = val.cityCode;
            $('#addr_area').combobox({
                url: '/sys/area/getAreas.jhtml?cityId=' + regionCode,
                valueField: 'cityCode',
                textField: 'name',
                onSelect: function (sel) {
                    $('#regionCode').prop('value', sel.cityCode);
                }
                <c:if test="${scenicInfo.city.cityCode != null && scenicInfo.city.cityCode % 100 >0}">,
                onLoadSuccess: function () {
                    var regionCode = '${scenicInfo.city.cityCode}';
                    if ($('#regionCode').val() == (regionCode.substring(0, 4) + '00')) {
                        $('#addr_area').combobox('select', regionCode);
                    }
                }
                </c:if>
            });
        }

        function getAdviceHours() {
            var unit = $('#advice_unit').val();
            if (unit == '天') {
                $('#adviceTime').numberspinner('setValue', $('#adviceTime').val() * 8 * 60);//一天8个小时
                $('#advice_unit').val('分钟');
            } else if (unit == '小时') {
                $('#adviceTime').numberspinner('setValue', $('#adviceTime').val() * 60);
                $('#advice_unit').val('分钟');
            }
        }
        function getIntro() {
            $("#intro").val($("#introduction_text").val());
        }

        //正式保存上架
        function submitScenic() {
            $.messager.progress({
                title: '港口数据管理',
                msg: '正在保存港口数数据, 请稍候...'
            });
            getAdviceHours();
            getIntro();
            $("#status").val('1');
            $('#scenic_edit_form').form('submit', {
                url: '/scenic/scenicInfo/saveScenic.jhtml',
                onSubmit: function () {
                    var isValid = $(this).form('validate')
                    if (!isValid) {
                        $.messager.progress('close');
                        $.messager.alert('港口数据管理', "请完善港口数据!", 'error');
                    }
                    return isValid;	// 返回false终止表单提交
                },
                success: function (data) {
                    $.messager.progress('close');
                    $.messager.alert('提示', '操作成功！', 'info', function() {  // 为适应新旧首页的写法
                        if (window.parent.$("#tabs") && window.parent.$("#tabs").length > 0) {
                            window.parent.$("#tabs").tabs("close", "新增港口");
                        } else {
                            window.close();
                        }
                    });
//                    BgmgrUtil.backCall(data, function () {
//                        $.messager.alert('提示', '操作成功！', 'info', null, null);
//                    }, null);
                }, onLoadError: function (data) {
                    $.messager.progress('close');
                    console.info('load error' + data);
                },
                error: function () {
                    $.messager.progress('close');
                    error.apply(this, arguments);
                    $.messager.alert('提示', '失败！', 'info', null, null);
                }
            });
        }
        //保存草稿
        function save() {
            $.messager.progress({
                title: '港口数据管理',
                msg: '正在保存港口数数据, 请稍候...'
            });
            getAdviceHours();
            getIntro();
            $("#status").val('-1');
            $('#scenic_edit_form').form('submit', {
                url: '/scenic/scenicInfo/saveScenic.jhtml',
                success: function (data) {
                    $.messager.progress('close');
                    $.messager.alert('提示', '操作成功！', 'info', function() {  // 为适应新旧首页的写法
                        if (window.parent.$("#tabs") && window.parent.$("#tabs").length > 0) {
                            window.parent.$("#tabs").tabs("close", "新增港口");
                        } else {
                            window.close();
                        }
                    });
//                    BgmgrUtil.backCall(data, function () {
//                        $.messager.alert('提示', '操作成功！', 'info', null, null);
//                    }, null);
                },
                error: function () {
                    $.messager.progress('close');
                    error.apply(this, arguments);
                    $.messager.alert('提示', '失败！', 'info', null, null);
                }
            });
        }
        var scenicLoader = function (param, success, error) {
            var q = param.q || '';
            q = encodeURI(q);
            if (q.length <= 1) {
                return false
            }
            $.ajax({
                url: '/scenic/scenicInfo/listName.jhtml',
                dataType: 'json',
                data: {
                    rows: 10,
                    name: q,
                    cityCode:$('#regionCode').val().substring(0,4)
                },
                success: function (msg) {
                    var data = msg.resultList;
                    var items = $.map(data.data, function (item) {
                        return {
                            scenicIds: item.scenicId,
                            scenicName: item.scenicName + (item.fatherName ? '(' + item.fatherName + ')' : '')+ (item.scenicId ? '(' + item.scenicId + ')' : '')
                        };
                    });
                    success(items);
                },
                error: function () {
                    error.apply(this, arguments);
                }
            });
        };

        function selectScenic(data) {
            $('#fatherId').val(data.scenicIds);
            var name = data.scenicName;
            if (name.indexOf('(') > -1) {
                name = name.substr(0, (name.indexOf('(')));
            }
            $('#fatherName').val(name);
        }
    </script>
    <style>
        #imageBox {
            margin: 3px 3px;
            width: 800px;
            min-height: 400px;
            background: #e4f5ff
        }
    </style>
</head>
<body style="overflow: auto">
<form id="scenic_edit_form" action="/scenic/scenicInfo/saveScenic.jhtml" method="post" enctype="multipart/form-data">
    <div class="easyui-accordion" data-options="multiple:true" style="width:100%;">
        <div title="基本信息"  selected="selected" style="padding:10px;">
            <div style="width:300px;margin-top: 10px;float:right; display: none;">
                <span>
                    <a href='javascript:void(0)'
                       onclick='openUrl("http://you.ctrip.com/sight/xian7/${scenicInfo.scenicOther.ctripId}.html")'
                            >[携程]</a>
                </span>
                <span>
                    <a href='javascript:void(0)'
                       onclick='openUrl("http://www.mafengwo.cn/group/s.php?q=${scenicInfo.name}&t=poi")'
                            >[马蜂窝]</a>
                </span>
                <span>
                    <a href='javascript:void(0)'
                       onclick='openUrlWithGBK("http://www.cncn.com/jingdian_so.php?q=","${scenicInfo.name}")'
                            >[欣欣]</a>
                </span>
                <span>
                    <a href='javascript:void(0)'
                       onclick='openUrl(" http://www.ly.com/scenery/scenerysearchlist_0_0_${scenicInfo.name}_0_0_0_0_0_0_0.html")'
                            >[同程]</a>
                </span>
                <span>
                    <a href='javascript:void(0)'
                       onclick='openUrl("http://piao.qunar.com/ticket/list.htm?keyword=${scenicInfo.name}&region=&from=mpl_search_suggest")'
                            >[去哪儿]</a>
                </span>
                <br/>
                <span>
                    <a href='javascript:void(0)'
                       onclick='openUrlWithUTF8("http://jingdian.landtu.com/search/","${scenicInfo.name}",1)'
                            >[蓝途网]</a>
                </span>
                <span>
                    <a href='javascript:void(0)'
                       onclick='openUrl("https://www.baidu.com/s?wd=${scenicInfo.name}+site:tuniu.com")'
                            >[途牛网]</a>
                </span>
                <span>
                   <a href='javascript:void(0)'
                      onclick='openUrl("http://www.xialv.com/search?keyword=${scenicInfo.name}")'
                           >[侠侣网]</a>
                </span>
                <span>
                   <a href='javascript:void(0)'
                      onclick='openUrl(" http://www.lvmama.com/lvyou/search/?t=jingdian&q=${scenicInfo.name}")'
                           >[驴妈妈]</a>
                </span>
                 <span>
                   <a href='javascript:void(0)'
                      onclick='openUrl("http://trip.elong.com/so/all/${scenicInfo.name}")'
                           >[艺龙网]</a>
                </span>
                <br/>
                <span>
                   <a href='javascript:void(0)'
                      onclick='openUrl("http://baike.baidu.com/item/${scenicInfo.name}")'
                           >[百度百科]</a>
                </span>
                <span>
                    <a href='javascript:void(0)'
                       onclick='openUrlWithUTF8("http://www.baike.com/wiki/","${scenicInfo.name}",2)'
                            >[互动百科]</a>
                </span>
               <span>
                    <a href='javascript:void(0)'
                       onclick='openUrl("https://www.baidu.com/s?wd=${scenicInfo.name}+site:dianping.com")'
                            >[大众点评]</a>
                </span>
                <%--<table id="relation">--%>
                <%--</table>--%>
            </div>
            <div>
                <span>港口名称:</span>
                <span>
                    <input class="easyui-textbox" type="text" name="scenicInfo.name" value="${scenicInfo.name}"
                           data-options="required:true">
                </span>
                <span>父景点:</span>
                <input type="hidden" id="fatherName" name="scenicInfo.father.name" value=".${scenicInfo.father.name}"/>
                <input type="hidden" id="fatherId" name="scenicInfo.father.id" value="${scenicInfo.father.id}"/>
                <span>
                  <input type="text" id="fatherSelect" value="${scenicInfo.father.name}" class="easyui-combobox"
                         data-options="loader: scenicLoader,mode: 'remote',valueField: 'scenicIds',textField: 'scenicName',onSelect:selectScenic">
                    <script>
                        //根据景点id 找到景点名称
                        var scenicIds = $('#fatherId').val();
                        if (scenicIds > 0)
                            $.ajax({
                                url: '/scenic/scenicInfo/listName.jhtml',
                                dataType: 'json',
                                method: 'POST',
                                data: {
                                    rows: 1,
                                    id: scenicIds
                                },
                                success: function (msg) {

                                    var data = msg.resultList.data[0];
                                    $('#fatherSelect').val(data.scenicName);
                                    $('#fatherName').val(data.scenicName);
                                    $('#fatherId').val(data.scenicIds);
                                },
                                error: function () {
                                    alert('找不到已存景区');
                                }
                            });
                    </script>
                </span>
                <a id="cancelFather" href="javascript:void(0)"
                   class="easyui-linkbutton" onClick="cancelFather()">取消关联</a>
                <script type="text/javascript">
                    function cancelFather() {
                        $('#fatherSelect').combobox("clear");
                        $('#fatherName').val(null);
                        $('#fatherId').val(null);
                    }
                </script>
            </div>
            <div>
                <span>交通信息:</span>
                <span>
                    <input type="checkbox" style="vertical-align: middle;"
                           <c:if test="${scenicInfo.hasTaxi == true}">checked="checked"</c:if>
                           onclick="var c = document.getElementById('hasTaxi');if (c.checked) c.value = true;else  c.value = false;"
                           value="${scenicInfo.hasTaxi}" id="hasTaxi" name="scenicInfo.hasTaxi" placeholder="打车" cn="打车">打车&nbsp;
                    <input type="checkbox" style="vertical-align: middle;"
                           <c:if test="${scenicInfo.hasBus == true}">checked="checked"</c:if>
                           onclick="var c = document.getElementById('hasBus'); if (c.checked) c.value = true;else  c.value = false;"
                           value="${scenicInfo.hasBus}" id="hasBus" name="scenicInfo.hasBus" placeholder="公交" cn="公交">公交&nbsp;
                    <input type="checkbox" style="vertical-align: middle;"
                           <c:if test="${scenicInfo.isThreeLevel == true}">checked="checked"</c:if>
                           onclick="var c = document.getElementById('isThreeLevel'); if (c.checked) c.value = true;else  c.value = false;"
                           value="${scenicInfo.isThreeLevel}" id="isThreeLevel" name="scenicInfo.isThreeLevel" placeholder="三级景区" cn="三级景区">三级景区
                </span>

            </div>

            <div>
                <p>简介:</p>
                 <textarea data-options="multiline:true" id="introduction_text" rows="13" cols="100" data-options="required:true">
                     ${scenicInfo.scenicOther.description}
                 </textarea>
                <input type="hidden" id="intro" name="scenicInfo.scenicOther.description"/>
            </div>

            <div>
                <span>时间描述:</span>
                <span>
                    <input class="easyui-textbox" type="text" name="scenicInfo.scenicOther.adviceTimeDesc" id="adviceTimeDesc"
                           value="${scenicInfo.scenicOther.adviceTimeDesc}"
                           data-options="required:true,prompt:'可填写时间范围...'">
                </span>
                <span>游玩时间(整数):</span>
                <span>
                <input class="easyui-numberspinner" type="text" name="scenicInfo.scenicOther.adviceTime" id="adviceTime"
                       value="<c:if test="${scenicInfo.scenicOther.adviceTime >0}">${scenicInfo.scenicOther.adviceTime}</c:if>"
                       data-options="min:0,required:true">
                        <select id="advice_unit">
                            <option value="天">天</option>
                            <option value="小时">小时</option>
                            <option value="分钟" selected="selected">分钟</option>
                        </select>
                        <script>
                            $(function () {
                                var num = $('#adviceTime').numberspinner('getValue');
                                if (num % 60 == 0 && num / 60 > 0) {
                                    if (num % 480 == 0 && num / 480 > 0) {//天. 一天以8小时计算
                                        $('#adviceTime').numberspinner('setValue', num / 480);
                                        $('#advice_unit').val('天');
                                    } else {//小时
                                        $('#adviceTime').numberspinner('setValue', num / 60);
                                        $('#advice_unit').val('小时');
                                    }
                                }
                            })
                        </script>
                </span>

            </div>

        </div>

        <div title="价格" id="ticketDiv"  style="padding:10px;">
            <tr cellpadding="10">
            <tr>
                <td>参考价:</td>
                <td>
                    <span style="color: gray">¥</span>
                    <input name="scenicInfo.price" id="price" value="${scenicInfo.price}"
                           class="easyui-numberspinner" data-options="min:0">
                    <span style="color: gray">起</span>
                </td>
                <td>价格描述:</td>
                <td>
                    <input id="ticket" name="scenicInfo.ticket" value="${scenicInfo.ticket}"
                           class="easyui-textbox" data-options="">
                </td>
            </tr>
            <%--<tr>--%>
            <%--<td>小帮价:</td>--%>
            <%--<td>--%>
            <%--<span style="color: gray">¥${scenicInfo.lowestPrice}</span>--%>
            <%--</td>--%>
            <%--<td>--%>
            <%--</td>--%>
            <%--</tr>--%>
            </table>
        </div>
        <div title="更多信息" id="contactDiv"  style="padding:10px;" width="100%">
            <div style="width:550px;margin-top: 10px;float:right">
                <div id="container" style="width: 550px;height: 200px;border: 1px solid gray;overflow:hidden;">
                </div>
                <a href="javascript:void(0)" style="width:150px;height:30px" class="easyui-linkbutton"

                   onclick="searchByAddress()">按地址获取经纬度</a>
            </div>
            <table cellpadding="10">
                <tr>

                    <td>电话:</td>
                    <td>
                        <input name="scenicInfo.scenicOther.telephone" value="${scenicInfo.scenicOther.telephone}" class="easyui-validatebox textbox" type="text" name="tel">
                    </td>
                    <td>评分:</td>
                    <td>
                        <input name="scenicInfo.score" value="${scenicInfo.score}" class="easyui-numberspinner"
                               data-options="min:0,max:100,required:true">
                    </td>
                </tr>
                <tr>
                    <td>区域:</td>
                    <td>
                        <select class="easyui-combobox" name="province" id="addr_province" style="width: 80px;">
                            <option value="请选择" selected="selected">请选择</option>
                        </select>
                        <select class="easyui-combobox" name="cityField" id="addr_city" style="width: 80px;">
                            <option value="请选择" selected="selected">请选择</option>
                        </select>
                        <select class="easyui-combobox" name="area" id="addr_area" style="width: 80px;">
                            <option value="请选择" selected="selected">请选择</option>

                        </select>
                    </td>
                    <td>地址:</td>
                    <td><input id="address" name="scenicInfo.scenicOther.address" value="${scenicInfo.scenicOther.address}"
                               class="easyui-textbox" type="text" data-options="required:true"></td>
                </tr>
                <tr>
                    <td>经度:</td>
                    <td>
                        <input id="lng" name="scenicInfo.scenicGeoinfo.baiduLng" class="easyui-textbox"
                               data-options="required:true,prompt:'可按地址获取...'"
                               type="text" value="${scenicInfo.scenicGeoinfo.baiduLng}"/>
                    </td>
                    <td>纬度:</td>
                    <td>
                        <input id="lat" name="scenicInfo.scenicGeoinfo.baiduLat" class="easyui-textbox"
                               data-options="required:true,prompt:'可按地址获取...'"
                               type="text" value="${scenicInfo.scenicGeoinfo.baiduLat}"/>
                    </td>

                </tr>
                <tr>
                    <td>开放时间:</td>
                    <td>
                        <input name="scenicInfo.scenicOther.openTime" value="${scenicInfo.scenicOther.openTime}" class="easyui-textbox"
                               data-options="multiline:true" style="width:200px;height:60px">
                    </td>
                    <td>交通指南:</td>
                    <td>
                        <input name="scenicInfo.scenicOther.trafficGuide" value="${scenicInfo.scenicOther.trafficGuide}" class="easyui-textbox"
                               data-options="multiline:true" style="width:200px;height:60px">
                    </td>
                </tr>
            </table>
        </div>
        <c:if test="${scenicInfo.id > 0 }">
        <div title="港口相册" id="picsDiv"  style="padding:10px;">
            <div style="width:400px;float: right">
                封面：
                <div id="coverParent">
                    <div id="coverBox"></div>
                </div>
                <script>
                    var id = ${scenicInfo.id};
                    var address = '${scenicInfo.cover}';
                    if (address.indexOf("http:") == -1)
                        address = BizConstants.QINIU_DOMAIN + address;
                    showImage($('#coverBox'), address, id);
                </script>
                <input type="hidden" id="coverLarge" name="scenicInfo.cover" value="${scenicInfo.cover}"/>
            </div>
            <div id="imageBox">
                    <%--<input type="hidden" id="galleryId" name="galleryId">--%>
                <input type="hidden" id="gp" name="galleryPage" value="1">
                <div id="imagePanel"></div>
            </div>
            <div class="webuploader-more" id="webuploader-more" onclick="loadPics($('#gp').val())">加载更多</div>
            <!--<div class="webuploader-more"
                     onclick="window.parent.addTab('景点相册', '/scenic/scenicInfo/gallery.jhtml?scenicIds=${scenicInfo.id}&type=scenic');">
                    相册管理
                </div>-->
            </c:if>

        </div>
        <div id="submitDiv" title="提交" style="text-align:right;padding:5px 10px">
            <input type="hidden" name="scenicInfo.id" value="${scenicInfo.id }"/>
            <input type="hidden" name="scenicInfo.level" value="${scenicInfo.level }"/>
            <input type="hidden" name="scenicInfo.star" value="${scenicInfo.star }"/>
            <input type="hidden" name="scenicInfo.isShow" value="${scenicInfo.isShow }"/>
            <input type="hidden" id="regionCode" name="scenicInfo.city.cityCode" value="${scenicInfo.city.cityCode}"/>
            <input type="hidden" id="deleteFlag" name="scenicInfo.deleteFlag" value="F"/>
            <input type="hidden" id="scenicType" name="scenicInfo.scenicType" value="sailboat"/>
            <input type="hidden" id="orderNum" name="scenicInfo.ranking" value="${scenicInfo.ranking>0?scenicInfo.ranking:9999}"/>
            <input type="hidden" id="status" name="scenicInfo.status" value="${scenicInfo.status>0?scenicInfo.status:0 }"/>
            <%--<input type="hidden" name="isModified" value="${scenicInfo.isModified>0? scenicInfo.isModified:0}"/>--%>
            <%--<input type="hidden" name="refId" value="${scenicInfo.refId}"/>--%>
            <%--<input type="hidden" name="lowestPrice" value="${scenicInfo.lowestPrice}"/>--%>
            <%--<input type="hidden" name="marketPrice" value="${scenicInfo.marketPrice}"/>--%>
            <!--<p>此处预览</p>-->
            <div style="padding:5px 0;">
                <p style="color:red;">管理员提交即时生效，请谨慎操作</p>
                <c:if test="${scenicInfo.id == null}">
                    <a href="#" style="width:120px;height:30px" class="easyui-linkbutton"
                       onclick="if(confirm('确定保存吗？')){save()}"
                            >保存草稿</a>
                </c:if>
                <a href="#" style="width:120px;height:30px" class="easyui-linkbutton"
                   onclick="if(confirm('确定提交吗？<c:if test="${scenicInfo.status != 1}">提交后将上架</c:if>')){submitScenic()}"
                        >提交</a>
                <c:if test="${scenicInfo.id == null}">
                    <!--<a href="#" style="width:120px;height:30px" class="easyui-linkbutton"

                    onclick="if(confirm('不保存并关闭本页？')){window.parent.$('#tabs').tabs('close', '新增景点');}"
                    >取消</a>-->
                </c:if>
                <c:if test="${scenicInfo.id > 0}">
                    <!--<a href="#" style="width:120px;height:30px" class="easyui-linkbutton"

                    onclick="if(confirm('不保存并关闭本页？')){window.parent.$('#tabs').tabs('close', '新增景点');}"
                    >取消</a>-->
                </c:if>
            </div>
        </div>
    </div>
</form>
</body>
<script type="text/javascript">
    var map = new BMap.Map("container");

    map.enableScrollWheelZoom();    //启用滚轮放大缩小，默认禁用
    map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
    //    map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
    map.addControl(new BMap.OverviewMapControl({isOpen: true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT}));   //右下角，打开

    var localSearch = new BMap.LocalSearch(map);
    localSearch.enableAutoViewport(); //允许自动调节窗体大小

    <c:if test="${scenicInfo.id > 0 }">
    //    searchByAddress();
    localSearch.setSearchCompleteCallback(function (searchResult) {
        var poi = searchResult.getPoi(0);
        map.centerAndZoom(poi.point, 13);
        var marker = new BMap.Marker(new BMap.Point(poi.point.lng, poi.point.lat));  // 创建标注，为要查询的地方对应的经纬度
        map.addOverlay(marker);
        var content = document.getElementById("address").value + "<br/><br/>经度：" + poi.point.lng + "<br/>纬度：" + poi.point.lat;
        var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>");
        marker.addEventListener("click", function () {
            this.openInfoWindow(infoWindow);
        });
        // marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
    });
    localSearch.search(document.getElementById("address").value);
    var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25), {
        offset: new BMap.Size(10, 25), // 指定定位位置
        imageOffset: new BMap.Size(0, 0 - 10 * 25) // 设置图片偏移
    });
    var marker = new BMap.Marker(new BMap.Point(${scenicInfo.scenicGeoinfo.baiduLng}, ${scenicInfo.scenicGeoinfo.baiduLng}));  // 创建标注，为要查询的地方对应的经纬度
    marker.setIcon(myIcon);
    map.addOverlay(marker);
    var content = "经度：${scenicInfo.scenicGeoinfo.baiduLng}<br/>纬度：${scenicInfo.scenicGeoinfo.baiduLat}";
    var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>");
    marker.addEventListener("click", function () {
        this.openInfoWindow(infoWindow);
    });
    </c:if>
    function searchByAddress() {
        map.clearOverlays();//清空原来的标注
        var keyword = document.getElementById("address").value;
        if (keyword == undefined || keyword == '') {
            return;
        }
        localSearch.setSearchCompleteCallback(function (searchResult) {
            var poi = searchResult.getPoi(0);
            $('#lng').textbox('setValue', poi.point.lng);
            $('#lat').textbox('setValue', poi.point.lat);
            map.centerAndZoom(poi.point, 13);
            var marker = new BMap.Marker(new BMap.Point(poi.point.lng, poi.point.lat));  // 创建标注，为要查询的地方对应的经纬度
            map.addOverlay(marker);
            var content = document.getElementById("address").value + "<br/><br/>经度：" + poi.point.lng + "<br/>纬度：" + poi.point.lat;
            var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>");
            marker.addEventListener("click", function () {
                this.openInfoWindow(infoWindow);
            });
            // marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
        });
        localSearch.search(keyword);
    }
    function openUrl(url) {
        if (url.indexOf('cncn') != -1) {
            window.open(url);
        } else {
            window.parent.addTab('港口详情', url);
        }
    }
</script>
<%--<c:if test="${scenicInfo.id > 0 }">--%>
<%--<script>--%>
<%--&lt;%&ndash;// 页面加载后相关设置项&ndash;%&gt;--%>
<%--&lt;%&ndash;$(function () {&ndash;%&gt;--%>
<%--&lt;%&ndash;// 构建表格&ndash;%&gt;--%>
<%--&lt;%&ndash;$('#relation').datagrid({&ndash;%&gt;--%>
<%--&lt;%&ndash;title: "相关网站景点列表",&ndash;%&gt;--%>
<%--&lt;%&ndash;thread: '',&ndash;%&gt;--%>
<%--&lt;%&ndash;url: '/scenic/scenicRelation/listRelationWithOutGroup.jhtml',&ndash;%&gt;--%>
<%--&lt;%&ndash;border: true,&ndash;%&gt;--%>
<%--&lt;%&ndash;singleSelect: true,&ndash;%&gt;--%>
<%--&lt;%&ndash;columns: [[{&ndash;%&gt;--%>
<%--&lt;%&ndash;field: 'source',&ndash;%&gt;--%>
<%--&lt;%&ndash;title: '站点',&ndash;%&gt;--%>
<%--&lt;%&ndash;align: "center",&ndash;%&gt;--%>
<%--&lt;%&ndash;width: 80&ndash;%&gt;--%>
<%--&lt;%&ndash;}, {&ndash;%&gt;--%>
<%--&lt;%&ndash;field: 'sourceName',&ndash;%&gt;--%>
<%--&lt;%&ndash;title: '名称',&ndash;%&gt;--%>
<%--&lt;%&ndash;align: "center",&ndash;%&gt;--%>
<%--&lt;%&ndash;formatter: linkFormatter,&ndash;%&gt;--%>
<%--&lt;%&ndash;width: 210&ndash;%&gt;--%>
<%--&lt;%&ndash;}&ndash;%&gt;--%>
<%--&lt;%&ndash;]],&ndash;%&gt;--%>
<%--&lt;%&ndash;onBeforeLoad: function (data) {   // 查询参数&ndash;%&gt;--%>
<%--&lt;%&ndash;data.id = '${scenicInfo.id}';&ndash;%&gt;--%>
<%--&lt;%&ndash;}&ndash;%&gt;--%>
<%--&lt;%&ndash;});&ndash;%&gt;--%>
<%--&lt;%&ndash;});&ndash;%&gt;--%>
<%--function linkFormatter(value, rowData) {--%>
<%--var btnEdit = "<a href='javascript:void(0)' onclick='openUrl(\"" +--%>
<%--(rowData.sourceUrl != undefined ? rowData.sourceUrl.trim() : "")--%>
<%--+ "\")' style='color:blue;text-decoration: underline;'" +--%>
<%--" '>" + rowData.sourceName + "</a>";--%>
<%--return btnEdit;--%>
<%--}--%>
<%--function openUrl(url) {--%>
<%--if (url.indexOf('cncn') != -1) {--%>
<%--window.open(url);--%>
<%--} else {--%>
<%--window.parent.addTab('景点详情', url);--%>
<%--}--%>
<%--}--%>
<%--function openUrlWithGBK(url, name) {--%>
<%--name = encodeToGb2312(name);--%>
<%--if (url.indexOf('cncn') != -1) {--%>
<%--window.open(url + name);--%>
<%--} else {--%>
<%--window.parent.addTab('景点详情', url + name);--%>
<%--}--%>
<%--}--%>
<%--function openUrlWithUTF8(url, name, encodeCount) {--%>
<%--if (encodeCount == 1) {--%>
<%--name = encodeURI(name);--%>
<%--} else {--%>
<%--name = encodeURI(encodeURI(name));--%>
<%--}--%>
<%--if (url.indexOf('cncn') != -1) {--%>
<%--window.open(url + name);--%>
<%--} else {--%>
<%--window.parent.addTab('景点详情', url + name);--%>
<%--}--%>
<%--}--%>
<%--</script>--%>
<%--</c:if>--%>
</html>