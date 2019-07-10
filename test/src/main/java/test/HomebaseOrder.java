package test;


public class HomebaseOrder {
	
	public Long id;
	public  int stage;
	public  String customerEmail;
	public  String customerPhone;
	public  int allocationsUsed;
	
	

	public HomebaseOrder(Long id, String customerEmail, String customerPhone) {
		super();
		this.id = id;
		this.stage = 0;
		this.customerEmail = customerEmail;
		this.customerPhone = customerPhone;
		this.allocationsUsed = 0;
	}



	public HomebaseOrder() {
	}
	

}
