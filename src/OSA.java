
import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
import java.io.PrintWriter;


public class OSA {

	double P_comp=1,P_feature=1,P_combine=1,N_comp=1,N_feature=1,N_combine=1;
	double P_OSA,N_OSA;
	PrintWriter pw=null;

	void countPros(String comparative_wrd,String feature,BufferedReader br)throws Exception
	{
		
		System.out.println("---------Testing----------");
		comparative_wrd=comparative_wrd.trim();
		feature=feature.trim();
		
		String regex=".*\\b("+comparative_wrd+")\\b.*";
		String regex_feature=".*\\b("+feature+")\\b.*";
		
		String line=null;
		//String[] splitString;
		
		while((line=br.readLine())!=null)
		{
			//splitString = (line.split("[\\s+><\\.+\\)\\(,!/:;]"));
			line=line.replaceAll("[><\\.+\\)\\(,!/:;]", " ");
			//System.out.println("line="+line);
			//System.out.println("comparative_wrd="+comparative_wrd);
			//System.out.println("faeture="+feature);
			
			if(comparative_wrd.equals(""))
			{
				//System.out.println("hello");
				P_comp=1;
			}
			else
			{
				//if(line.contains(comparative_wrd))		
				if(line.matches(regex))
				{
					P_comp++;
				}
			}

			if(feature.equals(""))
			{
				P_feature=1;
			}
			else
			{
				//if(line.contains(feature))	
				if(line.matches(regex_feature))
				{
					P_feature++;
				}
			}


//if(!comparative_wrd.equals("") && !feature.equals("") && line.contains(comparative_wrd+" "+feature))

			if(!comparative_wrd.equals("") && !feature.equals("") && line.matches(regex) && line.matches(regex_feature))
			{
				int p=line.indexOf(comparative_wrd)+comparative_wrd.length();
				if(p < line.indexOf(feature))
					P_combine++;
			}


		}
	}

	void countCons(String comparative_wrd,String feature,BufferedReader br) throws Exception
	{
		comparative_wrd=comparative_wrd.trim();
		feature=feature.trim();
		
		String regex=".*\\b("+comparative_wrd+")\\b.*";
		String regex_feature=".*\\b("+feature+")\\b.*";
		
		String line=null;
		//String[] splitString;

		while((line=br.readLine())!=null)
		{
			line=line.replaceAll("[><\\.+\\)\\(,!/:;]", " ");

			if(comparative_wrd.equals(""))
			{
				N_comp=1;
			}
			else
			{
				//if(line.contains(comparative_wrd))
				if(line.matches(regex))
				{
					N_comp++;
				}
			}

			if(feature.equals(""))
			{
				N_feature=1;
			}
			else
			{
				//String regex=".*\\b("+feature.trim()+")\\b.*";
				
				if(line.matches(regex_feature))
				{
					N_feature++;
				}
			}

			//if(!comparative_wrd.equals("") && !feature.equals("") && line.contains(comparative_wrd+" "+feature))
			
			if(!comparative_wrd.equals("") && !feature.equals("") && line.matches(regex) && line.matches(regex_feature))
			{
				int p=line.indexOf(comparative_wrd)+comparative_wrd.length();
				if(p < line.indexOf(feature))	
					N_combine++;
			}
		}
	}
	void calculateOSA(String entity1,String entity2)
	{
		/*
		System.out.println("P_combine="+P_combine);
		System.out.println("P_comp="+P_comp);
		System.out.println("P_feature="+P_feature);
		*/
		P_OSA=Math.log(P_combine/(P_comp*P_feature));
		N_OSA=Math.log(N_combine/(N_comp*N_feature));
		/*
		System.out.println("P_OSA "+P_OSA);
		System.out.println("N_OSA "+N_OSA);
		*/
		if(P_OSA>N_OSA)
		{
			pw.write(entity1+"\n");
		}
		else
		{
			pw.write(entity2+"\n");
		}
	}

	void OSA_positive(String comparative_wrd,String feature,BufferedReader br_pros,BufferedReader br_cons) throws Exception
	{
		countPros(comparative_wrd,feature,br_pros);

		for(int i=0;i<5;i++)
		{
			Wnet word= new Wnet();
			String syno=word.NextSynonym();
			if(syno!=null && !(syno.equalsIgnoreCase(comparative_wrd)))
				countPros(syno,feature,br_pros);

			String anto=word.NextAntonym();
			if(anto!=null)
				countPros(anto,feature,br_cons);//antonym of C
		}

	}
	void OSA_negative(String comparative_wrd,String feature,BufferedReader br_pros,BufferedReader br_cons) throws Exception
	{
		countCons(comparative_wrd,feature,br_cons);
		for(int i=0;i<5;i++)
		{
			Wnet word= new Wnet();
			String syno=word.NextSynonym();
			if(syno!=null && !(syno.equalsIgnoreCase(comparative_wrd)))
				countCons(syno,feature,br_cons);

			String anto=word.NextAntonym();
			if(anto!=null)
				countCons(anto,feature,br_pros);//antonym of C
		}

	}
}
