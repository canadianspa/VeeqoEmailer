package entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Settings {
	@Id
	public Long id;
	public Long lastId;
	public Long lastChecked;

	public Settings() {
	}

	public Settings(Long lastId, Long lastChecked) {
		super();
		this.lastId = lastId;
		this.lastChecked = lastChecked;
	}
	
	

}
