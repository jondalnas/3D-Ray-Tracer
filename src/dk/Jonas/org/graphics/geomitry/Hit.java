package dk.Jonas.org.graphics.geomitry;

import dk.Jonas.org.graphics.ray.Ray;
import dk.Jonas.org.vector.Vector3;

public class Hit {
	private double distance;
	private Vector3 color;
	private Vector3 pos;
	private Vector3 center;
	private Vector3 normal;
	private double damper = 1;
	private double reflectivity;
	private double refractionIndex = 1;
	private double opacity = -1;
	private int physicsMask;
	
	public Hit(double t, Vector3 color, Ray ray, Vector3 center) {
		this.distance = t;
		this.color = color;
		this.pos = ray.dir.mul(t).add(ray.pos);
		this.center = center;
		this.normal = pos.sub(center).normalized();
	}
	
	public Hit(double t, Vector3 color, Ray ray, Vector3 center, double refractionIndex, int physicsMask, double opacity) {
		this(t, color, ray, center);
		this.refractionIndex = refractionIndex;
		this.physicsMask = physicsMask;
		this.opacity = opacity;
	}
	
	public Hit(double t, Vector3 color, Ray ray, Vector3 center, double refractionIndex, int physicsMask, double opacity, Vector3 normal) {
		this(t, color, ray, center, refractionIndex, physicsMask, opacity);
		this.normal = normal;
	}
	
	public Hit(double t, Vector3 color, Ray ray, Vector3 center, Vector3 normal) {
		this(t, color, ray, center);
		this.normal = normal;
	}
	
	public Hit(double t, Vector3 color, Ray ray, Vector3 center, double damper, double reflectivity) {
		this(t, color, ray, center);
		this.damper = damper;
		this.reflectivity = reflectivity;
	}
	
	public Hit(double t, Vector3 color, Ray ray, Vector3 center, double damper, double reflectivity, double refractionIndex, int physicsMask, double opacity) {
		this(t, color, ray, center, refractionIndex, physicsMask, opacity);
		this.damper = damper;
		this.reflectivity = reflectivity;
	}
	
	public Hit(double t, Vector3 color, Ray ray, Vector3 center, double damper, double reflectivity, double refractionIndex, int physicsMask, double opacity, Vector3 normal) {
		this(t, color, ray, center, refractionIndex, physicsMask, opacity, normal);
		this.damper = damper;
		this.reflectivity = reflectivity;
	}
	
	public Hit(double t, Vector3 color, Ray ray, Vector3 center, double damper, double reflectivity, Vector3 normal) {
		this(t, color, ray, center, normal);
		this.damper = damper;
		this.reflectivity = reflectivity;
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

	public double getRefractionIndex() {
		return refractionIndex;
	}
	
	public double getOpacity() {
		return opacity;
	}

	public int getPhysicsMask() {
		return physicsMask;
	}

	public double getDamper() {
		return damper;
	}

	public double getReflectivity() {
		return reflectivity;
	}
}
