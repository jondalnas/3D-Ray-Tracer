package dk.Jonas.org.graphics.ray;

import java.util.List;

import dk.Jonas.org.graphics.Light;
import dk.Jonas.org.graphics.Screen;
import dk.Jonas.org.graphics.geomitry.Geometry;
import dk.Jonas.org.graphics.geomitry.Hit;
import dk.Jonas.org.vector.Vector3;

public class Ray {
	public Vector3 pos;
	public Vector3 dir;
	
	public Vector3 color;
	
	private Hit closest;
	
	public Ray(Vector3 pos, Vector3 dir) {
		this.pos = pos;
		this.dir = dir;
	}
	
	public Hit intersects(List<Geometry> geometries) {
		Hit result = null;
		
		double distToTarget = Double.MAX_VALUE;
		
		for (Geometry g : geometries) {
			Hit hit = g.collides(this);
			
			if (hit == null) continue;
			
			if (hit.getDistance() < distToTarget) {
				result = hit;
				distToTarget = hit.getDistance();
			}
		}
		
		return result;
	}
	
	public Vector3 rayColor(List<Geometry> geometries, List<Light> lights) {
		closest = intersects(geometries);
		
		if (closest == null) return Vector3.zero;
		
		color = closest.getColor();
		
		double brightness = 0;
		
		for (Light l : lights) {
			Vector3 toLight = l.pos.sub(closest.getPos());
			double distanceToLight = toLight.length();
			toLight.mulEqual(1/distanceToLight);
			
			Ray toLightRay = new Ray(closest.getPos().add(toLight), toLight);
			
			double currBrightness;
			
			Hit closestToLight;
			if ((closestToLight = toLightRay.intersects(geometries)) == null || closestToLight.getDistance() > distanceToLight) {
				currBrightness = closest.getNormal().dot(toLight);
				
				if (currBrightness > brightness) brightness = currBrightness;
			}
		}
		
		return color.mul(1 - (1 - brightness) * Screen.MIN_BRIGHTNESS);
	}
}
