import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import entities.HomebaseOrder;

@WebServlet(
		name = "DebugServlet",
		urlPatterns = {"/ds"}
		)
public class DebugServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {

		ObjectifyService.register(HomebaseOrder.class); 

		Query<HomebaseOrder> q = ObjectifyService.ofy().load().type(HomebaseOrder.class);

		//get all the orders not fully allocated
		Query<HomebaseOrder> q0 = q.filter("stage", 0);
		List<HomebaseOrder> hos0 = q0.list();	


		response.getWriter().println("Stage 0");

		for(HomebaseOrder ho: hos0)
		{
			response.getWriter().println(ho.id);
		}

		response.getWriter().println("<br />");

		//get all the orders fully allocated
		Query<HomebaseOrder> q1 = q.filter("stage", 1);
		List<HomebaseOrder> hos1 = q1.list();	


		response.getWriter().println("Stage 1");

		for(HomebaseOrder ho: hos1)
		{
			response.getWriter().println(ho.id);
		}

		response.getWriter().println("<br />");

		//get all the orders removed
		Query<HomebaseOrder> q2 = q.filter("stage", 2);
		List<HomebaseOrder> hos2 = q2.list();	


		response.getWriter().println("Stage 2");

		for(HomebaseOrder ho: hos2)
		{
			response.getWriter().println(ho.id);
		}

		response.getWriter().println("<br />");




	}
}
