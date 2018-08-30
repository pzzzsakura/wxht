$(function () {
 var userInfoUrl = "/wxht/local/getuserinfo";
 $.getJSON(userInfoUrl,function (data) {
   if(data.success){
     if(data.user!=null&&data.user.userImg!=null){
       $("#user-img").attr("src",getContextPath()+data.user.userImg);
     }
    $("#user-name").text(data.user.userName);
    $("#change-phone").text("("+data.user.userPhone.substr(0,3)+"****"+data.user.userPhone.substr(7,4)+")");
   }else{
    $.toast(data.errMsg);
   }
 });



  // 点击后打开侧栏
  $('.icon-me').click(function() {
    $.openPanel('#panel-left-demo');
  });
  $('.close-panel').click(function() {
    $.closePanel('#panel-left-demo');
  });
  $.init();
});