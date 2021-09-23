/**
 * Created by vacuity on 15/11/27.
 */

$.extend($.fn.validatebox.defaults.rules, {
    validateSceneStrExist: {
        validator: function (value, param) {
            var b = false;
            if (value) {
                var accountId = $('#accountId').val();
                var qrcodeId = $('#qrcodeId').val();
                console.log(qrcodeId);
                $.ajax({
                    tyep: "post",
                    dataType: "json",
                    async: false,//是否异步执行（该属性已被遗弃）
                    url: "/wechat/wechatQrcode/validateSceneStr.jhtml?accountId="+accountId+"&qrcode="+qrcodeId,
                    data: "sceneStr="+value,
                    success: function (result) {
                        b = result.notExisted;
                    },
                    error: function (errorMSG) {
                        b = false;
                    }
                });
            }
            return b;
        },
        message: '该参数代码已被使用'
    }
});


var WechatQrcode = {


    init: function() {
        WechatQrcode.initDg();
    },


    initDg: function() {

       $('#dg_qrcode').datagrid({
            url:'/wechat/wechatQrcode/getList.jhtml?accountId=' + $("#accountId").val(),
            pagination: true,
            pageList: [10, 20],
            border: false,
            fit:true,
            rownumbers: true,
            fitColumns: true,
            singleSelect: true,
            checkOnSelect: true,
            columns:[[
                {field:'path',title:'二维码',width:110,sortable: false, align:'center',
                    formatter:function(value, rowData, index){
                        var imgA = '<a href="#" id="tip-'+ rowData.id +'" class="easyui-tooltip"><img class="qrcode-img" src="'+ value +'"></a>';

                        return imgA;
                    }
                },
                {field:'name',title:'二维码名称',width:100,sortable: false},
                {field:'sceneStr',title:'参数代码',width:100,sortable: false},
                {field:'opt',title:'操作',width:120, align:'center', sortable: false,
                    formatter:function(value, row, index){
                    var edit = '<a id="btn" href="#" class="easyui-linkbutton" onclick="WechatQrcode.editQrcode('+ row.id +')" data-options="iconCls:\'icon-search\'">修改</a>';
                    var del = '<a id="btn" href="#" class="easyui-linkbutton" onclick="WechatQrcode.delQrcode('+ row.id +')" data-options="iconCls:\'icon-search\'">删除</a>';
                    var down = '<a id="btn" href="/wechat/wechatQrcode/download.jhtml?id='+ row.id +'" class="easyui-linkbutton" data-options="iconCls:\'icon-search\'">下载</a>';
                    var space = "&nbsp;&nbsp;";
                    return edit + space + del + space + down;
                } }
            ]],
            toolbar:"#toolbar",
           onLoadSuccess:function(data) {

               var dataRows = data.rows;
               if (dataRows.length > 0) {

                   $.each(dataRows, function(i, perValue) {

                       $("#tip-"+ perValue.id+"").tooltip({
                           position: 'top',
                           content: '<img width="100" height="100" src="'+ perValue.path +'">',
                           onShow: function(){
                               $(this).tooltip('tip').css({
                                   backgroundColor: '#666',
                                   borderColor: '#666'
                               });
                           }
                       });

                   });
               }

           }
        });

    },


    addQrcode: function () {
        $("#edit-panel").dialog({
            title:'新增二维码',
            modal:true,
            top:"20",
            left:"100",
            onClose:function(){
                $("#edit-form").form('clear');
            },
            buttons:'#btn-div'
        });
        $("#edit-panel").dialog("open");
    },

    delQrcode:function(id) {

        var url = "/wechat/wechatQrcode/delWechatQrcode.jhtml?id="+id;
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中，请稍后...'
        });
        $.post(url,
            function(result){
                if(result.success){
                    $.messager.progress("close");
                    show_msg("删除成功!");
                    $('#dg_qrcode').datagrid("load");
                }else{
                    $.messager.progress("close");
                    show_msg(result.errorMsg);
                }
            }
        );

    },

    downQrcode:function(id) {

        var url = "/wechat/wechatQrcode/download.jhtml?id="+id;
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中，请稍后...'
        });
        $.post(url,
            function(result){
                if(result.success){
                    $.messager.progress("close");
                    show_msg("删除成功!");
                    $('#dg_qrcode').datagrid("load");
                }else{
                    $.messager.progress("close");
                    show_msg(result.errorMsg);
                }
            }
        );


    },

    editQrcode:function(id){
        var editUrl="/wechat/wechatQrcode/getDetail.jhtml?id="+id;
        $("#edit-form").form('load',editUrl);
        $("#edit-panel").dialog({
            title:'编辑二维码',
            modal:true,
            top:"20",
            left:"100",
            onClose:function(){
                $("#edit-form").form('clear');
            }
        });
        $("#edit-panel").dialog("open");
    },

    closeEdit: function () {
        $("#edit-form").form('clear');
        $("#edit-panel").dialog("close");
    },

    makeQrcode: function () {
        var accountId = $("#accountId").val();
        var sceneStr = $("#sceneStr").val();
        var name = $("#name").val();
        var id = $("#qrcodeId").val();
        if(sceneStr == null || sceneStr == ""){
            show_msg("请完善参数代码！");
            return;
        }
        if(name == null || name == ""){
            show_msg("请完善二维码名称！");
            return;
        }


        var isValid = $("#edit-form").form('validate');

        if (!isValid) {
            show_msg("页面数据有问题，请检查数据！");
            return;
        }
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中，请稍后...'
        });

        $.post('/wechat/wechatQrcode/doMakeQrcode.jhtml', {
                'accountId': accountId,
                'sceneStr': sceneStr,
                'name': name,
                'id': id
            }, function(result) {
                WechatQrcode.closeEdit();
                if(result.success){
                    $.messager.progress("close");
                    show_msg("二维码生成成功");
                    $('#dg_qrcode').datagrid("load");
                }else{
                    $.messager.progress("close");
                    show_msg(result.errorMsg);
                }
        });
    }
}


$(function(){
    WechatQrcode.init();
})