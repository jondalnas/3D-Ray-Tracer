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
		
		if (closest == null) return Screen.SKY_COLOR;
		
		color = closest.getColor();
		
		if (color != null) {
			double brightness = 0;
			
			for (Light l : lights) {
				Hit currClosest = closest;
				
				for (int i = 0; i < 3; i++) {
					Vector3 toLight = l.pos.sub(currClosest.getPos());
					double distanceToLight = toLight.length();
					toLight.mulEqual(1/distanceToLight);
					
					Ray toLightRay = new Ray(currClosest.getPos().add(toLight), toLight);
					
					Hit closestToLight;
					if ((closestToLight = toLightRay.intersects(geometries)) == null || closestToLight.getDistance() > distanceToLight) {
						double currBrightness = currClosest.getNormal().dot(toLight);
						
						if (currBrightness > brightness) brightness = currBrightness;
					} else if (closestToLight.getColor() == null) {
						currClosest = closestToLight;
						
						continue;
					}
					
					break;
				}
			}
			
			return color.mul(1 - (1 - brightness) * Screen.MIN_BRIGHTNESS);
		} else {
			Vector3 reflectDir = dir.sub(closest.getNormal().mul(2*(dir.dot(closest.getNormal()))));
			
			Ray reflection = new Ray(closest.getPos(), reflectDir);
			
			return reflection.rayColor(geometries, lights).mul(0.9).add(Vector3.zero);
		}
	}
}
