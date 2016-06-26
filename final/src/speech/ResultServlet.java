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
	              
	              /////////////////////////////////////////////////////////////
	              //기존 불쾌 단어 개수 비율
	              /*String sql = String.format("SELECT COUNT(*) AS \"cnt\" FROM MORPHRESULT WHERE fuzzy >= 4.0 and id = " + id);              
	              PreparedStatement pstmt=null;
	              pstmt = conn.prepareStatement(sql);
	              ResultSet rs = null;
	              rs = pstmt.executeQuery(); //SELECT 쿼리인 경우
	              
	              while(rs.next()) //불쾌단어 개수
	              {
	            	  emocount = rs.getDouble("cnt");
	              }
	              System.out.println("불쾌 단어의 개수는 "+emocount+"개 입니다.");
	              	              
	              String sql2 = String.format("SELECT COUNT(*) AS \"cnt\" FROM MORPHRESULT WHERE id = " + id + " and (type = 'N' or type = 'V')");
	              PreparedStatement pstmt2=null;
	              pstmt2 = conn.prepareStatement(sql2);
	              ResultSet rs2 = null;
	              rs2 = pstmt2.executeQuery(); //SELECT 쿼리인 경우
	              
	              while(rs2.next()) //단어 총 개수
	              {
	            	  allcount = rs2.getDouble("cnt");
	              }
	              System.out.println("단어의 개수는 "+allcount+"개 입니다.");*/
	              
	              //////////////////////////////////////////////////////////////
	            //(불쾌 단어들 퍼지값 합) / (전체 감정 단어 퍼지 값 합) 
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
		              
		              System.out.println("(불쾌 단어들 퍼지값 합) / (전체 감정 단어 퍼지 값 합) : " + (int) ((unsum / sum*100) * 100) / 100.0);
		              System.out.println("unsum : " + unsum);
		              System.out.println("sum : " + sum);
		              
	              //unpercent = (emocount/allcount*100);
	              unpercent = (int) ((unsum / sum*100) * 100) / 100;
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
					customer = rs3.getString("customer");
	              }
	              System.out.println("customerdb에 저장됨");
	              

	              //morphresult의 해당 고객 id에 대해서 각각의 행마다 id를 따로 주기
	              /*String sql4 = String.format("SELECT * FROM morphresult WHERE id = " + id);
	              PreparedStatement pstmt4=null;
	              pstmt4 = conn.prepareStatement(sql4);
	              ResultSet rs4 = null;
	              rs4 = pstmt4.executeQuery(); //SELECT 쿼리인 경우
	              
	              
	              while(rs4.next())
	              {
	            	  Statement st5 = conn.createStatement();
		              st5.executeUpdate("update morphresult set rowid = " + i + " where id = " + id + " and mresult = '" + rs4.getString("mresult") + "'");
		              i++;
	              }
	              
	              System.out.println("rowid 저장됨");*/
				
				//response.setContentType("text/html; charset=UTF-8");
				 //RequestDispatcher rd = request.getRequestDispatcher("/stt/wordcount.jsp");
	             //rd.include(request, response);

				//System.out.println("customerdb에 저장됨");
				
				///////////////////////////////////////////////////////////
				
				//불쾌 단어 사이 거리 구하기
	              //현재 고객에 대한 테이블 가져오기
	              /*String sql4 = String.format("SELECT * FROM MORPHRESULT WHERE id = " + id);
	              PreparedStatement pstmt4=null;
	              pstmt4 = conn.prepareStatement(sql4);
	              ResultSet rs4 = null;
	              rs4 = pstmt4.executeQuery();
	              int freq=0;
<<<<<<< HEAD
	              int array[] = {};
	              int i = 0;
	              
	              System.out.println("단어 빈도 수");
	              /*
=======
	              //int array[] = {};
	              Vector<Integer> array = new Vector<Integer>();
	              Vector<Integer> dist = new Vector<Integer>();
	              
	              HashMap<String, Integer> hm = new HashMap<String, Integer>();
	              int i = 0;
	              
	              //System.out.println("단어 빈도 수");
>>>>>>> a6c24597e075390e1bc88c789825253e880f1b49
	              while(rs4.next()) //단어 총 개수
	              {
	            	  //각각 단어에 index를 준다 -> rowid
	            	  //hashmap
	            	  hm.put(rs4.getString("mresult"), i);
	            	  i++;
	            	  System.out.println(hm.get(rs4.getString("mresult")));  
	            	  array.add(0);
	            	  
	            	//불쾌 단어 만나면
	            	  if(rs4.getDouble("fuzzy") >= 4.00)
	            	  {
	            		  freq++;
	            		  System.out.println(freq);
	            		  //여기서 freq들 저장
	            		  //array[i] = freq;
	            		  //i++;
	            		  array.add(freq);
	            		  
	            		  //그때의 hashmap의 value값을 얻어서 vector에 저장한다
	            		  array.add(hm.get(rs4.getString("mresult")));
	            	  } 
	              }
	              
	              System.out.println("단어사이거리");
	              for(int j = 0; j<array.size(); j++)
	              {
	            	System.out.println(array.get(j));  
	            	
	            	dist.add(array.get(j+1)-array.get(j));
	              }*/
	              
				/////////////////////////////////////////////////////////////
				//그래프 넘기기
				String sql6 = String.format("SELECT * FROM MORPHRESULT WHERE id = " + id);
	              PreparedStatement pstmt6=null;
	              pstmt6 = conn.prepareStatement(sql6);
	              ResultSet rs6 = null;
	              rs6 = pstmt6.executeQuery();
	              Vector<Integer> vc  = new Vector<Integer>();
	              int countsum = 0; 
	              int countunsum = 0; //불쾌 단어 개수
	              
	              
	              while(rs6.next()) //단어 총 개수
	              {
	            	  if(rs6.getDouble("fuzzy") >= 4.00) //불쾌 단어를 만나면
	            	  {
	            		  vc.add(++countsum);
	            		  ++countunsum;
	            	  }
	            	  else //불쾌 단어가 아닌 감정단어거나 그냥 단어를 만났을 때
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
	              
	              System.out.println("vc내용 : ");
	              for(int i = 0; i<vc.size(); i++)
	              {
	            	  System.out.println(vc.get(i));
	            	  array[i] = vc.get(i);
	              }
	              
	              //세션 값으로 넘겨주기
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
