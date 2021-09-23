/**
 * jQuery :  城市联动插件
 * ccy
 */
$.fn.ProvinceCity = function(options){
	
	//初始化
	var _self = this;
	_self.provinceName = options.provinceName;
	_self.cityName = options.cityName;
	_self.countyName = options.countyName;
	_self.provinceCode = options.provinceCode;
	_self.cityCode = options.cityCode;
	_self.countyCode = options.countyCode;
	_self.callback = typeof options.callback == "undefined"?function(){}:options.callback;
	_self.getFullName = function(){
		return $sel1.val()+$sel2.val()+$sel3.val();
	}
	
	//插入3个空的下拉框
	_self.append('<select style="width:100px;margin-right:5px;" name="'+_self.provinceName+'" id="prov"></select>');
	_self.append('<select style="width:100px;margin-right:5px;" name="'+_self.cityName+'" id="city"><option value="-1">请选择</option></select>');
	_self.append('<select style="width:100px;" name="'+_self.countyName+'" id="area"><option value="-1">请选择</option></select>');

	//分别获取3个下拉框
	var $sel1 = _self.find("select").eq(0);
	var $sel2 = _self.find("select").eq(1);
	var $sel3 = _self.find("select").eq(2);
	
	var str = '<option value="-1">请选择</option>' ; 
	$(provinces).each(function(){
		str += "<option value='"+this.code+"'>"+this.name+"</option>" ; 
	});

	$sel1.html(str);
	
	$sel1.change(function(){
		if("-1"===$sel1.val()){
			$sel2.html('<option value="-1">请选择</option>');
			$sel3.html('<option value="-1">请选择</option>');
			return false ; 
		}
		$(provinces).each(function(){
			if(this.code===$sel1.val()){
				$sel3.html('<option value="-1">请选择</option>');
				var citystr = '<option value="-1">请选择</option>' ; 
				var regions = this.regions ; 
				$(regions).each(function(){
					citystr += "<option value='"+this.code+"'>"+this.name+"</option>" ; 
				});
				$sel2.html(citystr);
			}
		});
	
	})
	
	$sel2.change(function(){
		if("-1"===$sel2.val()){
			$sel3.html('<option value="-1">请选择</option>');
			return false ; 
		}
		$(provinces).each(function(){
			if(this.code===$sel1.val()){
				var regions = this.regions ; 
				$(regions).each(function(){
					if(this.code===$sel2.val()){ 
						var countstr = '<option value="-1">请选择</option>' ;
						var regions = this.regions ; 
						$(regions).each(function(){
							countstr += "<option value='"+this.code+"'>"+this.name+"</option>" ; 
						});
						$sel3.html(countstr);
					}
					
				});
			}
		});
	})
	
	$sel3.change(function(){
		_self.callback();
	});
	

	
	return _self ; 
};


//初始化省市县下拉菜单
function initRegionUnit(provinceCode,cityCode,countyCode){
	
	$("#provincesCity select option[value='"+provinceCode+"']").attr("selected","selected");
	$("#provincesCity select").eq(0).change(); 
	$("#provincesCity select option[value='"+cityCode+"']").attr("selected","selected");
	$("#provincesCity select").eq(1).change(); 
	$("#provincesCity select option[value='"+countyCode+"']").attr("selected","selected");
	$("#provincesCity select").eq(2).change(); 
	
}