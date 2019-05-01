package pool;

import packets.UpdatePacket;

public class UpdatePacketPool extends ObjectPool<UpdatePacket> {

	public UpdatePacketPool(int coreSize, int maxSize) {
		super(coreSize, maxSize);
	}

	@Override
	public UpdatePacket create() {
		return new UpdatePacket(0, 0, 0, 0);
	}

}