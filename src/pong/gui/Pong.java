package pong.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

/**
 * An Pong is a Java graphical container that extends the JPanel class in
 * order to display graphical elements.
 */
public class Pong extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Constant (c.f. final) common to all Pong instances (c.f. static)
	 * defining the background color of the Pong
	 */
    private static final Color backgroundColor = new Color(0xC, 0x2D, 0x4E);

	/**
	 * Width of pong area
	 */
	public static final int SIZE_PONG_X = 800;
	/**
	 * Height of pong area
	 */
	public static final int SIZE_PONG_Y = 600;
	/**
	 * Time step of the simulation (in ms)
	 */
	public static final int timestep = 10;

	/**
	 * Object Ball 
	 */
	private Ball ball;
		
	/**
	 * Ensemble contenant les objets Racket (une raquette par joueur)
	 */
	private Set<Racket> setRacket;

	/**
	 * Pixel data buffer for the Pong rendering
	 */
	private Image buffer = null;
	/**
	 * Graphic component context derived from buffer Image
	 */
	private Graphics graphicContext = null;


	public Pong() {
		this.ball = new Ball("image/ball.png");
		
		this.setRacket = new HashSet<Racket>();
		setRacket.add(new Racket("image/racket.png", 1));
		setRacket.add(new Racket("image/racket.png", 2));
		
		this.setPreferredSize(new Dimension(SIZE_PONG_X, SIZE_PONG_Y));
		this.addKeyListener(this);
	}

	/**
     * Proceeds to the movement of the rackets, the ball and updates the screen
	 */
	public void animate() {
		
		/* L'iterateur sert à parcourir l'ensemble des raquettes */
		Iterator<Racket> it = setRacket.iterator();
		while(it.hasNext()) {
			Racket racket = it.next();
			racket.animateRacket();
		}

		ball.animateBall(setRacket);
		
		/* Update output */
		updateScreen();
	}

	public void keyPressed(KeyEvent e) {
		Iterator<Racket> it = setRacket.iterator();
		while(it.hasNext()) {
			Racket racket = it.next();
			racket.keyPressedRacket(e);
		}
	}
	
	public void keyReleased(KeyEvent e) {
		Iterator<Racket> it = setRacket.iterator();
		while(it.hasNext()) {
			Racket racket = it.next();
			racket.keyReleasedRacket(e);
		}
	}
	public void keyTyped(KeyEvent e) { }

	/*
	 * (non-Javadoc) This method is called by the AWT Engine to paint what
	 * appears in the screen. The AWT engine calls the paint method every time
	 * the operative system reports that the canvas has to be painted. When the
	 * window is created for the first time paint is called. The paint method is
	 * also called if we minimize and after we maximize the window and if we
	 * change the size of the window with the mouse.
	 * 
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		g.drawImage(buffer, 0, 0, this);
	}

	/**
	 * Draw each Pong item based on new positions
	 */
	public void updateScreen() {
		
		if (buffer == null) {
			/* First time we get called with all windows initialized */
			buffer = createImage(SIZE_PONG_X, SIZE_PONG_Y);
			if (buffer == null)
				throw new RuntimeException("Could not instanciate graphics");
			else
				graphicContext = buffer.getGraphics();
		}
		
		/* Fill the area with blue */
		graphicContext.setColor(backgroundColor);
		graphicContext.fillRect(0, 0, SIZE_PONG_X, SIZE_PONG_Y);

		/* Draw items */
		graphicContext.drawImage(ball.getImg(), ball.getPosition().x, ball.getPosition().y, ball.getWidth(), ball.getHeight(), null);
		
		Iterator<Racket> it = setRacket.iterator();
		while(it.hasNext()) {
			Racket racket = it.next();
			graphicContext.drawImage(racket.getImg(), racket.getPosition().x, racket.getPosition().y, racket.getWidth(), racket.getHeight(), null);
		}
		
		this.repaint();
	}
}
