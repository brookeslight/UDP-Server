package packets;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Packet implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4978985760972570930L;
	protected PacketId id;
	
	public Packet(PacketId id) {
		super();
		this.id = id;
	}

	public PacketId getId() {
		return id;
	}
	
	public byte[] getBytes() {
		byte [] data = null;
		try(ByteArrayOutputStream byteOut = new ByteArrayOutputStream(); ObjectOutputStream objectOut = new ObjectOutputStream(new BufferedOutputStream(byteOut))) {
			objectOut.writeObject(this);
			objectOut.flush();
			data = byteOut.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
}