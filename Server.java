package host;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import packets.Packet;

public class Server implements Runnable {
	
	private Thread thread;
	private boolean running = false;
	private JFrame frame;
	private JTextArea area;
	private JTextField feild;
	private JScrollPane scroll;
	private ThreadPoolExecutor threadPool;
	//
	private InetAddress address;
	private static final int port = 3245;
	private DatagramSocket socket;
	private byte[] byteBuffer = new byte[2048]; //max safe bytes is 512ish
	//
	private ClientHandler clientHandler;
	private EntityHandler entityHandler;
	
	public static void main(String[] args) {
		new Server().start();
	}
	
	public synchronized void start() {
		if(this.running == true) {
			return;
		}
		this.running = true;
		this.thread = new Thread(this, "Server Thread");
		this.thread.start();
	}
	
	private void initWindow() {
		this.frame = new JFrame("Server");
		this.area = new JTextArea();
		this.scroll = new JScrollPane(this.area);
		this.scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.area.setText("Starting...");
		this.feild = new JTextField();
		this.feild.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(feild.getText().equalsIgnoreCase("stop")) {
					close();
				}else {
					area.append(System.lineSeparator() + feild.getText());
					feild.setText("");
				}
			}
		});
		this.frame.add(this.feild, BorderLayout.SOUTH);
		this.frame.add(this.scroll);
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
		this.frame.setSize(800,450);
		this.frame.setVisible(true);
		this.frame.requestFocus();
	}
	
	private void init() {
		//start server
		try {
			this.address = InetAddress.getByName(InetAddress.getLocalHost().getHostAddress());
			this.socket = new DatagramSocket(new InetSocketAddress(this.address, port));
			this.socket.setSoTimeout(0);
			this.socket.setBroadcast(false);
			this.socket.setReuseAddress(false);
			this.socket.setTrafficClass(0x10);
//			IPTOS_LOWCOST (0x02)
//			IPTOS_RELIABILITY (0x04)
//			IPTOS_THROUGHPUT (0x08)
//			IPTOS_LOWDELAY (0x10)
			this.area.append(System.lineSeparator() + "Server Sucessfully Created At: " + this.address.getHostAddress() + " On Port: " + port);
		} catch (UnknownHostException | SocketException e) {
			e.printStackTrace();
		}
		//
		this.threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(20); //or cached?
		this.clientHandler = new ClientHandler(this.socket);
		this.entityHandler = new EntityHandler();
		this.entityHandler.start();
	}
	
	@Override
	public void run() {
		this.initWindow();
		this.init();
		long lastTime = System.nanoTime();
		double amountOfTicks = 240.0;
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
		this.receive(); //get updates
		//tick entities in entities handler thread
		//send updated renders from sub thread
	}
	
	private void receive() {
		try {
			DatagramPacket receivedPacket = new DatagramPacket(this.byteBuffer, this.byteBuffer.length);
			this.socket.receive(receivedPacket);
			ObjectInputStream objectIn = new ObjectInputStream(new BufferedInputStream(new ByteArrayInputStream(receivedPacket.getData())));
			this.threadPool.execute(new ServerThread((Packet) objectIn.readObject(), this.area, this.clientHandler, new InetSocketAddress(receivedPacket.getAddress(), receivedPacket.getPort()), this.entityHandler));
			objectIn.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void close() {
		System.out.println("closing");
		this.running = false;
		this.entityHandler.close();
		this.threadPool.shutdown();
		if(this.socket != null) {
			this.socket.close();
		}
		System.exit(0);
	}

}