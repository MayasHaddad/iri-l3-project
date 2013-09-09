package td.td1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tools.FrenchStemmer;
import tools.FrenchTokenizer;
import tools.Normalizer;
import tools.TreeTaggerNormalizer;

/**
 * TD 1
 * @author xtannier
 *
 */
public class TD1 {
	/**
	 * Le répertoire du corpus
	 */
	// CHEMIN A CHANGER si nécessaire
	private static String DIRNAME = "/net/x2/u/etudiant/mhadda1/textesTIW/";
	/**
	 * L'exécutable de TreeTagger
	 */
	// CHEMIN A CHANGER si nécessaire
	private static String TREETAGGER_BIN = "/net/public/iri/tree-tagger/cmd/tree-tagger-french";
	/**
	 * Un fichier de ce répertoire
	 */
	private static String FILENAME = DIRNAME + "texte.95-1.txt";

	/**
	 * Créez une méthode \emph{main} permettant de 
	 * raciniser le texte d'un fichier du corpus.
	 * @param fileName
	 * @throws IOException
	 */
	private static void stemming(String fileName) throws IOException {
		Normalizer stemmer = new FrenchStemmer();
		try {
			System.out.println(stemmer.normalize(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// DONE !
	}



	/**
	 * Une méthode permettant d'afficher le nombre d'occurrences
	 * de chaque mot dans un fichier.
	 * @param fileName le fichier à analyser
	 * @param normalizer la classe de normalisation utilisée
	 * @throws IOException
	 */
	private static void getTermFrequencies(String fileName, Normalizer stemmer) throws IOException {
		// Création de la table des mots
		HashMap<String, Integer> hits = new HashMap<String, Integer>();
		//Normalizer stemmer = new FrenchStemmer();
		ArrayList<String> ListMots = stemmer.normalize(fileName);
		for (int i=0;i < ListMots.size()-1;i++)
		{
			if (hits.containsKey(ListMots.get(i)))
			{
				hits.put(ListMots.get(i),hits.get(ListMots.get(i))+1);
			}else hits.put(ListMots.get(i),1);
		}
		// TODO !

		// Affichage du résultat
		for (Map.Entry<String, Integer> hit : hits.entrySet()) {
			System.out.println(hit.getKey() + "\t" + hit.getValue());
		}
	}


	/**
	 * Une méthode permettant d'afficher le nombre d'occurrences
	 * de chaque mot pour l'ensemble du corpus.
	 * @param dirName le répertoire à analyser
	 * @param normalizer la classe de normalisation utilisée
	 * @throws IOException
	 */
	private static void getCollectionFrequency(String dirName, Normalizer stemmer) throws IOException {
		// Création de la table des mots
		HashMap<String, Integer> hits = new HashMap<String, Integer>();
		File dir = new File(dirName);
		//Normalizer stemmer = new FrenchStemmer();
		if (dir.isDirectory()) {
			// Liste des fichiers du répertoire
			// ajouter un filtre (FileNameFilter) sur les noms
			// des fichiers si nécessaire
			String[] fileNames = dir.list();

			// Parcours des fichiers et remplissage de la table
			for(String fileName : fileNames){
				ArrayList<String> ListMots = stemmer.normalize(dirName+fileName);
				for (int i=0;i < ListMots.size()-1;i++)
				{
					if (hits.containsKey(ListMots.get(i).toLowerCase()))
					{
						hits.put(ListMots.get(i).toLowerCase(),hits.get(ListMots.get(i).toLowerCase())+1);
					}else hits.put(ListMots.get(i).toLowerCase(),1);
				}
			}
			for (Map.Entry<String, Integer> hit : hits.entrySet()) {
				System.out.println(hit.getKey() + "\t" + hit.getValue());
			}

		}

		// Affichage du résultat (avec la fréquence)	
		for (Map.Entry<String, Integer> hit : hits.entrySet()) {
			System.out.println(hit.getKey() + "\t" + hit.getValue());
		}

	}

	/**
	 * Main, appels de toutes les méthodes des exercices du TD1. 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			stemming(FILENAME);

			Normalizer stemmer = new FrenchTokenizer();
			//Normalizer stemmer = new FrenchStemmer();
			//Normalizer stemmer = new TreeTaggerNormalizer(TREETAGGER_BIN);
			//getTermFrequencies(FILENAME,stemmer);
			//getCollectionFrequency(DIRNAME,stemmer);
		}catch (IOException e) {
			e.printStackTrace();
		}

		// BEING DONE
	}
}
