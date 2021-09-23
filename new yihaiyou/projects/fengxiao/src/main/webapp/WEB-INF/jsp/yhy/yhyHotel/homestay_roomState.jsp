<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
	<%@include file="../../common/yhyheader.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/yhy/yhyHotel/homestay_roomState.css">
	<link rel="stylesheet" type="text/css" href="/css/yhy/yhyHotel/homestay_index.css">
    <title>民宿房态管理-一海游商户系统</title>
</head>
<body class="homestayRoomState includeTable">
<%@include file="../../yhy/yhyCommon/common_header.jsp" %>
	<div class="roomset financeList">
		<div class="selectBar">
			<div class="selB_1 businesOrderNum">
				<div class="form-group yhy-form-group">
				  	<input type="text" name="searchContent" class="form-control" placeholder="手机/订单号">
				</div>
			</div>
			<div class="selB_1 orderTime">
				<span class="orderTime_1">入住时间</span>
				<div class="form-group yhy-form-group">
				  	<input type="text" class="form-control" name="startDate">
				</div>
				<span class="orderTime_2">到</span>
				<div class="form-group yhy-form-group">
				  	<input type="text" class="form-control" name="endDate">
				</div>
			</div>
			<div class="selB_1 orderState">
				<span class="orderState_1">状态</span>
				<select id="searchStatusSel" data-btn-class="btcombo btn-default status-sel" name="status">
					<option value="">全部</option>
					<option value="SUCCESS">未入住</option>
					<option value="CHECKIN">已入住</option>
					<option value="CHECKOUT">已退房</option>
				</select>
			</div>
			<div class="selB_1 search">
				<div class="btn-group">
  					<button type="button" class="btn btn-default" id="searchRoomStateBtn">查询</button>
				</div>
			</div>
		</div>
		<div class="financeTable">
			<table id="hotelRoomStateList" class="table table-striped yhy-common-table">
				<thead>
					<tr>
						<th class="orderNum">订单号</th>
						<%--<th class="bookman">预订人</th>--%>
						<th class="phoneNum">手机号</th>
						<th class="inDay">入住日期</th>
						<th class="outDay">离店时间</th>
						<th class="roomType">房型</th>
						<th class="roomNum">房号</th>
						<th class="checkState">状态</th>
						<th class="operation">操作</th>
					</tr>
				</thead>
			</table>
		</div>

		<!-- 办理入住 -->
        <div class="checkin">
            <form role="form" id="check_in_form" action="" method="post" enctype="multipart/form-data">
                <input type="hidden" name="orderDetailId">
                <input type="hidden" name="capacity">
                <input type="hidden" name="roomNums">
                <div class="checkhead">办理入住<span class="checkclose"></span></div>
                <div class="checkbody" id="check_in_body">
                    <div class="message1 clearfix">
                        <%--<div class="form-group mesdiv bookName">--%>
                            <%--<input type="text" readonly="readonly" name="recName" class="form-control" placeholder="预订人姓名">--%>
                        <%--</div>--%>
                        <div class="form-group mesdiv bookPhone">
                            <input type="text" readonly="readonly" name="mobile" class="form-control" placeholder="手机号码">
                        </div>
                        <div class="form-group mesdiv validateCode">
                            <label class="leftTitle">入住验证:</label>
                            <input type="text" name="validateCode" class="form-control" placeholder="订单验证码">
                            <button type="button" class="btn btn-danger btn-sm" id="validate_checkin_btn">检查验证码</button>
                        </div>
                        <div class="form-group mesdiv beizhu">
                            <textarea class="form-control" name="remark" placeholder="备注"></textarea>
                        </div>
                    </div>
                    <div class="hotel-room-group">
                        <div class="hotel-room-item">
                            <div class="message1 message3 clearfix pb0">
                                <div class="form-group mesdiv bookName">
                                    <input type="text" readonly="readonly" name="playDate" class="form-control" placeholder="入住日期">
                                </div>
                                <div class="mesdiv blankbox">至</div>
                                <div class="form-group mesdiv normalbox">
                                    <input type="text" readonly="readonly" name="leaveDate" class="form-control" placeholder="退房日期">
                                </div>
                                <div class="mesdiv blankbox days"></div>
                                <div class="form-group mesdiv normalbox">
                                    <input type="text" readonly="readonly" name="seatType" class="form-control" placeholder="房型名称">
                                </div>
                                <div class="mesdiv latearive ml">
                                    <select class="form-control hotel-room-no-sel" data-name="roomNo"></select>
                                </div>
                            </div>
                            <div class="message1 message2 clearfix">
                                <div class="hotel-member-group">
                                    <div class="oneperson hotel-member-item">
                                        <div class="form-group mesdiv bookName">
                                            <input type="text" data-name="name" class="form-control" placeholder="入住人姓名">
                                        </div>
                                        <div class="mesdiv latearive">
                                            <select class="form-control" data-name="idType">
                                                <option value="IDCARD" selected="selected">二代身份证</option>
                                                <option value="PASSPORT">护照</option>
                                                <option value="HKMP">港澳通行证</option>
                                                <option value="TWP">台湾通行证</option>
                                                <option value="OTHER">其他</option>
                                            </select>
                                        </div>
                                        <div class="form-group mesdiv IDnum">
                                            <input type="text" data-name="idNumber" class="form-control" placeholder="证件号码">
                                        </div>
                                        <div class="mesdiv cut del-member">－</div>
                                    </div>
                                </div>
                                <div class="mesdiv add add-member">＋入住人</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="checkfooter">
                    <div class="footer-btn-area">
                        <button type="button" class="btn sureBtn" data-loading-text="确认中...">确认入住</button>
                    </div>
                    <div class="count">已支付<span class="money"></span></div>
                </div>
            </form>
        </div>

		<!-- 离店结算 -->

		<div class="checkout">
            <form id="check_out_form">
                <input type="hidden" name="orderDetailId">
                <div class="checkhead">离店结算<span class="checkclose"></span></div>
                <div class="checkbody">
                    <div class="message1 clearfix">
                        <%--<div class="mesdiv bookName recName"></div>--%>
                        <div class="mesdiv bookPhone mobile"></div>
                        <span class="text">验证码:</span>
                        <div class="mesdiv validateCode" style="width: 300px;"></div>
                        <div class="form-group mesdiv beizhu">
                            <textarea class="form-control" placeholder="备注" name="remark"></textarea>
                        </div>
                    </div>
                    <div class="hotel-room-group">
                        <div class="hotel-room-item">
                            <div class="message1 message3 clearfix">
                                <div class="mesdiv bookName playDate"></div>
                                <div class="mesdiv blankbox">至</div>
                                <div class="mesdiv normalbox leaveDate"></div>
                                <div class="mesdiv blankbox nights"></div>
                                <div class="mesdiv normalbox seatType"></div>
                                <div class="mesdiv latearive checkreadolny ml roomNo"></div>
                            </div>
                            <div class="message1 message2 clearfix">
                                <div class="hotel-member-group">
                                    <div class="oneperson hotel-member-item">
                                        <div class="mesdiv bookName name"></div>
                                        <div class="mesdiv latearive checkreadolny idType"></div>
                                        <div class="mesdiv IDnum idNumber"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="checkfooter">
                    <div class="footer-btn-area">
                        <button type="button" class="btn sureBtn" data-loading-text="确认中...">确认离店</button>
                    </div>
                    <div class="count">已支付 <span class="money"></span></div>
                </div>
            </form>
		</div>

		<!-- 入住信息 -->

        <div class="checkmess">
            <form id="check_detail_form">
                <div class="checkhead">入住信息<span class="checkclose"></span></div>
                <div class="checkbody">
                    <div class="message1 clearfix">
                        <%--<div class="mesdiv bookName recName"></div>--%>
                        <div class="mesdiv bookPhone mobile"></div>
                        <span class="text">验证码:</span>
                        <div class="mesdiv validateCode" style="width: 300px;"></div>
                        <div class="mesdiv beizhu bzmess remark"></div>
                    </div>
                    <div class="hotel-room-group">
                        <div class="hotel-room-item">
                            <div class="message1 message3 clearfix">
                                <div class="mesdiv bookName playDate"></div>
                                <div class="mesdiv blankbox">至</div>
                                <div class="mesdiv normalbox leaveDate"></div>
                                <div class="mesdiv blankbox nights"></div>
                                <div class="mesdiv normalbox seatType"></div>
                                <div class="mesdiv normalbox ml roomNo"></div>
                            </div>
                            <div class="message1 message2 clearfix">
                                <div class="hotel-member-group">
                                    <div class="oneperson hotel-member-item">
                                        <div class="mesdiv bookName name"></div>
                                        <div class="mesdiv latearive checkreadolny idType"></div>
                                        <div class="mesdiv IDnum idNumber"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="checkfooter">
                    <div class="footer-btn-area">
                        <button type="button" class="btn sureBtn">返回</button>
                    </div>
                    <div class="count">已支付 <span class="money"></span></div>
                </div>
            </form>
        </div>
    </div>
<div class="shadow disn"></div>
<%@include file="../../yhy/yhyCommon/common_footer.jsp" %>
</body>
<%@include file="../../common/yhyfooter.jsp" %>
<script type="text/javascript" src="/js/yhy/yhyHotel/homestay.js"></script>
<script type="text/javascript" src="/js/yhy/yhyHotel/homestay_roomstate.js"></script>
</html>