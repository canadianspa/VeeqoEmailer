import java.io.IOException;
import java.util.List;
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

import entities.HomebaseOrder;
import entities.LineItems;
import entities.Settings;

@WebServlet(
		name = "SetSettings",
		urlPatterns = {"/ss"}
		)
public class SetSettings extends HttpServlet {


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {


		ObjectifyService.register(Settings.class); 


		
		Settings olds = ObjectifyService.ofy().load().type(Settings.class).first().now();

		
		ObjectifyService.ofy().delete().entity(olds);

		Settings news = new Settings(0L,0L);

		ObjectifyService.ofy().save().entity(news);
		response.getWriter().println("Settings Reset");

	}
}