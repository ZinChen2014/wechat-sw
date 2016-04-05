var openId ="";
var tHref ="";
$(function(){
	$(".next_shuibiao").click(function(){
		var now = $(this).parents("table").attr("indexlist");
		if($(this).parents("table").attr("indexall")-1==now){
			$(".list0").show().siblings().hide();			
		}else{
			$(this).parents("table").next("table").show().siblings().hide();
		}
	});
	var url = decodeURI(location.href);
	var index = url.indexOf("/usedWater/payWater/");

	openId = getQueryStringValue(url, 'openId');
	tHref = url.substring(0,index);
});

function callpay(userId,total_fee,realNumber,feeIDs){
	  window.location.href=tHref+"/pay/xyPay/json?&userNo="+userId+"&totalfee="+total_fee
   	  +"&flag=A&realNumber="+realNumber+"&feeIDs="+feeIDs+"&openId="+openId;
}