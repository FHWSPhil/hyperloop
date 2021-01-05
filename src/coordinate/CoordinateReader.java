package coordinate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CoordinateReader {
	
	/**
	 * 
	 * @param fileName Must be a .csv-File with the values {ID,"StationName","District","Lat","Long"} in order to be read right.
	 * @return A list of coordinates with the given values.
	 * @throws IOException If .csv-File not found.
	 */
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
	
	//find the lat-Coordinate of all coordinates
	public static double findLat(List<Coordinate> coordinates) {
		if(coordinates.isEmpty()) throw new IllegalStateException("List is Empty!");
		//The program is limited to a List of coordinates that contains the same Latitude and Longitude numbers before the decimal point.
		return (double) ((int) coordinates.get(0).getY());
	}

	//find the lon-Coordinate of all coordinates
	public static double findLon(List<Coordinate> coordinates) {
		if(coordinates.isEmpty()) throw new IllegalStateException("List is Empty!");
		//The program is limited to a List of coordinates that contains the same Latitude and Longitude numbers before the decimal point.
		return (double) ((int) coordinates.get(0).getX());
	}
	
	//for testing
	//TODO Comment this, when project is final.
	public static void main(String[] args) throws IOException {
		List<Coordinate> coordinates = readCoordinates("vbb_neo4j.csv");
		
		for(Coordinate coordinate : coordinates) {
			System.out.println(coordinate.getStationName() + ": " + coordinate.getX() + " | " + coordinate.getY());
		}
	}
}
