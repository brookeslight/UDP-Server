package renderEntitys;

import java.awt.Graphics;
import java.io.Serializable;

import entity.EntityId;

public abstract class RenderEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8249770506362469204L;
	protected EntityId id;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	public RenderEntity(EntityId id, int x, int y, int width, int height) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public abstract void render(Graphics g);
	
	public EntityId getId() {
		return id;
	}
	public void setId(EntityId id) {
		this.id = id;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
}
