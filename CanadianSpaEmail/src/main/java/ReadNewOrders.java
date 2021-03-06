import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

import entities.Address;
import entities.VeeqoOrder;
import entities.LineItems;
import entities.Settings;

@WebServlet(
		name = "ReadNewOrders",
		urlPatterns = {"/rno"}
		)
public class ReadNewOrders extends HttpServlet {


	final ArrayList<String> tags = new ArrayList<String>(Arrays.asList("B%20%26%20Q","HOMEBASE","ARGOS"));


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

	//stage 0 orders still need to be shipped, stage 1 all orders shipped, stage 2 problemo
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {


		ObjectifyService.register(VeeqoOrder.class); 
		ObjectifyService.register(Settings.class); 



		Settings s = ObjectifyService.ofy().load().type(Settings.class).first().now();

		long newLast = s.lastId;
		for(String tag: tags)
		{
			long hold = readNew(tag, s.lastId);
			//find the last id of all the tags
			newLast = (newLast > hold) ? newLast: hold;
		}

		s.lastId = newLast;
		ObjectifyService.ofy().save().entity(s).now();
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(s.lastId);


	}


	public long readNew(String tag, double lastId)
	{
		Settings s = ObjectifyService.ofy().load().type(Settings.class).first().now();

		String APIKEY = APIKEYS.veeqoApi;

		Client client = ClientBuilder.newClient();
		javax.ws.rs.core.Response veeqoresponse = client.target("https://api.veeqo.com/orders?page_size=25&since_id="+s.lastId+"&tags="+tag)
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("x-api-key", APIKEY)
				.get();



		String body = veeqoresponse.readEntity(String.class);
		Gson g = new Gson();


		Order[] orders = g.fromJson(body, Order[].class);

		if(orders.length > 0)
		{
			for(Order o: orders)
			{
				Pattern pattern = Pattern.compile("([a-z0-9_.-]+)@([a-z0-9_.-]+[a-z])");
				Matcher matcher = pattern.matcher(o.customer_note.text);
				String email = " ";
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

				VeeqoOrder ho = new VeeqoOrder(id,email,phoneNum);

				ObjectifyService.ofy().save().entity(ho).now();

				DeliverTo dt = o.deliver_to;
				Address a = new Address(dt.first_name,dt.last_name,dt.address1,dt.address2,dt.city,dt.country,dt.state,dt.zip,dt.phone);
				String name = dt.first_name + " " +  dt.last_name;
				Emailer.orderRecieved(name, ho.customerEmail, li,a);
				Texter.orderRecieved(name, ho.customerPhone, li,a);

			}
			return (long) orders[0].id;



		}
		
		return 0;
	}

}