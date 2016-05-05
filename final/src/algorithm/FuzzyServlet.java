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
    private double pleasant; //��.���� ����
    private double vitalization; //Ȱ��ȭ ����
    private Vector<Double> fresult = new Vector<Double>(); //������Ʈ�� ������ �� �� �ִ� ���� (���� �ܾ��� ����)
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
	                    "sttresult",   // DBMS ����� ���̵�
	                    "sttresult");   // DBMS ����� ��ȣ
	              stmt = conn.createStatement();
	              System.out.println("����� ����Ǿ����ϴ�.");    
	              
	              //���� ���� id�ΰ͸� ��������
	              String sql = String.format("SELECT * FROM MORPHRESULT WHERE id = '" + id + "'");              
	              PreparedStatement pstmt=null;
	              pstmt = conn.prepareStatement(sql);
	              ResultSet rs = null;
	              rs = pstmt.executeQuery(); //SELECT ������ ���
	              
	              while(rs.next()) 
	              {
	            	  //Ÿ���� N �̳� V �ΰ͸� �ش�
	            	  if (rs.getString("TYPE").equals("V") || rs.getString("TYPE").equals("N")) 
	            	  {
	            		  pleasant = rs.getDouble("pleasant"); //��.����
	            		  vitalization = rs.getDouble("vitalization"); //Ȱ��ȭ
	            		  
	            		  //������ ����
	            		  //��.���� y�� �ִ� 2��, Ȱ��ȭ y�� �ִ� 2���� vector�� ���ϵ�
	            		  pp = fc.funcPlea(pleasant);
	            		  vv = fc.funcVitali(vitalization);
	            		  
	            		  //ǥ���� �ش��ϴ� �� fresult�� ������
	            		  res = getFResult(pleasant, vitalization);
	            		  
	            		  //�׽�Ʈ
	            		  for (int i = 0; i < pp.size(); i++) {
	            				Object obj = vv.get(i);
	            				System.out.println("��.���� y�� : " + (double) obj);
	            			}
	            		  
	            		  for (int i = 0; i < vv.size(); i++) {
	            				Object obj = vv.get(i);
	            				System.out.println("Ȱ��ȭ y�� : " + (double) obj);
	            			}
	            		  
	            		  for (int i = 0; i < res.size(); i++) {
	            				Object obj = res.get(i);
	            				System.out.println("ǥ �� : " + (double) obj);
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
		Vector<Double> ff = new Vector<Double>(); //������Ʈ�� ������ �� �� �ִ� ���� (���� �ܾ��� ����)
		
		//ǥ���� �ش��ϴ� �� fresult ������
		//��.���� �Լ� �׷������� �ش��ϴ� fresult ã�Ƽ� ���� -> 2�� ���ü���
		
		if((pleasant>=5.50 && pleasant<=7.00) && (vitalization>=1.00 && vitalization<5.00)) //��&���� ǥ��
			  ff.add(4.00); //����
		  if((pleasant>=5.50 && pleasant<=7.00) && (vitalization>=3.00 && vitalization<7.00)) //��&����
			  ff.add(2.50); //����
		  if((pleasant>=5.50 && pleasant<=7.00) && (vitalization>=5.00 && vitalization<=7.00)) //��&���� ǥ��
			  ff.add(2.50); //����
		  
		  if((pleasant>=4.00 && pleasant<7.00) && (vitalization>=1.00 && vitalization<3.00)) //���� ��&���� ǥ��
			  ff.add(4.00); //����
		  if((pleasant>=4.00 && pleasant<7.00) && (vitalization>=3.00 && vitalization<7.00)) //���� ��&����
			  ff.add(4.00); //����
		  if((pleasant>=4.00 && pleasant<7.00) && (vitalization>=5.00 && vitalization<=7.00)) //���� ��&���� ǥ��
			  ff.add(4.00); //����
		  
		  if((pleasant>=2.50 && pleasant<5.50) && (vitalization>=1.00 && vitalization<3.00)) //���� ����&���� ǥ��
			  ff.add(4.00); //����
		  if((pleasant>=2.50 && pleasant<5.50) && (vitalization>=3.00 && vitalization<7.00)) //���� ����&����
			  ff.add(5.50); //����
		  if((pleasant>=2.50 && pleasant<5.50) && (vitalization>=5.00 && vitalization<=7.00)) //���� ����&���� ǥ��
			  ff.add(5.50); //����
		  
		  if((pleasant>=1.00 && pleasant<4.00) && (vitalization>=1.00 && vitalization<3.00)) //����&���� ǥ��
			  ff.add(5.50); //����
		  if((pleasant>=1.00 && pleasant<4.00) && (vitalization>=3.00 && vitalization<7.00)) //����&����
			  ff.add(7.00); //�ſ� ����
		  if((pleasant>=1.00 && pleasant<4.00) && (vitalization>=5.00 && vitalization<=7.00)) //����&���� ǥ��
			  ff.add(7.00); //�ſ� ����
		  
		  
		  
		  
		return ff;
	}
}
