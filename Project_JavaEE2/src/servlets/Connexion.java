package servlets;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Utilisateur;
import forms.ConnexionForm;

public class Connexion extends HttpServlet{

	public static final String ATT_USER = "utilisateur";
    public static final String ATT_FORM = "form";
    public static final String ATT_INTERVALLE_CONNEXIONS = "intervalleConnexions";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String COOKIE_DERNIERE_CONNEXION = "derniereConnexion";
    public static final String FORMAT_DATE = "dd/MM/yyyy HH:mm:ss";
    public static final String VUE = "/WEB-INF/connexion.jsp";
    public static final String  CHAMP_MEMOIRE = "memoire";
    public static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 365;  // 1 an
    
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Tentative de récupération du cookie depuis la requête */
        String derniereConnexion = getCookieValue(request, COOKIE_DERNIERE_CONNEXION);
        /* Si le cookie existe, alors calcul de la durée */
        if ( derniereConnexion != null ) {
                     request.setAttribute(ATT_INTERVALLE_CONNEXIONS, "intervalle test");
        }
        /* Affichage de la page de connexion */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
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
    	
    	if ( req.getParameter(CHAMP_MEMOIRE) != null ) {
            //créer date courante
            //setCookie( response, COOKIE_DERNIERE_CONNEXION, dateDerniereConnexion, COOKIE_MAX_AGE );
        } else {
            /* Demande de suppression du cookie du navigateur */
           // setCookie( response, COOKIE_DERNIERE_CONNEXION, "", 0 );
        }

        /* Stockage du formulaire et du bean dans l'objet request */
        req.setAttribute(ATT_FORM, form);
        req.setAttribute(ATT_USER, utilisateur);

        this.getServletContext().getRequestDispatcher(VUE).forward(req, resp);
    }

    /**
     * Méthode utilitaire gérant la récupération de la valeur d'un cookie donné
     * depuis la requête HTTP.
     */
    private static String getCookieValue(HttpServletRequest request, String nom) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie != null && nom.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    
	/*
	 * Méthode utilitaire gérant la création d'un cookie et son ajout à la
	 * réponse HTTP.
	 */
	private static void setCookie( HttpServletResponse response, String nom, String valeur, int maxAge ) {
	    Cookie cookie = new Cookie( nom, valeur );
	    cookie.setMaxAge(maxAge);
	    response.addCookie(cookie);
	
	}
}
