package packets;

public enum SoundId {
	
	DOORBELL("/service-bell_daniel_simion.wav");
	
	private String url;
	
	SoundId(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
	
}