package pool;

import packets.RenderPacket;

public class RenderPacketPool extends ObjectPool<RenderPacket> {

	public RenderPacketPool(int coreSize, int maxSize) {
		super(coreSize, maxSize);
	}

	@Override
	public RenderPacket create() {
		return new RenderPacket(null, 0, 0, 0, 0);
	}

}