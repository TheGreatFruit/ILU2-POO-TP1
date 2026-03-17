package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;
import villagegaulois.VillageSansChefException;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		
		Village village = new Village("le village des réductibles", 10, 5);
		Gaulois bonemine = new Gaulois("Bonemine", 7);
		village.ajouterHabitant(bonemine);
		village.installerVendeur(bonemine, "fleurs", 20);
		Etal etal = new Etal();
		
		
		
		etal = village.rechercherEtal(bonemine);
		
		System.out.println("\nTest de l'exeception partie 2 : Argument invalide ou étal vide (décommenter la ligne selon le test)");
		try {
			//etal.libererEtal();
			//etal.acheterProduit(1, bonemine);
			etal.acheterProduit(0, bonemine);
		}catch(IllegalArgumentException | IllegalStateException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println("\nTest de l'exeception partie 3, sans le chef de village");
		try {
			village.afficherVillageois();
		}catch (VillageSansChefException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println("Fin du test");
	}
}
