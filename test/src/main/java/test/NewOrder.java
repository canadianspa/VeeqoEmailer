package test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;






public class NewOrder {


	public class Order{
		int id;
		CustomerNote customer_note;
		DeliverTo deliver_to;
		LineItem[] line_items;
		boolean allocated_completely;

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
		int lastId= 30375175;

		String APIKEY = "***REMOVED***";

		Client client = ClientBuilder.newClient();
		Response response = client.target("https://api.veeqo.com/orders?page_size=25&since_id="+lastId+"&tags=B%20%26%20Q")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("x-api-key", APIKEY)
				.get();



		String body = response.readEntity(String.class);
		Gson g = new Gson();


		Order[] orders = g.fromJson(body, Order[].class);


		if(orders.length > 0)
		{
			for(Order o: orders)
			{
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
				
				if(o.allocated_completely)
				{
					ho.stage = 1;
				}
				System.out.println(ho.stage);


			}
		}


	}
}
