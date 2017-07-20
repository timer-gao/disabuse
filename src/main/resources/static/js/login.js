/**
 * Created by A on 2017/7/12.
 */
$(document).ready(function(){

    $(".unable-login").click(function(){
        $(".modal-wrapper").css("display","flex");
        $("#forgotform").css("display","block");
    })

    $(".close").click(function(){
        $("#forgotform").css("display","none");
        $(".modal-wrapper").css("display","none");
    })
});

 function checkvalue(txt){

     if(txt.value=="")
        alert("不能够为空");
 }

//注册校验
//用户名
var usernameRegex = /^\w{1,15}$/;
//邮箱
var emailRegex =/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
//密码
var passwordRegex = /^[a-z0-9_-]{6,18}$/;



function validateForm() {   //注册信息客户端校验
    var flag = true;
    //校验用户名
    var usernameNode = document.getElementById("fullname");  //获得用户名的节点对象
    var username = usernameNode.value;                       //获得节点的值，及输入框内的值
    if (!usernameRegex.test(username))                        //验证是否符合正则表达式
    {

        $(".error").css("visibility","visible");
        flag = false;
    }

    else if (usernameRegex.test(username))
    {

        $(".error").css("visibility","hidden");
        flag=true;
    }

    //校验邮箱
    var emailNode = document.getElementById("email");
    var email = emailNode.value;
    if (!emailRegex.test(email)) {

        $(".error").css("visibility","visible");
        flag = false;
    }

    else{
        alert("该邮箱合法");
    }

    //校验密码
    var passwordNode = document.getElementById("password");
    var password = passwordNode.value;
    if(!passwordRegex.test(password)){

        $(".error").css("visibility","visible");
        flag = false;
    }


    return flag;
}

$(document).ready(function(){
    $(".tab").click(function() {
        var X = $(this).attr("id");
        if (X == 'clicksignup') {
            $("#clicksignin").removeClass("active");
            $("#clicksignup").addClass("active");
            $("#signup").css("display", "block");
            $("#signin").css("display", "none");
        }
        else {
            $("#clicksignup").removeClass("active");
            $("#clicksignin").addClass("active");
            $("#signin").css("display", "block");
            $("#signup").css("display", "none");
        }
    });

});