<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>第二步价格类型列表</title>
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript">
var FG_DOMAIN = '<s:property value="fgDomain"/>';
</script> 
<link rel="stylesheet" type="text/css" href="/css/line/line/addWizard.css">
<script type="text/javascript" src="/js/line/line/editStep21.js"></script>
</head>
<body style="margin:0;padding:0px 0px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
	<div data-options="region:'center',border:false" style="padding:5px 10px 5px 10px; border-left: 1px solid rgb(204,204,204);">
		<a name="loctop"></a> 
		<form id="editForm" method="post">
			<input type="hidden" value="${lineDisplay.id}" name="line.id" id="hid_line_id">
		<input id="productId" name="productId" type="hidden" value="<s:property value="lineDisplay.id"/>"/>
		<table>
        	<tr>
			   	<td class="label">线路名称:</td>
			   	<td style="line-height: 18px;">
			   		<span id="productName"><s:property value="lineDisplay.name"/></span>
			   	</td>
			</tr>
        	<tr>
			   	<td class="label"><font color="red">*&nbsp;</font>价格类型:</td>
			   	<td>
			   		<table>
			   			<thead>
			   			<tr>
			   				<td width="140">类型名称</td>
			   				<td width="110">成人价</td>
			   				<td width="110">佣金</td>
			   				<td width="80">状态</td>
			   				<td width="140">操作</td>
			   			</tr>
			   			</thead>
			   			<tbody id="typepricetbody">
			   			<s:iterator value="linetypepricesDisplay" status="stuts">                
			                <s:if test="#stuts.odd == true">
			                <tr class="odd">
			                </s:if>
			                <s:else>
			                <tr class="even">
			                </s:else>
		                    	<td><s:property value="quoteName"/></td>
		                    	<td><span class="orange-bold"><span class="minDiscountPrice" linetypepriceId="<s:property value="id"/>">0</span></span>元起</td>
		                    	<td><span class="orange-bold"><span class="minRatePrice" linetypepriceId="<s:property value="id"/>">0</span></span>元起</td>
		                    	<td>
		                    		<s:if test='status == "enable"'>
		                    		正常
		                    		</s:if>
					                <s:else>
					                异常
					                </s:else>
		                    	</td>
		                    	<td align="center">
		                    		<s:if test="lineDisplay.agentFlag!=true">
		                    			<a href='javascript:void(0)' style='color:blue;text-decoration:none;' onclick='editStep21.doEditPrice(<s:property value="id"/>)'>编辑报价</a>
										<a href='javascript:void(0)' style='color:blue;text-decoration:none;' onclick='editStep21.onpenQuantitySalesDialog(<s:property value="id"/>)'>设置拱量</a>
										<a href='javascript:void(0)' style='color:blue;text-decoration:none;' onclick='editStep21.doDelPrice(<s:property value="id"/>)'>删除</a>
		                    		</s:if>
		                    		<s:else>
										<a href='javascript:void(0)' style='color:blue;text-decoration:none;'
										   onclick='editStep21.doViewPrice(<s:property
												   value="lineDisplay.id"/>)'>详细报价</a>
		                    		</s:else>
			                    </td>
		                    </tr>
			            </s:iterator>
			   			</tbody>
			   		</table>
		            <s:if test="lineDisplay.agentFlag!=true">
			   			<a href="javascript:void(0)" onclick="editStep21.prevGuide()" class="easyui-linkbutton line-btn" data-options="" style="width: 587px;margin-top: 5px;">添加价格类型</a>
			   		</s:if>
			   	</td>
			</tr>
        	<tr>
                <td class="label">线路保险:</td>
                <td style="line-height: 18px;">
                    <%--<div id="ice-searcher" style="padding:3px">--%>
                        <%--<span>--%>
                            <%--<select id="search-type">--%>
                                <%--<option value="insurance.name" selected>保险名称</option>--%>
                                <%--<option value="insurance.company">保险公司</option>--%>
                            <%--</select>--%>
                        <%--</span>--%>
                        <%--<input id="search-content" placeholder="输入查询内容"--%>
                               <%--style="line-height:22px;border:1px solid #ccc; padding: 0 3px;">--%>
                        <%--<a href="#" class="easyui-linkbutton" style="width: 80px;"--%>
                           <%-- onclick="editStep21.doSearchInsurance()">查询</a>--%>
                    <%--</div>--%>
                    <div style="width: 100%; height: 300px;">
                        <div id="insuranceDg"></div>
                        <div id="insuranceContent"></div>
                    </div>
                </td>
			</tr>
			<tr>
				<td class="label">发车基本信息:</td>
				<td style="line-height: 18px;">
					<table style="width: 100%;">
						<tr>
							<td class="none-border" style="padding-top: 9px;">
								<input type="text" name="lineDeparture.signInfo" value="${lineDeparture.signInfo}" class="easyui-textbox" style="width: 150px;" data-options="prompt:'请输入举旗标志等信息...'">
							</td>
							<td class="none-border">
								<label>紧急联系人及电话</label>
								<input id="em_contactName" name="lineDeparture.emergencyContact" value="${lineDeparture.emergencyContact}" type="text" class="easyui-textbox" style="width: 100px;" data-options="prompt:'联系人姓名', required:true">
								<input id="em_contactPhone" name="lineDeparture.emergencyPhone" value="${lineDeparture.emergencyPhone}" type="text" class="easyui-textbox" style="width: 100px;" data-options="prompt:'联系电话', validType:'mobile', required:true">
								<input type="hidden" id="hid_departureId" name="lineDeparture.id" value="${lineDeparture.id}">
							</td>
							<td class="none-border">
								<label>
									<input type="hidden" id="hid_autoSendInfo" value="${lineDeparture.autoSendInfo}">
									<input type="checkbox" id="autoSendInfoId" name="lineDeparture.autoSendInfo" value="false"> 可自动发送出团通知
								</label>
							</td>
						</tr>
						<tr>
							<td class="none-border" colspan="3">
								<div style="width: 100%; height: 200px;">
									<div id="line_board_laction"></div>
								</div>

							</td>
							<td class="none-border">
							</td>
							<td class="none-border">
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="label">接送机（站）服务:</td>
				<td style="line-height: 18px;">
					<input type="hidden" id="hid_hasTransfer" value="${lineDeparture.hasTransfer}">
					<label style="margin-right: 10px;">
						<input type="radio" name="lineDeparture.hasTransfer" value="1"> 提供
					</label>
					<label style="margin-right: 10px;">
						<input type="radio" name="lineDeparture.hasTransfer" checked value="0"> 不提供
					</label>
					<label style="margin-right: 10px;">
						<input type="radio" name="lineDeparture.hasTransfer" value="-1"> 有条件提供
					</label>
				</td>
			</tr>
			<tr>
				<td class="label">接车信息备注:</td>
				<td style="line-height: 18px;">
					<input class="easyui-textbox" name="lineDeparture.transferDesc" value="${lineDeparture.transferDesc}" data-options="multiline:true, prompt:'接车信息备注'" style="width: 500px; height: 100px;">
				</td>
			</tr>
			<tr>
				<td class="label">联系人信息:</td>
				<td style="line-height: 18px;">
					<div style="width: 100%; height: 200px;">
						<div id="line_board_contact"></div>
					</div>
				</td>
			</tr>
			<tr>
				<td class="label">线路信息备注:</td>
				<td style="line-height: 18px;">
					<input class="easyui-textbox" name="lineDeparture.departureDesc" value="${lineDeparture.departureDesc}" data-options="multiline:true, prompt:'线路信息备注'" style="width: 500px; height: 100px;">
				</td>
			</tr>
		</table>
	    </form>
	    <div style="text-align:left;margin:20px;height:30px;">
	       	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="editStep21.saveDeparture(3)">保存，下一步</a>
	       	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="editStep21.nextGuide(4)">修改完成</a>
	   	</div>	
	</div>
