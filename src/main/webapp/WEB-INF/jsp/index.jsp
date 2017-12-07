<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <%@ include file="./common/base.jsp" %>
    <link type="text/css" href="${COINX_CDN}common/plugin/dialog/css/artdialog.css" rel="stylesheet"/>
    <link type="text/css" rel="stylesheet" href="${COINX_CDN}cac/css/cac.css">
    <style type="text/css">
    	.autoHigth{height: auto;}
        .title-icon.i1{background-size:25px 20px;height: inherit;line-height: inherit;background-position: left top;margin-bottom: 0px;min-height: 20px;overflow: hidden;}
        .total{margin-bottom: 10px;}
        .bind-info{margin-top: 6px;}
    </style>
</head>
<body>
<jsp:include page="./common/header.jsp" >
	<jsp:param value="cac" name="nav"/>
</jsp:include>

	
<div class="tipslayer success" style="visibility: hidden"></div>

<div class="layout-content layout-center">
    <div class="account-moudle clearfix">
        <div class="content" style="float:left;width: 590px">
            <div class="account-bind mrg20 fleft">
                <h2 class="title-icon i1">
                    <div class="total">
                        <c:choose>
                            <c:when test="${dto.code=='USD'}">
                                <span><s:message code='view.content.EstimatedTotalAsset' />: </span><span class="link">${dto.aboutTotal} ${dto.code}</span>
                            </c:when>
                            <c:otherwise>
                                <span><s:message code='view.content.EstimatedTotalAsset' />: </span><span class="link">${dto.aboutTotal}${dto.code}</span>
                            </c:otherwise>
                        </c:choose>

                    </div>

                    <span class="checkbox-1">
                        <input class="magic-checkbox" type="checkbox" name="layout" id="notbalance" >
                        <label for="notbalance"><s:message code='view.content.HideCurrency' /></label>
                    </span>
                </h2>
                <ul class="bind-info listcurrency">
                    <li class="t-header">
                        <span><s:message code='view.content.Name' /></span>
                        <span><s:message code='view.content.usable' /></span>
                        <span><s:message code='view.content.Frozen' /></span>
                        <span><s:message code='view.content.actions' /></span>
                    </li>
                    <c:forEach var="v" items="${dto.assets }">
                        <c:if test="${v.typeCode != 'CNY'}">
                            <li>
                                <span>${v.typeCode }</span>
                                <span>${v.cashAmount}</span>
                                <span class="c999">${v.unCashAmount}</span>
                                <c:choose>
                                    <c:when test="${v.typeCode == 'USD'|| v.typeCode == 'THB' }">
                                        <c:choose>
                                            <c:when test="${kycLevel==0}">
                                                <span><a class="link isKyc" href="javascript:;"><s:message code='view.content.link2' /></a></span>
                                            </c:when>
                                            <c:when test="${kycLevel==1}">
                                                <span><a class="link authstrKyc" href="javascript:;"><s:message code='view.content.link2' /></a></span>
                                            </c:when>
                                            <c:when test="${kycLevel==2}">
                                                <span><a class="link rejectKyc" href="javascript:;"><s:message code='view.content.link2' /></a></span>
                                            </c:when>
                                            <c:otherwise>
                                                <c:choose>
                                                    <c:when test="${not empty v.userBank}">
                                                        <c:choose>
                                                            <c:when test="${v.bankStatus=='1'}">
                                                                <span><a class="link authstr" href="JavaScript:;"><s:message code='view.content.deposit' /></a></span>
                                                            </c:when>
                                                            <c:when test="${v.bankStatus=='2'}">
                                                                <span><a class="link" href="/legal/show-sys-bank?coinId=${v.typeId}&isAddress=1&coinCode=${v.typeCode}"><s:message code='view.content.deposit' /></a></span>
                                                            </c:when>
                                                            <c:when test="${v.bankStatus=='3'}">
                                                                <span><a class="link  AuditFailure" data-coinid="${v.typeId}" data-coinCode="${v.typeCode}"  data-bankreason="${v.bankReason}"  href="JavaScript:;"><s:message code='view.content.deposit' /></a></span>
                                                            </c:when>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span><a class="link" href="${UCENTER_WEB}/user/to-bind-card-page?coinid=${v.typeId}&coinCode=${v.typeCode}"><s:message code='view.content.link2' /></a></span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${noticeActive || false }">
                                                <span><a class="link" id="isKyc" href="javascript:;"><s:message code='view.content.ChargingCoin' /></a></span>
                                            </c:when>
                                            <c:otherwise>
                                                <c:choose>
                                                    <c:when test="${v.addressFlag}">
                                                        <span><a class="link" href="/virtual/address/in/apply?coinId=${v.typeId}&isAddress=1&coinCode=${v.typeCode}"> <s:message code='view.content.ChargingCoin' /></a></span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span><a class="link" href="/virtual/address/in/apply?coinId=${v.typeId}&isAddress=2&coinCode=${v.typeCode}"><s:message code='view.content.ChargingCoin' /></a></span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </li>
                        </c:if>
                    </c:forEach>
                </ul>
            </div>

            <div class="account-bind fleft">
                <h2 class="title-icon i4"><s:message code='view.content.AssetDistribution' /></h2>
                <div style="font-size: 18px;color: #51A1FC;text-align: center;">
                    <c:choose>
                        <c:when test="${dto.code}==USD">
                            <s:message code='view.content.TotalAsset' />  ≈ ${dto.aboutTotal } ${dto.code}
                        </c:when>
                        <c:otherwise>
                            <s:message code='view.content.TotalAsset' />  ≈ ${dto.aboutTotal }${dto.code}
                        </c:otherwise>
                    </c:choose>

                </div>
                <div class="charts" id="charts-1" style="height: 400px;margin:0 auto;"> </div>
            </div>
    </div>
        <div class="account-bind fright">
            <h2 class="title-icon i2"><s:message code='view.content.MyBankAccount' /></h2>
            <c:forEach var="v" items="${dto.userBank }">
	            <div class="stack mtb0 clearfix">
	            	<span class="fleft">${v.code} <s:message code='view.content.Account' /></span>
                    <c:choose>
                        <c:when test="${kycLevel==0}">
                            <span class="fright"><a href="javaScript:;" class="link isKyc" target="_self"><s:message code='view.content.link2' /></a></span>
                        </c:when>
                        <c:when test="${kycLevel==1}">
                            <span class="fright"><a href="javaScript:;" class="link authstrKyc" target="_self"><s:message code='view.content.link2' /></a></span>
                        </c:when>
                        <c:when test="${kycLevel==2}">
                            <span class="fright"><a href="javaScript:;" class="link rejectKyc" target="_self"><s:message code='view.content.link2' /></a></span>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${v.bankStatus ==0}">
                                    <span class="fright"><a href="${UCENTER_WEB}/user/to-bind-card-page?coinid=${v.coinId }&coinCode=${v.code}" class="link" target="_self"><s:message code='view.content.link2' /></a></span>
                                </c:when>
                                <c:when test="${v.bankStatus ==1}">
                                    <span class="fright"><a class="link PendingReview" style="color:#3d3d3d;" ><s:message code='view.content.PendingReview'/></a></span>
                                </c:when>
                                <c:when test="${v.bankStatus ==2}">
                                    <span class="fright"><a  href="/legal/show-balance?coinId=${v.coinId}" class="link" target="_self"><s:message code='view.content.Withdrawal' /></a></span>
                                </c:when>
                                <c:when test="${v.bankStatus ==3}">
                                    <span class="fright" ><a class="link AuditFailure" data-coinid="${v.coinId}" data-coinCode="${v.code}" data-bankreason="${v.bankReason}"><s:message code='view.content.AuditFailure'/></a></span>
                                </c:when>
                                <c:otherwise>
                                    <span class="fright"><a href="${UCENTER_WEB}/user/to-bind-card-page?coinid=${v.coinId }&coinCode=${v.code}" class="link" target="_self"><s:message code='view.content.link2' /></a></span>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
	            </div>
            </c:forEach>
        </div>
        <div class="account-bind fright" style="min-height: 329px;">
            <h2 class="title-icon i3"><s:message code='view.content.Coinaddressmanagement' /></h2>
            <div class="outAddressWrap">
            	<p style="height: 150px; text-align: center; line-height: 100px;"><s:message code='view.content.addAddressText' /></p>
            </div>
            <a class="submitBtn mg-auto mgt20" id="newaddress"  href="javascript:;"><s:message code='view.content.AddAddress' /></a>
        </div>
    </div>
