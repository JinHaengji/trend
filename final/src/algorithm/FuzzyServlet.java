package algorithm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FuzzyServlet
 */
@WebServlet("/stt/fuzzy")
public class FuzzyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private int id;
    private double pleasant; //쾌.불쾌 점수
    private double vitalization; //활성화 점수
    private Vector<Double> fresult = new Vector<Double>(); //블랙리스트에 영향을 줄 수 있는 정도 (불쾌 단어의 지수)
    private Vector<Double> pp = new Vector<Double>(); 
    private Vector<Double> vv = new Vector<Double>(); 
    private Vector<Double> res = new Vector<Double>(); 
    private FuzzyFunction fc = new FuzzyFunction();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FuzzyServlet() {
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
        id = (int) session.getAttribute("id");
        
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
	              
	              //현재 고객의 id인것만 가져오기
	              String sql = String.format("SELECT * FROM MORPHRESULT WHERE id = '" + id + "'");              
	              PreparedStatement pstmt=null;
	              pstmt = conn.prepareStatement(sql);
	              ResultSet rs = null;
	              rs = pstmt.executeQuery(); //SELECT 쿼리인 경우
	              
	              while(rs.next()) 
	              {
	            	  //타입이 N 이나 V 인것만 해당
	            	  if (rs.getString("TYPE").equals("V") || rs.getString("TYPE").equals("N")) 
	            	  {
	            		  pleasant = rs.getDouble("pleasant"); //쾌.불쾌
	            		  vitalization = rs.getDouble("vitalization"); //활성화
	            		  
	            		  //행지꺼 먼저
	            		  //쾌.불쾌 y값 최대 2개, 활성화 y값 최대 2개가 vector로 리턴됨
	            		  pp = fc.funcPlea(pleasant);
	            		  vv = fc.funcVitali(vitalization);
	            		  
	            		  //표에서 해당하는 각 fresult들 가져옴
	            		  res = getFResult(pleasant, vitalization);
	            		  
	            		  //테스트
	            		  for (int i = 0; i < pp.size(); i++) {
	            				Object obj = vv.get(i);
	            				System.out.println("쾌.불쾌 y값 : " + (double) obj);
	            			}
	            		  
	            		  for (int i = 0; i < vv.size(); i++) {
	            				Object obj = vv.get(i);
	            				System.out.println("활성화 y값 : " + (double) obj);
	            			}
	            		  
	            		  for (int i = 0; i < res.size(); i++) {
	            				Object obj = res.get(i);
	            				System.out.println("표 값 : " + (double) obj);
	            			}
	            	  }
	              }
	              
	    }
	    catch (Exception e)
	    {
	         e.printStackTrace();
	    }
	}
	
	public Vector<Double> getFResult(double pleasant, double vitalization)
	{
		Vector<Double> ff = new Vector<Double>(); //블랙리스트에 영향을 줄 수 있는 정도 (불쾌 단어의 지수)
		
		//표에서 해당하는 각 fresult 가져옴
		//쾌.불쾌 함수 그래프에서 해당하는 fresult 찾아서 저장 -> 2개 나올수도
		
		if((pleasant>=5.50 && pleasant<=7.00) && (vitalization>=1.00 && vitalization<5.00)) //쾌&적게 표현
			  ff.add(4.00); //조금
		  if((pleasant>=5.50 && pleasant<=7.00) && (vitalization>=3.00 && vitalization<7.00)) //쾌&보통
			  ff.add(2.50); //안줌
		  if((pleasant>=5.50 && pleasant<=7.00) && (vitalization>=5.00 && vitalization<=7.00)) //쾌&많이 표현
			  ff.add(2.50); //안줌
		  
		  if((pleasant>=4.00 && pleasant<7.00) && (vitalization>=1.00 && vitalization<3.00)) //조금 쾌&적게 표현
			  ff.add(4.00); //조금
		  if((pleasant>=4.00 && pleasant<7.00) && (vitalization>=3.00 && vitalization<7.00)) //조금 쾌&보통
			  ff.add(4.00); //조금
		  if((pleasant>=4.00 && pleasant<7.00) && (vitalization>=5.00 && vitalization<=7.00)) //조금 쾌&많이 표현
			  ff.add(4.00); //조금
		  
		  if((pleasant>=2.50 && pleasant<5.50) && (vitalization>=1.00 && vitalization<3.00)) //조금 불쾌&적게 표현
			  ff.add(4.00); //조금
		  if((pleasant>=2.50 && pleasant<5.50) && (vitalization>=3.00 && vitalization<7.00)) //조금 불쾌&보통
			  ff.add(5.50); //많이
		  if((pleasant>=2.50 && pleasant<5.50) && (vitalization>=5.00 && vitalization<=7.00)) //조금 불쾌&많이 표현
			  ff.add(5.50); //많이
		  
		  if((pleasant>=1.00 && pleasant<4.00) && (vitalization>=1.00 && vitalization<3.00)) //불쾌&적게 표현
			  ff.add(5.50); //많이
		  if((pleasant>=1.00 && pleasant<4.00) && (vitalization>=3.00 && vitalization<7.00)) //불쾌&보통
			  ff.add(7.00); //매우 많이
		  if((pleasant>=1.00 && pleasant<4.00) && (vitalization>=5.00 && vitalization<=7.00)) //불쾌&많이 표현
			  ff.add(7.00); //매우 많이
		  
		  
		  
		  
		return ff;
	}
}
