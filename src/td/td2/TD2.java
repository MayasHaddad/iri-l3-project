package td.td2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tools.FrenchStemmer;
import tools.FrenchTokenizer;
import tools.Normalizer;
import tools.TreeTaggerNormalizer;
import td.td1.TD1;;

public class TD2 {

	private static String DIRNAME = "/net/x2/u/etudiant/mhadda1/textesTIW/";
	/**
	 * L'exécutable de TreeTagger
	 */
	// CHEMIN A CHANGER si nécessaire
	private static String TREETAGGER_BIN = "/net/public/iri/tree-tagger/cmd/tree-tagger-french";
	/**
	 * Un fichier de ce répertoire
	 */
	
	private static String FILE="texte.95-11.txt";
	
	private static String FILENAME = DIRNAME + FILE;
	
	
private static boolean motNotVu(ArrayList<String> l, String s){
		return !(l.contains(s));
	}
	
private static void setMotVu(ArrayList<String> l,String s){
	if(motNotVu(l,s)) l.add(s);
}

private static HashMap<String, Integer> getDocumentFrequency(String dirName,
			Normalizer stemmer) throws IOException{
	HashMap<String, Integer> hits = new HashMap<String, Integer>();
	File dir = new File(dirName);
	if (dir.isDirectory()) {
		// Liste des fichiers du répertoire
		// ajouter un filtre (FileNameFilter) sur les noms
		// des fichiers si nécessaire
		File[] files = dir.listFiles();
		ArrayList<String> listMotsVus= new ArrayList<String>();
		
		// Parcours des fichiers et remplissage de la table
		for(File file : files){
			if(!file.isDirectory()){
			String fileName=file.toString();
			ArrayList<String> ListMots = stemmer.normalize(fileName);
			for (String Mot : ListMots)
			{
				if (hits.containsKey(Mot)){
					if(!listMotsVus.contains(Mot))
						hits.put(Mot,hits.get(Mot)+1);
					
				}else hits.put(Mot,1);
				listMotsVus.add(Mot);
			}
			listMotsVus.clear();
		}
		}
		}

 	return hits;
}	

public static double getTermFrequency(String fileName,String Word, Normalizer stemmer) throws IOException{
	Double tf=0.0;
	ArrayList<String> ListMots = stemmer.normalize(fileName);
	for (String Mot : ListMots)
	{
		if (Mot.equals(Word)) tf++;
	}
	return tf;
}
		
public static HashMap<String, Double> getTfIdf(String fileName, HashMap<String, Integer> dfs,
int documentNumber, Normalizer stemmer) throws IOException{
	HashMap<String, Double> tfIdfmap = new HashMap<String, Double>();
	ArrayList<String> ListMots = stemmer.normalize(fileName);
	for (String Mot : ListMots)
	{
		if (!tfIdfmap.containsKey(Mot))	tfIdfmap.put(Mot,getTermFrequency(fileName,Mot,stemmer)*Math.log(documentNumber/dfs.get(Mot)));
	}
	 return tfIdfmap;
}

public static void getWeightFiles(String inDirName, String outDirName, Normalizer normalizer){
	File dir = new File(inDirName);
	if (dir.isDirectory()){
		String[] fileNames = dir.list();
		ArrayList<String> myListOfFiles= new ArrayList();
		// Parcours des fichiers et remplissage de la table
		for(String fileName : fileNames){
			myListOfFiles.add(fileName);
		 }
		
	}
}

public static void main(String[] args) {
			try {
				
				Normalizer stemmer = new FrenchTokenizer();
				//Normalizer stemmer = new FrenchStemmer();
				//Normalizer stemmer = new TreeTaggerNormalizer(TREETAGGER_BIN);
				//getTermFrequencies(FILENAME,stemmer);
				//for (Map.Entry<String, Integer> hit : getDocumentFrequency(DIRNAME,stemmer).entrySet())
			    //System.out.println(hit.getKey() + "\t" + hit.getValue());
				int nbDocs=0;
				File dir = new File(DIRNAME);
				File fich=new File(DIRNAME+"/poids/"+FILE+".poids");
				FileOutputStream ps=new FileOutputStream(fich);
				PrintWriter ecrivain =new PrintWriter(ps);
				if (dir.isDirectory()) nbDocs=dir.listFiles().length;
				for (Map.Entry<String, Double> hit: getTfIdf(FILENAME,getDocumentFrequency(DIRNAME,stemmer),nbDocs,stemmer).entrySet())
					ecrivain.println(hit.getKey() + "\t" + hit.getValue());
						ecrivain.close();
			}catch (IOException e) {
				e.printStackTrace();
			}

	
	
	
}
}