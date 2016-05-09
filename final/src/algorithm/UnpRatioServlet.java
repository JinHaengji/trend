package algorithm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UnpRatioServlet
 */
@WebServlet("/stt/unpratio")
public class UnpRatioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int id;
	private int rowcount = 0;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UnpRatioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Connection conn = null;
		Statement stmt = null;

		HttpSession session = request.getSession();
		id = (int) session.getAttribute("id"); //현재 고객 id 가져옴

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		try 
		{
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/resultdb?useUnicode=true&characterEncoding=UTF-8", // JDBC
																								// URL
					"sttresult", // DBMS 사용자 아이디
					"sttresult"); // DBMS 사용자 암호
			stmt = conn.createStatement();
			System.out.println("제대로 연결되었습니다.");

			// 감정 추출한 것에서
			// 현재 고객의 id인것만 가져오기
			String sql = String.format("SELECT * FROM MORPHRESULT WHERE id = '" + id + "'");
			PreparedStatement pstmt = null;
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = null;
			rs = pstmt.executeQuery(); // SELECT 쿼리인 경우

			while (rs.next())
			{
				//전체 단어 개수 가져옴
				//String query = "selecet count(*) from morphresult where id = '" + id + "'";
				 
			      Statement st1 = conn.createStatement();
			       
			      // execute the query, and get a java resultset
			      ResultSet rs1 = st1.executeQuery("selecet count(*) from morphresult where id = '" + id + "'");
				
			      if(rs1.next()) 
			    	  rowcount = rs1.getInt(1);
			      
			        System.out.println("전체 단어 개수 : " + rowcount);
				
				
				//불쾌 단어 개수 가져옴(fuzzy 값이 4 이상인 단어 -> 불쾌 단어)
				if (rs.getDouble("pleasant") > 0) 
				{
					
				}
				//불쾌 단어 개수/전체 단어 개수 -> 비율 구함
				
				//customerdb에 저장
			}

			//fuzzyresult.jsp로 넘어감
			/*response.setContentType("text/html; charset=UTF-8");
			 RequestDispatcher rd = request.getRequestDispatcher("/stt/fuzzyresult.jsp");
            rd.include(request, response);*/
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
