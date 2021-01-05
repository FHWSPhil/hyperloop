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
		
//		bw.write("<svg width=\"10000\" height=\"10000\" viewBox= \"0 0 10000 10000\" style=\"transform: scale(1,-1)\">\n");
		bw.write("<svg width=\"10000\" height=\"10000\" viewBox= \"0 0 10000 10000\">\n");
		
		//Schreibe Punkte
		for(Coordinate coordinate : coordinates) {
			int x = (int) ((coordinate.getX()-52)*10000);//-3300;
			int y = (int) ((coordinate.getY()-13)*10000);//-800;
//			System.out.println("x: " + x);
//			System.out.println("y: " + y);
			
			bw.write("<g id=\"" + coordinate.getId() + "\">\n");
			bw.write("<circle cx=\"" + x + "\" cy=\"" + y + "\" r=\"10\" stroke=\"black\" stroke-width=\"1\" fill=\"yellow\" /> \n");
			
//			bw.write("<g transform=\"translate(0, 10000) scale(1, -1) rotate(-90)\">\n");
			bw.write("<text x=\"" + x + "\" y=\"" + (y+2) + "\" "
					+ "font-size=\"6\" text-anchor=\"middle\">" + coordinate.getStationName() + "</text>\n");
//			bw.write("</g>\n");
			bw.write("</g>\n");
		}
		
		//Schreibe Linie
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
