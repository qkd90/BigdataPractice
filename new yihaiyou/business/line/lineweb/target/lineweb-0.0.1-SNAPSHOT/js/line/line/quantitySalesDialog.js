var LineQuantitySales = {

    init: function() {
        LineQuantitySales.initQuantityList();
        LineQuantitySales.initJsp();

    },

    initJsp: function() {

        var flag = $("#q_flag").val();
        if (flag) {
            $(":input[name='flag'][value='"+ flag +"']").attr("checked", true);
        }
        var type = $("#q_type").val();
        if (type) {
            $(":input[name='type'][value='"+ type +"']").attr("checked", true);
        }

        var quantitySaleId = $("#hpt_quantitySalesId").val();
        if (quantitySaleId) {
            var url = "/line/line/getQuantitySalesDetailByquantitySaleId.jhtml?quantitySalesId=" + quantitySaleId;
            $("#q_center").datagrid({url:url});
        }

        $(":input[name='type']").click(function() {

            var this_value = $(this).val();
            if (this_value == "money") {
                $("#q_center").datagrid("loadData",[]);
            } else {
                $("#q_center").datagrid("loadData",[]);
            }
        });
    },

    initQuantityList: function() {
        $("#q_center").datagrid({
            rownumbers:false,
            border:false,
            singleSelect:true,
            striped:true,
            pagination:false,
            columns : [[
                { field : 'numStart', title : '大于等于', align : 'center', width : 110 },
                { field : 'numEnd', title : '小于', align : 'center', width : 110 ,
                    formatter: function (value, rowData, index) {
                        if (value == 0) {
                            return "";
                        } else {
                            return value;
                        }
                    }
                },
                { field : 'favorablePrice', title : '优惠金额（成人）', align : 'center', width : 110 ,
                    formatter: function (value, rowData, index) {
                        //if (value == 0) {
                        //    return "";
                        //} else {
                            return value;
                        //}
                    }
                },
                { field : 'favorablePriceChild', title : '优惠金额（儿童）', align : 'center', width : 110 ,
                    formatter: function (value, rowData, index) {
                        //if (value == 0) {
                        //    return "";
                        //} else {
                            return value;
                        //}
                    }
                }
                //,
                //{ field : 'discount', title : '折价比（成人）', align : 'center', width : 110 ,
                //    formatter: function (value, rowData, index) {
                //        if (value == 0) {
                //            return "";
                //        } else {
                //            return value;
                //        }
                //    }
                //},
                //{ field : 'discountChild', title : '折价比（儿童）', align : 'center', width : 110 ,
                //    formatter: function (value, rowData, index) {
                //        if (value == 0) {
                //            return "";
                //        } else {
                //            return value;
                //        }
                //    }
                //}
            ]],
            toolbar : [{
                text : '设置拱量',
                iconCls : 'icon-edit',
                handler : function() {
                    LineQuantitySales.addQuantitySales();
                }
            }]

        });

    },

    addQuantitySales: function() {

        $("#quantity_dialog").dialog({
            title: '设置拱量',
            closed: true,
            cache: false,
            modal: true,
            toolbar:[{
                text:'新增',
                iconCls:'icon-add',
                handler:function(){
                    LineQuantitySales.addQuantityRow();
                }
            }],
            buttons:[{
                text:'确定',
                iconCls:'icon-save',
                handler:function(){

                    LineQuantitySales.setQuantityData();

                }
            },{
                text:'取消',
                iconCls:'icon-cancel',
                handler:function(){
                    $("#quantity_dialog").dialog("close");
                    $(".q_row").remove();
                }
            }],
            onClose: function() {
                $(".q_row").remove();
            },
            onBeforeOpen: function() {

                var allRowObjects = $("#q_center").datagrid("getData");
                var allRows = allRowObjects.rows;


                $.each(allRows, function(index, perValue) {

                    if ((allRows.length-1) > index) {

                        var html = '';
                        html += '<tr class="q_row" id="row_'+ index +'" row-index="'+ index +'">';

                        html += '   <td>';
                        html += '       <input type="text" id="endNum_'+ index +'" onchange="LineQuantitySales.compareNum('+ index +', this)" class="input_quantity" value="'+ perValue.numEnd +'"/>';
                        html += '       <span>以内</span>';
                        html += '   </td>';

                        if (perValue.favorablePrice) {

                            html += '   <td>';
                            html += '       <input type="text" id="quantity_'+ index +'" onchange="LineQuantitySales.checknum(this)" class="input_quantity"  value="'+ perValue.favorablePrice +'"/>';
                            html += '   </td>';

                            html += '   <td>';
                            html += '       <input type="text" id="quantity_child_'+ index +'" onchange="LineQuantitySales.checknum(this)" class="input_quantity"  value="'+ perValue.favorablePriceChild +'"/>';
                            html += '   </td>';
                        } else {

                            html += '   <td>';
                            html += '       <input type="text" id="quantity_'+ index +'" onchange="LineQuantitySales.checknum(this)" class="input_quantity"  value="'+ perValue.discount +'"/>';
                            html += '   </td>';

                            html += '   <td>';
                            html += '       <input type="text" id="quantity_child_'+ index +'" onchange="LineQuantitySales.checknum(this)" class="input_quantity"  value="'+ perValue.discountChild +'"/>';
                            html += '   </td>';
                        }

                        html += '   <td>';
                        html += '       <i class="iconfont col_btn" onclick="LineQuantitySales.delQuantitySales('+ index +')">&#xe60d;</i>';
                        html += '   </td>';

                        html += '</tr>';

                        $(".q_tbody").append(html);


                    } else if (allRows.length > 1 && (allRows.length-1) == index) {

                        var lastHtml = '';

                        lastHtml += '<tr class="q_row" id="row_last" row-index="last">';

                        lastHtml += '   <td>';
                        lastHtml += '      <input type="hidden" id="endNum_last" value="" class="input_quantity">';
                        lastHtml += '       <span>其他</span>';
                        lastHtml += '   </td>';


                        if (perValue.favorablePrice) {

                            lastHtml += '   <td>';
                            lastHtml += '       <input type="text" id="quantity_last" onchange="LineQuantitySales.checknum(this)" class="input_quantity" value="'+ perValue.favorablePrice +'"/>';
                            lastHtml += '   </td>';

                            lastHtml += '   <td>';
                            lastHtml += '       <input type="text" id="quantity_child_last" onchange="LineQuantitySales.checknum(this)" class="input_quantity" value="'+ perValue.favorablePriceChild +'"/>';
                            lastHtml += '   </td>';
                        } else {

                            lastHtml += '   <td>';
                            lastHtml += '       <input type="text" id="quantity_last" onchange="LineQuantitySales.checknum(this)" class="input_quantity"  value="'+ perValue.discount +'"/>';
                            lastHtml += '   </td>';

                            lastHtml += '   <td>';
                            lastHtml += '       <input type="text" id="quantity_child_last" onchange="LineQuantitySales.checknum(this)" class="input_quantity"  value="'+ perValue.discountChild +'"/>';
                            lastHtml += '   </td>';
                        }


                        lastHtml += '   <td>';
                        //lastHtml += '       <i class="iconfont col_btn" onclick="QuantitySales.delQuantitySales(2)">&#xe60d;</i>';
                        lastHtml += '   </td>';

                        lastHtml += '</tr>';

                        $(".q_tbody").append(lastHtml);


                    } else {

                        var firstHtml = '';
                        firstHtml += '<tr class="q_row" id="row_0" row-index="0">';
                        firstHtml += '  <td>';
                        firstHtml += '      <input type="hidden" id="endNum_0" value="0" class="input_quantity">';
                        firstHtml += '      <span>大于0</span>';
                        firstHtml += '  </td>';

                        if (perValue.favorablePrice) {
                            firstHtml += '  <td>';
                            firstHtml += '       <input type="text" id="quantity_0" onchange="LineQuantitySales.checknum(this)" class="input_quantity"  value="'+ perValue.favorablePrice +'"/>';
                            firstHtml += '  </td>';
                            firstHtml += '  <td>';
                            firstHtml += '       <input type="text" id="quantity_child_0" onchange="LineQuantitySales.checknum(this)" class="input_quantity"  value="'+ perValue.favorablePriceChild +'"/>';
                            firstHtml += '  </td>';
                        } else {
                            firstHtml += '  <td>';
                            firstHtml += '       <input type="text" id="quantity_0" onchange="LineQuantitySales.checknum(this)" class="input_quantity"  value="'+ perValue.discount +'"/>';
                            firstHtml += '  </td>';
                            firstHtml += '  <td>';
                            firstHtml += '       <input type="text" id="quantity_child_0" onchange="LineQuantitySales.checknum(this)" class="input_quantity"  value="'+ perValue.discountChild +'"/>';
                            firstHtml += '  </td>';
                        }

                        firstHtml += '  <td>';
                        firstHtml += '  </td>';
                        firstHtml += '</tr>';
                        $(".q_tbody").append(firstHtml);

                    }

                });


                if (allRows.length < 1) {
                    var firstHtml = '';
                    firstHtml += '<tr class="q_row" id="row_0" row-index="0">';
                    firstHtml += '  <td>';
                    firstHtml += '      <input type="hidden" id="endNum_0" value="0" class="input_quantity">';
                    firstHtml += '      <span>大于0</span>';
                    firstHtml += '  </td>';
                    firstHtml += '  <td>';
                    firstHtml += '      <input type="text" id="quantity_0" onchange="LineQuantitySales.checknum(this)" class="input_quantity" />';
                    firstHtml += '  </td>';
                    firstHtml += '  <td>';
                    firstHtml += '      <input type="text" id="quantity_child_0" onchange="LineQuantitySales.checknum(this)" class="input_quantity" />';
                    firstHtml += '  </td>';
                    firstHtml += '  <td>';
                    //firstHtml += '      <i class="iconfont col_btn" onclick="QuantitySales.delQuantitySales(0)">&#xe60d;</i>';
                    firstHtml += '  </td>';
                    firstHtml += '</tr>';
                    $(".q_tbody").append(firstHtml);

                }
            }
        });

        $("#quantity_dialog").dialog("open");

    },

    setQuantityData: function() {


        var flag = true;

        var newRows = $(".q_row");

        var preEndNum = 0;

        var rowDataArr = [];
        var type = $(":input[name='type']:checked").val();




        $.each(newRows, function(i, perValue) {

            var index = $(perValue).attr("row-index");

            var startNum = 0;

            var endNum = '';
            var quantity = '';
            var quantityChild = '';

            if (i != (newRows.length - 1)) {
                endNum = $("#endNum_"+ index +"").val();
                endNum = Number(endNum);
                quantity = $("#quantity_"+ index +"").val();
                quantityChild = $("#quantity_child_"+ index +"").val();
                if (!endNum) {
                    flag = false;
                    show_msg("请完善数据！");
                }
            } else {

                if (index == 0) {
                    quantity = $("#quantity_0").val();
                    quantityChild = $("#quantity_child_0").val();
                } else {
                    quantity = $("#quantity_last").val();
                    quantityChild = $("#quantity_child_last").val();
                }


            }

            if (!quantity && !quantityChild) {
                flag = false;
                show_msg("请完善数据！");
            }

            $.each(newRows, function(i, perValue) {

                var currentIndex = $(perValue).attr("row-index");
                if (i != (newRows.length - 1) && i >= 1) {
                    var preIndex = $(newRows[i-1]).attr("row-index");
                    var currentValue = $("#endNum_"+ currentIndex +"").val();
                    currentValue = Number(currentValue);
                    var preValue = $("#endNum_"+ preIndex +"").val();
                    preValue = Number(preValue);

                    //console.log(preValue + ":" + currentValue);

                    if (preValue > currentValue) {
                        $("#endNum_"+ currentIndex +"").val("");
                        $("#endNum_"+ currentIndex +"").focus();
                        show_msg("当前值不能小于上一数值！");
                        flag = false;
                        return false;
                    }

                }

            });






            if (i != 0) {
                startNum = preEndNum;
            }



            var favorablePrice = '';
            var discount = '';
            var favorablePriceChild = '';
            var discountChild = '';

            if (type == "money") {
                favorablePrice = quantity;
                favorablePriceChild = quantityChild;
                discount = '';
                discountChild = '';
            } else {
                favorablePrice = '';
                favorablePriceChild = '';
                discount = quantity;
                discountChild = quantityChild;
            }


            var tempData = {
                numStart : startNum,
                numEnd: endNum,
                discount:discount,
                favorablePrice:favorablePrice,
                discountChild:discountChild,
                favorablePriceChild:favorablePriceChild
            };


            preEndNum = endNum;



            rowDataArr.push(tempData);

        });

        if (flag) {
            $("#q_center").datagrid("loadData", rowDataArr);
            $("#quantity_dialog").dialog("close");
        }

    },

    addQuantityRow: function() {

        var rows = $(".q_row");

        if (rows.length == 1 && $(rows[0]).attr("row-index") == 0) {
            $(".q_row").remove();
        }

        var newRows = $(".q_row");

        var index = 1;

        if (newRows.length > 0) {
            index = $(newRows[newRows.length-2]).attr("row-index");
            index = Number(index);
            index += 1;
            var html = '';
            html += '<tr class="q_row" id="row_'+ index +'" row-index="'+ index +'">';
            html += '   <td>';
            html += '       <input type="text" id="endNum_'+ index +'" onchange="LineQuantitySales.compareNum('+ index +', this)" class="input_quantity"/>';
            html += '       <span>以内</span>';
            html += '   </td>';
            html += '   <td>';
            html += '       <input type="text" id="quantity_'+ index +'" onchange="LineQuantitySales.checknum(this)" class="input_quantity" />';
            html += '   </td>';
            html += '   <td>';
            html += '       <input type="text" id="quantity_child_'+ index +'" onchange="LineQuantitySales.checknum(this)" class="input_quantity" />';
            html += '   </td>';
            html += '   <td>';
            html += '       <i class="iconfont col_btn" onclick="LineQuantitySales.delQuantitySales('+ index +')">&#xe60d;</i>';
            html += '   </td>';
            html += '</tr>';
            $(newRows[newRows.length-2]).after(html);



        } else {

            var html = '';
            html += '<tr class="q_row" id="row_'+ index +'" row-index="'+ index +'">';
            html += '   <td>';
            html += '       <input type="text" id="endNum_'+ index +'" onchange="LineQuantitySales.compareNum('+ index +', this)" class="input_quantity" data-options="min:0"/>';
            html += '       <span>以内</span>';
            html += '   </td>';
            html += '   <td>';
            html += '       <input type="text" id="quantity_'+ index +'" onchange="LineQuantitySales.checknum(this)" class="input_quantity" />';
            html += '   </td>';
            html += '   <td>';
            html += '       <input type="text" id="quantity_child_'+ index +'" onchange="LineQuantitySales.checknum(this)" class="input_quantity" />';
            html += '   </td>';
            html += '   <td>';
            html += '       <i class="iconfont col_btn" onclick="LineQuantitySales.delQuantitySales('+ index +')">&#xe60d;</i>';
            html += '   </td>';
            html += '</tr>';
            $(".q_tbody").append(html);


            var lastHtml = '';
            lastHtml += '<tr class="q_row" id="row_last" row-index="last">';
            lastHtml += '   <td>';
            lastHtml += '      <input type="hidden" id="endNum_last" value="">';
            lastHtml += '       <span>其他</span>';
            lastHtml += '   </td>';
            lastHtml += '   <td>';
            lastHtml += '       <input type="text" id="quantity_last" onchange="LineQuantitySales.checknum(this)" class="input_quantity" />';
            lastHtml += '   </td>';
            lastHtml += '   <td>';
            lastHtml += '       <input type="text" id="quantity_child_last" onchange="LineQuantitySales.checknum(this)" class="input_quantity" />';
            lastHtml += '   </td>';
            lastHtml += '   <td>';
            lastHtml += '   </td>';
            lastHtml += '</tr>';

            $(".q_tbody").append(lastHtml);


        }

    },

    delQuantitySales: function(index) {

        $("#row_"+ index +"").remove();

        var lastRows = $(".q_row");

        if (lastRows.length == 1 && $(lastRows[0]).attr("row-index") == "last") {

            var firstHtml = '';
            firstHtml += '<tr class="q_row" id="row_0" row-index="0">';
            firstHtml += '  <td>';
            firstHtml += '      <input type="hidden" id="endNum_0" value="0">';
            firstHtml += '      <span>大于0</span>';
            firstHtml += '  </td>';
            firstHtml += '  <td>';
            firstHtml += '      <input type="text" id="quantity_0" onchange="LineQuantitySales.checknum(this)" class="input_quantity" />';
            firstHtml += '  </td>';
            firstHtml += '  <td>';
            firstHtml += '      <input type="text" id="quantity_child_0" onchange="LineQuantitySales.checknum(this)" class="input_quantity" />';
            firstHtml += '  </td>';
            firstHtml += '  <td>';
            //firstHtml += '      <i class="iconfont col_btn" onclick="QuantitySales.delQuantitySales(0)">&#xe60d;</i>';
            firstHtml += '  </td>';
            firstHtml += '</tr>';


            $("#row_last").remove();
            $(".q_tbody").append(firstHtml);
        }

    },
    saveQuantitySales: function() {
        var flag = true;

        var allRowObjects = $("#q_center").datagrid("getData");
        var allRows = allRowObjects.rows;
        if (allRows.length > 0 ) {
            var rowsJsonStr = JSON.stringify(allRows);
            $("#hipt_rowsJsonStr").val(rowsJsonStr);
        } else {
            show_msg("请完善拱量配置信息！");
            flag = false;
        }

        var url = "/line/line/saveQuantitySales.jhtml";

        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });


        $('#quantity_form_id').form('submit', {
            url:url,
            onSubmit: function(){
                if (flag) {
                    return true;
                } else {
                    $.messager.progress("close");
                    return false;
                }
            },
            success:function(result){
                $.messager.progress("close");
                var result = eval('(' + result + ')');
                if (result.success) {
                    window.parent.$("#quantitySales_dialog").dialog("close");
                }
            }
        });
    },

    cancelQuantitySales: function() {
        window.parent.$("#quantitySales_dialog").dialog("close");
    },
    /**
     * 小数点后两位
     * @param obj
     */
    checknum : function (obj) {
        if(/^\d+\.?\d{0,2}$/.test(obj.value)){
            obj.value = obj.value;
        }else{
            $(obj).focus();
            obj.value = "";
        }
    },
    /**
     * 保证当前数量要大于前一条记录的数量
     * @param obj
     */
    compareNum : function (index, obj) {

        var preIndex = $("#row_"+ index +"").prev().attr("row-index");
        var preNum = 0;
        if (preIndex) {
            preNum = $("#endNum_"+ preIndex +"").val();
            preNum = Number(preNum);
        }

        var nowNum = $("#endNum_" + index + "").val();

        nowNum = Number(nowNum);

        var jquryObj = $(obj);

        if(/^[0-9]*[1-9][0-9]*$/.test(jquryObj.val())){

            if (nowNum > preNum) {
                obj.value = jquryObj.val();
            } else if (nowNum <= 0) {
                $(obj).focus();
                obj.value = "";
                show_msg("请输入大于0的数值！");
            } else {
                $(obj).focus();
                obj.value = "";
                show_msg("请输入比上条记录值大的数值！");
            }

        }else{
            $(obj).focus();
            obj.value = "";
        }
    }



}

$(function() {
    LineQuantitySales.init();
});