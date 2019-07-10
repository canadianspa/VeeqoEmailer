package test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;


public class NewOrderFinder {


	public class Order{
		int id;
		CustomerNote customer_note;
		DeliverTo deliver_to;
		LineItem[] line_items;
	}

	public class CustomerNote
	{
		String text;
	}

	public class DeliverTo
	{
		String first_name;
		String last_name;
		String address1;
		String address2;
		String city;
		String country;
		String state;
		String zip;
		String phone;
	}
	
	public class LineItem
	{
		int quantity;
		Sellable sellable;
		
	}
	
	public class Sellable
	{
		String product_title;
	}


	public static void main(String[] args) throws IOException {

		int orderId= 30375170;

		String APIKEY = "***REMOVED***";

		Client client = ClientBuilder.newClient();
		Response response = client.target("https://api.veeqo.com/orders/" + orderId)
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("x-api-key", APIKEY)
				.get();



		String body = response.readEntity(String.class);
		Gson g = new Gson();
		Order o = g.fromJson(body, Order.class);

		Pattern pattern = Pattern.compile("([a-z0-9_.-]+)@([a-z0-9_.-]+[a-z])");
        Matcher matcher = pattern.matcher(o.customer_note.text);
        String email = "";
        if(matcher.find())
        {
        	email  = matcher.group();
        }
      
		Long id = (long) o.id;
		String phoneNum = o.deliver_to.phone;
		int num = o.line_items.length;
		
		String[] productTitles = new String[num] ;
		int[] quantities = new int[num];

		for(int i = 0; i < num; i ++)
		{
			productTitles[i] = o.line_items[i].sellable.product_title;
			quantities[i] = o.line_items[i].quantity; 
		}
		
		LineItems li = new LineItems(productTitles,quantities);
		
		HomebaseOrder ho = new HomebaseOrder(id,email,phoneNum);
		

		DeliverTo dt = o.deliver_to;
		String name = dt.first_name + " " +  dt.last_name;
		Address a = new Address(dt.first_name,dt.last_name,dt.address1,dt.address2,dt.city,dt.country,dt.state,dt.zip,dt.phone);
		
		System.out.println("hi");
		Emailer.orderRecieved(name,ho.customerEmail, li,a);
		
	}
}