</div>



<%--发车信息编辑开始--%>
<div id="bard_location_dialog" class="easyui-dialog" style="width:450px;height:350px;padding: 10px;"
	 data-options="resizable:true,modal:true,closed: true">
	<form id="boardLoactionForm" method="post">
		<input type="hidden" id="hid_loaction_departureId" name="lineDepartureInfo.departure.id">
		<table>
			<tr>
				<td>
					<label><font color="red">*&nbsp;</font>发车地点：</label>
				</td>
				<td>
					<input type="text" class="easyui-textbox" name="lineDepartureInfo.originStation" style="width: 200px;" data-options="required:true,prompt:'发车地点'">
				</td>
			</tr>
			<tr>
				<td>
					<label><font color="red">*&nbsp;</font>发车时间：</label>
				</td>
				<td>
					<input type="text" class="Wdate" id="d412" name="lineDepartureInfo.startTime" required="true" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'HH:mm:ss'})" style="border: #95B8E7 1px solid;border-radius: 5px;height: 23px;"/>
				</td>
			</tr>
			<tr>
				<td>
					<label><font color="red">*&nbsp;</font>返回地点：</label>
				</td>
				<td>
					<input type="text" class="easyui-textbox" name="lineDepartureInfo.returnPlace" style="width: 200px;" data-options="required:true,prompt:'返回地点'">
				</td>
			</tr>
			<tr>
				<td>
					<label>备注：</label>
				</td>
				<td>
					<input type="text" class="easyui-textbox" name="lineDepartureInfo.remark" style="width: 250px;height: 100px;" data-options="multiline:true, prompt:'备注'">
				</td>
			</tr>
		</table>
	</form>
