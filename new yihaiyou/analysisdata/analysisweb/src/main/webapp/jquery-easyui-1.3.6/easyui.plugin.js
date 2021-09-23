$.extend($.fn.datagrid.methods, {
    statistics: function (jq) {
        var opt=$(jq).datagrid('options').columns;
        var rows = $(jq).datagrid("getRows");
        var footers=$(jq).datagrid("getFooterRows");
         
        var footer = new Array();
        footer['sum'] = "";
        footer['avg'] = "";
        footer['max'] = "";
        footer['min'] = "";
         
        for(var i=0; i<opt[0].length; i++){
            if(opt[0][i].sum){
                footer['sum'] = footer['sum'] + sum(opt[0][i].field)+ ',';
            }
            if(opt[0][i].avg){
                footer['avg'] = footer['avg'] + avg(opt[0][i].field)+ ',';
            }
            if(opt[0][i].max){
                footer['max'] = footer['max'] + max(opt[0][i].field)+ ',';
            }
            if(opt[0][i].min){
                footer['min'] = footer['min'] + min(opt[0][i].field)+ ',';
            }
        }
 
        var footerObj = new Array();
         
        if(footer['sum'] != ""){
            var tmp = '{' + footer['sum'].substring(0,footer['sum'].length - 1) + "}";
            var obj = eval('(' + tmp + ')');
            if(obj[opt[0][0].field] == undefined){
                footer['sum'] += '"' + opt[0][0].field + '":"<b>当页合计:</b>"';
                obj = eval('({' + footer['sum'] + '})');
            }else{
                obj[opt[0][0].field] = "<b>当页合计:</b>" + obj[opt[0][0].field];
            }
            footerObj.push(obj);
        }
         
        if(footer['avg'] != ""){
            var tmp = '{' + footer['avg'].substring(0,footer['avg'].length - 1) + "}";
            var obj = eval('(' + tmp + ')');
            if(obj[opt[0][0].field] == undefined){
                footer['avg'] += '"' + opt[0][0].field + '":"<b>当页均值:</b>"';
                obj = eval('({' + footer['avg'] + '})');
            }else{
                obj[opt[0][0].field] = "<b>当页均值:</b>" + obj[opt[0][0].field];
            }
            footerObj.push(obj);
        }
         
        if(footer['max'] != ""){
            var tmp = '{' + footer['max'].substring(0,footer['max'].length - 1) + "}";
            var obj = eval('(' + tmp + ')');
             
            if(obj[opt[0][0].field] == undefined){
                footer['max'] += '"' + opt[0][0].field + '":"<b>当页最大值:</b>"';
                obj = eval('({' + footer['max'] + '})');
            }else{
                obj[opt[0][0].field] = "<b>当页最大值:</b>" + obj[opt[0][0].field];
            }
            footerObj.push(obj);
        }
         
        if(footer['min'] != ""){
            var tmp = '{' + footer['min'].substring(0,footer['min'].length - 1) + "}";
            var obj = eval('(' + tmp + ')');
             
            if(obj[opt[0][0].field] == undefined){
                footer['min'] += '"' + opt[0][0].field + '":"<b>当页最小值:</b>"';
                obj = eval('({' + footer['min'] + '})');
            }else{
                obj[opt[0][0].field] = "<b>当页最小值:</b>" + obj[opt[0][0].field];
            }
            footerObj.push(obj);
        }
         
         
         
        if(footerObj.length > 0){
        	//添加原footer数据
        	if(footers!=null&&footers.length>0){
        		for(var i=0;i<footers.length;i++){
        			footers[i].saleNo="<font style='color:red;font-weight:bold;'>总计</font>";
        			footers[i].billCode="<font style='color:red;font-weight:bold;'>总  计</font>";
            		footerObj.push(footers[i]);
            	}
        	}
            $(jq).datagrid('reloadFooter',footerObj); 
        }
         
         // 当页合计
        function sum(filed){
            var sumNum = 0;
            for(var i=0;i<rows.length;i++){
                sumNum += Number(rows[i][filed]);
            }
            return '"' + filed + '":"' + sumNum.toFixed(2) +'"';
        };
         
        // 平均值
        function avg(filed){
            var sumNum = 0;
            for(var i=0;i<rows.length;i++){
            	sumNum += Number(rows[i][filed]);
            }
            if(rows.length>0){
            	return '"' + filed+ '":"'+ (sumNum/rows.length).toFixed(2) +'"';
            }else{
            	return '"' + filed+ '":"'+ sumNum.toFixed(2) +'"';
            }
        }
 
        // 当页最大值
        function max(filed){
            var max = 0;
            for(var i=0;i<rows.length;i++){
                if(i==0){
                    max = Number(rows[i][filed]);
                }else{
                    max = Math.max(max,Number(rows[i][filed]));
                }
            }
            return '"' + filed + '":"'+ max.toFixed(2) +'"';
        }
         
        // 当页最小值
        function min(filed){
            var min = 0;
            for(var i=0;i<rows.length;i++){
                if(i==0){
                    min = Number(rows[i][filed]);
                }else{
                    min = Math.min(min,Number(rows[i][filed]));
                }
            }
            return '"' + filed + '":"'+ min.toFixed(2) +'"';
        }
    }
});