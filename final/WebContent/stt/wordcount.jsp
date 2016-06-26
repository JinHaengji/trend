<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">

<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script>
<%
	Vector<Integer> vc = (Vector)session.getAttribute("vc");
	int countunsum = (Integer)session.getAttribute("countunsum"); //불쾌 단어 개수 -> y축 -> 자동으로 max값 잡아주는듯
	int size = vc.size(); //전체 단어 개수 -> x축
	//int array[] = (int[])session.getAttribute("array");
	String customer = (String)session.getAttribute("customer");
	int unpercent = (Integer)session.getAttribute("unpercent");
%>
$(function () {
    $('#container').highcharts({
        chart: {
            type: 'line'
        },
        title: {
            text: '고객 불쾌 단어 누적 지수'
        },
        subtitle: {
            text: '그래프'
        },
        xAxis: { //x축
            categories: <%= size%>
        },
        yAxis: { //y축
            title: {
                text: '전체 단어 개수'
            }
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: false
                },
                enableMouseTracking: false
            }
        },
        series: [{ //[0,0,0, 0, 0, 0, 1, 1, 2,2]
            name: '불쾌단어',
            data: [
            	<% 
	            	for(int i = 0; i<vc.size(); i++)
	            	{ %>
	            		<%= vc.get(i)%>,
	            <%
	            	}
            	%>
            	]
        }]
    });
});
</script>
<title>빈도수 구하기</title>
</head>
<body>
<!-- 
<form method="GET" action="frequency">
	<input type="submit" value="빈도수 구하기">
</form> -->
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

<br>
<h1 align="center"><%=customer %> 고객님은 블랙리스트에 들어갈 확률이 <%=unpercent%>%입니다.</h1>
</body>
</html>