</div>
<%--发车信息编辑结束--%>

<%--联系人信息编辑--%>
<div id="bard_contact_dialog" class="easyui-dialog" style="width:450px;height:350px;padding: 10px;"
	 data-options="resizable:true,modal:true,closed: true">
	<form method="post" id="boardContactForm">
		<input type="hidden" id="hid_contactLineId" name="lineContact.line.id">
		<table>
			<tr>
				<td>
					<label>联系人类型：</label>
				</td>
				<td>
					<input type="text" name="lineContact.contactType" class="easyui-combobox" style="width: 200px;"
						   data-options="
						   editable:false,
						   required:true,
						   prompt:'联系人类型',
						   panelHeight:'auto',
						   valueField:'id',
						   textField:'text',
						   data:[{
								'id':'sendHuman',
								'text':'送站/机联系人'
								},{
								'id':'receive',
								'text':'接站/机联系人'
								},{
								'id':'tourGuide',
								'text':'导游',
								},{
								'id':'departurePlace',
								'text':'出发地联系人'
								},{
								'id':'localPlace',
								'text':'当地联系人'
								},{
									'id':'others',
									'text':'其他'
								}]
					"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>联系人：</label>
				</td>
				<td>
					<input type="text" name="lineContact.contactName" class="easyui-textbox" style="width: 200px;" data-options="prompt:'联系人'">
				</td>
			</tr>
			<tr>
				<td>
					<label>联系电话：</label>
				</td>
				<td>
					<input type="text" name="lineContact.contactPhone" class="easyui-textbox" style="width: 200px;" data-options="prompt:'联系电话', validType:'mobile'">
				</td>
			</tr>
			<tr>
				<td>
					<label>备注：</label>
				</td>
				<td>
					<input type="text" class="easyui-textbox" name="lineContact.remark" style="width: 250px;height: 100px;" data-options="multiline:true, prompt:'备注'">
				</td>
			</tr>
		</table>
	</form>
</div>
<%--联系人信息编辑结束--%>


<div id="quantitySales_dialog" class="easyui-dialog" style="left:0px;top:10px;width:700px;height:450px;"
	 data-options="resizable:true,modal:true,closed: true">
	<iframe name="editIframe" id="editIframe1" scrolling="no" frameborder="0"  style="width:100%;height:100%;"></iframe>
</div>
	
</body>
</html>
