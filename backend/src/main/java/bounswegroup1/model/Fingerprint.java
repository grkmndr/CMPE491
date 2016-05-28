package bounswegroup1.model;

public class Fingerprint{
	private Long id;
	private String songName;
	private String artistName;
	private String makam;
	private String fingerprint;

	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public String getSongName(){
		return songName;
	}

	public void setSongName(String songName){
		this.songName = songName;
	}

	public String getArtistName(){
		return artistName;
	}

	public void setArtistName(String artistName){
		this.artistName = artistName;
	}

	public String getMakam(){
		return makam;
	}

	public void setMakam(String makam){
		this.makam = makam;
	}

	public String getFingerprint(){
		return fingerprint;
	}

	public void setFingerprint(String fingerprint){
		this.fingerprint = fingerprint;
	}

	public Fingerprint(){

	}

	public Fingerprint(Long id, String songName, String artistName, String makam, String fingerprint){
		this.id = id;
		this.songName = songName;
		this.artistName = artistName;
		this.makam = makam;
		this.fingerprint = fingerprint;
	}
}