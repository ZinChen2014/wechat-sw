$(function(){
	$(".next_shuibiao").click(function(){
		var now = $(this).parents("table").attr("indexlist");
		//alert(now);
	//	var temp = "list" + now;
		//alert(temp);
		
		//var obj = document.getElementsByClassName(temp)[0];
		//alert($("."+temp).attr("indexall"));
		if($(this).parents("table").attr("indexall")-1==now){
			$(".list0").show().siblings().hide();			
		}else{
			$(this).parents("table").next("table").show().siblings().hide();
		}
	});
});

function goForUrl(){
	var url = decodeURI(location.href);
	var index = url.indexOf("/usedWater/");
	var openId = getQueryStringValue(url, 'openId');
	
	var xhref = url.substring(0,index);
	window.location.href=xhref+"/usedWater/payWater?openId="+openId;
}