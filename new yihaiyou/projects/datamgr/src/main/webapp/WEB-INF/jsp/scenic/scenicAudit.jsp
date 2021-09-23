<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>${scenicInfo.name}</title>
    <%@ include file="/WEB-INF/pages/common/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctxPath}/static/diyUpload/css/webuploader.css">
    <link rel="stylesheet" type="text/css" href="${ctxPath}/static/diyUpload/css/diyUpload.css">
    <script type="text/javascript" src="${ctxPath}/static/diyUpload/js/webuploader.html5only.min.js"></script>
    <script type="text/javascript" src="${ctxPath}/static/diyUpload/js/diyUpload.js"></script>
    <script>
        $(pageInit);

        function pageInit() {
            //折叠默认展开
            $('#ticketDiv').css("display", "block");
            $('#contactDiv').css("display", "block");
            $('#picsDiv').css("display", "block");
            $('#submitDiv').css("display", "block");
            //交通枢纽信息
            $('#gp').val(1);
            loadPics($('#gp').val());
        }
        function loadPics(gp) {
            var gi = $('#galleryId').val();
            if (gi == undefined) gi = '';
            $.get("${ctxPath}/mgrer/scenic/scenicGalleryList?rows=4&scenicIds=${scenicInfo.id}&galleryId=" + gi
                    + "&type=scenic&page=" + $('#gp').val(),
                    function (result) {
                        if (result.rows.length == 0) {
                            $('#webuploader-more').html('已无更多');
                            $('#webuploader-more').attr('onclick', '');
                            $('#webuploader-more').css("background-color", "grey");
                            return;
                        }
                        var galleryId = result.rows[0].galleryId;
                        $('#galleryId').prop('value', galleryId);
                        $.each(result.rows, function (i, n) {
                            var id = n.id;
                            var address = BizConstants.QINIU_DOMAIN + n.addressLarge;
                            showImage($('#imagePanel'), address, id);
                            $('#fileBox_' + id).find('.diyCancel').click(function () {//增加事件
                                alert('请在景点编辑页面或相册管理页面删除图片');
                            });
                        });
                    });
            $('#gp').prop('value', parseInt(gp) + 1);
        }
        //上架
        function onSale(id, name) {
            var sure = confirm("确定上架[" + name + "]吗？");
            if (sure) {
                $.get("${ctxPath}/mgrer/scenic/show?id=" + id,
                        function (data) {
                            $.messager.alert('提示', '操作成功！', 'info', null, function() {  // 为适应新旧首页的写法
                                if (window.parent.$("#tabs")) {
                                    window.parent.closeTab('审核景点', '景点管理', '');
                                } else {
                                    window.close();
                                }
                            });
                        }
                );
            }
        }
        //拒绝
        function decline(id, name) {
            var sure = confirm("确定拒绝[" + name + "]吗？");
            if (sure) {
                $.get("${ctxPath}/mgrer/scenic/decline?id=" + id,
                        function (data) {
                            $.messager.alert('提示', '操作成功！', 'info', null, function() {  // 为适应新旧首页的写法
                                if (window.parent.$("#tabs")) {
                                    window.parent.closeTab('审核景点', '景点管理', '');
                                } else {
                                    window.close();
                                }
                            });
                        }
                );
            }
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
<body>
<div class="easyui-accordion" data-options="multiple:true" style="width:100%;">
    <div title="基本信息"  selected="selected" style="padding:10px;">
        <div>
            <span>景点名称:</span>
                <span>
                     <span style="background-color:lightsteelblue;font-size: 16px">${scenicInfo.name}</span>
                </span>
        </div>
        <div>
            <span>景点类别:</span>
                <span>
                      <c:forEach items="${labelList}" var="label">
                          <input type="checkbox" value="${label.id}" disabled="disabled" name="label" <c:if
                                  test="${label.checked}">
                                 checked</c:if>>${label.name}
                      </c:forEach>
                </span>
        </div>
        <div>
            <span>交通信息:</span>
                <span>
                    <input type="checkbox" disabled="disabled"
                           <c:if test="${scenicInfo.flagHasTaxi ==1}">checked="checked"</c:if>
                           value="1" id="flagHasTaxi" name="flagHasTaxi" placeholder="打车" cn="打车">打车&nbsp;
                    <input type="checkbox" disabled="disabled"
                           <c:if test="${scenicInfo.flagHasBus ==1}">checked="checked"</c:if>
                           value="1" id="flagHasBus" name="flagHasBus" placeholder="公交" cn="公交">公交&nbsp;
                    <input type="hidden" value="0" id="isStation" name="isStation" placeholder="交通枢纽" cn="交通枢纽">
                    <select id="stationType" name="stationType" disabled="disabled">
                        <option value="0" <c:if test="${scenicInfo.stationType ==0}">selected="selected"</c:if>>
                            非交通枢纽
                        </option>
                        <option value="1" <c:if test="${scenicInfo.stationType ==1}">selected="selected"</c:if>>
                            是火车站
                        </option>
                        <option value="2" <c:if test="${scenicInfo.stationType ==2}">selected="selected"</c:if>>
                            是机场
                        </option>
                        <option value="3" <c:if test="${scenicInfo.stationType ==3}">selected="selected"</c:if>>
                            是汽车站
                        </option>
                    </select>
                    ${scenicInfo.flagThreeLevelRegion}

                    <input type="checkbox" disabled="disabled"
                           <c:if test="${scenicInfo.flagThreeLevelRegion ==1}">checked="checked"</c:if>
                           value="0" id="flagThreeLevelRegion" name="flagThreeLevelRegion" placeholder="三级景区" cn="三级景区">三级景区
                    <input type="hidden" value="${scenicInfo.userId}" class="form-control" id="userId" name="userId"
                           placeholder="用户ID" need="need" cn="用户ID"/>
                </span>

        </div>

        <div>
            <p>
                简介
            </p>


                 <span style="font-size: 16px">
                     <hr>
                     ${scenicOther.introduction}
                     <hr>
                 </span>
    </div>

        <div>
            <span>游玩时间:</span>
            <span>
                <span style="background-color:lightsteelblue;font-size: 16px"><c:if
                        test="${scenicInfo.adviceHours >0}">${scenicInfo.adviceHours}</c:if></span>
                分钟
            </span>
            <span>时间描述:</span>
                <span>
                   <span style="background-color:lightsteelblue;font-size: 16px">${scenicInfo.adviceTime}</span>
                </span>
        </div>
    </div>
    <div title="景点相册" id="picsDiv"  style="padding:10px;">
        <div id="imageBox">
            <input type="hidden" id="galleryId" name="galleryId">
            <input type="hidden" id="gp" name="galleryPage" value="1">

            <div id="imagePanel"></div>
            <c:if test="${scenicInfo.id > 0 }">
                <div class="webuploader-more" id="webuploader-more" onclick="loadPics($('#gp').val())">加载更多</div>
            </c:if>
        </div>
    </div>
    <div title="门票价格" id="ticketDiv"  style="padding:10px;">
        <tr cellpadding="10">
        <tr>
            <td>销售价:</td>
            <td>
                <span style="color: gray">¥</span>
                    <span style="background-color:lightsteelblue;font-size: 16px">
                         <c:if test="${scenicInfo.price >0}">${scenicInfo.price}</c:if>
                    </span>
                <span style="color: gray">起</span>
            </td>
            <td>市场价:</td>
            <td>
                <span style="color: gray">¥</span>
                    <span style="background-color:lightsteelblue;font-size: 16px">
                        <c:if test="${scenicInfo.price >0}">${scenicInfo.marketPrice}</c:if>
                    </span>
                <span style="color: gray">起</span>
            </td>
        </tr>
        <tr>
            <td>价格描述:</td>
            <td>
                <span style="background-color:lightsteelblue;font-size: 16px">${scenicInfo.ticket}</span>
            </td>
            <td>小帮价:</td>
            <td>
                <span style="color: gray">¥${scenicInfo.lowestPrice}</span>
            </td>
            <td>
            </td>
        </tr>
        </table>
    </div>
    <div title="联系方式" id="contactDiv"  style="padding:10px;">
        <table cellpadding="10">
            <tr>

                <td>电话:</td>
                <td>
                    <span style="background-color:lightsteelblue;font-size: 16px">${scenicInfo.telephone}</span>
                </td>
                <td>评分:</td>
                <td>
                        <span style="background-color:lightsteelblue;font-size: 16px"><c:if
                                test="${scenicInfo.score >0}">${scenicInfo.score}</c:if></span>
                </td>
            </tr>
            <tr>
                <td>区域:</td>
                <td>
                        <span id="areaShow" style="background-color:lightsteelblue;font-size: 16px">
                        </span>
                    <script>
                        $.get("${ctxPath}/common/area/get?cityCode=${scenicInfo.cityCode}", function (result) {
                            $('#areaShow').append(result.fullPath.replace('||', '——'));
                        });
                    </script>
                </td>
                <td>地址:</td>
                <td>
                    <span style="background-color:lightsteelblue;font-size: 16px">${scenicInfo.address}</span>
                </td>
            </tr>
            <tr>
                <td>开放时间:</td>
                <td>
                    <span style="background-color:lightsteelblue;font-size: 16px">${scenicInfo.openTime}</span>
                </td>
            </tr>
        </table>
    </div>
    <div id="submitDiv" title="提交" style="text-align:right;padding:5px 10px">
        <div style="padding:5px 0;">
            <p style="color:red;">若审核已上架的景点，审核生效后，新的数据将覆盖旧的数据</p>
            <a href="#" style="width:120px;height:30px" class="easyui-linkbutton"
               onclick="decline(${scenicInfo.id},'${scenicInfo.name}');">拒绝</a>
            <a href="#" style="width:120px;height:30px" class="easyui-linkbutton"
               onclick="onSale(${scenicInfo.id},'${scenicInfo.name}');">审核通过</a>
            <!--<a href="#" style="width:120px;height:30px" class="easyui-linkbutton"

               onclick="if(confirm('不保存并关闭本页？')){window.parent.closeTab('审核景点', '景点管理','');}"
                    >取消</a>-->
        </div>
    </div>
</div>
</body>
</html>