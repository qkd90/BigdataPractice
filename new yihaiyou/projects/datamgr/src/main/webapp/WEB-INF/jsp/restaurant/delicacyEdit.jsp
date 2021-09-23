<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>管理后台</title>
    <%@include file="../common/common141.jsp" %>
    <%--<script src="${ctxPath}/static/xheditor-1.2.2/xheditor-1.2.2.min.js" type="text/javascript"></script>--%>
    <%--<script src="${ctxPath}/static/xheditor-1.2.2/xheditor_lang/zh-cn.js" type="text/javascript"></script>--%>
    <link rel="stylesheet" type="text/css" href="/js/diyUpload/css/webuploader.css">
    <link rel="stylesheet" type="text/css" href="/js/diyUpload/css/diyUpload.css">
    <script type="text/javascript" src="/js/diyUpload/js/webuploader.html5only.min.js"></script>
    <script type="text/javascript" src="/js/diyUpload/js/diyUpload.js"></script>
    <script>
        $(pageInit);
        function pageInit() {
//            //富文本编辑器
//            $('#intro').xheditor({
//                tools: 'Blocktag,Fontface,FontSize,|,Bold,Italic,Underline,|,FontColor,BackColor,' +
//                '|,Removeformat,List,Outdent,Indent,|,Link,Hr,Emot,Table,|,Img,|,Source,WYSIWYG,Fullscreen,Preview',
//                skin: 'default'
//            });
            $('#picsDiv').css("display", "block");
            $('#submitDiv').css("display", "block");
            initProvince();
            $('#imagePanel').diyUpload({
                url: "/restaurant/delicacy/upload.jhtml?foodName=" + $('#foodName').val(),
                success: function (data) {
                    console.info(data);
                    //上传成功
                    $('#foodPicture').val(data.data);
                },
                error: function (err) {
                    console.info('error:'+err);
                },
                buttonText: '上传',
                chunked: true,
                // 分片大小
                chunkSize: 512 * 1024,
                //最大上传的文件数量, 总文件大小,单个文件大小(单位字节);
                fileNumLimit: 1,
                fileSizeLimit: 500000 * 1024,
                fileSingleSizeLimit: 50000 * 1024,
                accept: {}
            });
        }
        var cityId = '${delicacy.city.cityCode}';
        function initCity(val) {
            var selProvince = '';
            if (val != undefined && val != 'undefined')
                selProvince = val.cityCode;
            $('#addr_city').combobox({
                url: '/sys/area/getCities.jhtml?provinceId=' + selProvince,
                valueField: 'cityCode',
                textField: 'name',
                onSelect: function (sel) {
                    if (sel == undefined)return;
                    $('#cityId').prop('value', sel.cityCode);
                }
                <c:if test="${delicacy.city.cityCode >0 && delicacy.city.cityCode % 10000 > 0}">,
                    onLoadSuccess: function () {
                        if ($('#cityId').val() == (cityId.substring(0, 2) + '0000')) {
                            $('#addr_city').combobox('select', cityId.substring(0, 4) + '00');
                        }
                    }
                </c:if>
            });
        }


        function initProvince() {
            $('#addr_province').combobox({
                url: '/sys/area/getProvinces.jhtml',
                valueField: 'cityCode',
                textField: 'name',
                onSelect: function (sel) {
                    if (sel == undefined)return;
                    $('#cityId').prop('value', sel.cityCode);
                    initCity(sel);
                }
                <c:if test="${delicacy.city.cityCode >0}">,
                    onLoadSuccess: function () {
                        var cityId = '${delicacy.city.cityCode}';
                        $('#addr_province').combobox('select', cityId.substring(0, 2) + '0000');
                    }
                </c:if>
            });
        }
        function getIntro() {
            $("#introduction").val($("#intro").val());
        }
        function submitDelicacy() {
            getIntro();
            $('#delicacy_edit_form').form('submit', {
                url: '/restaurant/delicacy/saveDelicacy.jhtml',
                onSubmit: function () {
                    return $(this).form('validate');	// 返回false终止表单提交
                },
                success: function (data) {
                    BgmgrUtil.backCall(data, function () {
                        $.messager.alert('提示', '操作成功！', 'info', null, null);
                    }, null);
                }, onLoadError: function (data) {
                    console.info('load error' + data);
                },
                error: function () {
                    error.apply(this, arguments);
                    $.messager.alert('提示', '失败！', 'info', null, null);
                }
            });
        }
    </script>
    <style>
        #imageBox {
            margin: 3px 3px;
            width: 300px;
            height: 200px;
            background: #e4f5ff
        }
    </style>
