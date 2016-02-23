<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import="vo.Resultdb"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Web Speech API Demo</title>
<style>
body {
	max-width: 500px;
	margin: 2em auto;
	font-size: 20px;
}

h1 {
	text-align: center;
}

.buttons-wrapper {
	text-align: center;
}

.hidden {
	display: none;
}

#transcription, #log {
	display: block;
	width: 100%;
	height: 5em;
	overflow-y: scroll;
	border: 1px solid #333333;
	line-height: 1.3em;
}

.button-demo {
	padding: 0.5em;
	display: inline-block;
	margin: 1em auto;
}
</style>
</head>
<body>
	<h1>Web Speech API</h1>
	<h2>Result</h2>
	<!-- ê²°ê³¼ì°½ ë³´ì¬ì£¼ë ê³µê° -->
	<textarea id="transcription" readonly="readonly"></textarea>
	
		<%
		ArrayList<Resultdb> resultdbs = (ArrayList<Resultdb>) request.getAttribute("resultdbs");
		for (Resultdb resultdb : resultdbs) {
	    %> 
	

	
	<script>
	 var transcription = document.getElementById('transcription');
	 transcription.textContent="<%=resultdb.getresult()%>";
	</script>
	 
<%
		}
	%>
</body>
</html>