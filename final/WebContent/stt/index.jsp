<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Customer Analysis System</title>

<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- 부가적인 테마 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

  
<style>
      body
      {
        max-width: 500px;
        margin: 2em auto;
        font-size: 20px;
      }

      h1
      {
      	margin-top: 200px;
        text-align: center;
        font-family: 'Impact';
        color: #E95D3C;
      }
      
      label {
      	font-family: Arial Narrow;
      	color: #EDA900;
      	font-size: 30px;
      }
      
      .button-demo {
		background: #E95D3C;
    	color: #fff;
    	font-family: Lucida Console;
    	font-size: 15px;
    	height: 40px;
    	width: 100px;
    	line-height: 25px;
    	margin: 25px 25px;
    	text-align: center;
    	border: 0;
    	transition: all 0.3s ease 0s;
	}


	.button-demo:hover {
 		 background: #EDA900
	}
	
	      .buttons-wrapper
      {
        text-align: center;
      }
	
</style>
</head>   
<body>
   <h1>Customer Analysis System</h1>
   <form action="index" method="GET">
   <div class=buttons-wrapper> 
          <label>Customer  : </label><input type="text" name="customer"><br>
          <label>Counselor : </label><input type="text" name="counsellor"><br><br>
          <input class="button-demo" type="submit" value="Start">
    </div> 
    </form>
</body>
</html>