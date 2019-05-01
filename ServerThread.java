package host;

import java.net.InetSocketAddress;
import javax.swing.JTextArea;

import entity.Player;
import entity.Recruit;
import packets.AddPacket;
import packets.Packet;
import packets.SoundId;
import packets.UpdatePacket;

public class ServerThread implements Runnable {
	
	private Packet packet;
	private JTextArea area;
	private ClientHandler clientHandler;
	private InetSocketAddress address;
	private EntityHandler entityHandler;
	
	public ServerThread(Packet packet, JTextArea area, ClientHandler clientHandler, InetSocketAddress address, EntityHandler entityHandler) {
		this.packet = packet;
		this.area = area;
		this.clientHandler = clientHandler;
		this.address = address;
		this.entityHandler = entityHandler;
	}
	
	public void run() {
		//this.area.append(System.lineSeparator() + this.packet.getId());
		switch (this.packet.getId()) {
		case ADD:
			this.handleAddPacket();
			break;
		case DISCONNECT:
			this.handleDisconnectPacket();
			break;
		case UPDATE:
			this.handleUpdatePacket();
			break;
		default:
			this.area.append(System.lineSeparator() + "UNKNOWN PACKET RECEIVED");
			break;
		}
	}
	
	private void handleAddPacket() {
		AddPacket addPacket = (AddPacket) this.packet;
		Player p = new Recruit(0, 0, addPacket.getUserName());
		this.clientHandler.add(this.address, p);
		this.entityHandler.add(p);
		this.area.append(System.lineSeparator() + addPacket.getUserName() + " has joined " + "(" + this.address.getAddress().getHostAddress() + ":" + this.address.getPort() + ")");
		this.clientHandler.sendBackRender(this.address, this.entityHandler.getRenderEntities());
		this.clientHandler.sendBackInit(this.address);
		this.clientHandler.sendBackSound(this.address, SoundId.DOORBELL);
	}
	
	private void handleDisconnectPacket() {
		this.clientHandler.sendBackRender(this.address, this.entityHandler.getRenderEntities()); //maybe unnecessary?
		Player p = this.clientHandler.getPlayer(this.address);
		this.entityHandler.remove(p);
		this.clientHandler.remove(this.address);
		this.area.append(System.lineSeparator() + p.getUserName() + " has left " + "(" + this.address.getAddress().getHostAddress() + ":" + this.address.getPort() + ")");
	}
	
	private void handleUpdatePacket() {
		UpdatePacket updatePacket = (UpdatePacket) this.packet;
		Player p = this.clientHandler.getPlayer(this.address);
		if(updatePacket.getVelX() == -1) {
			p.setVelX(-p.getMaxVelocity());
		}else if(updatePacket.getVelX() == 1) {
			p.setVelX(p.getMaxVelocity());
		}else {
			p.setVelX(0);
		}
		if(updatePacket.getVelY() == -1) {
			p.setVelY(-p.getMaxVelocity());
		}else if(updatePacket.getVelY() == 1) {
			p.setVelY(p.getMaxVelocity());
		}else {
			p.setVelY(0);
		}
		p.setMouseX(updatePacket.getMouseX());
		p.setMouseY(updatePacket.getMouseY());
		this.clientHandler.sendBackRender(this.address, this.entityHandler.getRenderEntities());
	}
}