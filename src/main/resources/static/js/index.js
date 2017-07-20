/**
 * Created by Gaomj on 2017/7/13.
 */
var i=0;
$(document).ready(function () {
    $(window).scroll(function () {
        var scrollTop = $(window).scrollTop(), height = $(window).height(),
            documentHeight = $(document).height();
        if(scrollTop + height === documentHeight){
            //
            var html = "<iframe class=\"cardFrame\" src=\"questionListItem.html?number="+ i++ +"\" scrolling=\"no\"/>";
            $("body").append(html);
        }
    })
})
/*
var i=2;
$(window).scroll(function(){
    var scrollTop = $(this).scrollTop();
    var scrollHeight = $(document).height();
    var windowHeight = $(this).height();
    if(scrollTop + windowHeight == scrollHeight){
        alert("you are in the bottom");
        //insertIframe();
    }

});
//生成数据html,append到div中
function insertIframe() {
    var $mainDiv = $("body");
    var html = "<iframe class=\"cardFrame\" src=\"questionListItem.html?number="+ i++ +"\" scrolling=\"no\"/>";
    $mainDiv.append(html);
}*/
