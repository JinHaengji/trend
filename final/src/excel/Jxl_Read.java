package excel;

import java.io.*; // ���� �Է� ���� Ŭ����
import jxl.*; // jxl ���� Ŭ����
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
		// �̹����� ���ϰ� ���� ���� �Է� ���� ���� ó��

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
	    	  System.out.println("����̹� ���� ����");       
	      }

		Workbook myWorkbook = Workbook.getWorkbook(new File("db2.xls" + "")); // ������
																				// ����
		Sheet mySheet = myWorkbook.getSheet(0); // ��Ʈ�� �Է� ����

		// System.out.print("�й�t����t���tn"); // ���� ����

		for (int no = 0; no < 532; no++) { // ���� ���� ��ŭ ������
			for (int i = 0; i < 5; i++) { // ���� ���� ��ŭ ������
				Cell myCell = mySheet.getCell(i, no); // ���� ��� ���� ������ ������
				
				  if(i==0) emotionword = defaultString(myCell.getContents());
				  if(i==1) prototypicality = defaultDouble(myCell.getContents());
				  if(i==2) familiarity = defaultDouble(myCell.getContents());
				  if(i==3) vitalization = defaultDouble(myCell.getContents()); 
				  if(i==4) pleasant = defaultDouble(myCell.getContents());
				System.out.print(myCell.getContents() + "  "); // ���� �Ÿ� ��ŭ ���� ����
				// getContents()�޼ҵ忡 ����

				// Quick and dirty function to return the contents of this cell
				// as a string. �̶�� API�� ���� �ִ�����.
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
			System.out.println(); // ���� �ٲ� �� ���� �����Ͽ� ���
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