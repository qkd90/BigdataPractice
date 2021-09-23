// 弹出消息弹窗
var PROMPT_ARRAY = [];
function promptMessage(message, timeout, autoClose) {
    $("#msg_box").addClass("open").click(function () {
        $.each(PROMPT_ARRAY, function (i, data) {
            clearTimeout(data);
        });
        closeMsgBox();
    });
    $(".cg_prompt").fadeIn().find("p").text(message);
    if (autoClose != null ? autoClose : true) {
        PROMPT_ARRAY.push(setTimeout(function () {
                closeMsgBox();
            }, timeout ? timeout : 2000) //默认延迟2秒
        )
    }
}

// 改变消息弹窗提示文字 (需要已经弹出消息框, 并且模式为持续显示 !)
function changeMsg(newMsg) {
    if (!isNull(newMsg)) {
        $(".cg_prompt").find("p").text(newMsg);
    }
}

// 手动关闭自动弹窗 (仅当消息框为持续显示时候才需要手动关闭, 可延时关闭, 否则立即关闭)
// 可使用一个提示窗口关闭后的回调函数, 注意! 该函数不可为匿名内部函数! 否则不会被调用到
function closeMsgBox(options) {
    setTimeout(function () {
        $("#msg_box").removeClass("open").unbind("click");
        $(".cg_prompt").fadeOut();
        if (options && typeof options.afterclose == 'function') {
            options.afterclose;
        }
    }, options && options.timeout ? options.timeout : 0)
}
function promptWarn(message, timeout) {
    $(".mask").addClass("open");
    $(".cg_prompt2").fadeIn().find("p").text(message);
    setTimeout(function () {
        $(".mask").removeClass("open");
        $(".cg_prompt2").fadeOut();
    }, timeout ? timeout : 3000); //延迟3秒
}

// 过渡动画
function loadingBegin(message) {
    if(isNull(message)){
        $(".loading-background").show();
        $("#loading").show();
    }else{
        $(".loading-background").show();
        $("#loading").find("p").html(message);
        $("#loading").show();
    }

}
function loadingEnd() {
    $(".loading-background").hide();
    $("#loading").hide();
}

function deleteWarn(msg, fn, node, num) {
    if(isNull(num)){
        $(".grxxccx .determine").html("确定");
        $(".grxxccx .cancel").html("取消");
    }else{
        if(num=='1'){
            $(".grxxccx .determine").html("是");
            $(".grxxccx .cancel").html("否");
        }
    }

    $(".grxxccx .ppc_nr").text(msg);
    $(".mask").show();
    $(".grxxccx").fadeIn();
    $(".grxxccx .determine").on("click", function () {
        $(".mask").hide();
        $(".grxxccx").hide();
        fn(node);
        $(this).off();
        $(".grxxccx .cancel").off();
    });
    $(".grxxccx .cancel").on("click", function () {
        $(".mask").hide();
        $(".grxxccx").hide();
        $(this).off();
        $(".grxxccx .determine").off();
    });
}

function deleteWarn2(msg, fn1, node1, fn2, node2) {
    $(".grxxccx .determine").html("去添加");
    $(".grxxccx .cancel").html("不,直接购买");

    $(".grxxccx .ppc_nr").text(msg);
    $(".mask").show();
    $(".grxxccx").fadeIn();
    $(".grxxccx .determine").on("click", function () {
        $(".mask").hide();
        $(".grxxccx").hide();
        fn1(node1);
        $(this).off();
        $(".grxxccx .cancel").off();
    });
    $(".grxxccx .cancel").on("click", function () {
        $(".mask").hide();
        $(".grxxccx").hide();
        fn2(node2);
        $(this).off();
        $(".grxxccx .determine").off();

    });
}


function protocolPop(title, content) {
    var pop = $(".protocol_pop");
    pop.find("h1").text(title);
    pop.find("div").html(content);
    $("#msg_box").addClass("open");
    $("#msg_box,.protocol_pop i").click(function () {
        $("#msg_box").removeClass("open").unbind("click");
        pop.fadeOut();
    });
    pop.fadeIn();
}