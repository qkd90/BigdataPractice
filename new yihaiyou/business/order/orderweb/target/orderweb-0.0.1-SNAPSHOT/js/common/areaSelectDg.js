/**
 * 城市选择面板
 * 使用方法：引入该js，
 * 调用入口：AreaSelectDg.open(countryId, provinceId, cityId, callbackFunc);
 *          AreaSelectDg.open(countryId, provinceId, cityId, callbackFunc);
 * 回调函数回传callbackFunc(countryId, provinceId, cityId, fullName)，没有值的对应为null
 */
var AreaSelectDg = {
    initFlag : false,   // 是否已初始化标识
    required : true,   // 是否必选
    countryId : null,
    provinceId : null,
    cityId : null,
    callbackFunc : null,    // 窗口关闭回调函数，不带参数
	// 页面内容初始化
	initComp : function() {
        if(AreaSelectDg.initFlag) {
            return;
        }
        AreaSelectDg.initFlag = true;
        // 动态加入标签内容
        var html = '<div id="frmAreaSelectDg" style="width:560px;height:320px;">'
            + '<div id="frmAreaSelectLayout" class="easyui-layout" data-options="fit:true">'
            + '   <div data-options="region:\'west\',border:false" style="width:33%;">'
            + '     <div id="frmCountryGridTb"><input id="frmCountryGridQry" style="width:130px;"/></div><table id="frmCountryGrid"></table>'
            + '   </div>'
            + '   <div data-options="region:\'center\',border:false">'
            + '     <div id="frmProvinceGridTb"><input id="frmProvinceGridQry" style="width:130px;"/></div><table id="frmProvinceGrid"></table>'
            + '   </div>'
            + '   <div data-options="region:\'east\',border:false" style="width:33%;">'
            + '     <div id="frmCityGridTb">'
            + '       <input id="frmCityGridQry" style="width:114px;"/>'
            + '       <a id="frmAreaSelectBtn" href="javascript:void(0)">选择</a>'
            + '     </div>'
            + '     <table id="frmCityGrid"></table>'
            + '   </div>'
            + '</div>'
            + '</div>';
            $('body').append(html);
        // 渲染组件
        // 窗口组件
        $("#frmAreaSelectDg").dialog({
            title : "城市选择",
            resizable:false,
            modal:true,
            closed:true,
            collapsible:false,
            shadow:false,
            onClose:function() {
                $('#frmCountryGridQry').searchbox('setValue', '');
                $('#frmProvinceGridQry').searchbox('setValue', '');
                $('#frmCityGridQry').searchbox('setValue', '');
            }
        });
        // 工具栏组件
        $('#frmCountryGridQry').searchbox({
            searcher:function(value,name){
                AreaSelectDg.searchCountry();
            },
            prompt:'请输入国家查询'
        });
        $('#frmProvinceGridQry').searchbox({
            searcher:function(value,name){
                var row = $("#frmCountryGrid").datagrid('getSelected');
                if (row) {
                    AreaSelectDg.searchProvince();
                } else {
                    show_msg("请先选择国家");
                }
            },
            prompt:'请输入省/区查询'
        });
        $('#frmCityGridQry').searchbox({
            searcher:function(value,name){
                var row = $("#frmProvinceGrid").datagrid('getSelected');
                if (row) {
                    AreaSelectDg.searchCity();
                } else {
                    show_msg("请先选择省/区");
                }
            },
            prompt:'请输入城市查询'
        });
        $('#frmAreaSelectBtn').linkbutton({
            //iconCls:'icon-ok',
            //plain:'false'
        });
        $('#frmAreaSelectBtn').click(function(){
            // 如果表格有数据，判断是否选中
            var countrySelected = $('#frmCountryGrid').datagrid('getSelected');
            var provinceSelected = $('#frmProvinceGrid').datagrid('getSelected');
            var citySelected = $('#frmCityGrid').datagrid('getSelected');
            if (AreaSelectDg.required) {
                var countryData = $('#frmCountryGrid').datagrid('getData');
                if (countryData && countryData.total > 0 && !countrySelected) {
                    show_msg("请先选择国家");
                    return;
                }
                var provinceData = $('#frmProvinceGrid').datagrid('getData');
                if (provinceData && provinceData.total > 0 && !provinceSelected) {
                    show_msg("请先选择省/区");
                    return;
                }
                var cityData = $('#frmCityGrid').datagrid('getData');
                if (cityData && cityData.total > 0 && !citySelected) {
                    show_msg("请先选择城市");
                    return;
                }
            }
            // 关闭窗口，执行回调函数
            $("#frmAreaSelectDg").dialog("close");
            if (AreaSelectDg.callbackFunc) {
                var funcArgs = [];
                var fullName = '';
                if (countrySelected) {
                    funcArgs.push(countrySelected.cityCode);
                    fullName += countrySelected.name;
                } else {
                    funcArgs.push(null);
                }
                if (provinceSelected) {
                    funcArgs.push(provinceSelected.cityCode);
                    fullName += '/' + provinceSelected.name;
                } else {
                    funcArgs.push(null);
                }
                if (citySelected) {
                    funcArgs.push(citySelected.cityCode);
                    fullName += '/' + citySelected.name;
                } else {
                    funcArgs.push(null);
                }
                funcArgs.push(fullName);
                AreaSelectDg.callbackFunc.apply(this, funcArgs);
            }
        });
        // 调整按钮样式
        $('#frmAreaSelectBtn .l-btn-text').css('line-height', '20px');
        // 表格组件
        $("#frmCountryGrid").datagrid({
            fit:true,
            //fitColumns:true,
            //url:'/line/line/search.jhtml',
            //data: [],
            singleSelect:true,
            border:false,
            striped:true,//斑马线
            pagination:false,
            idField: 'id',
            columns:[[
                {title:'国家名称', field:'name', width:170}
            ]],
            toolbar : '#frmCountryGridTb',
            onBeforeLoad : function(data) {
                data.name = $('#frmCountryGridQry').searchbox('getValue');
            },
            onSelect : function(rowIndex, rowData) {
                AreaSelectDg.searchProvince();
            },
            onLoadSuccess : function(data) {
                if (AreaSelectDg.countryId && data && data.total > 0) {
                    $("#frmCountryGrid").datagrid('selectRecord', AreaSelectDg.countryId);
                    // 只做一次选中
                    AreaSelectDg.countryId = null;
                }
            }
        });
        $("#frmProvinceGrid").datagrid({
            fit:true,
            //fitColumns:true,
            //url:'/line/line/search.jhtml',
            //data: [],
            singleSelect:true,
            border:true,
            striped:true,//斑马线
            pagination:false,
            idField: 'id',
            columns:[[
                {title:'省/区名称', field:'name', width:170}
            ]],
            toolbar : '#frmProvinceGridTb',
            onBeforeLoad : function(data) {
                var row = $("#frmCountryGrid").datagrid('getSelected');
                if (row) {
                    data.fatherId = row.id;
                }
                data.name = $('#frmProvinceGridQry').searchbox('getValue');
            },
            onSelect : function(rowIndex, rowData) {
                AreaSelectDg.searchCity();
            },
            onLoadSuccess : function(data) {
                if (AreaSelectDg.provinceId && data && data.total > 0) {
                    $("#frmProvinceGrid").datagrid('selectRecord', AreaSelectDg.provinceId);
                    // 只做一次选中
                    AreaSelectDg.provinceId = null;
                }
            }
        });
        $("#frmCityGrid").datagrid({
            fit:true,
            //fitColumns:true,
            //url:'/line/line/search.jhtml',
            //data: [],
            singleSelect:true,
            border:false,
            striped:true,//斑马线
            pagination:false,
            idField: 'id',
            columns:[[
                {title:'城市名称', field:'name', width:170}
            ]],
            toolbar : '#frmCityGridTb',
            onBeforeLoad : function(data) {
                var row = $("#frmProvinceGrid").datagrid('getSelected');
                if (row) {
                    data.fatherId = row.id;
                }
                data.name = $('#frmCityGridQry').searchbox('getValue');
            },
            onLoadSuccess : function(data) {
                if (AreaSelectDg.cityId && data && data.total > 0) {
                    $("#frmCityGrid").datagrid('selectRecord', AreaSelectDg.cityId);
                    // 只做一次选中
                    AreaSelectDg.cityId = null;
                }
            }
        });
        // 调整表格边框
        $('#frmAreaSelectLayout .panel-body').css({'border-top':'none', 'border-bottom':'none'});
        $('#frmAreaSelectLayout').layout();
        AreaSelectDg.initEvent();
    },
    // 绑定事件
    initEvent : function() {

    },
    initArea: function(target, areaid) {
        var url = "/sys/area/getAreaInfo.jhtml";
        $.post(url, {'cityId': areaid}, function(data) {
            if (data.success) {
                if (data.level == 0) {
                    target.textbox('setValue', data.countryName);
                    target.attr('data-country', data.countryId);
                    target.attr('data-province', '');
                    target.attr('data-city', '');
                } else if (data.level == 1) {
                    target.textbox('setValue', data.countryName + "/" + data.provinceName);
                    target.attr('data-country', data.countryId);
                    target.attr('data-province', data.provinceId);
                    target.attr('data-city', '');
                } else if (data.level == 2) {
                    target.textbox('setValue', data.countryName + "/" + data.provinceName + "/" + data.cityName);
                    target.attr('data-country', data.countryId);
                    target.attr('data-province', data.provinceId);
                    target.attr('data-city', data.cityId);
                }
            }
        });
    },
    // 查询国家
    searchCountry : function() {
        // 清除行选中
        $('#frmCountryGrid').datagrid('unselectAll');
        $('#frmProvinceGrid').datagrid('unselectAll');
        $('#frmCityGrid').datagrid('unselectAll');
        // 清除联动表格数据
        $('#frmProvinceGrid').datagrid('loadData', []);
        $('#frmCityGrid').datagrid('loadData', []);
        // 查询
        $('#frmCountryGrid').datagrid({url:'/sys/area/listAreaNew.jhtml'});
    },
    // 查询省/区
    searchProvince : function() {
        // 清除行选中
        $('#frmProvinceGrid').datagrid('unselectAll');
        $('#frmCityGrid').datagrid('unselectAll');
        // 清除联动表格数据
        $('#frmCityGrid').datagrid('loadData', []);
        // 查询
        $('#frmProvinceGrid').datagrid({url:'/sys/area/listAreaNew.jhtml'});
    },
    // 查询城市
    searchCity : function() {
        // 清除行选中
        $('#frmCityGrid').datagrid('unselectAll');
        // 查询
        $('#frmCityGrid').datagrid({url:'/sys/area/listAreaNew.jhtml'});
    },
    // 打开窗口（必选），参数：国家、省区、城市标识（默认选中）、回调函数
    open : function(countryId, provinceId, cityId, callbackFunc) {
        if (arguments.length > 0) { // 处理参数
            for (var i = 0; i < arguments.length; i++) {
                if (typeof arguments[i] == "function") {
                    AreaSelectDg.callbackFunc = arguments[i];
                } else if (i === 0) {
                    AreaSelectDg.countryId = arguments[i];
                } else if (i === 1) {
                    AreaSelectDg.provinceId = arguments[i];
                } else if (i === 2) {
                    AreaSelectDg.cityId = arguments[i];
                }
            }
        }
        AreaSelectDg.initComp();
        $("#frmAreaSelectDg").dialog("open");
        AreaSelectDg.searchCountry();
    },
    // 打开窗口（可选），参数：国家、省区、城市标识（默认选中）、回调函数
    openForQry : function(countryId, provinceId, cityId, callbackFunc) {
        AreaSelectDg.required = false;
        if (arguments.length > 0) { // 处理参数
            for (var i = 0; i < arguments.length; i++) {
                if (typeof arguments[i] == "function") {
                    AreaSelectDg.callbackFunc = arguments[i];
                } else if (i === 0) {
                    AreaSelectDg.countryId = arguments[i];
                } else if (i === 1) {
                    AreaSelectDg.provinceId = arguments[i];
                } else if (i === 2) {
                    AreaSelectDg.cityId = arguments[i];
                }
            }
        }
        AreaSelectDg.initComp();
        $("#frmAreaSelectDg").dialog("open");
        AreaSelectDg.searchCountry();
    }
};