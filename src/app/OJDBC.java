package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.Datum;

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

	public void addImageData(String filename, double txR, double txG,
			double txB, int nbPixels, int color, Integer[] hist) {
		try {
			ArrayDescriptor arrayDescriptor = ArrayDescriptor.createDescriptor(
					"HISTOGRAMM", connection);

			ARRAY array = new ARRAY(arrayDescriptor, connection, hist);

			String insertQuery = "UPDATE MULTIMEDIA SET hist = ?, TXR = " + txR
					+ ", TXG = " + txG + ",TXB = " + txB + " , NBPIXELS = "
					+ nbPixels + ", COLOR =" + color + "  WHERE nom = '"
					+ filename + "' ";

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

			String insertQuery = "begin batacharyaHistProc( ?, ?, ?); end; ";
			OracleCallableStatement preparedStatement = (OracleCallableStatement) connection
					.prepareCall(insertQuery);

			preparedStatement.registerOutParameter(1, OracleTypes.ARRAY,
					"BATRESULT");
			preparedStatement.setString(2, name);
			preparedStatement.setDouble(3, seuil);

			preparedStatement.execute();
			ARRAY array_to_pass = preparedStatement.getARRAY(1);
			Datum[] elements = array_to_pass.getOracleArray();

			for (int i = 0; i < elements.length; i++) {
				al.add(elements[i].stringValue());
				System.out.println("function "+elements[i].stringValue());
			}

			preparedStatement.close();

		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}

		return al;
	}

	public ArrayList<String> bhattacharyaTx(String name, double seuil) {

		ArrayList<String> al = new ArrayList<String>();

		try {

			String insertQuery = "begin batacharyaTxProc(?, ?, ?); end; ";
			OracleCallableStatement preparedStatement = (OracleCallableStatement) connection
					.prepareCall(insertQuery);

			preparedStatement.registerOutParameter(1, OracleTypes.ARRAY,
					"BATRESULT");
			preparedStatement.setString(2, name);
			preparedStatement.setDouble(3, seuil);

			preparedStatement.execute();
			ARRAY array_to_pass = preparedStatement.getARRAY(1);
			Datum[] elements = array_to_pass.getOracleArray();

			for (int i = 0; i < elements.length; i++) {
				al.add(elements[i].stringValue());
			}

			preparedStatement.close();

		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}

		return al;
	}

	public ArrayList<String> simpleDistNbPixelsShape(String name, double seuil) {

		ArrayList<String> al = new ArrayList<String>();

		try {

			String insertQuery = "begin NBPIXELSLoop( ?, ?, ?); end; ";
			OracleCallableStatement preparedStatement = (OracleCallableStatement) connection
					.prepareCall(insertQuery);

			preparedStatement.registerOutParameter(1, OracleTypes.ARRAY,
					"SIGRESULT");
			preparedStatement.setString(2, name);
			preparedStatement.setDouble(3, seuil);

			preparedStatement.execute();
			ARRAY array_to_pass = preparedStatement.getARRAY(1);
			Datum[] elements = array_to_pass.getOracleArray();

			for (int i = 0; i < elements.length; i++) {
				al.add(elements[i].stringValue());
			}

			preparedStatement.close();

		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}

		return al;
	}

	public ArrayList<String> signature(String name, double color,
			double texture, double shape, double locationn, double seuil) {

		ArrayList<String> al = new ArrayList<String>();

		try {

			String insertQuery = "begin SigLoop(?, ?, ?, ?, ?, ?, ?); end; ";
			OracleCallableStatement preparedStatement = (OracleCallableStatement) connection
					.prepareCall(insertQuery);

			preparedStatement.registerOutParameter(1, OracleTypes.ARRAY,
					"SIGRESULT");
			preparedStatement.setString(2, name);
			preparedStatement.setDouble(3, color);
			preparedStatement.setDouble(4, texture);
			preparedStatement.setDouble(5, shape);
			preparedStatement.setDouble(6, locationn);
			preparedStatement.setDouble(7, seuil);

			preparedStatement.execute();
			ARRAY array_to_pass = preparedStatement.getARRAY(1);
			Datum[] elements = array_to_pass.getOracleArray();

			for (int i = 0; i < elements.length; i++) {
				al.add(elements[i].stringValue());
			}

			preparedStatement.close();

		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}

		return al;
	}

}