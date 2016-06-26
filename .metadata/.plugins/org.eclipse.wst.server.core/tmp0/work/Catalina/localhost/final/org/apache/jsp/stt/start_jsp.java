/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.65
 * Generated at: 2016-06-26 08:20:51 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.stt;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Vector;
import java.util.StringTokenizer;
import org.apache.commons.logging.Log;
import java.net.URLEncoder;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.kr.morph.MorphAnalyzer;
import org.apache.lucene.analysis.kr.KoreanTokenizer;
import java.io.StringReader;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.kr.morph.AnalysisOutput;
import java.util.List;
import java.util.ArrayList;

public final class start_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=EUC-KR");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

	request.setCharacterEncoding("EUC-KR");
	Log log = LogFactory.getLog("org.apache.lucene.analysis.kr");
    String question = request.getParameter("input");
    if(question==null) question = "";    

      out.write("\r\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<HTML>\r\n");
      out.write(" <HEAD>\r\n");
      out.write("  <TITLE>Morpheme analysis</TITLE>\r\n");
      out.write("\t<STYLE type=\"text/css\">\t\r\n");
      out.write("\ttd {  font-size: 10pt}\r\n");
      out.write("\tselect {  font-size: 10pt}\r\n");
      out.write("\ttextarea {  font-size: 10pt}\r\n");
      out.write("\t.benhur1 {  font-size: 12pt}\r\n");
      out.write("\ta {color: #EDA900}\r\n");
      out.write("\ta:visited {  text-decoration: none; color: #EDA900}\r\n");
      out.write("\ta:link {  text-decoration: none; color: #EDA900}\r\n");
      out.write("\ta:hover {  color: #E95D3C; text-decoration: underline }\r\n");
      out.write("\r\n");
      out.write(".outer {\r\n");
      out.write("\tcolor:#666666;\r\n");
      out.write("\tbackground-color:#ffffff;\r\n");
      out.write("\tfont-family: 돋움, Arial, Tahoma;\r\n");
      out.write("\tborder-bottom: #E95D3C 1px solid;\r\n");
      out.write("}\r\n");
      out.write(".title {\r\n");
      out.write("\tcolor:#666666;\r\n");
      out.write("\tbackground-color:#ffffff;\r\n");
      out.write("\tfont-family: 돋움, Arial, Tahoma;\r\n");
      out.write("\tFONT-SIZE: 12px;\r\n");
      out.write("\theight: 18px; margin-bottom:0px;\r\n");
      out.write("\tpadding: 2px 0 2px 10px;\r\n");
      out.write("}\r\n");
      out.write(".inner {\r\n");
      out.write("\tcolor:#666666;\r\n");
      out.write("\tbackground-color:#ffffff;\r\n");
      out.write("\tfont-family: 돋움, Arial, Tahoma;\r\n");
      out.write("\tFONT-SIZE: 12px;\r\n");
      out.write("\theight: 18px; margin-bottom:0px;\r\n");
      out.write("\tpadding: 2px 0 2px 10px;\r\n");
      out.write("\tmargin: 0 0 0 50px;\r\n");
      out.write("}\r\n");
      out.write("\t\r\n");
      out.write("\t.h1 {\r\n");
      out.write("\t \ttext-align: center;\r\n");
      out.write("        font-family: 'Impact';\r\n");
      out.write("        color: #E95D3C;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t.button-demo {\r\n");
      out.write("\t\tbackground: #E95D3C;\r\n");
      out.write("    \tcolor: #fff;\r\n");
      out.write("    \tfont-family: Lucida Console;\r\n");
      out.write("    \tfont-size: 13px;\r\n");
      out.write("    \theight: 30px;\r\n");
      out.write("    \twidth: 100px;\r\n");
      out.write("    \tline-height: 20px;\r\n");
      out.write("    \tmargin: 10px 10px;\r\n");
      out.write("    \ttext-align: center;\r\n");
      out.write("    \tborder: 0;\r\n");
      out.write("    \ttransition: all 0.3s ease 0s;\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t.button-demo:hover {\r\n");
      out.write(" \t\t background: #EDA900\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t.ali {\r\n");
      out.write("\tmargin-left: 500px;\r\n");
      out.write("\t}\r\n");
      out.write("\t</STYLE>\r\n");
      out.write(" </HEAD>\r\n");
      out.write("\r\n");
      out.write(" <BODY>\r\n");
      out.write("\r\n");
      out.write("<table width=\"800\" align=\"center\">\r\n");
      out.write("  <tr>\r\n");
      out.write("  <td>\r\n");
      out.write("  </td>\r\n");
      out.write("  </tr>\r\n");
      out.write("  <tr>\r\n");
      out.write("  <td>\r\n");
      out.write("\t<a href=\"start.jsp\" class=\"menu\">형태소분석</a>  | <a href=\"cnouns.jsp\" class=\"menu\">복합명사 분해</a>  | <a href=\"space.jsp\" class=\"menu\">자동띄워쓰기</a> | <a href=\"keyword.jsp\" class=\"menu\">색인어추출</a>\r\n");
      out.write("\t<hr/>\r\n");
      out.write("  </td>\r\n");
      out.write("  </tr>\r\n");
      out.write("  </table>\r\n");
      out.write(" <form method=\"post\" name=\"morph\">\r\n");
      out.write("  <table width=\"800\" align=\"center\">\r\n");
      out.write("  <tr> \r\n");
      out.write("  <td>\r\n");
      out.write("\t\t<div style=\"font-size:18pt;text-align:center\" class=\"h1\">Analysis of Korean morpheme</div>\r\n");
      out.write("\r\n");
      out.write("  </td>\r\n");
      out.write("  </tr>\r\n");
      out.write("  <tr>\r\n");
      out.write("  <td>\r\n");
      out.write("  <br>\r\n");
      out.write("  <div style=\"text-align:center\">\r\n");
      out.write("  <textarea name=\"input\" rows=\"7\" cols=\"100\">\r\n");
      out.write(" 뭘 똑바로 해? 이 씨발 미친건가 니 상담실 맞나 상담실을 이따구로 하냐 너 구청 뭐 야 너 이름이 뭐야 너는 내 정보 다 알고있잖아 야 너 말 다했냐\r\n");
      out.write("너 말 다했냐고\r\n");
      out.write("야이 시발년아\r\n");
      out.write("이따구로 하냐\r\n");
      out.write("존나 기분나빠\r\n");
      out.write("아 그래\r\n");
      out.write("양아치냐\r\n");
      out.write("너 이름 뭔데\r\n");
      out.write("미친년이네 이거\r\n");
      out.write("너 뭔데\r\n");
      out.write("실장이냐\r\n");
      out.write("어느 사 실장인데\r\n");
      out.write("내가 알아야 너를 고발할거아냐\r\n");
      out.write("싸가지 없는 것을\r\n");
      out.write("원래 다 그런다\r\n");
      out.write("나는 너 목소리 다 녹음하고 있거든\r\n");
      out.write("여보세요\r\n");
      out.write("  </textarea>\r\n");
      out.write("  <div>\r\n");
      out.write("  <div style=\"text-align:right;margin-right:35px\">\r\n");
      out.write("  \t<input type=\"submit\" class=\"button-demo\" name=\"action\" value=\"실행하기\">\r\n");
      out.write("  </div>\r\n");
      out.write("  </td>\r\n");
      out.write("  </tr>\r\n");
      out.write("  <tr>\r\n");
      out.write("  <td style=\"background-color:#FFCA6C\">\r\n");
      out.write("  <div style=\"font-weight:bold; margin-top:20px;\">입력 : </div>\r\n");
      out.write("  <div style=\"padding-left:40px; margin-top:5px\">");
      out.print(question );
      out.write("</div>\r\n");
      out.write("  </td>\r\n");
      out.write("  </tr>\r\n");
      out.write("  <tr>\r\n");
      out.write("  <td>\r\n");
      out.write("  <div style=\"font-weight:bold;margin-top:20px\">출력 : </div>\r\n");
      out.write("  <hr>\r\n");

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

      out.write("\t\r\n");
      out.write("  </td>\r\n");
      out.write("  </tr>\r\n");
      out.write("  </table>\r\n");
      out.write("</form>  \r\n");
      out.write("<div class=\"ali\">\r\n");
      out.write("<table>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td>\r\n");
      out.write("\t\t<form method=\"GET\" action=\"morphdb\">\r\n");
      out.write("\t\t\t<input class=\"button-demo\" type=\"submit\" value=\"DB 저장\">\r\n");
      out.write("\t\t</form>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<td>\r\n");
      out.write("\t\t<form method=\"GET\" action=\"emotion\">\r\n");
      out.write("  \t\t\t <input class=\"button-demo\" type=\"submit\" value=\"감정 처리\">\r\n");
      out.write("\t\t</form>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<td>\r\n");
      out.write("\t\t<form method=\"GET\" action=\"finalresult\">\r\n");
      out.write("  \t\t\t <input class=\"button-demo\" type=\"submit\" value=\"GRAPH 결과\">\r\n");
      out.write("\t\t</form>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write(" </BODY>\r\n");
      out.write("</HTML>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
