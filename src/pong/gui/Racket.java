package pong.gui;

import java.awt.Point;
import java.awt.event.KeyEvent;

public class Racket extends PongItem {
	
	/**
	 * Speed of racket (in pixels per second)
	 */
	public static final int RACKET_SPEED = 4;
	
	private int speed;
	
	public Racket(String path) {
		super(path);
		this.speed = 0;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/* Update racket position */
	public void animateRacket() {
		setPositionY(getPositionY() + speed);
		
		if (getPositionY() < 0)
			setPositionY(0);
		if (getPositionY() > SIZE_PONG_Y - getHeight()/2)
			setPositionY(SIZE_PONG_Y - getHeight()/2);
	}
	
	/* Event (key pressed) */
	public void keyPressedRacket(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
			case KeyEvent.VK_KP_UP:
				this.speed = -RACKET_SPEED;
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_KP_DOWN:
				this.speed = RACKET_SPEED;
				break;
			default:
				System.out.println("got press "+e);
		}
	}
	
	public void keyReleasedRacket(KeyEvent e) {
		switch (e.getKeyCode()) {
	        case KeyEvent.VK_UP:
	        case KeyEvent.VK_KP_UP:
				this.speed = 0;
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_KP_DOWN:
				this.speed = 0;
				break;
			default:
				System.out.println("got release "+e);
			}
	}
}
