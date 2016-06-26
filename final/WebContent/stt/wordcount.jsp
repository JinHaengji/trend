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
	int countunsum = (Integer)session.getAttribute("countunsum"); //���� �ܾ� ���� -> y�� -> �ڵ����� max�� ����ִµ�
	int size = vc.size(); //��ü �ܾ� ���� -> x��
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
            text: '�� ���� �ܾ� ���� ����'
        },
        subtitle: {
            text: '�׷���'
        },
        xAxis: { //x��
            categories: <%= size%>
        },
        yAxis: { //y��
            title: {
                text: '��ü �ܾ� ����'
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
            name: '����ܾ�',
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
<title>�󵵼� ���ϱ�</title>
</head>
<body>
<!-- 
<form method="GET" action="frequency">
	<input type="submit" value="�󵵼� ���ϱ�">
</form> -->
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

<br>
<h1 align="center"><%=customer %> ������ ������Ʈ�� �� Ȯ���� <%=unpercent%>%�Դϴ�.</h1>
</body>
</html>