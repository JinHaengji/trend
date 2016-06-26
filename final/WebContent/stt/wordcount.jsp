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
            text: 'GRAPH'
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
<title>BlackList</title>
</head>
<body>
<h1>Customer BlackList</h1>
<!-- 
<form method="GET" action="frequency">
	<input type="submit" value="빈도수 구하기">
</form> -->
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

<br>
<h2>BlackList 기준표</h1>
<br>
<table>
	<tr>
		<th>기준</th>
		<th>결과</th>
	</tr>
	
	<tr>
	<td>0% - 50%</td>
	<td>블랙리스트에 포함되지 않습니다.</td>
	</tr>

	<tr>
	<td>50% - 70%</td>
	<td>블랙리스트에 포함 될 확률이 높습니다.</td>	
	</tr>
	
	<tr>
	<td>70% - 100%</td>
	<td>블랙리스트에 포함됩니다.</td>	
	</tr>
</table>
<br>
<br>
<h1 align="center"><%=customer %> 고객님은 블랙리스트에 들어갈 확률이 <%=unpercent%>%입니다.</h1>
<%
   String result;
   if(unpercent>=0 && unpercent<=50)
   {
      result = "블랙리스트에 포함되지 않습니다.";
   }
   else if(unpercent>50 && unpercent<=70)
   {
      result = "블랙리스트에 포함될 확률이 높습니다.";
   }
   else
   {
      result = "블랙리스트에 포함됩니다.";
   }
%>
<h1 align="center">따라서, <%=customer %> 고객님은 <%=result %></h1>
</body>
</html>