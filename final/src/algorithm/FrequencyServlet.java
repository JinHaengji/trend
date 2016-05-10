package algorithm;

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
 * Servlet implementation class FrequencyServlet
 */
@WebServlet("/stt/frequency")
public class FrequencyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int id;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrequencyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//���� �ܾ� ������ �ܾ� ���� ���ϱ�
		
		Connection conn = null;
        Statement stmt = null;
        
        HttpSession session = request.getSession();
		id = (int) session.getAttribute("id"); //���� �� id ������
        
        request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
        
	    try {
	    	ServletContext sc = this.getServletContext();
	          Class.forName(sc.getInitParameter("driver"));
	            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	            conn = DriverManager.getConnection(
	                    "jdbc:mysql://localhost/resultdb?useUnicode=true&characterEncoding=UTF-8", //JDBC URL
	                    "sttresult",   // DBMS ����� ���̵�
	                    "sttresult");   // DBMS ����� ��ȣ
	              stmt = conn.createStatement();
	              System.out.println("����� ����Ǿ����ϴ�.");    
	              
	              //����ܾ�(fuzzy>=4.0)�ΰ� ������ 
	              String sql = String.format("SELECT * FROM MORPHRESULT WHERE fuzzy >= 4.0 and id = " + id);              
	              PreparedStatement pstmt=null;
	              pstmt = conn.prepareStatement(sql);
	              ResultSet rs = null;
	              rs = pstmt.executeQuery(); //SELECT ������ ���
	              
	              while(rs.next()) 
	              {
	            	  //emocount = rs.getDouble("cnt");
	            	  
	              }
	              //System.out.println("���� �ܾ��� ������ "+emocount+"�� �Դϴ�.");
	              
	              /*String sql2 = String.format("SELECT COUNT(*) AS \"cnt\" FROM MORPHRESULT WHERE id = " + id);
	              PreparedStatement pstmt2=null;
	              pstmt2 = conn.prepareStatement(sql2);
	              ResultSet rs2 = null;
	              rs2 = pstmt2.executeQuery(); //SELECT ������ ���
	              
	              while(rs2.next()) 
	              {
	            	  allcount = rs2.getDouble("cnt");
	              }
	              System.out.println("�ܾ��� ������ "+allcount+"�� �Դϴ�.");
	              
	              //unpercent = (emocount/allcount*100);
	              unpercent = (int) ((emocount/allcount*100) * 100) / 100.0;
	              System.out.println("���� �ܾ� ������ "+unpercent+"% �Դϴ�.");
	              //���ε� ������ �����ϴ� ����
	             
	              //�� �̸�, ���� �̸� ��������
	              String sql3 = String.format("SELECT customer, counsellor FROM apiresult WHERE id = " + id);
	              PreparedStatement pstmt3=null;
	              pstmt3 = conn.prepareStatement(sql3);
	              ResultSet rs3 = null;
	              rs3 = pstmt3.executeQuery(); //SELECT ������ ���
	              
	              while(rs3.next())
	              {
	            	//db�� ����
		              //String query = "UPDATE CUSTOMERDB SET unpratio = " + unpercent + " WHERE id = " + id;
		              String query = "insert into customerdb(cusid, customer, counsellor, unpratio) values(" + id + ", '" + rs3.getString("customer") + "', '" + rs3.getString("counsellor") + "', " + unpercent + ")";
		              PreparedStatement statement1 = conn.prepareStatement(query);
					statement1.execute();
	              }
	              
				
				System.out.println("customerdb�� �����");*/
				
				response.setContentType("text/html; charset=UTF-8");
				 RequestDispatcher rd = request.getRequestDispatcher("/stt/.jsp");
	             rd.include(request, response);
	              
	    } catch (Exception e)
	    {
	         e.printStackTrace();
	    }
	}

}
