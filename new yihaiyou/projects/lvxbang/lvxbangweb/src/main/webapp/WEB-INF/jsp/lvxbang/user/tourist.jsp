<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 16/1/19
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>常用信息</title>
    <meta name="keywords" content="index"/>
    <meta name="description" content="index"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/float.css" rel="stylesheet" type="text/css">
    <link href="/css/member.css" rel="stylesheet" type="text/css">
</head>
<body myname="Member" class="member_xx member_cylxr" id="nav">
<!-- #BeginLibraryItem "/lbi/head.lbi" -->
<!--Float_w-->

<!--Float_w end-->

<!--head-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<!--head  end--><!-- #EndLibraryItem -->
<div class="main cl">
    <jsp:include page="/WEB-INF/jsp/lvxbang/user/personalHeader.jsp"></jsp:include>

    <div class=" cl mc_nr">
        <div class="w1000 mc_nr_bg">
            <div class="m_left fl">
                <a href="#" class="checked not"><i></i>常用出行人</a>
            </div>

            <div class="m_right fr">
                <div class="m_r_top">
                    <div class="m_r_top_l oval4 fl posiR disn">
                        <input type="text" placeholder="输入旅客姓名" value="" class="input" id="name" onkeydown="KeyDown()">
                        <a href="javaScript:;" class="ico" onclick="TouristList.refreshList()"></a>
                    </div>

                    <p class="m_r_top_r fr textR">
                        <a href="javaScript:;" class="add btn" onclick="TouristList.addTourist()">增加</a>
                        <a href="javaScript:;" class="del btn" onclick="TouristList.delSelected()">删除</a>
                    </p>
                </div>

                <div class="m_r_center cl tourist_list">
                    <dl class="m_r_center_dl">
                        <dt>
                        <div class="w1"><span class="checkbox disB fl mr5" input="selectall"><i></i></span><span
                                class="fl">序号</span></div>
                        <div class="w2">姓名</div>
                        <div class="w3">性别</div>
                        <div class="w4">证件类型</div>
                        <div class="w5">证件号码</div>
                        <div class="w6">手机电话</div>
                        <div class="w7">旅客类型</div>
                        </dt>
                        <div id="mytourist">

                        </div>

                    </dl>
                    <p class="cl"></p><!-- #BeginLibraryItem "/lbi/paging.lbi" -->
                    <!--pager-->
                    <p class="cl"></p>
                    <img src="/images/food2.png" class="fr foodxx" style="margin-right:73px;"/>
                    <div class="m-pager st cl">

                    </div>
                    <!--pager end-->
                    <p class="cl h30"></p><!-- #EndLibraryItem --></div>

            </div>
            <p class="cl"></p>
        </div>
    </div>

</div>
<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<!--foot-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<!--foot end--><!-- #EndLibraryItem --></body>
</html>
<script src="/js/lib/pager.js" type="text/javascript"></script>
<script src="/js/lvxbang/user/myTourist.js" type="text/javascript"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //下拉框
        $('.m_r_center_dl').delegate('.m_hide .name,.m_hide i', 'click', function () {
            $(this).nextAll(".m_hide_hi").show();
            $(this).parents(".m_hide").addClass("checked");
        });

        $('.m_r_center_dl').delegate(' .m_hide.checked i', 'click', function () {
            $(this).nextAll(".m_hide_hi").hide();
            $(this).parents(".m_hide").removeClass("checked");
        });


        $('.m_r_center_dl').delegate('.m_hide_hi li', 'click', function () {
            var label = $(this).text();
            $(this).closest(".m_hide_hi").hide();
            $(this).parents(".m_hide").removeClass("checked").find(".name").text(label);
        });

        //修改
        $('.m_r_center_dl').delegate('.m_add', 'click', function () {
            if (TouristList.busy) {
                alert("请完成当前修改或添加");
                return;
            }
            TouristList.busy = true;
            var closest = $(this).closest("dd");
            closest.addClass("checked");
            closest.find(".w2 .m_hide input").val(closest.find(".w2 .m_name").text());
            closest.find(".w5 .m_hide input").val(closest.find(".w5 .m_name").text());
            closest.find(".w6 .m_hide input").val(closest.find(".w6 .m_name").text());
            closest.find(".w3 .m_hide .name").text(closest.find(".w3 .m_name").text());
            closest.find(".w4 .m_hide .name").text(closest.find(".w4 .m_name").text());
            closest.find(".w7 .m_hide .name").text(closest.find(".w7 .m_name").text());
        });

        //确定
