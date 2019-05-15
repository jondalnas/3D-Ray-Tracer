package dk.Jonas.org.graphics.ray;

import dk.Jonas.org.vector.Vector3;

public class Ray {
	public Vector3 pos;
	public Vector3 dir;
	
	public Ray(Vector3 pos, Vector3 dir) {
		this.pos = pos;
		this.dir = dir;
	}

	public void step(double step) {
		pos.addEqual(dir.mul(step));
	}
	
	public boolean collides() {
		return pos.add(new Vector3(0, 0, -5)).length() < 2;
	}
}
