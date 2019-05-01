package client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Key implements KeyListener {
	private ClientPlayer player;
	private Game game;
	
	public Key(ClientPlayer player, Game game) {
		this.game = game;
		this.player = player;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			this.player.setVelY(-1);
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			this.player.setVelX(-1);
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			this.player.setVelY(1);
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			this.player.setVelX(1);
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.game.close();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			if(this.player.getVelY()<0) {
				this.player.setVelY(0);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			if(this.player.getVelX()<0) {
				this.player.setVelX(0);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			if(this.player.getVelY()>0) {
				this.player.setVelY(0);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			if(this.player.getVelX()>0) {
				this.player.setVelX(0);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}