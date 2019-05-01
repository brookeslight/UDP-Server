package pool;

import renderEntitys.RenderPlayer;

public class RenderPlayerPool extends ObjectPool<RenderPlayer>{

	public RenderPlayerPool(int coreSize, int maxSize) {
		super(coreSize, maxSize);
	}

	@Override
	public RenderPlayer create() {
		return new RenderPlayer(0, 0, 0, 0, 0, 0);
	}

}