</div>
	

<%@ include file="./common/footer.jsp" %>
</body>
<script type="text/html" id="notBalanceTemplate">
{{each data as v}}
{{if v.typeCode == 'USD' || v.typeCode == 'THB'}}
<li>
    <span>{{v.typeCode }}</span>
    <span>{{v.cashAmount}}</span>
    <span class="c999">{{v.unCashAmount}}</span>
    {{if ${kycLevel}==0}}
        <span><a class="link isKyc" href="javascript:;"><s:message code='view.content.link2' /></a></span>
    {{else if ${kycLevel}==1}}
         <span><a class="link authstrKyc" href="javascript:;"><s:message code='view.content.link2' /></a></span>
    {{else if ${kycLevel}==2}}
        <span><a class="link rejectKyc" href="javascript:;"><s:message code='view.content.link2' /></a></span>
    {{else}}
        {{if v.noticeActive}}
            <span><a class="link" href="${UCENTER_WEB}"><s:message code='view.content.link2' /></a></span>
        {{else}}
            {{if v.userBank}}
                {{if v.bankStatus=='1'}}
                     <span><a class="link authstr" href="JavaScript:;"><s:message code='view.content.deposit' /></a></span>
                {{else if v.bankStatus=='2'}}
                    <span><a class="link" href="/legal/show-sys-bank?coinId={{v.typeId}}&isAddress=1&coinCode={{v.typeCode}}"><s:message code='view.content.deposit' /></a></span>
                {{else if v.bankStatus=='3'}}
    <span><a class="link  AuditFailure" data-coinid="{{v.typeId}}" data-coinCode="{{v.typeCode}}"  data-bankreason="{{v.bankReason}}"  href="JavaScript:;"><s:message code='view.content.deposit' /></a></span>
                {{/if}}

            {{else}}
                <span><a class="link" href="${UCENTER_WEB}/user/to-bind-card-page?coinid={{v.typeId}}&coinCode={{v.typeCode}}"><s:message code='view.content.link2' /></a></span>
            {{/if}}
        {{/if}}
    {{/if}}
