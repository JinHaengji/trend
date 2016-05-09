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
		id = (int) session.getAttribute("id"); //���� �� id ������

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
					"sttresult", // DBMS ����� ���̵�
					"sttresult"); // DBMS ����� ��ȣ
			stmt = conn.createStatement();
			System.out.println("����� ����Ǿ����ϴ�.");

			// ���� ������ �Ϳ���
			// ���� ���� id�ΰ͸� ��������
			String sql = String.format("SELECT * FROM MORPHRESULT WHERE id = '" + id + "'");
			PreparedStatement pstmt = null;
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = null;
			rs = pstmt.executeQuery(); // SELECT ������ ���

			while (rs.next())
			{
				//��ü �ܾ� ���� ������
				//String query = "selecet count(*) from morphresult where id = '" + id + "'";
				 
			      Statement st1 = conn.createStatement();
			       
			      // execute the query, and get a java resultset
			      ResultSet rs1 = st1.executeQuery("selecet count(*) from morphresult where id = '" + id + "'");
				
			      if(rs1.next()) 
			    	  rowcount = rs1.getInt(1);
			      
			        System.out.println("��ü �ܾ� ���� : " + rowcount);
				
				
				//���� �ܾ� ���� ������(fuzzy ���� 4 �̻��� �ܾ� -> ���� �ܾ�)
				if (rs.getDouble("pleasant") > 0) 
				{
					
				}
				//���� �ܾ� ����/��ü �ܾ� ���� -> ���� ����
				
				//customerdb�� ����
			}

			//fuzzyresult.jsp�� �Ѿ
			/*response.setContentType("text/html; charset=UTF-8");
			 RequestDispatcher rd = request.getRequestDispatcher("/stt/fuzzyresult.jsp");
            rd.include(request, response);*/
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
