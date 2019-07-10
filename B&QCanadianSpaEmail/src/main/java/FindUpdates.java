import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import entities.Address;
import entities.HomebaseOrder;
import entities.LineItems;
import entities.Settings;


@WebServlet(
		name = "FindUpdates",
		urlPatterns = {"/fu"}
		)
public class FindUpdates extends HttpServlet {

	public class Order{
		int id;
		Allocations[] allocations;
		boolean allocated_completely;
		DeliverTo deliver_to;

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

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {



		ObjectifyService.register(HomebaseOrder.class); 
		ObjectifyService.register(Settings.class); 

		Settings s = ObjectifyService.ofy().load().type(Settings.class).first().now();


		Query<HomebaseOrder> q = ObjectifyService.ofy().load().type(HomebaseOrder.class);

		//get all the orders not fully allocated
		Query<HomebaseOrder> q0 = q.filter("stage", 0);
		List<HomebaseOrder> hos0 = q0.list();	
	

		for(HomebaseOrder h: hos0)
		{
			try {
				Order o = idToClass(h.id);
				if(o.allocated_completely)
				{
					h.stage = 1;

				}
				
				
				if(o.allocations.length > h.allocationsUsed)
				{
					for(int i = o.allocations.length - 1; i >= h.allocationsUsed; i --)
					{
						
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

						
						
						Emailer.orderShipped(name, h.customerEmail, li,a,o.allocations[i].shipment.tracking_url,o.allocations[i].shipment.tracking_number.tracking_number, h.stage);
						Texter.orderShipped(name, h.customerPhone, li,a,o.allocations[i].shipment.tracking_url,o.allocations[i].shipment.tracking_number.tracking_number, h.stage);
					}
				}
				
				h.allocationsUsed = o.allocations.length;
				ObjectifyService.ofy().save().entity(h).now();
			} catch (Exception e) {
				  Logger log = Logger.getLogger(FindUpdates.class.getName());
				  log.info("Deleted:" + h.id);

				ObjectifyService.ofy().delete().entity(h).now();
			}

			
			
			
		}

		response.getWriter().println(hos0.size());

	}

	public Order idToClass(Long orderId)
	{
		String APIKEY = "***REMOVED***";

		
		Client client = ClientBuilder.newClient();
		javax.ws.rs.core.Response response = client.target("https://api.veeqo.com/orders/" + orderId)
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("x-api-key", APIKEY)
				.get();



		String body = response.readEntity(String.class);
		Gson g = new Gson();


		return g.fromJson(body, Order.class);
	}
	
	
	

}