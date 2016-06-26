<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- 부가적인 테마 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<title>고객 상담 분석 시스템</title>
<style>
      body
      {
        max-width: 500px;
        margin: 2em auto;
        font-size: 20px;
      }

      h1
      {
        text-align: center;
      }
</style>
</head>   
<body>
   <h1>시작 페이지</h1>
   <form action="index" method="GET">
           고객 이름 : <input type="text" name="customer"><br>
           상담사 이름 : <input type="text" name="counsellor"><br>
            <input type="submit" value="시작">
    </form>
</body>
</html>