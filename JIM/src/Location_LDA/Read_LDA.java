package Location_LDA;

import input_output_interface.data_storage;

import java.io.BufferedReader;

import exception.length_check_exception;

public class Read_LDA {
	
	public static double[][] load_matrix(BufferedReader br)
	{
		try{
			String line=br.readLine();
			String part[]=line.split(" ");
			int len1=Integer.parseInt(part[0]);
			int len2=Integer.parseInt(part[1]);
			double matrix[][]=new double[len1][len2];
			for(int i=0;i<len1;i++){
				line=br.readLine();
				part=line.split(" ");
				if(part.length!=len2){
					System.out.println(part.length);
					throw new length_check_exception();
				}
				for(int j=0;j<len2;j++){
					matrix[i][j]=Double.parseDouble(part[j]);
				}
			}
			System.out.println("Load matrix complete!");
			return matrix;
		}
		catch(length_check_exception l){
			l.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static double[] load_parameter(BufferedReader br, ULA_LDA ula)
	{
		try{
			String line, part[];
			// U 
			line = br.readLine();
			part = line.split(" ");
			ula.U = Integer.parseInt(part[1]);
			System.out.println("U "+ula.U);
			
			// V
			line = br.readLine();
			part = line.split(" ");
			ula.V = Integer.parseInt(part[1]);
			System.out.println("V "+ula.V);
			
			// K
			line = br.readLine();
			part = line.split(" ");
			ula.K = Integer.parseInt(part[1]);
			System.out.println("K "+ula.K);
			
			// M
			line = br.readLine();
			part = line.split(" ");
			ula.M = Integer.parseInt(part[1]);			
			System.out.println("M "+ula.M);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static double[] load_parameter(BufferedReader br, LA_baseline la)
	{
		try{
			String line, part[];
			// U 
			line = br.readLine();
			part = line.split(" ");
			la.U = Integer.parseInt(part[1]);
			System.out.println("U "+la.U);
			
			// V
			line = br.readLine();
			part = line.split(" ");
			la.V = Integer.parseInt(part[1]);
			System.out.println("V "+la.V);
			
			// K
			line = br.readLine();
			part = line.split(" ");
			la.K = Integer.parseInt(part[1]);
			System.out.println("K "+la.K);
			
			// M
			line = br.readLine();
			part = line.split(" ");
			la.M = Integer.parseInt(part[1]);			
			System.out.println("M "+la.M);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public static double[] load_parameter(BufferedReader br, LDA ula)
	{
		try{
			String line, part[];
			// U 
			line = br.readLine();
			part = line.split(" ");
			ula.U = Integer.parseInt(part[1]);
			System.out.println("U "+ula.U);
			
			// V
			line = br.readLine();
			part = line.split(" ");
			ula.V = Integer.parseInt(part[1]);
			System.out.println("V "+ula.V);
			
			// K
			line = br.readLine();
			part = line.split(" ");
			ula.K = Integer.parseInt(part[1]);
			System.out.println("K "+ula.K);
			
			// M
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	public static double[] load_parameter(BufferedReader br, ILA_LDA ila)
	{
		try{
			String line, part[];
			// U 
			line = br.readLine();
			part = line.split(" ");
			ila.U = Integer.parseInt(part[1]);
			System.out.println("U "+ila.U);
			
			// V
			line = br.readLine();
			part = line.split(" ");
			ila.V = Integer.parseInt(part[1]);
			System.out.println("V "+ila.V);
			
			// K
			line = br.readLine();
			part = line.split(" ");
			ila.K = Integer.parseInt(part[1]);
			System.out.println("K "+ila.K);
			
			// M
			line = br.readLine();
			part = line.split(" ");
			ila.M = Integer.parseInt(part[1]);			
			System.out.println("M "+ila.M);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public static double[] load_parameter(BufferedReader br, CA_baseline ca)
	{
		try{
			String line, part[];
			// U 
			line = br.readLine();
			part = line.split(" ");
			ca.U = Integer.parseInt(part[1]);
			System.out.println("U "+ca.U);
			
			// V
			line = br.readLine();
			part = line.split(" ");
			ca.V = Integer.parseInt(part[1]);
			System.out.println("V "+ca.V);
			
			// K
			line = br.readLine();
			part = line.split(" ");
			ca.K = Integer.parseInt(part[1]);
			System.out.println("K "+ca.K);
			
			// M
			line = br.readLine();
			part = line.split(" ");
			ca.M = Integer.parseInt(part[1]);			
			System.out.println("M "+ca.M);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	public static double[] load_parameter(BufferedReader br, UILA_LDA uila)
	{
		try{
			String line, part[];
			// U 
			line = br.readLine();
			part = line.split(" ");
			uila.U = Integer.parseInt(part[1]);
			System.out.println("U "+uila.U);
			
			// V
			line = br.readLine();
			part = line.split(" ");
			uila.V = Integer.parseInt(part[1]);
			System.out.println("V "+uila.V);
			
			// K
			line = br.readLine();
			part = line.split(" ");
			uila.K = Integer.parseInt(part[1]);
			System.out.println("K "+uila.K);
			
			// M
			line = br.readLine();
			part = line.split(" ");
			uila.M = Integer.parseInt(part[1]);			
			System.out.println("M "+uila.M);
			
			// L
			line = br.readLine();
			part = line.split(" ");
			uila.L = Integer.parseInt(part[1]);			
			System.out.println("L "+uila.L);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static double[] load_parameter(BufferedReader br, ILCA_LDA uila)
	{
		try{
			String line, part[];
			// U 
			line = br.readLine();
			part = line.split(" ");
			uila.U = Integer.parseInt(part[1]);
			System.out.println("U "+uila.U);
			
			// V
			line = br.readLine();
			part = line.split(" ");
			uila.V = Integer.parseInt(part[1]);
			System.out.println("V "+uila.V);
			
			// K
			line = br.readLine();
			part = line.split(" ");
			uila.K = Integer.parseInt(part[1]);
			System.out.println("K "+uila.K);
			
			// M
			line = br.readLine();
			part = line.split(" ");
			uila.M = Integer.parseInt(part[1]);			
			System.out.println("M "+uila.M);
			
			// C
			line = br.readLine();
			part = line.split(" ");
			uila.C = Integer.parseInt(part[1]);			
			System.out.println("C "+uila.C);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static double[] load_array(BufferedReader br)
	{
		try{
			String line=br.readLine();
			int len1=Integer.parseInt(line);
			double array[] = new double[len1];
			line=br.readLine();
			String part[]=line.split(" ");
			if(part.length!=len1){
				System.out.println(part.length);
				throw new length_check_exception();
			}
			for(int i=0;i<len1;i++){
				array[i]=Double.parseDouble(part[i]);
			}
			System.out.println("Load array complete!");
			return array;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void load_model(ULA_LDA ula){
		String path = ula.outputPath;
		//String line;
		
		// locTopic
		String filename = path + "matrix/locTopic.txt";
		BufferedReader ratingReader = data_storage.file_handle_read(filename);
		ula.locTopicDistribution = load_matrix(ratingReader);
		
		// userTopic
		filename = path + "matrix/userTopic.txt";
		ratingReader = data_storage.file_handle_read(filename);
		ula.userTopicDistribution = load_matrix(ratingReader);
		
		// topicWord
		filename = path + "matrix/topicWord.txt";
		ratingReader = data_storage.file_handle_read(filename);
		ula.topicItemDistribution = load_matrix(ratingReader);
		
		// lambda_u
		filename = path + "matrix/lambda_u.txt";
		ratingReader = data_storage.file_handle_read(filename);
		ula.lambda_u = load_array(ratingReader);
		
		// parameter
		filename = path + "matrix/parameter.txt";
		ratingReader = data_storage.file_handle_read(filename);
		load_parameter(ratingReader, ula);
	}
	
	
	
	
	public static void load_model(LDA ula){
		String path = ula.outputPath;
		String line;
		
		// locTopic
/*		String filename = path + "matrix/locTopic.txt";
		BufferedReader ratingReader = data_storage.file_handle_read(filename);
		ula.locTopicDistribution = load_matrix(ratingReader);*/
		
		// userTopic
		String filename = path + "matrix/userTopic.txt";
		BufferedReader ratingReader = data_storage.file_handle_read(filename);
		ula.userTopicDistribution = load_matrix(ratingReader);
		
		// topicWord
		filename = path + "matrix/topicWord.txt";
		ratingReader = data_storage.file_handle_read(filename);
		ula.topicItemDistribution = load_matrix(ratingReader);
		
		// lambda_u
/*		filename = path + "matrix/lambda_u.txt";
		ratingReader = data_storage.file_handle_read(filename);
		ula.lambda_u = load_array(ratingReader);*/
		
		// parameter
		filename = path + "matrix/parameter.txt";
		ratingReader = data_storage.file_handle_read(filename);
		load_parameter(ratingReader, ula);
	}
	
	
	
	public static void load_model(ILA_LDA ila){
		String path = ila.outputPath;
		String line;
		
		// userTopic
		String filename = path + "matrix/userTopic.txt";
		BufferedReader ratingReader = data_storage.file_handle_read(filename);
		ila.userTopicDistribution = load_matrix(ratingReader);
		
		// topicWord
		filename = path + "matrix/topicWord.txt";
		ratingReader = data_storage.file_handle_read(filename);
		ila.topicItemDistribution = load_matrix(ratingReader);
		
		// topicLoc
		filename = path + "matrix/topicLoc.txt";
		ratingReader = data_storage.file_handle_read(filename);
		ila.topicLocDistribution = load_matrix(ratingReader);

		
		// parameter
		filename = path + "matrix/parameter.txt";
		ratingReader = data_storage.file_handle_read(filename);
		load_parameter(ratingReader, ila);
	}
	
	
	public static void load_model(CA_baseline ca){
		String path = ca.outputPath;
		String line;
		
		// userTopic
		String filename = path + "matrix/userTopic.txt";
		BufferedReader ratingReader = data_storage.file_handle_read(filename);
		ca.userTopicDistribution = load_matrix(ratingReader);
		
		// topicWord
		filename = path + "matrix/topicWord.txt";
		ratingReader = data_storage.file_handle_read(filename);
		ca.topicItemDistribution = load_matrix(ratingReader);
		
		// topicLoc
		filename = path + "matrix/topicLoc.txt";
		ratingReader = data_storage.file_handle_read(filename);
		ca.topicLocDistribution = load_matrix(ratingReader);

		
		// parameter
		filename = path + "matrix/parameter.txt";
		ratingReader = data_storage.file_handle_read(filename);
		load_parameter(ratingReader, ca);
	}
	
	
	
	public static void load_model(UILA_LDA uila){
		String path = uila.outputPath;
		String line;
		
		// userTopic
		String filename = path + "matrix/userTopic.txt";
		BufferedReader ratingReader = data_storage.file_handle_read(filename);
		uila.userTopicDistribution = load_matrix(ratingReader);
		
		// locTopic
		filename = path + "matrix/locTopic.txt";
		ratingReader = data_storage.file_handle_read(filename);
		uila.locTopicDistribution = load_matrix(ratingReader);
		
		// topicWord
		filename = path + "matrix/topicWord.txt";
		ratingReader = data_storage.file_handle_read(filename);
		uila.topicItemDistribution = load_matrix(ratingReader);
		
		// topicLoc
		filename = path + "matrix/topicLoc.txt";
		ratingReader = data_storage.file_handle_read(filename);
		uila.topicLocDistribution = load_matrix(ratingReader);
		
		// lambda_u
		filename = path + "matrix/lambda_u.txt";
		ratingReader = data_storage.file_handle_read(filename);
		uila.lambda_u = load_array(ratingReader);
		
		// parameter
		filename = path + "matrix/parameter.txt";
		ratingReader = data_storage.file_handle_read(filename);
		load_parameter(ratingReader, uila);
	}
	
	public static void load_model(ILCA_LDA ilca){
		String path = ilca.outputPath;
		//String line;
		
		// userTopic
		String filename = path + "matrix/userTopic.txt";
		BufferedReader ratingReader = data_storage.file_handle_read(filename);
		ilca.userTopicDistribution = load_matrix(ratingReader);
		
		// locTopic
		filename = path + "matrix/locTopic.txt";
		ratingReader = data_storage.file_handle_read(filename);
		ilca.locTopicDistribution = load_matrix(ratingReader);
		
		// topicWord
		filename = path + "matrix/topicWord.txt";
		ratingReader = data_storage.file_handle_read(filename);
		ilca.topicItemDistribution = load_matrix(ratingReader);
		
		// topicCat
		filename = path + "matrix/topicCategory.txt";
		ratingReader = data_storage.file_handle_read(filename);
		ilca.topicCategoryDistribution = load_matrix(ratingReader);
		
		// lambda_u
		filename = path + "matrix/lambda_u.txt";
		ratingReader = data_storage.file_handle_read(filename);
		ilca.lambda_u = load_array(ratingReader);
		
		// parameter
		filename = path + "matrix/parameter.txt";
		ratingReader = data_storage.file_handle_read(filename);
		load_parameter(ratingReader, ilca);
	}
	
	
	public static void load_model(LA_baseline la){
		String path = la.outputPath;
		//String line;
		
		// locTopic
		String filename = path + "matrix/locTopic.txt";
		BufferedReader ratingReader = data_storage.file_handle_read(filename);
		la.locTopicDistribution = load_matrix(ratingReader);
		
		// userTopic
		filename = path + "matrix/userTopic.txt";
		ratingReader = data_storage.file_handle_read(filename);
		la.userTopicDistribution = load_matrix(ratingReader);
		
		// topicWord
		filename = path + "matrix/topicWord.txt";
		ratingReader = data_storage.file_handle_read(filename);
		la.topicItemDistribution = load_matrix(ratingReader);
		
		// lambda_u
		filename = path + "matrix/lambda_u.txt";
		ratingReader = data_storage.file_handle_read(filename);
		la.lambda_u = load_array(ratingReader);
		
		// parameter
		filename = path + "matrix/parameter.txt";
		ratingReader = data_storage.file_handle_read(filename);
		load_parameter(ratingReader, la);
	}
}
