package dk.Jonas.org.graphics.geomitry;

import dk.Jonas.org.Art;
import dk.Jonas.org.graphics.Bitmap;
import dk.Jonas.org.graphics.ray.Ray;
import dk.Jonas.org.vector.Vector3;

public class Plane extends Geometry {
	private Vector3 normal;
	private Vector3 tangent;
	private Vector3 bitangent;
	
	private Vector3 color;
	private Bitmap image;
	private double imageColorBlending;
	private double refractionIndex;
	private double opacity = -1;
	private int physicsMask;
	
	public Plane(Vector3 pos, Vector3 normal, Vector3 color) {
		super(pos);
		this.normal = normal;
		calculateTangents();
		this.color = color;
	}
	
	public Plane(Vector3 pos, Vector3 normal, Vector3 color, Bitmap image, double imageColorBlending) {
		this(pos, normal, color.mul(1-imageColorBlending));
		this.image = image;
		this.imageColorBlending = imageColorBlending;
	}
	
	public Plane(Vector3 pos, Vector3 normal, Bitmap image) {
		super(pos);
		this.normal = normal;
		calculateTangents();
		this.image = image;
		this.imageColorBlending = 1;
	}
	
	public Plane(Vector3 pos, Vector3 normal, int physicsMask, double refractionIndex) {
		super(pos);
		this.normal = normal;
		this.physicsMask = physicsMask;
		this.refractionIndex = refractionIndex;
	}
	
	public Plane(Vector3 pos, Vector3 normal, Vector3 color, int physicsMask, double refractionIndex, double opacity) {
		this(pos, normal, physicsMask, refractionIndex);
		this.color = color;
		this.opacity = opacity;
	}
	
	public Plane(Vector3 pos, Vector3 normal, Vector3 color, Bitmap image, double imageColorBlending, int physicsMask, double refractionIndex, double opacity) {
		this(pos, normal, color.mul(1-imageColorBlending), physicsMask, refractionIndex, opacity);
		this.image = image;
		this.imageColorBlending = imageColorBlending;
	}
	
	public Plane(Vector3 pos, Vector3 normal, Bitmap image, int physicsMask, double refractionIndex, double opacity) {
		this(pos, normal, physicsMask, refractionIndex);
		this.opacity = opacity;
		this.image = image;
		this.imageColorBlending = 1;
	}

	@Override
	public Hit collides(Ray ray) {
		Vector3 negNormal = normal.negative();
		
		double normalDotDir = ray.dir.dot(negNormal);
		
		if (normalDotDir > 0) {
			double t = negNormal.dot(pos.sub(ray.pos)) / normalDotDir;
			
			if (t < 0) return null;

			Vector3 tCoord = ray.dir.mul(t).add(ray.pos);
			Vector3 currColor = color == null ? Vector3.ZERO : color.clone();
			
			if (image != null) {
				double tx = ((tangent.dot(tCoord.sub(pos))) % 1 + 1) % 1;
				double ty = ((bitangent.dot(tCoord.sub(pos))) % 1 + 1) % 1;
				
				currColor = currColor.add(image.getPixelVector(tx, ty).mul(imageColorBlending));
			}
			
			return new Hit(t, currColor, ray, tCoord, refractionIndex, physicsMask, opacity, normal);
		}
		
		return null;
	}
	
	private void calculateTangents() {
		if (normal.x == 0) {
			if (normal.z == 0) {
				tangent = new Vector3(1, (-normal.x-normal.z)/normal.y, 1);
				bitangent = new Vector3(-1, (-normal.x-normal.z)/normal.y, 1);
			} else {
				tangent = new Vector3(1, 1, (-normal.x-normal.y)/normal.z);
				bitangent = new Vector3(-1, 1, (-normal.x-normal.y)/normal.z);
			}
		} else {
			tangent = new Vector3((-normal.y-normal.z)/normal.x, 1, 1);
			bitangent = new Vector3((-normal.y-normal.z)/normal.x, -1, 1);
		}
	}
}
