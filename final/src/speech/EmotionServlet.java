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
	
	private double pleasant; // 쾌.불쾌 점수
	private double vitalization; // 활성화 점수
	private Vector<Double> fresult = new Vector<Double>(); // 블랙리스트에 영향을 줄 수 있는
															// 정도 (불쾌 단어의 지수)
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
					"sttresult", // DBMS 사용자 아이디
					"sttresult"); // DBMS 사용자 암호
			stmt = conn.createStatement();
			System.out.println("제대로 연결되었습니다.");
			

			////////////////////////////////////////////////////////////////////////////////////////////////////	
			
			// 모든 저장된 값
			rs = stmt.executeQuery("SELECT id,MRESULT,TYPE,prototypicality FROM MORPHRESULT");
			response.setContentType("text/html; charset=UTF-8");

			// 섭섭

			ArrayList<Exceldb> emotions = new ArrayList<Exceldb>();

			while (rs.next()) { // 섭섭 , 하, 어다, 걱정, 되, ㅂ니다
				////////////////////////////////////////////////////////////////////////////////////////////////////
				// 단어랑 type이 찍힘
				System.out.println(rs.getString("mresult") + "  " + rs.getString("TYPE"));

				// 단어가 두글자 이상이고, 타입이 v,n이면
				if (((rs.getString("TYPE").equals("V")) || (rs.getString("TYPE").equals("N")))
						&& (rs.getString("mresult").length() >= 2)) {
					System.out.println("포함 하는 " + rs.getString("mresult"));
					ResultSet rs2 = null;
					Statement statement = conn.createStatement();	
					// 섭섭하다, 걱정되다
					rs2 = statement.executeQuery(
							"SELECT emotionword, prototypicality, familiarity, pleasant, vitalization FROM emotiondic");
					
					//일단 0으로 초기화
					Statement statement2 = conn.createStatement();
					statement2.executeUpdate(
							"UPDATE MORPHRESULT SET prototypicality=0, familiarity=0, pleasant=0 , vitalization=0 where mresult='"
									+ rs.getString("mresult") + "'");
					// 감정사전 단어 다 가져오기
					while (rs2.next()) {
						Statement statement1 = conn.createStatement();
						// 감정사전에 포함되면
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
							// 섭섭하다 like 섭섭~
						}
					}
				} 
				

				else {
					// 모든 저장된 값
					System.out.println("포함 하지 않는 " + rs.getString("mresult"));
					Statement statement2 = conn.createStatement();
					statement2.executeUpdate(
							"UPDATE MORPHRESULT SET prototypicality=0, familiarity=0, pleasant=0, vitalization=0 where mresult='"
									+ rs.getString("mresult") + "'");

				}
			}

			///////////////////////////////////////////////////////////////////////////////////////////////////

			// 감정사전에 없으면

			// 감정 추출한 것에서
			// 현재 고객의 id인것만 가져오기
			PreparedStatement pstmt_f = null;
			String sql_f = String.format("SELECT * FROM MORPHRESULT WHERE id = '" + id + "'");			
			pstmt_f = conn.prepareStatement(sql_f);
			ResultSet rs_f = pstmt_f.executeQuery(); // SELECT 쿼리인 경우;
			
			while (rs_f.next()) {
				Vector<Double> min = new Vector<Double>();

		        minsum=0;
	            sum=0;
	            fuzzy=0;
	            
				// 감정단어인 것만 해당
				if (rs_f.getDouble("pleasant") > 0) {
					pleasant = rs_f.getDouble("pleasant"); // 쾌.불쾌
					vitalization = rs_f.getDouble("vitalization"); // 활성화

					// 행지꺼 먼저
					// 쾌.불쾌 y값 최대 2개, 활성화 y값 최대 2개가 vector로 리턴됨
					pp = fc.funcPlea(pleasant);
					vv = fc.funcVitali(vitalization);

					// 표에서 해당하는 각 fresult들 가져옴
					res = getFResult(pleasant, vitalization);

					// 테스트
					for (int i = 0; i < vv.size(); i++) {
						Object obj = vv.get(i);
						System.out.println("활성화 y값 : " + (double) obj);
					}

					for (int i = 0; i < pp.size(); i++) {
						Object obj = pp.get(i);
						System.out.println("쾌.불쾌 y값 : " + (double) obj);
					}

					for (int i = 0; i < res.size(); i++) {
						Object obj = res.get(i);
						System.out.println("표 값 : " + (double) obj);
					}
					
					//최소값 구하기
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
						System.out.println("최소 값 : " + (double) obj);
					}
					
					//최종계산
					for(int i=0; i<res.size();i++) {
						sum+=res.get(i)*min.get(i);
					}
					
					//fuzzy=sum/minsum;
					fuzzy = (int) ((sum/minsum) * 100) / 100.0;
					System.out.println(sum);	
					System.out.println(fuzzy);	
					
					//fuzzy db에 저장
					Statement statement1_f = conn.createStatement();
					statement1_f.executeUpdate("UPDATE MORPHRESULT SET fuzzy = " + fuzzy + " WHERE mresult='" + rs_f.getString("mresult") + "'");
				}
			}	
			//위에는 퍼지값
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
		Vector<Double> ff = new Vector<Double>(); // 블랙리스트에 영향을 줄 수 있는 정도 (불쾌
													// 단어의 지수)

		// 표에서 해당하는 각 fresult 가져옴
		// 쾌.불쾌 함수 그래프에서 해당하는 fresult 찾아서 저장 -> 2개 나올수도

		if ((pleasant >= 1.00 && pleasant < 4.00) && (vitalization >= 1.00 && vitalization < 5.00)) // 불쾌&적게
																									// 표현
			ff.add(5.50); // 많이
		if ((pleasant >= 2.50 && pleasant < 5.50) && (vitalization >= 1.00 && vitalization < 5.00)) // 조금
																									// 불쾌&적게
																									// 표현
			ff.add(4.00); // 조금
		if ((pleasant >= 4.00 && pleasant < 7.00) && (vitalization >= 1.00 && vitalization < 5.00)) // 조금
																									// 쾌&적게
																									// 표현
			ff.add(4.00); // 조금
		if ((pleasant >= 5.50 && pleasant <= 7.00) && (vitalization >= 1.00 && vitalization < 5.00)) // 쾌&적게
																										// 표현
			ff.add(4.00); // 조금

		if ((pleasant >= 1.00 && pleasant < 4.00) && (vitalization >= 3.00 && vitalization < 7.00)) // 불쾌&보통
			ff.add(7.00); // 매우 많이
		if ((pleasant >= 2.50 && pleasant < 5.50) && (vitalization >= 3.00 && vitalization < 7.00)) // 조금
																									// 불쾌&보통
			ff.add(5.50); // 많이
		if ((pleasant >= 4.00 && pleasant < 7.00) && (vitalization >= 3.00 && vitalization < 7.00)) // 조금
																									// 쾌&보통
			ff.add(4.00); // 조금
		if ((pleasant >= 5.50 && pleasant <= 7.00) && (vitalization >= 3.00 && vitalization < 7.00)) // 쾌&보통
			ff.add(2.50); // 안줌

		if ((pleasant >= 1.00 && pleasant < 4.00) && (vitalization >= 5.00 && vitalization <= 7.00)) // 불쾌&많이
																										// 표현
			ff.add(7.00); // 매우 많이
		if ((pleasant >= 2.50 && pleasant < 5.50) && (vitalization >= 5.00 && vitalization <= 7.00)) // 조금
																										// 불쾌&많이
																										// 표현
			ff.add(5.50); // 많이
		if ((pleasant >= 4.00 && pleasant < 7.00) && (vitalization >= 5.00 && vitalization <= 7.00)) // 조금
																										// 쾌&많이
																										// 표현
			ff.add(4.00); // 조금
		if ((pleasant >= 5.50 && pleasant <= 7.00) && (vitalization >= 5.00 && vitalization <= 7.00)) // 쾌&많이
																										// 표현
			ff.add(2.50); // 안줌

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
