import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import edu.smu.tspell.wordnet.*;

public class Wnet
{
	
	static TreeMap<String,Integer> word= new TreeMap<String,Integer>();
	static TreeMap<String,Integer> antoword= new TreeMap<String,Integer>();
	
	void clearTreeMap()
	{
		word.clear();
		antoword.clear();
	}

	String NextSynonym()
	{
		String syno=null;
		try{
		syno=word.firstKey();
		word.remove(syno);	
		}
		catch(Exception e)
		{
		}
		return syno;
	}
	
	String NextAntonym()
	{
		String anto=null;
		try{
		anto=antoword.firstKey();
		antoword.remove(anto);	
		}
		catch(Exception e)
		{
		}
		return anto;
	}
	
	void printmap()
	{
		System.out.println("\nSynonyms are :");
		
		Set<String> keys = word.keySet();
	       for(String p:keys){
	    	   System.out.print(p+"; ");
	       }
	       
	    System.out.println("\nAntonyms are :");	
		Set<String> antokeys = antoword.keySet();
	        for(String p:antokeys){
		    	   System.out.print(p+"; ");
	       }
	}
	
	void wordnetfunc(String wordForm)
	{
		System.setProperty("wordnet.database.dir","/usr/local/WordNet-3.0/dict");
		
		word.clear();
		antoword.clear();
		System.out.println("\n"+wordForm);
		
		if(wordForm==null)
			return;
		
		WordNetDatabase database = WordNetDatabase.getFileInstance();
		
			Synset[] synsets = database.getSynsets(wordForm);
			
			//  Display the word forms and definitions for synsets retrieved
			if (synsets.length > 0)
			{
				for (int i = 0; i < synsets.length; i++)
				{
					
					String[] wordForms = synsets[i].getWordForms();					
					WordSense ws[]=synsets[i].getAntonyms(wordForm);
					
					for (int j = 0; j < wordForms.length; j++)
					{
						String str=wordForms[j];
						
						if(!word.containsKey(str))
							word.put(str, j);		
					}
					
					for (int j = 0; j < ws.length ; j++)
					{
						String s=ws[j].toString();
						
						StringTokenizer stok=new StringTokenizer(s,"'");
						String token=stok.nextToken();
						
						if(!antoword.containsKey(token))
							antoword.put(token, 0);
					}
					
				}
			}
		}
	}