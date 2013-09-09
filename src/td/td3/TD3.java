package td.td3;

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


public class TD3 {

	
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
	
	public static double getSimilarity(String fileName1, String fileName2)throws IOException
	{
		double score=0;
		HashMap<String, Double> weightmap1 = new HashMap<String, Double>();
		HashMap<String, Double> weightmap2 = new HashMap<String, Double>();
		ArrayList<String> lines=new ArrayList();
		
		try{
			InputStream ips=new FileInputStream(fileName1); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				lines.add(ligne);
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
		ArrayList<String> mots=new ArrayList();
		Double wikc=0.0;
		for (String ligne:lines){
			String mot=ligne.split("\t")[0];
			mots.add(mot);
			Double wik=Double.parseDouble(ligne.split("\t")[1]);
			weightmap1.put(mot,wik);
			wikc+=Math.pow(wik,2);
		}
		Double r1=Math.sqrt(wikc);
		lines.clear();
		try{
			InputStream ips=new FileInputStream(fileName2); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				lines.add(ligne);
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
		Double wjkc=0.0;
		Double sumwijk=0.0;
		for (String ligne:lines){
			Double wjk=Double.parseDouble(ligne.split("\t")[1]);
			String mot=ligne.split("\t")[0];
			wjkc+=Math.pow(wjk,2);
			weightmap2.put(mot,wjk);
			if(weightmap1.containsKey(mot))
			sumwijk+=weightmap1.get(mot)*weightmap2.get(mot);
		}
		Double r2= Math.sqrt(wjkc);
		score=sumwijk/(r1*r2);
		return score;
	}
	
	public static void getSimilarDocuments(String fileName,String dirName) throws IOException{
		File dir = new File(dirName);
		if (dir.isDirectory()) {
		String[] fileNames = dir.list();
			File fich=new File(DIRNAME+"/similarities/"+FILE+".sim");
			FileOutputStream ps=new FileOutputStream(fich);
			PrintWriter ecrivain =new PrintWriter(ps);
			for(String poidName:fileNames)
				ecrivain.println(poidName + "\t" + getSimilarity(fileName,DIRNAME+"/poids/"+poidName));
					ecrivain.close();
					try{
						   Process proc=Runtime.getRuntime().exec("cat "+fich.toString()+" | sort -k 2n,2nr >"); 
						   InputStream in = proc.getInputStream();
						   BufferedWriter out=new BufferedWriter(new FileWriter(DIRNAME+"/similarities/"+"kk.txt"));
						   int c;
				            while ((c = in.read()) != -1) {
				                out.write((char)c);
				            }
				 
					    in.close();
					    out.flush();
					    out.close();
				 
						   }catch(IOException e){
						   }
		}
	}
	
public static void main(String[] args){
	try {
		getSimilarDocuments(DIRNAME+"/poids/"+FILE+".poids",DIRNAME+"/poids/");
		//System.out.println(getSimilarity(DIRNAME+"/poids/"+"texte.95-7.txt"+".poids",DIRNAME+"/poids/"+"texte.95-1.txt"+".poids"));
	}catch (IOException e) {
		e.printStackTrace();
	}
}
}
