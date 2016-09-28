package app;

import java.sql.SQLException;

public class MainClass {
	
	
	public static void main (String [] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{ 
		
		final OJDBC dB = new OJDBC();
		
		int[] hist = new int[4];
		
		 hist[0] = 98;
		 hist[1] = 12;
		 hist[2] = 98;
		 hist[3] = 12;
			
		//dB.addImageData(3, 4, 6, 100, 0, hist);
		dB.batacharya(1, 4, hist);
	}
}
