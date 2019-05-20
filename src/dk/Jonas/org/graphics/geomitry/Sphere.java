package dk.Jonas.org.graphics.geomitry;

import dk.Jonas.org.graphics.Bitmap;
import dk.Jonas.org.graphics.ray.Ray;
import dk.Jonas.org.vector.Vector3;

public class Sphere extends Geometry {
	private double radius;
	private Vector3 color;
	private Bitmap image;
	private double imageColorBlending;
	private double refractionIndex;
	private double opacity = -1;
	private int physicsMask;
	
	public Sphere(Vector3 pos, double radius, Vector3 color) {
		super(pos);
		this.radius = radius;
		this.color = color;
	}
	
	public Sphere(Vector3 pos, double radius, Vector3 color, Bitmap image, double imageColorBlending) {
		this(pos, radius, color.mul(1-imageColorBlending));
		this.image = image;
		this.imageColorBlending = imageColorBlending;
	}
	
	public Sphere(Vector3 pos, double radius, Bitmap image) {
		super(pos);
		this.radius = radius;
		this.image = image;
		this.imageColorBlending = 1;
	}
	
	public Sphere(Vector3 pos, double radius, int physicsMask, double refractionIndex) {
		super(pos);
		this.radius = radius;
		this.physicsMask = physicsMask;
		this.refractionIndex = refractionIndex;
	}
	
	public Sphere(Vector3 pos, double radius, Vector3 color, int physicsMask, double refractionIndex, double opacity) {
		this(pos, radius, physicsMask, refractionIndex);
		this.color = color;
		this.opacity = opacity;
	}
	
	public Sphere(Vector3 pos, double radius, Vector3 color, Bitmap image, double imageColorBlending, int physicsMask, double refractionIndex, double opacity) {
		this(pos, radius, color.mul(1-imageColorBlending), physicsMask, refractionIndex, opacity);
		this.image = image;
		this.imageColorBlending = imageColorBlending;
	}
	
	public Sphere(Vector3 pos, double radius, Bitmap image, int physicsMask, double refractionIndex, double opacity) {
		this(pos, radius, physicsMask, refractionIndex);
		this.opacity = opacity;
		this.image = image;
		this.imageColorBlending = 1;
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
		
		Vector3 currColor = color == null ? Vector3.ZERO : color.clone();
		
		if (image != null) {
			Vector3 t0Pos = ray.dir.mul(t0).add(ray.pos).sub(pos);
			
			double lat = Math.asin(t0Pos.y / radius) + Math.PI / 2;
			double lng = Math.acos(t0Pos.z / (radius * Math.sin(lat)));

			double tx = lat / (Math.PI * 2);
			double ty = lng / (Math.PI * 2);
			
			currColor = currColor.add(image.getPixelVector(tx, ty).mul(imageColorBlending));
		}
		
		return new Hit(t0, currColor, ray, pos, refractionIndex, physicsMask, opacity);
	}
}
