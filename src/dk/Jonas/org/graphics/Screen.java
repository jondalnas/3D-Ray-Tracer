package dk.Jonas.org.graphics;

import java.util.ArrayList;
import java.util.List;

import dk.Jonas.org.InputHandler;
import dk.Jonas.org.graphics.geomitry.Geometry;
import dk.Jonas.org.graphics.geomitry.Sphere;
import dk.Jonas.org.graphics.ray.Ray;
import dk.Jonas.org.vector.Vector3;

public class Screen extends Bitmap {
	public static final double MIN_BRIGHTNESS = 0.8;

	Camera cam;
	
	List<Geometry> geometries = new ArrayList<Geometry>();
	List<Light> light = new ArrayList<Light>();
	
	public Screen(int width, int height) {
		super(width, height);
		
		cam = new Camera(width, height, 90);

		geometries.add(new Sphere(new Vector3(0, 0, 5), 2, new Vector3(1, 0, 0)));
		geometries.add(new Sphere(new Vector3(5, 0, 5), 2, new Vector3(0, 1, 0)));
		geometries.add(new Sphere(new Vector3(-5, 1, 8), 3, new Vector3(0, 0, 1)));
		
		light.add(new Light(new Vector3(1, 0, 0)));
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
				
				Vector3 color = r.rayColor(geometries, light);
				
				pixels[x+y*width] = (int) (color.x * 0xff) << 16 | (int) (color.y * 0xff) << 8 | (int) (color.z * 0xff);
			}
		}
	}
}