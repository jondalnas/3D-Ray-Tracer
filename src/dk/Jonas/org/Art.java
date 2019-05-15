package dk.Jonas.org;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import dk.Jonas.org.graphics.Bitmap;

public class Art {
	public int[] pixels;
	public int width, height;
	
	public static Bitmap loadImage(String path) {
		
		if (!path.endsWith(".png")) {
			path = path + ".png";
		}
		
		try {
			BufferedImage image = ImageIO.read(Art.class.getResource(path));
	
			int w = image.getWidth();
			int h = image.getHeight();
			
			Bitmap result = new Bitmap(w, h);
			
			image.getRGB(0, 0, w, h, result.pixels, 0, w);
			
			for (int i = 0; i < result.pixels.length; i++) {
				if (result.pixels[i]>>24==0) {
					result.pixels[i] = -1;
				}
			}
			
			return result;
		} catch (IOException e) {
			System.out.println(path);
			throw new RuntimeException(e);
		}
	}
	
	public static void writeImage(String path, BufferedImage img) {
		File file = new File(path);
		try {
			file.createNewFile();
			ImageIO.write(img, "PNG", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int makeCol(int col) {
		return 0xff000000 + col;
	}
}
