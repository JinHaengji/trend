package speech;

import jxl.*;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
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
	static String mresult = null;
	static double prototypicality;
	static double familiarity;
	static double vitalization;
	static double pleasant;
	static String type = null;

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
			Workbook myWorkbook = Workbook.getWorkbook(new File("D:\\졸업작품\\db2.xls"));
			Sheet mySheet = myWorkbook.getSheet(0); // 시트를 입력 받음

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

			// select id,mresult from morphresult where type = 'N' or type =
			// 'V'; ???
			// type이 v와 n인 결과값만 가져오기
			rs = stmt.executeQuery("SELECT id,MRESULT,TYPE FROM MORPHRESULT WHERE TYPE='V' OR TYPE='N'");
			response.setContentType("text/html; charset=UTF-8");

			ArrayList<Exceldb> emotions = new ArrayList<Exceldb>();

			while (rs.next()) {
				emotions.add(new Exceldb().setid(rs.getInt("id")).setmresult(rs.getString("mresult"))
						.settype(rs.getString("type")));

				for (int no = 0; no < 434; no++) { // 행의 갯수 만큼 돌리고
					for (int i = 0; i < 5; i++) { // 열의 갯수 만큼 돌려서
						Cell myCell = mySheet.getCell(i, no); // 셀의 행과 열의 정보를
																// 가져옴
						if (i == 0)
							mresult = defaultString(myCell.getContents());
						if (i == 1)
							prototypicality = defaultDouble(myCell.getContents());
						if (i == 2)
							familiarity = defaultDouble(myCell.getContents());
						if (i == 3)
							vitalization = defaultDouble(myCell.getContents());
						if (i == 4)
							pleasant = defaultDouble(myCell.getContents());
					
						if ((rs.getString("type").equals("V")) || (rs.getString("type").equals("N"))) {

							if (mresult.contains(rs.getString("mresult"))) { // 감정사전에
																				// 포함되어
								Statement statement = conn.createStatement();								// 있으면
								// emotions.add(new
								// Exceldb().setprototypicality(prototypicality).setfamiliarity(familiarity)
								// .setvitalization(vitalization).setpleasant(pleasant));
								System.out.println(mresult + " 포함  " + rs.getString("mresult"));
								//statement.executeUpdate("INSERT INTO MORPHRESULT(id,prototypicality,familiarity,vitalization,pleasant) VALUES ("+rs.getInt("id")+","+prototypicality+","+familiarity+","+vitalization+","+pleasant+")");
								statement.executeUpdate("UPDATE MORPHRESULT SET prototypicality="+prototypicality+", familiarity="+familiarity+", vitalization="+vitalization+", pleasant=" +pleasant);
								// stmt = null;
							} }else { // 포함되어있지 않으면
								Statement statement2 = conn.createStatement();	
								// emotions.add(new
								// Exceldb().setprototypicality(prototypicality).setfamiliarity(familiarity)
								// .setvitalization(vitalization).setpleasant(pleasant));
							
								statement2.executeUpdate("UPDATE MORPHRESULT SET prototypicality=0, familiarity=0, vitalization=0, pleasant=0");
								// stmt = null;
								//System.out.println("포함x" + rs.getString("mresult"));
							}
						
						// getContents()메소드에 대해
					}
						// Quick and dirty function to return the contents of
						// this cell
						// as a string. 이라고 API에 쓰여 있더군요.
					
				}
				// System.out.println(emotions);
			}

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
