package client;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

public class Mouse implements MouseInputListener, MouseWheelListener {
	
	private Camera cam;
	private float x;
	private float y;
	
	public Mouse(Camera cam) {
		this.cam = cam;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		this.cam.changeZoom(e.getWheelRotation()/10.0d);
	}

	public float getX() {
		return Math.round( (((this.x - (this.cam.getX() * (1.0d/this.cam.getZoomFactor()))) * this.cam.getZoomFactor()) - (this.cam.getCanvas().getWidth()/2 * this.cam.getZoomFactor()) ) );
	}

	public float getY() {
		return Math.round( (((this.y - (this.cam.getY() * (1.0d/this.cam.getZoomFactor()))) * this.cam.getZoomFactor()) - (this.cam.getCanvas().getHeight()/2 * this.cam.getZoomFactor()) ) );
	}

}