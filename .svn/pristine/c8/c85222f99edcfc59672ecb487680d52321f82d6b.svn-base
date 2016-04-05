$(function() {
	
	
	$("#submit").bind("click", function() {
			var params = {};
			
			var flag = true;
			params.keyword1 = $("#keyword1").val();
			if(""==params.keyword1 || null==params.keyword1){
				flag = false;
			}
			params.keyword2 = $("#keyword2").val();
			if(""==params.keyword2 || null==params.keyword2){
				flag = false;
			}
			params.keyword3 = $("#keyword3").val();
			if(""==params.keyword3 || null==params.keyword3){
				flag = false;
			}
			params.remark = $("#remark").val();
			if(""==params.remark || null==params.remark){
				flag = false;
			}
			
			if(flag){
				$.ajax({
	           	    // dataType : "json",
	                 type : 'post',
	                 data: params,
	                 url : $("#_context").val()+"/shutOffWater/sendShutOffWaterInfo/json",
	                 success : function(obj) {
	                	 alert(obj.msg);
	                     if(true==obj.flag){
	                    	 window.location.href = $("#_context").val()+"/admin/getShutOffWaterInfoList";
	                     }
	                 }
		         });  
			}else{
				alert("请完善停水消息模板，再提交");
				return false;
			}
				
	});
	
});




