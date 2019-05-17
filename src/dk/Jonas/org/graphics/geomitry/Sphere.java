package dk.Jonas.org.graphics.geomitry;

import dk.Jonas.org.graphics.ray.Ray;
import dk.Jonas.org.vector.Vector3;

public class Sphere extends Geometry {
	public double radius = 2;
	
	private Vector3 color;
	
	public double n = 0.5;
	
	public Sphere(Vector3 pos, double radius, Vector3 color) {
		super(pos);
		this.radius = radius;
		this.color = color;
	}
	
	public Hit collides(Ray ray) {
		double r2 = radius * radius;
		
		Vector3 l = pos.sub(ray.pos);
		
		double tc = l.dot(ray.dir.normalized());
		if (tc < 0) return null;
		
		double d2 = l.dot(l)-tc*tc;
		if (d2 < 0 || d2 > r2) return null;
		
		double thc = Math.sqrt(r2-d2);
		double t0 = tc-thc;
		double t1 = tc+thc;
		
		if (t0 > t1) {
			double tmp = t0;
			t0 = t1;
			t1 = tmp;
		}
		
		return new Hit(t0, color, ray, pos);
	}
}
