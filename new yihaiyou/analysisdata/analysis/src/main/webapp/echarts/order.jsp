<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单日、周、月报表</title>
<!-- <script src="/echarts/doc/asset/js/esl/esl.js"></script> -->
<link rel="stylesheet" type="text/css" href="/jquery-easyui-1.3.6/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/jquery-easyui-1.3.6/themes/icon.css">

</head>

<body>

<p>
	开始:<input class="easyui-datebox" name="start" id="start"></input> 
	结束:<input class="easyui-datebox" name="end" id="end"></input>
	<a href="#" id="search" class="easyui-linkbutton"">查询</a>
</p>
<br/>
	<div id="main"
		style="width: 100%; height: 500px; border: 1px solid #ccc"></div>
	
</body>
<script type="text/javascript" src="/jquery-easyui-1.3.6/jquery.min.js"></script>
<script type="text/javascript" src="/jquery-easyui-1.3.6/jquery.easyui.min.js"></script>
<script src="/echarts/dist/echarts-all.js"></script>
<script type="text/javascript">
		$.fn.datebox.defaults.formatter = function(date){
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y + '-' + m + '-' + d;
		}
		
		$(function(){
			
			$('#search').click(function(){
				var start = $("#start").datebox('getValue');
				var end = $("#end").datebox('getValue');
				$.ajax({
					url: "some.php",
					data: { name: "John", location: "Boston" },
					success : function(){
						
					}
				});
			});
		});
		
			var nodes = [];
			var links = [];
			var constMaxDepth = 6;
			var constMaxChildren = 3;
			var constMinChildren = 2;
			var constMaxRadius = 10;
			var constMinRadius = 2;
			var mainDom = document.getElementById('main');

			function rangeRandom(min, max) {
			    return Math.random() * (max - min) + min;
			}

			function createRandomNode(depth) {
			    var x = mainDom.clientWidth / 2 + (.5 - Math.random()) * 200;
			    var y = (mainDom.clientHeight - 20) * depth / (constMaxDepth + 1) + 20;
			    var node = {
			        name : 'NODE_' + nodes.length,
			        value : rangeRandom(constMinRadius, constMaxRadius),
			        // Custom properties
			        id : nodes.length,
			        depth : depth,
			        initial : [x, y],
			        fixY : true,
			        category : depth === constMaxDepth ? 0 : 1,
			        onclick:function(params){
			        	alert(params);
			        }
			    }
			    nodes.push(node);

			    return node;
			}

			function forceMockThreeData() {
			    var depth = 0;

			    var rootNode = createRandomNode(0);
			    rootNode.name = 'ROOT';
			    rootNode.category = 2;

			    function mock(parentNode, depth) {
			        var nChildren = Math.round(rangeRandom(constMinChildren, constMaxChildren));
			        
			        for (var i = 0; i < nChildren; i++) {
			            var childNode = createRandomNode(depth);
			            links.push({
			                source : parentNode.id,
			                target : childNode.id,
			                weight : 1 
			            });
			            if (depth < constMaxDepth) {
			                mock(childNode, depth + 1);
			            }
			        }
			    }

			    mock(rootNode, 1);
			}

			forceMockThreeData();
			var a = 1;
			option = {
			    title : {
			        text: 'Force',
			        subtext: 'Force-directed tree',
			        x:'right',
			        y:'bottom'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: '{a} : {b}'
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            restore : {show: true},
			            magicType: {show: true, type: ['force', 'chord']},
			            saveAsImage : {show: true}
			        }
			    },
			    legend: {
			        x: 'left',
			        data:['叶子节点','非叶子节点', '根节点']
			    },
			    series : [
			        {
			            type:'force',
			            name : "Force tree",
			            ribbonType: false,
			            categories : [
			                {
			                    name: '叶子节点',
			                    itemStyle: {
			                        normal: {
			                            color : '#ff7f50'
			                        }
			                    }
			                },
			                {
			                    name: '非叶子节点',
			                    itemStyle: {
			                        normal: {
			                            color : '#6f57bc'
			                        }
			                    }
			                },
			                {
			                    name: '根节点',
			                    itemStyle: {
			                        normal: {
			                            color : '#af0000'
			                        }
			                    }
			                }
			            ],
			            itemStyle: {
			                normal: {
			                    label: {
			                        show: false
			                    },
			                    nodeStyle : {
			                        brushType : 'both',
			                        strokeColor : 'rgba(255,215,0,0.6)',
			                        lineWidth : 1
			                    }
			                }
			            },
			            minRadius : constMinRadius,
			            maxRadius : constMaxRadius,
			            nodes : nodes,
			            links : links
			        }
			    ]
			};

		var zr = echarts.init(document.getElementById('main'));
		zr.showLoading({
		    text: '正在努力的读取数据中...',    //loading话术
		});
		zr.hideLoading();
		var ecConfig = echarts.config;
		function eConsole(param) {
			
		    alert(param);
		}
		zr.on(ecConfig.EVENT.CLICK, eConsole);
		zr.setOption(option, true);
			
	</script>
</html>