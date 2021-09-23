<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>第五步</title>
    <%@ include file="../../common/common141.jsp"%>
    <link rel="stylesheet" type="text/css" href="/css/cruiseship/cruiseShip/addWizard.css">
    <script src="/js/xheditor-1.2.2/xheditor-1.2.2.min.js" type="text/javascript"></script>
    <script src="/js/xheditor-1.2.2/xheditor_lang/zh-cn.js" type="text/javascript"></script>
    <script type="text/javascript" src="/js/cruiseship/cruiseShip/util.js"></script>
    <link rel="stylesheet" type="text/css" href="/js/diyUpload/css/webuploader.css">
    <link rel="stylesheet" type="text/css" href="/js/diyUpload/css/diyUpload.css">
    <script type="text/javascript" src="/js/diyUpload/js/webuploader.html5only.min.js"></script>
    <script type="text/javascript" src="/js/diyUpload/js/diyUpload.js"></script>
    <script type="text/javascript" src="/js/cruiseship/cruiseShip/editStep5.js"></script>
</head>
<body style="margin:0;padding:0px 0px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
    <div data-options="region:'center',border:false" style="padding:10px 20px 10px 20px">
        <input id="shipId" name="shipId" type="hidden" value="<s:property value="productId"/>"/>
        <input classifyId="" name="classifyId" type="hidden" value="<s:property value="cruiseShipProjectClassify.id"/>"/>

        <div class="row_hd">服务信息</div>
        <!-- 服务表格工具条 始 -->
        <div id="serviceGridtb">
            <div style="padding:2px 5px;">
                <a href="javascript:void(0)" onclick="editStep5.searchService()" class="easyui-linkbutton" >刷新</a>
                <a href="javascript:void(0)" onclick="editStep5.openEditServiceDg()" class="easyui-linkbutton" >新增</a>
                <a href="javascript:void(0)" onclick="editStep5.doDelService()" class="easyui-linkbutton" >删除</a>
            </div>
        </div>
        <!-- 服务表格工具条 终 -->
        <!-- 服务数据表格 始 -->
        <div style="margin-left:10px;">
            <table id="serviceGrid" style="width:800px; height:400px;"></table>
        </div>
        <!-- 服务数据表格 终-->
        <div class="row_hd">美食信息</div>
        <!-- 美食表格工具条 始 -->
        <div id="foodGridtb">
            <div style="padding:2px 5px;">
                <a href="javascript:void(0)" onclick="editStep5.searchFood()" class="easyui-linkbutton" >刷新</a>
                <a href="javascript:void(0)" onclick="editStep5.openEditFoodDg()" class="easyui-linkbutton" >新增</a>
                <a href="javascript:void(0)" onclick="editStep5.doDelFood()" class="easyui-linkbutton" >删除</a>
            </div>
        </div>
        <!-- 美食表格工具条 终 -->
        <!-- 美食数据表格 始 -->
        <div style="margin-left:10px;">
            <table id="foodGrid" style="width:800px; height:400px;"></table>
        </div>
        <!-- 美食数据表格 终-->
        <div class="row_hd">娱乐信息</div>
        <!-- 娱乐表格工具条 始 -->
        <div id="entainmentGridtb">
            <div style="padding:2px 5px;">
                <a href="javascript:void(0)" onclick="editStep5.searchEntainment()" class="easyui-linkbutton" >刷新</a>
                <a href="javascript:void(0)" onclick="editStep5.openEditEntainmentDg()" class="easyui-linkbutton" >新增</a>
                <a href="javascript:void(0)" onclick="editStep5.doDelEntainment()" class="easyui-linkbutton" >删除</a>
            </div>
        </div>
        <!-- 娱乐表格工具条 终 -->
        <!-- 娱乐数据表格 始 -->

        <div style="margin-left:10px;">
            <table id="entainmentGrid" style="width:800px; height:400px;"></table>
        </div>
        <!-- 娱乐数据表格 终-->

        <div style="text-align:left;margin:20px;height:30px;">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="editStep5.nextGuide(6)">保存，下一步</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="editStep5.nextGuide(5)">编辑完成</a>
        </div>
    </div>
</div>

