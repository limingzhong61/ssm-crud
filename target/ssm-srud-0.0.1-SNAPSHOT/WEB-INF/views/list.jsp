<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>employees list</title>
<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<!-- web路径：
	不以/开始的相对路径，找资源，以当前资源的路径为基准，经常容易出问题
	以/开始的开始的相对路径，找资源，以服务器为标准（http//localhost/端口号)需要加项目名;
	就是http//localhost/端口号/crud/...
	${APP_PATH}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css
  -->
<!-- Bootstrap -->
<link
	href="${APP_PATH}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<!-- 搭建页面 -->
	<div class="container">
		<!-- title -->
		<div class="row">
			<div class="col-md-12">
				<h1>SSM-CRUD</h1>
			</div>
		</div>
		<!-- bottom -->
		<div class="row">
			<div class="col-md-4 col-md-offset-8">
				<button class="btn btn-primary">add</button>
				<button class="btn btn-danger">delete</button>
			</div>
		</div>
		<!-- table of data -->
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover">
					<tr>
						<th>#</th>
						<th>empName</th>
						<th>gender</th>
						<th>email</th>
						<th>deptName</th>
						<th>operator</th>
					</tr>
					<c:forEach items="${pageInfo.list}" var="emp">
						<tr>
							<th>${emp.empId}</th>
							<th>${emp.empName}</th>
							<th>${emp.gender=="M"?"male":"female"}</th>
							<th>${emp.email}</th>
							<th>${emp.department.deptName}</th>
							<th>
								<button class="btn btn-primary btn-sm">
									<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
									编辑
								</button>
								<button class="btn btn-danger btn-sm">
									<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
									删除
								</button>
							</th>
						</tr>
					</c:forEach>

				</table>
			</div>
		</div>
		<!-- information of devide page -->
		<div class="row">
			<!-- 分页文字信息 -->
			<div class="col-md-6">
				<span>当前${pageInfo.pageNum}页，总${pageInfo.pages}页，共${pageInfo.total}条记录</span>
			</div>
			<!-- 分页条信息 -->
			<div class="col-md-6">
				<nav aria-label="Page navigation">
					<ul class="pagination">
						<li><a href="${APP_PATH}/emps?pageNumber=1">首页</a></li>
						<c:if test="${pageInfo.hasPreviousPage }">
							<li><a
								href="${APP_PATH}/emps?pageNumber=${pageInfo.pageNum-1}"
								aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
							</a></li>
						</c:if>
						<c:forEach items="${pageInfo.navigatepageNums}" var="pageNumber">
							<c:if test="${pageNumber == pageInfo.pageNum}">
								<li class="active"><a href="#">${pageNumber}</a></li>
							</c:if>
							<c:if test="${pageNumber != pageInfo.pageNum}">
								<li><a href="${APP_PATH}/emps?pageNumber=${pageNumber}">${pageNumber}</a></li>
							</c:if>
						</c:forEach>
						<c:if test="${pageInfo.hasNextPage }">
							<li><a
								href="${APP_PATH}/emps?pageNumber=${pageInfo.pageNum+1}"
								aria-label="Next"> <span aria-hidden="true">&raquo;</span>
							</a></li>
						</c:if>
						<li><a href="${APP_PATH}/emps?pageNumber=${pageInfo.pages}">末页</a></li>
					</ul>
				</nav>
			</div>
		</div>
	</div>





	<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
	<script src="${APP_PATH}/static/js/jquery-3.4.1.min.js"></script>
	<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
	<script
		src="${APP_PATH}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</body>
</html>