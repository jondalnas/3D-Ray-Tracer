package dk.Jonas.org.graphics;

public class Bitmap {
	public final int width, height;
	public final int[] pixels;
	public final int[] pixels11;
	public final int[] pixels12;

	public Bitmap(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		pixels11 = new int[width * height];
		pixels12 = new int[width * height];
		
		for (int i = 0; i < pixels.length; i++) {
			pixels11[i] = -1;
			pixels12[i] = -1;
		}
	}

	public void draw(Bitmap bitmap, double x, double y) {
		for (int y0 = 0; y0 < bitmap.height; y0++) {
			double yp = y0 + y;
			if (yp < 0  || yp >= height) continue;
			
			for (int x0 = 0; x0 < bitmap.width; x0++) {
				if (bitmap.pixels[x0 + y0 * bitmap.width] == -1) continue;

				double xp = x0 + x;
				if (xp < 0  || xp >= width) continue;
				
				setPixel(xp, yp, bitmap.getPixel(x0, y0));
			}
		}
	}
	
	public void draw(Bitmap bitmap, double x0, double y0, double xScale, double yScale, double rot) {
		rot = Math.toRadians(rot);
		
		double pi = Math.PI;
		
		xScale *= pi;
		yScale *= pi;
	
		double cosr0 = Math.cos(rot);
		double sinr0 = Math.sin(rot);
		double cosr1 = Math.cos(rot + pi / 2);
		double sinr1 = Math.sin(rot + pi / 2);
		
		double w = bitmap.width * xScale;
		double h = bitmap.height * yScale;

		double wdt = (w/pi)/2;
		double hgt = (h/pi)/2;
		
		for (int yc = 0; yc < h; yc++) {
			double ys = yc / yScale;
			if (ys < 0 || ys >= bitmap.height) continue;
			
			double ye = (yc/pi)-hgt;
			
			for (int xc = 0; xc < w; xc++) {
				double xs = xc / xScale;
				if (xs < 0 || xs >= bitmap.width || bitmap.getPixel(xs, ys) == -1) continue;
				
				double xe = (xc/pi)-wdt;
				
				double xp = x0 + cosr0 * xe + cosr1 * ye;
				if (xp < 0 || xp >= width) continue;
				
				double yp = y0 + sinr0 * xe + sinr1 * ye;
				if (yp < 0 || yp >= height) continue;
				
				setPixel(xp, yp, bitmap.getPixel(xs, ys));
			}
		}
	}
	
	public void draw(Bitmap bitmap, double x0, double y0, double x1, double y1) {
		double x = x1 - x0;
		double y = y1 - y0;
		
		double xScale = x / bitmap.width;
		double yScale = y / bitmap.height;
	
		for (int yc = 0; yc < y; yc++) {
			double yp = yc + y0;
			if (yp < 0 || yp >= height) continue;
			
			double ys = yc / yScale;
			if (ys < 0 || ys >= bitmap.height) continue;
			
			for (int xc = 0; xc < x; xc++) {
				double xp = xc + x0;
				if (xp < 0 || xp >= width) continue;
				
				double xs = xc / xScale;
				if (xs < 0 || xs >= bitmap.width || bitmap.getPixel(xs, ys) == -1) continue;
				
				setPixel(xp, yp, bitmap.getPixel(xs, ys));
			}
		}
	}
	
	public void draw(Bitmap bitmap, int x, int y, double rot) {
		rot = Math.toRadians(rot);
		
		double w = bitmap.width;
		double h = bitmap.height;
		
		double pi = Math.PI;
		
		double cosr0 = Math.cos(rot);
		double sinr0 = Math.sin(rot);
		double cosr1 = Math.cos(rot + pi / 2);
		double sinr1 = Math.sin(rot + pi / 2);
		
		double wpi = (bitmap.width / pi) / 2;
		double hpi = (bitmap.height / pi) / 2;
		
		for (int y0 = 0; y0 < h; y0++) {
			if (y0 < 0 || y0 >= bitmap.height) continue;
			double y0p = (y0 / pi) - hpi;
			for (int x0 = 0; x0 < w; x0++) {
				if (x0 < 0 || x0 >= bitmap.width || bitmap.pixels[x0 + y0 * bitmap.width] == -1) continue;
				double x0p = (x0 / pi) - wpi;
				
				double xp = (x + cosr0 * x0p + cosr1 * y0p);
				if (xp < 0 || xp >= width) continue;
				
				double yp = (y + sinr0 * x0p + sinr1 * y0p);
				if (yp < 0 || yp >= height) continue;
				
				setPixel(xp, yp, bitmap.getPixel(x0, y0));
			}
		}
	}
	
