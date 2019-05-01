package entity;

public enum PlayerId {
	
	
	RECRUIT(150,4);
	
	private float health;
	private float maxVelocity;
	
	private PlayerId(float health, float maxVelocity) {
		this.health = health;
		this.maxVelocity = maxVelocity;
	}
	
	public float getHealth() {
		return health;
	}
	public float getMaxVelocity() {
		return maxVelocity;
	}
}