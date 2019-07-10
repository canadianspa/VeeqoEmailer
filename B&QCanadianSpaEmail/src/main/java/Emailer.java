import java.io.IOException;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import entities.Address;
import entities.LineItems;

public final class Emailer {
	
	public static void orderRecieved(String name, String email, LineItems li, Address a)
	{
		String apikey = "***REMOVED***";
		Email from = new Email("noreply@canadianspacompany.com");
		String subject = "";
		Email to = new Email(email);
		Content content = new Content("text/plain", "a");
		Mail mail = new Mail(from, subject, to, content);
		
		
		mail.setTemplateId("d-520336ae738a41fcb33c8291584495a5");
		mail.getPersonalization().get(0).addDynamicTemplateData("name", name);
		mail.getPersonalization().get(0).addDynamicTemplateData("lineItems", li.toString());
		mail.getPersonalization().get(0).addDynamicTemplateData("address", a.toString());
		
		
			
		SendGrid sg = new SendGrid(apikey);
		Request emailrequest = new Request();
		try {
			emailrequest.setMethod(Method.POST);
			emailrequest.setEndpoint("mail/send");
			emailrequest.setBody(mail.build());
			Response emailresponse = sg.api(emailrequest);
			System.out.println(emailresponse.getStatusCode());
			System.out.println(emailresponse.getBody());
			System.out.println(emailresponse.getHeaders());

		} catch (IOException ex) {
			
			ex.printStackTrace();

		}
	}
	
	public static void orderShipped(String name, String email, LineItems li,Address a, String trackingURL, String trackingNumber, int stage)
	{
		String apikey = "***REMOVED***";
		Email from = new Email("noreply@canadianspacompany.com");
		String subject = "";
		Email to = new Email(email);
		Content content = new Content("text/plain", "a");
		Mail mail = new Mail(from, subject, to, content);
		
		
		mail.setTemplateId("d-917899789e7b41e5aa05c2c7413ebdd6");
		mail.getPersonalization().get(0).addDynamicTemplateData("name", name);
		mail.getPersonalization().get(0).addDynamicTemplateData("lineItems", li.toString());
		mail.getPersonalization().get(0).addDynamicTemplateData("address", a.toString());
		mail.getPersonalization().get(0).addDynamicTemplateData("trackingUrl", trackingURL);
		mail.getPersonalization().get(0).addDynamicTemplateData("trackingNumber", trackingNumber);
		
		
			
		SendGrid sg = new SendGrid(apikey);
		Request emailrequest = new Request();
		try {
			emailrequest.setMethod(Method.POST);
			emailrequest.setEndpoint("mail/send");
			emailrequest.setBody(mail.build());
			Response emailresponse = sg.api(emailrequest);
			System.out.println(emailresponse.getStatusCode());
			System.out.println(emailresponse.getBody());
			System.out.println(emailresponse.getHeaders());

		} catch (IOException ex) {
			
			ex.printStackTrace();

		}
	}
	
	public static void holder(){
		
	}

}
