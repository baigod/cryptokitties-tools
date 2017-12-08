<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<link rel="stylesheet" href="/static/css/bootstrap.min.css">
<link rel="stylesheet" href="/static/css/bootstrap-table.css">
</head>
<body>
	<h1>在售猫单</h1>

	<table id="virtual-table" data-url="/data" data-toggle="#table1" data-height="" data-toolbar="#virtual-toolbar" data-undefined-text="_" data-striped="true" data-method="post"
		data-data-type="json" data-response-handler="SERVICE.resHandler" data-show-refresh="true" data-pagination='true' data-side-pagination="server" data-page-number='1'
		data-page-size='20' data-show-toggle="true" data-show-columns="true" data-click-to-select="true" data-query-params="SERVICE.getParams">
		<thead>
			<tr>
				<th data-field="id" data-align="center">id</th>
				<th data-field="coinName" data-align="center">EnglishName</th>
				<th data-field="shortName" data-align="center">CurrencyAbbreviation</th>
				<th data-field="symbol" data-align="center">CurrencyUnit</th>
				<th data-field="autoCreateAddress" data-align="center">AutomaticallyAddress</th>
				<th data-field="currency" data-align="center">AddCurrency</th>
				<th data-field="gather" data-align="center">AddUp</th>
				<th data-field="createDateStr" data-align="center">TimeAdded</th>
				<th data-field="statusDesc" data-align="center">WalletStatus</th>
				<th data-field="" data-align="left" data-formatter="SERVICE.statusFormatter" data-events="operateEvents" data-width="180">actions</th>
			</tr>
		</thead>
	</table>

	<table id="table" data-toggle="table" data-height="460" data-url="/data" data-sort-name="name" data-sort-order="desc">
		<thead>
			<tr>
				<th data-field="id" data-sortable="true">Item ID</th>
				<th data-field="name" data-sortable="true">Item Name</th>
				<th data-field="price" data-sortable="true">Item Price</th>
			</tr>
		</thead>
	</table>



	<script type="text/javascript" src="/static/js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="/static/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/static/js/bootstrap-table.min.js"></script>
	<script type="text/javascript" src="/static/js/bootstrap-table-zh-CN.min.js"></script>
	<script type="text/javascript">
		
	</script>
</body>
</html>