	public void fill(int x0, int y0, int x1, int y1, int col) {
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				pixels[x + y * width] = col;
			}
		}
	}

	public void drawSqure(int x0, int y0, int x1, int y1, int col) {
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				if (x == x0 || y == y0 || x == x1 - 1 || y == y1 - 1)
					pixels[x + y * width] = col;
			}
		}
	}
	
	public void drawLine(double x, double y, double x2, double y2, int color) {
		double w = x2 - x;
		double h = y2 - y;
		int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
		if (w<0) dx1 = -1 ; else if (w>0) dx1 = 1;
		if (h<0) dy1 = -1 ; else if (h>0) dy1 = 1;
		if (w<0) dx2 = -1 ; else if (w>0) dx2 = 1;
		int longest = Math.abs((int) w);
		int shortest = Math.abs((int) h);
		if (!(longest>shortest)) {
			longest = Math.abs((int) h);
			shortest = Math.abs((int) w);
			if (h<0) dy2 = -1;
			else if (h>0) dy2 = 1;
			dx2 = 0;
		}
		int numerator = longest >> 1;
		for (int i = 0; i <= longest; i++) {
			if (x < 0 || x >= width || y < 0 || y >= height) return;
			setPixel(x, y, color);
			numerator += shortest;
			if (!(numerator<longest)) {
				numerator -= longest;
				x += dx1;
				y += dy1;
			} else {
				x += dx2;
				y += dy2;
			}
		}
	}

	public void fillCircle(int x0, int y0, int radius, int col) {
		radius--;
		for (int y = y0 - radius - 1; y <= y0 + radius + 1; y++) {

			if (y < 0 || y >= height) continue;

			for (int x = x0 - radius - 1; x <= x0 + radius + 1; x++) {

				if (x < 0 || x >= width) continue;

				int xx = x - x0;
				int yy = y - y0;

				if (xx * xx + yy * yy < radius * radius)
					pixels[x + y * width] = col;
			}
		}
	}

	public void drawCircle(int x, int y, int r, int col) {
		for (double i = 0; i < 360; i += 0.1) {
			double xx = Math.cos(Math.toRadians(i))*r+x;
			double yy = Math.sin(Math.toRadians(i))*r+y;

			int xx1 = (int) (xx*2);
			int yy1 = (int) (yy*2);
			
			if (xx1 % 2 == 0 && yy1 % 2 == 0) pixels11[xx1/2 + yy1/2 * width] = col;
			else pixels12[xx1/2 + yy1/2 * width] = col;
		}
	}
	
	/*public static void FillTriangleSimple(Bitmap bitmap, int x0, int y0, int x1, int y1, int x2, int y2, int color) {
		int[] pixels = bitmap.pixels;
		int width = bitmap.width;
		int height = bitmap.height;
		// sort the points vertically
		if (y1 > y2) {
			int xx = x1;
			x1 = x2;
			x2 = xx;
			
			int yy = y1;
			y1 = y2;
			y2 = yy;
		}
		
		if (y0 > y1) {
			swap(ref x0, ref x1);
			swap(ref y0, ref y1);
		}
		
		if (y1 > y2) {
			swap(ref x1, ref x2);
			swap(ref y1, ref y2);
		}
		
		double dx_far = (double) (x2 - x0) / (y2 - y0 + 1);
		double dx_upper = (double) (x1 - x0) / (y1 - y0 + 1);
		double dx_low = (double) (x2 - x1) / (y2 - y1 + 1);
		double xf = x0;
		double xt = x0 + dx_upper; // if y0 == y1, special case
		for (int y = y0; y <= (y2 > height-1 ? height-1 : y2); y++)
		{
			if (y >= 0)
			{
				for (int x = (int) (xf > 0 ? xf : 0); x <= (xt < width ? xt : width-1) ; x++)
					pixels[x + y * width] = color;
				for (int x = (int) (xf < width ? xf : width-1); x >= (xt > 0 ? xt : 0); x--)
					pixels[x + y * width] = color;
			}
			xf += dx_far;
			if (y < y1)
				xt += dx_upper;
			else
				xt += dx_low;
		}
	}*/
	
	public void setPixel(double x, double y, int col) {
		if (x < 0 || y < 0 || x >= width || y >= height) return;
		
		int xx = (int) (x*2);
		int yy = (int) (y*2);
		
		if (xx % 2 == 0 && yy % 2 == 0) pixels11[xx/2 + yy/2 * width] = col;
		else pixels12[xx/2 + yy/2 * width] = col;
	}
	
	public int getPixel(double x, double y) {
		int xx = (int) (x*2);
		int yy = (int) (y*2);
		
		if (xx % 2 == 0 && yy % 2 == 0) return pixels[xx/2 + yy/2 * width];
		else {
			int r00 = ((pixels[xx/2 + yy/2 * width]>>16)&0xff)/4;
			int g00 = ((pixels[xx/2 + yy/2 * width]>>8)&0xff)/4;
			int b00 = ((pixels[xx/2 + yy/2 * width])&0xff)/4;

			int r10 = (((x+1>=width?-1:pixels[(xx+1)/2 + yy/2 * width])>>16)&0xff)/4;
			int g10 = (((x+1>=width?-1:pixels[(xx+1)/2 + yy/2 * width])>>8)&0xff)/4;
			int b10 = (((x+1>=width?-1:pixels[(xx+1)/2 + yy/2 * width]))&0xff)/4;

			int r01 = (((y+1>=height?-1:pixels[xx/2 + (yy+1)/2 * width])>>16)&0xff)/4;
			int g01 = (((y+1>=height?-1:pixels[xx/2 + (yy+1)/2 * width])>>8)&0xff)/4;
			int b01 = (((y+1>=height?-1:pixels[xx/2 + (yy+1)/2 * width]))&0xff)/4;

			int r11 = (((x+1>=width||y+1>=height?-1:pixels[(xx+1)/2 + (yy+1)/2 * width])>>16)&0xff)/4;
			int g11 = (((x+1>=width||y+1>=height?-1:pixels[(xx+1)/2 + (yy+1)/2 * width])>>8)&0xff)/4;
			int b11 = (((x+1>=width||y+1>=height?-1:pixels[(xx+1)/2 + (yy+1)/2 * width]))&0xff)/4;

			int r = r00+r10+r01+r11;
			int g = g00+g10+g01+g11;
			int b = b00+b10+b01+b11;
			
			return (r<<16)|(g<<8)|(b);
		}
	}

	public void prossesPixels() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (pixels11[x+y*width] != -1) pixels[x+y*width] = pixels11[x+y*width];
				if (pixels12[x+y*width] != -1) {
					int r0 = (int) (((pixels12[x+y*width]>>16) & 0xff)/4)*3;
					int g0 = (int) (((pixels12[x+y*width]>>8) & 0xff)/4)*3;
					int b0 = (int) (((pixels12[x+y*width]) & 0xff)/4)*3;
					
					
					
					int r00 = ((pixels[x+y*width]>>16) & 0xff)/4;
					int g00 = ((pixels[x+y*width]>>8) & 0xff)/4;
					int b00 = ((pixels[x+y*width]) & 0xff)/4;
					
					int r10 = (((x+1>=width?0:pixels[(x+1)+y*width])>>16) & 0xff)/4;
					int g10 = (((x+1>=width?0:pixels[(x+1)+y*width])>>8) & 0xff)/4;
					int b10 = (((x+1>=width?0:pixels[(x+1)+y*width])) & 0xff)/4;
					
					int r01 = (((y+1>=height?0:pixels[x+(y+1)*width])>>16) & 0xff)/4;
					int g01 = (((y+1>=height?0:pixels[x+(y+1)*width])>>8) & 0xff)/4;
					int b01 = (((y+1>=height?0:pixels[x+(y+1)*width])) & 0xff)/4;
					
					int r11 = (((y+1>=height||x+1>=width?0:pixels[(x+1)+(y+1)*width])>>16) & 0xff)/4;
					int g11 = (((y+1>=height||x+1>=width?0:pixels[(x+1)+(y+1)*width])>>8) & 0xff)/4;
					int b11 = (((y+1>=height||x+1>=width?0:pixels[(x+1)+(y+1)*width])) & 0xff)/4;
					
					
					
					int r000 = r0+r00;
					int g000 = g0+g00;
					int b000 = b0+b00;
					
					int r100 = r0+r10;
					int g100 = g0+g10;
					int b100 = b0+b10;
					
					int r010 = r0+r01;
					int g010 = g0+g01;
					int b010 = b0+b01;
					
					int r110 = r0+r11;
					int g110 = g0+g11;
					int b110 = b0+b11;
					
					

					pixels[x+y*width] = (r000<<16) | (g000<<8) | (b000);
					if (x+1<width) pixels[(x+1)+y*width] = (r100<<16) | (g100<<8) | (b100);
					if (y+1<height) pixels[x+(y+1)*width] = (r010<<16) | (g010<<8) | (b010);
					if (y+1<height&&x+1<width) pixels[(x+1)+(y+1)*width] = (r110<<16) | (g110<<8) | (b110);
				}
				
				pixels11[x+y*width] = -1;
				pixels12[x+y*width] = -1;
			}
		}
	}

	public void renderScreen(Bitmap bitmap, int col) {
		for (int y0 = 0; y0 < bitmap.height; y0++) {
			for (int x0 = 0; x0 < bitmap.width; x0++) {
				if (pixels[x0 + y0 * width] != bitmap.pixels[x0 + y0 * width]) {
					pixels[x0 + y0 * width] = bitmap.pixels[x0 + y0 * width];
				}
				
				bitmap.pixels[x0 + y0 * width] = col;
			}
		}
	}
}