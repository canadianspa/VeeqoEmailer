import entities.Address;
import entities.LineItems;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public final class Texter {
	
	public static final String ACCOUNT_SID = "***REMOVED***";
	 public static final String AUTH_TOKEN = "***REMOVED***";

	public static void orderRecieved(String name, String phone, LineItems li,Address a)
	{
		 try {
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);


			 phone = "07876683967";
			 Message message = Message.creator(
			     new PhoneNumber(phone),  // To number
			     new PhoneNumber("+15559994321"),  // From number
			     "Hello " + name + ", your order of from Canadian Spa Company for:" + li.toString() + " has been recieved"                // SMS body
			 ).create();

			 System.out.println(message.getSid());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void orderShipped(String name, String phone, LineItems li,Address a,String trackingURL, String trackingNumber, int stage)
	{
		try {
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
			 phone = "07876683967";
			 Message message = Message.creator(
			     new PhoneNumber(phone),  // To number
			     new PhoneNumber("+15559994321"),  // From number
			     "Hello " + name + ", your order of from Canadian Spa Company has been shipped, you can monitor the progress with:" + trackingURL    // SMS body
			 ).create();

			 System.out.println(message.getSid());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
