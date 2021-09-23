///////////////////////////////////快递单号生成规则//////////////////////////////
var ExpressType={};//快递公司类型
var expressNo;
ExpressType.MDZX='-1';//门店直销
ExpressType.YZ_EMS='2';//邮政EMS
ExpressType.EMS='42';//EMS快递
ExpressType.ZHAIJISONG='6';//宅急送
ExpressType.SHUNFENG='15';//顺丰速运
ExpressType.SHUNFENG_HDFK='88888';//顺丰货到付款
ExpressType.SHENTONGE='24';//申通
ExpressType.SHENTONGE_HDFK='77678';//申通
ExpressType.YUANTONG='3';//圆通

//向下关联单号填充
//expressId:快递公司 firstExpressNo:快递单号,index，第几行
function enterNextNo(expressId,index,length){
	var value = index;
	while (true){
		var newid=value+1;
		if (typeof($("#expressId" + value).val()) != 'undefined')
		{
			switch(expressId){
				case ExpressType.SHUNFENG://顺丰
					$("#expressId" + newid).attr('value',getSFNext($('#expressId' + value).val()));
					break;
				case ExpressType.SHUNFENG_HDFK://顺丰货到付款
					$("#expressId" + newid).attr('value',getSFNext($('#expressId' + value).val()));
					break;
				case ExpressType.EMS://EMS
					$("#expressId" + newid).attr('value',getEMSNext($('#expressId' + value).val()));
					break;
				case ExpressType.YZ_EMS://EMS
					$("#expressId" + newid).attr('value',getEMSNext($('#expressId' + value).val()));
					break;
				case ExpressType.ZHAIJISONG://宅急送
					$("#expressId" + newid).attr('value',getZJSNext($('#expressId' + value).val()));
					break;
				case ExpressType.SHENTONGE://申通
					$("#expressId" + newid).attr('value',getSTNext($('#expressId' + value).val()));
					break;
				case ExpressType.SHENTONGE_HDFK://申通
					$("#expressId" + newid).attr('value',getSTNext($('#expressId' + value).val()));
					break;
				case ExpressType.YUANTONG://申通
					$("#expressId" + newid).attr('value',getYTNext($('#expressId' + value).val()));
					break;
			}
			value++;
		}else {
			break;
		}
	}
	return ;
}

//获取顺丰下一个快递单号
function getSFNext(ShunFengNo){
	var fri,Nfri,Yuandanhao;
	var res;
	var num1,num2,num3,num4,num5,num6,num7,num8,num9,num10,num11,num12;
	var Nnum1,Nnum2,Nnum3,Nnum4,Nnum5,Nnum6,Nnum7,Nnum8,Nnum9,Nnum10,Nnum11,Nnum12;
	var mid,I,ShunFengres;

	ShunFengres = '';
	fri = ShunFengNo.substring(0,11);
	Yuandanhao = ShunFengNo;
	//先将前11位加1，存储为新前11位
	
	Nfri =   String(Math.abs(fri)+1);
	//获取原始前11位
	num1 = Math.abs(Yuandanhao.substr(0,1));
	num2 = Math.abs(Yuandanhao.substr(1,1));
	num3 = Math.abs(Yuandanhao.substr(2,1));
	num4 = Math.abs(Yuandanhao.substr(3,1));
	num5 = Math.abs(Yuandanhao.substr(4,1));
	num6 = Math.abs(Yuandanhao.substr(5,1));
	num7 = Math.abs(Yuandanhao.substr(6,1));
	num8 = Math.abs(Yuandanhao.substr(7,1));
	num9 = Math.abs(Yuandanhao.substr(8,1));
	num10 = Math.abs(Yuandanhao.substr(9,1));
	num11 = Math.abs(Yuandanhao.substr(10,1));
	num12 = Math.abs(Yuandanhao.substr(11,1));
	//获取新前11位
	Nnum1 = Math.abs(Nfri.substr(0,1));
	Nnum2 = Math.abs(Nfri.substr(1,1));
	Nnum3 = Math.abs(Nfri.substr(2,1));
	Nnum4 = Math.abs(Nfri.substr(3,1));
	Nnum5 = Math.abs(Nfri.substr(4,1));
	Nnum6 = Math.abs(Nfri.substr(5,1));
	Nnum7 = Math.abs(Nfri.substr(6,1));
	Nnum8 = Math.abs(Nfri.substr(7,1));
	Nnum9 = Math.abs(Nfri.substr(8,1));
	Nnum10 = Math.abs(Nfri.substr(9,1));
	Nnum11 = Math.abs(Nfri.substr(10,1));
	if ( Nnum9-num9==1 && (num9)%(2)==1 ){
		if (num12-8 >= 0)
			Nnum12 = num12-8             // -8
		else
			Nnum12 = num12-8 +10
	}else if( Nnum9-num9==1 && (num9)%(2)==0 ){
		if (num12-7 >=0)
			Nnum12 = num12-7             // -7
		else
			Nnum12 = num12-7 +10
	}else{
		if( ((num10==3) || (num10==6))&& (num11==9) ){
			if (num12-5 >=0)
				Nnum12 = num12-5             // -5
			else
				Nnum12 = num12-5 +10
		}else if (num11==9){
			if (num12-4 >=0)
				Nnum12 = num12-4             // -4
			else
				Nnum12 = num12-4 +10
		}else{
			if (num12-1 >=0)
				Nnum12 = num12-1            // -1
			else
				Nnum12 = num12-1 +10
		}
	}
	ShunFengres = Nfri+String(Nnum12);
	return ShunFengres;
}


//获取EMS下一位快递单号
function getEMSNext(no){
	var serialNo = no.substr(2,8);
	if (serialNo < 99999999)
		serialNo++;
	strSerialNo = pad(serialNo, 8);
	temp = no.substr(0,2) + strSerialNo + no.substr(10,1);
	temp = no.substr(0,2) + strSerialNo + getEMSLastNum(temp) + no.substr(11,2);
	return temp;
}

function pad(num, n) {
	return Array(Math.abs((''+num).length-(n+1))).join(0)+num;
}

function getEMSLastNum(no)
{
	var v = new Number(no.substr(2,1)) * 8;
	v += new Number(no.substr(3,1)) * 6;
	v += new Number(no.substr(4,1)) * 4;
	v += new Number(no.substr(5,1)) * 2;
	v += new Number(no.substr(6,1)) * 3;
	v += new Number(no.substr(7,1)) * 5;
	v += new Number(no.substr(8,1)) * 9;
	v += new Number(no.substr(9,1)) * 7;
	v = 11 - v % 11;
	if (v == 10)
		v = 0;
	else if (v == 11)
		v = 5;
	return v.toString();
} 


//获取申通下一位快递单
function getSTNext(no){
	var  temp=new Number(no)+1;
	return temp;
}

//获取圆通下一位快递单
function getYTNext(no){
	var  temp=new Number(no)+1;
	return temp;
}
