<%@ page language="java"  pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="utf-8" />
            <title>系统主页</title>
            <link href="css/main.css?t=2" rel="stylesheet" type="text/css" />
        </head>

        <body>
            <div id="container">
                <!--引入的是静态的jsp文件,它将引入的jsp中的源代码原封不动地附加到当前文件中 -->
                <%@include file="head.jsp"%>
                    <div class="pageBody">
                        <p>作业四：基于AJAX的CRUD</p>
                        <p>要求：</p>
                        （1）组合查询，模糊查询，查询结果分页，可选择每页大小，排序字段，排序方式（升序/降序）<br/> （2）查询结果表格奇偶行不同样式，移动高亮，选择高亮 （）可删除当前行，批量删除选中行 （4）
                        <br/> （3）用户注册
                        <p>知识点</p>
                        （1）JS<br/>（2）jQuery<br/>（3）AJAX<br/> （4）JSON<br/>
                        <br/>
                    </div>
            </div>
        </body>

        </html>