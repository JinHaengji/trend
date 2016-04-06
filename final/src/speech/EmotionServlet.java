package speech;

import java.io.FileNotFoundException;
import java.io.IOException;
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

import vo.Exceldb;

@WebServlet("/stt/emotion")
public class EmotionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int id;

	static String type = null;

	@SuppressWarnings("resource")
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, FileNotFoundException {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		request.setCharacterEncoding("UTF-8");

		// String[] mresult = request.getParameterValues("mresult");
		// String[] quantity = request.getParameterValues("bookonum");
		// System.out.println("quantity"+quantity.length);

		try {
			// D:\\졸작\\ref\\db2.xls
			///////////////////////// 엑셀 설정//////////////////////////////
			// Workbook myWorkbook = Workbook.getWorkbook(new
			// File("D:\\졸작\\ref\\db2.xls")); // 파일을
			// 읽음
			// Workbook myWorkbook = Workbook.getWorkbook(new
			// File("D:\\졸업작품\\db2.xls"));
			// Sheet mySheet = myWorkbook.getSheet(0); // 시트를 입력 받음

			//////////////////////////////////////////////////////////////

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

			// 모든 저장된 값
			rs = stmt.executeQuery("SELECT id,MRESULT,TYPE,prototypicality FROM MORPHRESULT");
			response.setContentType("text/html; charset=UTF-8");

			// type이 v와 n인 결과값만 가져오기
			// ResultSet rs3 = null;
			// Statement statement3 = conn.createStatement();
			// rs3 = statement3.executeQuery("SELECT id,MRESULT,TYPE FROM
			// MORPHRESULT WHERE TYPE='V' OR TYPE='N'");
			// 섭섭

			ArrayList<Exceldb> emotions = new ArrayList<Exceldb>();

			while (rs.next()) { // 섭섭 , 하, 어다, 걱정, 되, ㅂ니다
				////////////////////////////////////////////////////////////////////////////////////////////////////
				//단어랑 type이 찍힘
				System.out.println(rs.getString("mresult") + "  " + rs.getString("TYPE"));

				//단어가 두글자 이상이고, 타입이  v,n이면
				if (((rs.getString("TYPE").equals("V")) || (rs.getString("TYPE").equals("N")))&&(rs.getString("mresult").length() >= 2)) { 
					System.out.println("포함 하는 " + rs.getString("mresult"));
					ResultSet rs2 = null;
					Statement statement = conn.createStatement();
					//섭섭하다, 걱정되다
					/*rs2 = statement.executeQuery(
							"SELECT emotionword, prototypicality, familiarity, vitalization, pleasant FROM emotiondic WHERE emotionword like '"
									+ rs.getString("mresult") + "%'");*/
					rs2 = statement.executeQuery(
							"SELECT emotionword, prototypicality, familiarity, vitalization, pleasant FROM emotiondic");
					
					//감정사전 단어 다 가져오기
					while (rs2.next()) { 
						Statement statement1 = conn.createStatement();
						 
						//감정사전에 포함되면
							if ((rs2.getString("emotionword").contains(rs.getString("mresult"))||rs.getString("mresult").contains(rs2.getString("emotionword")))
									&& (rs2.getString("emotionword").charAt(0) == rs.getString("mresult").charAt(0))
									&& (rs2.getString("emotionword").charAt(1) == rs.getString("mresult").charAt(1))) {

								System.out.println(rs2.getString("emotionword") + "  ..  " + rs.getString("mresult"));
								/*statement1.executeUpdate(
										"UPDATE MORPHRESULT SET prototypicality=" + rs2.getString("prototypicality")
												+ ", familiarity=" + rs2.getString("familiarity") + ", vitalization="
												+ rs2.getString("vitalization") + ", pleasant="
												+ rs2.getString("pleasant") + " WHERE '" + rs2.getString("emotionword")
												+ "' like '" + rs.getString("mresult") + "%'");*/
								statement1.executeUpdate(
										"UPDATE MORPHRESULT SET prototypicality=" + rs2.getString("prototypicality")
												+ ", familiarity=" + rs2.getString("familiarity") + ", vitalization="
												+ rs2.getString("vitalization") + ", pleasant="
												+ rs2.getString("pleasant") + " WHERE mresult='" + rs.getString("mresult") + "'");
								// 섭섭하다 like 섭섭~
							} 
						
					}
				} else {
					// 모든 저장된 값
					System.out.println("포함 하지 않는 " + rs.getString("mresult"));
					Statement statement2 = conn.createStatement();
					statement2.executeUpdate(
							"UPDATE MORPHRESULT SET prototypicality=0, familiarity=0, vitalization=0, pleasant=0 where mresult='"
									+ rs.getString("mresult") + "'");

				}
			}
			/*
			 * if(rs.getDouble("prototypicality")==0) { Statement statement2 =
			 * conn.createStatement();
			 * //System.out.println(rs2.getString("emotionword") + "  ..  " +
			 * rs3.getString("mresult")); statement2.executeUpdate(
			 * "UPDATE MORPHRESULT SET prototypicality=0, familiarity=0, vitalization=0, pleasant=0"
			 * ); }/* // System.out.println(rs.getString("mresult"));
			 * 
			 * /* statement.executeUpdate(
			 * "UPDATE MORPHRESULT SET prototypicality=0, familiarity=0, vitalization=0, pleasant=0"
			 * + " where mresult='" + rs.getString("mresult") + "'");
			 */

			///////////////////////////////////////////////////////////////////////////////////////////////////

			// 감정사전에 없으면

			// System.out.println(emotions);

			request.setAttribute("emotions", emotions);

			response.setContentType("text/html; charset=UTF-8");
			// RequestDispatcher rd =
			// request.getRequestDispatcher("/bookstore/Bookorder.jsp");
			// rd.include(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
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
