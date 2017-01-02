/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.69
 * Generated at: 2016-11-29 12:18:56 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.stt;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class wordcount_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("<style>\n");
      out.write("table {\n");
      out.write("\tmargin-left:750px;\n");
      out.write("\t}\n");
      out.write("\n");
      out.write(" h1\n");
      out.write("      {\n");
      out.write("        text-align: center;\n");
      out.write("        font-family: 'Impact';\n");
      out.write("        color: #E95D3C;\n");
      out.write("      }\n");
      out.write("      \n");
      out.write("tr {   \n");
      out.write("\theight: 1em;  \n");
      out.write("\t}\n");
      out.write("      \n");
      out.write("\n");
      out.write("td {                           \n");
      out.write("\ttext-align: center;                      \n");
      out.write("\tpadding: 1em;       \n");
      out.write("\t}        \n");
      out.write("      \n");
      out.write("th {                       \n");
      out.write("\ttext-align: center;                \n");
      out.write("\tpadding: 1em;\n");
      out.write("\tbackground-color: #EDA900;      \n");
      out.write("\tcolor: white;       \n");
      out.write("\t}\n");
      out.write("\t\n");
      out.write("h2 {\n");
      out.write("\ttext-align: center;\n");
      out.write("\tcolor: #EDA900;\n");
      out.write("}\n");
      out.write("\t\n");
      out.write("table tr:nth-child(even) {   \n");
      out.write("\ttext-align: center;         \n");
      out.write("    background-color: #eee;    \n");
      out.write("     }\n");
      out.write("\n");
      out.write("table tr:nth-child(odd) {      \n");
      out.write("\ttext-align: center;  \n");
      out.write("\tbackground-color:#fff;     \n");
      out.write(" }\n");
      out.write("\n");
      out.write("\t   \n");
      out.write("</style>\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=EUC-KR\">\n");
      out.write("\n");
      out.write("<script src=\"http://code.jquery.com/jquery-1.10.2.js\"></script>\n");
      out.write("<script>\n");

	Vector<Integer> vc = (Vector)session.getAttribute("vc");
	int countunsum = (Integer)session.getAttribute("countunsum"); //불쾌 단어 개수 -> y축 -> 자동으로 max값 잡아주는듯
	int size = vc.size(); //전체 단어 개수 -> x축
	//int array[] = (int[])session.getAttribute("array");
	String customer = (String)session.getAttribute("customer");
	//int unpercent = (Integer)session.getAttribute("unpercent");
	int fresult = (Integer)session.getAttribute("fresult");

      out.write("\n");
      out.write("$(function () {\n");
      out.write("    $('#container').highcharts({\n");
      out.write("        chart: {\n");
      out.write("            type: 'line'\n");
      out.write("        },\n");
      out.write("        title: {\n");
      out.write("            text: '고객 불쾌 단어 누적 지수'\n");
      out.write("        },\n");
      out.write("        subtitle: {\n");
      out.write("            text: 'GRAPH'\n");
      out.write("        },\n");
      out.write("        xAxis: { //x축\n");
      out.write("            categories: ");
      out.print( size);
      out.write("\n");
      out.write("        },\n");
      out.write("        yAxis: { //y축\n");
      out.write("            title: {\n");
      out.write("                text: '전체 단어 개수'\n");
      out.write("            }\n");
      out.write("        },\n");
      out.write("        plotOptions: {\n");
      out.write("            line: {\n");
      out.write("                dataLabels: {\n");
      out.write("                    enabled: false\n");
      out.write("                },\n");
      out.write("                enableMouseTracking: false\n");
      out.write("            }\n");
      out.write("        },\n");
      out.write("        series: [{ //[0,0,0, 0, 0, 0, 1, 1, 2,2]\n");
      out.write("            name: '불쾌단어',\n");
      out.write("            data: [\n");
      out.write("            \t");
 
	            	for(int i = 0; i<vc.size(); i++)
	            	{ 
      out.write("\n");
      out.write("\t            \t\t");
      out.print( vc.get(i));
      out.write(",\n");
      out.write("\t            ");

	            	}
            	
      out.write("\n");
      out.write("            \t]\n");
      out.write("        }]\n");
      out.write("    });\n");
      out.write("});\n");
      out.write("</script>\n");
      out.write("<title>BlackList</title>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("<h1>Customer BlackList</h1>\n");
      out.write("<!-- \n");
      out.write("<form method=\"GET\" action=\"frequency\">\n");
      out.write("\t<input type=\"submit\" value=\"빈도수 구하기\">\n");
      out.write("</form> -->\n");
      out.write("<script src=\"https://code.highcharts.com/highcharts.js\"></script>\n");
      out.write("<script src=\"https://code.highcharts.com/modules/exporting.js\"></script>\n");
      out.write("<div id=\"container\" style=\"min-width: 310px; height: 400px; margin: 0 auto\"></div>\n");
      out.write("\n");
      out.write("<br>\n");
      out.write("<h2>BlackList 기준표</h1>\n");
      out.write("<br>\n");
      out.write("<table align = \"center\">\n");
      out.write("\t<tr>\n");
      out.write("\t\t<th>기준</th>\n");
      out.write("\t\t<th>결과</th>\n");
      out.write("\t</tr>\n");
      out.write("\t\n");
      out.write("\t<tr>\n");
      out.write("\t<td>0% - 50%</td>\n");
      out.write("\t<td>블랙리스트에 포함되지 않습니다.</td>\n");
      out.write("\t</tr>\n");
      out.write("\n");
      out.write("\t<tr>\n");
      out.write("\t<td>50% - 70%</td>\n");
      out.write("\t<td>블랙리스트에 포함 될 확률이 높습니다.</td>\t\n");
      out.write("\t</tr>\n");
      out.write("\t\n");
      out.write("\t<tr>\n");
      out.write("\t<td>70% - 100%</td>\n");
      out.write("\t<td>블랙리스트에 포함됩니다.</td>\t\n");
      out.write("\t</tr>\n");
      out.write("</table>\n");
      out.write("<br>\n");
      out.write("<br>\n");
      out.write("<h1 align=\"center\">");
      out.print(customer );
      out.write(" 고객님은 블랙리스트에 포함될 확률이 ");
      out.print(fresult);
      out.write("%입니다.</h1>\n");

   String result;
   if(fresult>=0 && fresult<=50)
   {
      result = "블랙리스트에 포함되지 않습니다.";
   }
   else if(fresult>50 && fresult<=70)
   {
      result = "블랙리스트에 포함될 확률이 높습니다.";
   }
   else
   {
      result = "블랙리스트에 포함됩니다.";
   }

      out.write("\n");
      out.write("<h1 align=\"center\">따라서, ");
      out.print(customer );
      out.write(" 고객님은 ");
      out.print(result );
      out.write("</h1>\n");
      out.write("</body>\n");
      out.write("</html>");
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