var rePay_monery=0.00;
$(function(){
	$(".next_shuibiao").click(function(){
		var now = $(this).parents("table").attr("indexlist");
		if($(this).parents("table").attr("indexall")-1==now){
			$(".list0").show().siblings().hide();			
		}else{
			$(this).parents("table").next("table").show().siblings().hide();
		}
		
	});
});

function changefsubmit(obj){
  if(obj.is(':checked')) {
	  	obj.parents("table").find("#fsubmit").css('display','block');
	}else{
		obj.parents("table").find("#fsubmit").css('display','none');
	}
}

function checkRate(object){
	var money=object.val();
	//object.value=parseFloat(money);
	if(chkInputMonery(money)){
		object.val(parseFloat(money).toFixed(2));
		rePay_monery=money;
	}else{
		rePay_monery=0.00;
	}
}

function formSubmit(obj) {
	//var money = parseFloat(rePay_monery);
	var userId = obj.parents("table").find("._userNO").html().trim();
	
	if(chkInputMonery){
		var url = decodeURI(location.href);
		var index = url.indexOf("/usedWater/rePayWater/");

		openId = getQueryStringValue(url, 'openId');
		var tHref = url.substring(0,index);
		 window.location.href=tHref+"/pay/xyPay/json?userNo="+userId+"&totalfee="+rePay_monery+"&flag=B&openId="+openId;
	}
}

function chkInputMonery(money){
	if (money == null || $.trim(money) == "" || "0.00"==money || 0==money) {
		alert("输入金额");
		return false;
	}
	var re = /^([0-9]\d{0,5})(\.(\d)?(\d)?)?$/; 	
	if (!re.test(money)) {
		alert("请输入金额到分");		
		return false;
	}
	return true;
}




