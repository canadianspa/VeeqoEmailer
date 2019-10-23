package entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class VeeqoOrder {
	
	@Id
	public Long id;
	public @Index int stage;
	public @Index String customerEmail;
	public @Index String customerPhone;
	public @Index int allocationsUsed;
	
	

	public VeeqoOrder(Long id, String customerEmail, String customerPhone) {
		super();
		this.id = id;
		this.stage = 0;
		this.customerEmail = customerEmail;
		this.customerPhone = customerPhone;
		this.allocationsUsed = 0;
	}



	public VeeqoOrder() {
	}
	

}