</li>
{{else}}
<li>
    <span>{{v.typeCode }}</span>
    <span>{{v.cashAmount}}</span>
    <span class="c999">{{v.unCashAmount}}</span>
    {{if v.noticeActive}}
         <span><a class="link" href="${UCENTER_WEB}"><s:message code='view.content.ChargingCoin' /></a></span>
    {{else}}
        {{if v.addressFlag}}
            <span><a class="link" href="/virtual/address/in/apply?coinId={{v.typeId}}&isAddress=1&coinCode={{v.typeCode}}"><s:message code='view.content.ChargingCoin' /></a></span>
        {{else}}
            <span><a class="link" href="/virtual/address/in/apply?coinId={{v.typeId}}&isAddress=2&coinCode={{v.typeCode}}"><s:message code='view.content.ChargingCoin' /></a></span>
        {{/if}}
    {{/if}}
</li>
{{/if}}
{{/each}}
</script>
<script type="text/html" id="userOutAddrsTemplate">
{{each data as v}}
<div class="stack mb10 clearfix " style="overflow: hidden;">
	<div class="clearfix toggles" style="border-bottom: 1px solid #dddddd;"><span class="c3d fleft">{{v.shortName}}</span><span class="fright arrow_down"></span></div>
	{{each v.list as vl}}
		<div><s:message code='view.content.Note' />：<span>{{vl.remark}}</span><a class="fright link" href="/coin/operate/apply/index?coinId={{vl.coinId}}&remark={{vl.remark}}&address={{vl.address}}&Id={{vl.id}}"><s:message code='view.content.CashwithdrawalCoin' /></a></div>
	{{/each}}
