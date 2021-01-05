package hyperloop;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import coordinate.Coordinate;

public class Line {
	private Coordinate point1;
	private Coordinate point2;
    private final double slope;
    private final Optional<Double> yIntercept;
    private final Optional<Double> xIntercept;
    private double distance;
    
    

    public Line(Coordinate p1, Coordinate p2){
    	this.point1 = p1;
    	this.point2 = p2;
        this.slope = calculateSlope(p1, p2);
        this.yIntercept = evaluateYIntercept(p1, this.slope);
        this.xIntercept = evaluateXIntercept(p1, this.slope);
        this.distance = calculateDistance(p1, p2);
    }

    private double calculateSlope(Coordinate p1, Coordinate p2){
        double slope = Double.POSITIVE_INFINITY;
        double diffX = Math.abs(p1.getX() - p2.getX());
        double diffY = Math.abs(p1.getY() - p2.getY());
        if(diffX != 0) slope = diffY / diffX;
        return slope;
    }

    private Optional<Double> evaluateYIntercept(Coordinate p, double slope){
        return (slope == Double.POSITIVE_INFINITY) ? Optional.empty() : Optional.of(p.getY() - slope * p.getX());
    }

    private Optional<Double> evaluateXIntercept(Coordinate p, double slope){
        return (slope == Double.POSITIVE_INFINITY) ? Optional.of(p.getX()) : Optional.empty();
    }

    public int positionToLine(Coordinate p, double tolerance){
        int position = -2;
        if(yIntercept.isPresent()) {
            double gY = this.slope * p.getX() + this.yIntercept.get();
            position = (tolerance >= Math.abs((p.getY() - gY))) ? 0 : (p.getY() > gY) ? 1 : -1;
           
        }
        else if(xIntercept.isPresent()){
            position = (tolerance >= Math.abs((p.getX() - this.xIntercept.get()))) ? 0 : (p.getX() > this.xIntercept.get()) ? 1 : -1;
        }
        return position;
    }
    
    public double calculateDistance (Coordinate p1, Coordinate p2) {
    	return Math.sqrt(Math.exp(p2.getX() - p1.getX())+ Math.exp(p2.getY() - p1.getY()));
    }
    
    public double getDistance() {
    return this.distance;
    }
    
    public List<Coordinate> getPointOfOrigin(){
        List<Coordinate> points = new LinkedList<Coordinate>();
        points.add(this.point1);
        points.add(this.point2);
        return points;
    }
    
    
    
    @Override
    public String toString() {
        String equation = "";
        if(xIntercept.isPresent()) equation = String.format("g: x = %f", this.xIntercept.get());
        else if(yIntercept.isPresent()){
            if(slope == 0) equation = String.format("g: y = %f", this.yIntercept.get());
            else {
                if(yIntercept.get() > 0) {
                    equation = String.format("g: y = %f * x + %f", this.slope, this.yIntercept.get());
                }
                else if(yIntercept.get() < 0){
                    equation = String.format("g: y = %f * x + (%f)", this.slope, this.yIntercept.get());
                }
                else {
                    equation = String.format("g: y = %f * x", this.slope);
                }
            }
        }
        return equation;
    }
}
