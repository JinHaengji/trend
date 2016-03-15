package speech;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SpeechServlet
 */
@WebServlet("/stt/api")
public class APIServlet extends HttpServlet
{
   private static final long serialVersionUID = 1L;
    private int id;
    private String trans;
    //private int flag;
    private String transcription;
    
   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
   {      
	   
	      Connection conn = null;
	      Statement stmt = null;
	      ResultSet rs = null;
      HttpSession session = request.getSession();
      
      request.setCharacterEncoding("UTF-8");
       response.setContentType("text/html; charset=UTF-8");

       //session.removeAttribute("trans");
       trans = (String) session.getAttribute("trans");
       if(trans == null)
          trans="";
       
       id = (int) session.getAttribute("id");
       System.out.println("id : " + id);
       String date = request.getParameter("date");
       transcription = new String(request.getParameter("transcription").getBytes("8859_1"), "UTF-8");
       transcription = trans + transcription;
       session.setAttribute("trans", transcription);
       //session.removeAttribute("trans"); //세션 삭제
       String confidence = request.getParameter("confidence");
       
       System.out.println(id+", "+date+", "+transcription + ", " + confidence);
      
      try {
         ServletContext sc = this.getServletContext();
         Class.forName(sc.getInitParameter("driver"));
         DriverManager.registerDriver(new com.mysql.jdbc.Driver());
         conn = DriverManager.getConnection("jdbc:mysql://localhost/resultdb?useUnicode=true&characterEncoding=UTF-8", "sttresult", "sttresult"); 
         stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE APIRESULT SET date = '" + date + "', result = '" + transcription + "', confidence = " + confidence + " WHERE id =" + id);
            //session.setAttribute("trans", trans);
         
      } catch (Exception e) {
         throw new ServletException(e);
         
      } finally {
         try {if (rs != null) rs.close();} catch(Exception e) {}
         try {if (stmt != null) stmt.close();} catch(Exception e) {}
         try {if (conn != null) conn.close();} catch(Exception e) {}
      }
   } 
}