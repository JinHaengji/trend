package excel;

import java.io.*; // 파일 입력 관련 클래스
import jxl.*; // jxl 관련 클래스
import java.sql.*;
import java.util.*;
import java.sql.*;
import java.util.*;
import java.sql.*;
import java.util.*;

public class Jxl_Read {
	int id;
	static String emotionword = null;
	static double prototypicality;
	static double familiarity;
	static double vitalization;
	static double pleasant;

	public static void main(String args[]) throws FileNotFoundException, IOException, jxl.read.biff.BiffException {
		// 이번에도 파일과 엑셀 파일 입력 관련 예외 처리

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String insert_sql = "";
		String delete_sql = "";
		String update_sql = "";
		
		 try {	         	
	         Class.forName("com.mysql.jdbc.Driver");
	         conn = DriverManager.getConnection("jdbc:mysql://localhost/resultdb?useUnicode=true&characterEncoding=UTF-8", "sttresult", "sttresult"); 
	         stmt = conn.createStatement();
	    } catch (Exception e) {
	    	  System.out.println("드라이버 연결 실패");       
	      }

		Workbook myWorkbook = Workbook.getWorkbook(new File("db2.xls" + "")); // 파일을
																				// 읽음
		Sheet mySheet = myWorkbook.getSheet(0); // 시트를 입력 받음

		// System.out.print("학번t성명t비고tn"); // 엑셀 제목

		for (int no = 0; no < 532; no++) { // 행의 갯수 만큼 돌리고
			for (int i = 0; i < 5; i++) { // 열의 갯수 만큼 돌려서
				Cell myCell = mySheet.getCell(i, no); // 셀의 행과 열의 정보를 가져옴
				
				  if(i==0) emotionword = defaultString(myCell.getContents());
				  if(i==1) prototypicality = defaultDouble(myCell.getContents());
				  if(i==2) familiarity = defaultDouble(myCell.getContents());
				  if(i==3) vitalization = defaultDouble(myCell.getContents()); 
				  if(i==4) pleasant = defaultDouble(myCell.getContents());
				System.out.print(myCell.getContents() + "  "); // 텝의 거리 만큼 열을 나열
				// getContents()메소드에 대해

				// Quick and dirty function to return the contents of this cell
				// as a string. 이라고 API에 쓰여 있더군요.
			}
			  insert_sql = "INSERT INTO emotiondic(emotionword,prototypicality,familiarity,vitalization,pleasant) values('"
				         + emotionword + "',"
				         + prototypicality + ","
				         + familiarity + ","
				         + vitalization + ","
				         + pleasant + ");";
			  
			  try {
				stmt.executeUpdate(insert_sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(); // 행이 바뀔 때 마다 개행하여 출력
		}		
	}
	
	 static public String defaultString(String str){
		   if(str != null && str.length() > 0){}
		   else{
		     str=" ";
		   }
		   return str;
		  }
	 
	 static public double defaultDouble(String str){
		   if(str != null && str.length()>0){		  
		   }
		   else{
		     str="";
		   }
		   return Double.parseDouble(str);
		  }
}