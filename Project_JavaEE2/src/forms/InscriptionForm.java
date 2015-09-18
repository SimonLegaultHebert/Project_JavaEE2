package forms;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import beans.Utilisateur;

/**
 * Contient la logique d'affaire du formulaire pour l'inscription.
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
	
	/**
	 * Logique d'affaire du formulaire.
	 * @param req
	 * @return
	 */
	public Utilisateur inscrireUtilisateur(HttpServletRequest req){
		String email = req.getParameter(CHAMP_EMAIL).trim();
		String motDePasse = req.getParameter(CHAMP_PASS).trim();
		String confirmation = req.getParameter(CHAMP_CONFIRMATION).trim();
		String nom = req.getParameter(CHAMP_NOM).trim();

		Utilisateur utilisateur = creerUtilisateur(email, motDePasse, confirmation, nom);
        /* Initialisation du résultat global de la validation. */
        if (erreurs.isEmpty()) {
            resultat = "Succès de l'inscription.";
        } else {
            resultat = "Échec de l'inscription.";
        }
        return utilisateur;
	}
	
	/**
	 * Valider les paramètres et retourne l'utilisateur en conséquence.
	 * @param email
	 * @param motDePasse
	 * @param confirmation
	 * @param nom
	 * @return
	 */
	private Utilisateur creerUtilisateur(String email, String motDePasse, String confirmation, String nom){
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
	
	/**
	 * Validation du courriel.
	 * @param email
	 */
	private void validationEmail(String email){
		if(email != null && !email.isEmpty()){
			if(!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")){
				throw new InvalidParameterException("Merci de saisir une adresse mail valide.");
			}		
		}else{
			throw new InvalidParameterException("Merci de saisir une adresse mail valide.");
		}
	}
	
	/**
	 * Validation du mot de passe et de la confirmation.
	 * @param motDePasse
	 * @param confirmation
	 */
	private void validationMotDePasse(String motDePasse, String confirmation){
		if(motDePasse != null && motDePasse.length() > 5 && confirmation != null && confirmation.length() > 5){
			if(!motDePasse.equals(confirmation)){
				throw new InvalidParameterException("Les mots de passe entrés sont différents, merci de les saisir à nouveau.");
			}			
		}else{
			throw new InvalidParameterException("Les mots de passe doivent contenir au moins 6 caractères.");
		}
	}
	
	/**
	 * Validation du nom.
	 * @param nom
	 */
	private void validationNom(String nom){
		if(nom == null || nom.length() < 6){
			throw new InvalidParameterException("Le nom d'usager doit contenir au moins 6 caractères.");
		}
	}
	
	/**
	 * Rajoute les erreurs de paramètres dans la Map.
	 * @param champ
	 * @param message
	 */
	private void rajouterErreur(String champ, String message) {
	    erreurs.put(champ, message);
	}
	
	public String getResultat() {
		return resultat;
	}
	
	public Map<String, String> getErreurs() {
		return erreurs;
	}
}
