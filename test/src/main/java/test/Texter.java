package test;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class Texter {
	public static final String ACCOUNT_SID = "***REMOVED***";
	public static final String AUTH_TOKEN = "***REMOVED***";
	public static void main(String[] args) {
		
		
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);


		 Message message = Message.creator(
		     new PhoneNumber("07876683967"),  // To number
		     new PhoneNumber("441462600061"),  // From number
		     "Hello world!"                    // SMS body
		 ).create();

		 System.out.println(message.getSid());

	}

}
