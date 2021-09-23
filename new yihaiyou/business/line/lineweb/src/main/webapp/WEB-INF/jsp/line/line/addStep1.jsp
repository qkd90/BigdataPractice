<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>第一步</title>
	<%@ include file="../../common/common141.jsp"%>
	<script type="text/javascript" src="/js/line/line/lineUtil.js"></script>
	<link rel="stylesheet" type="text/css" href="/js/kindeditor/themes/default/default.css">
	<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
	<link rel="stylesheet" type="text/css" href="/css/line/line/addWizard.css">
	<link rel="stylesheet" type="text/css" href="/css/line/iconfont/iconfont.css">
	<link rel="stylesheet" type="text/css" href="/js/line/line/photo_js/js_photo.css">
	<link rel="stylesheet" type="text/css" href="/js/line/line/photo_js/jquery.loadmask.css">
	<script type="text/javascript" src="/js/line/line/photo_js/jquery.loadmask.js"></script>
	<script type="text/javascript" src="/js/line/line/photo_js/jquery.loadmask.min.js"></script>
	<script type="text/javascript" src="/js/line/line/photo_js/js_photo.js"></script>
	<script type="text/javascript" src="/js/common/areaSelectDg.js"></script>
	<script type="text/javascript" src="/js/line/line/addStep1.js"></script>
    <link rel="stylesheet" type="text/css" href="/js/diyUpload/css/webuploader.css">
    <link rel="stylesheet" type="text/css" href="/js/diyUpload/css/diyUpload.css">
    <script type="text/javascript" src="/js/diyUpload/js/webuploader.html5only.min.js"></script>
    <script type="text/javascript" src="/js/diyUpload/js/diyUpload.js"></script>
	<style type="text/css">
		.textbox-text-readonly {color:gray;}
	</style>
