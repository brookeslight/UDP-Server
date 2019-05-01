package packets;

public class DisconnectPacket extends Packet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4435604985758187928L;

	public DisconnectPacket() {
		super(PacketId.DISCONNECT);
	}

}
