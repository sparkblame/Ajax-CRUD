<%@ page language="java"  pageEncoding="utf-8"%>
    <!DOCTYPE html>
    <html>


    <head>
        <meta charset="utf-8">
        <title>出错啦</title>
        <link href="css/error.css" rel="stylesheet" />
        <script>
            var path = "${path}";
        </script>
        <script language="JavaScript" src="js/error.js"></script>
    </head>

    <body>

        <div id="errorDiv">
            <div id="errorHint">
                <p id="errorInfo">${info}</p>
                <p><span id="leaveTime">10</span>秒后自动返回到登录页面</p>
                <p>不能跳转，请<a href="${path}">点击这儿</a></p>
            </div>
        </div>
    </body>

    </html>