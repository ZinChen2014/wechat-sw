var href ="";
var openId ="";
$(function() {
	$("#fsubmit").click(function() {submit()})
	var url = decodeURI(location.href);
	var index = url.indexOf("/pages/user/");
	if(index>-1){
		var data = getQueryStringValue(url, 'data');
		openId = $.parseJSON(data).openId;
	}else{
		index = url.indexOf("/bindingUser/unbindUser");
		openId = getQueryStringValue(url, 'openId');
	}
	href = url.substring(0,index);
})
function submit(){
	//var openId = "oa5uZuEm53BTuVGJhdYWlwFITMqg";
	  if(check_login()&&checkOpenID(openId)){
		  var userno = $.trim($('#userno').val());
		  var mobileno = $.trim($('#mobileno').val());
    	  var d ={
    			 "openId":openId,
       			  "userno":userno
       			 // "mobileno":mobileno
       			 }
    	  $.ajax({
                 type : 'post',
                 data: d,
                 url : href+"/bindingUser/isExstBind/json",
                 success : function(obj) {
                     if(obj.flag==1){
                    	// alert(obj.msg);
                   	  window.location.href=href+"/bindingUser/toBind/json?openId="
                   	  +openId+"&userno="+userno+"&mobileno="+mobileno ;
                     }else if(obj.flag==2){
                    	 alert(obj.msg);
                    	 WeixinJSBridge.call('closeWindow');
                     }
                     else{
                    	 $('#err').html("<br/>"+obj.msg);
                         $('#userno').focus();
                     }
                 }
             });  
      }
  }
function checkOpenID(openId){
	
	 if(null==openId || ""==openId) {
        $('#err').html("<br />无法获取openID,请刷新页面");
        $('#userno').focus();
        return false;
	 }
	 return true;
}
 function check_login() {
    var userno = $('#userno').val();
    var mobileno = $('#mobileno').val();
    
    if (userno == '') {
        $('#err').html("<br />请输入用水户号");
        $('#userno').focus();
        return false;
    }
   // /^\d{8}$/
    var regex = /^[0-9]\d*$/;
    if (!regex.test(userno)) {
        $('#err').html("<br />请输入数字组成的户号！");
        $('#userno').focus();
        return false;
    }
    if (mobileno == '') {
        $('#err').html("<br />请输入手机号码");
        $('#mobileno').focus();
        return false;
    }
    var regex = /^\d{11}$/;
    if (!regex.test(mobileno)) {
        $('#err').html("<br />请输入11位手机号码！");
        $('#mobileno').focus();
        return false;
    }
     $('#err').html("");
     return true;
}
