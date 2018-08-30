$(function () {
  var userInfoUrl = "/wxht/local/getuserinfo";
  $.getJSON(userInfoUrl,function (data) {
    if(data.success){
      $("#username").val(data.user.userPhone);
      var sex;
      if(data.user.sex==1){
        var tempHtml = '<option data-id="1" selected="selected">男</option>'
            + '<option data-id="0">女</option>';
      }else{
        var tempHtml = '<option data-id="0" selected="selected">女</option>'
            + '<option data-id="1">男</option>';
      }
      $("#sex").html(tempHtml);
      $("#user").attr("readOnly",true);
    }else{
      $.toast(data.errMsg);
    }
  });
var modifyUserInfo = "/wxht/local/modifyuserinfo";
  $('#submit').click(function() {
    // 获取帐号
    var username = $('#username').val();
    // 获取原密码
    var sex = $('#sex').find("option").not(function () {
      return !this.selected;
    }).data('id');
    // 获取新密码
    var userImg = $('#user-img')[0].files[0];
    // 将参数post到后台去修改密码
    if(isNull(username)){
      alert("用户名不能为空")
    }
    $.ajax({
      url : url,
      type : 'POST',
      data : {
        username:username,
        sex:sex,
        userImg:userImg
      },
      contentType : false,
      processData : false,
      cache : false,
      success : function(data) {
        if (data.success) {
          $.toast('提交成功！');
          window.location.href = '/wxht/wxmanage/wxmanage';
        }else {
          $.toast('提交失败！' + data.errMsg);
        }
      }
    });
  });

});