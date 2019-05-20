package dk.Jonas.org.graphics.ray;

import java.util.List;

import dk.Jonas.org.graphics.Light;
import dk.Jonas.org.graphics.Screen;
import dk.Jonas.org.graphics.geomitry.Geometry;
import dk.Jonas.org.graphics.geomitry.Hit;
import dk.Jonas.org.graphics.geomitry.PhysicsMask;
import dk.Jonas.org.vector.Vector3;

public class Ray {
	public Vector3 pos;
	public Vector3 dir;
	public Vector3 color;
	
	public int ittr;
	
	private Hit closest;
	private double currRefractIndex = 1.0;
	
	public Ray(Vector3 pos, Vector3 dir) {
		this.pos = pos;
		this.dir = dir;
	}
	
	public Ray(Vector3 pos, Vector3 dir, double currRefractIndex) {
		this(pos, dir);
		this.currRefractIndex = currRefractIndex;
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
		
		if (closest.getPhysicsMask() == 0) {
			double brightness = 0;
			
			for (Light l : lights) {
				Vector3 toLight = l.pos.sub(closest.getPos());
				double distanceToLight = toLight.length();
				toLight.mulEqual(1/distanceToLight);
				
				Ray toLightRay = new Ray(closest.getPos().add(toLight), toLight);
				
				Hit closestToLight;
				if ((closestToLight = toLightRay.intersects(geometries)) == null || closestToLight.getDistance() > distanceToLight) {
					double currBrightness = closest.getNormal().dot(toLight);
					
					if (currBrightness > brightness) brightness = currBrightness;
				}
			}
			
			return color.mul(1 - (1 - brightness) * Screen.MIN_BRIGHTNESS);
		} else {
			if (ittr >= Screen.MAX_NUM_OF_ITTERATIONS) return closest.getColor() == null ? Vector3.ZERO : closest.getColor();
			
			double fr = 0;
			Vector3 currColor = Vector3.ZERO;
			
			if (PhysicsMask.REFLECT.testMask(closest.getPhysicsMask())) {
				Vector3 reflectDir = dir.sub(closest.getNormal().mul(2*(dir.dot(closest.getNormal()))));
				Ray reflection = new Ray(closest.getPos(), reflectDir);
				reflection.ittr = ittr + 1;
				currColor = reflection.rayColor(geometries, lights);
			}

			if (PhysicsMask.REFRACT.testMask(closest.getPhysicsMask())) {
				double cosIn = closest.getNormal().dot(dir);
				Vector3 normal = closest.getNormal();
				double nIn = currRefractIndex, nTarget = closest.getRefractionIndex();
				if (cosIn < 0) {
					cosIn = -cosIn;
				} else {
					//TODO: Check what the refraction index of the medium entering is, instead of 1
					nIn = nTarget;
					nTarget = 1;
					normal = normal.negative();
				}
				double n = nIn / nTarget;
				double k = 1 - n * n * (1 - cosIn * cosIn);
				Vector3 refractColor = Vector3.ZERO;
				if (k >= 0) {
					Vector3 refractDir = dir.mul(n).add(normal.mul(n * cosIn - Math.sqrt(k)));
					
					Ray refraction = new Ray(closest.getPos(), refractDir, closest.getRefractionIndex());
					refraction.ittr = ittr + 1;
					
					refractColor = refraction.rayColor(geometries, lights);
				}
				
				if (PhysicsMask.REFRACT.testMask(closest.getPhysicsMask())) {
					cosIn = closest.getNormal().dot(dir);
					double sinTarget = n * Math.sqrt(Math.max(0, 1 - cosIn * cosIn));
					fr = 1;
					if (sinTarget < 1) {
						double cosTarget = Math.sqrt(Math.max(0, 1 - sinTarget * sinTarget));
						cosIn = Math.abs(cosIn);
						double parral = ((nTarget * cosIn) - (nIn * cosTarget)) / ((nTarget * cosIn) + (nIn * cosTarget));
						double perpen = ((nIn * cosIn) - (nTarget * cosTarget)) / ((nIn * cosIn) + (nTarget * cosTarget));
						fr = (parral * parral + perpen * perpen) / 2;
					}
					
					currColor = currColor.mul(fr).add(refractColor.mul(1 - fr));
				} else {
					currColor = refractColor;
				}
			}
			
			return (closest.getOpacity() == -1) ? currColor : currColor.mul(closest.getOpacity()).add(closest.getColor().mul(1-closest.getOpacity()));
		}
	}
}
