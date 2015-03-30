
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class opinionmining
{
	
	public static void main(String[] args) throws Exception
	{
		
        OSA o=new OSA();
		BufferedReader br_input=null;
		BufferedReader br_pros=null,br_cons=null;
		
		try
		{

			String inputFile="Output.txt";	     //args[0];
			String prosFile="IntegratedPros.txt";	//args[1];
			String consFile="IntegratedCons.txt";   //args[2];
			String comparative_wrd=null;
			String feature=null;		
			String line;
			String entity1,entity2;

			br_input=new BufferedReader(new FileReader(inputFile)) ;
			o.pw=new PrintWriter("opinion.txt");

			br_pros=new BufferedReader(new FileReader(prosFile)) ;
			br_cons=new BufferedReader(new FileReader(consFile)) ;

			while((line=br_input.readLine())!=null)
			{
				if(line.contains("(") && line.contains("<"))
				{
					entity1=line.substring(line.indexOf("1_<")+3,line.indexOf(">2_")).trim();
					entity2=line.substring(line.indexOf("2_<")+3,line.indexOf(">3_")).trim();
					feature=line.substring(line.indexOf("3_<")+3,line.indexOf(">4_")).trim();
					comparative_wrd=line.substring(line.indexOf("4_<")+3,line.lastIndexOf(">")).trim();

					
					//-----------------------TreeMap Created-----------------------
					 Wnet word1= new Wnet();
					 word1.wordnetfunc(comparative_wrd);
					
					 
					if(entity1.equals("") && entity2.equals(""))
					{
						o.pw.write("ENTITY VALUES ARE NOT THERE\n");
					}
					else
					{
						o.OSA_positive(comparative_wrd,feature,br_pros,br_cons);
						o.OSA_negative(comparative_wrd,feature,br_pros,br_cons);						
						o.calculateOSA(entity1,entity2);
					}
				}
				else
				{	

					o.pw.write(line.substring(line.indexOf("_")+1));
					o.pw.write("\n");
				}
				o.P_comp=1;o.P_feature=1;o.P_combine=1;o.N_comp=1;o.N_feature=1;o.N_combine=1;
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				br_pros.close();
				br_cons.close();
				br_input.close();
				o.pw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Done!");
		}
	 }
		
}
