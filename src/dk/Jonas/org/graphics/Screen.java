package dk.Jonas.org.graphics;

import dk.Jonas.org.graphics.ray.Ray;

public class Screen extends Bitmap {
	Camera cam;
	
	public Screen(int width, int height) {
		super(width, height);
		
		cam = new Camera(width, height, 90);
	}
	
	public double step = 0.1;
	
	public void render() {
		cam.updateRotation();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Ray r = cam.generateRay(x, y);
				
				for (int steps = 0; steps < 100; steps++) {
					r.step(step);
					
					if (r.collides()) {
						pixels[x+y*width] = 0xffffffff;
						break;
					}
				}
			}
		}
	}
}