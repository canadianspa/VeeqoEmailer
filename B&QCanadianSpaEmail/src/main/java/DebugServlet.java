import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import entities.VeeqoOrder;

@WebServlet(
		name = "DebugServlet",
		urlPatterns = {"/ds"}
		)
public class DebugServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {

		ObjectifyService.register(VeeqoOrder.class); 

		Query<VeeqoOrder> q = ObjectifyService.ofy().load().type(VeeqoOrder.class);

		//get all the orders not fully allocated
		Query<VeeqoOrder> q0 = q.filter("stage", 0);
		List<VeeqoOrder> hos0 = q0.list();	


		response.getWriter().println("Stage 0");

		for(VeeqoOrder ho: hos0)
		{
			response.getWriter().println(ho.id);
		}

		response.getWriter().println("<br />");

		//get all the orders fully allocated
		Query<VeeqoOrder> q1 = q.filter("stage", 1);
		List<VeeqoOrder> hos1 = q1.list();	


		response.getWriter().println("Stage 1");

		for(VeeqoOrder ho: hos1)
		{
			response.getWriter().println(ho.id);
		}

		response.getWriter().println("<br />");

		//get all the orders removed
		Query<VeeqoOrder> q2 = q.filter("stage", 2);
		List<VeeqoOrder> hos2 = q2.list();	


		response.getWriter().println("Stage 2");

		for(VeeqoOrder ho: hos2)
		{
			response.getWriter().println(ho.id);
		}

		response.getWriter().println("<br />");




	}
}
