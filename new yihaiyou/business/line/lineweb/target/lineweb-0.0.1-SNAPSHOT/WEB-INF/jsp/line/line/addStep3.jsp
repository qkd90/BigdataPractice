<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>第三步</title>
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript">
var FG_DOMAIN = '<s:property value="fgDomain"/>';
</script>
	<link rel="stylesheet" type="text/css" href="/js/lineDiyupload/css/webuploader.css">
	<link rel="stylesheet" type="text/css" href="/js/lineDiyupload/css/diyUpload.css">
	<script type="text/javascript" src="/js/lineDiyupload/js/webuploader.html5only.min.js"></script>
	<script type="text/javascript" src="/js/lineDiyupload/js/diyUpload.js"></script>
<script type="text/javascript" src="/js/line/line/lineUtil.js"></script>
<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
<link rel="stylesheet" type="text/css" href="/css/line/line/addWizard.css">
<link rel="stylesheet" type="text/css" href="/js/line/line/photo_js/js_photo.css">
<link rel="stylesheet" type="text/css" href="/js/line/line/photo_js/jquery.loadmask.css">
<link rel="stylesheet" type="text/css" href="/css/line/iconfont/iconfont.css">
<script type="text/javascript" src="/js/line/line/photo_js/js_photo.js"></script>
<script type="text/javascript" src="/js/line/line/photo_js/jquery.loadmask.js"></script>
<script type="text/javascript" src="/js/line/line/photo_js/jquery.loadmask.min.js"></script>
<script type="text/javascript" src="/js/line/line/addStep3.js"></script>

