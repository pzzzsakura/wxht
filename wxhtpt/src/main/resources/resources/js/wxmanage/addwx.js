$(function () {
  var wxId = getQueryString("wxId");
  var wxPostUrl = '/wxht/wxmanage/modifywx';
 // 通过wxManageId获取信息的URL
  var infoUrl = '/wxht/frontend/getwxdetail?wxId=' + wxId;
  if (wxId) {
    // 若有wxId则为编辑操作
    getInfo(wxId);
  } else {
    getWXManage();
    wxPostUrl = '/wxht/wxmanage/addwx';
  }
// 获取需要编辑的微信号信息，并赋值给表单
  function getInfo(wxId) {
    $.getJSON(
        infoUrl,
        function (data) {
          if (data.success) {
            // 从返回的JSON当中获取product对象的信息，并赋值给表单
            var wx = data.wx;
            $('#wx-name').val(wx.wxName);
            $('#wx-desc').val(wx.wxDesc);
            // 获取原本的群组列表
            var optionHtml = '<option data-id="'
                + data.wx.wxManage.wxManageId + '" selected="selected">'
                + data.wx.wxManage.wxManageName + '</option>'
            $('#wxManage').html(optionHtml);
            $('#wxManage').attr('disabled', 'disabled');
          }
        });
  }

  function getWXManage() {
  //获取当前微信号所有群组
    var wxmanageUrl = "/wxht/wxmanage/getwxgrouplist";
    $.getJSON(wxmanageUrl, function (data) {
      if (data.success) {
        var wxmanageList = data.wxManageList;
        var tempHtml = "";
        wxmanageList.map(function (item, index) {
          tempHtml += '<option data-value="' + item.wxManageId + '">'
              + item.wxManageName + '</option>';
        });
        $("#wxManage").html(tempHtml);
      }
    });
  }

  //新增上传控件
  $(".detail-img-div").on('change', '.detail-img:last-child', function () {
    if ($('.detail-img').length < 6) {
      $("#detail-img").append(
          '<div id="detail-img"><input type="file" class="detail-img"/></div>');
    }
  });

  $("#submit").click(function () {
    var formData = new FormData();
    var wx = {};
    wx.wxId = wxId;
    wx.wxName = $("#wx-name").val();
    wx.wxDesc = $("#wx-desc").val();
    if(isNull(wx.wxName)){
      alert("名称不能为空")
    }
    if(isNull(wx.wxDesc)){
      alert("描述不能为空")
    }
    wx.wxManage = {
      wxManageId: $("#wxManage").find("option").not(function () {
        return !this.selected;
      }).data("value")
    };
    //获取缩略图文件流
    var thumbnail = $("#small-img")[0].files[0];
    //遍历商品详情图获取文件流
    $(".detail-img").map(function (index, item) {
      if ($(".detail-img")[index].files.length > 0) {
        formData.append('wxImg' + index, $('.detail-img')[index].files[0]);
      }
    });

    var verifyCode = $('#j_captcha').val();
    if (!verifyCode) {
      $.toast('请输入验证码！');
      return;
    }

    formData.append("wxStr", JSON.stringify(wx));
    formData.append("verifyCode", verifyCode);
    formData.append("thumbnail", thumbnail);

    $.ajax({
      url: wxPostUrl,
      data: formData,
      dataType: 'json',
      contentType: false,
      processData: false,
      cache: false,
      type: 'post',
      success: function (data) {
        if (data.success) {
          $.toast("增加成功");
          window.location.href="/wxht/wxmanage/wxgrouplist";
        } else {
          $.toast("添加失败");
        }
        $("#captcha_img").click();
      }
    });

  });
})