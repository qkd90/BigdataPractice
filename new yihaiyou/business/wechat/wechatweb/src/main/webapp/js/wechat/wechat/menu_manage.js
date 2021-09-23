/**
 * Created by vacuity on 15/11/25.
 */


var WechatMenu = {

    flag: true,
    currNode: null,
    currMax: 16,
    inValid: true,
    firstFlag: true,


    initTree: function (id) {
        var accountId = $("#accountId").val();
        var treeUrl = "/wechat/wechatMenu/getList.jhtml?accountId=" + id;
        $('#tree').tree({
            url: treeUrl,
            //checkbox:true,
            //dnd : true,
            onSelect: function (node) {
                //alert(node.text);
                if (!WechatMenu.inValid){
                    return;
                }
                var nowNode = $('#tree').tree('getSelected');
                WechatMenu.dealMenu();
                WechatMenu.currNode = nowNode;
                WechatMenu.flag = false;
                WechatMenu.edit(node);
            },
            onBeforeSelect: function(node) {

                if (WechatMenu.checkInput()){
                    return true;
                } else {
                    alert("数据不完整，请补充完整数据再进行下一步");
                    if(WechatMenu.currNode != null) {
                        $('#tree').tree('check', WechatMenu.currNode.target);
                    }
                    return false;
                }
            },
            onContextMenu: function (e, node) {
                e.preventDefault();
                $('#tree').tree('select', node.target);
                $('#mm').menu('show', {
                    left: e.pageX,
                    top: e.pageY
                });
            },
            onLoadSuccess: function (node, data) {
                WechatMenu.initParentCombox();
            }
        });

    },

    checkInput: function () {
        if (WechatMenu.firstFlag){
            return true;
        }
        var father = $('#parentId').combobox('getValue');
        var name = $("#menuName").val();
        var resource = $("#resource").combobox('getValue');
        var url = $("#exterminal-link").val();

        if(!$("#menuName").textbox('isValid')){
            return false;
        }

        if (WechatMenu.flag == true) {
            // add
            if (url == null || url == ""){
                if(resource == null || resource == ""){
                    return false;
                }
            } else {
                if (!$("#exterminal-link").textbox('isValid')){
                    return false;
                }
            }
        } else {
            // edit
            var isLeaf = $('#tree').tree('isLeaf', WechatMenu.currNode.target);
            if(isLeaf){
                if (url == null || url == ""){
                    if(resource == null || resource == ""){
                        return false;
                    }
                } else {
                    if (!$("#exterminal-link").textbox('isValid')){
                        return false;
                    }
                }
            }
        }
        return true;
    },

    edit: function (node) {
        //var node = WechatMenu.currNode;
        var children = node.children;
        if (children && children.length > 0) {
            //$("#resource-tr").addClass("display-none");
            $(".resource").addClass("display-none");
        } else {
            //$("#resource-tr").removeClass("display-none");
            $(".resource").removeClass("display-none");
            if (node.resourceId != null) {
                $('#resource').combobox('setValue', node.resourceId);
            }
            if (node.url != null) {
                $("#exterminal-link").textbox('setValue',node.url);
            } else {
                $("#exterminal-link").textbox('setValue','');
            }

        }


        $("#parent-tr").addClass("display-none");
        $("#menuName").textbox('setValue',node.menuName);


    },

    remove: function () {
        var node = $('#tree').tree('getSelected');
        $('#tree').tree('remove', node.target);
        WechatMenu.initParentCombox();
        WechatMenu.flag = true;
        WechatMenu.currNode = null;
        WechatMenu.inValid = true;
        WechatMenu.firstFlag = true;
    },

    up: function () {
        var node = $('#tree').tree('getSelected');
        var nodeId = node.id;
        var par = $('#tree').tree('getParent', node.target);
        var data = $('#tree').tree('getRoots');
        if (par == null) {
            //一级菜单上移
            for (var i = 0; i < data.length; i++) {
                if (data[i].id == nodeId) {
                    if (i == 0) {
                        break;
                    } else {
                        //var s = data[i - 1];
                        //data[i - 1].target = data[i].target;
                        //data[i].target = s.target;

                        WechatMenu.updateData(data[i], data[i - 1]);

                        break;
                    }
                }
            }
        } else {
            var parId = par.id;
            for (var i = 0; i < data.length; i++) {
                if (data[i].id == parId) {
                    var children = data[i].children;
                    for (var j = 0; j < children.length; j++) {
                        if (children[j].id == nodeId) {
                            if (j == 0) {
                                break;
                            } else {
                                //var s = children[j - 1];
                                //children[j - 1] = children[j];
                                //children[j] = s;

                                //var s = children[j - 1];
                                //children[j - 1].target = children[j].target;
                                //children[j].target = s.target;
                                //
                                //WechatMenu.updateData(children[j-1]);
                                WechatMenu.updateData(children[j], children[j-1]);
                                break;
                            }
                        }
                    }
                }
            }
        }
    },

    down: function () {
        var node = $('#tree').tree('getSelected');
        var nodeId = node.id;
        var par = $('#tree').tree('getParent', node.target);
        var data = $('#tree').tree('getRoots');


        if (par == null) {
            //一级菜单下移
            for (var i = 0; i < data.length; i++) {
                if (data[i].id == nodeId) {
                    if (i == data.length - 1) {
                        break;
                    } else {
                        //var s = data[i + 1];
                        //data[i + 1] = data[i];
                        //data[i] = s;

                        //var s = data[i + 1];
                        //data[i + 1].target = data[i].target;
                        //data[i].target = s.target;
                        //WechatMenu.updateData(data[i+1]);
                        //WechatMenu.updateData(data[i]);

                        WechatMenu.updateData(data[i], data[i + 1]);
                        break;
                    }
                }
            }
        } else {
            var parId = par.id;
            for (var i = 0; i < data.length; i++) {
                if (data[i].id == parId) {
                    var children = data[i].children;
                    for (var j = 0; j < children.length; j++) {
                        if (children[j].id == nodeId) {
                            if (j == children.length - 1) {
                                break;
                            } else {
                                //var s = children[j + 1];
                                //children[j + 1] = children[j];
                                //children[j] = s;

                                //var s = children[j + 1];
                                //children[j + 1].target = children[j].target;
                                //children[j].target = s.target;

                                WechatMenu.updateData(children[j], children[j + 1]);
                                break;
                            }
                        }
                    }
                }
            }
        }
        //$('#tree').tree('loadData', data);
    },

    updateData: function (node1, node2) {

        var id = node1.id;
        var text = node1.text;
        var checked = node1.checked;
        var resourceId = node1.resourceId;
        var url = node1.url;
        var children = node1.children;

        node1.id = node2.id;
        node2.id = id;

        node1.text = node2.text;
        node2.text = text;

        node1.checked = node2.checked;
        node2.checked = checked;

        node1.resourceId = node2.resourceId;
        node2.resourceId = resourceId;

        node1.url = node2.url;
        node2.url = url;

        node1.children = node2.children;
        node2.children = children;


        $('#tree').tree('update', node1);
        $('#tree').tree('update', node2);

    },

    update: function () {
        var node = $('#tree').tree('getSelected');
        if (node) {
            node.text = '修改';  //-->txt-->DB
            node.iconCls = 'icon-save'; //-->sel-->DB
            $('#tree').tree('update', node);
        }
    },

    append: function () {
        var node = $('#tree').tree('getSelected');

        $('#tree').tree('append', {
            parent: (node ? node.target : null),
            data: [
                {
                    text: 'new1',//  -->txt-->DB
                    id: '1',
                }
            ]
        });
    },

    initCombox: function () {
        $('#resource').combobox({
            url: "/wechat/wechatMenu/getResourceList.jhtml",
            valueField: 'id',
            textField: 'resName',
            panelHeight: 'auto',

            onSelect: function (record) {
            },
            onLoadSuccess: function () { //加载完成后,设置选中第一项
//                var val = $(this).combobox("getData");
//                for (var item in val[0]) {
//                    if (item == "id") {
//                        $(this).combobox("select", val[0][item]);
//                    }
//                }
            }
        });
    },

    addMenu: function () {
        if (!WechatMenu.checkInput()){
            alert("数据不完整，请补充完整数据再进行下一步");
            return;
        }
        WechatMenu.dealMenu();
        if ($('#tree').tree('getRoots').length < 3) {
        }
        WechatMenu.flag = true;
        $("#parent-tr").removeClass("display-none");
        $(".resource").removeClass("display-none");

        $("#menuName").textbox('setValue', "");
        $('#resource').combobox('setValue', "");
        $("#exterminal-link").textbox('setValue','');

    },


    initParentCombox: function () {
        var list = new Array();
        var parent = $('#tree').tree('getRoots');
        var topCom = {};
        topCom["id"] = -1;
        topCom["text"] = "顶级菜单";
        list.push(topCom);
        for (var i = 0; i < parent.length; i++) {
            var parCom = {};
            parCom["id"] = parent[i].id;
            parCom["text"] = parent[i].text;
            list.push(parCom);
        }
        $('#parentId').combobox({
            data: list,
            valueField: 'id',
            textField: 'text',
            panelHeight: 'auto',
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

    dealMenu: function () {
        if(WechatMenu.firstFlag){
            WechatMenu.firstFlag = false;
            return;
        }
        if (WechatMenu.flag == true) {
            WechatMenu.dealAdd();
        } else {
            WechatMenu.dealEdit();
        }
    },

    dealAdd: function () {
        var father = $('#parentId').combobox('getValue');
        var name = $("#menuName").val();
        var resource = $("#resource").combobox('getValue');
        var url = $("#exterminal-link").val();

        if (father == null) {
            //alert("数据不完整无法完成菜单添加");
            return;
        }
        var data = $('#tree').tree('getRoots');
        if (father == -1) {
            if (data.length >= 3) {
                alert("当前一级菜单已经达到最大限制，不能继续添加，您刚选择添加的一级菜单将会无效");
                return;
            } else {
                var appendData = new Array();
                var newData = {};
                newData["id"] = WechatMenu.currMax;
                WechatMenu.currMax += 1;
                newData["text"] = name;
                newData["menuName"] = name;
                newData["resourceId"] = resource;
                newData["url"] = url;
                newData["children"] = new Array();
                appendData.push(newData);

                $('#tree').tree('append', {
                    parent: null,
                    data: appendData
                });
                WechatMenu.initParentCombox();
            }
        } else {
            for (var i = 0; i < data.length; i++) {
                if (data[i].id == father) {
                    var children = data[i].children;
                    if (children.length >= 5) {
                        alert("当前菜单下的二级菜单已经达到最大限制，不能继续添加，您刚选择添加的菜单将会无效");
                        return;
                    } else {
                        var appendData = new Array();
                        var newData = {};
                        newData["id"] = WechatMenu.currMax;
                        WechatMenu.currMax += 1;
                        newData["text"] = name;
                        newData["menuName"] = name;
                        newData["url"] = url;
                        newData["resourceId"] = resource;
                        appendData.push(newData);
                        //$('#tree').tree('loadData', data);
                        $('#tree').tree('append', {
                            parent: data[i].target,
                            data: appendData
                        });
                        return;
                    }
                }
            }
        }
    },

    dealEdit: function () {
        var par = $('#tree').tree('getParent', WechatMenu.currNode.target);
        var isLeaf = $('#tree').tree('isLeaf', WechatMenu.currNode.target);
        var nodeId = WechatMenu.currNode.id;
        var data = $('#tree').tree('getRoots');
        var name = $("#menuName").val();
        var url = $("#exterminal-link").val();
        var resource = $("#resource").combobox('getValue');
        if (name == "") {
            //alert("名称不允许为空");
            return;
        }
        if (!isLeaf) {
            //一级菜单
            for (var i = 0; i < data.length; i++) {
                if (data[i].id == nodeId) {
                    data[i].menuName = name;
                    data[i].text = name;
                    if (data[i].children.length == 0) {
                        if (url != null) {
                            data[i].url = url;
                        }
                        if (resource != null) {
                            data[i].resourceId = resource;
                        }
                    }
                    $('#tree').tree('update', data[i]);
                    WechatMenu.initParentCombox();
                    return;
                }
            }
        } else {
            if (url == null && resource == null) {
                //alert("资源不允许为空");
                return;
            }

            var childData = $('#tree').tree('find', WechatMenu.currNode.id);
            childData.menuName = name;
            childData.text = name;
            childData.url = url;
            childData.resourceId = resource;
            //$('#tree').tree('loadData', data);
            $('#tree').tree('update', childData);
        }
        //$('#tree').tree('loadData', data);
    },

    submitMenu: function () {
        if (WechatMenu.checkInput()) {
            WechatMenu.dealMenu();
        }


        var accountId = $("#accountId").val();
        var data = $('#tree').tree('getRoots');

        var list = new Array();
        for (var i = 0; i < data.length; i++) {
            var parentData = data[i];
            var children = parentData.children;
            var param = {};
            param["menuName"] = parentData.text;
            if (children == null || children.length == 0) {

                if (parentData.url != null && "" != parentData.url){
                    param["url"] = parentData.url;
                } else {
                    var resourceId = parentData.resourceId;

                    param["resourceId"] = resourceId;
                }

                param["children"] = new Array();
            } else {
                var childList = new Array();
                for (var j = 0; j < children.length; j++) {
                    var childData = children[j];
                    var childParam = {};
                    childParam["menuName"] = childData.text;

                    if (childData.url != null && "" != childData.url){
                        childParam["url"] = childData.url;
                    } else {
                        var resourceId = childData.resourceId;
                        childParam["resourceId"] = resourceId;
                    }

                    childList.push(childParam);
                }
                param["children"] = childList;
            }
            list.push(param);
        }

        var json = JSON.stringify(list);

        $.post("/wechat/wechatMenu/saveMenu.jhtml",
            {
                accountId: accountId,
                data: json

            }, function (result) {
                $.messager.progress("close");
                if (result.success == true) {
                    show_msg("处理成功");

                    var url = "/wechat/wechatAccount/list.jhtml";
                    window.location.href = url;
                } else {
                    show_msg("处理失败");
                }
            }
        );

        //submit
    }
}


//$(function() {
//    WechatMenu.initTree();
//    WechatMenu.initCombox();
//});