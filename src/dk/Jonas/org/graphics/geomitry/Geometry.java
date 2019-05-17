package dk.Jonas.org.graphics.geomitry;

import dk.Jonas.org.graphics.ray.Ray;
import dk.Jonas.org.vector.Vector3;

public abstract class Geometry {
	public Vector3 pos = new Vector3(0, 10, 0);
	
	public Geometry(Vector3 pos) {
		this.pos = pos;
	}
	
	public abstract Hit collides(Ray ray);
}
