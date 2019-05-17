package dk.Jonas.org.graphics.geomitry;

import dk.Jonas.org.vector.Vector3;

public class Hit {
	private double distance;
	private int color;
	private Vector3 pos;
	
	public Hit(double t0, int color, Vector3 rayDir) {
		super();
		this.distance = t0;
		this.color = color;
		this.pos = rayDir.mul(t0);
	}
	
	public double getDistance() {
		return distance;
	}
	public int getColor() {
		return color;
	}
	public Vector3 getPos() {
		return pos;
	}
}
