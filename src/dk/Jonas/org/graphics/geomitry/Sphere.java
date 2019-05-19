package dk.Jonas.org.graphics.geomitry;

import dk.Jonas.org.graphics.ray.Ray;
import dk.Jonas.org.vector.Vector2;
import dk.Jonas.org.vector.Vector3;

public class Sphere extends Geometry {
	public double radius = 2;
	
	private Vector3 color;
	
	public double n = 1.33;
	
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
		
		if (t0 <= 0.0000000001) t0 = t1;
		
		Vector3 currColor = color;
		
		/*if (currColor != null) {
			Vector3 t0Pos = ray.dir.mul(t0).add(ray.pos);

			Vector2 xy = t0Pos.xy();
			Vector2 xz = t0Pos.xz();
			
			if (Math.abs(xy.dot(new Vector2(0, 1))) % 0.25 < 0.05 || Math.abs(xz.dot(new Vector2(0, 1))) % 0.25 < 0.05) currColor = Vector3.ZERO;
		}*/
		
		return new Hit(t0, currColor, ray, pos, n);
	}
}
