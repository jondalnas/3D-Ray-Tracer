package dk.Jonas.org.graphics;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import dk.Jonas.org.Art;
import dk.Jonas.org.InputHandler;
import dk.Jonas.org.graphics.geomitry.Geometry;
import dk.Jonas.org.graphics.geomitry.PhysicsMask;
import dk.Jonas.org.graphics.geomitry.Plane;
import dk.Jonas.org.graphics.geomitry.Sphere;
import dk.Jonas.org.graphics.ray.Ray;
import dk.Jonas.org.vector.Vector3;

public class Screen extends Bitmap {
	public static final double MIN_BRIGHTNESS = 0.8;

	public static final Vector3 SKY_COLOR = new Vector3(0.121568627, 0.564705882, 1);

	public static final int MAX_NUM_OF_ITTERATIONS = 10;

	Camera cam;
	
	List<Geometry> geometries = new ArrayList<Geometry>();
	List<Light> light = new ArrayList<Light>();
	
	public Screen(int width, int height) {
		super(width, height);
		
		cam = new Camera(width, height, 90);
		
		//cam.pos = new Vector3(-3, 3, 0);
		//cam.rot = new Vector3(20, 30, -10);

		geometries.add(new Sphere(new Vector3(0, 1, 3), 2, PhysicsMask.REFLECT.getMask() | PhysicsMask.REFRACT.getMask(), 1.33));
		geometries.add(new Sphere(new Vector3(5, 0, 5), 2, Art.cage, PhysicsMask.REFLECT.getMask(), 1.0, 0));
		geometries.add(new Sphere(new Vector3(-1, 1, 12), 3, new Vector3(0, 0, 1)));
		geometries.add(new Plane(new Vector3(0, -3, 0), new Vector3(0, 1, 0), Art.cage));
		
		light.add(new Light(new Vector3(1, 0, 0)));
	}
	
	public double step = 0.1;
	
	public void render() {
		double xv = 0, zv = 0;

		if (InputHandler.keys[KeyEvent.VK_D]) xv++;
		else if (InputHandler.keys[KeyEvent.VK_A]) xv--;
		else if (InputHandler.keys[KeyEvent.VK_W]) zv++;
		else if (InputHandler.keys[KeyEvent.VK_S]) zv--;
		
		if (InputHandler.keys[KeyEvent.VK_RIGHT]) cam.rot.addEqual(new Vector3(0, 5, 0));
		else if (InputHandler.keys[KeyEvent.VK_LEFT]) cam.rot.addEqual(new Vector3(0, -5, 0));
		else if (InputHandler.keys[KeyEvent.VK_UP]) cam.rot.addEqual(new Vector3(-5, 0, 0));
		else if (InputHandler.keys[KeyEvent.VK_DOWN]) cam.rot.addEqual(new Vector3(5, 0, 0));
		
		double rot = Math.toRadians(cam.rot.y);
		
		cam.pos.addEqual(new Vector3(zv * Math.sin(rot) + xv * Math.cos(rot), 0, zv * Math.cos(rot) - xv * Math.sin(rot)).mul(0.1));
		
		fill(0, 0, width, height, 0xffffff);
		
		cam.updateRotation();
		
		//light.get(0).pos = new Vector3(Math.sin(Main.ticks/90.0)*7, 0, Math.cos(Main.ticks/90.0)*7+5);
		//geometries.get(0).pos = new Vector3(geometries.get(0).pos.x, Math.sin(Main.ticks/90.0)*2.0, geometries.get(0).pos.z);
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Ray r = cam.generateRay(x, y);
				
				Vector3 color = r.rayColor(geometries, light);
				
				pixels[x+y*width] = (int) (color.x * 0xff) << 16 | (int) (color.y * 0xff) << 8 | (int) (color.z * 0xff);
			}
		}
	}
}