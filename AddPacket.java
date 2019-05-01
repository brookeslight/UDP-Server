package packets;

public class AddPacket extends Packet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 224657633507771466L;
	
	private String userName;
	
	public AddPacket(String userName) {
		super(PacketId.ADD);
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}
	
}