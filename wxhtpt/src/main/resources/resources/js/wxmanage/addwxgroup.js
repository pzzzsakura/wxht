$(function () {
  var wxManageId = getQueryString("wxManageId");
  var isEdit = false;
  var getWXManageCategoryUrl = "/wxht/wxmanage/getwxmanagecategorylist";
  if(wxManageId){
    isEdit =true;
    edit();
  }else {
    $.getJSON(getWXManageCategoryUrl, function (data) {
      if (data.success) {
        var wmc = data.wxManageCategoryList;
        var tempHtml = "";
        wmc.map(function (item, index) {
          tempHtml += '<option data-id="' + item.wmcId + '">'
              + item.wmcName + '</option>';
        });
        $('#group-category').html(tempHtml);
      } else {
        $.toast(data.errMsg);
      }
    });
  }
function edit() {
    var getGroupUrl = "/wxht/frontend/getwxmanagedetail?wxManageId="+wxManageId;
    $.getJSON(getGroupUrl,function (data) {
      if(data.success){
        $("#group-name").val(data.wxManage.wxManageName);
        $("#group-desc").val(data.wxManage.wxManageDesc);
        var groupCategory = '<option data-id="'
            + data.wxManage.wxManageCategory.wmcId + '" selected="selected">'
            + data.wxManage.wxManageCategory.wmcName + '</option>'
        $('#group-category').html(groupCategory);
        $('#group-category').attr('disabled', 'disabled');
      }else{
        $.toast(data.errMsg);
      }
    });

}

  $("#submit").click(function () {
    var wxManageName = $("#group-name").val();
    var wxManageDesc = $("#group-desc").val();
    var wxManageImg = $("#group-img")[0].files[0];
    var wmcId = $('#group-category').find('option').not(function () {
        return !this.selected;
      }).data('id');
    var verifyCode = $("#j_captcha").val();
    var editUrl="/wxht/wxmanage/editwxmanage";
    var addUrl = "/wxht/wxmanage/addwxmanage";
    if(isNull(wxManageName)){
      alert("名称不能为空")
    }
    if(isNull(wxManageDesc)){
      alert("简介不能为空")
    }
    if(isNull(verifyCode)){
      alert("验证码不能为空")
    }
    var formData = new FormData();
    formData.append("wxManageName",wxManageName);
    formData.append("wxManageDesc",wxManageDesc);
    formData.append("wxManageImg",wxManageImg);
    formData.append("wmcId",wmcId);
    formData.append("verifyCode",verifyCode);
    formData.append("wxManageId",wxManageId);
    $.ajax({
      url: (isEdit ? editUrl : addUrl),
      type: 'POST',
      data:formData,
      contentType:false,
      processData: false,
      cache: false,
      success: function (data) {
        if (data.success) {
          $.toast('提交成功！');
          window.location.href="/wxht/wxmanage/wxgrouplist"
        } else {
          $.toast('提交失败！' + data.errMsg);
        }
        $("#captcha_img").click();
      }
    });
  });

});