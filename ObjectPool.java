package pool;

import java.util.concurrent.LinkedBlockingQueue;

public abstract class ObjectPool<T> {
	private int coreSize;
	private int maxSize;
	private LinkedBlockingQueue<T> pool; //maybe concurrent linked queue?
	
	public ObjectPool(int coreSize, int maxSize) {
		this.coreSize = coreSize;
		this.maxSize = maxSize;
		this.pool = new LinkedBlockingQueue<T>(this.maxSize);
		for(int i = 0; i < this.coreSize; i++) {
			this.pool.add(this.create());
		}
		if(this.pool.size() == this.coreSize) {
			System.out.println("Pool made Sucessfully");
		}
	}
	
	public T checkOut() {
		if(this.pool.isEmpty() == false) {
			return this.pool.poll();
		}else {
			return this.create();
		}
	}
	
	public void checkIn(T obj) {
		if(obj!=null) {
			this.pool.offer(obj);
		}else {
			this.pool.offer(this.create());
		}
	}
	
//	public T checkOut() {
//		if(this.pool.isEmpty() == false) {
//			T t = this.pool.poll();
//			if(t!=null) {
//				System.out.println("checked out " + t);
//				return t;
//			}else {
//				System.out.println("unable to pool?!?!?!??!!?!?");
//				return null;
//			}
//		}else {
//			System.out.println("nothing in pool so returning new");
//			return this.create();
//		}
//	}
//	
//	public void checkIn(T obj) {
//		if(obj!=null) {
//			if(this.pool.offer(obj) == true) {
//				System.out.println("sucsessfully checked in " + obj);
//			}else {
//				System.out.println("failed to checked in added " + obj);
//			}
//		}else {
//			if(this.pool.offer(this.create()) == true) {
//				System.out.println("sucsessfully checked in a new Object because what was checked in was null");
//			}else {
//				System.out.println("failed to checked in a new Object because what was checked in was null");
//			}
//		}
//	}
	
	public abstract T create();

	public int getCoreSize() {
		return this.coreSize;
	}
	
	public int getMaxSize() {
		return this.maxSize;
	}
	
	public int getCurrentSize() {
		return this.pool.size();
	}
	
	public void terminate() {
		this.pool.clear();
		this.pool = null;
	}
}