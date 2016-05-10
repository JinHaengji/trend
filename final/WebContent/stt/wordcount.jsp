<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">

<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script>
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
        xAxis: {
            categories: [1 , 2, 3, 4, 5, 6, 7, 8, 9, 10]
        },
        yAxis: {
            title: {
                text: '��ü �ܾ� ����'
            }
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: false
            }
        },
        series: [{
            name: 'Tokyo',
            data: [0,0,0, 0, 0, 0, 1, 1, 2,2]
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

</body>
</html>