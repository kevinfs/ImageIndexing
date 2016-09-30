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

	public void bhattacharya(String name, int nbTotal, int sizeTab) {
		try {

			String insertQuery = "";

			PreparedStatement preparedStatement = connection
					.prepareStatement(insertQuery);

			/*
			 * preparedStatement.executeUpdate();
			 * 
			 * preparedStatement.close();
			 */

		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}
	}

}