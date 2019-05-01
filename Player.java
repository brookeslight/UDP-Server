package entity;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import quadTree.Bounds;

public abstract class  Player extends Entity {
	protected PlayerId playerId;
	protected Gun gun;
	protected String userName;
	protected float velX;
	protected float velY;
	protected float mouseX;
	protected float mouseY;
	protected float maxVelocity;
	protected float health;
	protected float scope;
	protected int ammo;
	
	public Player(float x, float y, PlayerId playerId, String userName, Gun gun) {
		super(EntityId.PLAYER, x, y, 64, 64);
		this.playerId = playerId;
		this.gun = gun;
		this.userName = userName;
		this.velX = 0;
		this.velY = 0;
		this.mouseX = 0;
		this.mouseY = 0;
		this.maxVelocity = playerId.getMaxVelocity();
		this.health = playerId.getHealth();
		this.scope = gun.getScope();
		this.ammo = gun.getMaxAmmo();
	}
	
	public abstract void useSpecial();

	public PlayerId getPlayerId() {
		return playerId;
	}
	
	public Gun getGun() {
		return gun;
	}

	public void setGun(Gun gun) {
		this.gun = gun;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}

	public float getMouseX() {
		return mouseX;
	}

	public void setMouseX(float mouseX) {
		this.mouseX = mouseX;
	}

	public float getMouseY() {
		return mouseY;
	}

	public void setMouseY(float mouseY) {
		this.mouseY = mouseY;
	}

	public float getMaxVelocity() {
		return maxVelocity;
	}

	public void setMaxVelocity(float maxVelocity) {
		this.maxVelocity = maxVelocity;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public void setPlayerId(PlayerId playerId) {
		this.playerId = playerId;
	}
	

	public float getScope() {
		return scope;
	}

	public void setScope(float scope) {
		this.scope = scope;
	}

	public int getAmmo() {
		return ammo;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	public Area getBounds() {
		AffineTransform af = new AffineTransform();
		af.rotate(Math.atan2((this.y+(this.height/2)) - this.mouseY, (this.x+(this.width/2)) - this.mouseX) - Math.PI / 2, (this.x+(this.width/2)), (this.y+(this.height/2)));
		return new Area(new Rectangle((int)this.x, (int)this.y, this.width, this.height)).createTransformedArea(af);
	}
	public Area getBoundsRight() {
		AffineTransform af = new AffineTransform();
		af.rotate(Math.atan2((this.y+(this.height/2)) - this.mouseY, (this.x+(this.width/2)) - this.mouseX) - Math.PI / 2, (this.x+(this.width/2)), (this.y+(this.height/2)));
		return new Area(new Rectangle((int)this.x, (int)this.y, this.width/4, this.height)).createTransformedArea(af);
	}
	public Area getBoundsLeft() {
		AffineTransform af = new AffineTransform();
		af.rotate(Math.atan2((this.y+(this.height/2)) - this.mouseY, (this.x+(this.width/2)) - this.mouseX) - Math.PI / 2, (this.x+(this.width/2)), (this.y+(this.height/2)));
		return new Area(new Rectangle((int)this.x+(this.width/4*3), (int)this.y, this.width/4, this.height)).createTransformedArea(af);
	}
	public Area getBoundsTop() {
		AffineTransform af = new AffineTransform();
		af.rotate(Math.atan2((this.y+(this.height/2)) - this.mouseY, (this.x+(this.width/2)) - this.mouseX) - Math.PI / 2, (this.x+(this.width/2)), (this.y+(this.height/2)));
		return new Area(new Rectangle((int)this.x+(this.width/4), (int)this.y, this.width/2, this.height/2)).createTransformedArea(af);
	}
	public Area getBoundsBottom() {
		AffineTransform af = new AffineTransform();
		af.rotate(Math.atan2((this.y+(this.height/2)) - this.mouseY, (this.x+(this.width/2)) - this.mouseX) - Math.PI / 2, (this.x+(this.width/2)), (this.y+(this.height/2)));
		return new Area(new Rectangle((int)this.x+(this.width/4), (int)this.y+(this.height/2), this.width/2, this.height/2)).createTransformedArea(af);
	}
	public AffineTransform getTransfrom() {
		AffineTransform af = new AffineTransform();
		af.rotate(Math.atan2((this.y+(this.height/2)) - this.mouseY, (this.x+(this.width/2)) - this.mouseX) - Math.PI / 2, (this.x+(this.width/2)), (this.y+(this.height/2)));
		return af;
	}
	public Bounds queryBounds(int r) {
		return new Bounds(this.x + r,  this.y + r, this.width + 2*r, this.health + 2*r);
	}
}