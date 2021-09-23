<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="../common/common141.jsp" %>
    <script type="text/javascript" src="/js/common/areaSelectDg.js"></script>
    <script type="text/javascript" src="/js/area.js"></script>
    <title>美食列表</title>
</head>
<body style="margin:0;padding:0px 3px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
    <div id="toolbar" style="padding:10px 0 5px 10px">
        <table id="qryCondition">
            <tr>
                <td width="80" align="right">城市:</td>
                <td>
                    <input type="hidden" id="qry_cityCode">
                    <input type="hidden" id="qry_isChina">
                    <input id="qryCity" class="easyui-textbox"
                           data-options="buttonText:'清空',editable:false,prompt:'点击选择城市'"
                           style="width:200px" data-country="" data-province="" data-city="">
                </td>
                <td width="80" align="right">筛选:</td>
                <td>
                    <select class="easyui-combobox" name="condition" id="qry_condition" style="width: 100px;">
                        <option value="0" selected="selected">全部</option>
                        <option value="1">已关联餐厅</option>
                        <option value="2">未关联餐厅</option>
                    </select>
                </td>
                <td width="80" align="right">封面筛选:</td>
                 <td>
                    <select class="easyui-combobox" name="hasCoverImage" id="hasCoverImage" style="width: 100px;">
                        <option value="0" selected="selected">全部</option>
                        <option value="1">有封面</option>
                        <option value="2">无封面</option>
                    </select>
                </td>
                <td width="80" align="right">名称:</td>
                <td><input class="easyui-textbox" id="qry_name" name="name"
                           data-options="prompt:'请输入美食名称'" style="width:200px;"></td>
                <td></td>

                <td></td>
                <td>
                    <a id="queryBtn" href="javascript:void(0)" class="easyui-linkbutton" style="width:80px; margin-left: 12px;"
                       onClick="query()">查询</a>
                </td>
                <td>
                    <a id="resetBtn" href="javascript:void(0)" class="easyui-linkbutton" style="width:80px; margin-left: 12px;"
                       onClick="reset()">重置</a>
                </td>
            </tr>
        </table>
        <div style="padding:2px 44px;">
            <a href="#" class="easyui-linkbutton" id="addButton" style="margin-left: 12px;" onclick="add()">新增美食</a>
            <a href="#" class="easyui-linkbutton" id="addButton2"  style="margin-left: 12px;"       onclick="addRestaurant()">新增餐厅</a>
        </div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="cityCodes"></div>
        <table id="qryResult"></table>
    </div>

</div>