<%--服务信息编辑窗口--%>
<div id="editServiceDg" title="服务信息" class="easyui-dialog" data-options="closed:true,modal:true" style="width:600px;height:400px;padding:6px">
    <input id="projectId" name="cruiseShipProject.id" type="hidden" value="<s:property value="id"/>"/>
    <form id="serviceForm" method="post">
        <input type="hidden" id="serviceId" name="cruiseShipProject.id" value="">
        <input type="hidden" name="cruiseShipProject.cruiseShip.id" value="">
        <table>
            <tr>
                <td align="right">名称：</td>
                <td>
                    <input class="easyui-textbox" name="cruiseShipProject.name"  data-options="required:true" style="width:80px;" />
                </td>
            </tr>
            <tr>
                <td align="center">容纳人数：</td>
                <td>
                    <input id="peopleNum" name="cruiseShipProject.peopleNum" style="width:80px;"/>
                </td>
            </tr>
            <tr>
                <td align="right">楼层：</td>
                <td>
                    <input class="easyui-numberbox" name="cruiseShipProject.level"  style="width:80px;">
                </td>
            </tr>
            <tr>
                <td align="center">分类名称：</td>
                <td>
                    <input id="classifyName" name="cruiseShipProject.cruiseShipProjectClassify.id" data-options="required:true" style="width:80px;">
                </td>
            </tr>
            <tr>
                <td align="right">介绍：</td>
                <td>
                    <input id="introduction" name="cruiseShipProject.introduction" style="width:400px; height:120px;" value=""/>
                </td>
            </tr>
            <tr>
                <td align="right">着装类型：</td>
                <td>
                    <input class="easyui-textbox" name="cruiseShipProject.suitType" style="width:400px; height:120px;" value=""/>
                </td>
            </tr>
            <tr>
                <td align="right">消费情况：</td>
                <td>
                    <input id="costStatus" class="easyui-textbox" name="cruiseShipProject.costStatus" style="width:400px; height:120px;" value=""/>
                </td>
            </tr>
            <tr>
                <td align="right">封面:</td>
                <td>
                    <div style="width:180px; float: right">
                        <div id="coverParent">
                            <div id="coverBox"></div>
                        </div>
                        <input type="hidden" id="coverPath" name="cruiseShipProject.coverImage">
                        <input type="hidden" id="coverImgId" name="coverImgId">
                    </div>
                </td>
            </tr>
        </table>
        <div style="width: 540px;">
            <label>服务图片:</label>
            <div id="service_imageBox">
                <div id="service_imagePanel"></div>
            </div>
            <div id="service_imageContent"></div>
            <input type="hidden" id="serviceChildFolder" name="serviceChildFolder" value="cruiseship/project/">
            <span style="color: rgba(128, 128, 128, 0.55);top: 47px; position: relative;">为了展示效果，建议上传图片的规格为300×230.</span>
        </div>
        <div style="margin-top: 50px; padding-top: 3px; text-align: center; border-top: dashed #706f6e 1px;">
            <a id="submit_service" href="javascript:void(0)" class="easyui-linkbutton"
               data-options="" onclick="editStep5.saveService()">保存</a>
        </div>

    </form>
</div>
<%--美食信息编辑窗口--%>
<div id="editFoodDg" title="美食信息" class="easyui-dialog" data-options="closed:true,modal:true" style="width:600px;height:400px;padding:6px">
    <form id="foodForm" method="post">
        <input type="hidden" id="foodId" name="cruiseShipProject.id" value="">
        <input type="hidden" name="cruiseShipProject.cruiseShip.id" value="">
        <table>
            <tr>
                <td align="right">名称：</td>
                <td>
                    <input class="easyui-textbox" name="cruiseShipProject.name"  data-options="required:true" style="width:80px;" />
                </td>
            </tr>
            <tr>
                <td align="right">楼层：</td>
                <td>
                    <input class="easyui-numberbox" name="cruiseShipProject.level"  style="width:80px;"/>
                </td>
            </tr>
            <tr>
                <td align="right">容纳人数：</td>
                <td>
                    <input class="easyui-numberbox" name="cruiseShipProject.peopleNum" style="width:80px;"/>
                </td>
            </tr>
            <tr>
                <td align="right">分类名称：</td>
                <td>
                    <input id="foodClassifyName" class="easyui-combobox" data-options="required:true" name="cruiseShipProject.cruiseShipProjectClassify.id"  style="width:80px;">
                </td>
            </tr>
            <tr>
                <td align="right">介绍：</td>
                <td>
                    <input class="easyui-textbox" name="cruiseShipProject.introduction" style="width:400px; height:120px;" value=""/>
                </td>
            </tr>
            <tr>
                <td align="right">着装类型：</td>
                <td>
                    <input class="easyui-textbox" name="cruiseShipProject.suitType" style="width:400px; height:120px;" value=""/>
                </td>
            </tr>
            <tr>
                <td align="right">消费情况：</td>
                <td>
                    <input class="easyui-textbox" name="cruiseShipProject.costStatus" style="width:400px; height:120px;" value="">
                </td>
            </tr>
            <tr>
                <td align="right">餐厅开放状态：</td>
                <td>
                    <input class="easyui-textbox" name="cruiseShipProject.openStatus" style="width:400px; height:120px;" value=""/>
                </td>
            </tr>
            <tr>
                <td align="right">封面:</td>
                <td>
                    <div style="width:180px; float: right">
                        <div id="foodCoverParent">
                            <div id="foodCoverBox"></div>
                        </div>
                        <input type="hidden" id="foodCoverPath" name="cruiseShipProject.coverImage">
                        <input type="hidden" id="foodCoverImgId" name="coverImgId">
                    </div>
                </td>
            </tr>
            </table>
            <div style="width: 540px;">
                <label>美食图片:</label>
                <div id="food_imageBox">
                    <div id="food_imagePanel"></div>
                </div>
                <div id="food_imageContent"></div>
                <input type="hidden" id="foodChildFolder" name="foodChildFolder" value="cruiseship/project/">
                <span style="color: rgba(128, 128, 128, 0.55);top: 47px; position: relative;">为了展示效果，建议上传图片的规格为300×230.</span>
            </div>
            <div style="margin-top: 50px; padding-top: 3px; text-align: center; border-top: dashed #706f6e 1px;">
                <a id="submit_food" href="javascript:void(0)" class="easyui-linkbutton"
                   data-options="" onclick="editStep5.saveFood()">保存</a>
            </div>
    </form>
