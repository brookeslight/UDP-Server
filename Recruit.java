package entity;

public class Recruit extends Player {

	public Recruit(float x, float y, String userName) {
		super(x, y, PlayerId.RECRUIT, userName, Gun.DMR);
	}
	
	@Override
	public void tick() {
		this.x += this.velX;
		this.y += this.velY;
	}

	@Override
	public void useSpecial() {
		
	}

}