</div>
{{/each}}
</script>
 <script type="text/javascript" src="${COINX_CDN}common/plugin/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${COINX_CDN}common/plugin/dialog/js/dialog-min.js"></script>
<script type="text/javascript" src="${COINX_CDN}common/plugin/Highcharts/highcharts.src.js"></script>
<script type="text/javascript" src="${COINX_CDN}common/plugin/Highcharts/highcharts-3d.js"></script>
<script type="text/javascript" src="${COINX_CDN}common/plugin/arttemplate/template-web.js"></script>
 <script type="text/javascript" src="${COINX_CDN}common/js/jsutil.js?1c"></script>
<script src="${COINX_CDN}common/plugin/Highcharts/no-data-to-display.src.js"></script>
<script type="text/javascript">
var dtoJsonList = ${dtoJson};
var chartsJson = [];
var chartsData=[];
var userOutAddrs = dtoJsonList.userOutAddrs;
var noticeActive='${noticeActive}';
var kyc='${kycLevel}'
var noData_text = "<s:message code='view.content.notData' />";
if(dtoJsonList.assets.length <= 0){
	$(".listcurrency").find("li").after("<li style='line-height:200px;border:0 none;text-align: center;'>"+noData_text+"</li>");
}
$.each(dtoJsonList.assets,function(index,i){
	if(i.typeCode == "USD" || i.typeCode == "THB"){
		i.about = i.about;
		i.amount =i.amount;
		i.cashAmount = i.cashAmount;
		i.freezeAmount = i.freezeAmount;
		i.percent =i.percent;
		i.unCashAmount = i.unCashAmount;

	}else{
		i.about =i.about;
		i.amount = i.amount;
		i.cashAmount =i.cashAmount;
		i.freezeAmount =i.freezeAmount;
		i.percent =i.percent;
		i.unCashAmount = i.unCashAmount;
	}
	if(noticeActive==''){
        i.noticeActive = '';
    }else{
        i.noticeActive = true;
    }
		chartsJson.push(i);
});

$.each(chartsJson,function(index,i){
    if(parseFloat(i.percent) !=0){
        chartsData.push([i.typeCode,parseFloat((i.percent*100).toFixed(2))]);
    }
});

var notBalance = [];
$.each(chartsJson,function(index,i){
	if(i.amount > 0){
	    if(noticeActive==''){
            i.noticeActive = '';
        }else{
            i.noticeActive = true;
        }
		notBalance.push(i);
	}
});

if(userOutAddrs.length > 0){
	$(".outAddressWrap").html(template("userOutAddrsTemplate",{data: userOutAddrs}));
	$(".outAddressWrap").find(".toggles").on("click", function(){
		$(this).parents().toggleClass("autoHigth");
		$(this).find(".arrow_down").toggleClass("arrow_top");
	});
}



$(function(){
	var _msg = sessionStorage.getItem("withdrawalsMsg");
	if(_msg !== null && _msg !== ""){
		JsUtil.textSuccess(_msg);
		sessionStorage.removeItem("withdrawalsMsg"); 
	}
	
	$("#notbalance").on("change",function(){
		$(".listcurrency").find("li").not('.t-header').remove("li");
// 		var newData=[];
		if($(this).prop('checked')){
// 			newData = notBalance;
			var html = template("notBalanceTemplate", {data:notBalance});
			$(".listcurrency").find(".t-header").after(html);
		}else{
// 			newData = chartsJson;
			var html = template("notBalanceTemplate", {data:chartsJson});
			$(".listcurrency").find(".t-header").after(html);
		}
	});
    Highcharts.setOptions({
        lang: {
            noData: "<s:message code='view.content.notData' />"
        }
    });
    $('#charts-1').highcharts({
        chart: {
            width:470,
            height: 350,
            type: 'pie',
            spacing : [30, 0 , 10, 20],
            options3d: {
                enabled: true,
                alpha: 0
            }
        },
        title:false,
        exporting: {
            enabled:false
        },
        credits:{
            enabled:false
        },
        tooltip: {
            headerFormat: '<br>',
            pointFormat: '{point.name}: <b>{point.y}%</b>'
        },
        legend: {
            layout: 'vertical',
            align: 'left',
            verticalAlign: 'middle',
            borderWidth: 0,
            squareSymbol: false,
            symbolRadius:0,
            itemMarginTop: 5,
            itemMarginBottom: 5,
        },
        plotOptions: {
            pie: {
            	size: 250,
            	center:['64%', '50%'],
//             	innerSize:'50%',
                showInLegend: true,
                dataLabels: {
                    enabled: false
                }
            }
        },
        series: [{
            type: 'pie',
            innerSize: '77%',
            data: chartsData
        }]
    });
});

