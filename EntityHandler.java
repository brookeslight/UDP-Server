package host;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import entity.Block;
import entity.Entity;
import entity.EntityId;
import entity.Player;
import pool.RenderBlockPool;
import pool.RenderPlayerPool;
import renderEntitys.RenderBlock;
import renderEntitys.RenderEntity;
import renderEntitys.RenderPlayer;

public class EntityHandler implements Runnable {
	private ConcurrentHashMap<EntityId, CopyOnWriteArrayList<Entity>> entities = new ConcurrentHashMap<EntityId, CopyOnWriteArrayList<Entity>>();
	private Thread thread;
	private boolean running = false;
	//
	private ArrayList<RenderEntity> renderEntities = new ArrayList<RenderEntity>();
	private ArrayList<RenderEntity> temp = new ArrayList<RenderEntity>();
	//
	private RenderPlayerPool renderPlayerPool = new RenderPlayerPool(15, 20);
	private RenderBlockPool renderBlockPool = new RenderBlockPool(15, 20);
	
	public void add(Entity entity) {
		if(this.entities.containsKey(entity.getId())) {
			this.entities.get(entity.getId()).add(entity);
		}else {
			this.entities.put(entity.getId(), new CopyOnWriteArrayList<Entity>());
			this.entities.get(entity.getId()).add(entity);
		}
	}
	
	public void remove(Entity entity) {
		this.entities.get(entity.getId()).remove(entity);
	}
	
	public synchronized void start() {
		if(this.running == true) {
			return;
		}
		this.running = true;
		this.thread = new Thread(this, "Game Tick Thread");
		this.thread.start();
	}
	
	public synchronized void close() {
		this.running = false;
		this.thread.interrupt();
	}

	@Override
	public void run() {
		this.add(new Block(0,0));
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		while(this.running == true){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				this.tick();
				updates++;
				delta--;
			}
					
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("TICKS: " + updates);
				updates = 0;
			}
		}
	}
	
	private void tick() {
		this.temp = new ArrayList<RenderEntity>();
		for(List<Entity> entityList: this.entities.values()) {
			for(Entity e: entityList) {
				e.tick();
				if(e.getId() == EntityId.PLAYER) {
					 Player p = (Player) e;
					 RenderPlayer pl = this.renderPlayerPool.checkOut();
					 pl.init((int)p.getX(), (int)p.getY(), p.getWidth(), p.getHeight(), p.getMouseX(), p.getMouseY());
					 this.temp.add(pl);
					 this.renderPlayerPool.checkIn(pl);
				}else if(e.getId() == EntityId.BLOCK) {
					RenderBlock block = this.renderBlockPool.checkOut();
					block.init((int)e.getX(), (int)e.getY());
					this.temp.add(block);
					this.renderBlockPool.checkIn(block);
				}
			}
		}
		this.renderEntities = this.temp;
	}

	public ArrayList<RenderEntity> getRenderEntities() {
		return new ArrayList<RenderEntity>(this.renderEntities);
	}
	
	
	
//	public ArrayList<RenderEntity> getRenderEntities() {
//		 ArrayList<RenderEntity> renderEntities = new  ArrayList<RenderEntity>();
//		 if(this.entities.containsKey(EntityId.PLAYER)) {
//			 for(Entity e: this.entities.get(EntityId.PLAYER)) {
//				 Player p = (Player) e;
//				 renderEntities.add(new RenderPlayer(p.getX(), p.getY(), p.getWidth(), p.getHeight(), p.getMouseX(), p.getMouseY()));
//			 }
//		 }
//		 return renderEntities;
//	}

}