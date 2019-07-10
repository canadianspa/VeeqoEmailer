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
		String output = "";
		output += first_name + " " + last_name + ",\r\n";
		output += address1 + ",\r\n";
		output += address2 + ",\r\n";
		output += city + ",\r\n";
		output += country + ",\r\n";
		output += state + ",\r\n";
		output += zip + ",\r\n";
		output += phone + ",\r\n";
		return output;
	}
	
	public String name()
	{
		return first_name + " " + last_name;
	}
	
	
	
	
	
	
}
