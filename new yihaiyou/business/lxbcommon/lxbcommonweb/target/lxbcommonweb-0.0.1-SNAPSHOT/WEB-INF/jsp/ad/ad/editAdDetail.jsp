<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/10/26
  Time: 13:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@ include file="../../common/common141.jsp"%>
  <link href="/js/kindeditor/themes/default/default.css" rel="stylesheet" type="text/css">
  <%--<link href="/css/ticket/form.css" rel="stylesheet" type="text/css">--%>
  <script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
  <link rel="stylesheet" type="text/css" href="/css/ad/addAds.css">
  <script type="text/javascript" src="/js/ad/editAdDetail.js"></script>
  <script type="text/javascript" src="/js/ad/adsUtil.js"></script>
  <title></title>
    <style type="text/css">
        table tr {line-height: 45px;}
        table tr td.label  {width: 100px;text-align: right;}
        .textbox { margin-left: 0;}
    </style>
</head>
<body>
    <div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
    <!-- 数据表格 始 -->
      <div title="" data-options="region:'center',border:false" style="padding:10px;">
        <form id="adsform" action="" method="post" enctype="multipart/form-data">
          <input name="id" type="hidden" style="width:220px;" value="${ads.id}"/>
          <table>
            <tr style="line-height: 30px;">
                <td class="label"><label class="keyzone">广告位置：</label></td>
                <td><div class="valuezone">
                    <input id="add_ads_location" name="adsLocation" class="easyui-combobox" style="width:220px;" value="${ads.sysResourceMap.id}"/>
                  </div></td>
            </tr>
            <tr>
                <td class="label"><label class="keyzone">广告标题：</label></td>
                <td><div class="valuezone">
                    <input id="add_ads_adTitle" class="easyui-textbox" name="adTitle" style="width:120px;margin-left: 0;" value="${ads.adTitle}"/>
                  </div></td>
            </tr>
            <tr>
                <td class="label"><label class="keyzone">打开方式：</label></td>
                <td><div class="valuezone">
                    <input id="add_ads_opentype" name="adsOpenType" class="easyui-combobox" style="width:120px;" value="${ads.openType}"/>
                  </div></td>
            </tr>
            <tr>
                <td class="label"><label class="keyzone">广告URL：</label></td>
                <td><div class="valuezone">
                    <input id="qry_keyword" name="adsUrl" class="easyui-textbox" data-options="prompt:''" style="width:200px;margin-left: 0;" value="${ads.url}">
                  </div></td>
            </tr>
            <tr id="picture">
                <td class="label"><label class="keyzone">广告图片：</label></td>
                <td><div class="valuezone">
                    <input id="filePath" name="imgPath" type="hidden" value="${ads.imgPath}"/>
                    <input type="button" class="J_add_pic" id="add_descpic" value="添加描述图" style="width:100px;">
                  </div></td>
            </tr>
            <tr>
                <td class="label"><label  class="keyzone">开始时间：</label></td>
                <td><div class="valuezone">
                    <input id="add_ads_stime" name="adsStime" class="easyui-datebox" style="width:120px;" value="${ads.startTime}"/>
                  </div></td>
            </tr>
            <tr>
                <td class="label"><label  class="keyzone">结束时间：</label></td>
                <td><div class="valuezone">
                    <input id="add_ads_etime" name="adsEtime" class="easyui-datebox" style="width:120px;" value="${ads.endTime}"/>
                  </div></td>
            </tr>
            <tr>
                <td class="label"><label class="keyzone">广告排序：</label></td>
                <td><div class="valuezone">
                    <input id="add_ads_sort" class="easyui-numberbox" name="adSort" style="width:120px;" value="${ads.sort}"/>
                  </div></td>
            </tr>
            <tr>
                <td class="label"><label class="keyzone">广告状态：</label></td>
                <td><div class="valuezone">
                    <input id="add_ads_status" name="adsStatus" class="easyui-combobox" style="width:120px;" value="${ads.adStatus}"/>
                  </div></td>
            </tr>
          </table>
          <div id="imgView" style="display:none; position: absolute;bottom: 123px;left: 245px;">
            <img alt="" src="" width="200" height="120" style="border-radius: 5px;">
            <a href="javascript:;" id="remove_img_id" class="easyui-linkbutton line-btn" onclick="AdsDetail.deleteImg()" style="margin-left: -48px;margin-top: -23px;">删除</a>
          </div>
          <div style="text-align:center;padding:5px">
             <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="AdsDetail.commitForm()">提交</a>
            <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="AdsDetail.doCancel()">取消</a>
          </div>
        </form>
      </div>

  </div>

</body>
</html>
