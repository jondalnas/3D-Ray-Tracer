package dk.Jonas.org;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dk.Jonas.org.graphics.Screen;

public class Main extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 1920;//640;
	public static final int HEIGHT = 1080;//480;
	public static final int SCALE = 1; //4

	private boolean running;
	private Thread thread;

	private Screen screen;
	private BufferedImage img;
	private int[] pixels;
	private InputHandler input;
	
	public static int ticks;

	public Main() {
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		
		setSize(size);
		
		screen = new Screen(WIDTH, HEIGHT);
		input = new InputHandler();

		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		
		addKeyListener(input);
		addFocusListener(input);
		addMouseMotionListener(input);
		
	}

	public synchronized void start() {
		if (running) return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		int frames = 0;
		long lastNano = System.nanoTime();
		
		while (true) {
			tick();
			render();
			
			frames++;
			if (frames == 10) {
				System.out.println((frames / (double) ((System.nanoTime() - lastNano) / 1000000000L)) + " FPS");
				frames = 0;
				lastNano = System.nanoTime();
			}
		}
	}

	private void tick() {
		ticks++;
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.render();

		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Main game = new Main();

		JFrame frame = new JFrame("Graphics Engine");
		JPanel panel = new JPanel(new BorderLayout());

		panel.add(game, 0);

		frame.setContentPane(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		frame.setSize(new Dimension(frame.getWidth(), frame.getHeight()));
		frame.setPreferredSize(frame.getSize());

		game.start();
	}
}
