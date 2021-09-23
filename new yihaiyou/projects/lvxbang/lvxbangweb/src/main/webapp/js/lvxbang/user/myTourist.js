/**
 * Created by vacuity on 16/1/19.
 */



var TouristList = {
    pager: null,
    data: {},
    busy: false,
    nowIndex: 0,
    init: function () {
        TouristList.pager = new Pager(
            {
                pageSize: 10,
                countUrl: "/lvxbang/user/myTouristCount.jhtml",
                searchData: {},
                pageRenderFn: function (pageNo, pageSize) {
                    TouristList.data.pageNo = pageNo;
                    TouristList.data.pageSize = pageSize;
                    $.post("/lvxbang/user/getMyTourists.jhtml", TouristList.data, function (result) {
                        var num = $('#tourist-0').find('.num').html();
                        if (!isNull(num)) {
                            $('#tourist-0').find('.num').html(result.length+1);
                        }
                        var html = "";
                        for (var i = 0; i < result.length; i++) {
                            var data = result[i];
                            data.index = i + 1;
                            html += template("tpl-tourist-list-item", data);
                        }
                        $("#mytourist").html(html);
                    },"json");
                }
            }
        );
        TouristList.refreshList();
    },

    refreshList: function () {
        TouristList.data = {};

        var name = $("#name").val();
        if (name != null && name != "") {
            TouristList.data["name"] = name;
        }
        TouristList.pager.init(TouristList.data);
    },


    deleteByIds: function (ids) {
        var msg = "确定删除该常用出行人？";
        deleteWarn(msg, function() {
            $.post("/lvxbang/user/delTourists.jhtml",
            {
                delIds: ids
            },
            function (result) {
                if (result.success) {

                    TouristList.refreshList();
                } else {
                    promptWarn("删除失败");
                }
            },"json")}
        );

    },

    delSelected: function () {
        var ids = "";
        var checkList = $(".tourist");
        for (var i = 0; i < checkList.size(); i++) {
            var check = checkList.eq(i);
            if (check.hasClass("checked")) {
                ids += check.children(".touristId").val() + ",";
            }
        }

        if($('#tourist-0').find('.checkbox').hasClass("checked")){
            TouristList.busy = false;
        }

        if (ids == "" & !$('#tourist-0').find('.checkbox').hasClass("checked")) {
            promptWarn("请至少选择一项进行删除");
            return;
        }
        TouristList.deleteByIds(ids);
    },

    initPanel: function () {
        $(".personal").removeClass("checked");
        $("#tourist-panel").addClass("checked");
    },


    addTourist: function () {
        if (TouristList.busy) {
            //
            promptWarn("请编辑完新添加联系人");
            return;
        }
        TouristList.busy = true;
        TouristList.add = false;
        var num = $(".m_r_center_dl dd").length + 1;
        $($('#tplPassenger').html().replace('##id', num)).appendTo($('.m_r_center_dl')).hide().fadeIn();

        var newTourist = $("#newTourist");
        newTourist.find("div > .m_name").hide();
        newTourist.find("div > .m_hide").show();

        if (num == 11) {
            //
            var lastTourist = $(".m_r_center_dl dd").eq(9);
        }
        //$(this).attr("myprovision_id", 1);
    },

    editTourist: function (id) {
        //
        if (TouristList.busy) {
            promptWarn("请完成当前更改");
            return;
        }
        $("#tourist-" + id).find("div > .m_name").hide();
        $("#tourist-" + id).find("div > .m_hide").show();
        TouristList.busy = true;
    },

    changeIndex: function (id) {
        TouristList.nowIndex = id;
    },


    updateOrInsert: function (id) {
        //var id = TouristList.nowIndex;
        var name = $("#name-" + id).val();
        var gender = $("#gender-" + id).text();
        var idType = $("#idtype-" + id).text();
        var idNumber = $("#idnumber-" + id).val();
        var tel = $("#tel-" + id).val();
        var peopleType = $("#people-" + id).text();

        $("#name-" + id).parent("div").removeClass("border_red");
        $("#idnumber-" + id).parent("div").removeClass("border_red");
        $("#tel-" + id).parent("div").removeClass("border_red");

        var data = {};
        if (Number(id) != 0) {
            data["id"] = $("#id-" + id).val();
        }
        if (name == "") {
            $("#name-" + id).parent("div").addClass("border_red");
            promptWarn("联系人姓名不能为空");
            return;
        }

        data["name"] = name;
        if (gender == "男") {
            gender = "male";
            data["gender"] = gender;
        } else if (gender == "女") {
            gender = "female";
            data["gender"] = gender;
        }

        if (idType == "身份证") {
            idType = "IDCARD";
            data["idType"] = idType;
        } else if (idType == "护照") {
            idType = "PASSPORT";
            data["idType"] = idType;
        }

        //var idNo = /^(\d{18})$/;
        var idNo = /^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$/;
        if (idNumber == "" || !idNumber.match(idNo)) {
            $("#idnumber-" + id).parent("div").addClass("border_red");
            promptWarn("联系人证件号码格式不正确");
            return;
        }

        data["idNumber"] = idNumber;
        var mobile = /^((1[3,5,8,7,4]{1})\d{9})$/;
        if (tel == "") {
            $("#tel-" + id).parent("div").addClass("border_red");
            promptWarn("联系人手机号不能为空");
            return;
        } else if (!tel.match(mobile)) {
            //
            $("#tel-" + id).parent("div").addClass("border_red");
            promptWarn("联系人手机号格式不正确，请重新输入");
            return;
        }

        data["telephone"] = tel;
        if (peopleType == "成人") {
            peopleType = "ADULT";
            data["peopleType"] = peopleType;
        } else if (peopleType == "儿童") {
            peopleType = "KID";
            data["peopleType"] = peopleType;
        }

        $.post("/lvxbang/user/saveTourist.jhtml", data, function (result) {
            TouristList.busy = false;
            if (result.success) {
                if (Number(id) == 0) {
                    TouristList.refreshList();
                    $("#tourist-0").remove();
                } else {
                    var closest = $("#tourist-" + id);
                    closest.find(".w2 .m_name").text(closest.find(".w2 .m_hide input").val());
                    closest.find(".w5 .m_name").text(closest.find(".w5 .m_hide input").val());
                    closest.find(".w6 .m_name").text(closest.find(".w6 .m_hide input").val());
                    closest.find(".w3 .m_name").text(closest.find(".w3 .m_hide .name").text());
                    closest.find(".w4 .m_name").text(closest.find(".w4 .m_hide .name").text());
                    closest.find(".w7 .m_name").text(closest.find(".w7 .m_hide .name").text());
                    closest.removeClass("checked");
                }
                promptMessage("修改成功!", 1000, true);
            } else {
                promptMessage(result.msg, 1000, true);
                return;
            }
        },"json");
    },

    //changeId: function (id) {
    //    $("#tourist-0").id = "tourist-" + id;
    //    $("#name-0").id = "name-" + id;
    //    var gender = $("#gender-" + id).text();
    //    var idType = $("#idtype-" + id).text();
    //    var idNumber = $("#idnumber-" + id).val();
    //    var tel = $("#tel-" + id).val();
    //    var peopleType = $("#people-" + id).text();
    //},

    cancelEdit: function (id) {
        //
        //var id = TouristList.nowIndex;
        var closest = $("#tourist-" + id);
        closest.removeClass("checked");
        if (id == 0) {
            //TouristList.refreshList();
            $("#tourist-0").remove();
        }
        //TouristList.nowIndex = 0;
        TouristList.busy = false;
    }

};


$(function () {
    TouristList.initPanel();
    TouristList.init();
});
