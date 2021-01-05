package hyperloop;

import java.util.LinkedList;
import java.util.List;

import coordinate.Coordinate;

public class Track {
	private final Line track;
    private List<Coordinate> points;

    public Track(Line track){
        this.track = track;
        this.points = new LinkedList<>();
    }


    public boolean addPoint(Coordinate p, double tolerance){
        boolean isAdded = false;
        if(!this.points.contains(p) && isOnTrack(p, tolerance)) isAdded = this.points.add(p);
        return isAdded;
    }

    public boolean isOnTrack(Coordinate p, double tolerance){
        return this.track.positionToLine(p, tolerance) == 0;
    }

    public int size(){
        return this.points.size();
    }

    public final Line getTrack(){
        return this.track;
    }

    public final List<Coordinate> getPoints(){
    	return this.points;
    }
}
