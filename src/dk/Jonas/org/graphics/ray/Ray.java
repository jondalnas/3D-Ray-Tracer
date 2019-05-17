package dk.Jonas.org.graphics.ray;

import java.util.List;

import dk.Jonas.org.graphics.geomitry.Geometry;
import dk.Jonas.org.graphics.geomitry.Hit;
import dk.Jonas.org.vector.Vector3;

public class Ray {
	public Vector3 pos;
	public Vector3 dir;
	
	public int color;
	
	public double sr = 2;
	
	public Ray(Vector3 pos, Vector3 dir) {
		this.pos = pos;
		this.dir = dir;
	}
	
	public int rayColor(List<Geometry> geometries) {
		double distToTarget = Double.MAX_VALUE;
		
		for (Geometry g : geometries) {
			Hit hit = g.collides(this);
			
			if (hit == null) continue;
			
			if (hit.getDistance() < distToTarget) {
				color = hit.getColor();
				distToTarget = hit.getDistance();
			}
		}
		
		return color;
	}
}
