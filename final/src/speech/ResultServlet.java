package speech;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/stt/finalresult")
public class ResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private double emocount;
	private double allcount;
	private double unpercent;
	private int id;
	private String customer = "";
	private String counsellor = "";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Connection conn = null;
        Statement stmt = null;
        
        HttpSession session = request.getSession();
		id = (int) session.getAttribute("id"); //현재 고객 id 가져옴
        
        request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
        
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
	              
	              String sql = String.format("SELECT COUNT(*) AS \"cnt\" FROM MORPHRESULT WHERE prototypicality > 0 and fuzzy >= 4.0 and id = " + id);              
	              PreparedStatement pstmt=null;
	              pstmt = conn.prepareStatement(sql);
	              ResultSet rs = null;
	              rs = pstmt.executeQuery(); //SELECT 쿼리인 경우
	              
	              while(rs.next()) 
	              {
	            	  emocount = rs.getDouble("cnt");
	              }
	              System.out.println("불쾌 단어의 개수는 "+emocount+"개 입니다.");
	              
	              String sql2 = String.format("SELECT COUNT(*) AS \"cnt\" FROM MORPHRESULT WHERE id = " + id);
	              PreparedStatement pstmt2=null;
	              pstmt2 = conn.prepareStatement(sql2);
	              ResultSet rs2 = null;
	              rs2 = pstmt2.executeQuery(); //SELECT 쿼리인 경우
	              
	              while(rs2.next()) 
	              {
	            	  allcount = rs2.getDouble("cnt");
	              }
	              System.out.println("단어의 개수는 "+allcount+"개 입니다.");
	              
	              //unpercent = (emocount/allcount*100);
	              unpercent = (int) ((emocount/allcount*100) * 100) / 100.0;
	              System.out.println("불쾌 단어 비율은 "+unpercent+"% 입니다.");
	              //바인딩 데이터 연결하는 과정
	             
	              //고객 이름, 상담사 이름 가져오기
	              String sql3 = String.format("SELECT customer, counsellor FROM apiresult WHERE id = " + id);
	              PreparedStatement pstmt3=null;
	              pstmt3 = conn.prepareStatement(sql3);
	              ResultSet rs3 = null;
	              rs3 = pstmt3.executeQuery(); //SELECT 쿼리인 경우
	              
	              while(rs3.next())
	              {
	            	//db에 저장
		              //String query = "UPDATE CUSTOMERDB SET unpratio = " + unpercent + " WHERE id = " + id;
		              String query = "insert into customerdb(cusid, customer, counsellor, unpratio) values(" + id + ", '" + rs3.getString("customer") + "', '" + rs3.getString("counsellor") + "', " + unpercent + ")";
		              PreparedStatement statement1 = conn.prepareStatement(query);
					statement1.execute();
	              }
	              
				
				System.out.println("customerdb에 저장됨");
	              
	    } catch (Exception e)
	    {
	         e.printStackTrace();
	    }
	}
}
