package packets;

public class SoundPacket extends Packet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1290648271584017612L;
	private SoundId sound;
	
	public SoundPacket(SoundId sound) {
		super(PacketId.SOUND);
		this.sound = sound;
	}
	
	public SoundId getSound() {
		return sound;
	}

}