</head>
<body style="margin:0;padding:0px 0px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
	<div data-options="region:'center',border:false" style="padding:10px 0px 10px 0px;">
		<form id="editForm" method="post">
		<input id="lineexplainId" name="lineexplainId" type="hidden"/>
		<input id="productId" name="productId" type="hidden" value="7"/>
		<div style="padding:0px 0px 10px 0px">
			<table>
	        	<tr>
				   	<td class="label">线路名称:</td>
				   	<td style="line-height: 18px;">
				   		<span id="productName"></span>
				   	</td>
				</tr>
				<tr>
					<td class="label">行程天数:</td>
					<td style="line-height: 18px;">
						<input type="text" class="easyui-numberspinner" id="planDayId" name="days" style="width: 50px;" data-options="min:1, editable:false, value:1">天
					</td>
				</tr>
			</table>
		</div> 
		<div class="row_hd">线路特色</div>
		<div style="overflow:auto;">
			<table>
	        	<tr>
				   	<td class="label">主题标签:</td>
				   	<td>
				   		<%--<div style="width: 560px;">
				   			<s:iterator value="playtitles" status="stuts">   
				   			<div class="ck-div"><input type="checkbox" class="left-block" name="playTitleId" value="<s:property value="id"/>"/><span class="ck-label"><s:property value="name"/></span></div>
				   			</s:iterator>
				   		</div>--%>
				   		<div class="clr-float">
				   			<input class="easyui-textbox" name="defineTag" style="width:200px;" data-options="validType:'maxLength[30]'">
				   			<span class="tip">自定义标签最多<span class="green-bold">30</span>个字符，多个主题以中文逗号（，）隔开</span>
				   		</div>
				   	</td>
				</tr>
	        	<tr>
				   	<td class="label">行程亮点:</td>
				   	<td>
				   		<textarea id="lineLightPointK" name="lineLightPoint" style="width:600px; height:120px; visibility: hidden;"></textarea>
				   		<span class="tip">还可以输入<span class="green-bold">6000</span>个字符</span>
				   	</td>
				</tr>
	        	<tr>
				   	<td class="label">&nbsp;</td>
				   	<td>
				   	</td>
				</tr>
			</table>
		</div>
		<div class="row_hd">行程内容</div>
		<div style="overflow:auto;">
			<div id="lineDayDiv">
				<input type="hidden" name="planDetail" id="planDetailId">
				<div id="aa" class="easyui-accordion" style="width:800px;height:500px; margin-left: 130px;">
				</div>
			</div>
		</div>
			<div class="row_hd">特殊人群</div>
			<div style="overflow:auto;">
				<table>
					<tr>
						<td class="label"><font color="red">*&nbsp;</font>出游人确认:</td>
						<td>
							出游人中是否有<input class="easyui-numberbox" name="tripAgeMin" value="60" style="width:40px;" data-options="min:1,max:100,required:true">（含）
							至<input class="easyui-numberbox" name="tripAgeMax" value="65" style="width:40px;" data-options="min:1,max:100,required:true">（不含）周岁的老人。
						</td>
					</tr>
					<tr>
						<td class="label" rowspan="4"><font color="red">*&nbsp;</font>儿童价标准:</td>
						<td>
							<div>
								<label>
									<input type="radio" class="left-block" name="childStandardType" value="none" checked/><span class="rdo-label">无</span>
								</label>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div>
								<label>
									<input type="radio" class="left-block" name="childStandardType" value="age" />
									<span class="rdo-label">年龄<input class="easyui-textbox" name="childStartNum" value="" style="width:70px;" data-options="validType:'maxLength[3]',prompt:'输入数字'">
									~<input class="easyui-textbox" name="childEndNum" value="" style="width:70px;" data-options="validType:'maxLength[3]',prompt:'输入数字'">周岁（不含），
									<input class="easyui-combobox" name="childBed" style="width:80px;" data-options="valueField:'id',textField:'text',data:[{id:'不占床',text:'不占床'},{id:'-----',text:'-----'}]" value=""/>，
									<input class="easyui-textbox" name="childOtherRemark" style="width:220px;" value="" data-options="validType:'maxLength[100]',prompt:'是否包含大交通、用餐等请注明'">。</span>
								</label>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div>
								<label>
									<input type="radio" class="left-block" name="childStandardType" value="height" />
									<span class="rdo-label">身高<input class="easyui-textbox" name="heightChildStartNum" value="" style="width:70px;" data-options="validType:'maxLength[3]',prompt:'输入数字'">
									~<input class="easyui-textbox" name="heightChildEndNum" value="" style="width:70px;" data-options="validType:'maxLength[3]',prompt:'输入数字'">米（含），
									<input class="easyui-combobox" name="heightChildBed" style="width:80px;" data-options="valueField:'id',textField:'text',data:[{id:'不占床',text:'不占床'},{id:'-----',text:'-----'}]" value=""/>，
									<input class="easyui-textbox" name="heightChildOtherRemark" style="width:220px;" value="" data-options="validType:'maxLength[100]',prompt:'是否包含大交通、用餐等请注明'">。</span>
								</label>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div style="margin-bottom: 20px;">
								<label>
									<input type="radio" class="left-block" name="childStandardType" value="desc"/><span class="rdo-label">儿童价特殊说明</span>
								</label>
							</div>
							<textarea id="childLongRemarkK" name="childLongRemark" style="width:600px; height:120px; visibility: hidden;"></textarea>
							<span class="tip">还可以输入<span class="green-bold">6000</span>个字符</span>
						</td>
					</tr>
				</table>
			</div>
		<div class="row_hd">行程介绍</div>
		<div style="overflow:auto;">
			<table>
				<tr>
					<td class="label">接待标准:</td>
					<td>
						<textarea id="receiveStandardK" name="receiveStandard" style="width:600px; height:120px; visibility: hidden;"></textarea>
						<span class="tip">还可以输入<span class="green-bold">6000</span>个字符</span>
					</td>
				</tr>
				<tr>
					<td class="label">沿途景点:</td>
					<td>
						<textarea id="accrossScenicK" name="accrossScenic" style="width:600px; height:120px; visibility: hidden;"></textarea>
						<span class="tip">还可以输入<span class="green-bold">6000</span>个字符</span>
					</td>
				</tr>
			</table>
		</div>
		<div class="row_hd">预订指南</div>
		<div style="overflow:auto;">
			<table>
				<tr>
					<td class="label">预订须知:</td>
					<td>
						<textarea id="orderContextK" name="orderContext" style="width:600px; height:120px; visibility: hidden;"></textarea>
						<span class="tip">还可以输入<span class="green-bold">6000</span>个字符</span>
					</td>
				</tr>
				<tr>
					<td class="label">安全提示:</td>
					<td>
						<textarea id="tipContextK" name="tipContext" style="width:600px; height:120px; visibility: hidden;"></textarea>
						<span class="tip">还可以输入<span class="green-bold">6000</span>个字符</span>
					</td>
				</tr>
				<tr>
					<td class="label">出行须知:</td>
					<td>
						<textarea id="tripNoticeK" name="tripNotice" style="width:600px; height:120px; visibility: hidden;"></textarea>
						<span class="tip">还可以输入<span class="green-bold">6000</span>个字符</span>
					</td>
				</tr>
				<tr>
					<td class="label">特殊限制:</td>
					<td>
						<textarea id="specialLimitK" name="specialLimit" style="width:600px; height:120px; visibility: hidden;"></textarea>
						<span class="tip">还可以输入<span class="green-bold">6000</span>个字符</span>
					</td>
				</tr>
				<tr>
					<td class="label">签约方式:</td>
					<td>
						<textarea id="signWayK" name="signWay" style="width:600px; height:120px; visibility: hidden;"></textarea>
						<span class="tip">还可以输入<span class="green-bold">6000</span>个字符</span>
					</td>
				</tr>
				<tr>
					<td class="label">付款方式:</td>
					<td>
						<textarea id="payWayK" name="payWay" style="width:600px; height:120px; visibility: hidden;"></textarea>
						<span class="tip">还可以输入<span class="green-bold">6000</span>个字符</span>
					</td>
				</tr>
				<tr>
					<td class="label">违约责任提示:</td>
					<td>
						<textarea id="breachTipK" name="breachTip" style="width:600px; height:120px; visibility: hidden;"></textarea>
						<span class="tip">还可以输入<span class="green-bold">6000</span>个字符</span>
					</td>
				</tr>
				<tr>
					<td class="label">购物项目:</td>
					<td>
						<textarea id="shoppingDescK" name="shoppingDesc" style="width:600px; height:120px; visibility: hidden;"></textarea>
						<span class="tip">还可以输入<span class="green-bold">6000</span>个字符</span>
					</td>
				</tr>
				<tr>
					<td class="label">备注:</td>
					<td>
						<textarea id="remarkK" name="remark" style="width:600px; height:120px; visibility: hidden;"></textarea>
						<span class="tip">还可以输入<span class="green-bold">6000</span>个字符</span>
					</td>
				</tr>
			</table>
		</div>
		</form>
	    <div style="text-align:left;margin:20px;height:30px;">
	       	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="addStep3.nextGuide()">确认提交</a>
	   	</div>	
	</div>
</div>
	
</body>
</html>
