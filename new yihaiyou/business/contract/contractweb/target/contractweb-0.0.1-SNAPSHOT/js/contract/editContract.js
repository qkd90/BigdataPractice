/**
 * Created by dy on 2016/9/1.
 */
var ContractEdit = {
    fileFolder:'contract/appendice',
    limitNum: 5000,	// 字数限制仅文本
    init: function() {
        ContractEdit.initContent();
        ContractEdit.initCom();
        ContractEdit.initAppendice();
        ContractEdit.initFrameHeight();

        if(!$("#hid_unitAid").val()) {
            ContractEdit.initUnitACompany();
        }
    },

    initAppendice: function() {
        if ($("#contractId").val()) {
            var url = "/contract/contract/getContractAppendiceList.jhtml";
            $.post(url,
                {'contract.id': $("#contractId").val()},
                function(data) {
                    if (data.success) {
                        $.each(data.appendiceList, function(i, perValue) {
                            ContractEdit.addTr(perValue);
                        });

                    } else {
                    }
                }
            );
        }
    },

    appendTr: function(index, result) {
        var html_tr = "";
        html_tr += '<tr id="tr_'+ index +'" class="appendice_class" rows="'+ index +'">';
        html_tr += '<td width="320px">'+ result.name +'</td>';
        html_tr += '<input type="hidden" value="'+ result.name +'">' ;
        html_tr += '<input type="hidden" value="'+ result.path +'">' ;
        html_tr += '<input type="hidden" value="'+ result.type +'">' ;
        html_tr += '<td align="center">' ;
        if (result.type == 'image') {
            html_tr += '<a href="javascript:void(0)"  onclick="ContractEdit.download(\''+ QINIU_BUCKET_URL +result.path+'\')" class="easyui-linkbutton">下载</a>&nbsp;&nbsp;' ;
        } else {
            html_tr += '<a href="'+ QINIU_BUCKET_URL +result.path+'" class="easyui-linkbutton">下载</a>&nbsp;&nbsp;' ;
        }
        html_tr += '<a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="ContractEdit.delTr('+ index +')">删除</a>' ;
        html_tr += '</td>';
        html_tr += '</tr>';
        $("#appendicesTbody").append(html_tr);


    },
    addTr: function(result) {
        var appdeniceClassList = $(".appendice_class");
        var index = appdeniceClassList.length + 1;
        ContractEdit.appendTr(index, result);
        ContractEdit.initFrameHeight();
    },
    delTr: function(index) {
        $("#tr_" + index +"").remove();
    },
    download: function(imgPathURL, fileName) {
        $("#downDialog").dialog({
            onBeforeOpen: function() {
                $("#viewImg").attr("src", imgPathURL);
            },
            onClose: function() {
                $("#viewImg").attr("src", "");
            }
        });
        $("#downDialog").dialog("open");
    },
    initUnitACompany: function() {
        var url = "/sys/sysUnit/getUnitACompany.jhtml";
        $.post(url, function(data) {
            if (data.success) {
                $("#hid_unitAid").val(data.unit.id);
                $("#unitAname").textbox("setValue", data.unit.name);
            }
        });
    },

    initCom: function() {

        var editorYuding1 = KindEditor.ready(function(K) {
            var uploadbutton = KindEditor.uploadbutton({
                button : KindEditor('#uploadButton')[0],
                fieldName : 'resource',
                extraParams : {folder:ContractEdit.fileFolder},
                url : '/sys/imgUpload/uploadFileQiniu.jhtml',
                afterUpload : function(data) {
                    $.messager.progress("close");
                    if (data.success) {
                        ContractEdit.addTr(data.result);
                    } else {
                        show_msg(data.errorMsg);
                    }
                },
                afterError : function(str) {
                    $.messager.progress("close");

                    alert('自定义错误信息: ' + str);
                }
            });
            uploadbutton.fileBox.change(function(e) {
                $.messager.progress({
                    title:'温馨提示',
                    text:'附件上传中,请耐心等待...'
                });

                uploadbutton.submit();
            });
        });


        var settlementType = $("input[name='contract.settlementType']:checked").val();
        if (settlementType == 'week') {
            $("#settlementValue_"+ settlementType +"").combobox('setValue', $("#settlementValue").val());
        } else {
            $("#settlementValue_"+ settlementType +"").numberbox('setValue', $("#settlementValue").val());
        }


        var valuationModels = $("input[name='contract.valuationModels']:checked").val();
        if (valuationModels == 'commissionModel') {
            $("#valuationValue_"+ valuationModels +"").numberbox('setValue', $("#valuationValue").val());
        } else if (valuationModels == 'fixedModel') {
            $("#valuationValue_"+ valuationModels +"").numberbox('setValue', $("#valuationValue").val());
        }




        /*var settlementValue = $("input[name='contract.settlementType']:checked").val();

        $.each($("input[name='contract.settlementType']"), function(i, perValue) {

            if (settlementValue != $(perValue).val()) {
                $("#settlementType_"+ $(perValue).val() +"").numberbox({
                    disabled:true
                });
            }
        });
*/

        $("#ipt_signtime").datebox({
            onSelect: function(date) {
                var signtime = $("#ipt_signtime").datebox("getValue");
                var efftime = $("#ipt_efftime").datebox("getValue");
                var exptime = $("#ipt_exptime").datebox("getValue");
                if (exptime) {
                    var result = ContractUtil.dateStringCompare(exptime, signtime);
                    if (result <= 0) {
                        $("#ipt_signtime").datebox("setValue", "");
                        show_msg("签约日期不能大于合同失效日期")
                    }
                }
                if (efftime) {
                    var result = ContractUtil.dateStringCompare(efftime, signtime);
                    if (result < 0) {
                        $("#ipt_signtime").datebox("setValue", "");
                        show_msg("签约日期不能大于合同生效日期")
                    }
                }
                //console.log("signtime:" + signtime);
                //console.log("efftime:" + efftime);
                //console.log("exptime:" + exptime);
            }
        });
        $("#ipt_efftime").datebox({
            onSelect: function(date) {
                var signtime = $("#ipt_signtime").datebox("getValue");
                var efftime = $("#ipt_efftime").datebox("getValue");
                var exptime = $("#ipt_exptime").datebox("getValue");
                if (exptime) {
                    var result = ContractUtil.dateStringCompare(exptime, efftime);
                    console.log("1-"+result);
                    if (result <= 0) {
                        $("#ipt_efftime").datebox("setValue", "");
                        show_msg("合同生效日期不能大于合同失效日期")
                    }
                }
                if (signtime) {
                    var result = ContractUtil.dateStringCompare(efftime, signtime);
                    console.log("2-"+result);
                    if (result < 0) {
                        $("#ipt_efftime").datebox("setValue", "");
                        show_msg("签约日期不能大于合同生效日期")
                    }
                }
            }
        });
        $("#ipt_exptime").datebox({
            onSelect: function(date) {
                var signtime = $("#ipt_signtime").datebox("getValue");
                var efftime = $("#ipt_efftime").datebox("getValue");
                var exptime = $("#ipt_exptime").datebox("getValue");
                if (signtime) {
                    var result = ContractUtil.dateStringCompare(exptime, signtime);
                    if (result <= 0) {
                        $("#ipt_exptime").datebox("setValue", "");
                        show_msg("合同失效日期不能小于签约日期")
                    }
                }
                if (efftime) {
                    var result = ContractUtil.dateStringCompare(exptime, efftime);
                    if (result <= 0) {
                        $("#ipt_exptime").datebox("setValue", "");
                        show_msg("合同失效日期不能小于等于合同生效日期")
                    }
                }
            }
        });

    },
    initContent:function(){
        //富文本产品详情
        var editorContent;
        KindEditor.ready(function(K) {
            editorContent = K.create('#kind_content', {
                resizeType : 1,
                allowPreviewEmoticons : false,
                //uploadJson : '/ticket/ticketImg/uploadImg.jhtml?folder='+TicketConstants.ticketDescImg,
                //fileManagerJson :  '/ticket/ticketImg/imgsView.jhtml?folder='+TicketConstants.ticketDescImg,
                allowImageUpload : true,
                allowFileManager : true,
                filePostName: 'resource',
                items : [ 'fontname', 'fontsize',  'forecolor', 'bold', 'underline', 'table',
                    'removeformat', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                    'insertunorderedlist', 'image', 'multiimage', 'wordpaste', 'link' ],
                afterChange: function() {
                    this.sync();
                    var hasNum = this.count('text');
                    if (hasNum > ContractEdit.limitNum) {
                        //超过字数限制自动截取
                        var strValue = this.text();
                        strValue = strValue.substring(0,ContractEdit.limitNum);
                        this.text(strValue);
                        show_msg("字数过长已被截取，请简化");
                        //计算剩余字数
                        $('textarea[name="contract.content"]').next().children('.green-bold').html(0);
                    } else {
                        //计算剩余字数
                        $('textarea[name="contract.content"]').next().children('.green-bold').html(ContractEdit.limitNum-hasNum);
                    }
                },
                afterBlur: function() {
                    this.sync();
                }
            });
        });
    },


    unitLoader:function(param, success, error) {
        var q = param.q || '';
        if (q.length <= 1) {
            return false
        }
        $.ajax({
            url : '/sys/sysUnit/listContractCompanys.jhtml',
            dataType : 'json',
            //contentType: "application/x-www-form-urlencoded; charset=utf-8",
            type : 'POST',
            data : {
                featureClass : "P",
                style : "full",
                maxRows : 20,
                q : q
            },
            success : function(data) {
                var items = $.map(data, function(item) {
                    return {
                        id : item.unitId,
                        name : item.unitName
                    };
                });
                success(items);
            },
            error : function() {
                error.apply(this, arguments);
            }
        });
    },

    selectOn: function(enumType, type) {
        if (enumType == 'settlementType') {
            $.each($("input[name='contract.settlementType']"), function(i, perValue) {
                if (type != perValue.value) {
                    $("#settlementValue_"+ perValue.value +"").numberbox('setValue', "")
                }
            });

        } else if (enumType == 'valuationModels') {
            $.each($("input[name='contract.valuationModels']"), function(i, perValue) {
                if (type != perValue.value) {
                    $("#valuationValue_"+ perValue.value +"").numberbox('setValue', "")
                }
            });
        }
    },

    save: function() {

        var settlementType = $("input[name='contract.settlementType']:checked").val();
        $("#settlementValue").val($("#settlementValue_"+ settlementType +"").numberbox('getValue'));

        var valuationModels = $("input[name='contract.valuationModels']:checked").val();
        if (valuationModels == 'commissionModel') {
            $("#valuationValue").val($("#valuationValue_"+ valuationModels +"").numberbox('getValue'));
        } else if (valuationModels == 'fixedModel') {
            $("#valuationValue").val($("#valuationValue_"+ valuationModels +"").numberbox('getValue'));
        } else {
            $("#valuationValue").val("");
        }

        // 保存表单
        $('#editForm').form('submit', {
            url : "/contract/contract/saveOrUpdate.jhtml",
            onSubmit : function() {
                var isValid = $(this).form('validate');

                var appdeniceClassList = $(".appendice_class");
                if (appdeniceClassList.length <= 0) {
                    show_msg("请完善合同内容");
                    return false;
                }

                $.each(appdeniceClassList, function(i, perValue) {
                    var inputs = $(perValue).find("input");
                    $("#appendicesTbody").append('<input type="hidden" name="contract.appendicesList['+ i +'].name" value="'+ inputs[0].value +'">');
                    $("#appendicesTbody").append('<input type="hidden" name="contract.appendicesList['+ i +'].path" value="'+ inputs[1].value +'">');
                    $("#appendicesTbody").append('<input type="hidden" name="contract.appendicesList['+ i +'].type" value="'+ inputs[2].value +'">');

                });


                if(isValid){
                    $.messager.progress({
                        title:'温馨提示',
                        text:'数据处理中,请耐心等待...'
                    });
                } else {
                    show_msg("请完善当前页面数据");
                }
                return isValid;
            },
            success : function(result) {
                $.messager.progress("close");
                var result = eval('(' + result + ')');
                if(result.success==true){
                    window.parent.ContractManage.closeEditPanel(true);
                }else{
                    show_msg("保存合同失败");
                }
            }
        });
    },
    initFrameHeight: function() {
        window.parent.$("#editIframe").css("height", $("#div-content").height() + 30);
    },

    cancelEdit: function() {
        window.parent.ContractManage.closeEditPanel(true);
    }
};

$(function(){
    ContractEdit.init();
});