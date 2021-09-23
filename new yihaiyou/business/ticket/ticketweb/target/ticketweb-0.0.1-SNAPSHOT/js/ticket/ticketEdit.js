/**门票管理*/
var TicketEdit = {
    init: function () {
//			TicketEdit.initPlugin();
        TicketEdit.initAgree();
        TicketEdit.initRuyuan();
        TicketEdit.initXiangqing();
        TicketEdit.initComp();
        TicketEdit.initAgent();
    },
    // 下一步
    nextGuide: function () {
//			 var param = "?ticketId=45";
//			 parent.window.showGuide(2, true,param);
        TicketEdit.saveTickeEdit();
    },
    htmlFormatter: function (html) {
        var str = html;
        str = str.replace(/<\/?[^>]*>/g, ''); //去除HTML tag
        str = str.replace(/[ | ]*\n/g, '\n'); //去除行尾空白
        //str = str.replace(/\n[\s| | ]*\r/g,'\n'); //去除多余空行
        str = str.replace(/&nbsp;/ig, '');//去掉&nbsp;
        return str;
    },
    initAgent: function () {
        var flag = $("#ticket_agent").val();
        if (flag == "true") {
            var city_code = $("#ticket_area").val();
            if (city_code) {
                city_code = city_code.substring(0, 4) + "00";
                var data = {
                    'cityCode': city_code
                };
                $.post("/ticket/ticket/getAreaByCitycode.jhtml",
                    data,
                    function (data) {
                        $("#sce_div_area").hide();
                        $("#sce_span_area").html(data.fullPath);
                    }, 'json'
                );
            }
            $("#sce_div_area").hide();
//				$("#sce_span_area").html();
            $("#sce_div_aji").hide();
            if ($("#ticket_aji").val()) {
                $("#sce_span_aji").html($("#ticket_aji").val());
            } else {
                $("#sce_span_aji").html("未评级");
            }
            $("#sce_div_address").hide();
            $("#sce_span_address").html($("#ticket_address").val());
            $("#btn_editSce").hide();
//				alert("aa");
        } else {
//				alert("cc");
        }
    },
    initComp: function () {
        var filePath = $("#filePath").val();
        if (filePath.length > 0) {
            $('#imgView img').attr('src', filePath);
            $("#imgView").show();
        } else {
            $("#imgView").hide();
        }
        var type = $("#type_ticket").val();
        if (type == "scenic") {
            TicketEdit.initInput(1);
            TicketEdit.sleTicCategory(1);
        } else if (type == "shows") {
            TicketEdit.initInput(2);
            TicketEdit.sleTicCategory(2);
        } else if (type == "sailboat") {
            TicketEdit.initInput(3);
            TicketEdit.sleTicCategory(3);
        } else if (type == "yacht") {
            TicketEdit.initInput(4);
            TicketEdit.sleTicCategory(4);
        }




        $("#span_close").click(function () {
            alert("aa");
        });
        // 图片上传
        KindEditor.ready(function (K) {
            var uploadbutton = K.uploadbutton({
                button: K('#add_descpic')[0],
                fieldName: 'resource',
                url: '/ticket/ticketImg/upload.jhtml',
                extraParams: {oldFilePath: $('#filePath').val(), folder: TicketConstants.ticketDescImg},
                afterUpload: function (result) {
                    $.messager.progress("close");
                    if (result.success == true) {
                        var url = K.formatUrl(result.url, 'absolute');
                        $('#filePath').val(url);
                        $('#imgView img').attr('src', url);
                        $('#imgView').show();
                    } else {
                        show_msg("图片上传失败");
                    }
                },
                afterError: function (str) {
                    show_msg("图片上传失败");
                }
            });
            uploadbutton.fileBox.change(function (e) {
                var filePath = uploadbutton.fileBox[0].value;
                if (!filePath) {
                    show_msg("图片格式不正确");
                    return;
                }
                var suffix = filePath.substr(filePath.lastIndexOf("."));
                suffix = suffix.toLowerCase();
                if ((suffix != '.jpg') && (suffix != '.gif') && (suffix != '.jpeg') && (suffix != '.png') && (suffix != '.bmp')) {
                    show_msg("图片格式不正确");
                    return;
                }
                $.messager.progress({
                    title: '温馨提示',
                    text: '图片上传中,请耐心等待...'
                });
                uploadbutton.submit();
            });
        });


        /*$('#look_span').tooltip({
            content: $('<div style="font-color:	#d0d0d0;">'+confirmHtml+'</div>'),
            showEvent: 'click',
            onShow: function(){
                var t = $(this);
                t.tooltip('tip').unbind().bind('mouseenter', function(){
                    t.tooltip('show');
                }).bind('mouseleave', function(){
                    t.tooltip('hide');
                });
            }
        });*/
    },
    editScenic: function () {
        $("#div1_sce").show();
        $("#div2_sce").hide();
        $("#sce_span_address").html("");
        $("#sce_span_aji").html("");
        $("#sce_span_area").html("");
        $("#jd_address").textbox("setValue", "");
        $("#scenic_id").val("");
        $("#sec_cityNameId").combobox('setValue', "请选择城市或地区");
        $("#sec_proNameId").combobox('setValue', "请选择省份");
        $("input[name='ticket.sceAji']")[5].checked = true;
        $("#sce_div_area").show();
        $("#sce_div_aji").show();
        $("#sce_div_address").show();
    },
    initInput: function (index) {
        var ticket_name = $("#ticket_name").val();
        var ticket_area = $("#ticket_area").val();
        var ticket_address = $("#ticket_address").val();
        var ticket_startTime = $("#ticket_startTime").val();
        var ticket_aji = $("#ticket_aji").val();
        if (index == 1) {
//				alert(ticket_name);
            $("#div1_sce").hide();
            $("#div2_sce").show();
            $("#scenicNameId").combobox('setText', ticket_name);
//				$("#scenicNameId").val(ticket_name);
            $("#sec_cityNameId").combobox('setValue', ticket_area);
            $("#startCityIdHidden").val(ticket_area);
            $("#hidden_sceAji").val(ticket_aji);
            $("#jd_address").textbox('setValue', ticket_address);
        } else if (index == 2) {
            $("#yc_showName").textbox('setValue', ticket_name);
            $("#yc_cityNameId").combobox('setValue', ticket_area);
            $("#yc_startCityIdHidden").val(ticket_area);
//				$("#hidden_sceAji").val(ticket_aji);
            $("#yc_showAddress").textbox('setValue', ticket_address);
            $("#yc_showTime").datetimebox('setValue', ticket_startTime);
//				alert("2");
        } else if (index == 3) {
            $("#div1_sce").hide();
            $("#div2_sce").show();
            $("#scenicNameId").combobox('setText', ticket_name);
            $("#sec_cityNameId").combobox('setValue', ticket_area);
            $("#startCityIdHidden").val(ticket_area);
            $("#jd_address").textbox('setValue', ticket_address);
            //$("#boat_name").textbox('setValue',ticket_name);
//				$("#boat_cityNameId").combobox('setValue',ticket_area);
//				$("#boat_startCityIdHidden").val(ticket_area);
//				$("#hidden_sceAji").val(ticket_aji);
//				$("#boat_startTime").datetimebox('setValue',ticket_startTime);
//				alert("3");
        } else if (index == 4) {
            $("#div1_sce").hide();
            $("#div2_sce").show();
            $("#scenicNameId").combobox('setText', ticket_name);
            $("#sec_cityNameId").combobox('setValue', ticket_area);
            $("#startCityIdHidden").val(ticket_area);
            $("#jd_address").textbox('setValue', ticket_address);
            //$("#boat_name").textbox('setValue',ticket_name);
//				$("#boat_cityNameId").combobox('setValue',ticket_area);
//				$("#boat_startCityIdHidden").val(ticket_area);
//				$("#hidden_sceAji").val(ticket_aji);
//				$("#boat_startTime").datetimebox('setValue',ticket_startTime);
//				alert("4");
        }
    },
    validator: function (value) {
        return /^[\u0391-\uFFE5]+$/.test(value);
    },
    saveBefore: function () {
        var flag = true;
        var ticketType = $("#ticke_type").val();
        if (ticketType == "scenic") {
            var ticket_name = $("#hidden_secName").val();
            var ticket_area = $("#sec_cityNameId").combobox("getValue");
            var radioInput = $("input[name='ticket.sceAji']:checked ").val();
            var flag = TicketEdit.validator(ticket_area);
            var jd_address = $("#jd_address").val();
            if (ticket_name.length <= 0) {
                flag = false;
                show_msg("请填写景点名称！");
                return flag;
            } else if (ticket_name.length > 30) {
                flag = false;
                show_msg("景点名称超过30字！");
                return flag;
            } else if (flag || ticket_area.length <= 0) {
                flag = false;
                show_msg("请填写景点所属区域！");
                return flag;
            } else if (radioInput.length <= 0) {
                flag = false;
                show_msg("请填写景区级别！");
                return flag;
            } else if (jd_address.length <= 0) {
                flag = false;
                show_msg("请填写景地址！");
                return flag;
            } else {
                $("#ticket_name").val(ticket_name);
                $("#ticket_area").val(ticket_area);
                $("#ticket_address").val(jd_address);
                return TicketEdit.valTextArea();
            }
        } else if (ticketType == "shows") {
            var show_name = $("#yc_showName").val();
            var show_area = $("#yc_cityNameId").combobox("getValue");
            var flag = TicketEdit.validator(show_area);
            var show_startTime = $("#yc_showTime").datetimebox('getValue');
            var show_address = $("#yc_showAddress").val();
            if (show_name.length <= 0) {
                flag = false;
                show_msg("请填写演出门票名称！");
                return flag;
            } else if (show_name.length > 18) {
                flag = false;
                show_msg("演出门票名称超过18字！");
                return flag;
            } else if (flag || show_area.length <= 0) {
                flag = false;
                show_msg("请填写演出门票所属区域！");
                return flag;
            } else if (show_startTime.length <= 0) {
                flag = false;
                show_msg("请填写演出时间！");
                return flag;
            } else if (show_address.length <= 0) {
                flag = false;
                show_msg("请填演出详细地址！");
                return flag;
            } else {
                $("#ticket_name").val(show_name);
                $("#ticket_area").val(show_area);
                $("#ticket_startTime").val(show_startTime);
                $("#ticket_address").val(show_address);
                return TicketEdit.valTextArea();
            }
        } else if (ticketType == "sailboat") {
            var ticket_name = $("#hidden_secName").val();
            var boat_name = $("#boat_name").val();
            var ticket_area = $("#sec_cityNameId").combobox("getValue");
            var boat_area = $("#boat_cityNameId").combobox("getValue");
            var boat_startTime = $("#boat_startTime").datetimebox('getValue');
            var flag = TicketEdit.validator(boat_area);
            var jd_address = $("#jd_address").val();
            if (ticket_name.length <= 0) {
                flag = false;
                show_msg("请选择要关联的景点！");
                return flag;
            }
            //else if(ticket_name.length>30 ){
            //    flag = false;
            //    show_msg("景点名称超过30字！");
            //    return flag;
            //}
            //else if(boat_name.length<=0){
            //flag = false;
            //show_msg("请填写船票名称！");
            //return flag;
            //}else if(boat_name.length>18 ){
            //flag = false;
            //show_msg("船票名称超过18字！");
            //return flag;
            //}
            else if (!flag || ticket_area.length <= 0) {
                flag = false;
                show_msg("请选择船票所属区域！");
                return flag;
            }
            else if (jd_address.length <= 0) {
                flag = false;
                show_msg("请填写地址！");
                return flag;
            }
            //else if(!flag && boat_area.length<=0 ){
            //	flag = false;
            //	show_msg("请填写船票所属区域！");
            //	return flag;
            //}
            //else if(boat_startTime.length<=0){
            //	flag = false;
            //	show_msg("请填写船只出航时间！");
            //	return flag;
            //}
            else {
                $("#ticket_startTime").val(boat_startTime);
                //$("#ticket_name").val(boat_name);
                $("#ticket_name").val(ticket_name);
                //$("#ticket_area").val(boat_area);
                $("#ticket_area").val(ticket_area);
                $("#ticket_address").val(jd_address);
                return TicketEdit.valTextArea();
            }
        } else if (ticketType == "yacht") {
            var ticket_name = $("#hidden_secName").val();
            var boat_name = $("#boat_name").val();
            var ticket_area = $("#sec_cityNameId").combobox("getValue");
            var boat_area = $("#boat_cityNameId").combobox("getValue");
            var boat_startTime = $("#boat_startTime").datetimebox('getValue');
            var flag = TicketEdit.validator(boat_area);
            var jd_address = $("#jd_address").val();
            if (ticket_name.length <= 0) {
                flag = false;
                show_msg("请选择要关联的景点！");
                return flag;
            }
            //else if(ticket_name.length>30 ){
            //    flag = false;
            //    show_msg("景点名称超过30字！");
            //    return flag;
            //}
            //else if(boat_name.length<=0){
            //flag = false;
            //show_msg("请填写船票名称！");
            //return flag;
            //}else if(boat_name.length>18 ){
            //flag = false;
            //show_msg("船票名称超过18字！");
            //return flag;
            //}
            else if (!flag || ticket_area.length <= 0) {
                flag = false;
                show_msg("请选择船票所属区域！");
                return flag;
            }
            else if (jd_address.length <= 0) {
                flag = false;
                show_msg("请填写地址！");
                return flag;
            }
            //else if(!flag && boat_area.length<=0 ){
            //	flag = false;
            //	show_msg("请填写船票所属区域！");
            //	return flag;
            //}
            //else if(boat_startTime.length<=0){
            //	flag = false;
            //	show_msg("请填写船只出航时间！");
            //	return flag;
            //}
            else {
                $("#ticket_startTime").val(boat_startTime);
                //$("#ticket_name").val(boat_name);
                $("#ticket_name").val(ticket_name);
                //$("#ticket_area").val(boat_area);
                $("#ticket_area").val(ticket_area);
                $("#ticket_address").val(jd_address);
                return TicketEdit.valTextArea();
            }
        }
    },
    valTextArea: function () {
        var flag = true;
        var adviceHours = $("#adviceHours").val();
        var search_classify = $("#search_classify").combotree('getValue');
        var kindRuyuan = $("#kindRuyuan").val();
        kindRuyuan = TicketEdit.htmlFormatter(kindRuyuan);
        var kindTuigai = $("#kindTuigai").val();
        kindTuigai = TicketEdit.htmlFormatter(kindTuigai);
        var kindprivilege = $("#kindprivilege").val();
        kindprivilege = TicketEdit.htmlFormatter(kindprivilege);
        var jd_name = $("#jd_name").textbox("getValue");
        var filePath = $("#filePath").val();
        console.log(search_classify);
        if (search_classify.length <= 0) {
            flag = false;
            show_msg("请选择产品分类！");
            return flag;
        } else if (filePath.length <= 0) {
            flag = false;
            show_msg("门票描述图片不能为空！");
            return flag;
        } else if (adviceHours.length <= 0) {
            flag = false;
            show_msg("请填写提前预定天数！");
            return flag;
        } else if (kindRuyuan.length <= 0) {
            flag = false;
            show_msg("请填写入园说明！");
            return flag;
        } else if (kindRuyuan.length > 1000) {
            flag = false;
            show_msg("入园说明字数超过1000字！");
            return flag;
        } else if (kindTuigai.length <= 0) {
            flag = false;
            show_msg("请填写退改规则！");
            return flag;
        } else if (kindTuigai.length > 1000) {
            flag = false;
            show_msg("退改规则超过1000字！");
            return flag;
        } else if (kindprivilege.length <= 0) {
            flag = false;
            show_msg("请填写优惠政策！");
            return flag;
        } else if (kindprivilege.length > 1000) {
            flag = false;
            show_msg("优惠政策超过1000字！");
            return flag;
        } else if (jd_name.length <= 0) {
            flag = false;
            show_msg("请填写产品标题！");
            return flag;
        } else if (jd_name.length > 1000) {
            flag = false;
            show_msg("产品标题字数超过18字！");
            return flag;
        } else if (filePath.length <= 0) {
            flag = false;
            show_msg("门票描述图片不能为空！");
            return flag;
        } else {
            return flag;
        }
    },
    saveTickeEdit: function () {
        if (TicketEdit.saveBefore()) {
            // 保存表单
            $('#userInputForm').form('submit', {
                url: "/ticket/ticket/edit.jhtml",
                onSubmit: function () {
                    $.messager.progress({
                        title: '温馨提示',
                        text: '数据处理中,请耐心等待...'
                    });
                    var isValid = TicketEdit.saveBefore();
                    if (!isValid) {
                        $.messager.progress("close");
                    }
                    return isValid;
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.success) {
                        $("#ticketId").val(result.id);
                        $("#ticketNameId").val(result.name);
                        var param = result.id;
                        TicketUtil.buildTicket(result.id);
                        $.messager.progress("close");
                        parent.window.showGuide(2, true, param);
                    } else {
                        $.messager.progress("close");
                        show_msg("操作失败");
                    }
                }
            });
        }
    },
    initAgree: function () {
        var inputAgree = $("#hidden_input_agree").val();
        if (inputAgree == 1) {
            $("#input_agree").prop("checked", true);
        } else {
            $("#input_agree").prop("checked", false);
        }
        //入园保障
        $("#div_agree").click(function () {
//				alert($("#input_agree").prop("value"));
            if ($("#input_agree").prop("checked")) {
                $("#input_agree").prop("checked", false);
                $("#hidden_input_agree").val(0);
            } else {
                $("#input_agree").prop("checked", true);
                $("#hidden_input_agree").val(1);
            }
        });
        $("#input_agree").click(function () {
            if ($("#input_agree").prop("checked")) {
//					$("#input_agree").prop("checked", false);
                $("#hidden_input_agree").val(1);
            } else {
//					$("#input_agree").prop("checked", true);
                $("#hidden_input_agree").val(0);
            }
        });
    },
    initRuyuan: function () {
        //富文本入园说明
        var editorRuyuan;
        KindEditor.ready(function (K) {
            editorRuyuan = K.create('#kindRuyuan', {
                resizeType: 1,
                allowPreviewEmoticons: false,
                allowImageUpload: false,
                items: ['fontsize', 'forecolor', 'bold'],
                afterChange: function () {
                    this.sync();
                    if (this.count('text') <= 1000) {
                        K('#em_ruyuan').html(1000 - this.count('text'));
                    } else {
                        show_msg("字数超过限制，请重新编辑！");
                    }
                    var text = this.text();
                    console.log(text);
                },
                afterBlur: function () {
                    this.sync();
                }
                //html:$("#hidden_ruyuan").val()
            });
            var ruyuan = '${ticketExplain.enterDesc}';
            editorRuyuan.insertHtml(ruyuan);
            K('#use_ruyuan').click(function () {
                var html = '游客买了您的票之后如何进入景区？';
                editorRuyuan.insertHtml(html);
            });
        });
        //富文本退改规则
        var editorTuigai;
        KindEditor.ready(function (K) {
            editorTuigai = K.create('#kindTuigai', {
                resizeType: 1,
                allowPreviewEmoticons: false,
                allowImageUpload: false,
                items: ['fontsize', 'forecolor', 'bold'],
                afterChange: function () {
                    this.sync();
                    if (this.count('text') <= 1000) {
                        K('#em_tuigai').html(1000 - this.count('text'));
                    } else {
                        show_msg("字数超过限制，请重新编辑！");
                    }
                },
                afterBlur: function () {
                    this.sync();
                }
            });
            var tuigai = '${ticketExplain.rule }';
            editorTuigai.insertHtml(tuigai);
            K('#use_tuigai').click(function () {
                var html = '游客如何退换票？';
                editorTuigai.insertHtml(html);
            });
        });
        //富文本优惠政策
        var editorPrivilege;
        KindEditor.ready(function (K) {
            editorPrivilege = K.create('#kindprivilege', {
                resizeType: 1,
                allowPreviewEmoticons: false,
                allowImageUpload: false,
                items: ['fontsize', 'forecolor', 'bold'],
                afterChange: function () {
                    this.sync();
                    if (this.count('text') <= 1000) {
                        K('#em_youhui').html(1000 - this.count('text'));
                    } else {
                        show_msg("字数超过限制，请重新编辑！");
                    }
                },
                afterBlur: function () {
                    this.sync();
                }
            });
            K('#use_privilege').click(function () {
                var html = '有哪些人是可以享受优惠的？如';
                html += 'A：免费政策：免票对象：1.2米以下儿童免票。';
                html += 'B：优惠政策：儿童长者票对象：身高1.2米至1.5米儿童（含1.2米和1.5米），或65周岁以上的长者（凭身份证验票入园）；其他优惠以景区公布为准。';
                editorPrivilege.insertHtml(html);
            });
        });
        $("#dlg").window({
            title: '图片空间',
            width: 650,
            height: 530,
            top: ($(window).height() - 400) * 0.5,
            left: ($(window).width() - 500) * 0.5,
            shadow: true,
            modal: true,
            iconCls: 'icon-add',
            closed: true,
            minimizable: false,
            maximizable: false,
            collapsible: false
        });
//			$("#uploadLinkId").click(function(){
    },
    initXiangqing: function () {
        //富文本产品详情
        var editorXiangqing;
        KindEditor.ready(function (K) {
            editorXiangqing = K.create('#kindXiangqing', {
                resizeType: 1,
                allowPreviewEmoticons: false,
                uploadJson: '/ticket/ticketImg/uploadImg.jhtml?folder=' + TicketConstants.ticketDescImg,
                fileManagerJson: '/ticket/ticketImg/imgsView.jhtml?folder=' + TicketConstants.ticketDescImg,
                allowImageUpload: true,
                allowFileManager: true,
                filePostName: 'resource',
                items: ['fontname', 'fontsize', 'forecolor', 'bold', 'underline', 'table',
                    'removeformat', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                    'insertunorderedlist', 'image', 'link'],
                afterChange: function () {
                    this.sync();
                    if (this.count() <= 3000) {
                        K('#textareaLength2').html(3000 - this.count());
                        var html = '可以填写门票介绍、温馨提示、发票说明、付款帮助等内容，当前输入字数<span id="textareaLength1" style="color:green;">' + (2000 - this.count('text')) + '</span>--<span id="textareaLength2" style="color:green;">' + (3000 - this.count()) + '</span><span>(纯文本--含链接)</span>';
                        K("#suffix_tip_id").html(html);
                    } else {
                        var html = '<span style="color: red;">当前输入字符串链接长度已超出' + (this.count() - 3000) + '</span>';
                        K("#suffix_tip_id").html(html);
                        show_msg("链接元素过长，请重新编辑！");
                    }
                    if (this.count('text') <= 2000) {
                        var html = '可以填写门票介绍、温馨提示、发票说明、付款帮助等内容，当前输入字数<span id="textareaLength1" style="color:green;">' + (2000 - this.count('text')) + '</span>--<span id="textareaLength2" style="color:green;">' + (3000 - this.count()) + '</span><span>(纯文本--含链接)</span>';
                        //K('#textareaLength1').html(2000-this.count('text'));
                        K("#suffix_tip_id").html(html);
                    } else {
                        var html = '<span style="color: red;">当前输入字符串文本长度已超出' + (this.count() - 2000) + '</span>';
                        K("#suffix_tip_id").html(html);
                        show_msg("纯文本字数过长，请重新编辑！");
                        //K("#beyondFontTip").html(this.count('text') - 2000);
                    }
                },
                afterBlur: function () {
                    this.sync();
                }
            });
            //editorXiangqing.html(K('#hidden_xiangq').val());
            var xiangqing = '${ticketExplain.proInfo}';
            editorXiangqing.insertHtml(xiangqing);
            K('#div_kindXiangqing').children('.ke-container').children('.ke-edit').click(function () {
                editorXiangqing.html('');
            });
            editorXiangqing.sync();
        });
    },
    //选择门票类别
    sleTicCategory: function (selId) {
        if (selId == 1) {
            $("#ticke_type").val("scenic");
            TicketEdit.show_jingdian();
            TicketEdit.hide_yanchu();
            TicketEdit.hide_chuanzhi();
            TicketEdit.hide_youting();
        } else if (selId == 2) {
            $("#ticke_type").val("shows");
            TicketEdit.hide_jingdian();
            TicketEdit.show_yanchu();
            TicketEdit.hide_chuanzhi();
            TicketEdit.hide_youting();
        } else if (selId == 3) {
            $("#ticke_type").val("sailboat");
            TicketEdit.hide_jingdian();
            TicketEdit.hide_yanchu();
            TicketEdit.hide_youting();
            TicketEdit.show_chuanzhi();
        } else if (selId == 4) {
            $("#ticke_type").val("yacht");
            TicketEdit.hide_jingdian();
            TicketEdit.hide_yanchu();
            TicketEdit.hide_chuanzhi();
            TicketEdit.show_youting();
        }
    },
    //隐藏景点类别信息
    hide_jingdian: function () {
        $("#secTicket").css("background", "#efefef");
        $("#div_jd_name").hide();
        $("#div_jd_area").hide();
        $("#div_jd_aji").hide();
        $("#div_jd_address").hide();
    },
    //显示景点类别信息
    show_jingdian: function () {
        $("#secTicket").css("background", "#FE7700");
        $("#div_jd_name").show();
        $("#div_jd_area").show();
        $("#div_jd_aji").show();
        $("#div_jd_address").show();
    },
    //隐藏门票类别信息
    hide_yanchu: function () {
        $("#perTicket").css("background", "#efefef");
        $("#div_yc_name").hide();
        $("#div_yc_area").hide();
        $("#div_yc_time").hide();
        $("#div_yc_address").hide();
    },
    //显示门票类别信息
    show_yanchu: function () {
        $("#perTicket").css("background", "#FE7700");
        $("#div_yc_name").show();
        $("#div_yc_area").show();
        $("#div_yc_time").show();
        $("#div_yc_address").show();
    },
    //隐藏帆船船票类别信息
    hide_chuanzhi: function () {
        $("#boatTicket").css("background", "#efefef");
        $("#div_cz_name").hide();
        $("#div_cz_area").hide();
        $("#div_cz_time").hide();
    },
    // 隐藏游艇船票类别信息
    hide_youting: function () {
        $("#yachtTicket").css("background", "#efefef");
        $("#div_cz_name").hide();
        $("#div_cz_area").hide();
        $("#div_cz_time").hide();
    },
    //显示游艇船票类别信息
    show_chuanzhi: function () {
        $("#boatTicket").css("background", "#FE7700");
        $('#div_jd_name').show();
        //$("#div_cz_name").show();
        //$("#div_cz_area").show();
        //$("#div_cz_time").show();
        $("#div_jd_area").show();
        $("#div_jd_address").show();
    },
    //显示游艇船票类别信息
    show_youting: function () {
        $("#yachtTicket").css("background", "#FE7700");
        $('#div_jd_name').show();
        //$("#div_cz_name").show();
        //$("#div_cz_area").show();
        //$("#div_cz_time").show();
        $("#div_jd_area").show();
        $("#div_jd_address").show();
    },
    initCommbox: function () {
        $('#dd').dialog('close');
        $("#scenicNameId")
            .combobox(
            {
                onSelect: function (param) {
                    var id = param.id;
                    var name = param.name;
                    name = name.substring(0, name.indexOf("-"));
                    $("#hidden_secName").val(name);
                    $("#scenic_id").val(id);
                    var address = param.address;
                    var star = param.star;
                    $("#jd_address").textbox('setValue', address);
                    $("#sec_cityNameId").combobox('setValue', param.city_code);
                    $("#sce_div_aji").hide();
                    if (star == null) {
                        $("#sce_span_aji").html("未评级");
                        $("input[name='ticket.sceAji']").val("0A");
                    } else {
                        $("#sce_span_aji").html("A" + star);
                        $("input[name='ticket.sceAji']").val(star + "A");
                    }
                    $("#sce_div_address").hide();
                    $("#sce_span_address").html(address);
                    var city_code = param.city_code.toString();
                    city_code = city_code.substring(0, 4) + "00";
                    var data = {
                        'cityCode': city_code
                    };
                    $.post("/ticket/ticket/getAreaByCitycode.jhtml",
                        data,
                        function (data) {
                            $("#sce_div_area").hide();
                            $("#sce_span_area").html(data.fullPath);
                        }, 'json'
                    );
                }
            });
    },
    scenicLoader: function (param, success, error) {
        var q = param.q || '';
        if (q.length <= 1) {
            return false
        }
        var scenicType = $("#ticke_type").val();
        if (scenicType == "sailboat" || scenicType == "yacht") {
            scenicType = "sailboat";
        }
        $.ajax({
            url: '/ticket/ticket/getScenicList.jhtml',
            dataType: 'json',
            //contentType: "application/x-www-form-urlencoded; charset=utf-8",
            type: 'POST',
            data: {
                featureClass: "P",
                style: "full",
                maxRows: 20,
                name_startsWith: q,
                scenicType: scenicType
            },
            success: function (data) {
                var items = $.map(data, function (item) {
                    return {
                        id: item.id,
                        name: item.name + "-" + item.fatherName,
                        ticketName: item.name,
                        city_code: item.city_code,
                        address: item.address,
                        star: item.star
                    };
                });
                success(items);
            },
            error: function () {
                error.apply(this, arguments);
            }
        });
    },
    nameFormatter: function (name, city_code) {
        var nameStr = "";
        var city_code = city_code.toString();
        city_code = city_code.substring(0, 4) + "00";
        var data = {
            'cityCode': city_code
        };
        $.post("/ticket/ticket/getAreaByCitycode.jhtml",
            data,
            function (data) {
                nameStr = name + "-" + data.fullPath;
                return nameStr;
            }, 'json'
        );
    },
    addImg: function () {
        $("#up").uploadPreview({Img: "ImgPr", Width: 120, Height: 120});
        $(".J_add_pic_first").css({'margin-left': " 110px", 'margin-top': "5px"});
        $("#img_div").show();
    },
    initRemove_img: function () {
        $("#remove_img_id").click(function () {
            $("#filePath").val("");
            $('#imgView img').attr('src', "")
            $("#imgView").hide();
        });
    },
    //下面用于多图片上传预览功能
    setImagePreviews: function setImagePreviews(avalue) {
        var docObj = document.getElementById("doc");
        var dd = document.getElementById("dd");
        dd.innerHTML = "";
        var fileList = docObj.files;
        for (var i = 0; i < fileList.length; i++) {
            dd.innerHTML += "<div style='float:left' > <img id='img" + i + "'  /> </div>";
            var imgObjPreview = document.getElementById("img" + i);
            if (docObj.files && docObj.files[i]) {
                //火狐下，直接设img属性
                imgObjPreview.style.display = 'block';
                imgObjPreview.style.width = '150px';
                imgObjPreview.style.height = '180px';
                //imgObjPreview.src = docObj.files[0].getAsDataURL();
                //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
                imgObjPreview.src = window.URL.createObjectURL(docObj.files[i]);
            }
            else {
                //IE下，使用滤镜
                docObj.select();
                var imgSrc = document.selection.createRange().text;
                alert(imgSrc)
                var localImagId = document.getElementById("img" + i);
                //必须设置初始大小
                localImagId.style.width = "150px";
                localImagId.style.height = "180px";
                //图片异常的捕捉，防止用户修改后缀来伪造图片
                try {
                    localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
                    localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
                }
                catch (e) {
                    alert("您上传的图片格式不正确，请重新选择!");
                    return false;
                }
                imgObjPreview.style.display = 'none';
                document.selection.empty();
            }
        }
        return true;
    },
    //上传图片
    ajaxFileUpload: function () {
        $("#uploadaId").click(function () {
            $("#uploadaId").click(function () {
                // 图片上传
                KindEditor.ready(function (K) {
                    var uploadbutton = K.uploadbutton({
                        button: K('#uploadaId')[0],
                        fieldName: 'resource',
                        url: '/ticket/ticketImg/uploadEditImg.jhtml',
                        extraParams: {folder: TicketConstants.ticketDescImg},
                        afterUpload: function (result) {
                            $.messager.progress("close");
                            if (result.success == true) {
                                var url = K.formatUrl(result.url, 'absolute');
//									$('#filePath').val(url);	
//									$('#imgView img').attr('src', url);
//									$('#imgView').show();
                            } else {
                                show_msg("图片上传失败");
                            }
                        },
                        afterError: function (str) {
                            show_msg("图片上传失败");
                        }
                    });
                    uploadbutton.fileBox.change(function (e) {
                        var filePath = uploadbutton.fileBox[0].value;
                        if (!filePath) {
                            show_msg("图片格式不正确");
                            return;
                        }
                        var suffix = filePath.substr(filePath.lastIndexOf("."));
                        suffix = suffix.toLowerCase();
                        if ((suffix != '.jpg') && (suffix != '.gif') && (suffix != '.jpeg') && (suffix != '.png') && (suffix != '.bmp')) {
                            show_msg("图片格式不正确");
                            return;
                        }
                        $.messager.progress({
                            title: '温馨提示',
                            text: '图片上传中,请耐心等待...'
                        });
                        uploadbutton.submit();
                    });
                });
            });
        });
    },
    //上传详情图片
    uploadXiangqImg: function () {
        $('input[id=lefile]').click();
    },
    initCategory: function () {
        var sceAjiArr = $("input[name='ticket.sceAji']");
        var hidden_sceAji = $("#hidden_sceAji").val();
        $.each(sceAjiArr, function (i, perValue) {
            if (perValue.value == hidden_sceAji) {
                perValue.checked = "checked";
            }
        });
        //设置自定义分类
        var category = $("#ticket_category").val();
        var select = document.getElementById("search_classify");
        $("#search_classify").combotree('setValue', category);
    },
    showCategoryPanel: true,
    initProCommbox: function () {
        $("#search_classify").combotree({
            url: '/goods/goods/getComboCatgoryData.jhtml?type=service',
            //editable: false, //不可编辑状态
            //cache: false,
            //panelHeight: 'auto',//自动高度适合
            //valueField: 'id',
            //textField: 'name',
            onBeforeSelect: function (node) {
                //var $target = $(node.target);
                var tree = $(this).tree;
                var isLeaf = tree('isLeaf', node.target);
                if (!isLeaf) {
                    TicketEdit.showCategoryPanel = true;
                    return false;
                }
                TicketEdit.showCategoryPanel = false;
                return true;
            },
            onHidePanel: function (data) {
                if (TicketEdit.showCategoryPanel) {
                    $('#search_classify').combotree('showPanel');
                }
            },
            onShowPanel: function () {
                TicketEdit.showCategoryPanel = false;
            },
            onLoadSuccess: function (node, data) {
                var ticket_category = $('#ticket_category').val();
                if (ticket_category) {
                    $("#search_classify").combotree('setValue', ticket_category);
                }
                $.each(data, function (i, node) {
                    if (node.children && node.children.length > 0) {
                        $('#' + node.domId).css('cursor', 'not-allowed');
                    }
                });
            }
        });
        $("#sec_proNameId")
            .combobox(
            {
                url: '/ticket/ticket/getProvince.jhtml?name_startsWith=100000',
                editable: false, //不可编辑状态
                cache: false,
                panelHeight: '200',//自动高度适合
                valueField: 'id',
                textField: 'name',
                onHidePanel: function () {
                    $("#sec_cityNameId").combobox("setValue", '');
                    var lanmuid = $('#sec_proNameId').combobox('getValue');
//						      if(!(TicketEdit.validator(lanmuid))){
//						    	 alert(lanmuid); 
//						      }
                    $.ajax({
                        type: "POST",
                        url: "/ticket/ticket/getCity.jhtml?name_startsWith=" + lanmuid,
                        cache: false,
                        dataType: "json",
                        success: function (data) {
                            $("#sec_cityNameId").combobox("loadData", data);
                        }
                    });
                },
                onLoadSuccess: function () {
                    var city = $('#startCityIdHidden').val();
                    if (city) {
                        var pro = city.substr(0, 2) + '0000';
                        $("#sec_proNameId").combobox('setValue', pro);
                    }
                }
            });
        $('#sec_cityNameId').combobox({
            //url:'itemManage!categorytbl',
            editable: false, //不可编辑状态
            cache: false,
            panelHeight: '200',//自动高度适合
            valueField: 'id',
            textField: 'name',
            onLoadSuccess: function () {
                var city = $('#startCityIdHidden').val();
                city = city.substring(0, 4) + "00";
                if (city) {
                    $.ajax({
                        type: "POST",
                        url: "/ticket/ticket/getAreaByCitycode.jhtml?cityCode=" + city,
                        cache: false,
                        dataType: "json",
                        success: function (data) {
                            if (data) {
                                //$("#sec_cityNameId").combobox('setValue', data.id);
                                //$("#sec_cityNameId").combobox('setText', data.name);
                            }
                        }
                    });
                }
            }
        });
        var startCityIdHidden = $("#startCityIdHidden").val();
        if (startCityIdHidden) {
            var city = $('#startCityIdHidden').val();
            city = city.substring(0, 4) + "00";
            $.post("/ticket/ticket/getAreaByCitycode.jhtml?cityCode=" + city,
                function (data) {
                    if (data) {
                        $("#sec_cityNameId").combobox('setValue', data.id);
                        $("#sec_cityNameId").combobox('setText', data.name);
                    }
                }, 'json'
            );
        }
        $("#yc_proNameId")
            .combobox(
            {
                url: '/ticket/ticket/getProvince.jhtml?name_startsWith=100000',
                editable: false, //不可编辑状态
                cache: false,
                panelHeight: '200',//自动高度适合
                valueField: 'id',
                textField: 'name',
                onHidePanel: function () {
                    $("#yc_cityNameId").combobox("setValue", '');
                    var lanmuid = $('#yc_proNameId').combobox('getValue');
                    $.ajax({
                        type: "POST",
                        url: "/ticket/ticket/getCity.jhtml?name_startsWith=" + lanmuid,
                        cache: false,
                        dataType: "json",
                        success: function (data) {
                            $("#yc_cityNameId").combobox("loadData", data);
                        }
                    });
                },
                onLoadSuccess: function () {
                    var city = $('#yc_startCityIdHidden').val();
                    if (city) {
                        var pro = city.substr(0, 2) + '0000';
                        $("#yc_proNameId").combobox('setValue', pro);
                    }
                }
            });
        $('#yc_cityNameId').combobox({
            //url:'itemManage!categorytbl',
            editable: false, //不可编辑状态
            cache: false,
            panelHeight: '200',//自动高度适合
            valueField: 'id',
            textField: 'name',
            onLoadSuccess: function () {
                var city = $('#yc_startCityIdHidden').val();
                city = city.substring(0, 4) + "00";
                if (city) {
                    $.ajax({
                        type: "POST",
                        url: "/ticket/ticket/getAreaByCitycode.jhtml?cityCode=" + city,
                        cache: false,
                        dataType: "json",
                        success: function (data) {
                            if (data) {
                                $("#yc_cityNameId").combobox('setValue', data.id);
                                $("#yc_cityNameId").combobox('setText', data.name);
                            }
                        }
                    });
                }
            }
        });
        var yc_startCityIdHidden = $("#yc_startCityIdHidden").val();
        if (yc_startCityIdHidden) {
            var city = $('#yc_startCityIdHidden').val();
            city = city.substring(0, 4) + "00";
            $.post("/ticket/ticket/getAreaByCitycode.jhtml?cityCode=" + city,
                function (data) {
                    if (data) {
                        $("#yc_cityNameId").combobox('setValue', data.id);
                        $("#yc_cityNameId").combobox('setText', data.name);
                    }
                }, 'json'
            );
        }
        $("#boat_proNameId")
            .combobox(
            {
                url: '/ticket/ticket/getProvince.jhtml?name_startsWith=100000',
                editable: false, //不可编辑状态
                cache: false,
                panelHeight: '200',//自动高度适合
                valueField: 'id',
                textField: 'name',
                onHidePanel: function () {
                    $("#boat_cityNameId").combobox("setValue", '');
                    var lanmuid = $('#boat_proNameId').combobox('getValue');
                    $.ajax({
                        type: "POST",
                        url: "/ticket/ticket/getCity.jhtml?name_startsWith=" + lanmuid,
                        cache: false,
                        dataType: "json",
                        success: function (data) {
                            $("#boat_cityNameId").combobox("loadData", data);
                        }
                    });
                },
                onLoadSuccess: function () {
                    var city = $('#boat_startCityIdHidden').val();
                    if (city) {
                        var pro = city.substr(0, 2) + '0000';
                        $("#boat_proNameId").combobox('setValue', pro);
                    }
                }
            });
        $('#boat_cityNameId').combobox({
            //url:'itemManage!categorytbl',
            editable: false, //不可编辑状态
            cache: false,
            panelHeight: '200',//自动高度适合
            valueField: 'id',
            textField: 'name',
            onLoadSuccess: function () {
                var city = $('#boat_startCityIdHidden').val();
                city = city.substring(0, 4) + "00";
                if (city) {
                    $.ajax({
                        type: "POST",
                        url: "/ticket/ticket/getAreaByCitycode.jhtml?cityCode=" + city,
                        cache: false,
                        dataType: "json",
                        success: function (data) {
                            if (data) {
                                $("#boat_cityNameId").combobox('setValue', data.id);
                                $("#boat_cityNameId").combobox('setText', data.name);
                            }
                        }
                    });
                }
            }
        });
        var boat_startCityIdHidden = $("#boat_startCityIdHidden").val();
        if (boat_startCityIdHidden) {
            var city = $('#boat_startCityIdHidden').val();
            city = city.substring(0, 4) + "00";
            $.post("/ticket/ticket/getAreaByCitycode.jhtml?cityCode=" + city,
                function (data) {
                    if (data) {
                        $("#boat_cityNameId").combobox('setValue', data.id);
                        $("#boat_cityNameId").combobox('setText', data.name);
                    }
                }, 'json'
            );
        }
    },
    editFenlei: function () {
        var t = Math.random(); 	// 保证页面刷新
        var url = "/ticket/ticket/addcategory.jhtml";
        var ifr = $("#dd").children()[0];
        $(ifr).attr("src", url);
        $('#dd').dialog({
            title: '修改分类',
            width: 480,
            height: 400,
            closed: true,
            cache: false,
            modal: true
        });
        $('#dd').dialog('open');
        /*var url = "/goods/goods/addcategory.jhtml";
         //			console.log(window.parent.$("#tabs"));
         window.parent.parent.$("#tabs").tabs('add',{
         title:'增加分类',
         content:'<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>',
         closable:true,
         });*/
    }
};
//返回本页面数据
function getIfrData() {
    var data = {};
    data.ticketId = $('#ticketId').val();
//	data.productName = $('#name').textbox('getValue');
//	data.productAttr = $(':checked[name="productAttr"]').val();
    return data;
}
$(function () {
    TicketEdit.init();
    TicketEdit.initCommbox();
//	TicketEdit.initUploadImg();
    $("#add_pic").click(function () {
        $("#up").click();
        TicketEdit.addImg();
    });
    TicketEdit.initRemove_img();
    TicketEdit.ajaxFileUpload();	//点击按钮直接上传图片
    TicketEdit.initCategory();
    TicketEdit.initProCommbox();
    $("#scenicNameId").combobox({
        onChange: function () {
            $("#sce_span_address").html("");
            $("#sce_span_aji").html("");
            $("#sce_span_area").html("");
            $("#jd_address").textbox("setValue", "");
            $("#sec_cityNameId").combobox('setValue', "请选择城市或地区");
            $("#sec_proNameId").combobox('setValue', "请选择省份");
            $("input[name='ticket.sceAji']")[5].checked = true;
            $("#hidden_secName").val($("#scenicNameId").combobox('getText'));
            $("#sce_div_area").show();
            $("#sce_div_aji").show();
            $("#sce_div_address").show();
        }
    });
    $("#search_classify").bind('click', function () {
        alert("绑定单击事件");
    });
});
