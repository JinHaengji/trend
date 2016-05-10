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

@WebServlet("/stt/finalresult")
public class ResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private double emocount;
	private double allcount;
	private double unpercent;
	private int id;
	private String customer = "";
	private String counsellor = "";

	private int i = 1;

<<<<<<< HEAD
	//private int freq;
=======
>>>>>>> 36b7b4729b80ccb64a93da00ffe166d7fc35cf06
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
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
	              
	              String sql = String.format("SELECT COUNT(*) AS \"cnt\" FROM MORPHRESULT WHERE prototypicality > 0 and fuzzy >= 4.0 and id = " + id);              
	              PreparedStatement pstmt=null;
	              pstmt = conn.prepareStatement(sql);
	              ResultSet rs = null;
	              rs = pstmt.executeQuery(); //SELECT ������ ���
	              
	              while(rs.next()) //����ܾ� ����
	              {
	            	  emocount = rs.getDouble("cnt");
	              }
	              System.out.println("���� �ܾ��� ������ "+emocount+"�� �Դϴ�.");
	              	              
	              String sql2 = String.format("SELECT COUNT(*) AS \"cnt\" FROM MORPHRESULT WHERE id = " + id);
	              PreparedStatement pstmt2=null;
	              pstmt2 = conn.prepareStatement(sql2);
	              ResultSet rs2 = null;
	              rs2 = pstmt2.executeQuery(); //SELECT ������ ���
	              
	              while(rs2.next()) //�ܾ� �� ����
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
	              System.out.println("customerdb�� �����");
	              
	              //morphresult�� �ش� �� id�� ���ؼ� ������ �ึ�� id�� ���� �ֱ�
	              /*String sql4 = String.format("SELECT * FROM morphresult WHERE id = " + id);
	              PreparedStatement pstmt4=null;
	              pstmt4 = conn.prepareStatement(sql4);
	              ResultSet rs4 = null;
	              rs4 = pstmt4.executeQuery(); //SELECT ������ ���
	              
	              
	              while(rs4.next())
	              {
	            	  Statement st5 = conn.createStatement();
		              st5.executeUpdate("update morphresult set rowid = " + i + " where id = " + id + " and mresult = '" + rs4.getString("mresult") + "'");
		              i++;
	              }
	              
	              System.out.println("rowid �����");*/
				
<<<<<<< HEAD
				

				response.setContentType("text/html; charset=UTF-8");
				 RequestDispatcher rd = request.getRequestDispatcher("/stt/wordcount.jsp");
	             rd.include(request, response);

=======
>>>>>>> 36b7b4729b80ccb64a93da00ffe166d7fc35cf06
				System.out.println("customerdb�� �����");
				
	              //���� ���� ���� ���̺� ��������
	              String sql4 = String.format("SELECT * FROM MORPHRESULT WHERE id = " + id);
	              PreparedStatement pstmt4=null;
	              pstmt4 = conn.prepareStatement(sql4);
	              ResultSet rs4 = null;
	              rs4 = pstmt4.executeQuery();
	              int freq=0;
	              while(rs4.next()) //�ܾ� �� ����
	              {
	            	  if(rs4.getDouble("fuzzy") > 0){
	            		  freq++;
	            	  } 
	            	  else{
	            		  System.out.println(freq);
            		  //freq=0;
	            	  }
	              }
	              System.out.println(freq);
<<<<<<< HEAD

=======
	              
					response.setContentType("text/html; charset=UTF-8");
					 RequestDispatcher rd = request.getRequestDispatcher("/stt/wordcount.jsp");
		             rd.include(request, response);
>>>>>>> 36b7b4729b80ccb64a93da00ffe166d7fc35cf06
	              
	    } catch (Exception e)
	    {
	         e.printStackTrace();
	    }
	}
}
