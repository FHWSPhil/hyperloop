package coordinate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CoordinateReader {
	public static List<Coordinate> readCoordinates(String fileName) throws IOException {
		List<Coordinate> coordinates = new LinkedList<>();
		
		File file = new File(fileName);
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String line = br.readLine(); //read first line to skip it
		while((line = br.readLine()) != null) {
			
			//formatting read line to match String splitter
			String formatedLine = line.replaceAll("\",\"", ",");
			formatedLine = formatedLine.substring(1, formatedLine.length() - 1);
			
			//splitting formatted String and paste into coordinate
			String[] splittedString = formatedLine.split(",");
			long id = Long.valueOf(splittedString[0]);
			String stationName = splittedString[1];
			String district = splittedString[2];
			double x = Double.valueOf(splittedString[3]);
			double y = Double.valueOf(splittedString[4]);
			
			Coordinate coordinate = new Coordinate(id, stationName, district, x, y);
			coordinates.add(coordinate);
		}
		
		br.close();
		
		return coordinates;
	}
	
	//for testing
	public static void main(String[] args) throws IOException {
		List<Coordinate> coordinates = readCoordinates("vbb_neo4j.csv");
		
		for(Coordinate coordinate : coordinates) {
			System.out.println(coordinate.getStationName() + ": " + coordinate.getX() + " | " + coordinate.getY());
		}
	}
}