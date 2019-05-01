package renderEntitys;

import java.awt.Color;
import java.awt.Graphics;

import entity.EntityId;

public class RenderBlock extends RenderEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9135501301812252504L;

	public RenderBlock(int x, int y) {
		super(EntityId.BLOCK, x, y, 64, 64);
	}
	
	public void init(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(this.x, this.y, this.width, this.height);
	}

}