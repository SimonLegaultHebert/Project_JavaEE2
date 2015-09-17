package servlets;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Inscription extends HttpServlet{

	public static final String VUE = "/WEB-INF/inscription.jsp";
	
	public static final String CHAMP_EMAIL = "email";
	public static final String CHAMP_PASS = "motdepasse";
	public static final String CHAMP_CONFIRMATION = "confirmation";
	public static final String CHAMP_NOM = "nom";
	public static final String ATT_ERREURS  = "erreurs";
    public static final String ATT_RESULTAT = "resultat";


	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VUE).forward(req, resp);		
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter(CHAMP_EMAIL).trim();
		String motDePasse = req.getParameter(CHAMP_PASS).trim();
		String confirmation = req.getParameter(CHAMP_CONFIRMATION).trim();
		String nom = req.getParameter(CHAMP_NOM).trim();
		Map<String, String> erreurs = validerParametres(email, motDePasse, confirmation, nom);      
        String resultat;
        /* Initialisation du résultat global de la validation. */
        if (erreurs.isEmpty()) {
            resultat = "Succès de l'inscription.";
        } else {
            resultat = "Échec de l'inscription.";

        }
                
        req.setAttribute(ATT_ERREURS, erreurs);
        req.setAttribute(ATT_RESULTAT, resultat);       
		this.getServletContext().getRequestDispatcher(VUE).forward(req, resp);
	}
	
	
	private HashMap<String, String> validerParametres(String email, String motDePasse, String confirmation, String nom){
		HashMap<String, String> erreurs = new HashMap<String, String>();
		/* Validation du champ email. */
        try {
            validationEmail(email);
        } catch (InvalidParameterException e) {
            erreurs.put(CHAMP_EMAIL, e.getMessage());
        }

        /* Validation des champs mot de passe et confirmation. */
        try {
            validationMotDePasse(motDePasse, confirmation);
        } catch (InvalidParameterException e) {
            erreurs.put(CHAMP_PASS, e.getMessage());
        }

        /* Validation du champ nom. */
        try {
            validationNom( nom );
        } catch (InvalidParameterException e) {
            erreurs.put(CHAMP_NOM, e.getMessage());
        }	
        return erreurs;
	}
	
	private void validationEmail(String email){
		if(email != null && !email.isEmpty()){
			if(!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")){
				throw new InvalidParameterException("Merci de saisir une adresse mail valide.");
			}		
		}else{
			throw new InvalidParameterException("Merci de saisir une adresse mail valide.");
		}
	}
	
	private void validationMotDePasse(String motDePasse, String confirmation){
		if(motDePasse != null && motDePasse.length() > 5 && confirmation != null && confirmation.length() > 5){
			if(!motDePasse.equals(confirmation)){
				throw new InvalidParameterException("Les mots de passe entrés sont différents, merci de les saisir à nouveau.");
			}			
		}else{
			throw new InvalidParameterException("Les mots de passe doivent contenir au moins 6 caractères.");
		}
	}
	
	private void validationNom(String nom){
		if(nom == null || nom.length() < 6){
			throw new InvalidParameterException("Le nom d'usager doit contenir au moins 6 caractères.");
		}
	}
}
