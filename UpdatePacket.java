package packets;

public class UpdatePacket extends Packet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7215178923634571554L;
	private int velX;
	private int velY;
	private float mouseX;
	private float mouseY;
	
	public UpdatePacket(int velX, int velY, float mouseX, float mouseY) {
		super(PacketId.UPDATE);
		this.velX = velX;
		this.velY = velY;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}
	
	public void init (int velX, int velY, float mouseX, float mouseY) {
		this.velX = velX;
		this.velY = velY;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}

	public int getVelX() {
		return velX;
	}

	public int getVelY() {
		return velY;
	}

	public float getMouseX() {
		return mouseX;
	}

	public float getMouseY() {
		return mouseY;
	}

}