//        $('dl').delegate('dd .m_r_c_but a.fl', 'click', function () {
//            var closest=$(this).closest("dd");
//            closest.find(".w2 .m_name").text(closest.find(".w2 .m_hide input").val());
//            closest.find(".w5 .m_name").text(closest.find(".w5 .m_hide input").val());
//            closest.find(".w6 .m_name").text(closest.find(".w6 .m_hide input").val());
//            closest.find(".w3 .m_name").text(closest.find(".w3 .m_hide .name").text());
//            closest.find(".w4 .m_name").text(closest.find(".w4 .m_hide .name").text());
//            closest.find(".w7 .m_name").text(closest.find(".w7 .m_hide .name").text());
//            closest.removeClass("checked");
//            TouristList.updateOrInsert.call(this);
//        });

        //取消
//        $('dl').delegate('dd .m_r_c_but a.fr', 'click', function () {
//            var closest = $(this).closest("dd");
//            closest.removeClass("checked");
//            TouristList.cancelEdit();
//        });


        //全部删除添加的乘客
//        var calculatePrice = 0
//        $('.m_r_center_dl').delegate('.m_del', 'click', function () {
//            $(this).closest("dd").fadeOut(function () {
//                $(this).remove();
//                calculatePrice--;
//            });
//        });

//        //添加乘客
//        $('.m_r_top_r .add').click(function(){
//            if($(".m_r_center_dl > dd").length < 10){
//                var num=$(".m_r_center_dl dd").length+1;
//                $($('#tplPassenger').html().replace('##id',num)).appendTo($('.m_r_center_dl')).hide().fadeIn();
//                //计算价格
//                calculatePrice++;
//            }else{
//                alert("10")
//            }
//        });

        //删除乘客
//        $('.m_r_top_r .del').click(function () {
//            if ($(".m_r_center_dl .checkbox").hasClass('checked')) {
//                $(".m_r_center_dl .checkbox.checked").closest("dd").remove();
//            }
//
//        });

        //checkbox
        $('.m_r_center_dl').delegate('.checkbox', 'click', function () {
            var input = $(this).attr("input");
            var myStaute = $(this).attr("data-staute");
            if (!myStaute) {
                if (input == "selectall") {
                    $(this).addClass("checked").attr("data-staute", "1").parents('.m_r_center_dl').find("dd .checkbox").addClass("checked").attr("data-staute", "1");
                } else {
                    $(this).addClass("checked").attr("data-staute", "1");
                }
            }
            else {
                if (input == "selectall") {
                    $(this).removeClass("checked").removeAttr("data-staute").parents('.m_r_center_dl').find("dd .checkbox").removeClass("checked").removeAttr("data-staute");
                } else {
                    $(this).removeClass("checked").removeAttr("data-staute");
                    $(this).parents('.m_r_center_dl').find("dt .checkbox").removeClass("checked").removeAttr("data-staute");
                }

            }
        });

    });
</script>
<script>
    function KeyDown() {
        if (event.keyCode == 13) {
            event.returnValue = false;
            event.cancel = true;
            TouristList.refreshList();
        }
    }
</script>
<script id="tplPassenger" type="text/mytemplate">
    <dd class="checked" id="tourist-0">
        <div class="posiA m_r_c_but">
            <a href="javaScript:;" class="fl" onclick="TouristList.updateOrInsert(0)">确定</a>
            <a href="javaScript:;" class="fr" onclick="TouristList.cancelEdit(0)">取消</a>
        </div>
        <div class="w1 mt5">
            <span class="checkbox disB fl mr5" input="options"><i></i></span><span class="num">##id</span>
        </div>
        <div class="w2 m_click">
            <p class="m_name"></p>
            <div class="posiR m_hide" >
                <input type="text" placeholder="姓名" value="" class="input" id="name-0" ></div>
        </div>
        <div class="w3 m_click">
            <p class="m_name">男</p>
            <div class="posiR m_hide" ">
                <span class="name" id="gender-0" >男</span>
                <i class="ico"></i>
                <div class="posiA m_hide_hi">
                    <ul>
                        <li>男</li>
                        <li>女</li>
                    </ul>
                </div>
            </div>

        </div>
        <div class="w4 m_click">
            <p class="m_name">身份证</p>
            <div class="posiR m_hide">
                <span class="name" id="idtype-0">身份证</span>
                <i class="ico"></i>
                <div class="posiA m_hide_hi">
                    <ul>
                        <li>身份证</li>
                        <li>护照</li>
                    </ul>
                </div>
            </div>

        </div>
        <div class="w5 m_click">
            <p class="m_name"></p>
            <div class="posiR m_hide"><input type="text" placeholder="身份证号码" value="" class="input" id="idnumber-0">
            </div>

        </div>
        <div class="w6 m_click">
            <p class="m_name"></p>
            <div class="posiR m_hide"><input type="text" placeholder="手机号码" value="" class="input" id="tel-0"></div>
        </div>
        <div class="w7 m_click">
            <p class="m_name">成人</p>
            <div class="posiR m_hide">
                <span class="name" id="people-0">成人</span>
                <i class="ico"></i>
                <div class="posiA m_hide_hi">
                    <ul>
                        <li>成人</li>
                        <li>儿童</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="w8"><a href="javaScript:;" class="m_add" myprovision_id="1"></a></div>
        <div class="w9"><a href="javaScript:;" class="m_del"></a></div>
    </dd>

