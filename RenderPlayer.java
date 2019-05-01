package renderEntitys;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import entity.EntityId;

public class RenderPlayer extends RenderEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -575730904263507758L;
	private float mouseX;
	private float mouseY;
	
	public RenderPlayer(int x, int y, int width, int height, float mouseX, float mouseY) {
		super(EntityId.PLAYER, x, y, width, height);
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}
	
	public void init(int x, int y, int width, int height, float mouseX, float mouseY) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		AffineTransform af = g2d.getTransform();
		
		g.setColor(Color.blue);

		g2d.rotate(Math.atan2((this.y+(this.height/2)) - this.mouseY, (this.x+(this.width/2)) - this.mouseX) - Math.PI / 2, (this.x+(this.width/2)), (this.y+(this.height/2)) );
		g.fillRect(this.x, this.y, this.width, this.height);
		
		g2d.setTransform(af);
	}
	
}