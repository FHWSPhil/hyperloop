import java.io.IOException;
import java.util.List;

import coordinate.Coordinate;
import coordinate.CoordinateReader;
import hyperloop.Track;
import hyperloop.TrackFinder;
import visualization.SvgVisualizer;

public class MainClass {
	public static void main(String[] args) throws IOException {
		//Read Coordinates/Points:
		List<Coordinate> coordinates = CoordinateReader.readCoordinates("vbb_neo4j.csv");

		
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
        SvgVisualizer.visualizeWithHTML(pointsOfOrigin.get(0), pointsOfOrigin.get(1), coordinates);
	}
}
