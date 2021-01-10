package hyperloop;

import java.util.LinkedList;
import java.util.List;

import coordinate.Coordinate;

public class Track {
	private final Line line;
    private List<Coordinate> points;

    public Track(Line line){
        this.line = line;
        this.points = new LinkedList<>();
    }

    public boolean addPoint(Coordinate p, double tolerance){
        boolean isAdded = false;
        if(!this.points.contains(p) && isOnTrack(p, tolerance)) isAdded = this.points.add(p);
        return isAdded;
    }
    
    /**
     * Returns whether the point is on the track.
     * @param p Point to proof.
     * @param tolerance
     * @return boolean
     */
    public boolean isOnTrack(Coordinate p, double tolerance){
        
    	boolean position = false;
        
        if(line.getYIntercept().isPresent()) {
        	if(this.line.getSlope() == 0) {
        	position = (p.getX() >= line.getfirstPointofOrigin().getX() && p.getX() <= line.getSecondPointofOrigin().getX() && tolerance >= Math.abs((p.getY() - line.getYIntercept().get()))) ? true : false;	
        	}
        	else {
            double gY = line.getSlope() * p.getX() + line.getYIntercept().get();
            position = (gY >= line.getfirstPointofOrigin().getY() && gY <= line.getSecondPointofOrigin().getY() && tolerance >= Math.abs((p.getY() - gY))) ? true : false;
        	}
        }
        else if(line.getXIntercept().isPresent()){
            position = (p.getY() >= line.getfirstPointofOrigin().getY() && p.getY() <= line.getSecondPointofOrigin().getY() && tolerance >= Math.abs((p.getX() - line.getXIntercept().get()))) ? true: false;
        }
        return position;
    }

    public int size(){
        return this.points.size();
    }

    public final Line getLine(){
        return this.line;
    }

    public final List<Coordinate> getPoints(){
    	return this.points;
    }
}
