package speech;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MorphDBServlet
 */
@WebServlet("/stt/morphdb") //���常 �ϴ� ���
public class MorphDBServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    private ArrayList<String> morphs = new ArrayList<String>();
    private ArrayList<String> types = new ArrayList<String>();
    private int id;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt=null;
        
		HttpSession session = request.getSession();
		
		request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    //ArrayList ���ǿ��� ��������
	    morphs = (ArrayList) session.getAttribute("morphs");
	    types = (ArrayList)session.getAttribute("types");
	    id = (int) session.getAttribute("id");
	    System.out.println("���� id : " + id);
	    //session.removeAttribute("morphs"); //���� ����
	    
	    //�� �Ѿ�Դ��� ����غ���
	    for(int i=0; i<morphs.size(); i++)
		{
			System.out.println("morph���� : " + morphs.get(i));
			System.out.println("���� Ÿ�� : " + types.get(i));
		}

	    //DB ����
	    try 
	    {
	         ServletContext sc = this.getServletContext();
	          Class.forName(sc.getInitParameter("driver"));
	            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	            conn = DriverManager.getConnection(
	                    "jdbc:mysql://localhost/resultdb?useUnicode=true&characterEncoding=UTF-8", //JDBC URL
	                    "sttresult",   // DBMS ����� ���̵�
	                    "sttresult");   // DBMS ����� ��ȣ
	              stmt = conn.createStatement();
	              System.out.println("����� ����Ǿ����ϴ�.");    
	              
	              //���� id
	              
	              //String sql = String.format("SELECT COUNT(*) AS \"cnt\" FROM APIRESULT");
	              //pstmt = conn.prepareStatement(sql);
	              //���ε� ������ �����ϴ� ����
	              //rs = pstmt.executeQuery(); //SELECT ������ ���
	              
	              /*while(rs.next()) 
	              {
	               id = rs.getInt("cnt")+1;
	              }*/
	              
	              //session.setAttribute("id", id); //session�� id ����
	              //System.out.println(id+", "+customer + ", " + counsellor);
	             
	              for(int i=0; i<morphs.size(); i++)
	              {
	            	  stmt.executeUpdate("INSERT INTO MORPHRESULT(id,mresult,type) VALUES ("+id+",'"+morphs.get(i)+"','"+types.get(i)+"')");
	              }
	              
	              response.setContentType("text/html; charset=UTF-8");
	              System.out.println("morphdb�� ����Ǿ����ϴ�.");
	              //System.out.println("insert?");   
	              
	  			PrintWriter out=response.getWriter(); 
				out.println("<script language='javascript'>"); 
				out.println("alert('db������ �Ϸ�Ǿ����ϴ�.');"); 
				out.println("</script>"); 
	              
	              RequestDispatcher rd = request.getRequestDispatcher("/stt/start.jsp");
	              rd.include(request, response);
	      } 
	    catch (Exception e)
	    {
	         e.printStackTrace();
	    }
	}
}