</div>
<%--娱乐信息编辑窗口--%>
<div id="editEntainmentDg" title="娱乐信息" class="easyui-dialog" data-options="closed:true,modal:true" style="width:600px;height:400px;padding:6px">
    <form id="entainmentForm" method="post">
        <input type="hidden" id="entainmentId" name="cruiseShipProject.id" value="">
        <input type="hidden" name="cruiseShipProject.cruiseShip.id" value="">
        <table>
            <tr>
                <td align="right">名称：</td>
                <td>
                    <input class="easyui-textbox" name="cruiseShipProject.name" data-options="required:true" data-options="required:true" style="width:80px;"/>
            </tr>
            <tr>
                <td align="right">容纳人数：</td>
                <td>
                    <input class="easyui-textbox" name="cruiseShipProject.peopleNum" style="width:80px;"/>
                </td>
            <tr>
            <tr>
                <td align="right">分类名称：</td>
                <td>
                    <input id="entainmentClassifyName" class="easyui-combobox" data-options="required:true" data-options="required:true" name="cruiseShipProject.cruiseShipProjectClassify.id"  style="width:80px;"/>
                </td>
            </tr>
            <tr>
                <td align="right">楼层：</td>
                <td>
                    <input class="easyui-textbox" name="cruiseShipProject.level"  style="width:80px;"/>
                </td>
            </tr>
            <tr>
                <td align="right">介绍：</td>
                <td>
                    <input class="easyui-textbox" name="cruiseShipProject.introduction" style="width:400px; height:120px;" value=""/>
                </td>
            </tr>
            <tr>
                <td align="right">着装类型：</td>
                <td>
                    <input id="suitType" name="cruiseShipProject.suitType" style="width:400px; height:120px;" value=""/>
                </td>
            </tr>
            <tr>
                <td align="right">开放状态：</td>
                <td>
                    <input class="easyui-textbox" name="cruiseShipProject.openStatus" style="width:400px; height:120px;" value=""/>
                </td>
            </tr>
            <tr>
                <td align="right">消费情况：</td>
                <td>
                    <input  name="cruiseShipProject.costStatus" style="width:400px; height:120px;" value=""/>
                </td>
            </tr>
            <tr>
                <td align="right">封面:</td>
                <td>
                    <div style="width:180px; float: right">
                        <div id="entainmentCoverParent">
                            <div id="entainmentCoverBox"></div>
                        </div>
                        <input type="hidden" id="entainmentCoverPath" name="cruiseShipProject.coverImage">
                        <input type="hidden" id="entainmentCoverImgId" name="coverImgId">
                    </div>
                </td>
            </tr>
            </table>
        <div style="width: 540px;">
            <label>娱乐图片:</label>
            <div id="entainment_imageBox">
                <div id="entainment_imagePanel"></div>
            </div>
            <div id="entainment_imageContent"></div>
            <input type="hidden" id="entainmentChildFolder" name="entainmentChildFolder" value="cruiseship/project/">
            <span style="color: rgba(128, 128, 128, 0.55);top: 47px; position: relative;">为了展示效果，建议上传图片的规格为300×230.</span>
        </div>
        <div style="margin-top: 50px; padding-top: 3px; text-align: center; border-top: dashed #706f6e 1px;">
            <a id="submit_entainment" href="javascript:void(0)" class="easyui-linkbutton"
               data-options="" onclick="editStep5.saveEntainment()">保存</a>
        </div>
    </form>
</div>

</body>
</html>
