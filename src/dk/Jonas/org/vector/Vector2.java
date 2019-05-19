package dk.Jonas.org.vector;

public class Vector2 {
	public static final Vector2 zero = new Vector2(0, 0);
	public double x, y;
	
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2 addEqual(Vector2 v) {
		x += v.x;
		y += v.y;
		
		return this;
	}
	
	public Vector2 add(Vector2 v) {
		Vector2 vr = new Vector2(x, y);
		
		vr.x += v.x;
		vr.y += v.y;
		
		return vr;
	}

	public Vector2 sub(Vector2 v) {
		Vector2 vr = new Vector2(x, y);
		
		vr.x -= v.x;
		vr.y -= v.y;
		
		return vr;
	}
	
	public Vector2 mul(double k) {
		Vector2 v = new Vector2(x, y);
		
		v.x *= k;
		v.y *= k;
		
		return v;
	}
	
	public void mulEqual(double k) {
		x *= k;
		y *= k;
	}
	
	public double dot(Vector2 v) {
		return x*v.x+y*v.y;
	}
	
	public double length() {
		return Math.sqrt(x*x+y*y);
	}
	
	public void normalize() {
		double d = length();
		
		x /= d;
		y /= d;
	}

	public Vector2 normalized() {
		Vector2 v = new Vector2(x, y);
		
		double d = v.length();
		
		if (d == 1) return v;
		
		v.x /= d;
		v.y /= d;
		
		return v;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	public Vector2 negative() {
		return new Vector2(-x, -y);
	}
}
