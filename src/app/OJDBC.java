package app;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

public class OJDBC {

	
	private static String host = "localhost";
	private static String user = "system";
	private static String password = "oracle";
	private static String url = "jdbc:oracle:thin:@"+host+":1521:orcl12c";

	/**
	 * Lazy singleton instance.
	 */
	private Connection connection;
	
	public OJDBC() {
		prepareConnection();
	}

	private void prepareConnection() {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(url, user, password);
			} catch (Exception e) {
				System.err.println("Connection failed : " + e.getMessage());
			}
		}
	}
	
	

	public void addImageData(int txR, int txG, int txB, int nbPixels, int color, int[] hist) {
		try {
			ArrayDescriptor arrayDescriptor = ArrayDescriptor.createDescriptor("HISTOGRAMM", connection);
					   
		    ARRAY array = new ARRAY(arrayDescriptor, connection, hist);
					   
     		String insertQuery = "UPDATE MULTIMEDIA SET hist = ?, TXR = " +txR+ ", TXG = "+ txG+ ",TXB = "+txB+ " , NBPIXELS = " +nbPixels+ ", COLOR =" + color + "  WHERE nom = '1.jpg' ";
		   
		    
			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setArray(1, array);
			
			preparedStatement.executeUpdate();

			preparedStatement.close();
			
		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}
	}

	public String loadScript(String nameFile) {
		String result = "";
		 try{
			 InputStream ips=new FileInputStream(nameFile); 
			 InputStreamReader ipsr=new InputStreamReader(ips);
			 BufferedReader br=new BufferedReader(ipsr);
			 
			 String ligne;
			 while ((ligne = br.readLine())!=null){
				 result += ligne+"\n";
			 } 
		 
		 }
		catch (IOException ie){
		     ie.printStackTrace(); 
		}
		return result;
	}
	
	public void bhattacharya(String name, double seuil) {
		try {
			
     		String insertQuery = loadScript("utils/sql/bhattacharyaHist.sql");
     		System.out.println(insertQuery);
			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
     		preparedStatement.setString(1, "'"+name+"'");
			preparedStatement.setDouble(2, seuil);	
			
			ResultSet rs =  preparedStatement.executeQuery();
			
			/*ResultSet rs =  preparedStatement.executeQuery();
			while (rs.next()) {
				 System.out.println("ici"); 
			}*/
			preparedStatement.close();
			
		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}
	}
	
	public void signature(String name, int color, int texture, int shape, int locationn, int seuil) {
		try {
			
     		String insertQuery = ""
     				+ "set serveroutput on; \n\n"

					+"CREATE OR REPLACE TYPE SIGRESULT IS TABLE OF VARCHAR2(30) ; \n"
					+"/\n"
					+"CREATE OR REPLACE Function SigLoop( nameInput IN varchar2, color IN integer, texture IN integer, shape IN integer, locationn IN integer, seuil IN integer) RETURN SIGRESULT\n"
					+"IS\n\n"
					
					  +"c1result MULTIMEDIA%ROWTYPE;\n"
					  +"sim integer;\n"
					  +"sig ordsys.ordimageSignature;\n"
					 +" indexx integer := 0;\n"
					  +"batresult_tab SIGRESULT := SIGRESULT(); \n\n"
					  
					  +"CURSOR c1\n"
					    +"IS\n"
					     +"SELECT *\n"
					     +"FROM MULTIMEDIA\n"
					     +"WHERE nom not like nameInput;\n\n"
					     
					     
					  +"BEGIN\n\n"
					  
					    +"select signature into sig from multimedia where nom = nameInput; \n"
					    +"open c1;\n\n"
					    
					    +"loop\n\n"
					    
					      +"fetch c1 into c1result;\n"
					        +"sim := ordsys.ordimageSignature.isSimilar(sig, c1result.signature, 'color =  ' || color || ', texture =' || texture || ', shape = ' || shape || ', location = ' || locationn, seuil);\n"
					        +"if sim = 1 then\n"
					          +"batresult_tab.EXTEND (1);\n"
					          +"indexx := indexx + 1;\n"
					          +"batresult_tab(indexx) := c1result.nom;\n\n"
					          
					        +"end if;\n"
					     +" exit when c1%notfound;\n\n"
					    
					    +"end loop;\n\n"
					    
					    +"return batresult_tab;\n"
					
					+"END;\n"
					+"/\n\n"
					
					+"SELECT * FROM table( SigLoop('1.jpg', ?, ?, ?, ?, ?)); ";
     		
			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setInt(1, color);
			preparedStatement.setInt(2, texture);
			preparedStatement.setInt(3, shape);
			preparedStatement.setInt(4, locationn);
			preparedStatement.setInt(5, seuil);
     		
			ResultSet rs =  preparedStatement.executeQuery();
			while (rs.next()) {
				  
			}
			preparedStatement.close();
			
		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}
	}
	

}