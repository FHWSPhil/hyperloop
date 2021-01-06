package visualization;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import coordinate.Coordinate;
import coordinate.CoordinateReader;
import hyperloop.Line;
import hyperloop.Track;

public class SvgVisualizerWithPrecision {
	/**
	 * The main algorithm to visualize the computed line of the HyperLoop in SVG.
	 * The program is limited to a List of coordinates that contains the same Latitude and Longitude numbers before the decimal point.
	 * The accuracy of the program is @param precision places of the Latitude and Longitude.
	 * Developed for public transport stations of Berlin.
	 * Texts in the SVG have to be transformed because the whole Graph is mirrored on the x-Axis.
	 * @param start StartCoordinate of computed line.
	 * @param end EndCoordinate of computed line.
	 * @param coordinates List of all coordinates.
	 * @param tolerance   to mark Stations within the tolerance.
	 * @param precision The precision of the numbers of decimal digits which have to be considered. Must be set between 4 and 6! Recommended is 4!
	 * @throws IOException
	 */
	public static void visualizeWithHTML(Coordinate start, Coordinate end, List<Coordinate> coordinates,double tolerance , int precision) throws IOException {
		if(precision < 4 || precision > 6) throw new IllegalArgumentException("Precission must be between 4 and 6 to work with Lon. and Lat. correctly!");
		String svgFile = "Svg_Visualization_With_Precision_" + precision + ".html";
		BufferedWriter bw = new BufferedWriter(new FileWriter(svgFile));
		
		precision = (int) Math.pow(10, precision);
		
		double lon = CoordinateReader.findLon(coordinates);
		double lat = CoordinateReader.findLat(coordinates);
			
		//Create html-svg with a good scaling.
		bw.write("<html> \n <body> \n");
		bw.write("<svg width=\"" + precision/2 + "\" height=\"" + precision/2 + "\" xmlns=\"http://www.w3.org/2000/svg\" viewBox= \"0 0 " + precision + " " + precision +"\" style=\"transform: scale(1,-1)\">\n");
		
		//Triangle as marker of Coordinate-Axis-End
		bw.write("<marker id=\"triangle\" viewBox=\"0 0 10 10\" refX=\"0\" refY=\"5\" markerUnits=\"strokeWidth\" markerWidth=\"4\" markerHeight=\"3\" orient=\"auto\">\r\n"
				+ " <path d=\"M 0 0 L 10 5 L 0 10 z\"/>\r\n"
				+ " </marker>");
		
		//xAxis
		bw.write("<line x1=\"0\" y1=\"0\" x2=\"" + scaleNumber(9940, precision) + "\" y2=\"0\" marker-end=\"url(#triangle)\" stroke=\"black\" stroke-width=\"" + 20 + "\"/>\n");
		//Description of x-Axis
		bw.write("<circle cx=\"" + precision + "\" cy=\"0\" r=\"" + scaleNumber(40, precision) + "\" stroke=\"black\" stroke-width=\"" + 20 + "\" fill=\"none\" />\n");
		bw.write("<text x=\"" + scaleNumber(9150, precision) + "\" y=\""+ scaleNumber(-20, precision) +"\" font-size=\"" + 100 + "\" transform=\"scale(1, -1)\" > Lon: " + (lon+1) + " Lat: " + lat + "</text>\n");
		
		//yAxis
		bw.write("<line x1=\"0\" y1=\"0\" x2=\"0\" y2=\"" + scaleNumber(9940, precision) + "\" marker-end=\"url(#triangle)\" stroke=\"black\" stroke-width=\"" + 20 + "\"/>\n");
		//Description of y-Axis
		bw.write("<circle cx=\"0\" cy=\"" + precision + "\" r=\"" + scaleNumber(40, precision) + "\" stroke=\"black\" stroke-width=\"" + 20 + "\" fill=\"none\" />\n");
		bw.write("<text x=\"" + scaleNumber(40, precision) + "\" y=\"" + scaleNumber(-9900, precision) + "\" font-size=\"" + 100 + "\" transform=\"scale(1, -1)\" > Lon: " + lon + " Lat: " + (lat+1) + "</text>\n");
		
		//Description of 0-Point
		bw.write("<circle cx=\"0\" cy=\"0\" r=\"" + scaleNumber(40, precision) + "\" stroke=\"black\" stroke-width=\"" + 20 + "\" fill=\"none\" />\n");
		bw.write("<text x=\"" + scaleNumber(40, precision) + "\" y=\""+scaleNumber(-20, precision)+"\" font-size=\"" + 100 + "\" transform=\"scale(1, -1)\" > Lon: " + lon + " Lat: " + lat + "</text>\n");
		
		//ledger lines for coordinate system
		double infoCounter = getInfoCounter(precision);
		double lonInfo = round(lon + infoCounter, getDecimalDigits(infoCounter));
		double latInfo = round(lat + infoCounter, getDecimalDigits(infoCounter));
		for(int i = 100; i <= precision; i+=100) {

			bw.write("<line x1=\"" + i + "\" y1=\"0\" x2=\"" + i + "\" y2=\"" + precision + "\" stroke=\"black\" stroke-width=\"1\" stroke-dasharray=\"5\"/>\n");
			bw.write("<text x=\"" + (i-15) + "\" y=\"" + (-10) + "\" transform=\"scale(1, -1)\" "
					+ "font-size=\"" + 6 + "\" text-anchor=\"middle\"> Lon: " + lonInfo + "</text>\n");
			lonInfo = round(lonInfo + infoCounter, getDecimalDigits(infoCounter));
			
			bw.write("<line x1=\"0\" y1=\"" + i + "\" x2=\"" + precision + "\" y2=\"" + i + "\" stroke=\"black\" stroke-width=\"1\" stroke-dasharray=\"5\"/>\n");
			bw.write("<text x=\"" + 25 + "\" y=\"" + -(i-5) + "\" transform=\"scale(1, -1)\" "
					+ "font-size=\"" + 6 + "\" text-anchor=\"middle\"> Lat: " + latInfo + "</text>\n");
			latInfo = round(latInfo + infoCounter, getDecimalDigits(infoCounter));
		}
		
		
		Track track = new Track(new Line(start, end));
		
		// Stations
		for (Coordinate coordinate : coordinates) {
			int x = coordinate.scaleXToCoordinateSystem(lon, precision);
			int y = coordinate.scaleYToCoordinateSystem(lat, precision);
			
			if (track.isOnTrack(coordinate, tolerance)) {			
				bw.write("<g id=\"" + coordinate.getId() + "\">\n");
				bw.write("<circle cx=\"" + x + "\" cy=\"" + y
					+ "\" r=\"" + scaleNumber(10, precision) + "\" stroke=\"black\" stroke-width=\"1\" fill=\"red\" /> \n");

				bw.write("<text x=\"" + x + "\" y=\"" + -(y + 10) + "\" transform=\"scale(1, -1)\" "
					+ "font-size=\"" + scaleNumber(6, precision) + "\" text-anchor=\"middle\">" + coordinate.toSvgString(x) + "</text>\n");
				bw.write("</g>\n");			
			} else {
				bw.write("<g id=\"" + coordinate.getId() + "\">\n");
				bw.write("<circle cx=\"" + x + "\" cy=\"" + y
					+ "\" r=\"" + scaleNumber(10, precision) + "\" stroke=\"black\" stroke-width=\"1\" fill=\"yellow\" /> \n");

				bw.write("<text x=\"" + x + "\" y=\"" + -(y + 10) + "\" transform=\"scale(1, -1)\" "
					+ "font-size=\"" + scaleNumber(6, precision) + "\" text-anchor=\"middle\">" + coordinate.toSvgString(x) + "</text>\n");
				bw.write("</g>\n");
			}
		}
		
		//Hyperloop-Path
		double startX = start.scaleXToCoordinateSystem(lon, precision);
		double startY = start.scaleYToCoordinateSystem(lat, precision);
		double endX = end.scaleXToCoordinateSystem(lon, precision);
		double endY = end.scaleYToCoordinateSystem(lat, precision);
		bw.write("<line x1=\"" + startX + "\" y1=\"" + startY //
				+ "\" x2=\"" + endX + "\" y2=\"" + endY //
				+ "\" style=\"stroke:rgb(255,0,0);stroke-width:2\" /> \n");
				
		bw.write("</svg> \n </body> \n </html>");
		
		bw.close();
				
		System.out.println("HTML-SVG has been written to \"" + svgFile + "\"!");
	}
	
