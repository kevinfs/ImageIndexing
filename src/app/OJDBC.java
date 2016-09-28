package app;

import java.sql.Connection;
import java.sql.DriverManager;

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
	
	/*
	private void createClients(StatisticManager statisticManager, int idEntry) {
		List<AbstractClient> servedClients = statisticManager.getServedClients();

		for (AbstractClient client : servedClients){
			addClient(idEntry, client, true);
		}
		
		List<AbstractClient> nonServedClients = statisticManager.getNonServedClients();
		
		for (AbstractClient client : nonServedClients){
			addClient(idEntry, client, false);
		}

	}

	private void addClient(int idEntry, AbstractClient client, boolean isServed) {
		try {

			String insertAddressQuery = "INSERT INTO client (arrival_time, service_start_time, departure_time, is_served, priority, entry_id) VALUES (?,?,?,?,?,?)";

			PreparedStatement preparedStatement = connection.prepareStatement(insertAddressQuery);

			preparedStatement.setInt(1, client.getArrivalTime());
			preparedStatement.setInt(2, client.getServiceStartTime());
			preparedStatement.setInt(3, client.getDepartureTime());
			preparedStatement.setBoolean(4, isServed);
			preparedStatement.setBoolean(5, client.isPriority());
			preparedStatement.setInt(6, idEntry);

			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}
	}
	
	public int servedClientCount(int simulationEntryId) {
		int count = 0;
		try {
			String request = "SELECT count(*) AS count FROM client AS c WHERE c.entry_id = ? AND c.is_served = true";
			PreparedStatement preparedStatement = connection.prepareStatement(request);
			preparedStatement.setInt(1, simulationEntryId);
			ResultSet result = preparedStatement.executeQuery();
			result.next();
			count = result.getInt("count");
			preparedStatement.close();

		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}
		return count;
	}
	
	*/

}