$('#newaddress,#isKyc').on('click', function () {
    var noticeActive =  '${noticeActive }' || false;
    if(Boolean(noticeActive)){
        var d = dialog({
            width:300,
            content: "<p style='padding:20px 0;'><s:message code='view.content.Mailboxnotactivated' /><p>",
            esc: false,
            cancel:false,
            okValue: '<s:message code="view.content.confirm" />',
            ok: function () {
                window.location.href = "${UCENTER_WEB}";
//		        return false;
            }
        });
        d.showModal();
    }else{
        window.location.href = '/virtual/address/in/add/index?coinId=1';
    }
});

$('.account-moudle').on('click','.isKyc', function () {
    if(kyc==0){
        var d = dialog({
            width:300,
            content: "<p style='padding:20px 0;'><s:message code='-101029'/><p>",
            esc: false,
            cancel:false,
            okValue: '<s:message code="view.content.confirm"/>',
            ok: function () {
                window.location.href = "${UCENTER_WEB}/user/to-kyc1-page";
//		        return false;
            }
        });
        d.showModal();
    }else{
       /* window.location.href = '/virtual/address/in/add/index?coinId=1';*/
    }
});

$('.account-moudle').on('click', '.authstrKyc', function () {
    if(kyc==1){
        var d = dialog({
            width:300,
            content: "<p style='padding:20px 0;'><s:message code="view.content.kycAudit"/><p>",
            esc: false,
            cancel:false,
            okValue: '<s:message code="view.content.confirm"/>',
            ok: function () {
//		        return false;
            }
        });
        d.showModal();
    }
});

$('.account-moudle').on('click', '.rejectKyc', function () {
    if(kyc==2){
        var d = dialog({
            width:300,
            title:"<s:message code='view.content.prompt' />",
            content: "<p'>kyc<s:message code='view.content.AuditFailure' /><p>",
            esc: false,
            cancel:false,
            okValue: '<s:message code="view.content.confirm" />',
            ok: function () {
                window.location.href = "${UCENTER_WEB}";
//		        return false;
            },
            cancelValue: '<s:message code="view.content.cancel" />',
            cancel: function () {
//         return false;
            }
        });
        d.showModal();
    }
});

$('.authstr').on('click', function () {
    var d = dialog({
        width:400,
        title:"<s:message code='view.content.prompt' />",
        content:"<s:message code='view.content.bankAudit' />",
        esc: false,
//         cancel:false,
        okValue: '<s:message code="view.content.confirm" />',
        ok: function () {
//		        return false;
        }
    });
    d.showModal();
});

$('.account-moudle').on('click','.AuditFailure', function () {
    var coinid = $(this).data('coinid');
    var coinCode = $(this).data('coincode');
    var reasonTxt = $(this).data("bankreason");
    var d = dialog({
        width:400,
        title:"<s:message code='view.content.prompt' />",
        content: reasonTxt,
        esc: false,
//         cancel:false,
        okValue: '<s:message code="view.content.relink" />',
        ok: function () {
            window.location.href = "${UCENTER_WEB}/user/to-bind-card-page?coinid="+coinid+"&coinCode="+coinCode+"";
//		        return false;
        },
        cancelValue: '<s:message code="view.content.cancel" />',
        cancel: function () {
//         return false;
        }
    });
    d.showModal();
});




</script>
</html>