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

@WebServlet("/stt/finalresult")
public class ResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private double emocount;
	private double allcount;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Connection conn = null;
        Statement stmt = null;
        
        
        
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
	              
	              String sql = String.format("SELECT COUNT(*) AS \"cnt\" FROM MORPHRESULT WHERE prototypicality>0");              
	              PreparedStatement pstmt=null;
	              pstmt = conn.prepareStatement(sql);
	              ResultSet rs = null;
	              rs = pstmt.executeQuery(); //SELECT ������ ���
	              
	              while(rs.next()) 
	              {
	            	  emocount = rs.getDouble("cnt");
	              }
	              System.out.println("���� ������ ������ "+emocount+"�� �Դϴ�.");
	              
	              String sql2 = String.format("SELECT COUNT(*) AS \"cnt\" FROM MORPHRESULT");
	              PreparedStatement pstmt2=null;
	              pstmt2 = conn.prepareStatement(sql2);
	              ResultSet rs2 = null;
	              rs2 = pstmt2.executeQuery(); //SELECT ������ ���
	              
	              while(rs2.next()) 
	              {
	            	  allcount = rs2.getDouble("cnt");
	              }
	              System.out.println("�ܾ��� ������ "+allcount+"�� �Դϴ�.");
	              
	              
	              System.out.println("�� %�� "+(emocount/allcount*100)+"% �Դϴ�.");
	              //���ε� ������ �����ϴ� ����
	             
     
	              
	    } catch (Exception e)
	    {
	         e.printStackTrace();
	    }
	}
}
