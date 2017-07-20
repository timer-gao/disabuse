/*<![CDATA[*/
$(document).ready(function(){
    $("#head-img").mouseenter(function(){
        // alert("move");
        $(".take-photo").css("opacity", "1");
    })
});
$(document).ready(function(){
    $("#head-img").mouseout(function(){
        // alert("move");
        $(".take-photo").css("opacity", "0");
    })
});
$(document).ready(function(){
    $("#edit-info").click(function(){
        // 重定向
        var redirect = "/people/" + $("#nickname").text();
        redirect = redirect + "/edit-info";
        window.location.assign(redirect);
    })
});

$(document).ready(function(){
    var text = $(".text").text();
    text = $.trim(text);
    var changeText = "";
    if (text.length > 135) {
        changeText = changeText + text.substring(0, 130);
        changeText += "...";
    }
    $(".text").text(changeText);
});
$(document).ready(function(){
    var txt = $("#tag-name").text();
    if(txt == "我的关注"){
        $("#focus").addClass("active");
    }else if(txt == "我的收藏"){
        $("#collection").addClass("active");
    }else if(txt == "我的提问"){
        $("#question").addClass("active");
    }else if(txt == "我的回答"){
        $("#answer").addClass("active");
    }
});
//点击关注或取消关注.
$(document).ready(function () {
    $("#focus-people").click(function () {
        var type, change;
        if($(this).text() == "关注"){
            type = "focus";
            change = "已关注";
        }else{
            type = "unfocus";
            change = "关注";
        }
        var nickName = $("#nickname").text();
        var data = "type=" + type + "&output=" + nickName;
        var url = '/people/' + nickName;
        $.ajax({
            type: 'POST',
            url: url,
            data: data,
            success: function (data) {
                if(data.success == "OK"){
                    $("#focus-people").text(change);
                    window.location.reload(true);
                }

                else
                    alert("操作失败");
            },
            error:function () {
                alert("sorry");
            }
        });
    });
});
//点击取消关注之类.
$(document).ready(function () {
    $(".like-button").click(function () {
        var id = $(this).parent().parent().attr("name");
        var name = $(this).parent().parent().attr("id");
//                    alert(id);
        var url = window.location.href;
        var elem = $(this).parent().parent();
//                    alert(url);
        $.ajax({
            type:'POST',
            url: url,
            data: "type=unlike&value=" + id + "&name=" + name,
            success: function (data) {
                if(data.success == "OK"){
                    window.location.reload(true);
                }

                else
                    alert("取消失败");
            },
            error:function () {
                alert("sorry, didn't link");
            }
        });
    });
});
//点击上传.
$(document).ready(function () {
    $("#file").change(function () {
        if ($(this).val() != '') {
            var url;
            var file = this.files[0];
            if (window.createObjectURL!=undefined)
            { // basic
                url = window.createObjectURL(file) ;
            }
            else if (window.URL!=undefined)
            {
                // mozilla(firefox)
                url = window.URL.createObjectURL(file) ;
            }
            else if (window.webkitURL!=undefined) {
                // webkit or chrome
                url = window.webkitURL.createObjectURL(file) ;
            }
            alert(url);
            $("#post-img").submit();
            /*var txt = $(this).val().split('.')[1];
            var url = "/avatar/" + $("#nickname").text() + '.' + txt;*/
            $(".user-img").attr("src", url);
        }else{
            alert("没有文件");
        }
    });
});
/*]]>*/