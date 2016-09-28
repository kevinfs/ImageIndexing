package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	
	public void batacharya(int nbTotal, int sizeTab, int[] tab) {
		try {
			
			String valTab = "";
			for(int i = 0; i < sizeTab-1; i++){
				valTab += tab[i]+",";
			}
			valTab += tab[sizeTab-1];
			System.out.println(valTab);
     		String insertQuery = "set serveroutput on; \n"+
     		"declare \n"+ 
     		"i ordsys.ordimage; \n"+
     		"ctx RAW(400) := NULL; \n"+
     		"ligne multimedia%ROWTYPE; \n"+
     		"cursor mm is select * from multimedia for update; \n"+
     		"sig1 ordsys.ordimageSignature; \n"+
     		"sig2 ordsys.ordimageSignature; \n"+
     		"sim integer; \n"+
     		"sumh1 double precision := 0; \n"+
     		"sumh2 double precision := 0; \n" +
     		"multsumh1h2 double precision := 0; \n"+
     		"BC double precision := 0; \n"+
     		"resultat double precision := 0; \n"+
     		"s integer := ?; \n"+
     		"TYPE TABLEAU IS VARRAY(?) OF INTEGER; \n"+
     		"tab TABLEAU; \n"+
     		"tab2 TABLEAU; \n"+
     		"nbTotal integer := ?; \n\n"+
  
			"begin \n\n"+

			"tab:= TABLEAU(?); \n"+
			"tab2:= TABLEAU(?); \n\n"+

			"for it in 1..s loop \n"+
			"	sumh1 := sumh1 + tab(it)/nbTotal; \n"+
			"	sumh2 := sumh2 + tab2(it)/nbTotal; \n"+
			"end loop; \n\n"+

			"multsumh1h2 := sumh1*sumh2; \n\n"+

			"for it in 1..s loop \n"+
			"	BC := BC + SQRT( (tab(it)/nbTotal * tab2(it)/nbTotal) / multsumh1h2); \n"+
			"end loop; \n\n"+

     		"resultat := -LN(BC); \n";   		
System.out.println(insertQuery);
			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
			
			/*preparedStatement.executeUpdate();

			preparedStatement.close();*/
			
		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}
	}
	

}