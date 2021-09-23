var editStep3 = {
    // 初始化
    init: function () {
        editStep3.initComp();
        editStep3.initListener();
    },
    // 初始控件
    initComp: function () {
        // 初始化原有的行程
        var planNum = $('.plan').length;
        if (planNum > 0) {
            for (var i = 1; i <= planNum; i++) {
                editStep3.buildPlanComp(i);
            }
        } else {    // 没有行程默认创建一天
            editStep3.buildPlan(1);
            editStep3.buildPlanComp(1);
        }
    },
    // 监听器
    initListener: function() {

    },
    // 添加行程，id规则="plan_"+index(day)
    doPlanAdd : function() {
        var planNum = $('.plan').length+1;
        editStep3.buildPlan(planNum);
        editStep3.buildPlanComp(planNum);
    },
    // 删除行程，如果后面有其他行程，修改对应的天
    doPlanDel : function(thiz) {
        var planId = $(thiz).parents('table.plan').attr('id');
        var day = parseInt(planId.substr(planId.lastIndexOf('_')+1));
        var planNum = $('.plan').length;
        if (planNum <= 1) {
            $.messager.alert('提示', '需要保留最少一天行程!', 'warning');
            return;
        }

        $('#'+planId).remove();
        for (var i = day+1; i <= planNum; i++) {
            $('#plan_'+i + ' input[name=day]').val(i-1);
            $('#plan_'+i + ' .orange-bold').html(i-1);
            $('#plan_'+i).attr('id', 'plan_'+(i-1));
        }
    },
    // 构建行程页面代码
    buildPlan: function(day) {
        var planHtml = '<table class="plan" id="plan_' + day + '">'
            + '<tr>'
            + '<td class="label dayLabel">第<span class="orange-bold">' + day + '</span>天:<input name="day" type="hidden" value="' + day + '"/></td>'
            + '<td><input name="dayDesc" style="width:360px;"><span class="tip">最多<span class="green-bold">100</span>个字符</span></td>'
            + '<td width="80"><a class="delPlan" href="javascript:void(0)" onClick="editStep3.doPlanDel(this)">删除行程</a></td>'
            + '</tr>'
            + '<tr>'
            + '<td class="label">停靠港口</td>'
            + '<td colspan="">'
            + ' <input style="width:360px;" name="stopPort">'
            + '</td>'
            + '</tr>'
            + '<tr>'
            + '<td class="label"></td>'
            + '<td colspan="2"><span style="line-height: 18px;">到达时间:</span><input style="width:100px;" name="arriveTime">'
            + '<span style="line-height: 18px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;离开时间:</span><input style="width:100px;" name="leaveTime"></td>'
            + '</tr>'
            + '<tr>'
            + '<td class="label"><span style="color: red">*&nbsp;</span>行程安排:</td>'
            + '<td colspan="2"><textarea id="arrange" name="arrange" style="width:600px; height:120px;"></textarea><span class="tip">已输入<span class="green-bold">0</span>个字符</span></td>'
            + '</tr><tr>'
            + '<td class="label"></td>'
            + '<td colspan="2"><span style="line-height: 18px;">早餐:</span><input style="width:100px;" name="breakfast" value="敬请自理">'
            + '<span style="line-height: 18px;">&nbsp;&nbsp;午餐:</span><input style="width:100px;" name="lunch" value="敬请自理">'
            + '<span style="line-height: 18px;">&nbsp;&nbsp;晚餐:</span><input style="width:100px;" name="supper" value="敬请自理"></td>'
            + '</tr><tr>'
            + '<td class="label">住宿:</td>'
            + '<td colspan="2"><input name="hotelName" style="width:360px;" value="邮轮上"></td>'
            + '</tr><tr>'
            + '<td class="label"></td><td colspan="2"></td>'
            + '</tr>'
            + '</table>';
        $('#planDiv').append(planHtml);
    },
    // 构建行程页面组件
    buildPlanComp: function(day) {
        var planId = 'plan_' + day;
        $('#'+planId+' input[name=dayDesc]').textbox({validType:'maxLength[100]', required:true});
        $('#'+planId+' input[name=arriveTime]').timespinner({prompt:'如：7:00',min:'00:00'});
        $('#'+planId+' input[name=stopPort]').textbox({validType:'maxLength[20]'});
        $('#'+planId+' input[name=leaveTime]').timespinner({prompt:'如：7:00',min:'00:00'});
        $('#'+planId+' input[name=breakfast]').textbox({validType:'maxLength[20]'});
        $('#'+planId+' input[name=lunch]').textbox({validType:'maxLength[20]'});
        $('#'+planId+' input[name=supper]').textbox({validType:'maxLength[20]'});
        $('#'+planId+' input[name=hotelName]').textbox({validType:'maxLength[100]'});
        // 富文本-行程安排
        var arrangeK = KindEditor.create('#'+planId+' textarea[name=arrange]', {
            resizeType : 1,
            allowPreviewEmoticons : false,
            //uploadJson : '/line/lineImg/uploadSave.jhtml?folder='+LineConstants.lineDescImg,
            //fileManagerJson : '/line/lineImg/imgsView.jhtml?folder='+LineConstants.lineDescImg,
            //allowImageUpload : true,
            //allowFileManager : true,
            //filePostName: 'resource',
            afterChange: function() {
                this.sync();
                var hasNum = this.count('text');
                $('#'+planId+' textarea[name=arrange]').next().children('.green-bold').html(hasNum);
            },
            afterBlur: function() {
                this.sync();
            },
            items : [ 'fontsize', 'forecolor',  'bold', '|', 'link', '|', 'image', '|', 'fullscreen']
        });
        // 按钮链接
        $('#'+planId+' .delPlan').linkbutton({plain:false});
    },
    // 更新表单的name，注意是.plan下的所有input和textarea
    updateInputName : function() {
        $('.plan input, textarea').attr("name", function(index, attr) {
            if (attr && attr.indexOf('.') > -1) {	// 已经替换过就不再替换，保存报错时可能出现的情况
                return attr;
            }
            var planId = $(this).parents('table.plan').attr('id');
            var index = parseInt(planId.substr(planId.lastIndexOf('_')+1));
            return 'plans['+(index-1)+'].'+attr;
        });
    },
    // 下一步
    nextGuide: function (winId) {
        // 保存表单
        $('#editForm').form('submit', {
            url : "/cruiseship/cruiseShipPlan/save.jhtml",
            onSubmit : function() {
                var isValid = $(this).form('validate');
                if(isValid) {
                    // 行程安排 字段验证
                    $('textarea[name$="arrange"]').each(function (index, element) {
                        var arrange = $(element).val();
                        if (!arrange) {
                            //show_msg("行程安排不能为空");
                            isValid = false;
                            return false;
                        }
                    });
                    if (!isValid) {
                        show_msg("行程安排不能为空");
                        return isValid;
                    }
                    editStep3.updateInputName();
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
                if (result.success) {
                    CruiseShipUtil.buildCruiseship($("#productId").val());
                    parent.window.showGuide(4, true);
                } else {
                    show_msg("保存失败");
                }
            }
        });
    }
};

//返回本页面数据
function getIfrData(){
	var data = {};
	return data;
}	

$(function(){
    editStep3.init();
});