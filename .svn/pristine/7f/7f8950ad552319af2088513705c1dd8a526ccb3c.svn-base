
$(function(){

});
function unbind(openId,userNo){
	if (typeof(openId) != "undefined"&&typeof(userNo) != "undefined"&&checkOpenID(openId)) {
		var url = decodeURI(location.href);
		var index = url.indexOf("/bindingUser");
		var href = url.substring(0,index);
		$.ajax({
             type : 'post',
             data: {
				  "openId":openId,
	   			  "userno":userNo
	   			 },
             url : href+"/bindingUser/unbindSingle/json",
             success : function(obj) {
                 if(true==obj.flag){
                	  alert("解绑成功");
                	/*  if (r==true)
                		  window.location.reload();
                	  else
                	    {
                		  window.location.reload();
                	    }*/
                 }else{
                	 alert("解绑失败");
                 }
             }
         });  
  }
}


function checkOpenID(openId){
	 if(null==openId || ""==openId) {
		 alert("无法获取openID,请刷新页面");
        return false;
	 }
	 return true;
}

