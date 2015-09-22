package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Deconnexion extends HttpServlet{

	public static final String URL_REDIRECTION = "http://www.reddit.com/r/leagueoflegends";

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.invalidate();
		
		resp.sendRedirect(URL_REDIRECTION);
	}
	
	
}
