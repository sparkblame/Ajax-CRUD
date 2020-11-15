<%@ page language="java"  pageEncoding="utf-8"%>
    <link href="css/head.css?t=1" rel="stylesheet" type="text/css" />
    <div id="header">
        <div id="rightTop">
            <c:if test="${empty currentUser}">
                <a href="login.html">登录</a> &nbsp;<a href="register.html">注册</a>
            </c:if>
            <c:if test="${!empty currentUser}">
                当前用户：<span>${currentUser.chrName}</span> &nbsp;[<a href="logout.do">安全退出</a>]
            </c:if>
        </div>
        <div id="menu">
            <ul>
                <li><a href="main.jsp">首页</a></li>
                <li class="menuDiv"></li>
                <li><a href="queryDownload.do">资源下载</a></li>
                <li class="menuDiv"></li>
                <li><a href="userManage.jsp">用户管理</a></li>
                <li class="menuDiv"></li>
                <li><a href="downloadManage.jsp">资源管理</a></li>
                <li class="menuDiv"></li>
                <li><a href="user.jsp">个人中心</a></li>
            </ul>
        </div>
    </div>
    <div id="banner">
    </div>