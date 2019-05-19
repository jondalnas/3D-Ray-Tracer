package dk.Jonas.org.graphics.geomitry;

import dk.Jonas.org.graphics.ray.Ray;
import dk.Jonas.org.vector.Vector3;

public class Plane extends Geometry {
	private Vector3 normal;
	
	private Vector3 color;
	
	public Plane(Vector3 pos, Vector3 normal, Vector3 color) {
		super(pos);
		this.normal = normal;
		this.color = color;
	}

	@Override
	public Hit collides(Ray ray) {
		Vector3 negNormal = normal.negative();
		
		double normalDotDir = ray.dir.dot(negNormal);
		
		if (normalDotDir > 0) {
			double t = negNormal.dot(pos.sub(ray.pos)) / normalDotDir;
			
			if (t < 0) return null;
			
			Vector3 currColor = color;
			
			/*if (ray.dir.mul(t).add(ray.pos).length() % 1.0 < 0.1) {
				currColor = Vector3.ZERO;
			}*/
			
			return new Hit(t, currColor, ray, ray.dir.mul(t).add(ray.pos), normal);
		}
		
		return null;
	}

}
