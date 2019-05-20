package dk.Jonas.org.vector;

public class Vector3 {
	public static final Vector3 ZERO = new Vector3(0, 0, 0);
	public double x, y, z;
	
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3 addEqual(Vector3 v) {
		x += v.x;
		y += v.y;
		z += v.z;
		
		return this;
	}
	
	public Vector3 add(Vector3 v) {
		Vector3 vr = new Vector3(x, y, z);
		
		vr.x += v.x;
		vr.y += v.y;
		vr.z += v.z;
		
		return vr;
	}

	public Vector3 sub(Vector3 v) {
		Vector3 vr = new Vector3(x, y, z);
		
		vr.x -= v.x;
		vr.y -= v.y;
		vr.z -= v.z;
		
		return vr;
	}
	
	public Vector3 mul(double k) {
		Vector3 v = new Vector3(x, y, z);
		
		v.x *= k;
		v.y *= k;
		v.z *= k;
		
		return v;
	}
	
	public void mulEqual(double k) {
		x *= k;
		y *= k;
		z *= k;
	}
	
	public Vector3 mulEqual(Matrix4x4 m) {
		double x = this.x*m.m00 + this.y*m.m10 + this.z*m.m20 + m.m30;
		double y = this.x*m.m01 + this.y*m.m11 + this.z*m.m21 + m.m31;
		double z = this.x*m.m02 + this.y*m.m12 + this.z*m.m22 + m.m32;
		double w = this.x*m.m03 + this.y*m.m13 + this.z*m.m23 + m.m33;
		
		if (w != 1) {
			x /= w;
			y /= w;
			z /= w;
		}
		
		return new Vector3(x, y, z);
	}

	public double dot(Vector3 v) {
		return x*v.x+y*v.y+z*v.z;
	}
	
	public double length() {
		return Math.sqrt(x*x+y*y+z*z);
	}
	
	public void normalize() {
		double d = length();
		
		x /= d;
		y /= d;
		z /= d;
	}

	public Vector3 normalized() {
		Vector3 v = new Vector3(x, y, z);
		
		double d = v.length();
		
		if (d == 1) return v;
		
		v.x /= d;
		v.y /= d;
		v.z /= d;
		
		return v;
	}

	public Vector3 negative() {
		return new Vector3(-x, -y, -z);
	}
	
	public Vector2 xy() {
		return new Vector2(x, y);
	}
	
	public Vector2 xz() {
		return new Vector2(x, z);
	}
	
	public Vector2 yz() {
		return new Vector2(y, z);
	}
	
	public Vector3 clone() {
		return new Vector3(x, y, z);
	}
	
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