</head>
<body style="overflow: auto">
<form id="delicacy_edit_form" action="/restaurant/delicacy/saveDelicacy.jhtml" method="post"
      enctype="multipart/form-data">
    <div class="easyui-accordion" data-options="multiple:true" style="width:100%;">
        <div title="基本信息"  selected="selected" style="padding:10px;">
            <div style="width:400px;margin-top: 10px;float:right">
                美食图片：
                <div id="imageBox">
                    <div id="imagePanel"></div>

                    <c:if test="${delicacy.id > 0}">
                        <script>
                            var id = ${delicacy.id};
                            var address = '${delicacy.cover}';
                            if(!address.startsWith("http:"))
                                address =BizConstants.QINIU_DOMAIN +address;
                            showImage($('#imagePanel'), address, id);
                            //  $('#fileBox_' + id).find('.diyCancel').click(function () {//增加事件
                            //     var removed = '<input type="hidden" name="removedImgs" value="' + id + '"> ';
                            //     $('#imageBox').append(removed);
                            //  });
                        </script>
                    </c:if>
                </div>
            </div>
            <table>
                <tr>
                    <td>美食名称:</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="delicacy.name" id="foodName"
                               value="${delicacy.name}"
                               data-options="required:true">
                    </td>
                    <td>价格:</td>
                    <td>
                        <span style="color: gray">¥</span>
                        <input name="delicacy.price" id="price" value="${delicacy.price >0?delicacy.price:0}"
                               class="easyui-numberspinner" data-options="min:0,required:true">
                        <span style="color: gray">起</span>
                    </td>
                </tr>
                <tr>
                    <td>区域:</td>
                    <td>
                        <select class="easyui-combobox" name="province" id="addr_province" style="width: 80px;">
                            <option value="请选择" selected="selected">请选择</option>
                        </select>
                        <select class="easyui-combobox" name="cityCode" id="addr_city" style="width: 80px;">
                            <option value="请选择" selected="selected">请选择</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>菜系:</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="delicacy.cuisine" value="${delicacy.cuisine}"
                               data-options="required:false">
                    </td>
                    <td>味道:</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="delicacy.taste" value="${delicacy.taste}"
                               data-options="required:false">
                    </td>
                </tr>
                <tr>
                    <%--<td>商圈:</td>--%>
                    <%--<td>--%>
                        <%--<input class="easyui-textbox" type="text" name="bussinessCircle"--%>
                               <%--value="${delicacy.bussinessCircle}"--%>
                               <%--data-options="required:false">--%>
                    <%--</td>--%>
                    <td>功效:</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="delicacy.efficacy" value="${delicacy.efficacy}"
                               data-options="required:false">
                    </td>
                </tr>
                <tr>
                    <td>推荐原因:</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="delicacy.extend.recommendReason"
                               value="${delicacy.extend.recommendReason}"
                               data-options="required:false">
                    </td>
                </tr>
            </table>
            <div>
                <p>
                    简介
                </p>
                 <textarea data-options="multiline:true" id="intro" rows="18" cols="100" data-options="required:true">
                     ${delicacy.extend.introduction}
                 </textarea>
                <input type="hidden" id="introduction" name="delicacy.extend.introduction"/>
            </div>
        </div>
    </div>
    <div title="提交" id="submitDiv" style="text-align:right;padding:5px 10px">
        <input type="hidden" name="delicacy.cover" id="foodPicture" value="${delicacy.cover}">
        <%--<input type="hidden" id="localNum" name="localNum" value="${delicacy.localNum>0?delicacy.localNum:0 }"/>--%>
        <input type="hidden" id="cityId" name="cityId" value="${delicacy.city.cityCode}"/>
        <input type="hidden" id="touristNum" name="touristNum"
               <%--value="${delicacy.touristNum>0?delicacy.touristNum:0 }"/>--%>
        <input type="hidden" id="agreeNum" name="delicacy.extend.agreeNum" value="${delicacy.extend.agreeNum>0?delicacy.extend.agreeNum:0 }"/>
        <input type="hidden" id="shareNum" name="delicacy.extend.shareNum" value="${delicacy.extend.shareNum>0?delicacy.extend.shareNum:0 }"/>
        <%--<input type="hidden" name="userId" value="${delicacy.user.id>0? delicacy.user.id:0}"/>--%>
        <input type="hidden" name="delicacy.id" value="${delicacy.id}"/>
        <!--<p>此处预览</p>-->
        <div style="padding:5px 0;">
            <p style="color:red;">管理员提交即时生效，请谨慎操作</p>
            <a href="#" style="width:120px;height:30px" class="easyui-linkbutton"

               onclick="if(confirm('确定提交吗？')){submitDelicacy()}"
                    >提交</a>
            <c:if test="${delicacy.id == null}">
                <a href="#" style="width:120px;height:30px" class="easyui-linkbutton"

                   onclick="if(confirm('不保存并关闭本页？')){window.parent.closeTab('新增美食', '美食管理','');}"
                        >取消</a>
            </c:if>
            <c:if test="${delicacy.id > 0}">
                <a href="#" style="width:120px;height:30px" class="easyui-linkbutton"

                   onclick="if(confirm('不保存并关闭本页？')){window.parent.closeTab('编辑美食', '美食管理','');}"
                        >取消</a>
            </c:if>
        </div>
    </div>
    </div>
</form>
</body>
</html>