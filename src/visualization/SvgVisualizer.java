package visualization;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import coordinate.Coordinate;
import coordinate.CoordinateReader;
import hyperloop.Line;
import hyperloop.Track;

public class SvgVisualizer {

	/**
	 * The main algorithm to visualize the computed line of the HyperLoop in SVG.
	 * The program is limited to a List of coordinates that contains the same
	 * Latitude and Longitude numbers before the decimal point. The accuracy of the
	 * program is four decimal places of the Latitude and Longitude. Developed for
	 * public transport stations of Berlin.
	 * 
	 * @param start       StartCoordinate of computed line.
	 * @param end         EndCoordinate of computed line.
	 * @param coordinates List of all coordinates.
	 * @param tolerance   to mark Stations within the tolerance.
	 * @throws IOException
	 */
	public static void visualizeWithHTML(Coordinate start, Coordinate end, List<Coordinate> coordinates, double tolerance)
			throws IOException {
		double lon = CoordinateReader.findLon(coordinates);
		double lat = CoordinateReader.findLat(coordinates);

		String svgFile = "Svg_Visualization.html";
		BufferedWriter bw = new BufferedWriter(new FileWriter(svgFile));

		// Create HTML with stylesheet svg.css --> hover-effect
		bw.write("<html> \n <head> \n");
		bw.write("<link rel=\"stylesheet\" href=\"svg.css\"></head>\n");
		bw.write("<body>\n");
		
		//Create svg with a good scaling.
		bw.write(
				"<svg width=\"5000\" height=\"5000\" xmlns=\"http://www.w3.org/2000/svg\" viewBox= \"0 0 10000 10000\" style=\"transform: scale(1,-1)\">\n");

		// Triangle as marker of Coordinate-Axis-End
		bw.write(
				"<marker id=\"triangle\" viewBox=\"0 0 10 10\" refX=\"0\" refY=\"5\" markerUnits=\"strokeWidth\" markerWidth=\"4\" markerHeight=\"3\" orient=\"auto\">\r\n"
						+ " <path d=\"M 0 0 L 10 5 L 0 10 z\"/>\r\n" + " </marker>");

		// xAxis
		bw.write(
				"<line x1=\"0\" y1=\"0\" x2=\"9940\" y2=\"0\" marker-end=\"url(#triangle)\" stroke=\"black\" stroke-width=\"20\"/>\n");
		// Description of x-Axis
		bw.write("<circle cx=\"10000\" cy=\"0\" r=\"40\" stroke=\"black\" stroke-width=\"20\" fill=\"none\" />\n");
		bw.write("<text x=\"9300\" y=\"-20\" font-size=\"100\" transform=\"scale(1, -1)\" > Lon: " + (lon + 1)
				+ " Lat: " + lat + "</text>\n");

		// yAxis
		bw.write(
				"<line x1=\"0\" y1=\"0\" x2=\"0\" y2=\"9940\" marker-end=\"url(#triangle)\" stroke=\"black\" stroke-width=\"20\"/>\n");
		// Description of y-Axis
		bw.write("<circle cx=\"0\" cy=\"10000\" r=\"40\" stroke=\"black\" stroke-width=\"20\" fill=\"none\" />\n");
		bw.write("<text x=\"40\" y=\"-9900\" font-size=\"100\" transform=\"scale(1, -1)\" > Lon: " + lon + " Lat: "
				+ (lat + 1) + "</text>\n");

		// Description of 0-Point
		bw.write("<circle cx=\"0\" cy=\"0\" r=\"40\" stroke=\"black\" stroke-width=\"20\" fill=\"none\" />\n");
		bw.write("<text x=\"40\" y=\"-20\" font-size=\"100\" transform=\"scale(1, -1)\" > Lon: " + lon + " Lat: " + lat
				+ "</text>\n");

		// ledger lines for coordinate system
		double lonInfo = round(lon + 0.01, 2);
		double latInfo = round(lat + 0.01, 2);
		for (int i = 100; i <= 10000; i += 100) {

			bw.write("<line x1=\"" + i + "\" y1=\"0\" x2=\"" + i
					+ "\" y2=\"10000\" stroke=\"black\" stroke-width=\"1\" stroke-dasharray=\"5\"/>\n");
			bw.write("<text x=\"" + (i - 15) + "\" y=\"-10\" transform=\"scale(1, -1)\" "
					+ "font-size=\"6\" text-anchor=\"middle\"> Lon: " + lonInfo + "</text>\n");
			lonInfo = round(lonInfo + 0.01, 2);

			bw.write("<line x1=\"0\" y1=\"" + i + "\" x2=\"10000\" y2=\"" + i
					+ "\" stroke=\"black\" stroke-width=\"1\" stroke-dasharray=\"5\"/>\n");
			bw.write("<text x=\"25\" y=\"" + -(i - 5) + "\" transform=\"scale(1, -1)\" "
					+ "font-size=\"6\" text-anchor=\"middle\"> Lat: " + latInfo + "</text>\n");
			latInfo = round(latInfo + 0.01, 2);
		}
		
		Track track = new Track(new Line(start, end));
		
		// Stations
		for (Coordinate coordinate : coordinates) {
			int x = coordinate.scaleXToCoordinateSystem(lon, 10000);
			int y = coordinate.scaleYToCoordinateSystem(lat, 10000);
			
			if (track.isOnTrack(coordinate, tolerance)) {			
				bw.write("<g id=\"" + coordinate.getId() + "\">\n");
				bw.write("<circle cx=\"" + x + "\" cy=\"" + y
					+ "\" r=\"10\" stroke=\"black\" stroke-width=\"1\" fill=\"red\" /> \n");

				bw.write("<text x=\"" + x + "\" y=\"" + -(y + 10) + "\" transform=\"scale(1, -1)\" "
					+ "font-size=\"6\" text-anchor=\"middle\">" + coordinate.toSvgString(x) + "</text>\n");
				bw.write("</g>\n");			
			} else {
				bw.write("<g id=\"" + coordinate.getId() + "\">\n");
				bw.write("<circle cx=\"" + x + "\" cy=\"" + y
					+ "\" r=\"10\" stroke=\"black\" stroke-width=\"1\" fill=\"yellow\" /> \n");

				bw.write("<text x=\"" + x + "\" y=\"" + -(y + 10) + "\" transform=\"scale(1, -1)\" "
					+ "font-size=\"6\" text-anchor=\"middle\">" + coordinate.toSvgString(x) + "</text>\n");
				bw.write("</g>\n");
			}
		}

		// Hyperloop-Path
		double startX = start.scaleXToCoordinateSystem(lon, 10000);
		double startY = start.scaleYToCoordinateSystem(lat, 10000);
		double endX = end.scaleXToCoordinateSystem(lon, 10000);
		double endY = end.scaleYToCoordinateSystem(lat, 10000);
		bw.write("<line x1=\"" + startX + "\" y1=\"" + startY //
				+ "\" x2=\"" + endX + "\" y2=\"" + endY //
				+ "\" style=\"stroke:rgb(255,0,0);stroke-width:2\" /> \n");

		bw.write("</svg> \n </body> \n </html>");

		bw.close();

		System.out.println("HTML-SVG has been written to \"" + svgFile + "\"!");
	}

	// rounding doubles for display of ledger lines Source:
	// https://stackoverflow.com/questions/22186778/using-math-round-to-round-to-one-decimal-place
	public static double round(double value, int precision) {
		int scale = (int) Math.pow(10, precision);
		return (double) Math.round(value * scale) / scale;
	}

	// testing
	// TODO Comment this, when project is final.
	public static void main(String[] args) throws IOException {
		Coordinate start = new Coordinate(1, "StartStation", "StartDistrict", 52.0, 13.0);
		Coordinate end = new Coordinate(2, "End", "EndDistrict", 52.9999, 13.9999);

		List<Coordinate> coordinates = CoordinateReader.readCoordinates("vbb_neo4j.csv");

		visualizeWithHTML(start, end, coordinates, 0.005);
	}
}
