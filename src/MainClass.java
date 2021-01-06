import java.io.IOException;
import java.util.List;

import coordinate.Coordinate;
import coordinate.CoordinateReader;
import hyperloop.Track;
import hyperloop.TrackFinder;
import visualization.SvgVisualizer;
import visualization.SvgVisualizerWithPrecision;

public class MainClass {
	public static void main(String[] args) throws IOException {
		//Read Coordinates/Points:
		List<Coordinate> coordinates = CoordinateReader.readCoordinates("vbb_neo4j.csv");
		System.out.println();
		
		//Calculate OptimalTrack
    	double tolerance = 0.005; 
		Track optimalTrack = TrackFinder.findOptimalTrack(coordinates, tolerance);
        System.out.println("Geradengleichung:");
        System.out.println(optimalTrack.getTrack());
        System.out.println();
        
        System.out.println("Ursprungspunkte:");
        List<Coordinate> pointsOfOrigin = optimalTrack.getTrack().getPointOfOrigin();
        System.out.println(optimalTrack.getTrack().getPointOfOrigin());
        System.out.println();
        
        System.out.println("Knoten auf der Geraden:");
        List<Coordinate> pointsOnOptimalTrack = optimalTrack.getPoints(); 
        System.out.println(optimalTrack.getPoints());
        System.out.println();
        
        //Visualize
        Coordinate start = pointsOfOrigin.get(0);
        Coordinate end = pointsOfOrigin.get(1);
        SvgVisualizer.visualizeWithHTML(start, end, coordinates, tolerance);
        SvgVisualizerWithPrecision.visualizeWithHTML(start, end, coordinates, tolerance, 4);
        SvgVisualizerWithPrecision.visualizeWithHTML(start, end, coordinates, tolerance, 5);
        SvgVisualizerWithPrecision.visualizeWithHTML(start, end, coordinates, tolerance, 6);
	}
}
