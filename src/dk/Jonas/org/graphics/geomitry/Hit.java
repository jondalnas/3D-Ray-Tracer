package dk.Jonas.org.graphics.geomitry;

import dk.Jonas.org.graphics.ray.Ray;
import dk.Jonas.org.vector.Vector3;

public class Hit {
	private double distance;
	private Vector3 color;
	private Vector3 pos;
	private Vector3 center;
	private Vector3 normal;
	
	public Hit(double t, Vector3 color, Ray ray, Vector3 center) {
		this.distance = t;
		this.color = color;
		this.pos = ray.dir.mul(t).add(ray.pos);
		this.normal = pos.sub(center).normalized();
	}
	
	public Hit(double t, Vector3 color, Ray ray, Vector3 center, Vector3 normal) {
		this.distance = t;
		this.color = color;
		this.pos = ray.dir.mul(t).add(ray.pos);
		this.center = center;
		this.normal = normal;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public Vector3 getColor() {
		return color;
	}
	
	public Vector3 getPos() {
		return pos;
	}

	public Vector3 getNormal() {
		return normal;
	}

	public Vector3 getCenter() {
		return center;
	}
}
