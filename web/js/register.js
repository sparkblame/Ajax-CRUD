/**
 * ajax请求，填充省份下拉框
 */
function fillProvince() {
    $.ajax({
        type: "post",
        url: "queryProvinceCity.do",
        data: {},
        dataType: "json",
        success: function(response) {
            var provinceElement = document.getElementById("provinceCode");
            //清除select的所有option
            provinceElement.options.length = 0;
            //增加一个选项
            provinceElement.add(new Option("请选择省份", ""));
            //循环增加其他所有选项
            for (index = 0; index < response.length; index++) {
                provinceElement.add(new Option(response[index].provinceName,
                    response[index].provinceCode));
            }
        }
    });
}

/**
 * 省份下拉框选择发生改变事件：
 * 清空城市下拉框选项，增加默认提示项
 * 检查是否选择了省份，没有选择则给出错误提示并返回
 * 否则，清除错误提示信息，查询被选择省份对应的城市信息，增加到城市下拉框的选择列表中
 */
var fillCity = function(cityCode) {
    var provinceCode = $("#provinceCode").val();
    if (provinceCode == "") {
        $("#provinceError").css("color", " #c00202");
        $("#provinceError").text("必须选择省份！");
        return;
    }
    province_correct = true;
    $("#provinceError").text("");
    $("#cityCode").empty();
    $("#cityCode").append($("<option>").val("").text("请选择城市"));
    $.ajax({
        type: "post",
        url: "queryProvinceCity.do",
        data: { provinceCode: provinceCode },
        dataType: "json",
        success: function(response) {
            for (index = 0; index < response.length; index++) {
                var option = $("<option>").val(response[index].cityCode)
                    .text(response[index].cityName);
                $("#cityCode").append(option);
            }
            $("#cityCode").val(cityCode);
            if ($("#cityCode").val != "") {
                city_correct = true;
            }
        }
    });
}

//检查用户名
function checkUserName() {
    if ($('#userName').val() == "") {
        $("#userNameError").css("color", " #c00202");
        $("#userNameError").text("用户名不能为空");
        return;
    }
    if (/^[a-zA-Z][a-zA-Z\d]{3,14}$/.test(this.value) == false) {
        $("#userNameError").css("color", " #c00202");
        $("#userNameError").text("用户名只能使用英文字母和数字，以字母开头，长度为4到15个字符");
        return;
    }
    $.ajax({
        type: "post",
        url: "checkExist.do",
        data: { userName: $(this).val() },
        dataType: "json",
        success: function(response) {
            if (response.code == 0) {
                $("#userNameError").css("color", "green");
                $("#userNameError").text("用户名可以用来注册");
                userName_correct = true;
            } else {
                $("#userNameError").css("color", "#c00202");
                $("#userNameError").text("用户名已存在");
            }
        }
    });
}
//检查邮箱格式
function checkMailFormat() {
    $("#mailError").text("");
    if ($("#mail").val() == "") {
        $("#mailError").css("color", " #c00202");
        $("#mailError").text("邮箱不能为空");
        mail_correct = false;
        return false;
    }
    if (/^[a-zA-Z0-9]+([._\\]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/.test($("#mail").val()) == false) {
        $("#mailError").css("color", " #c00202");
        $("#mailError").text("邮箱格式不对");
        mail_correct = false;
        return false;
    }

    mail_correct = true;
    return true;
}

//检查邮箱是否重复
function checkMailCanUse() {
    $.ajax({
        type: "post",
        url: "checkExist.do",
        data: { mail: $("#mail").val() },
        dataType: "json",
        success: function(response) {
            if (response.code == 0) {
                $("#mailError").css("color", "green");
                $("#mailError").text("邮箱地址可以用来注册");
                mail_correct = true;
            } else {
                $("#mailError").css("color", "#c00202");
                $("#mailError").text("邮箱必须唯一");
                mail_correct = false;
            }
        }
    });
}

function checkMail() {
    if (checkMailFormat()) {
        checkMailCanUse();
    }
}

var userName_correct = false;
var chrName_correct = false;
var mail_correct = false;
var province_correct = false;
var city_correct = false;
var password_correct = false;
var password1_correct = false;

$(document).ready(function() {
    fillProvince(); //调用函数，填充省份下拉框
    $("#provinceCode").change(fillCity); //绑定省份下拉框变化事件

    //绑定省份下拉框离开事件
    $("#provinceCode").blur(function(e) {
        if ($(this).val() == "") {
            $("#provinceError").css("color", " #c00202");
            $("#provinceError").text("必须选择城市！");
        } else {
            $("#provinceError").text("");
            province_correct = true;
        }
    });

    /**
     * 绑定城市下拉框选择项变化事件：检查是否选择了城市
     */
    $("#cityCode").blur(function(e) {
        if ($("#cityCode").val() == "") {
            $("#cityError").css("color", " #c00202");
            $("#cityError").text("必须选择城市！");
        } else {
            $("#cityError").text("");
            city_correct = true;
        }
    });

    //绑定用户名输入框离开事件
    $('#userName').blur(checkUserName);

    /**
     * 真实姓名输入框离开事件
     * 使用正则表达式表达式检查输入是否符合要求（必须为中文，长度2-4）
     */
    $('#chrName').blur(function(event) {
        if ($(this).val() == "") {
            $("#chrNameError").css("color", " #c00202");
            $("#chrNameError").text("真实姓名不能为空");
            return;
        }
        if (/^[\u4e00-\u9fa5]{2,4}$/.test(this.value) == false) {
            $("#chrNameError").css("color", " #c00202");
            $("#chrNameError").text("真实姓名只能使用中文，长度为2到4个字符");
        } else {
            chrName_correct = true;
            $("#chrNameError").text("");
        }
    });
    /**
     * 邮箱地址输入框离开事件
     * (1)使用正则表达式表达式检查输入是否符合要求
     * (2)使用ajax检查邮箱地址是否已存在
     */
    $("#mail").blur(checkMail);

    //密码输入框离开事件：
    $("#password").blur(function() {
        var password_min_length = 3
        if ($("#password").val().length >= password_min_length) {
            $("#passwordError").css("color", "green");
            $("#passwordError").text("密码设置成功");
            password_correct = true;
        } else {
            $("#passwordError").css("color", "#c00202");
            $("#passwordError").text("密码长度至少为3");
        }
    });

    //确认密码离开事件
    $("#password1").blur(function() {
        var password_min_length = 3;
        if ($("#password").val() == $("#password1").val() && $("#password").val().length >= password_min_length) {
            $("#password1Error").css("color", "green");
            $("#password1Error").text("密码设置成功");
            password1_correct = true;
        } else {
            $("#password1Error").css("color", "#c00202");
            $("#password1Error").text("密码不一致或长度不够");
        }
    });

    /**
     * 注册按钮点击事件
     */
    $("#btLogin").click(function(e) {
        if (userName_correct & mail_correct && chrName_correct && province_correct && city_correct && password_correct && password1_correct) {
            $.ajax({
                type: "post",
                url: "register.do",
                data: $("#registerForm").serialize(), //将表单内容序列化成一个URL 编码字符串
                dataType: "json",
                success: function(response) {
                    alert(response.info);
                    if (response.code == 0) {
                        if ($("#action").val() != "") {
                            CloseDiv('MyDiv', 'fade');
                            reload();
                        } else {
                            window.location.href = "login.html";
                        }
                    }
                }
            });
        } else {
            $("#userName").blur();
            $('#chrName').blur();
            $("#mail").blur();
            $("#password").blur();
            $("#password1").blur();
            $("#provinceCode").blur();
            $("#cityCode").blur();
        }
    });
});