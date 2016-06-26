<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
table {
	margin-left:450px;
	}

 h1
      {
        text-align: center;
        font-family: 'Impact';
        color: #E95D3C;
      }
      
tr {   
	height: 1em;  
	}
      

td {                           
	text-align: center;                      
	padding: 1em;       
	}        
      
th {                       
	text-align: center;                
	padding: 1em;
	background-color: #EDA900;      
	color: white;       
	}
	
h2 {
	text-align: center;
	color: #EDA900;
}
	
table tr:nth-child(even) {   
	text-align: center;         
    background-color: #eee;    
     }

table tr:nth-child(odd) {      
	text-align: center;  
	background-color:#fff;     
 }

	   
</style>
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
            text: 'GRAPH'
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
<title>BlackList</title>
</head>
<body>
<h1>Customer BlackList</h1>
<!-- 
<form method="GET" action="frequency">
	<input type="submit" value="�󵵼� ���ϱ�">
</form> -->
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

<br>
<h2>BlackList ����ǥ</h1>
<br>
<table>
	<tr>
		<th>����</th>
		<th>���</th>
	</tr>
	
	<tr>
	<td>0% - 50%</td>
	<td>������Ʈ�� ���Ե��� �ʽ��ϴ�.</td>
	</tr>

	<tr>
	<td>50% - 70%</td>
	<td>������Ʈ�� ���� �� Ȯ���� �����ϴ�.</td>	
	</tr>
	
	<tr>
	<td>70% - 100%</td>
	<td>������Ʈ�� ���Ե˴ϴ�.</td>	
	</tr>
</table>
<br>
<br>
<h1 align="center"><%=customer %> ������ ������Ʈ�� �� Ȯ���� <%=unpercent%>%�Դϴ�.</h1>
<%
   String result;
   if(unpercent>=0 && unpercent<=50)
   {
      result = "������Ʈ�� ���Ե��� �ʽ��ϴ�.";
   }
   else if(unpercent>50 && unpercent<=70)
   {
      result = "������Ʈ�� ���Ե� Ȯ���� �����ϴ�.";
   }
   else
   {
      result = "������Ʈ�� ���Ե˴ϴ�.";
   }
%>
<h1 align="center">����, <%=customer %> ������ <%=result %></h1>
</body>
</html>