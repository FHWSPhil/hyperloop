package hyperloop;

public class Coordinate {
	private long id;
	private String stationName;
	private String district;
	private double x;
	private double y;
	
	public Coordinate(long id, String stationName, String district, double x, double y) {
		super();
		this.id = id;
		this.stationName = stationName;
		this.district = district;
		this.x = x;
		this.y = y;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return getId() + "," + getStationName() + "," + getDistrict() + "," + getX() + "," + getY();
	}
}
