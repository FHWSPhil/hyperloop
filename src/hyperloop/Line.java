package hyperloop;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import coordinate.Coordinate;


public class Line {
	private Coordinate firstOriginPoint;
	private Coordinate secondOriginPoint;
    private final double slope;
    private final Optional<Double> yIntercept;
    private final Optional<Double> xIntercept;
   
    

    public Line(Coordinate p1, Coordinate p2){
    	this.firstOriginPoint = p1;
    	this.secondOriginPoint = p2;
        this.slope = calculateSlope(p1, p2);
        this.yIntercept = evaluateYIntercept(p1, this.slope);
        this.xIntercept = evaluateXIntercept(p1, this.slope);
       
    }

    //slope formula
    private double calculateSlope(Coordinate p1, Coordinate p2){
        double slope = Double.POSITIVE_INFINITY;
        double diffX = Math.abs(p1.getX() - p2.getX());
        double diffY = Math.abs(p1.getY() - p2.getY());
        if(diffX != 0) slope = diffY / diffX;
        return slope;
    }

    private Optional<Double> evaluateYIntercept(Coordinate p, double slope){   //calculate Y-Intercept, if the X-Difference are not equal to zero
        return (slope == Double.POSITIVE_INFINITY) ? Optional.empty() : Optional.of(p.getY() - slope * p.getX()); 
    }

    private Optional<Double> evaluateXIntercept(Coordinate p, double slope){ //if the straight line is parallel to the y-Axis
        return (slope == Double.POSITIVE_INFINITY) ? Optional.of(p.getX()) : Optional.empty();
    }

    public Double getSlope(){
    	return this.slope;
    }
    
    public Optional<Double> getYIntercept(){
    	return this.yIntercept;
    }
    
    public Optional<Double> getXIntercept(){
    	return this.xIntercept;
    }
    
    public Coordinate getfirstPointofOrigin() {
    	return this.firstOriginPoint;
    }
    
    public Coordinate getSecondPointofOrigin() {
    	return this.secondOriginPoint;
    }
    
     public List<Coordinate> getPointOfOrigin(){
        List<Coordinate> points = new LinkedList<Coordinate>();
        points.add(this.firstOriginPoint);
        points.add(this.secondOriginPoint);
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
