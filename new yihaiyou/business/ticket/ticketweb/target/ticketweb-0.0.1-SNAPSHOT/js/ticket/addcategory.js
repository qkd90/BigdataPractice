/**
 * Created by vacuity on 15/10/15.
 */

//$(function()
//{
//    AddCategory.initcombobox("line");
//    //AddCategory.submitform();
//    AddCategory.changecombox();
//});

var AddCategory={

    initcombobox:function(type){
        var requesturl="/ticket/ticket/";
        requesturl=requesturl+"getServiceRoot.jhtml?cateId=0";
        $('#add-parentcategory').combobox({
            url:requesturl,
            valueField:'id',
            textField:'name',
            panelHeight:'auto',
            onSelect: function (record) {
                if(record.id == 0){
                    $("#add-picture").show();
                }else{
                    $("#add-picture").hide();
                }
            },
            onLoadSuccess: function () { //加载完成后,设置选中第一项
                var val = $(this).combobox("getData");
                for (var item in val[0]) {
                    if (item == "id") {
                        $(this).combobox("select", val[0][item]);
                    }
                }
            }
        });
    },
    submitform:function(){
    	
    	
    	$('#cateCateFormId').form('submit', {
			url : "/goods/goods/getForm.jhtml",
			onSubmit : function() {
//				var isValid = TicketEdit.saveBefore();
//				return isValid;
			},
			success : function(result) {
//				var result = eval('(' + result + ')');
				if(result=='success'){
					window.parent.$('#search_classify').combobox('reload', '/ticket/ticket/getCategorgList.jhtml');
					window.parent.$('#dd').dialog('close');
//					parent.window.$('#dd').dialog('open');
				}else{
//					show_msg("保存门票失败");
				}
			}
		});
		
//        if (!checkForm()) {
//            return;
//        }
//        $('#add-categoryform').submit();
    }
};

$(function(){
//	alert("aa");
	AddCategory.initcombobox("service");
});


