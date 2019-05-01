package entity;

public class Block extends Entity {

	public Block(float x, float y) {
		super(EntityId.BLOCK, x, y, 64, 64);
	}

	@Override
	public void tick() {
		//do nothing
	}

}