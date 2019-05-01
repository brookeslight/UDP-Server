package client;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

import packets.AddPacket;
import packets.DisconnectPacket;
import packets.InitPacket;
import packets.Packet;
import packets.RenderPacket;
import packets.SoundPacket;
import packets.UpdatePacket;
import pool.UpdatePacketPool;
import renderEntitys.RenderEntity;

public class Game extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1889322491288035275L;
	private Thread thread;
	private boolean running = false;
	private JFrame frame;
	//
	private InetAddress address;
	private static final int port = 3245;
	private DatagramSocket socket;
	private byte[] receiveByteBuffer = new byte[2048];
	private byte[] sendByteBuffer = new byte[2048];
	private ArrayList<RenderEntity> renderEntities;
	private ClientPlayer player;
	private Camera cam;
	private BufferedImage background;
	private Key key;
	private Mouse mouse;
	private long timeSent;
	private int ping;
	private UpdatePacketPool pool;
	
	public static void main(String[] args) {
		new Game().start();
	}
	
	public synchronized void start() {
		if(this.running == true) {
			return;
		}
		this.running = true;
		this.thread = new Thread(this, "Game Thread");
		this.thread.start();
	}
	
	private void initWindow() {
		this.frame = new JFrame("2D SEIGE");
		this.setPreferredSize(new Dimension(960, 540));
		this.setMaximumSize(new Dimension(960, 540));
		this.setMinimumSize(new Dimension(960, 540));
		this.frame.setPreferredSize(new Dimension(960, 540));
		this.frame.setMaximumSize(new Dimension(960, 540));
		this.frame.setMinimumSize(new Dimension(960, 540));
		this.frame.add(this);
		this.frame.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent e) {	
			}

			@Override
			public void windowClosed(WindowEvent e) {	
			}

			@Override
			public void windowClosing(WindowEvent e) {
				close();
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}
			
			@Override
			public void windowOpened(WindowEvent e) {
			}
		});
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLocationRelativeTo(null);
		this.frame.setResizable(true);
		//this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//this.frame.setUndecorated(true);
		this.frame.setVisible(true);
		this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
	}
	
	private void init() {
		//create socket
		try {
			this.address = InetAddress.getByName("192.168.2.19");
			this.socket = new DatagramSocket();
			this.socket.setSoTimeout(180);
			this.socket.setBroadcast(false);
			this.socket.setReuseAddress(false);
			this.socket.setTrafficClass(0x10);
		} catch (UnknownHostException | SocketException e) {
			e.printStackTrace();
		}
		//send initial add packet
		try {
			this.sendByteBuffer = new AddPacket(System.getProperty("user.name")).getBytes();
			this.socket.send(new DatagramPacket(this.sendByteBuffer, this.sendByteBuffer.length, this.address, port));
			this.timeSent = System.currentTimeMillis();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//
		try {
			this.background = ImageIO.read(this.getClass().getResource("/img_lights.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.pool = new UpdatePacketPool(15,20);
		this.renderEntities = new ArrayList<RenderEntity>();
		this.player = new ClientPlayer();
		this.key = new Key(this.player, this);
		this.cam = new Camera(this.player, this);
		this.mouse = new Mouse(this.cam);
		this.addMouseListener(this.mouse);
		this.addMouseMotionListener(this.mouse);
		this.addMouseWheelListener(this.mouse);
		this.addKeyListener(this.key);
		this.requestFocus();
	}
	
	@Override
	public void run() {
		this.initWindow();
		this.init();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(this.running == true){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				this.tick();
				updates++;
				delta--;
			}
			this.render();
			frames++;
					
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}
	
	private void tick() {
		this.send();
		this.receive();
		this.cam.tick();
	}
	
	private void send() {
		try {
			UpdatePacket packet = this.pool.checkOut();
			packet.init(this.player.getVelX(), this.player.getVelY(), this.mouse.getX(), this.mouse.getY());
			this.sendByteBuffer = packet.getBytes();
			this.pool.checkIn(packet);
			this.socket.send(new DatagramPacket(this.sendByteBuffer, this.sendByteBuffer.length, this.address, port));
			this.timeSent = System.currentTimeMillis();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void receive() {
		try {
			DatagramPacket receivedPacket = new DatagramPacket(this.receiveByteBuffer, this.receiveByteBuffer.length);
			this.socket.receive(receivedPacket);
			ObjectInputStream objectIn = new ObjectInputStream(new BufferedInputStream(new ByteArrayInputStream(receivedPacket.getData())));
			Packet packet = (Packet) objectIn.readObject();
			objectIn.close();
			switch(packet.getId()) {
			case RENDER:
					RenderPacket renderPacket = (RenderPacket) packet;
					this.renderEntities = renderPacket.getRenderEntities();
					this.player.setX(renderPacket.getX());
					this.player.setY(renderPacket.getY());
					this.player.setHealth(renderPacket.getHealth());
					this.player.setAmmo(renderPacket.getAmmo());
				break;
			case SOUND:
					SoundPacket soundPacket = (SoundPacket) packet;
					this.playSound(soundPacket.getSound().getUrl());
				break;
			case INIT:
				InitPacket initPacket = (InitPacket) packet;
				this.player.setWidth(initPacket.getWidth());
				this.player.setHeight(initPacket.getHeight());
				this.player.setMaxAmmo(initPacket.getMaxAmmo());
				this.player.setMaxHealth(initPacket.getMaxHealth());
				this.cam.setMaxZoomFactor(initPacket.getMaxScope());
				break;
			default:	System.out.println("UNKNOWN PACKET");
				break;
			}
			this.ping = (int) (System.currentTimeMillis() - this.timeSent);
		} catch (IOException | ClassNotFoundException e) {
			this.send();
			e.printStackTrace();
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform af = g2d.getTransform();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight()); //background
		this.cam.move(g2d);
		g.drawImage(this.background, 0, 0, null);
		
		//start
		for(RenderEntity e: this.renderEntities) {
			e.render(g);
		}
		//finish
		
		g2d.setTransform(af);
		
		//hud
		g.setColor(Color.red);
		g.drawString(this.ping+"ms", this.getWidth()-50, 25);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(23, 23, 2*this.player.getMaxHealth()+4, 29);
		
		g.setColor(Color.RED);
		g.fillRect(25, 25, 2*this.player.getMaxHealth(), 25);
		
		g.setColor(Color.CYAN);
		g.fillRect(25, 25, (int) (2*this.player.getHealth()), 25);
		
		g.setColor(Color.DARK_GRAY);
		g.drawString(this.player.getAmmo() + "/" + this.player.getMaxAmmo(), 25, 75);
		
		g.dispose();
		bs.show();
	}
	
	private void playSound(String url) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(this.getClass().getResource(url)));
					clip.start();
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public void close() {
		//send disconnect packet
		try {
			this.sendByteBuffer = new DisconnectPacket().getBytes();
			this.socket.send(new DatagramPacket(this.sendByteBuffer, this.sendByteBuffer.length, this.address, port));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//close
		System.out.println("closing");
		this.running = false;
		if(this.socket != null) {
			this.socket.close();
		}
		System.exit(0);
	}

}