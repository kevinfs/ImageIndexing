package app;

import java.sql.SQLException;

public class MainClass {
	
	
	public static void main (String [] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{ 
		
		final OJDBC dB = new OJDBC();
		
		int[] hist = new int[256];
		
		for(int i=0; i<256; i++) hist[i] = i;
			
		dB.addImageData(3, 4, 6, 100, 0, hist);
		
	}
}
