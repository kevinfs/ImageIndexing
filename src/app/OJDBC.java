package app;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.Datum;
import oracle.sql.STRUCT;

public class OJDBC {

	private static String host = "localhost";
	private static String user = "system";
	private static String password = "oracle";
	private static String url = "jdbc:oracle:thin:@" + host + ":1521:orcl12c";

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

	public void addImageData(int txR, int txG, int txB, int nbPixels,
			int color, int[] hist) {
		try {
			ArrayDescriptor arrayDescriptor = ArrayDescriptor.createDescriptor(
					"HISTOGRAMM", connection);

			ARRAY array = new ARRAY(arrayDescriptor, connection, hist);

			String insertQuery = "UPDATE MULTIMEDIA SET hist = ?, TXR = " + txR
					+ ", TXG = " + txG + ",TXB = " + txB + " , NBPIXELS = "
					+ nbPixels + ", COLOR =" + color + "  WHERE nom = '1.jpg' ";

			PreparedStatement preparedStatement = connection
					.prepareStatement(insertQuery);
			preparedStatement.setArray(1, array);

			preparedStatement.executeUpdate();

			preparedStatement.close();

		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}
	}
	
	public ArrayList<String> bhattacharyaHist(String name, double seuil) {
		
		ArrayList<String> al = new ArrayList<String>();
		
		try {
<<<<<<< HEAD
			
			String insertQuery = "begin batacharyaHistProc( ?, ?, ?); end; ";
			OracleCallableStatement preparedStatement =  (OracleCallableStatement)connection.prepareCall(insertQuery);
     		
			preparedStatement.registerOutParameter(1, OracleTypes.ARRAY, "BATRESULT");
     		preparedStatement.setString(2, name);
			preparedStatement.setDouble(3, seuil);	
=======
>>>>>>> branch 'master' of https://github.com/kevinfs/ImageIndexing.git

<<<<<<< HEAD
			preparedStatement.execute();
			ARRAY array_to_pass = preparedStatement.getARRAY(1);
			Datum[] elements = array_to_pass.getOracleArray();
			
			for (int i=0;i<elements.length;i++){
				al.add(elements[i].stringValue());
			}
			
			preparedStatement.close();
			
=======
			String insertQuery = "";

			PreparedStatement preparedStatement = connection
					.prepareStatement(insertQuery);

			/*
			 * preparedStatement.executeUpdate();
			 * 
			 * preparedStatement.close();
			 */

>>>>>>> branch 'master' of https://github.com/kevinfs/ImageIndexing.git
		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}
		
		return al;
	}
	
	public ArrayList<String> signature(String name, double color, double texture, double shape, double locationn, double seuil) {
			
			ArrayList<String> al = new ArrayList<String>();
			
			try {
				
				String insertQuery = "begin SigLoop(?, ?, ?, ?, ?, ?, ?); end; ";
				OracleCallableStatement preparedStatement =  (OracleCallableStatement)connection.prepareCall(insertQuery);
	     		
				preparedStatement.registerOutParameter(1, OracleTypes.ARRAY, "SIGRESULT");
	     		preparedStatement.setString(2, name);
				preparedStatement.setDouble(3, color);
				preparedStatement.setDouble(4, texture);
				preparedStatement.setDouble(5, shape);
				preparedStatement.setDouble(6, locationn);
				preparedStatement.setDouble(7, seuil);

				preparedStatement.execute();
				ARRAY array_to_pass = preparedStatement.getARRAY(1);
				Datum[] elements = array_to_pass.getOracleArray();
				
				for (int i=0;i<elements.length;i++){
					al.add(elements[i].stringValue());
				}
				
				preparedStatement.close();
				
			} catch (SQLException se) {
				System.err.println(se.getMessage());
			}
			
			return al;
	}

}