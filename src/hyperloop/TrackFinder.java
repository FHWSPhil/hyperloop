package hyperloop;

import java.io.IOException;
import java.util.List;

import coordinate.Coordinate;
import coordinate.CoordinateReader;

public class TrackFinder {
	
	/**
	 * 
	 * @param points as List of all Coordinates
	 * @param tolerance determines the tolerance range of the line
	 * @return a Track, that has the most nodes on the line
	 */
	public static Track findOptimalTrack(List<Coordinate> points, double tolerance){
        Track optimalTrack = null;
        for(int i=0; i<points.size()-1; i++){
            for(int j=i+1; j<points.size(); j++){
            	Coordinate p1 = points.get(i);
            	Coordinate p2 = points.get(j);
                Line line = new Line(p1, p2); //calculate a Line for points p1 and p2
                Track track = new Track(line); //create a Track (all Points on the Line/Straight line equation)
                
                points.forEach(p -> {
                    if(track.isOnTrack(p, tolerance)) track.addPoint(p, tolerance);
                });

                if(optimalTrack == null || optimalTrack.size() < track.size()) optimalTrack = track;
            }
        }
        
        return optimalTrack;
    }
	
	//testing
//	public static void main(String[] args) throws IOException {
//		List<Coordinate> points1 = CoordinateReader.readCoordinates("vbb_neo4j.csv");
//
//    	double tolerance = 0.005; 
//		Track optimalTrack = TrackFinder.findOptimalTrack(points1, tolerance);
//        System.out.println("Geradengleichung:");
//        System.out.println(optimalTrack.getLine());
//        System.out.println();
//        System.out.println("Ursprungspunkte:");
//        System.out.println(optimalTrack.getLine().getPointOfOrigin());
//        System.out.println();
//        System.out.println("Knoten auf der Geraden:");
//        System.out.println(optimalTrack.getPoints());
//	}
}