</script>

<script type="text/html" id="tpl-tourist-list-item">
    <dd id="tourist-{{id}}">
        <div class="posiA m_r_c_but">
            <a href="javaScript:;" class="fl" onclick="TouristList.updateOrInsert({{id}})">确定</a>
            <a href="javaScript:;" class="fr" onclick="TouristList.cancelEdit({{id}})">取消</a>
        </div>
        <input id="id-{{id}}" type="hidden" value="{{id}}"/>
        <div class="w1 mt5">
            <span class="checkbox disB fl mr5 tourist" input="options">
                <input type="hidden" class="touristId" value="{{id}}"/>
                <i></i>
            </span>
            <span class="num">{{index}}</span>
        </div>
        <div class="w2 m_click">
            <p class="m_name">{{name}}</p>
            <div class="posiR m_hide"><input type="text" placeholder="姓名" value="{{name}}"
                                             class="input" id="name-{{id}}">
            </div>
        </div>
        <div class="w3 m_click">
            <p class="m_name">
                {{if gender == "male"}}
                男
                {{else}}
                女
                {{/if}}
            </p>

            <div class="posiR m_hide" style="width:55%;margin-left:13px;">
                <span class="name" id="gender-{{id}}" style="padding-left: 10px;">
                {{if gender == "male"}}
                男
                {{else}}
                女
                {{/if}}
                </span>

                <i class="ico"></i>
                <div class="posiA m_hide_hi">
                    <ul>
                        <li style=" padding: 3px 0 0 29%;">男</li>
                        <li style=" padding: 3px 0 0 29%;">女</li>
                    </ul>
                </div>
            </div>

        </div>
        <div class="w4 m_click">
            <p class="m_name"">
            {{if idType == "IDCARD"}}
            身份证
            {{else}}
            护照
            {{/if}}
            </p>


            <div class="posiR m_hide">
                <span class="name" id="idtype-{{id}}>
                {{if idType == " IDCARD"}}
                身份证
                {{else}}
                护照
                {{/if}}
                </span>

                <i class="ico"></i>
                <div class="posiA m_hide_hi">
                    <ul>
                        <li>身份证</li>
                        <li>护照</li>
                    </ul>
                </div>
            </div>

        </div>
        <div class="w5 m_click">
            <p class="m_name">{{idNumber}}</p>
            <div class="posiR m_hide"><input type="text" placeholder="身份证号" value="{{idNumber}}"
                                             class="input" id="idnumber-{{id}}">
            </div>

        </div>
        <div class="w6 m_click" >
            <p class="m_name">{{tel}}</p>
            <div class="posiR m_hide"><input type="text" placeholder="电话号码" value="{{tel}}"
                                             class="input" id="tel-{{id}}">
            </div>
        </div>
        <div class="w7 m_click">
            <p class="m_name">
                {{if peopleType == "ADULT"}}
                成人
                {{else}}
                儿童
                {{/if}}
            </p>

            <div class="posiR m_hide" style="width:70%">
                <span class="name" id="people-{{id}}">
                {{if peopleType == "ADULT"}}
                成人
                {{else}}
                儿童
                {{/if}}
                </span>

                <i class="ico"></i>
                <div class="posiA m_hide_hi">
                    <ul>
                        <li style="padding: 3px 0 0 12%;">成人</li>
                        <li style="padding: 3px 0 0 12%;">儿童</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="w8"><a href="javaScript:;" class="m_add"></a></div>
        <div class="w9"><a href="javaScript:;" class="m_del" onclick="TouristList.deleteByIds({{id}})"></a></div>
    </dd>
</script>