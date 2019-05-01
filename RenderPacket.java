package packets;

import java.util.ArrayList;

import renderEntitys.RenderEntity;

public class RenderPacket extends Packet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3233142036774923439L;
	private ArrayList<RenderEntity> renderEntities;
	private float x;
	private float y;
	private float health;
	private int ammo;
	
	public RenderPacket(ArrayList<RenderEntity> renderEntities, float x, float y, float health, int ammo) {
		super(PacketId.RENDER);
		this.renderEntities = renderEntities;
		this.x = x;
		this.y = y;
		this.health = health;
		this.ammo = ammo;
	}
	
	public void init(ArrayList<RenderEntity> renderEntities, float x, float y, float health, int ammo) {
		this.renderEntities = renderEntities;
		this.x = x;
		this.y = y;
		this.health = health;
		this.ammo = ammo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ArrayList<RenderEntity> getRenderEntities() {
		return renderEntities;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getHealth() {
		return health;
	}

	public int getAmmo() {
		return ammo;
	}

}