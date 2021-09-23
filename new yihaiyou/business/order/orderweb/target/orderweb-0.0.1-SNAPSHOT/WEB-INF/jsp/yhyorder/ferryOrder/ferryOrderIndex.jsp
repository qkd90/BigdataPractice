<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/10/8
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>邮轮订单管理</title>
    <%@ include file="../../common/common141.jsp" %>
    <script type="text/javascript" src="/js/yhyorder/ferry/ferryOrderIndex.js"></script>
    <style>
        .labelClass {
            float: left;
            margin-right: 5px;
            width: 60px;
            text-align: right;
        }

        .row_hd {
            height: 27px;
            width: 100%;
            line-height: 27px;
            border-bottom: 1px solid #ddd;
            background: #f4f4f4;
            margin: 0px 15px 10px 5px;
            padding-left: 5px;
            padding-right: 5px;
            font-weight: 700;
            color: #666;
        }

        .calender_class {
            border: #999 1px solid;
            width: 117px;
            margin-left: -4px;
            height: 22px;
            background: url("/jquery-easyui-1.4.1/themes/icons/date.png") no-repeat scroll right center transparent;
            border-radius: 5px 5px 5px 5px;
            border: 1px solid #95B8E7;
        }
    </style>
</head>
<body style="background-color: white">
    <div title="船票订单" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
        <!-- 表格 始 -->
        <div id="client_ship_tool">
            <form action="" id=client_ship_form">
                <div style="padding:5px 5px 3px 5px;">
                    <input id="client_ship_keyword" class="easyui-textbox" data-options="prompt:'订单编号，航班号，航线名称'" style="width:200px;"/>
                    <input id="client_ship_status" class="easyui-combobox" data-options="prompt:'订单状态',editable:false,
                        valueField: 'id',
                        textField: 'value',
                         data: [
                                {id:'SUCCESS',
                                value:'交易完成'
                                },
                                {id:'CONFIRMED',
                                value:'已确认'
                                },
                                {id:'FAILED',
                                value:'失败'
                                },
                                {id:'PARTIAL_FAILED',
                                value:'部分失败'
                                },
                                {id:'UNCONFIRMED',
                                value:'等待确认'
                                },

                                {id:'WAIT',
                                value:'等待支付'
                                },
                                {id:'PAYED',
                                value:'已支付'
                                },
                                {id:'REFUND',
                                value:'已退款'
                                },
                                {id:'CANCELED',
                                value:'已取消'
                                },
                                {id:'CANCELING',
                                value:'取消中'
                                },

                                {id:'DELETED',
                                value:'已删除'
                                },
                                {id:'CLOSED',
                                value:'已关闭'
                                },
                                {id:'INVALID',
                                value:'无效订单'
                                },
                                {id:'PROCESSING',
                                value:'处理中'
                                },
                                {id:'PROCESSED',
                                value:'已处理'
                                }
                                ]
                        " style="width:100px;">
                    <input id="start_ship_createTime" class="easyui-datebox" data-options="prompt:'下单日期始'" style="width:120px;"/>
                    -
                    <input id="end_ship_createTime" class="easyui-datebox" data-options="prompt:'下单日期止'" style="width:120px;"/>
                    <input id="start_ship_useTime" class="easyui-datebox"
                           data-options="prompt:'发班日期'" style="width:120px;"/>&nbsp;-
                    <input id="end_ship_useTime" class="easyui-textbox"
                           data-options="prompt:'发班时段'" style="width:150px;"/>

                    <a href="javascript:void(0)" id="client_ship_search" class="easyui-linkbutton"  onclick="FerryOrderIndex.doShipSearch('show_dg')">查询</a>
                    <a href="javascript:void(0)" id="client_ship_clear" class="easyui-linkbutton"  onclick="FerryOrderIndex.clearShipForm()">重置</a>
                </div>
            </form>
        </div>
        <!-- 表格工具条 终 -->
        <!-- 表格 始 -->
        <div data-options="region:'center',border:false">
            <table id="client_ship_dg"></table>
        </div>
        <!-- 表格 终 -->
    </div>
    <!-- 编辑窗口 -->
    <div id="editPanel" class="easyui-dialog" title="录入订单信息"
         data-options="fit:true,resizable:false,modal:true,closed:true">
        <iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"  style="width:100%;height:600px;"></iframe>
    </div>
</body>
</html>
