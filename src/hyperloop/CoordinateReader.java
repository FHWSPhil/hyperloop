package hyperloop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CoordinateReader {
	public static void readCoordinates(String fileName) throws IOException {
		File file = new File(fileName);
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String line;
		while((line = br.readLine()) != null) {
			System.out.println(line);
		}
		
		br.close();
	}
	
	//for testing
	public static void main(String[] args) throws IOException {
		readCoordinates("vbb_neo4j.csv");
	}
}
