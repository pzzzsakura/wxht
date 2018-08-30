$(function () {
  var wxManageId = getQueryString("wxManageId");
  // 获取此群组下的所有微信号
  var listUrl = '/wxht/wxmanage/getwxlist';
  // 删除该微信号
  var count;
  var statusUrl = '/wxht/wxmanage/removewx';
  getList();
  /**
   * 获取此群组下的微信号列表
   *
   * @returns
   */
  function getList() {
    // 从后台获取列表
    $.getJSON(listUrl+"?wxManageId="+wxManageId, function(data) {
      if (data.success) {
        count = data.count;
        var wxList = data.wxList;
        var tempHtml = '';
        // 遍历每条信息，拼接成一行显示，列信息包括：
        // 名称,所属群组, 删除(含wxId)，编辑按钮(含wxId)
        // 预览(含wxId)
        wxList.map(function(item, index) {
          // 拼接信息
          tempHtml += '' + '<div class="row row-wx">'
              + '<div class="col-50" align="center">'
              + item.wxName
              + '</div>'
              + '<div class="col-50" align="center">'
              + '<a href="#" class="preview" data-id="'
              + item.wxId
              + '" >预览</a>'
              +'<a href="#" class="edit" data-id="'
              + item.wxId
              + '" >编辑</a>'
              + '<a href="#" class="del" data-id="'
              + item.wxId
              + '">删除</a>'
              + '</div>'
              + '</div>';
        });
        // 将拼接好的信息赋值进html控件中
        $('.wx-wrap').html(tempHtml);
      }
    });
  }
  // 将class为wx-wrap里面的a标签绑定上点击的事件
  $('.wx-wrap').on(
      'click',
      'a',
      function(e) {
        var target = $(e.currentTarget);
        if (target.hasClass('edit')) {
          // 如果有class edit则点击就进入信息编辑页面，并带有wxId参数
          window.location.href = '/wxht/wxmanage/addwx?wxId='
              + e.currentTarget.dataset.id;
        } else if (target.hasClass('del')) {
          // 如果有class del 删除 并带有wxId参数
          deleteWX(e.currentTarget.dataset.id);
        } else if (target.hasClass('preview')) {
          // 如果有class preview则去前台展示系统该详情页预览微信号情况
          window.location.href = '/wxht/frontend/wxdetail?wxId='
              + e.currentTarget.dataset.id;
        }
      });
  function deleteWX(id) {
   var delUrl = "/wxht/wxmanage/removewx";
    $.confirm('确定么?', function() {
      $.ajax({
        url : delUrl,
        type : 'POST',
        data : {
         wxId:id
        },
        dataType : 'json',
        success : function(data) {
          if (data.success) {
            $.toast('操作成功！');
            getList();
          } else {
            $.toast('操作失败！');
          }
        }
      });
    });
  }
  $("#sub").click(function () {
    if(count>=10){
      alert("不能创建更多")
    }else{
      window.location.href="/wxht/wxmanage/addwx";
    }
  });


});