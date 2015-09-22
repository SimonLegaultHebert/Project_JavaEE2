package forms;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import beans.Utilisateur;

public class ConnexionForm{
	
	private static final String CHAMP_EMAIL = "email";
	private static final String CHAMP_PASS = "motdepasse";
	private static final Utilisateur UTILISATEUR = new Utilisateur();
	
	private String resultat;
	private HashMap<String, String> erreurs = new HashMap<String, String>();
	
	public Utilisateur connecterUtilisateur(HttpServletRequest req){
		validationMotDePasse(req.getParameter(CHAMP_PASS));
		validationEmail(req.getParameter(CHAMP_EMAIL));
        if ( erreurs.isEmpty() ) {
            resultat = "Succès de la connexion.";
        } else {
            resultat = "Échec de la connexion.";
        }
        return UTILISATEUR;
	}
	
	private void validationMotDePasse(String motDePasse){
    	if(motDePasse == null  || motDePasse.length() < 3){
    		rajouterErreur(CHAMP_PASS, "Le mot de passe doit contenir plus que trois caractères.");
    	}
    	UTILISATEUR.setMotDePasse(motDePasse);
    }
    

	private void validationEmail(String email){
		if(email != null && !email.isEmpty()){
			if(!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")){
				rajouterErreur(CHAMP_EMAIL, "Merci de saisir une adresse mail valide.");
			}		
		}else{
			rajouterErreur(CHAMP_EMAIL, "Merci de saisir une adresse mail valide.");
		}
		UTILISATEUR.setEmail(email);
	}
	
	private void rajouterErreur(String champ, String message){
		erreurs.put(champ, message);
	}
	
	public String getResultat() {
		return resultat;
	}

	public HashMap<String, String> getErreurs() {
		return erreurs;
	}

	
	
	
	

}
