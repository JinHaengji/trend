<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import="vo.Resultdb"%>
<html>
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Customer Analysis System</title>
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
        font-family: 'Impact';
        color: #E95D3C;
      }
      
      h3
      {
      	font-family: Arial Narrow;
      	color: #EDA900;
      }
      
       span
      {
      	font-family: 'Impact';    	
      	color: #E95D3C;
      }
      

      .buttons-wrapper
      {
        text-align: center;
      }

      .hidden
      {
        display: none;
      }

      #transcription,
      #log
      {
        display: block;
        width: 100%;
        height: 7em;
        overflow-y: scroll;
        border: 1px solid #333333;
        line-height: 1.3em;
      }

      .button-demo
      {
        padding: 0.5em;
        display: inline-block;
        margin: 1em auto;
      }
      
    input[type='radio'] {
    	-webkit-appearance:none;
    	width:20px;
    	height:20px;
    	border:1px solid darkgray;
    	border-radius:50%;
    	outline:none;
    	box-shadow:0 0 5px 0px gray inset;
	}

	input[type='radio']:hover {
    	box-shadow:0 0 5px 0px orange inset;
	}

	input[type='radio']:before {
    	content:'';
    	display:block;
    	width:60%;
    	height:60%;
    	margin: 20% auto;    
    	border-radius:50%;    
	}
	
	input[type='radio']:checked:before {
    	background: #E95D3C;
	}

	label {
		font-family: 'MS Sans Serif'; 
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
	
	#anly {
		background: #EDA900;
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
	
	#anly:hover {
 		 background: #E95D3C
	}
	
    </style>
  </head>
  <body>
    <h1>Customer Analysis System</h1>
    <h3>Customer consultation</h3>
    <textarea id="transcription" readonly="readonly"></textarea>

    <span> RESULTS:</span>
    <label><input type="radio" name="recognition-type" value="final" checked="checked" /> Final only</label>
    <label><input type="radio" name="recognition-type" value="interim" /> Interim</label>

    <h3>Connection</h3>
    <div id="log"></div>

    <div class="buttons-wrapper">
      <button id="button-play-ws" class="button-demo">Play</button>
      <button id="button-stop-ws" class="button-demo">Stop</button>
      <button id="clear-all" class="button-demo">Clear all</button>
      <form action="start.jsp" method="GET">
      	<input id="anly" type="submit" value="형태소 분석">
      </form>
    </div>
    <span id="ws-unsupported" class="hidden"></span>

    <script>
      // Test browser support
      window.SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition || null;

      if (window.SpeechRecognition === null) 
      {
        document.getElementById('ws-unsupported').classList.remove('hidden');
        document.getElementById('button-play-ws').setAttribute('disabled', 'disabled');
        document.getElementById('button-stop-ws').setAttribute('disabled', 'disabled');
      } 
      else 
      {
        var recognizer = new window.SpeechRecognition();
        var transcription = document.getElementById('transcription'); //결과 받아올 변수
        var log = document.getElementById('log');
		
        // 사용자가 멈출때 쓰일 인자
        recognizer.continuous = true;

        // 인식 시작
        recognizer.onresult = function(event) 
        {
          transcription.textContent = '';

          for (var i = event.resultIndex; i < event.results.length; i++)
          {
            if (event.results[i].isFinal) //인식 끝나면 정확도까지 보여줌(Stop demo 버튼 누르면)
            {
              //현재 시간
              var now = new Date();
              var nowAll = now.getFullYear() + "-" + (now.getMonth() + 1) + "-" + now.getDate() + " " + now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds() + " ";
              transcription.textContent = event.results[i][0].transcript + ' (Confidence: ' + event.results[i][0].confidence + ')';
              
              console.log(event.results[i][0].transcript);
             
              var xhr = new XMLHttpRequest();
              
              var a=decodeURIComponent(event.results[i][0].transcript); 
              xhr.open('GET', '/final/stt/api?date='+nowAll + '&transcription=' + a + '&confidence=' + event.results[i][0].confidence, true);
              xhr.send(null); 
            } 
            else
            {
              transcription.textContent += event.results[i][0].transcript; //인식 결과가 계속 더해짐
            }
          }
          
        };

        
        
        //에러 처리
        recognizer.onerror = function(event)
        {
          log.innerHTML = 'Recognition error: ' + event.message + '<br />' + log.innerHTML;
        };

        document.getElementById('button-play-ws').addEventListener('click', function() 
        {
          //연속적인 결과 얻을때
          recognizer.interimResults = document.querySelector('input[name="recognition-type"][value="interim"]').checked;

          try 
          {
            recognizer.start(); //인식 시작
            log.innerHTML = 'Recognition started' + '<br />' + log.innerHTML;
          } 
          catch(ex)
          {
            log.innerHTML = 'Recognition error: ' + ex.message + '<br />' + log.innerHTML; //에러 메시지 출력
          }
        });

        document.getElementById('button-stop-ws').addEventListener('click', function() 
        {
          recognizer.stop();
          log.innerHTML = 'Recognition stopped' + '<br />' + log.innerHTML;
        });

        document.getElementById('clear-all').addEventListener('click', function()
        {
          transcription.textContent = '';
          log.textContent = '';
        });
      }
      
      
    </script>
  </body>
</html>