$(function(){
    //给type绑定点击事件
    $(".type").click(function () {
        var index = $(".type").index(this);
        var obj = $(".type").eq(index);
        $(".type").removeClass("checked");
        obj.addClass("checked");
    });
    //给color绑定点击事件
    $(".color").click(function () {
        var index = $(".color").index(this);
        var obj = $(".color").eq(index);
        $(".color").removeClass("checked");
        obj.addClass("checked");
    });
});

//商品数量++
function increase() {
    var quantity = $("#quantity").val();
    var stock = parseInt($("#stock").text());
    var productId=$("#productId").val();
    quantity++;
    if(quantity > stock){
        quantity = stock;
        return;
    }
    stock--;
    $.ajax({
        url:"/product/updateProduct/add/"+productId+"/"+stock,
        type:"POST",
        dataType:"text",
        success:function (data) {
            if (data=="success"){
                $("#quantity").val(quantity);
                $("#stock").text(stock);
            }
        }
    });
}

//商品数量--
function reduce() {
    var productId=$("#productId").val();
    var quantity = $("#quantity").val();
    var stock = parseInt($("#stock").text());
    quantity--;
    if(quantity == 0){
        quantity = 1;
        return;
    }
    stock++;
    $.ajax({
        url:"/product/updateProduct/sub/"+productId+"/"+stock,
        type:"POST",
        dataType:"text",
        success:function (data) {
           if (data=="success"){
               $("#quantity").val(quantity);
               $("#stock").text(stock);
           }
        }
    });
}

//添加购物车
function addCart(){
    var id = $("#productId").val();
    var price = $("#productPrice").val();
    var quantity = $("#quantity").val();
    window.location.href="/cart/add/"+id+"/"+price+"/"+quantity;
}