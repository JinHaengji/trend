package speech;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;

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
	private int unpercent;
	private int id;
	private String customer = "";
	private String counsellor = "";
	private int size_all;
	
	private int i = 1;

	
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
	              
	              /////////////////////////////////////////////////////////////
	              //���� ���� �ܾ� ���� ����
	              /*String sql = String.format("SELECT COUNT(*) AS \"cnt\" FROM MORPHRESULT WHERE fuzzy >= 4.0 and id = " + id);              
	              PreparedStatement pstmt=null;
	              pstmt = conn.prepareStatement(sql);
	              ResultSet rs = null;
	              rs = pstmt.executeQuery(); //SELECT ������ ���
	              
	              while(rs.next()) //����ܾ� ����
	              {
	            	  emocount = rs.getDouble("cnt");
	              }
	              System.out.println("���� �ܾ��� ������ "+emocount+"�� �Դϴ�.");
	              	              
	              String sql2 = String.format("SELECT COUNT(*) AS \"cnt\" FROM MORPHRESULT WHERE id = " + id + " and (type = 'N' or type = 'V')");
	              PreparedStatement pstmt2=null;
	              pstmt2 = conn.prepareStatement(sql2);
	              ResultSet rs2 = null;
	              rs2 = pstmt2.executeQuery(); //SELECT ������ ���
	              
	              while(rs2.next()) //�ܾ� �� ����
	              {
	            	  allcount = rs2.getDouble("cnt");
	              }
	              System.out.println("�ܾ��� ������ "+allcount+"�� �Դϴ�.");*/
	              
	              //////////////////////////////////////////////////////////////
	            //(���� �ܾ�� ������ ��) / (��ü ���� �ܾ� ���� �� ��) 
					String sql5 = String.format("SELECT * FROM MORPHRESULT WHERE id = " + id);
		              PreparedStatement pstmt5=null;
		              pstmt5 = conn.prepareStatement(sql5);
		              ResultSet rs5 = null;
		              rs5 = pstmt5.executeQuery();
		              //int freq=0;
		              double sum = 0; 
		              double unsum = 0;
		              
		              while(rs5.next()) 
		              {
		            	  if(rs5.getDouble("fuzzy") >= 4.0)
		            	  {
		            		  unsum += rs5.getDouble("fuzzy");
		            		  sum += rs5.getDouble("fuzzy");
		            	  }
		            	  else
		            	  {
		            		  sum += rs5.getDouble("fuzzy");
		            	  }
		            	  
		              }
		              
		              System.out.println("(���� �ܾ�� ������ ��) / (��ü ���� �ܾ� ���� �� ��) : " + (int) ((unsum / sum*100) * 100) / 100.0);
		              System.out.println("unsum : " + unsum);
		              System.out.println("sum : " + sum);
		              
	              //unpercent = (emocount/allcount*100);
	              unpercent = (int) ((unsum / sum*100) * 100) / 100;
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
					customer = rs3.getString("customer");
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
				
				//response.setContentType("text/html; charset=UTF-8");
				 //RequestDispatcher rd = request.getRequestDispatcher("/stt/wordcount.jsp");
	             //rd.include(request, response);

				//System.out.println("customerdb�� �����");
				
				///////////////////////////////////////////////////////////
				
				//���� �ܾ� ���� �Ÿ� ���ϱ�
	              //���� ���� ���� ���̺� ��������
	              /*String sql4 = String.format("SELECT * FROM MORPHRESULT WHERE id = " + id);
	              PreparedStatement pstmt4=null;
	              pstmt4 = conn.prepareStatement(sql4);
	              ResultSet rs4 = null;
	              rs4 = pstmt4.executeQuery();
	              int freq=0;
<<<<<<< HEAD
	              int array[] = {};
	              int i = 0;
	              
	              System.out.println("�ܾ� �� ��");
	              /*
=======
	              //int array[] = {};
	              Vector<Integer> array = new Vector<Integer>();
	              Vector<Integer> dist = new Vector<Integer>();
	              
	              HashMap<String, Integer> hm = new HashMap<String, Integer>();
	              int i = 0;
	              
	              //System.out.println("�ܾ� �� ��");
>>>>>>> a6c24597e075390e1bc88c789825253e880f1b49
	              while(rs4.next()) //�ܾ� �� ����
	              {
	            	  //���� �ܾ index�� �ش� -> rowid
	            	  //hashmap
	            	  hm.put(rs4.getString("mresult"), i);
	            	  i++;
	            	  System.out.println(hm.get(rs4.getString("mresult")));  
	            	  array.add(0);
	            	  
	            	//���� �ܾ� ������
	            	  if(rs4.getDouble("fuzzy") >= 4.00)
	            	  {
	            		  freq++;
	            		  System.out.println(freq);
	            		  //���⼭ freq�� ����
	            		  //array[i] = freq;
	            		  //i++;
	            		  array.add(freq);
	            		  
	            		  //�׶��� hashmap�� value���� �� vector�� �����Ѵ�
	            		  array.add(hm.get(rs4.getString("mresult")));
	            	  } 
	              }
	              
	              System.out.println("�ܾ���̰Ÿ�");
	              for(int j = 0; j<array.size(); j++)
	              {
	            	System.out.println(array.get(j));  
	            	
	            	dist.add(array.get(j+1)-array.get(j));
	              }*/
	              
				/////////////////////////////////////////////////////////////
				//�׷��� �ѱ��
				String sql6 = String.format("SELECT * FROM MORPHRESULT WHERE id = " + id);
	              PreparedStatement pstmt6=null;
	              pstmt6 = conn.prepareStatement(sql6);
	              ResultSet rs6 = null;
	              rs6 = pstmt6.executeQuery();
	              Vector<Integer> vc  = new Vector<Integer>();
	              int countsum = 0; 
	              int countunsum = 0; //���� �ܾ� ����
	              
	              
	              while(rs6.next()) //�ܾ� �� ����
	              {
	            	  if(rs6.getDouble("fuzzy") >= 4.00) //���� �ܾ ������
	            	  {
	            		  vc.add(++countsum);
	            		  ++countunsum;
	            	  }
	            	  else //���� �ܾ �ƴ� �����ܾ�ų� �׳� �ܾ ������ ��
	            	  {
	            		  vc.add(countsum);
	            	  }
	              }
<<<<<<< HEAD
	              System.out.println(freq);
*/
=======
	              
	              size_all = vc.size();
	              int array[] = new int[size_all];
	              
	              System.out.println("vc���� : ");
	              for(int i = 0; i<vc.size(); i++)
	              {
	            	  System.out.println(vc.get(i));
	            	  array[i] = vc.get(i);
	              }
	              
	              //���� ������ �Ѱ��ֱ�
	              //HttpSession session = request.getSession();
	              session.setAttribute("vc", vc);
	              session.setAttribute("array", array);
	              session.setAttribute("countunsum", countunsum);
	              session.setAttribute("customer", customer);
	              session.setAttribute("unpercent", unpercent);
	              ////////////////////////////////////////////////////////////////
>>>>>>> a6c24597e075390e1bc88c789825253e880f1b49
	              
					response.setContentType("text/html; charset=UTF-8");
					 RequestDispatcher rd = request.getRequestDispatcher("/stt/wordcount.jsp");
		             rd.include(request, response);

	              
	    } catch (Exception e)
	    {
	         e.printStackTrace();
	    }
	}
}
