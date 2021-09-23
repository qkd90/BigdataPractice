////////////////////通用的检查组件
/// 使用步骤
//  1.在input定义以下属性
//     data-pattern：正则
//     data-type：数据类型
//     data-min：最小值
//     data-max：最大值
//     data-allowEmpty：是否允许为空
//     data-focus：是否获取焦点
//     data-paste：是否允许复制
//     data-restore:是否允许恢复到上一个数值
//     data-tip:提示值
//  2.在input添加  onfucus=Check.validInput(this)
//
var Check=$.extend({
	 /*
     ** 验证输入是否空值
     ** @val:要验证的字符串
     */
    isNull: function (val) {
        return (val.replace(/\s/g, "") === "");
    },
	validInput:function(obj){
		 // 特殊按键不做拦截操作
        var escapeChar = [8, 9, 12, 13, 16, 17, 18, 20, 27, 45, 46, 47];

        // 检测是否特殊字符
        var isEscapeChar = function (code) {
            if (escapeChar.indexOf(code) > -1) {
                return true;
            }
            if (code >= 112 && code <= 126) {
                return true;
            }
            if (code >= 33 && code <= 40) {
                return true;
            }
            return false;
        };
		var obj=$(obj);
		var oldvalue = obj.val();
		// 正则模式
        var pattern = obj.attr("data-pattern") || null;
        // 数据类型float或者int
        var type = obj.attr("data-type") || null;
        // 允许的最小值
        var min = obj.attr("data-min") || null;
        // 允许的最大值
        var max = obj.attr("data-max") || null;
        // 是否允许为空
        var allowEmpty = obj.attr("data-allowEmpty") === "true" ? true : false;
        // 验证失败是否自动获取焦点
        var focus = obj.attr("data-focus") === "true" ? true : false;
        // 是否关闭粘贴功能
        var closepaste = obj.attr("data-paste") === "no" ? true : false;
        // 验证失败是否自动恢复到上一个正确的值(或者defaultVal)
        var restore = obj.attr("data-restore") === "true" ? true : false;
        // 输入错误提示
        var tip = obj.attr("data-tip") || null;
       
        //正则表达
        if (pattern) {
            pattern = eval(pattern);
        }
       
        // 阻止粘贴
        if (closepaste) {
            obj.bind("paste", function () {
                return false;
            });
        }
        // 检查
		var check=function(val){
			var result=true;
			if(allowEmpty && Check.isNull(val)){
				result=true;
				return result;
			}else{
				  if (pattern && !pattern.test(val)) {
                      result = false;
                      return result;
                  }

				  if (type === "float" || type === "int") {
			            val = parseFloat(val);
			            if (isNaN(val)) {
			                result = false;
			                return result;
			            }
			        }
			        
			        if (min && val < min) {
			            result = false;
			            return result;
			        }

			        if (max && val > max) {
			            result = false;
			            return result;
			        }
			        return result;
			}
		};
		//绑定事件
		obj.bind("keyup",function(e){
			var val=obj.val();
			result=check(val);//检查
			if (!result) {
				if (restore) {
                    if (typeof defaultVal !== "undefined") {
                        obj.val(defaultVal);
                    } else {
                        obj.val(oldvalue);
                    }
                }
				if (focus) {
                    obj.focus().select();
                }
				 if (tip) {
                     $.messager.show({
                    	 title:"温馨提示",
                    	 msg:tip
                     });
                 }
			}else {
                oldvalue = val;
            }
		});
	}
});