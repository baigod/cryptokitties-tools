<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>cryptokitties攻略</title>
<link rel="stylesheet" href="/static/css/bootstrap.min.css">
<link rel="stylesheet" href="/static/css/bootstrap-table.css">
<style type="text/css">
.badge-pill {
	padding-right: 0.6em;
	padding-left: 0.6em;
	border-radius: 10rem;
}

.badge-cattribute {
	font-size: 14px;
	font-weight: normal;
	line-height: 21px;
	margin: 0.25em 0.1em;
	text-decoration: none;
	padding: 0.25em 0.4em;
}

.badge-secondary {
	color: #333;
	background-color: #fff;
	padding: 0.05em 0.25em;
	margin: 0.15em 0.2em 0.15em 0.5em;
}

.badge-secondary:hover {
	background-color: #FF0000;
}

.badge {
	display: inline-block;
	font-size: 75%;
	font-weight: 700;
	line-height: 21px;
	text-align: center;
	white-space: nowrap;
	vertical-align: baseline;
	border-radius: 1.35rem;
}
</style>
</head>
<body>
	<h1>cryptokitties攻略</h1>
	<div id="toolbar"><div style="font-weight: bold;">For Sale(在售)面板猫数据</div></div>
	<table id="table" data-toolbar="#toolbar" data-height="200px" data-toggle="table" data-height="460" data-url="/data" data-sort-name="cattributes_sum" data-sort-order="desc"
		data-side-pagination="server" data-pagination="true" data-page-number="1" data-page-size="10" data-show-toggle="true" data-show-columns="true" data-click-to-select="true"
		data-page-list="[10, 25, 50, 100,500, All]">
		<thead>
			<tr>
				<th data-field="id" data-width="2%" data-valign="middle" data-align="center" data-formatter="FMT.kittyUrl">id</th>
				<th data-field="image_url" data-width="2%" data-valign="middle" data-align="center" data-formatter="FMT.kittyImg">图</th>
				<th data-field="kitty_name" data-width="2%" data-valign="middle" data-align="center" data-formatter="FMT.kittyUrl" data-cell-style="FMT.cellStyle">名</th>
				<th data-field="generation" data-width="2%" data-valign="middle" data-align="center" data-sortable="true" data-formatter="FMT.gen">代</th>
				<th data-field="current_price" data-width="2%" data-valign="middle" data-align="center" data-sortable="true" data-align="right">价</th>
				<th data-field="children_num" data-width="2%" data-valign="middle" data-align="center" data-sortable="true">孩子数量</th>
				<th data-field="cattributes_sum" data-width="2%" data-valign="middle" data-align="center" data-sortable="true">属性总分</th>
				<th data-field="cattributes_avg" data-width="2%" data-valign="middle" data-align="center" data-sortable="true">属性均分</th>
				<th data-field="cattributes" data-valign="middle" data-formatter="FMT.cattributes">属性</th>
			</tr>
		</thead>
	</table>


	<script type="text/javascript" src="/static/js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="/static/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/static/js/bootstrap-table.min.js"></script>
	<script type="text/javascript" src="/static/js/bootstrap-table-zh-CN.min.js"></script>
	<script type="text/javascript">
		function colorRGB2Hex(color) {
			var rgb = color.split(',');
			var r = parseInt(rgb[0]);
			var g = parseInt(rgb[1]);
			var b = parseInt(rgb[2]);
			var hex = "#"
					+ ((1 << 24) + (r << 16) + (g << 8) + b).toString(16)
							.slice(1);
			return hex;
		}

		var FMT = {
			kittyUrl : function(value, row, index) {
				return [ '<a href="https://www.cryptokitties.co/kitty/'+ row.id +'" target="_blank"><nobr>'
						+ value + '</nobr></a>' ].join('');
			},
			gen : function(value, row, index) {
				return [ '<a href="https://www.cryptokitties.co/marketplace?search=gen:'
						+ value + '" target="_blank">' + value + '</a>' ]
						.join('');
			},
			cattributes : function(value, row, index) {
				var array = eval("(" + row.cattributes_tags + ")");
				var attrs = "";
				for (var i = 0; i < array.length; i++) {
					var item = array[i];
					console.log(item);
					var bcolor = "background-color: "
							+ colorRGB2Hex(255 + "," + item.ranking * 3 + ","
									+ item.ranking * 3);
					attrs = attrs
							+ [ '<a class="badge badge-pill badge-cattribute" style="'+bcolor+'" href="http://cryptokittydex.com/cattributes/'
						+ item.attr + '" target="_blank">'
									+ item.attr
									+ '<span class="badge badge-pill badge-secondary">'
									+ item.num + '</span></a>' ].join('');
				}
				return attrs;
			},
			kittyImg : function(value, row, index) {
				return [ '<img alt="'+row.bio+'" title="'+row.bio+'" src="'+row.image_url+'" width="65px" height="65px">' ]
						.join('');
			},
			cellStyle : function(value, row, index, field) {
				return {
					css : {
						"text-overflow" : "ellipsis ",
						"white-space" : "nowrap",
						"overflow" : "hidden "
					}
				};
			}
		};
	</script>


	<footer>
		<div style="font-weight: bold;">Help:</div>
		<div>属性色值从深到浅，表示属性的稀有度排行</div>
		<div style="font-weight: bold;">Plans:</div>
		<div>1.0:完成基本属性的销售数据查询</div>
		<div>1.1:完成基本属性的求偶数据查询</div>
		<div>2.0:基因数据快速匹配</div>
		<div>3.0:基于销售和求偶的行情K线，对猫做估值计算</div>
		<div style="font-weight: bold;">Notes:</div>
		<div>如有需求、bug和建议，请加Q群469544103</div>
		<div>捐助开发者(ETH):0x23b96A20Fae711ED6D286feAEED437a6831e3dD7</div>
	</footer>
</body>
</html>