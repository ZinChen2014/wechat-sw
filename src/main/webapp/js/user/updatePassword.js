var waitTime = 60;
var pwdFlag1 = false;
var pwdFlag2 = false;
var pwdFlag3 = false;

$(function() {
	$("#submit").bind("click", function() {
		if (pwdOnblur()&&pwdOnblur2()&&pwd2Onblur()) {	
				
			var params = {};
			params.username = $("#user-name").val();
			params.password = $("#pwd0").val();
			params.newpwd = $("#pwd").val();
	    	$.ajax({
           	    // dataType : "json",
                 type : 'post',
                 data: params,
                 url : $("#_context").val()+"/user/updatePassword/json",
                 success : function(obj) {
                     if(true==obj.flag){
                    	 alert("修改成功,请重新登陆");
                    	 setTimeout("relogin()",2000);
                     }else{
                    	 alert(obj.msg);
                     }
                 }
	         });  
				
		} 
	});
	
	
});
function pwdOnblur(){
	var value =  $("#pwd0").val();
	if(""==value || null==value){
		alert("请输入密码");
		return false;
	}
	
	if(!expressionCommon.checkLdu(value)){
		alert("输入密码含有特殊字符");
		return false;
	}
	if(!expressionCommon.checkMaxLength(value,16)){
		alert("输入密码大于16位");
		return false;
	}
	if(!expressionCommon.checkMinLength(value,6)){
		alert("输入密码小于6位");
		return false;
	}
	pwdFlag2 =true;
	return true;
}
function pwdOnblur2(){
	var value = $("#pwd").val();
	var pwd = $("#pwd0").val();
	if(""==value || null==value){
		alert("请输入密码");
		return false;
	}
	
	if(value==pwd){
		alert("当前密码和新密码不能重复");
		return false;
	}
	if(!expressionCommon.checkLdu(value)){
		alert("输入密码含有特殊字符");
		return false;
	}
	if(!expressionCommon.checkMaxLength(value,16)){
		alert("输入密码大于16位");
		return false;		
	}
	if(!expressionCommon.checkMinLength(value,6)){
		alert("输入密码小于6位");
		return false;
	}
	pwdFlag3 =true;
	return true;
}
function pwd2Onblur(){
	var value =$("#pwd2").val();
	var pwd = $("#pwd").val();
	if(""==value || null==value){
		alert("请输入密码")
		return false;
	}
	if(value!=pwd){
		alert("2次输入密码不一样");
		return false;
	}
	if(!expressionCommon.checkLdu(value)){
		alerthtml("输入密码含有特殊字符");
		return false;
	}
	if(!expressionCommon.checkMaxLength(value,16)){
		alert("输入密码大于16位");
		return false;
	}
	if(!expressionCommon.checkMinLength(value,6)){
		alert("输入密码小于6位");
		return false;
	}
	pwdFlag1 =true;
	return true;
}
function relogin(){
	  window.location.href=$("#_context").val()+"/user/logout" ;
}

