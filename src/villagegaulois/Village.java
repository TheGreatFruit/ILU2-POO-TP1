package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {

		if (chef==null) {
			throw new VillageSansChefException("Ce village n'a pas de chef !");
		}
		
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduits) {
	    StringBuilder chaine = new StringBuilder();

	    chaine.append(vendeur.getNom() + " cherche un endroit pour vendre "
	            + nbProduits + " " + produit + ".\n");

	    int indiceEtal = marche.trouverEtalLibre();

	    if (indiceEtal != -1) {
	        marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduits);

	        chaine.append("Le vendeur " + vendeur.getNom() + " vend des "
	                + produit + " à l'étal n°" + (indiceEtal + 1) + ".\n");
	    } else {
	        chaine.append("Il n'y a plus d'étal libre.\n");
	    }

	    return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalsT = marche.trouverEtals(produit);
		
		if(etalsT.length == 0) {
			chaine.append("Il n'y a pas de vendeur qui propose des "
					+ produit + " au marché.\n");
		}		
		else if(etalsT.length == 1) {
			chaine.append("Seul le vendeur " + etalsT[0].getVendeur().getNom()
					+ " propose des " + produit + " au marché.\n");
		} else {
			chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
			for(int i=0; i< etalsT.length;i++) {
				chaine.append("- " + etalsT[i].getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	
	// Renvoie l'etal correspondant au vendeur
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		return rechercherEtal(vendeur).libererEtal();
	}
	
	public String afficherMarche() {
		return marche.afficherMarche();
	}
	
	private static class Marche{
		private Etal[] etals;
		
		
		private Marche(int nbEtals){
		etals = new Etal[nbEtals];
		for(int i=0; i < nbEtals;i++) {
			etals[i] = new Etal();
		}
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit){
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre(){
			for(int i=0 ; i < etals.length; i++){
				if (! etals[i].isEtalOccupe())
					return i;
			}
			return -1;
		}

		private Etal[] trouverEtals(String produit){
			int cpt = 0;
			int  cpt2 = 0;
			for (int i=0; i< etals.length; i++){
				if(etals[i] != null && etals[i].contientProduit(produit)) {
					cpt ++;
				}
			}		
			
			Etal[] etalArticle = new Etal[cpt];
			
			for(int i=0; i<etals.length;i++) {
				if(etals[i].contientProduit(produit)){
					etalArticle[cpt2] = etals[i];
					cpt2++;
				}
			}
			return etalArticle;				
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0; i < etals.length;i++) {
				if(etals[i].getVendeur().getNom() == gaulois.getNom())
					return etals[i];
			}
			return null;
		}

		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			for(int i=0; i < etals.length;i++) {
				chaine.append(etals[i].afficherEtal());				
			}
			return chaine.toString();
		}
			
	}
}
	
	
	
