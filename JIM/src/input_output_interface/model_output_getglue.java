package input_output_interface;

import java.io.OutputStreamWriter;
import java.util.ArrayList;

import model.Social.model_topic_sharing_cluster_netplsa;
import parser.GetGluemovieInfoParser.gmovieList;
import ancillary.matrix_and_map_2;
import ancillary.sort_major;
import basic.Path;
import basic.myarray;



public class model_output_getglue {
	public static void outputMovieInfo(gmovieList ml,int listId,OutputStreamWriter osw)
	{
		try{
			//osw.write(ml.movieInfoList.get(listId).name+" "+ml.movieInfoList.get(listId).year+" ");
			osw.write(ml.gmovieinfoList.get(listId).name+" ");
			//ArrayList<Integer> types=ml.movieInfoList.get(listId).type;
			int type = ml.gmovieinfoList.get(listId).type;
		/*	
			int size = types.size();
			for(int i=0;i<size;i++){
				if(i==0)
					osw.write(ml.typeList[types.get(i)].typename+"");
				else
					osw.write("|"+ml.typeList[types.get(i)].typename);
			}
		*/
			osw.write(ml.typeList[type].typename+"");
			osw.write(" ");
			osw.write("Starting timestamp:"+ml.gmovieinfoList.get(listId).firstratetime+" ");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	// for model topic sharing cluster netplsa
	public static void outputTopic(matrix_and_map_2 m, model_topic_sharing_cluster_netplsa pmp,
									gmovieList ml, Path PathDef){
		try{
			OutputStreamWriter osw = data_storage.file_handle(PathDef.OUTPUT_PATH+"movieTopic/movie_topic");
			int numberOfMovies = pmp.number_of_keywords;
			int numberOfTopics = pmp.number_of_topics;
			sort_major sm = new sort_major();
			myarray [][] ma = new myarray[numberOfTopics][numberOfMovies];
			sm.sort_first_dimension(pmp.keywords_given_topics, m.reverse_map1, numberOfMovies, ma);
			final int SIZE_OF_OUTPUT = 20;
			for(int t=0; t<numberOfTopics;t++){
				osw.write("Topic "+t+":\n");
				for(int i=0; i<SIZE_OF_OUTPUT; i++){
					int movieId = ma[t][i].key;
					double score = ma[t][i].value;
					int listId = ml.movieIdMap.get(movieId);
					outputMovieInfo(ml, listId, osw);
					osw.write(score+"");
					osw.write("\n");
				}
				osw.write("\n");
				osw.flush();
			}
			osw.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
