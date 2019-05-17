package dk.Jonas.org.graphics;

import java.util.ArrayList;
import java.util.List;

import dk.Jonas.org.InputHandler;
import dk.Jonas.org.graphics.geomitry.Geometry;
import dk.Jonas.org.graphics.geomitry.Sphere;
import dk.Jonas.org.graphics.ray.Ray;
import dk.Jonas.org.vector.Vector3;

public class Screen extends Bitmap {
	Camera cam;
	
	List<Geometry> geometries = new ArrayList<Geometry>();
	
	public Screen(int width, int height) {
		super(width, height);
		
		cam = new Camera(width, height, 90);

		geometries.add(new Sphere(new Vector3(0, 0, 5), 2, 0xff0000));
		geometries.add(new Sphere(new Vector3(5, 0, 5), 2, 0x00ff00));
		geometries.add(new Sphere(new Vector3(-5, 1, 8), 3, 0x0000ff));
	}
	
	public double step = 0.1;
	
	public void render() {
		if (InputHandler.charDown == 'd') cam.rot.addEqual(new Vector3(0, 5, 0));
		else if (InputHandler.charDown == 'a') cam.rot.addEqual(new Vector3(0, -5, 0));
		else if (InputHandler.charDown == 'w') cam.pos.addEqual(new Vector3(0, 0, 0.5));
		else if (InputHandler.charDown == 's') cam.pos.addEqual(new Vector3(0, 0, -0.5));
		
		fill(0, 0, width, height, 0xffffff);
		
		cam.updateRotation();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Ray r = cam.generateRay(x, y);
				
				pixels[x+y*width] = r.rayColor(geometries);
			}
		}
	}
}