</head>
<body style="margin:0;padding:0px 0px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
	<div data-options="region:'center',border:false" style="padding:10px 20px 10px 20px">
		<form id="editForm" method="post">
		<input id="productId" name="productId" type="hidden"/>
		<table>
        	<tr>
			   	<td class="label">产品编号:</td>
			   	<td>
			   		<input class="easyui-textbox" name="productNo" value="系统自动生成，保存后可见" style="width:180px;" readonly="readonly">
					<select class="easyui-combobox" data-options="editable:false" name="combineType" style="width:80px;">
						<option value="<s:property value="@com.data.data.hmly.service.line.entity.enums.CombineType@single"/>">单一型</option>
						<option value="<s:property value="@com.data.data.hmly.service.line.entity.enums.CombineType@combine"/>">组合型</option>
					</select>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>线路类型:</td>
			   	<td>
			   		<%--<div class="rdo-div"><input type="radio" class="left-block" name="lineType" value="city" checked/><span class="rdo-label">厦门游</span></div>--%>
				   	<div class="rdo-div"><label><input type="radio" class="left-block" name="lineType" value="around" checked /><span class="rdo-label">周边游</span></label></div>
				   	<div class="rdo-div"><label><input type="radio" class="left-block" name="lineType" value="china"/><span class="rdo-label">国内游</span></label></div>
					<div class="rdo-div"><label><input type="radio" class="left-block" name="lineType" value="foreign"/><span class="rdo-label">出境游</span></label></div>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>产品性质:</td>
			   	<td>
			   		<div class="rdo-div">
						<label>
							<input type="radio" class="left-block" name="productAttr" value="<s:property value="@com.data.data.hmly.service.line.entity.enums.ProductAttr@gentuan"/>" checked/>
							<span class="rdo-label">跟团游</span>
						</label>
					</div>
					<%--<div class="rdo-div">
                     <label>
                         <input type="radio" class="left-block" name="productAttr" value="<s:property value="@com.data.data.hmly.service.line.entity.enums.ProductAttr@zizhuzijia"/>" checked/>
                         <span class="rdo-label">自助自驾</span>
                     </label>
                 </div>--%>
					<div class="rdo-div">
						<label>
							<input type="radio" class="left-block" name="productAttr" value="<s:property value="@com.data.data.hmly.service.line.entity.enums.ProductAttr@ziyou"/>"/>
							<span class="rdo-label">自助游</span>
						</label>
					</div>
					<div class="rdo-div">
						<label>
							<input type="radio" class="left-block" name="productAttr" value="<s:property value="@com.data.data.hmly.service.line.entity.enums.ProductAttr@zijia"/>"/>
							<span class="rdo-label">自驾游</span>
						</label>
					</div>
					<div class="rdo-div">
						<label>
							<input type="radio" class="left-block" name="productAttr" value="<s:property value="@com.data.data.hmly.service.line.entity.enums.ProductAttr@custommade"/>"/>
							<span class="rdo-label">精品定制</span>
						</label>
					</div>
					<div class="rdo-div">
						<label>
							<input type="radio" class="left-block" name="productAttr" value="<s:property value="@com.data.data.hmly.service.line.entity.enums.ProductAttr@localplay"/>"/>
							<span class="rdo-label">当地参团</span>
						</label>
					</div>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>产品名称:</td>
			   	<td>
			   		<input class="easyui-textbox" id="name" name="name" style="width:360px;" required="true" data-options="validType:'maxLength[80]'">
			   		<span class="tip">最多<span class="green-bold">80</span>个字符</span>
			   		<div class="tip">城市+某景点+交通方式+时间 例如：厦门鼓浪屿、永定土楼三日游</div>
			   	</td>
			</tr>
			<tr>
				<td class="label"><font color="red">*&nbsp;</font>线路标题:</td>
				<td>
					<input class="easyui-textbox" id="appendName" name="appendTitle" style="width:360px;" required="true" data-options="validType:'maxLength[50]'">
					<span class="tip">最多<span class="green-bold">50</span>个字符</span>
				</td>
			</tr>
        	<tr>
			   	<td class="label">线路分类:</td>
			   	<td>
			   		<select class="easyui-combobox" id="show_qry_customType" data-options="editable:false" name="category" style="width:190px;">
						<option value="">自定义分类（不限）</option>
						<c:forEach items="${linecategorgs}" var="categorg">
							<option value="${categorg.id}">${categorg.name}</option>
						</c:forEach>
					</select>
			   	</td>
			</tr>
			<tr class="tourPlaceType">
				<td class="label"><span style="color: red">*&nbsp;</span>发团地点:</td>
				<td>
					<div class="rdo-div"><label><input type="radio" class="left-block" name="tourPlaceType" value="<s:property value="@com.data.data.hmly.service.line.entity.enums.TourPlaceType@start"/>" checked/><span class="rdo-label">出发地成团</span></label></div>
					<div class="rdo-div"><label><input type="radio" class="left-block" name="tourPlaceType" value="<s:property value="@com.data.data.hmly.service.line.entity.enums.TourPlaceType@dest"/>"/><span class="rdo-label">目的地成团</span></label></div>
				</td>
			</tr>
        	<tr class="startPlace">
			   	<td class="label"><span class="requiredLable" style="color: red">*&nbsp;</span>出发城市:</td>
			   	<td>
					<input type="hidden" id="hidden_startCityId" name="startCityId">
					<input id="startCityId" class="easyui-textbox" data-options="buttonText:'×', required: true, editable:false,prompt:'点击选择出发城市'" style="width:200px" data-country="" data-province="" data-city="">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="addStep1.clearStartPlace()">无</a>
			   	</td>
			</tr>
			<tr>
				<td class="label"><font color="red">*&nbsp;</font>到达城市:</td>
				<td>
					<input type="hidden" id="hidden_arriveCityId" name="arriveCityId">
					<input id="arriveCityId" class="easyui-textbox" data-options="buttonText:'×', required: true, editable:false, prompt:'点击选择到达城市'" style="width:200px" data-country="" data-province="" data-city="">
				</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>游客需提前:</td>
			   	<td>
			   		<input class="easyui-numberspinner" style="width:50px;" name="preOrderDay" required="required" data-options="min:1,max:100,editable:true" value="1">  
					<span style="line-height: 18px;">天预订&nbsp;-&nbsp;建议在</span>
					<input class="easyui-combobox" name="suggestOrderHour" required="true" data-options="valueField:'id',textField:'text',editable:false,data:[{id:0,text:0},{id:1,text:1},{id:2,text:2},{id:3,text:3},{id:4,text:4},
					{id:5,text:5},{id:6,text:6},{id:7,text:7},{id:8,text:8},{id:9,text:9},{id:10,text:10},{id:11,text:11},{id:12,text:12},{id:13,text:13},{id:14,text:14},{id:15,text:15},{id:16,text:16},{id:17,text:17},{id:18,text:18},
					{id:19,text:19},{id:20,text:20},{id:21,text:21},{id:22,text:22},{id:23,text:23},{id:24,text:24}]" style="width:50px;"/>
					<span style="line-height: 18px;">点前预定&nbsp;&nbsp;</span>

					<div style="display: none;">
							指定购买日期
						<input class="easyui-numberspinner"  style="width:50px;" name="validOrderDay" data-options="min:1,max:100,editable:true,value:7" value="7">
						<span style="line-height: 18px;">天内有效</span>
					</div>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>购物与自费:</td>
			   	<td>
			   		<div class="rdo-div width100"><label><input type="radio" class="left-block" name="buypay" value="noBuyNoPay" checked/><span class="rdo-label">无购物无自费</span></label></div>
			   		<div class="rdo-div width100"><label><input type="radio" class="left-block" name="buypay" value="noBuyPay"/><span class="rdo-label">无购物有自费</span></label></div>
			   		<div class="rdo-div width100"><label><input type="radio" class="left-block" name="buypay" value="buyNoPay"/><span class="rdo-label">有购物无自费</span></label></div>
			   		<div class="rdo-div width100"><label><input type="radio" class="left-block" name="buypay" value="buyPay"/><span class="rdo-label">有购物有自费</span></label></div>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"></td>
			   	<td>
			   		<div class="tip red">请务必如实选择，否则产品将被强制下架并处罚</div>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><span class="requiredLable" style="color: red">*&nbsp;</span>交通方式:</td>
			   	<td>
			   		<input class="easyui-combobox" id="goWay" name="goWay" data-options="valueField:'id', textField:'text', prompt:'--去程--', required:true, editable:false, data:[{id:'plane',text:'飞机'},{id:'train',text:'火车'},{id:'car',text:'汽车'},
			   		{id:'ship',text:'轮船'},{id:'dongche',text:'动车'},{id:'gaotie',text:'高铁'}]" style="width:80px;"/>
			   		<input class="easyui-combobox" id="backWay" name="backWay" data-options="valueField:'id', textField:'text', prompt:'--返程--', required:true, editable:false, data:[{id:'plane',text:'飞机'},{id:'train',text:'火车'},{id:'car',text:'汽车'},
			   		{id:'ship',text:'轮船'},{id:'dongche',text:'动车'},{id:'gaotie',text:'高铁'}]" style="width:80px;"/>
			   		<input class="easyui-textbox" name="wayDesc" style="width:120px;" >
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="addStep1.clearTrafficWay()">无</a>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><span style="color: red">*&nbsp;</span>线路描述图:</td>
			   	<td>
					<%--<div id="addImgDivBtn">--%>
						<%--<a href="#" id="" class="easyui-linkbutton"  onclick="addStep1.addImgBtn()" >添加图片</a>--%>
						<%--<%--<input type="button" id="uploadButton" value="插入图片" />--%>
						<%--<%--<span class="tip red">上传线路描述图，有利于线路在平台上得到更好的展示</span>--%>
						<%--<input id="filePath" name="filePath" value="<s:property value="filePath"/>" type="hidden"/>--%>
						<%--<input id="childFolder" name="childFolder" value="<s:property value="childFolder"/>" type="hidden"/>--%>
					<%--</div>--%>
                     <div id="imageBox">
                         <div id="imagePanel"></div>
                     </div>
                    <div id="imageContent"></div>
			   	</td>
			</tr>
        	<%--<tr>
			   	<td class="label">收藏数:</td>
			   	<td>
			   		<input class="easyui-numberbox" name="collectSum" style="width:80px;" data-options="min:1, readonly:true">
			   		<span class="tip">首次添加为1000~9999范围内的随机数</span>
			   	</td>
			</tr>
			<tr>
				<td class="label">评论数:</td>
				<td>
					<input class="easyui-numberbox" name="commentSum" style="width:80px;" data-options="min:1, readonly:true">
					<span class="tip">首次添加为1000~9999范围内的随机数</span>
				</td>
			</tr>
			<tr>
				<td class="label">订单数:</td>
				<td>
					<input class="easyui-numberbox" name="orderSum" style="width:80px;" data-options="min:1, readonly:true">
					<span class="tip">首次添加为1000~9999范围内的随机数</span>
				</td>
			</tr>--%>
			<tr>
				<td class="label">产品经理推荐:</td>
				<td>
					<textarea id="line-shortDesc" name="shortDesc" style="width:600px; height:120px;"></textarea>
					<span class="tip">还可以输入<span class="green-bold">6000</span>个字符</span>
				</td>
			</tr>
        	<tr>
			   	<td class="label">支付设置:</td>
			   	<td>
			   		<div class="rdo-div"><label><input type="radio" class="left-block" name="paySet" value="close"/><span class="rdo-label">关闭支付</span></label></div>
			   		<div class="rdo-div"><label><input type="radio" class="left-block" name="paySet" value="earnest"/><span class="rdo-label">需订金预订</span></label></div>
			   		<div class="rdo-div"><label><input type="radio" class="left-block" name="paySet" value="allpay" checked/><span class="rdo-label">需全额支付</span></label></div>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label">在线预订:</td>
			   	<td>
			   		<div class="rdo-div width150"><label><input type="radio" class="left-block" name="confirmAndPay" value="confirm" checked/><span class="rdo-label">需要确认才能付款</span></label></div>
			   		<div class="rdo-div width150"><label><input type="radio" class="left-block" name="confirmAndPay" value="noconfirm"/><span class="rdo-label">本产品在线预订无需确认</span></label></div>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"></td>
			   	<td>
			   		<div class="remark">
				   		<div>产品备注<span class="tip">（仅自己可见）</span></div>
				   		<div>
				   			<input class="easyui-textbox" name="productRemark" data-options="multiline:true,validType:'maxLength[300]'" style="height:60px;width:320px;"></input>
				   			<div class="tip">最多<span class="green-bold">300</span>个字符</div>
				   		</div>
			   		</div>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label">积分兑换:</td>
			   	<td>
			   		<div class="rdo-div width100"><label><input type="radio" class="left-block" name="scoreExchange" value="participation"/><span class="rdo-label">参加积分兑换</span></label></div>
			   		<div class="rdo-div width100"><label><input type="radio" class="left-block" name="scoreExchange" value="no" checked/><span class="rdo-label">不参加</span></label></div>
			   	</td>
			</tr>
		</table>
	    </form>
        <div style="text-align:left;margin:20px;height:30px;">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="addStep1.nextGuide()">保存，下一步</a>
        </div>
    </div>
</div>
</body>
</html>
