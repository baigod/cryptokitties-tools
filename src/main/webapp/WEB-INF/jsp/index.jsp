<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head lang="en">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>Cryptokitties Fans</title>
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
	<div class="container-fluid">
		<ul class="nav nav-tabs">
			<li role="presentation" class="active"><a href="/">For Sale</a></li>
		</ul>
		<div id="toolbar"></div>
		<table data-search="true" data-search-text="" data-search-on-enter-key="true" data-show-refresh="true" data-id-field="id" class="table" id="table" data-toolbar="#toolbar"
			data-height="200px" data-toggle="table" data-height="460" data-url="/data" data-sort-name="cattributes_sum" data-sort-order="desc" data-side-pagination="server"
			data-pagination="true" data-page-number="1" data-page-size="10" data-show-toggle="true" data-show-columns="true" data-click-to-select="true"
			data-page-list="[10, 25, 50, 100,500, All]">
			<thead>
				<tr>
					<th data-field="id" data-width="2%" data-valign="middle" data-align="center" data-formatter="FMT.kittyUrl">id</th>
					<th data-field="image_url" data-width="2%" data-valign="middle" data-align="center" data-formatter="FMT.kittyImg">Photo</th>
					<th data-field="kitty_name" data-width="150px" data-valign="middle" data-align="center" data-formatter="FMT.kittyUrl" data-cell-style="FMT.nameStyle">Name</th>
					<th data-field="generation" data-width="2%" data-valign="middle" data-align="center" data-sortable="true" data-formatter="FMT.gen">Gen</th>
					<th data-field="current_price" data-width="2%" data-valign="middle" data-align="center" data-sortable="true" data-align="right">Price</th>
					<th data-field="children_num" data-width="2%" data-valign="middle" data-align="center" data-sortable="true">Children num</th>
					<th data-field="cattributes_sum" data-width="2%" data-valign="middle" data-align="center" data-sortable="true">Attr total score</th>
					<th data-field="cattributes_avg" data-width="2%" data-valign="middle" data-align="center" data-sortable="true">Attr avg score</th>
					<th data-field="status_cooldown_index" data-width="2%" data-valign="middle" data-align="center" data-sortable="true">Pregnant CD</th>
					<th data-field="cattributes" data-valign="middle" data-formatter="FMT.cattributes">Attrs</th>
				</tr>
			</thead>
		</table>
	</div>
	<blockquote>
		<footer>
			<div style="font-weight: bold;">Help:</div>
			<samp>Attribute color values from deep to shallow to represent the rarity of attributes</samp>
			<div style="font-weight: bold;">Notes:</div>
			<div>
				If you have needs, bug and suggestions, please call: <a href="https://github.com/15174834/cryptokitties-tools/issues" target="_blank">GitHub</a>
			</div>
			<div>
				If you need VPN, you can donate developer ETH: <mark style="cursor: pointer;">0x23b96A20Fae711ED6D286feAEED437a6831e3dD7</mark>:), bring your mail address in TRANSACTION DATA. And I'll send you the <a href="/files/Shadowsocks.exe" target="_blank">Shadowsocks</a> connection after receiving the currency..<br>
			</div>
			<div>
				Friendship Links: <a href="http://www.kittyexplorer.com/" target="_blank">Kitty Explorer</a>
			</div>
		</footer>
	</blockquote>
	<script type="text/javascript" src="/static/js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="/static/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/static/js/bootstrap-table.min.js"></script>
	<script type="text/javascript" src="/static/js/locale/bootstrap-table-en-US.min.js"></script>
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
				return [ '<a href="https://www.cryptokitties.co/kitty/'+ row.id +'" target="_blank">'
						+ value + '</a>' ].join('');
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
							+ [ '<a class="badge badge-pill badge-cattribute" style="'
									+ bcolor
									+ '" href="https://www.cryptokitties.co/marketplace/sale?search='
									+ item.attr
									+ '" target="_blank">'
									+ item.attr
									+ '<span class="badge badge-pill badge-secondary">'
									+ item.prop + '</span></a>' ].join('');
				}
				return attrs;
			},
			kittyImg : function(value, row, index) {
				return [ '<img title="'+row.bio+'" src="'+row.image_url+'" width="65px" height="65px">' ]
						.join('');
			},
			nameStyle : function(value, row, index, field) {
				return {
					css : {
						"text-overflow" : "ellipsis ",
						// 						"white-space" : "nowrap",
						"overflow" : "hidden "
					}
				};
			}
		};
	</script>

	<!-- Global site tag (gtag.js) - Google Analytics -->
	<script async src="https://www.googletagmanager.com/gtag/js?id=UA-111039393-1"></script>
	<script>
		window.dataLayer = window.dataLayer || [];
		function gtag() {
			dataLayer.push(arguments);
		}
		gtag('js', new Date());

		gtag('config', 'UA-111039393-1');
	</script>



</body>
</html>