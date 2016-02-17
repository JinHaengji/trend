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


// ServletContext에 보관된 MemberDao 사용하기  
@WebServlet("/stt/home")
public class HomePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	
	@Override
	public void doGet(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		 Connection conn = null;
	     Statement stmt = null;
	     ResultSet rs = null;
	     PreparedStatement pstmt=null;
	     
	     request.setCharacterEncoding("UTF-8");
	     HttpSession session = request.getSession();
	     int id = 0;
	     //id = 3;
	   
	     String customer = new String(request.getParameter("customer").getBytes("8859_1"), "UTF-8");
	     String counsellor = new String(request.getParameter("counsellor").getBytes("8859_1"), "UTF-8");
	      
	    

	    	
		try {
			ServletContext sc = this.getServletContext();
			
			 Class.forName(sc.getInitParameter("driver"));
	         DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	         
	         conn = DriverManager.getConnection(
	                 "jdbc:mysql://localhost/resultdb?useUnicode=true&characterEncoding=UTF-8", //JDBC URL
	                 "sttresult",   // DBMS 사용자 아이디
	                 "sttresult");   // DBMS 사용자 암호
	           stmt = conn.createStatement();
	           System.out.println("제대로 연결되었습니다.");    
	          
	           String sql = String.format("SELECT COUNT(*) AS \"cnt\" FROM APIRESULT");
	           pstmt = conn.prepareStatement(sql);
	           //바인딩 데이터 연결하는 과정
	           rs = pstmt.executeQuery(); //SELECT 쿼리인 경우
	           
	           while(rs.next()) {
	            id = rs.getInt("cnt")+1;
	           }
	           
	           
	           session.setAttribute("id", id); //session에 id 저장
	           System.out.println(id+", "+customer + ", " + counsellor);
	          
	           stmt.executeUpdate("INSERT INTO APIRESULT(id,customer,counsellor) VALUES ("+id+",'"+customer+"','"+counsellor+"')");
	                  
	           response.setContentType("text/html; charset=UTF-8");
	           System.out.println("insert?");   
	           RequestDispatcher rd = request.getRequestDispatcher("/stt/speech.jsp");
	           rd.include(request, response);

			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
	}
}
