package entity;

import java.awt.Rectangle;
import java.awt.geom.Area;

public abstract class Entity {
	
	protected EntityId id;
	protected float x;
	protected float y;
	protected int width;
	protected int height;
	
	public Entity(EntityId id, float x, float y, int width, int height) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public abstract void tick();
	
	public EntityId getId() {
		return id;
	}
	public void setId(EntityId id) {
		this.id = id;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
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
	public Area getBounds() {
		return new Area(new Rectangle((int)this.x, (int)this.y, this.width, this.height));
	}
}
