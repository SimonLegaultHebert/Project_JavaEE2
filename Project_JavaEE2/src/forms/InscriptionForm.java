package forms;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import beans.Utilisateur;

/**
 * Contient la logique d'affaire du formulaire pour l'inscription
 * @author Leg
 *
 */
public class InscriptionForm {
	
	private static final String CHAMP_EMAIL = "email";
	private static final String CHAMP_PASS = "motdepasse";
	private static final String CHAMP_CONFIRMATION = "confirmation";
	private static final String CHAMP_NOM = "nom";
	
	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();
	
	public Utilisateur inscrireUtilisateur(HttpServletRequest req){
		String email = req.getParameter(CHAMP_EMAIL).trim();
		String motDePasse = req.getParameter(CHAMP_PASS).trim();
		String confirmation = req.getParameter(CHAMP_CONFIRMATION).trim();
		String nom = req.getParameter(CHAMP_NOM).trim();

		Utilisateur utilisateur = validerParametres(email, motDePasse, confirmation, nom);
        /* Initialisation du résultat global de la validation. */
        if (erreurs.isEmpty()) {
            resultat = "Succès de l'inscription.";
        } else {
            resultat = "Échec de l'inscription.";

        }
        return utilisateur;
	}
	
	private Utilisateur validerParametres(String email, String motDePasse, String confirmation, String nom){
		Utilisateur utilisateur = new Utilisateur();
		
		/* Validation du champ email. */
        try {
            validationEmail(email);
        } catch (InvalidParameterException e) {
        	rajouterErreur(CHAMP_EMAIL, e.getMessage());
        }
        utilisateur.setEmail(email);

        /* Validation des champs mot de passe et confirmation. */
        try {
            validationMotDePasse(motDePasse, confirmation);
        } catch (InvalidParameterException e) {
        	rajouterErreur(CHAMP_PASS, e.getMessage());
        }
        utilisateur.setMotDePasse(motDePasse);
        
        /* Validation du champ nom. */
        try {
            validationNom( nom );
        } catch (InvalidParameterException e) {
        	rajouterErreur(CHAMP_NOM, e.getMessage());
        }	
        utilisateur.setNom(nom);
        
        return utilisateur;
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
	
	public String getResultat() {
		return resultat;
	}
	

	public Map<String, String> getErreurs() {
		return erreurs;
	}
	
	private void rajouterErreur(String champ, String message) {
	    erreurs.put(champ, message);
	}
	
}
