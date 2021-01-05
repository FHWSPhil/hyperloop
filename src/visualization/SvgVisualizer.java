package visualization;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import coordinate.Coordinate;
import coordinate.CoordinateReader;

public class SvgVisualizer {
	
	public static void visualizeWithHTML(Coordinate start, Coordinate end, List<Coordinate> coordinates) throws IOException {
		
		String svgFile = "Svg_Visualization.html";
		BufferedWriter bw = new BufferedWriter(new FileWriter(svgFile));
			
		bw.write("<html> \n <body> \n");
		
		bw.write("<svg width=\"5000\" height=\"5000\" xmlns=\"http://www.w3.org/2000/svg\" viewBox= \"0 0 10000 10000\" style=\"transform: scale(1,-1)\">\n");
		
		//Triangle as marker of Coordinate-Axis-End
		bw.write("<marker id=\"triangle\" viewBox=\"0 0 10 10\" refX=\"0\" refY=\"5\" markerUnits=\"strokeWidth\" markerWidth=\"4\" markerHeight=\"3\" orient=\"auto\">\r\n"
				+ " <path d=\"M 0 0 L 10 5 L 0 10 z\"/>\r\n"
				+ " </marker>");
		
		//xAxis
		bw.write("<line x1=\"0\" y1=\"0\" x2=\"9940\" y2=\"0\" marker-end=\"url(#triangle)\" stroke=\"black\" stroke-width=\"20\"/>\n");
		//Description of x-Axis
		bw.write("<circle cx=\"10000\" cy=\"0\" r=\"40\" stroke=\"black\" stroke-width=\"20\" fill=\"none\" />\n");
		bw.write("<text x=\"9300\" y=\"-20\" font-size=\"100\" transform=\"scale(1, -1)\" > Lon: " + 53 + " Lat: " + 13 + "</text>\n");
		
		//yAxis
		bw.write("<line x1=\"0\" y1=\"0\" x2=\"0\" y2=\"9940\" marker-end=\"url(#triangle)\" stroke=\"black\" stroke-width=\"20\"/>\n");
		//Description of y-Axis
		bw.write("<circle cx=\"0\" cy=\"10000\" r=\"40\" stroke=\"black\" stroke-width=\"20\" fill=\"none\" />\n");
		bw.write("<text x=\"40\" y=\"-9900\" font-size=\"100\" transform=\"scale(1, -1)\" > Lon: " + 52 + " Lat: " + 14 + "</text>\n");
		
		//Description of 0-Point
		bw.write("<circle cx=\"0\" cy=\"0\" r=\"40\" stroke=\"black\" stroke-width=\"20\" fill=\"none\" />\n");
		bw.write("<text x=\"40\" y=\"-20\" font-size=\"100\" transform=\"scale(1, -1)\" > Lon: " + 52 + " Lat: " + 13 + "</text>\n");
		
		//ledger lines for coordinate system
		for(int i = 100; i <= 10000; i+=100) {
			bw.write("<line x1=\"" + i + "\" y1=\"0\" x2=\"" + i + "\" y2=\"10000\" stroke=\"black\" stroke-width=\"1\" stroke-dasharray=\"5\"/>\n");
			bw.write("<line x1=\"0\" y1=\"" + i + "\" x2=\"10000\" y2=\"" + i + "\" stroke=\"black\" stroke-width=\"1\" stroke-dasharray=\"5\"/>\n");
		}
		
		

		//Stations
		for(Coordinate coordinate : coordinates) {
			int x = (int) ((coordinate.getX()-52)*10000);
			int y = (int) ((coordinate.getY()-13)*10000);
			
			bw.write("<g id=\"" + coordinate.getId() + "\">\n");
			bw.write("<circle cx=\"" + x + "\" cy=\"" + y + "\" r=\"10\" stroke=\"black\" stroke-width=\"1\" fill=\"yellow\" /> \n");
			
			bw.write("<text x=\"" + x + "\" y=\"" + -(y-10) + "\" transform=\"scale(1, -1)\" "
					+ "font-size=\"6\" text-anchor=\"middle\">" + coordinate.getStationName() + "</text>\n");
			bw.write("</g>\n");
		}
		
		
		//Hyperloop-Path
		bw.write("<line x1=\"" + start.getX() + "\" y1=\"" + start.getY() //
				+ "\" x2=\"" + end.getX() + "\" y2=\"" + end.getY() //
				+ "\" style=\"stroke:rgb(255,0,0);stroke-width:2\" /> \n");
		
		bw.write("</svg> \n </body> \n </html>");
		
		bw.close();
	}
	
	//testing
	public static void main(String[] args) throws IOException {
		Coordinate start = new Coordinate(1, "StartStation", "StartDistrict", 0, 0);
		Coordinate end = new Coordinate(2, "End", "EndDistrict", 10000, 10000);
		
		List<Coordinate> coordinates = CoordinateReader.readCoordinates("vbb_neo4j.csv");
		
		visualizeWithHTML(start, end, coordinates);
	}
}
