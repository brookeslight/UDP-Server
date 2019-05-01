package entity;

public enum Gun {
	DMR(1.0f, 1, 45.0f, 30, 7.5f, 2.0f, 2);
	
	private float shotsPerSecond;
	private int bulletsPerShot;
	private float dammagePerBullet;
	private int maxAmmo;
	private float reloadTime; //seconds
	private float scope; //zoom factor
	private int bulletSize;
	
	private Gun(float shotsPerSecond, int bulletsPerShot, float dammagePerBullet, int maxAmmo, float reloadTime, float scope, int bulletSize) {
		this.shotsPerSecond = shotsPerSecond;
		this.bulletsPerShot = bulletsPerShot;
		this.dammagePerBullet = dammagePerBullet;
		this.maxAmmo = maxAmmo;
		this.reloadTime = reloadTime;
		this.scope = scope;
		this.bulletSize = bulletSize;
	}

	public float getShotsPerSecond() {
		return shotsPerSecond;
	}

	public int getBulletsPerShot() {
		return bulletsPerShot;
	}

	public float getDammagePerBullet() {
		return dammagePerBullet;
	}

	public int getMaxAmmo() {
		return maxAmmo;
	}

	public float getReloadTime() {
		return reloadTime;
	}

	public float getScope() {
		return scope;
	}

	public int getBulletSize() {
		return bulletSize;
	}

}