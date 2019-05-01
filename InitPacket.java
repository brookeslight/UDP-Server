package packets;

public class InitPacket extends Packet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1859268936400062705L;
	
	private int width;
	private int height;
	private int maxAmmo;
	private int maxHealth;
	private float maxScope;
	
	public InitPacket(int width, int height, int maxAmmo, int maxHealth, float maxScope) {
		super(PacketId.INIT);
		this.width = width;
		this.height = height;
		this.maxAmmo = maxAmmo;
		this.maxHealth = maxHealth;
		this.maxScope = maxScope;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getMaxAmmo() {
		return maxAmmo;
	}

	public float getMaxScope() {
		return maxScope;
	}

}