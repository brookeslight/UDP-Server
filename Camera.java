package client;

import java.awt.Canvas;
import java.awt.Graphics2D;

public class Camera {
	
	private ClientPlayer player;
	private Canvas canvas;
	private static final float lerp = 0.275f;
	private double zoomFactor = 1.0d;
	private double x;
	private double y;
	private double targetX;
	private double targetY;
	private double targetZoomFactor = 1.0d;
//	private boolean isLookingAtPlayer = true;
//	private double playersZoomPlaceholder = 1.0d;
	private double maxZoomFactor = 1.0d;
	//min zoom is 1.0

	public Camera(ClientPlayer player, Canvas canvas) {
		this.player = player;
		this.canvas = canvas;
		this.x = -(this.player.getX()+this.player.getWidth()/2);
		this.y = -(this.player.getY()+this.player.getHeight()/2);
		this.targetX = -(this.player.getX()+this.player.getWidth()/2);
		this.targetY = -(this.player.getY()+this.player.getHeight()/2);
	}
	
	public void tick() {
		
//		if(this.isLookingAtPlayer == true) {
			this.targetX = -(this.player.getX()+this.player.getWidth()/2);
			this.targetY = -(this.player.getY()+this.player.getHeight()/2);
//		}
		
		this.x += (this.targetX - this.x) * lerp;
		this.y += (this.targetY - this.y) * lerp;
		
		this.zoomFactor += (this.targetZoomFactor - this.zoomFactor) * lerp;
	}

	public void move(Graphics2D g2d) {
		g2d.translate(this.x * (1.0d/this.zoomFactor) * (this.canvas.getWidth()/1920.0d), this.y * (1.0d/this.zoomFactor) * (this.canvas.getHeight()/1080.0d));
		g2d.scale((1.0d/this.zoomFactor) * (this.canvas.getWidth()/1920.0d), (1.0d/this.zoomFactor) * (this.canvas.getHeight()/1080.0d));
		g2d.translate(this.canvas.getWidth()/2 * this.zoomFactor * (1920.0d/this.canvas.getWidth()), this.canvas.getHeight()/2 * this.zoomFactor * (1080.0d/this.canvas.getHeight()));
	}
	
	public void changeZoom(double zoom) {
		if(this.targetZoomFactor+zoom >= this.maxZoomFactor) {
			this.targetZoomFactor = this.maxZoomFactor;
		} else if(this.targetZoomFactor+zoom <= 1.0d) { //min zoom factor is always 1
			this.targetZoomFactor = 1.0d;
		} else {
			this.targetZoomFactor += zoom;
		}
	}
	
//	public void lookAt(int x, int y) {
//		this.isLookingAtPlayer = false;
//		this.playersZoomPlaceholder = this.targetZoomFactor;
//		this.targetZoomFactor = 1.0d;
//		this.zoomFactor = 1.0d;
//		this.targetX = x;
//		this.targetY = y;
//		this.x = x;
//		this.y = y;
//	}
//	
//	public void firstPerson() {
//		this.isLookingAtPlayer = true;
//		this.targetZoomFactor = this.playersZoomPlaceholder;
//		this.zoomFactor = this.playersZoomPlaceholder;
//		this.targetX = -(this.player.getX()+32);
//		this.targetY = -(this.player.getY()+32);
//		this.x = -(this.player.getX()+32);
//		this.y = -(this.player.getY()+32);
//	}

	public ClientPlayer getPlayer() {
		return player;
	}

	public void setPlayer(ClientPlayer player) {
		this.player = player;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public double getZoomFactor() {
		return zoomFactor;
	}

	public void setZoomFactor(double zoomFactor) {
		this.zoomFactor = zoomFactor;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getTargetX() {
		return targetX;
	}

	public void setTargetX(double targetX) {
		this.targetX = targetX;
	}

	public double getTargetY() {
		return targetY;
	}

	public void setTargetY(double targetY) {
		this.targetY = targetY;
	}

	public double getTargetZoomFactor() {
		return targetZoomFactor;
	}

	public void setTargetZoomFactor(double targetZoomFactor) {
		this.targetZoomFactor = targetZoomFactor;
	}

//	public boolean isLookingAtPlayer() {
//		return isLookingAtPlayer;
//	}
//
//	public void setLookingAtPlayer(boolean isLookingAtPlayer) {
//		this.isLookingAtPlayer = isLookingAtPlayer;
//	}
//
//	public double getPlayersZoomPlaceholder() {
//		return playersZoomPlaceholder;
//	}
//
//	public void setPlayersZoomPlaceholder(double playersZoomPlaceholder) {
//		this.playersZoomPlaceholder = playersZoomPlaceholder;
//	}

	public double getMaxZoomFactor() {
		return maxZoomFactor;
	}

	public void setMaxZoomFactor(double maxZoomFactor) {
		this.maxZoomFactor = maxZoomFactor;
	}

	public static float getLerp() {
		return lerp;
	}

}