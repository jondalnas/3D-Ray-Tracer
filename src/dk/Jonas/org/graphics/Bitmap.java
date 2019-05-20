package dk.Jonas.org.graphics;

import dk.Jonas.org.vector.Vector3;

public class Bitmap {
	public final int width, height;
	public final int[] pixels;

	public Bitmap(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	
	public void fill(int x0, int y0, int x1, int y1, int col) {
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				pixels[x + y * width] = col;
			}
		}
	}
	
	public Vector3 getPixelVector(double x, double y) {
		int color = pixels[(int) (x * width) + (int) (y * height) * width];
		
		return new Vector3((color >> 16) & 0xff, (color >> 8) & 0xff, color & 0xff).mul(1/255.0);
	}
}