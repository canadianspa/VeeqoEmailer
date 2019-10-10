package entities;

public class Address {
	
	String first_name;
	String last_name;
	String address1;
	String address2;
	String city;
	String country;
	String state;
	String zip;
	String phone;
	
	public Address(String first_name, String last_name, String address1, String address2, String city, String country,
			String state, String zip, String phone) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.country = country;
		this.state = state;
		this.zip = zip;
		this.phone = phone;
	}

	@Override
	public String toString() {
		String output = first_name + " " + last_name + ", "  + address1  + ", "  + address2 + ", "  + city  + ", "
				+ ", "  + country +  ", "  + state + ", " + zip + ", " + phone;
		return output;
	}
	
	public String name()
	{
		return first_name + " " + last_name;
	}
	
	
	
	
	
	
}
