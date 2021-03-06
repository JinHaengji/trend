<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="org.apache.commons.logging.Log"%><%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@page import="java.net.URLEncoder"%>
<%
	request.setCharacterEncoding("EUC-KR");
	Log log = LogFactory.getLog("org.apache.lucene.analysis.kr");
    String question = request.getParameter("input");
    if(question==null) question = "";    
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<%@page import="org.apache.commons.logging.LogFactory"%>

<%@page import="org.apache.lucene.analysis.kr.morph.MorphAnalyzer"%>
<%@page import="org.apache.lucene.analysis.kr.KoreanTokenizer"%>
<%@page import="java.io.StringReader"%>
<%@page import="org.apache.lucene.analysis.Token"%>
<%@page import="org.apache.lucene.analysis.kr.morph.AnalysisOutput"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<HTML>
 <HEAD>
  <TITLE>Morpheme analysis</TITLE>
	<STYLE type="text/css">	
	td {  font-size: 10pt}
	select {  font-size: 10pt}
	textarea {  font-size: 10pt}
	.benhur1 {  font-size: 12pt}
	a {color: #EDA900}
	a:visited {  text-decoration: none; color: #EDA900}
	a:link {  text-decoration: none; color: #EDA900}
	a:hover {  color: #E95D3C; text-decoration: underline }

.outer {
	color:#666666;
	background-color:#ffffff;
	font-family: 돋움, Arial, Tahoma;
	border-bottom: #E95D3C 1px solid;
}
.title {
	color:#666666;
	background-color:#ffffff;
	font-family: 돋움, Arial, Tahoma;
	FONT-SIZE: 12px;
	height: 18px; margin-bottom:0px;
	padding: 2px 0 2px 10px;
}
.inner {
	color:#666666;
	background-color:#ffffff;
	font-family: 돋움, Arial, Tahoma;
	FONT-SIZE: 12px;
	height: 18px; margin-bottom:0px;
	padding: 2px 0 2px 10px;
	margin: 0 0 0 50px;
}
	
	.h1 {
	 	text-align: center;
        font-family: 'Impact';
        color: #E95D3C;
	}
	
	.button-demo {
		background: #E95D3C;
    	color: #fff;
    	font-family: Lucida Console;
    	font-size: 13px;
    	height: 30px;
    	width: 100px;
    	line-height: 20px;
    	margin: 10px 10px;
    	text-align: center;
    	border: 0;
    	transition: all 0.3s ease 0s;
	}


	.button-demo:hover {
 		 background: #EDA900
	}
	
	.ali {
	margin-left: 800px;
	}
	</STYLE>
 </HEAD>

 <BODY>

<table width="800" align="center">
  <tr>
  <td>
  </td>
  </tr>
  <tr>
  <td>
	<a href="start.jsp" class="menu">형태소분석</a>  | <a href="cnouns.jsp" class="menu">복합명사 분해</a>  | <a href="space.jsp" class="menu">자동띄워쓰기</a> | <a href="keyword.jsp" class="menu">색인어추출</a>
	<hr/>
  </td>
  </tr>
  </table>
 <form method="post" name="morph">
  <table width="800" align="center">
  <tr> 
  <td>
		<div style="font-size:18pt;text-align:center" class="h1">Analysis of Korean morpheme</div>

  </td>
  </tr>
  <tr>
  <td>
  <br>
  <div style="text-align:center">
  <textarea name="input" rows="7" cols="100">
안녕하세요
상담 좀 하려고요
제가 옷을 샀는데
옷이 찢어져서
아침에 회사를 가다가 굴욕을 당했어요
진짜 기절초풍을 했어요
그래서 고민하다가 전화드립니다
정말 당황스러워요
옷을 처음에 입었을 때는 호감이어서 흡족했는데
지금은 쫌 후회스럽네요
교환되나요
안된다고요
말 다했어요
아 진짜 어이없네
이따구로 하냐
존나 기분나빠
너 이름 뭔데
신경질나네 진짜
아주 부아가 치미네
내가 알아야 너를 고발할거아냐
싸가지 없네
나는 너 목소리 다 녹음하고 있거든
여보세요
안받냐
아 씨발
  </textarea>
  <div>
  <div style="text-align:right;margin-right:35px">
  	<input type="submit" class="button-demo" name="action" value="실행하기">
  </div>
  </td>
  </tr>
  <tr>
  <td style="background-color:#FFCA6C">
  <div style="font-weight:bold; margin-top:20px;">입력 : </div>
  <div style="padding-left:40px; margin-top:5px"><%=question %></div>
  </td>
  </tr>
  <tr>
  <td>
  <div style="font-weight:bold;margin-top:20px">출력 : </div>
  <hr>
<%
ArrayList<String> morphs = new ArrayList<String>(); //morph 결과 저장 ArrayList 생성
ArrayList<String> types = new ArrayList<String>(); //type 결과 저장 Arraylist 생성
//Vector<String> morphs2 = new Vector<String>();
//question : textarea에서 입력받은 값
//위의 자바문에서 받는걸로 되있음
try
{
	if(!"".equals(question)) 
	{
		log.info(question); //전체 넘어온 음성인식 결과 출력
		long start = System.currentTimeMillis(); //시간 찍히도록
		MorphAnalyzer analyzer = new MorphAnalyzer();
		KoreanTokenizer tokenizer = new KoreanTokenizer(new StringReader(question));
		Token token = null;
		
		//한 토큰식 얻어옴
		while((token=tokenizer.next())!=null) 
		{
			if(!token.type().equals("<KOREAN>")) continue;
			
			out.println("<div class='outer'>");
			try 
			{
				String str;
				
				analyzer.setExactCompound(false);
				List<AnalysisOutput> results = analyzer.analyze(token.termText());
				out.println("<div class='title'>");		
				out.println(token.termText()); //음절로 자르기 ex)안녕하세요/저는/박유진/입니다.
				out.println("</div>");		
				
				for(AnalysisOutput o : results) 
				{
					str = o.toString(); //string 형식으로 바꿈
					
					//System.out.println(str + "타입1 : " + o.getPos());
					//System.out.println(str + "타입2 : " + o.getPos2());
					//log.info("type : " + o.getType());
					out.println("<div class='inner'>");	//음운			
					out.println(str+"->");
					log.info("1:"+str);
					log.info("->:"+str.replaceAll("\\([a-zA-Z]+\\)","")); //타입이 없어진 형태소 분석 결과 -> DB 저장
					
					Pattern p = Pattern.compile("\\((.*?)\\)"); //()안에 문자들을 찾음
					Matcher m = p.matcher(str); //찾은 문자들을 m에 저장
					while(m.find()) //m에 저장된 값들을 하나씩 가져옴
					{
						log.info("타입 : " + m.group(1));
						types.add(m.group(1)); //타입 저장
					}
					
					String[] st2 = new String(str.replaceAll("\\([a-zA-Z]+\\)","")).split(","); //ex)안녕,하세,요 -> 안녕/하세/요
					for(String s : st2)  
					{
						System.out.println("split : " + s);
						morphs.add(s); //음운 저장
					}
						
					
					//morphs.add((str.replaceAll("\\([a-zA-Z]+\\)","")).split(","));
					/* //다시 , 단위로 쪼개기
					StringTokenizer st = new StringTokenizer(str.replaceAll("\\([a-zA-Z]+\\)",""),",");
					
					//tokenizer arraylist에 저장하기
				 	while(st.hasMoreTokens())
					{
						log.info("-->:"+st.nextToken());
						
						//ArrayList로 저장
						//morphs.add(st.nextToken()); //음운 저장
						//morphs2.add(st.nextToken());
					} */
					 
					//음운에서 한글만 가져오기
					/* log.info("2:"+o.getStem()+" / "); //각 음절 분해된것들의 첫번째것만!!!
					//log.info("3:"+o.getPos()+" / "); //위에꺼의 타입
					//log.info("3:"+o.getPatn()+" / "); //나눠진 음운들?의 개수
					log.info("3:"+o.getEomi()+" / "); //동사에서 색인어 다음 것들!!!
					log.info("4:"+o.getVsfx()); */	
					
					/* Pattern p1 = Pattern.compile("\\((.*?)\\)"); //()안에 문자들을 찾음
					Matcher m1 = p1.matcher(str); //찾은 문자들을 m에 저장
					while(m1.find()) //m에 저장된 값들을 하나씩 가져옴
					{
						log.info("타입 : " + m1.group(1));
						types.add(m1.group(1)); //타입 저장
					} */
					
					//계약기간(N),이(j)-> 계약/ 기간/ <100>
					for(int i=0;i<o.getCNounList().size();i++) //한번 더 쪼개질 경우
					{
						out.println(o.getCNounList().get(i).getWord()+"/");
						log.info("for문:"+o.getCNounList().get(i).getWord()); //쪼개진 음운에서 또 다른 의미가 있는 음운일 경우에만 나옴!
						//log.info("for문 타입 : " + );
						
						//ArrayList로 저장
						morphs.add(o.getCNounList().get(i).getWord()); //음운 저장
						types.add("notype"); //타입 저장(for문에서 나온 결과의 타입은 일단 버리는걸로!!!)
						//ex) 부여합니다아(N) -> 부여/합니다아 
						//둘 다 N타입으로 주기엔 나중에 N타입만 가져올 때 '합니다아'가 감정사전에서 찾아올 때 또 모든 결과를 찾아올 것 같아서 일단 버리는 걸로...
					}
					out.println("<"+o.getScore()+">");					
					out.println("</div>");		
				}
			}
			catch (Exception e)
			{
				out.println("<div class='title'>");					
				out.println(e.getMessage());
				out.println("</div>");					
				e.printStackTrace();
			}
			out.println("</div>");	
		}
		out.println("<div>"+(System.currentTimeMillis()-start)+"ms</div>");
		
		//만든 ArrayList 세션으로 넘기기
		session.setAttribute("morphs", morphs);
		session.setAttribute("types", types);
	}
} catch(Exception e)
{
	out.println(e.getMessage());
	e.printStackTrace();
}
%>	
  </td>
  </tr>
  </table>
</form>  
<div class="ali">
<table>
	<tr>
		<td>
		<form method="GET" action="morphdb">
			<input class="button-demo" type="submit" value="DB 저장">
		</form>
		</td>
		
		<td>
		<form method="GET" action="emotion">
  			 <input class="button-demo" type="submit" value="감정 처리">
		</form>
		</td>
		
		<td>
		<form method="GET" action="finalresult">
  			 <input class="button-demo" type="submit" value="GRAPH 결과">
		</form>
		</td>
	</tr>
</table>
</div>

 </BODY>
</HTML>