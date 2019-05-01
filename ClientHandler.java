package host;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import entity.Player;
import packets.InitPacket;
import packets.RenderPacket;
import packets.SoundId;
import packets.SoundPacket;
import pool.RenderPacketPool;
import renderEntitys.RenderEntity;

public class ClientHandler {
	
	private ConcurrentHashMap<InetSocketAddress, Player> clients = new ConcurrentHashMap<InetSocketAddress, Player>();
	private DatagramSocket socket;
	private byte[] byteBuffer = new byte[2048];
	private RenderPacketPool renderPacketPool;
	
	public ClientHandler(DatagramSocket socket) {
		this.socket = socket;
		this.renderPacketPool = new RenderPacketPool(15, 20);
	}
	
	public void add(InetSocketAddress address, Player player) {
		this.clients.put(address, player);
	}
	
	public void remove(InetSocketAddress address) {
		this.clients.remove(address);
	}
	
	public Player getPlayer(InetSocketAddress address) {
		return this.clients.get(address);
	}
	
	public void sendBackRender(InetSocketAddress address, ArrayList<RenderEntity> renderEntities) {
		Player p = this.clients.get(address);
		RenderPacket renderPacket = this.renderPacketPool.checkOut();
		renderPacket.init(renderEntities, p.getX(), p.getY(), p.getHealth(), p.getAmmo());
		this.byteBuffer = renderPacket.getBytes();
		this.renderPacketPool.checkIn(renderPacket);
		try {
			this.socket.send(new DatagramPacket(this.byteBuffer, this.byteBuffer.length, address.getAddress(), address.getPort()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendBackInit(InetSocketAddress address) { //pool is unnecessary because they are only sent once
		Player p = this.clients.get(address);
		InitPacket initPacket = new InitPacket(p.getWidth(), p.getHeight(), p.getAmmo(), (int)p.getHealth(), p.getScope());
		this.byteBuffer = initPacket.getBytes();
		try {
			this.socket.send(new DatagramPacket(this.byteBuffer, this.byteBuffer.length, address.getAddress(), address.getPort()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendBackSound(InetSocketAddress address, SoundId sound) { //pool is unnecessary because they are not sent as frequently
		try {
			SoundPacket soundPacket = new SoundPacket(sound);
			this.byteBuffer = soundPacket.getBytes();
			this.socket.send(new DatagramPacket(this.byteBuffer, this.byteBuffer.length, address.getAddress(), address.getPort()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}