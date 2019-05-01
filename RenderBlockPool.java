package pool;

import renderEntitys.RenderBlock;

public class RenderBlockPool extends ObjectPool<RenderBlock>{

	public RenderBlockPool(int coreSize, int maxSize) {
		super(coreSize, maxSize);
	}

	@Override
	public RenderBlock create() {
		return new RenderBlock(0, 0);
	}

}