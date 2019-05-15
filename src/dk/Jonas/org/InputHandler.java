package dk.Jonas.org;

import java.awt.event.*;

import dk.Jonas.org.graphics.Screen;

public class InputHandler implements KeyListener, FocusListener, MouseMotionListener {
	public boolean[] keys = new boolean[65536];
	public char charDown;
	
	public void focusGained(FocusEvent arg0) {
	}

	public void focusLost(FocusEvent e) {
		for (int i = 0; i < keys.length; i++)
			keys[i] = false;
	}

	public void keyPressed(KeyEvent e) {
		charDown = e.getKeyChar();
		int code = e.getKeyCode();
		if (code>0 && code<keys.length)
			keys[code] = true;
	}

	public void keyReleased(KeyEvent e) {
		charDown = ' ';
		int code = e.getKeyCode();
		if (code>0 && code<keys.length)
			keys[code] = false;
	}

	public void keyTyped(KeyEvent arg0) {
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}
}