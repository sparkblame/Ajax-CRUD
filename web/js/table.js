var countPage;
var nowPag = 1;
var pageSize;
var countSize;
 
var starIndex;
var endIndex;
 
// 用户提交信息
var name;
var sex;
var age;
 
// 定义行号
var num = 1;
 
$(document).ready(function() {
	// 注册添加用户的事件
	$("#submit").click(function() {
		// 获取用户提交的信息
		name = $("#name").val();
		sex = $("input[name='sex']:checked").val();
		age = $("#age").val();
		pageSize = $("#selectSize option:selected").val();
		// alert(name+sex+age+pageSize);
 
		// 创建表格下的tr对象
		$tr = $("<tr class='data' ></tr>");
		$td1 = $("<td></td>");
		$td2 = $("<td></td>");
		$td3 = $("<td></td>");
		$td4 = $("<td></td>");
		$td5 = $("<td></td>");
 
		$tr.append($td1.append("<input type='checkbox'>"));
		$tr.append($td2.append(name));
		$tr.append($td3.append(sex));
		$tr.append($td4.append(age));
		$tr.append($td5.append("<input type='button' value='删除'>"));
 
		$("#show").append($tr);
		pageNation();
 
	});
	// 注册选择显示条数的操作
	$("#selectSize").change(function() {
		pageSize = $("#selectSize option:selected").val();
		pageNation();
	});
 
	// 注册分页操作的按钮点击事件
	$("#first").click(pageNation);
	$("#back").click(pageNation);
	$("#next").click(pageNation);
	$("#last").click(pageNation);
 
});
 
// 分页操作的函数
var pageNation = function() {
	// 获取所有的数据条数
	countSize = $("#show tr").size();
	// 获取总页数
	countPage = Math.ceil(countSize / pageSize);
 
	// 处理翻页的操作
	if (this.nodeType == 1) {
		var idValue = $(this).attr("id");
		if ("first" == idValue) {
			// alert(idValue);
			nowPag = 1;
		} else if ("back" == idValue) {
			// alert(nowPag);
			if (nowPag > 1) {
				nowPag--;
			}
 
		} else if ("next" == idValue) {
			// alert(idValue);
			if (nowPag < countPage) {
				nowPag++;
			}
		} else if ("last" == idValue) {
			// alert(idValue);
			nowPag = countPage;
		}
 
	}
	// alert(pageSize);
	// 获取显示开始和结束的下标
	starIndex = (nowPag - 1) * pageSize + 1;
	endIndex = nowPag * pageSize;
 
	if (endIndex > countSize) {
		// alert("下标大于总记录数"+endIndex);
		endIndex = countSize;
	}
 
	if (countSize < pageSize) {
		// alert("总记录数小小于每页的显示条数"+endIndex);
		endIndex = countSize;
	}
 
	// alert(starIndex);
 
	if (starIndex == endIndex) {
		// 显示的操作
		$("#show tr:eq(" + (starIndex - 1) + ")").show();
		$("#show tr:lt(" + (starIndex - 1) + ")").hide();
	} else {
		// 显示的操作
		$("#show tr:gt(" + (starIndex - 1) + ")").show();
		$("#show tr:lt(" + (endIndex - 1) + ")").show();
 
		// 隐藏的操作
		$("#show tr:lt(" + (starIndex - 1) + ")").hide();
		$("#show tr:gt(" + (endIndex - 1) + ")").hide();
	}
	// 改变底部显示操作
	$("#sizeInfo")
			.html(
					"当前从" + starIndex + "条到第" + endIndex + "条记录,共" + countSize
							+ "条记录.");
	$("#pageInfo").html("当前是第" + nowPag + "页,共" + countPage + "页.");
};