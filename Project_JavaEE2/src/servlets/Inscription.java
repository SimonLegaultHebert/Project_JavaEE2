package servlets;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Utilisateur;
import forms.InscriptionForm;

public class Inscription extends HttpServlet{

	public static final String VUE = "/WEB-INF/inscription.jsp";
	
	public static final String ATT_FORM  = "form";
    public static final String ATT_USER = "utilisateur";

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VUE).forward(req, resp);		
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InscriptionForm form = new InscriptionForm();
		Utilisateur utilisateur = form.inscrireUtilisateur(req);
                
        req.setAttribute(ATT_FORM, form);
        req.setAttribute(ATT_USER, utilisateur);       
		this.getServletContext().getRequestDispatcher(VUE).forward(req, resp);
	}
	
	
	
}
