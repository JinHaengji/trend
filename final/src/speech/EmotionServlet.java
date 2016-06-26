package speech;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import algorithm.FuzzyFunction;
import vo.Exceldb;

@WebServlet("/stt/emotion")
public class EmotionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int id;
	
	private double pleasant; // ��.���� ����
	private double vitalization; // Ȱ��ȭ ����
	private Vector<Double> fresult = new Vector<Double>(); // ������Ʈ�� ������ �� �� �ִ�
															// ���� (���� �ܾ��� ����)
	private Vector<Double> pp = new Vector<Double>();
	private Vector<Double> vv = new Vector<Double>();
	private Vector<Double> res = new Vector<Double>();
	
	private FuzzyFunction fc = new FuzzyFunction();

	private double minsum;
	private double sum;
	private double fuzzy;
	
	public EmotionServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	static String type = null;

	@SuppressWarnings("resource")
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, FileNotFoundException {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		HttpSession session = request.getSession();
		id = (int) session.getAttribute("id");
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");


		try {

			ServletContext sc = this.getServletContext();

			Class.forName(sc.getInitParameter("driver"));
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/resultdb?useUnicode=true&characterEncoding=UTF-8", // JDBC
																								// //
																								// URL
					"sttresult", // DBMS ����� ���̵�
					"sttresult"); // DBMS ����� ��ȣ
			stmt = conn.createStatement();
			System.out.println("����� ����Ǿ����ϴ�.");
			

			////////////////////////////////////////////////////////////////////////////////////////////////////	
			
			// ��� ����� ��
			rs = stmt.executeQuery("SELECT id,MRESULT,TYPE,prototypicality FROM MORPHRESULT");
			response.setContentType("text/html; charset=UTF-8");

			// ����

			ArrayList<Exceldb> emotions = new ArrayList<Exceldb>();

			while (rs.next()) { // ���� , ��, ���, ����, ��, ���ϴ�
				////////////////////////////////////////////////////////////////////////////////////////////////////
				// �ܾ�� type�� ����
				System.out.println(rs.getString("mresult") + "  " + rs.getString("TYPE"));

				// �ܾ �α��� �̻��̰�, Ÿ���� v,n�̸�
				if (((rs.getString("TYPE").equals("V")) || (rs.getString("TYPE").equals("N")))
						&& (rs.getString("mresult").length() >= 2)) {
					System.out.println("���� �ϴ� " + rs.getString("mresult"));
					ResultSet rs2 = null;
					Statement statement = conn.createStatement();	
					// �����ϴ�, �����Ǵ�
					rs2 = statement.executeQuery(
							"SELECT emotionword, prototypicality, familiarity, pleasant, vitalization FROM emotiondic");
					
					//�ϴ� 0���� �ʱ�ȭ
					Statement statement2 = conn.createStatement();
					statement2.executeUpdate(
							"UPDATE MORPHRESULT SET prototypicality=0, familiarity=0, pleasant=0 , vitalization=0 where mresult='"
									+ rs.getString("mresult") + "'");
					// �������� �ܾ� �� ��������
					while (rs2.next()) {
						Statement statement1 = conn.createStatement();
						// ���������� ���ԵǸ�
						if ((rs2.getString("emotionword").contains(rs.getString("mresult"))
								|| rs.getString("mresult").contains(rs2.getString("emotionword")))
								&& (rs2.getString("emotionword").charAt(0) == rs.getString("mresult").charAt(0))
								&& (rs2.getString("emotionword").charAt(1) == rs.getString("mresult").charAt(1))) {

							System.out.println(rs2.getString("emotionword") + "  ..  " + rs.getString("mresult"));

							statement1.executeUpdate("UPDATE MORPHRESULT SET prototypicality="
									+ rs2.getString("prototypicality") + ", familiarity=" + rs2.getString("familiarity")
									+ ", pleasant=" + rs2.getString("pleasant") 
									+ ", vitalization=" + rs2.getString("vitalization") 
									+ " WHERE mresult='" + rs.getString("mresult") + "'");
							// �����ϴ� like ����~
						}
					}
				} 
				

				else {
					// ��� ����� ��
					System.out.println("���� ���� �ʴ� " + rs.getString("mresult"));
					Statement statement2 = conn.createStatement();
					statement2.executeUpdate(
							"UPDATE MORPHRESULT SET prototypicality=0, familiarity=0, pleasant=0, vitalization=0 where mresult='"
									+ rs.getString("mresult") + "'");

				}
			}

			///////////////////////////////////////////////////////////////////////////////////////////////////

			// ���������� ������

			// ���� ������ �Ϳ���
			// ���� ���� id�ΰ͸� ��������
			PreparedStatement pstmt_f = null;
			String sql_f = String.format("SELECT * FROM MORPHRESULT WHERE id = '" + id + "'");			
			pstmt_f = conn.prepareStatement(sql_f);
			ResultSet rs_f = pstmt_f.executeQuery(); // SELECT ������ ���;
			
			while (rs_f.next()) {
				Vector<Double> min = new Vector<Double>();

		        minsum=0;
	            sum=0;
	            fuzzy=0;
	            
				// �����ܾ��� �͸� �ش�
				if (rs_f.getDouble("pleasant") > 0) {
					pleasant = rs_f.getDouble("pleasant"); // ��.����
					vitalization = rs_f.getDouble("vitalization"); // Ȱ��ȭ

					// ������ ����
					// ��.���� y�� �ִ� 2��, Ȱ��ȭ y�� �ִ� 2���� vector�� ���ϵ�
					pp = fc.funcPlea(pleasant);
					vv = fc.funcVitali(vitalization);

					// ǥ���� �ش��ϴ� �� fresult�� ������
					res = getFResult(pleasant, vitalization);

					// �׽�Ʈ
					for (int i = 0; i < vv.size(); i++) {
						Object obj = vv.get(i);
						System.out.println("Ȱ��ȭ y�� : " + (double) obj);
					}

					for (int i = 0; i < pp.size(); i++) {
						Object obj = pp.get(i);
						System.out.println("��.���� y�� : " + (double) obj);
					}

					for (int i = 0; i < res.size(); i++) {
						Object obj = res.get(i);
						System.out.println("ǥ �� : " + (double) obj);
					}
					
					//�ּҰ� ���ϱ�
					for(int i=0; i<vv.size();i++) {
						for(int j=0; j<pp.size();j++) {
							if(vv.get(i)>pp.get(j))
								min.add(pp.get(j));
							else
								min.add(vv.get(i));
						}
					}

					for (int i = 0; i < min.size(); i++) {
						Object obj = min.get(i);
						minsum+=min.get(i);
						System.out.println("�ּ� �� : " + (double) obj);
					}
					
					//�������
					for(int i=0; i<res.size();i++) {
						sum+=res.get(i)*min.get(i);
					}
					
					//fuzzy=sum/minsum;
					fuzzy = (int) ((sum/minsum) * 100) / 100.0;
					System.out.println(sum);	
					System.out.println(fuzzy);	
					
					//fuzzy db�� ����
					Statement statement1_f = conn.createStatement();
					statement1_f.executeUpdate("UPDATE MORPHRESULT SET fuzzy = " + fuzzy + " WHERE mresult='" + rs_f.getString("mresult") + "'");
				}
			}	
			//������ ������
			//////////////////////////////////////////////////////////////////////////////////////////////////////
			request.setAttribute("emotions", emotions);

			response.setContentType("text/html; charset=UTF-8");
			 RequestDispatcher rd = request.getRequestDispatcher("/stt/finalresult.jsp");
             rd.include(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
	}
	
	public Vector<Double> getFResult(double pleasant, double vitalization) {
		Vector<Double> ff = new Vector<Double>(); // ������Ʈ�� ������ �� �� �ִ� ���� (����
													// �ܾ��� ����)

		// ǥ���� �ش��ϴ� �� fresult ������
		// ��.���� �Լ� �׷������� �ش��ϴ� fresult ã�Ƽ� ���� -> 2�� ���ü���

		if ((pleasant >= 1.00 && pleasant < 4.00) && (vitalization >= 1.00 && vitalization < 5.00)) // ����&����
																									// ǥ��
			ff.add(5.50); // ����
		if ((pleasant >= 2.50 && pleasant < 5.50) && (vitalization >= 1.00 && vitalization < 5.00)) // ����
																									// ����&����
																									// ǥ��
			ff.add(4.00); // ����
		if ((pleasant >= 4.00 && pleasant < 7.00) && (vitalization >= 1.00 && vitalization < 5.00)) // ����
																									// ��&����
																									// ǥ��
			ff.add(4.00); // ����
		if ((pleasant >= 5.50 && pleasant <= 7.00) && (vitalization >= 1.00 && vitalization < 5.00)) // ��&����
																										// ǥ��
			ff.add(4.00); // ����

		if ((pleasant >= 1.00 && pleasant < 4.00) && (vitalization >= 3.00 && vitalization < 7.00)) // ����&����
			ff.add(7.00); // �ſ� ����
		if ((pleasant >= 2.50 && pleasant < 5.50) && (vitalization >= 3.00 && vitalization < 7.00)) // ����
																									// ����&����
			ff.add(5.50); // ����
		if ((pleasant >= 4.00 && pleasant < 7.00) && (vitalization >= 3.00 && vitalization < 7.00)) // ����
																									// ��&����
			ff.add(4.00); // ����
		if ((pleasant >= 5.50 && pleasant <= 7.00) && (vitalization >= 3.00 && vitalization < 7.00)) // ��&����
			ff.add(2.50); // ����

		if ((pleasant >= 1.00 && pleasant < 4.00) && (vitalization >= 5.00 && vitalization <= 7.00)) // ����&����
																										// ǥ��
			ff.add(7.00); // �ſ� ����
		if ((pleasant >= 2.50 && pleasant < 5.50) && (vitalization >= 5.00 && vitalization <= 7.00)) // ����
																										// ����&����
																										// ǥ��
			ff.add(5.50); // ����
		if ((pleasant >= 4.00 && pleasant < 7.00) && (vitalization >= 5.00 && vitalization <= 7.00)) // ����
																										// ��&����
																										// ǥ��
			ff.add(4.00); // ����
		if ((pleasant >= 5.50 && pleasant <= 7.00) && (vitalization >= 5.00 && vitalization <= 7.00)) // ��&����
																										// ǥ��
			ff.add(2.50); // ����

		return ff;
	}

	static public String defaultString(String str) {
		if (str != null && str.length() > 0) {
		} else {
			str = " ";
		}
		return str;
	}

	static public double defaultDouble(String str) {
		if (str != null && str.length() > 0) {
		} else {
			str = "";
		}
		return Double.parseDouble(str);
	}
}
