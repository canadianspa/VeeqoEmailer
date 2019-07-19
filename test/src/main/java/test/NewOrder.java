package test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import test.NewOrderFinder.Order;






public class NewOrder {

	public class Order{
		int id;
		Allocations[] allocations;
		boolean allocated_completely;
		DeliverTo deliver_to;
		String status;
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

	public class Allocations{
		Shipment shipment;
		LineItem[] line_items;
	}

	public class Shipment{
		int id;
		TrackingNumber tracking_number;
		String tracking_url;
	}

	public class TrackingNumber{
		String tracking_number;
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

		int orderId= 30895701;

		String APIKEY = "";

		Client client = ClientBuilder.newClient();
		Response response = client.target("https://api.veeqo.com/orders/" + orderId)
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("x-api-key", APIKEY)
				.get();



		String body = response.readEntity(String.class);
		Gson g = new Gson();
		Order o = g.fromJson(body, Order.class);

		
		
		int numberOfShips = findShipments(o.allocations);
System.out.println(numberOfShips);
	
		for(int i = numberOfShips - 1; i >= 0; i --)
		{
			System.out.println(numberOfShips);
			int num = o.allocations[i].line_items.length;

			String[] productTitles = new String[num] ;
			int[] quantities = new int[num];

			for(int p = 0; p < num; p ++)
			{
				productTitles[p] = o.allocations[i].line_items[p].sellable.product_title;
				quantities[p] = o.allocations[i].line_items[p].quantity; 
			}

			LineItems li = new LineItems(productTitles,quantities);

			DeliverTo dt = o.deliver_to;
			Address a = new Address(dt.first_name,dt.last_name,dt.address1,dt.address2,dt.city,dt.country,dt.state,dt.zip,dt.phone);
			String name = dt.first_name + " " +  dt.last_name;


			System.out.println(o.allocations[i].shipment.tracking_url);

			Emailer.orderShipped(name, " ", li,a,o.allocations[i].shipment.tracking_url,o.allocations[i].shipment.tracking_number.tracking_number, 0);


			Texter.orderShipped(name, " ", li,a,o.allocations[i].shipment.tracking_url,o.allocations[i].shipment.tracking_number.tracking_number, 0);

		}


	}		

	public static int findShipments(Allocations[] allocations)
	{
		int numberOfShips = 0;
		for(Allocations a: allocations)
		{
			if(a.shipment != null)
			{
				numberOfShips += 1;
			}
		}
		
		return numberOfShips;
	}
}

