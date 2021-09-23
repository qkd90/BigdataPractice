<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="height:400px"></div>
    <script src="/echarts/doc/asset/js/jquery.js"></script>
    <!-- ECharts单文件引入 -->
    <script src="/echarts/dist/echarts.js"></script>
    <script type="text/javascript">
        // 路径配置
        require.config({
            paths: {
                echarts: '/echarts/dist'
            }
        });
        
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                'echarts/chart/scatter',
                'echarts/chart/line'
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('main')); 
                var option = {
                	    tooltip : {
                	        trigger: 'axis'
                	    },
                	    legend: {
                	        data:['销售量']
                	    },
                	    toolbox: {
                	        show : true,
                	        feature : {
                	            mark : {show: true},
                	            dataZoom : {show: true},
                	            dataView : {show: true},
                	            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                	            restore : {show: true},
                	            saveAsImage : {show: true}
                	        }
                	    },
                	    calculable : true,
                	    dataZoom : {
                	        show : true,
                	        realtime : true,
                	        start : 0,
                	        end : 100
                	    },
                	    xAxis : [
                	        {
                	            type : 'category',
                	            boundaryGap : false,
                	            data : [<s:property value="monthList"/>]
                	        }
                	    ],
                	    yAxis : [
                	        {
                	            type : 'value'
                	        }
                	    ],
                	    series : [
                	        {
                	            name:'销售量',
                	            type:'bar',
                	            data:[<s:property value="totalAmount"/>]
                	        }
                	    ]
                	};
                // 为echarts对象加载数据 
                myChart.setOption(option); 
                myChart.hideLoading();
               // getChartData(myChart);
            }
        );
    </script>
    <div>
        	<form id="sendform" method="post">
        					  <select name="nowYear">
        					  	<option value="2013">2013</option>
        					  	<option value="2014">2014</option>
        					  	<option value="2015" selected="selected">2015</option>
        					  </select>
        		产品名：<input type="text" name="proName" >
        					  <input type="submit" value=" 查看销售情况图表">
        	</form>
    </div>
    
    <script type="text/javascript">
  //提交表单
		$('#sendform').form('submit', {
			url : "/order/order/orderECharts.jhtml",
			onSubmit : function() {
				if($(this).form('validate')==false){
					$.messager.progress('close');
				}
				return $(this).form('validate');
			},
			success : function(result) {
				$.messager.progress("close");
				var result = eval('(' + result + ')');
				if(result.success==true){
					alert("保存成功!");
				}else{
					alert("保存成功111!");
				}
			}
		});
    </script>
</body>
</html>