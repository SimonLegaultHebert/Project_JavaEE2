package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Utilisateur;
import forms.ConnexionForm;

public class Connexion extends HttpServlet{

	public static final String ATT_USER = "utilisateur";
    public static final String ATT_FORM = "form";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String VUE = "/WEB-INF/connexion.jsp";
    
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
    	this.getServletContext().getRequestDispatcher(VUE).forward(req, resp);
    }
    
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
    	ConnexionForm form = new ConnexionForm();
    	Utilisateur utilisateur = form.connecterUtilisateur(req);
    	HttpSession session = req.getSession();
    	
    	if (form.getErreurs().isEmpty()){
            session.setAttribute(ATT_SESSION_USER, utilisateur);
        }else{
            session.setAttribute(ATT_SESSION_USER, null);
        }
    	
    	req.setAttribute(ATT_FORM, form);
    	req.setAttribute(ATT_USER, utilisateur);
    	this.getServletContext().getRequestDispatcher(VUE).forward(req, resp);
    }
}
