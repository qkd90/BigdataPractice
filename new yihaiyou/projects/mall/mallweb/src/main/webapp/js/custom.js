
//价格区间
$(document).ready(function(){
  $("#priceqj").mouseover(function(){
	$(".price-sub").animate({
    height:'toggle'
  });
  });
});

//购物车全选
$(function () {
        var controlerId="leader";        //主控checkbox的id属性值
        var checkboxItemName="checkbox";//受控checkbox的name属性值
        $("#"+controlerId).click(function () {
            $("input[name=" +checkboxItemName+"]").prop("checked", $(this).prop("checked"));
        });
        $("input[name=" +checkboxItemName+"]").each(function () {
            $(this).click(function () {
                if ($("input[name=" +checkboxItemName+"]:checked").length == $("input[name=" +checkboxItemName+"]").length) {
                    $("#"+controlerId).prop("checked", true)     //受控checkbox全部选择后，主控checkbox也处于选定状态
                } else {
                    $("#"+controlerId).prop("checked", false);   //只要受控checkbox有一个不选，主控checkbox就不选
                }
              //必要时可改为反馈全选/全不选/选部分三种状态
            })
        });
    });
//购物车数量增加减少
$(function(){
    var t = $("#num");
    $("#add").click(function(){
        t.val(parseInt(t.val())+1)
    })
    $("#min").click(function(){
        t.val(parseInt(t.val())-1)
    })
})
$(function(){
    var t = $("#num1");
    $("#add1").click(function(){
        t.val(parseInt(t.val())+1)
    })
    $("#min1").click(function(){
        t.val(parseInt(t.val())-1)
    })
})
//日期选择
$(document).ready(function() {
	$('#datepicker1').Zebra_DatePicker();
	$('#datepicker2').Zebra_DatePicker();
	$('#datepicker3').Zebra_DatePicker();
	$('#datepicker4').Zebra_DatePicker();
	$('#datepicker5').Zebra_DatePicker();
	$('#datepicker6').Zebra_DatePicker();

 });
//费用清单固定右侧
$("#order-right").pin()