</body>
</html>
<script type="text/javascript">
    $(pageInit);
    function pageInit() {
        initProvince();
    }

    // 查询
    function query() {
        $("#qryResult").datagrid("load", {});
    }

    // 重置
    function reset() {
        $('#qryCondition').form('clear');
    }

    function changeStatus(id, name, btnTxt) {
        var sure = confirm(" 确定要" + btnTxt + ": " + name + " 吗?");
        var status = 1;
        if (sure) {
            $('#status_' + id)[0].innerHTML = '正在' + btnTxt;
            $('#status_' + id).css("background-color", "orange");
            $('#status_' + id).css("color", "white");
            if (btnTxt == "下架") {
                status = 2;
            }
            $.get("/restaurant/delicacy/changeStatus.jhtml?delicacyId=" + id + "&status=" + status , function (result) {
                if (result.msg = "scccess") {
                    setTimeout(function () {
                        $('#status_' + id)[0].innerHTML = (btnTxt == '上架') ? '已上架' : '已下架';
                        $('#status_' + id).css("background-color", "");
                        $('#status_' + id).css("color", btnTxt == "上架" ? "green" : "gray");
                        $('#opt_' + id)[0].innerHTML = btnTxt == "上架" ? "下架" : "上架";
                        $('#opt_' + id).css("color", btnTxt == "上架" ? "gray" : "green");
                        var txt = (btnTxt == "上架") ? "下架" : "上架";
                        $('#opt_' + id).attr('onclick', 'changeStatus(' + id + ',\"' + name + '\",\"'+ txt + '\")');
                    }, 200);

                } else {
                    $.messager.alert('上下架提示', '操作失败! 检查参数!', 'error');
                }
            });
        }
    }

    // 页面加载后相关设置项
    $(function () {
        // 构建表格
        $('#qryResult').datagrid({
            fit: true,
//            title: "美食列表",
            thread: '',
            url: '/restaurant/delicacy/list.jhtml',
            border: true,
            singleSelect: false,
            striped: true,
            pagination: true,
            remoteSort: false,
            multiSort: true,
            pageSize: BizConstants.PAGESIZE,
            pageList: BizConstants.PAGELIST,
            columns: [[{
                field: 'ck',
                checkbox: true
            },{
                field: 'id',
                title: 'ID',
                sortable: true,
                width: 50,
                align: "center"
            }, {
                field: 'cover',
                title: '图片',
                formatter: imgFormatter,
                width: 100
            }, {
                field: 'name',
                title: '名称',
                sortable: true,
                width: 120,
                align: "center"
            }, {
                field: 'price',
                title: '价格',
                sortable: true,
                width: 60,
                align: "center"
            }, {
                field: 'city.fullPath',
                title: '所在地',
                width: 150,
                align: "center"
            }, {
                field: 'extend.introduction',
                title: '简介',
                sortable: true,
                width: 850
            }, {
                field: "status",
                title: "状态",
                align: "center",
                width: 160,
                formatter: statusFormatter
            }, {
                field: "OPT",
                title: "操作",
                align: "center",
                width: 250,
                formatter: optFormatter
            }
            ]],
            toolbar: '#toolbar',
            onBeforeLoad: function (data) {   // 查询参数

                data.foodName = $("#qry_name").textbox("getValue");
                var regionCode = $('#qry_cityCode').val();
                if (regionCode && regionCode!= "") {
                    data.cityId = regionCode;
                }
                data.isChina = $("#qry_isChina").val();
//                var cityCode = $('#qry_city').combobox('getValue');
//                if (cityCode == '') {
//                    cityCode = $('#qry_province').combobox('getValue');
//                    data.cityId = cityCode.substr(0, 2);
//                }
//                else {
//                    data.cityId = cityCode.substr(0, 4);
//                }
                var condition = $('#qry_condition').combobox('getValue');
              /*   console.log("typeof(condition)="+typeof(condition));
                console.log("condition="+condition); */
                switch (condition) {
                    case '1':
                        data.relationed = -1;
                      /*   console.log("data.relationed="+data.relationed); */
                        break;
                    case '2':
                        data.relationed = 1;
                      /*   console.log("data.relationed="+data.relationed); */
                        break;
                }
                var hasCoverImage = $('#hasCoverImage').combobox("getValue");
                switch(hasCoverImage){
                	case '1':
                		data.needCoverImage = 1;
                		break;
                	case '2':
                		data.notNeedCoverImage = 1;
                		break;
                }
                console.log("data.relationed="+data.relationed);
            }, onLoadSuccess: init
        });
    });
    function init() {
        initTips();
//        initAreaShow();
    }
    // 新增
    function add() {
        window.parent.addTab('新增美食', "/restaurant/delicacy/add.jhtml");
    }
    // 新增餐厅
    function addRestaurant() {
        window.parent.addTab('新增餐厅', "/restaurant/delicacyRestaurant/add.jhtml");
    }
    // 修改
    function edit(id) {
        window.parent.addTab('编辑美食', "/restaurant/delicacy/edit.jhtml?id=" + id);
    }

    function optFormatter(value, rowData, rowIndex) {
        var btnEdit = "<a id='opt_" + rowData.id + "' href='javascript:void(0)' ";
        if (rowData.status == 1) {
            btnEdit += "style='color:gray;text-decoration: underline;' ";
            btnEdit += " onclick='changeStatus(" + rowData.id + ",\"" + rowData.name + "\",\"下架\")'>下架</a>";
        } else if (rowData.status == 2) {
            btnEdit += "style='color:green;text-decoration: underline;' ";
            btnEdit += " onclick='changeStatus(" + rowData.id + ",\"" + rowData.name + "\",\"上架\")'>上架</a>";
        }
        btnEdit += "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='edit(" + rowData.id + ")'>编辑</a>";

        btnEdit += "&nbsp;<a href='#' style='color:blue;text-decoration: underline;'" +
                " id='relation_" + rowData.id + "' class='easyui-tooltip1' data-p1='" + rowIndex + "'>携程餐厅</a>";
        btnEdit += "&nbsp;<a href='#' style='color:blue;text-decoration: underline;'" +
                " id='relation_diy_" + rowData.id + "' class='easyui-tooltip2' data-p2='" + rowIndex + "'>自定义餐厅</a>";
        return btnEdit;
    }

    function statusFormatter(value, rowData, rowIndex) {
        var btnEdit = "<a id='status_" + rowData.id + "'";
        if (rowData.status == 1) {
            btnEdit += " style='color:green;text-decoration: none;'>已上架</a>";
        }
        else if (rowData.status == 2) {
            btnEdit += " style='color:gray;text-decoration: none;'>已下架</a>";
        }
        return btnEdit;
    }

    function imgFormatter(value, rowData, rowIndex) {
        if (rowData.cover != undefined && rowData.cover != '') {
            return '<img src="' + BizConstants.QINIU_DOMAIN + rowData.cover.replace('-w300h185', '') + '?imageView2/1/w/100/h/80/q/75' + '"/>';
        }
        return '<span style="color: red">无</span>';
    }
    function initTips() {
        $('#qryResult').datagrid('getPanel').find('.easyui-tooltip1').each(function () {
            var index = parseInt($(this).attr('data-p1'));
            $(this).tooltip({
                content: $('<div>加载中</div>'),
                hideEvent: 'none',
                onUpdate: function (cc) {
                    var row = $('#qryResult').datagrid('getRows')[index];
                    var keyword = encodeURI(encodeURI(row.name));
                    cc.panel({
                        title: row.name + "关联",
                        closable: true,
                        width: 430,
                        height: 'auto',
                        border: false,
                        href: '/restaurant/delicacyRestaurant/getSimilar.jhtml?cityId=' + row.city.cityCode + '&delicacyId=' + row.id + '&keyword=' + keyword,
                        onLoad: function () {
                            var row = $('#qryResult').datagrid('getRows')[index];
                            $('#search_' + row.id).click(function () {
                                var sname = $('#searchValue_' + row.id).val();
                                cc.panel({
                                    title: row.name + "关联",
                                    closable: true,
                                    width: 430,
                                    height: 'auto',
                                    border: false,
                                    href: '/restaurant/delicacyRestaurant/getSimilar.jhtml?cityId=' + row.city.cityCode + '&delicacyId=' + row.id + '&keyword=' + sname,
                                    onLoad: function () {
                                        var row = $('#qryResult').datagrid('getRows')[index];
//                                        $('#search_' + row.id).click(function () {
//
//                                        });
                                        $('#info_' + row.id).css('color', 'green');
                                        $('#ck_' + row.id).attr('checked', true);
                                    }
                                });
                            });
                            $('#reflash_' + row.id).click(function () {
                                var sname = $('#searchValue_' + row.id).val();
                                cc.panel({
                                    title: row.name + "关联",
                                    closable: true,
                                    width: 430,
                                    height: 420,
                                    border: false,
                                    href: '/restaurant/delicacyRestaurant/getSimilar.jhtml?cityId=' + row.city.cityCode + '&delicacyId=' + row.id + '&keyword=' + sname,
                                    onLoad: function () {
                                        var row = $('#qryResult').datagrid('getRows')[index];
//                                        $('#search_' + row.id).click(function () {
//
//                                        });
                                        $('#info_' + row.id).css('color', 'green');
                                        $('#ck_' + row.id).attr('checked', true);
                                    }
                                });
                            });
                            $('#info_' + row.id).css('color', 'green');
                            $('#ck_' + row.id).attr('checked', true);
                        }
                    });
                },
                onShow: function () {
                  var t = $(this);
                    t.tooltip('tip').mouseleave(function () {//鼠标离开tip
                        t.tooltip('hide');
                    });
                    t.tooltip('tip').focus().blur(function () {//失去焦点
                        t.tooltip('hide');
                    });
                    t.tooltip('tip').mouseenter(function () {//当鼠标移入tip，unbind，去掉blur的效果，兼容Chrome失焦
                        t.tooltip('tip').unbind();
                        t.tooltip('tip').mouseleave(function () {
                            t.tooltip('hide');
                        })
                    });
                },
                position: 'right'
            });
        });
        $('#qryResult').datagrid('getPanel').find('.easyui-tooltip2').each(function () {
            var index = parseInt($(this).attr('data-p2'));
            $(this).tooltip({
                content: $('<div>加载中</div>'),
                hideEvent: 'none',
                onUpdate: function (cc) {
                    var row = $('#qryResult').datagrid('getRows')[index];
                    var keyword = encodeURI(encodeURI(row.name));
                    var t = $(this);
                    cc.panel({
                        title: row.name + "关联",
                        closable: true,
                        width: 430,
                        height: 'auto',
                        border: false,
                        href: '/restaurant/delicacyRestaurant/getDiySimilar.jhtml?cityId=' + row.city.cityCode + '&delicacyId=' + row.id + '&keyword=' + keyword,
                        onLoad: function () {
                            var row = $('#qryResult').datagrid('getRows')[index];
                            /* $('#search_diy_' + row.id).click(function () {
                                var sname = encodeURI(encodeURI($('#searchValue_diy_' + row.id).val()));
                                cc.panel({
                                    title: row.foodName + "关联",
                                    closable: true,
                                    width: 430,
                                    height: 'auto',
                                    border: false,
                                    href: '${ctxPath}/mgrer/delicacy/restaurant/getDiySimilar?cityId=' + row.cityId + '&delicacyId=' + row.id + '&keyword=' + sname,
                                    onLoad: function () {
                                        var row = $('#qryResult').datagrid('getRows')[index];
//                                        $('#search_diy_' + row.id).click(function () {
//
//                                        });
                                        $('#info_' + row.id).css('color', 'green');
                                        $('#ck_' + row.id).attr('checked', true);
                                    }
                                });
                                t.tooltip('update');
                            }); */
                           /*  $('#reflash_diy_' + row.id).click(function () {
                                var sname = $('#searchValue_diy_' + row.id).val();
                                cc.panel({
                                    title: row.foodName + "关联",
                                    closable: true,
                                    width: 430,
                                    height: 420,
                                    border: false,
                                    href: '${ctxPath}/mgrer/delicacy/restaurant/getDiySimilar?cityId=' + row.cityId + '&delicacyId=' + row.id + '&keyword=' + sname,
                                    onLoad: function () {
                                        var row = $('#qryResult').datagrid('getRows')[index];
//                                        $('#search_diy_' + row.id).click(function () {
//
//                                        });
                                        $('#info_' + row.id).css('color', 'green');
                                        $('#ck_' + row.id).attr('checked', true);
                                    }
                                });
                                t.tooltip('update');
                            }); */
                            $('#info_' + row.id).css('color', 'green');
                            $('#ck_' + row.id).attr('checked', true);
                        }
                    });
                },
                onShow: function () {
                    var t = $(this);
                    t.tooltip('tip').mouseleave(function () {//鼠标离开tip
                        t.tooltip('hide');
                    	t.tooltip('update');
                    });
                    t.tooltip('tip').focus().blur(function () {//失去焦点
                        t.tooltip('hide');
                    	t.tooltip('update');
                    });
                    t.tooltip('tip').mouseenter(function () {//当鼠标移入tip，unbind，去掉blur的效果，兼容Chrome失焦
                        t.tooltip('tip').unbind();
                        t.tooltip('tip').mouseleave(function () {
                            t.tooltip('hide');
                            t.tooltip('update');
                        })
                    }); 
                },
                position: 'right'
            });
        });
    }
</script>