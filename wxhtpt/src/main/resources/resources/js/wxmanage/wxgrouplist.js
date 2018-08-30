$(function () {
var getGroupListUrl="/wxht/wxmanage/getwxgrouplist";
var count;
getList();
function getList() {
  $.getJSON(getGroupListUrl, function (data) {
    if (data.success) {
      var html = "";
      count = data.count;
      data.wxManageList.map(function (item, index) {
        html += '<div class="row row-group"><div class="col-25" align="center"><a href="/wxht/wxmanage/addwxgroup?wxManageId='
            + item.wxManageId + '">' + item.wxManageName
            + '</a></div><div class="col-25" align="center">'
            + item.wxManageCategory.wmcName
            + '</div><div class="col-25" align="center">'
            + Status(item.enableStatus)
            + '</div><div class="col-25" align="center"><a class="del" data-id="'+item.wxManageId+'">删除&nbsp;</a>' + go(item.enableStatus,
                item.wxManageId) + '</div></div>';
      })
      $(".group-wrap").html(html);
    } else {
      $.toast(data.errMsg);
    }
  })
}
$("#new").click(function () {
  if(count>=99){
    alert("不能创建更多")
  }else{
    window.location.href="/wxht/wxmanage/addwxgroup";
  }
});
  $(".group-wrap").on('click','a',function (e) {
    var target = $(e.currentTarget);
    if(target.hasClass('del')){
      del(e.currentTarget.dataset.id);
    }
    })

function Status(data){
  if(data==0){
    return "审核中";
  }else if(data==-1){
    return "店铺非法";
  }else{
    return "审核通过";
  }
}
  function del(id) {
    var delUrl = "/wxht/wxmanage/removewxmanage";
    $.confirm('确定么?', function() {
      $.ajax({
        url : delUrl,
        type : 'POST',
        data : {
          wxManageId:id
        },
        dataType : 'json',
        success : function(data) {
          if (data.success) {
            $.toast('操作成功！');
            getList();
          } else {
            $.toast('操作失败！'+data.errMsg);
          }
        }
      });
    });
  }

function go(enableStatus,wxManageId) {
  if(enableStatus==1){
    return '<a href="/wxht/wxmanage/wxlist?wxManageId='+wxManageId+'">进入</a>';
  }else{
    return '';
  }
}


});