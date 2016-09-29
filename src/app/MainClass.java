package app;

import java.sql.SQLException;

public class MainClass {
	
	
	public static void main (String [] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{ 
		
		final OJDBC dB = new OJDBC();
		dB.bhattacharya("1.jpg", 0.0005);
	}
}