	//rounding doubles for display of ledger lines Source: https://stackoverflow.com/questions/22186778/using-math-round-to-round-to-one-decimal-place
	public static double round (double value, int precision) {
	    int scale = (int) Math.pow(10, precision);
	    return (double) Math.round(value * scale) / scale;
	}
	
	private static int scaleNumber(int number, int precision) {
		double n = (double) number / 10000 * (double) precision;
		int rounded = (int) Math.round(n);
		if(rounded == 0) return 1;
		return rounded;
	}
	
	private static double getInfoCounter(int precision) {
		return 100.0/precision;
	}
	
	//Source: https://stackoverflow.com/questions/6264576/number-of-decimal-digits-in-a-double
	private static int getDecimalDigits(double n) {
		String s = Double.toString(n);
		int integerPlaces = s.indexOf('.');
		return s.length() - integerPlaces - 1;
	}
	
	//testing
	//TODO Comment this, when project is final.
	public static void main(String[] args) throws IOException {
		
		List<Coordinate> coordinates = CoordinateReader.readCoordinates("vbb_neo4j.csv");
		
		Coordinate start = new Coordinate(1, "StartStation", "StartDistrict", 52.0, 13.0);
		Coordinate end = new Coordinate(2, "End", "EndDistrict", 52.9999, 13.9999);
		visualizeWithHTML(start, end, coordinates, 0.005, 4);
		start = new Coordinate(1, "StartStation", "StartDistrict", 52.0, 13.0);
		end = new Coordinate(2, "End", "EndDistrict", 52.99999, 13.99999);
		visualizeWithHTML(start, end, coordinates, 0.005, 5);
		start = new Coordinate(1, "StartStation", "StartDistrict", 52.0, 13.0);
		end = new Coordinate(2, "End", "EndDistrict", 152.999999, 13.999999);
		visualizeWithHTML(start, end, coordinates, 0.005, 6);
	}
}
