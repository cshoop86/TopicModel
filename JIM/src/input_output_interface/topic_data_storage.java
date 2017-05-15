package input_output_interface;
import java.io.*;

import model.Fashion.Itembased.Item_based_Fashion_Context;
import model.Fashion.Itembased.Item_based_Fashion_Sharing_Temporalparameter;
import model.Fashion.Itembased.Item_based_Fashion_with_globalcontext;
import model.Fashion.Itembased.Item_based_Fashion_with_globalcontext_Sharing_Temporalparameter;
import model.Fashion.TopicSharing.*;
import model.Fashion.TopicVarying.Topic_Varying_model;
import model.Fashion.TopicVarying.Topic_Varying_model_Background;
import model.Fashion.TopicVarying.Topic_Varying_model_Cluster_Background;
import model.Fashion.TopicVarying.Topic_Varying_model_Sharing_TemporalParameter;
import model.Fashion.TopicVarying.Topic_Varying_model_Cluster;
import model.Social.Topic_based_LocalFashion;

import java.util.Enumeration;
import java.util.Hashtable;

import model.BasicPLSA.*;


import ancillary.matrix_and_map_2;
import basic.Path;
public class topic_data_storage {
	
	public static void storeTopics(PLSA pmp,matrix_and_map_2 m,int round,boolean is_store_for_viewing,Path PathDef){
		int folder = (round+1)*10;
		String matrix_path = PathDef.MATRIX_PATH+folder+"/";
		OutputStreamWriter oswbackground = data_storage.file_handle(matrix_path+"movie_background_smoothing");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		data_storage.store_array2(oswbackground, pmp.back_ground);
		
		//OutputStreamWriter oswUserBackground = data_storage.file_handle(matrix_path+"keywords_given_background");
		
		
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);	
			data_storage.store_array(oswbackground, pmp.back_ground);
		}
		//OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		//data_storage.store_matrix(oswMovieTemporal, pmp.keywords_given_temporal);
		//OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		//data_storage.store_array(oswUserLambda, pmp.lambda_temporal);
		
		
		
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	
	
	public static void storeTopics(Item_based_Fashion_Context pmp,matrix_and_map_2 m,int round,boolean is_store_for_viewing,Path PathDef){
		int folder = (round+1)*10;
		String matrix_path = PathDef.MATRIX_PATH+folder+"/";
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_matrix2(oswMovieTemporal, pmp.keywords_given_temporal);
		
		data_storage.store_array2(oswUserLambda, pmp.lambda_temporal);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.keywords_given_temporal);
			data_storage.store_array(oswUserLambda, pmp.lambda_temporal);
		}
		
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	
	
	public static void storeTopics(Item_based_Fashion_Sharing_Temporalparameter pmp,
									matrix_and_map_2 m,int round,boolean is_store_for_viewing,Path PathDef){
		int folder = (round+1)*10;
		String matrix_path = PathDef.MATRIX_PATH+folder+"/";
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_matrix2(oswMovieTemporal, pmp.keywords_given_temporal);
		
		data_storage.store_array2(oswUserLambda, pmp.temporal_parameter);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.keywords_given_temporal);
			data_storage.store_array(oswUserLambda, pmp.temporal_parameter);
		}
		
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	
	
	public static void storeTopics(Item_based_Fashion_with_globalcontext_Sharing_Temporalparameter pmp,matrix_and_map_2 m,int round,boolean is_store_for_viewing,Path PathDef){
		int folder = (round+1)*10;
		String matrix_path = PathDef.MATRIX_PATH+folder+"/";
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_matrix2(oswMovieTemporal, pmp.keywords_given_temporal);
		
		data_storage.store_array2(oswUserLambda, pmp.temporal_parameter);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.keywords_given_temporal);
			data_storage.store_array(oswUserLambda, pmp.temporal_parameter);
		}
		
		
		
		
		
		
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	
	

	public static void storeTopics(Topic_based_LocalFashion pmp,matrix_and_map_2 m,int round,boolean is_store_for_viewing,Path PathDef){
		int folder = (round+1)*10;
		String matrix_path = PathDef.MATRIX_PATH+folder+"/";
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		
		//OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_array2(oswUserLambda, pmp.lambda_temporal);
		
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			
			
			//data_storage.store_matrix(oswMovieTemporal, pmp.topics_given_timeslice);
			data_storage.store_array(oswUserLambda, pmp.lambda_temporal);
		}
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_matrix(oswMovieTemporal, pmp.topics_given_documents_timeslices);
	
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	public static void storeTopics(Topic_based_LocalFashion pmp,matrix_and_map_2 m,boolean is_store_for_viewing,Path PathDef){
		
		String matrix_path = PathDef.MATRIX_PATH+"/";
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		
		//OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_array2(oswUserLambda, pmp.lambda_temporal);
		
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			
			
			//data_storage.store_matrix(oswMovieTemporal, pmp.topics_given_timeslice);
			data_storage.store_array(oswUserLambda, pmp.lambda_temporal);
		}
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_matrix(oswMovieTemporal, pmp.topics_given_documents_timeslices);
	
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	//
	public static void storeTopics(Topic_Sharing_model pmp,matrix_and_map_2 m,int round,boolean is_store_for_viewing,Path PathDef){
		int folder = (round+1)*10;
		String matrix_path = PathDef.MATRIX_PATH+folder+"/";
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_array2(oswUserLambda, pmp.lambda_temporal);
		data_storage.store_matrix2(oswMovieTemporal, pmp.topics_given_timeslice);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.topics_given_timeslice);
			data_storage.store_array(oswUserLambda, pmp.lambda_temporal);
		}
		
	
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	
	//
	public static void storeTopics(Topic_Sharing_model_Background pmp, 
									matrix_and_map_2 m,
									int round,
									boolean is_store_for_viewing, Path PathDef){
		
		int folder = (round+1)*10;
		String matrix_path = PathDef.MATRIX_PATH+folder+"/";
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		OutputStreamWriter oswBackgroundLambda = data_storage.file_handle(matrix_path+"background_lambda");

		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_array2(oswUserLambda, pmp.lambda_temporal);
		data_storage.store_matrix2(oswMovieTemporal, pmp.topics_given_timeslice);
		
		
		OutputStreamWriter oswMovieBackground = data_storage.file_handle(matrix_path+"keywords_given_background");
		data_storage.store_array2(oswMovieBackground, pmp.keywords_given_background);
		
		data_storage.store_array2(oswBackgroundLambda, pmp.lambda_background);
		
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.topics_given_timeslice);
			data_storage.store_array(oswUserLambda, pmp.lambda_temporal);
			
			OutputStreamWriter oswMovieBackground = data_storage.file_handle(matrix_path+"keywords_given_background");
			data_storage.store_array(oswMovieBackground, pmp.keywords_given_background);
			
			data_storage.store_array(oswBackgroundLambda, pmp.lambda_background);
		}
		
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
		
	public static void storeTopics(Topic_Sharing_model_Sharing_TemporalParameter pmp,matrix_and_map_2 m,int round,boolean is_store_for_viewing,Path PathDef){
		int folder = (round+1)*10;
		String matrix_path = PathDef.MATRIX_PATH+folder+"/";
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_array2(oswUserLambda, pmp.temporal_parameter);
		data_storage.store_matrix2(oswMovieTemporal, pmp.topics_given_timeslice);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.topics_given_timeslice);
			data_storage.store_array(oswUserLambda, pmp.temporal_parameter);
		}
		
	
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	
	public static void storeTopics(Topic_Varying_model  pmp,matrix_and_map_2 m,int round,boolean is_store_for_viewing,Path PathDef){
		int folder = (round+1)*10;
		String matrix_path = PathDef.MATRIX_PATH+folder+"/";
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswMovieTopic2 = data_storage.file_handle(matrix_path+"temporal_movie_topic");
		data_storage.store_matrix2(oswMovieTopic2, pmp.keywords_given_context_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_matrix2(oswMovieTemporal, pmp.topics_given_timeslice);
		data_storage.store_array2(oswUserLambda, pmp.lambda_temporal);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswMovieTopic2 = data_storage.file_handle(matrix_path+"temporal_movie_topic");
			data_storage.store_matrix(oswMovieTopic2, pmp.keywords_given_context_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.topics_given_timeslice);	
			data_storage.store_array(oswUserLambda, pmp.lambda_temporal);
		}
		
		
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	
	
	public static void storeTopics(Topic_Varying_model_Background pmp,
									matrix_and_map_2 m, int round,
									boolean is_store_for_viewing,Path PathDef){
		int folder = (round+1)*10;
		String matrix_path = PathDef.MATRIX_PATH+folder+"/";
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		OutputStreamWriter oswBackgroundLambda = data_storage.file_handle(matrix_path+"background_lambda");
		
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswMovieTopic2 = data_storage.file_handle(matrix_path+"temporal_movie_topic");
		data_storage.store_matrix2(oswMovieTopic2, pmp.keywords_given_context_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_matrix2(oswMovieTemporal, pmp.topics_given_timeslice);
		data_storage.store_array2(oswUserLambda, pmp.lambda_temporal);
		
		OutputStreamWriter oswMovieBackground = data_storage.file_handle(matrix_path+"keywords_given_background");
		data_storage.store_array2(oswMovieBackground, pmp.keywords_given_background);
		
		data_storage.store_array2(oswBackgroundLambda, pmp.lambda_background);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswMovieTopic2 = data_storage.file_handle(matrix_path+"temporal_movie_topic");
			data_storage.store_matrix(oswMovieTopic2, pmp.keywords_given_context_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.topics_given_timeslice);	
			data_storage.store_array(oswUserLambda, pmp.lambda_temporal);
			
			OutputStreamWriter oswMovieBackground = data_storage.file_handle(matrix_path+"keywords_given_background");
			data_storage.store_array(oswMovieBackground, pmp.keywords_given_background);
			
			data_storage.store_array(oswBackgroundLambda, pmp.lambda_background);
		}
		
		
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	
	
	public static void storeTopics(Topic_Varying_model_Background pmp,
									matrix_and_map_2 m,
									boolean is_store_for_viewing,Path PathDef){
		
		String matrix_path = PathDef.MATRIX_PATH;
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		OutputStreamWriter oswBackgroundLambda = data_storage.file_handle(matrix_path+"background_lambda");
		
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswMovieTopic2 = data_storage.file_handle(matrix_path+"temporal_movie_topic");
		data_storage.store_matrix2(oswMovieTopic2, pmp.keywords_given_context_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_matrix2(oswMovieTemporal, pmp.topics_given_timeslice);
		data_storage.store_array2(oswUserLambda, pmp.lambda_temporal);
		
		OutputStreamWriter oswMovieBackground = data_storage.file_handle(matrix_path+"keywords_given_background");
		data_storage.store_array2(oswMovieBackground, pmp.keywords_given_background);
		
		data_storage.store_array2(oswBackgroundLambda, pmp.lambda_background);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswMovieTopic2 = data_storage.file_handle(matrix_path+"temporal_movie_topic");
			data_storage.store_matrix(oswMovieTopic2, pmp.keywords_given_context_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.topics_given_timeslice);	
			data_storage.store_array(oswUserLambda, pmp.lambda_temporal);
			
			OutputStreamWriter oswMovieBackground = data_storage.file_handle(matrix_path+"keywords_given_background");
			data_storage.store_array(oswMovieBackground, pmp.keywords_given_background);
			
			data_storage.store_array(oswBackgroundLambda, pmp.lambda_background);
		}
		
		
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	
	
	public static void storeTopics(Topic_Varying_model_Sharing_TemporalParameter  pmp,matrix_and_map_2 m,int round,boolean is_store_for_viewing,Path PathDef){
		int folder = (round+1)*10;
		String matrix_path = PathDef.MATRIX_PATH+folder+"/";
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswMovieTopic2 = data_storage.file_handle(matrix_path+"temporal_movie_topic");
		data_storage.store_matrix2(oswMovieTopic2, pmp.keywords_given_context_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_matrix2(oswMovieTemporal, pmp.topics_given_timeslice);
		data_storage.store_array2(oswUserLambda, pmp.temporal_parameter);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswMovieTopic2 = data_storage.file_handle(matrix_path+"temporal_movie_topic");
			data_storage.store_matrix(oswMovieTopic2, pmp.keywords_given_context_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.topics_given_timeslice);	
			data_storage.store_array(oswUserLambda, pmp.temporal_parameter);
		}
		
		
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	
	public static void storeTopics(Topic_Varying_model pmp,matrix_and_map_2 m,boolean is_store_for_viewing,Path PathDef){
		String matrix_path = PathDef.MATRIX_PATH;
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswMovieTopic2 = data_storage.file_handle(matrix_path+"temporal_movie_topic");
		data_storage.store_matrix2(oswMovieTopic2, pmp.keywords_given_context_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_matrix2(oswMovieTemporal, pmp.topics_given_timeslice);
		
		
		data_storage.store_array2(oswUserLambda, pmp.lambda_temporal);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswMovieTopic2 = data_storage.file_handle(matrix_path+"temporal_movie_topic");
			data_storage.store_matrix(oswMovieTopic2, pmp.keywords_given_context_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.topics_given_timeslice);
			
			
			data_storage.store_array(oswUserLambda, pmp.lambda_temporal);
			}
		
		
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
		
	}
	
	
	public static void storeTopics(Topic_Varying_model_Sharing_TemporalParameter pmp,matrix_and_map_2 m,boolean is_store_for_viewing,Path PathDef){
		String matrix_path = PathDef.MATRIX_PATH;
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswMovieTopic2 = data_storage.file_handle(matrix_path+"temporal_movie_topic");
		data_storage.store_matrix2(oswMovieTopic2, pmp.keywords_given_context_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_matrix2(oswMovieTemporal, pmp.topics_given_timeslice);
		
		
		data_storage.store_array2(oswUserLambda, pmp.temporal_parameter);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswMovieTopic2 = data_storage.file_handle(matrix_path+"temporal_movie_topic");
			data_storage.store_matrix(oswMovieTopic2, pmp.keywords_given_context_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.topics_given_timeslice);
			
			
			data_storage.store_array(oswUserLambda, pmp.temporal_parameter);
			}
		
		
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
		
	}
	
	public static void storeTopics(Topic_Sharing_model pmp,matrix_and_map_2 m,boolean is_store_for_viewing,Path PathDef){
		String matrix_path = PathDef.MATRIX_PATH;
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_matrix2(oswMovieTemporal, pmp.topics_given_timeslice);
		
		data_storage.store_array2(oswUserLambda, pmp.lambda_temporal);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.topics_given_timeslice);
			
			data_storage.store_array(oswUserLambda, pmp.lambda_temporal);
		}
		
		
		
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	
	
	public static void storeTopics(Topic_Sharing_model_Background pmp, matrix_and_map_2 m,
									boolean is_store_for_viewing, Path PathDef){
		String matrix_path = PathDef.MATRIX_PATH;
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		OutputStreamWriter oswBackgroundLambda = data_storage.file_handle(matrix_path+"background_lambda");

		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_matrix2(oswMovieTemporal, pmp.topics_given_timeslice);
		
		data_storage.store_array2(oswUserLambda, pmp.lambda_temporal);
		
		OutputStreamWriter oswMovieBackground = data_storage.file_handle(matrix_path+"keywords_given_background");
		data_storage.store_array2(oswMovieBackground, pmp.keywords_given_background);
		
		data_storage.store_array2(oswBackgroundLambda, pmp.lambda_background);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.topics_given_timeslice);
			
			data_storage.store_array(oswUserLambda, pmp.lambda_temporal);
			
			OutputStreamWriter oswMovieBackground = data_storage.file_handle(matrix_path+"keywords_given_background");
			data_storage.store_array(oswMovieBackground, pmp.keywords_given_background);
			
			data_storage.store_array(oswBackgroundLambda, pmp.lambda_background);
		}
		
		
		
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	
	
	public static void storeTopics(Topic_Sharing_model_Sharing_TemporalParameter pmp,matrix_and_map_2 m,boolean is_store_for_viewing,Path PathDef){
		String matrix_path = PathDef.MATRIX_PATH;
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_matrix2(oswMovieTemporal, pmp.topics_given_timeslice);
		
		data_storage.store_array2(oswUserLambda, pmp.temporal_parameter);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.topics_given_timeslice);
			
			data_storage.store_array(oswUserLambda, pmp.temporal_parameter);
		}
		
		
		
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	
	public static void storeTopics(Item_based_Fashion_Context pmp,matrix_and_map_2 m,boolean is_store_for_viewing,Path PathDef){
		String matrix_path = PathDef.MATRIX_PATH;
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_matrix2(oswMovieTemporal, pmp.keywords_given_temporal);
		
		data_storage.store_array2(oswUserLambda, pmp.lambda_temporal);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.keywords_given_temporal);
			
			data_storage.store_array(oswUserLambda, pmp.lambda_temporal);
		}
	
		
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	
	public static void storeTopics(Item_based_Fashion_Sharing_Temporalparameter pmp,matrix_and_map_2 m,boolean is_store_for_viewing,Path PathDef){
		String matrix_path = PathDef.MATRIX_PATH;
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_matrix2(oswMovieTemporal, pmp.keywords_given_temporal);
		
		data_storage.store_array2(oswUserLambda, pmp.temporal_parameter);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.keywords_given_temporal);
			
			data_storage.store_array(oswUserLambda, pmp.temporal_parameter);
		}
	
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	
	public static void storeTopics(Item_based_Fashion_with_globalcontext_Sharing_Temporalparameter pmp,matrix_and_map_2 m,boolean is_store_for_viewing,Path PathDef){
		String matrix_path = PathDef.MATRIX_PATH;
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_matrix2(oswMovieTemporal, pmp.keywords_given_temporal);
		
		data_storage.store_array2(oswUserLambda, pmp.temporal_parameter);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.keywords_given_temporal);
			
			data_storage.store_array(oswUserLambda, pmp.temporal_parameter);
		}
	
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	public static void storeTopics(PLSA pmp,matrix_and_map_2 m,boolean is_store_for_viewing,Path PathDef){
		String matrix_path = PathDef.MATRIX_PATH;
		OutputStreamWriter oswbackground = data_storage.file_handle(matrix_path+"movie_background_smoothing");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		data_storage.store_array2(oswbackground, pmp.back_ground);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			data_storage.store_array(oswbackground, pmp.back_ground);
		}
	
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}

	
	public static void storeTopics(TemporalPLSA pmp,matrix_and_map_2 m,int round,boolean is_store_for_viewing,Path PathDef){
		int folder = (round+1)*10;
		String matrix_path = PathDef.MATRIX_PATH+folder+"/";
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_array2(oswUserLambda, pmp.temporal_parameter);
		data_storage.store_matrix2(oswMovieTemporal, pmp.topics_given_timeslice);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.topics_given_timeslice);
			data_storage.store_array(oswUserLambda, pmp.temporal_parameter);
		}
		
	
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	
	
	
	
	public static int[] map2reversemap(Hashtable<Integer,Integer>ht){
		int size =ht.size();
		int reversemap[] = new int[size];
		Enumeration<Integer> e =ht.keys();
		while(e.hasMoreElements()){
			int k = e.nextElement();
			int v = ht.get(k);
			reversemap[v] = k;
		}
		return reversemap;
	}
	public static void loadTopics(Item_based_Fashion_Context pmp,matrix_and_map_2 m,Path PathDef)
	{
	
		String matrix_path = PathDef.MATRIX_PATH;
		BufferedReader brMovieTopic = data_storage.file_handle_read(matrix_path+"movie_topic");
		pmp.keywords_given_topics = data_storage.load_matrix(brMovieTopic);
		BufferedReader brTopicUser = data_storage.file_handle_read(matrix_path+"topic_user");
		pmp.topics_given_documents = data_storage.load_matrix(brTopicUser);
		BufferedReader brMovieTemporal = data_storage.file_handle_read(matrix_path+"topic_temporal");
		pmp.keywords_given_temporal = data_storage.load_matrix(brMovieTemporal);
		BufferedReader brUserLambda = data_storage.file_handle_read(matrix_path+"user_temporal_lambda");
		pmp.lambda_temporal = data_storage.load_array(brUserLambda);
		
		//BufferedReader brUserLabel = data_storage.file_handle_read(matrix_path+"user_rating");
		//pmp.count_keywords_documents = data_storage.load_matrix_int(brUserLabel);
		
		BufferedReader brMovieMap = data_storage.file_handle_read(matrix_path+"movie_map");
		m.map_id1 = data_storage.load_map(brMovieMap);
		BufferedReader brUserMap = data_storage.file_handle_read(matrix_path+"user_map");
		m.map_id2 = data_storage.load_map(brUserMap);
		pmp.number_of_keywords = pmp.keywords_given_topics.length;
		pmp.number_of_topics = pmp.keywords_given_topics[0].length;
		pmp.number_of_documents = pmp.topics_given_documents[0].length;
		pmp.number_of_timeslices = pmp.keywords_given_temporal[0].length;
		m.reverse_map1 = map2reversemap(m.map_id1);
		m.reverse_map2 = map2reversemap(m.map_id2);
	}
	
	
	public static void loadTopics(Item_based_Fashion_with_globalcontext pmp,matrix_and_map_2 m,Path PathDef){
		String matrix_path = PathDef.MATRIX_PATH;
		BufferedReader brMovieTopic = data_storage.file_handle_read(matrix_path+"movie_topic");
		pmp.keywords_given_topics = data_storage.load_matrix(brMovieTopic);
		BufferedReader brTopicUser = data_storage.file_handle_read(matrix_path+"topic_user");
		pmp.topics_given_documents = data_storage.load_matrix(brTopicUser);
		BufferedReader brMovieTemporal = data_storage.file_handle_read(matrix_path+"topic_temporal");
		pmp.keywords_given_temporal = data_storage.load_matrix(brMovieTemporal);
		BufferedReader brUserLambda = data_storage.file_handle_read(matrix_path+"user_temporal_lambda");
		pmp.lambda_temporal = data_storage.load_array(brUserLambda);
		
		//BufferedReader brUserLabel = data_storage.file_handle_read(matrix_path+"user_rating");
		//pmp.count_keywords_documents = data_storage.load_matrix_int(brUserLabel);
		
		BufferedReader brMovieMap = data_storage.file_handle_read(matrix_path+"movie_map");
		m.map_id1 = data_storage.load_map(brMovieMap);
		BufferedReader brUserMap = data_storage.file_handle_read(matrix_path+"user_map");
		m.map_id2 = data_storage.load_map(brUserMap);
		
		pmp.number_of_keywords = pmp.keywords_given_topics.length;
		pmp.number_of_topics = pmp.keywords_given_topics[0].length;
		pmp.number_of_documents = pmp.topics_given_documents[0].length;
		pmp.number_of_timeslices = pmp.keywords_given_temporal[0].length;
		m.reverse_map1 = map2reversemap(m.map_id1);
		m.reverse_map2 = map2reversemap(m.map_id2);
	}
	
	public static void loadTopics(Item_based_Fashion_with_globalcontext_Sharing_Temporalparameter pmp,matrix_and_map_2 m,Path PathDef){
		String matrix_path = PathDef.MATRIX_PATH;
		BufferedReader brMovieTopic = data_storage.file_handle_read(matrix_path+"movie_topic");
		pmp.keywords_given_topics = data_storage.load_matrix(brMovieTopic);
		BufferedReader brTopicUser = data_storage.file_handle_read(matrix_path+"topic_user");
		pmp.topics_given_documents = data_storage.load_matrix(brTopicUser);
		BufferedReader brMovieTemporal = data_storage.file_handle_read(matrix_path+"topic_temporal");
		pmp.keywords_given_temporal = data_storage.load_matrix(brMovieTemporal);
		
		BufferedReader brUserLambda = data_storage.file_handle_read(matrix_path+"user_temporal_lambda");
		pmp.temporal_parameter = data_storage.load_single_parameter(brUserLambda);
		
		//BufferedReader brUserLabel = data_storage.file_handle_read(matrix_path+"user_rating");
		//pmp.count_keywords_documents = data_storage.load_matrix_int(brUserLabel);
		
		BufferedReader brMovieMap = data_storage.file_handle_read(matrix_path+"movie_map");
		m.map_id1 = data_storage.load_map(brMovieMap);
		BufferedReader brUserMap = data_storage.file_handle_read(matrix_path+"user_map");
		m.map_id2 = data_storage.load_map(brUserMap);
		
		pmp.number_of_keywords = pmp.keywords_given_topics.length;
		pmp.number_of_topics = pmp.keywords_given_topics[0].length;
		pmp.number_of_documents = pmp.topics_given_documents[0].length;
		pmp.number_of_timeslices = pmp.keywords_given_temporal[0].length;
		m.reverse_map1 = map2reversemap(m.map_id1);
		m.reverse_map2 = map2reversemap(m.map_id2);
	}
	
	public static void loadTopics(Topic_Varying_model pmp,matrix_and_map_2 m,Path PathDef){
		String matrix_path = PathDef.MATRIX_PATH;
		BufferedReader brMovieTopic = data_storage.file_handle_read(matrix_path+"movie_topic");
		pmp.keywords_given_topics = data_storage.load_matrix(brMovieTopic);
		
		
		BufferedReader tMovieTopic = data_storage.file_handle_read(matrix_path+"temporal_movie_topic");
		pmp.keywords_given_context_topics=data_storage.load_matrix(tMovieTopic);
		BufferedReader brTopicUser = data_storage.file_handle_read(matrix_path+"topic_user");
		pmp.topics_given_documents = data_storage.load_matrix(brTopicUser);
		
		BufferedReader brMovieTemporal = data_storage.file_handle_read(matrix_path+"topic_temporal");
		pmp.topics_given_timeslice=data_storage.load_matrix(brMovieTemporal);
		BufferedReader brUserLambda = data_storage.file_handle_read(matrix_path+"user_temporal_lambda");
		pmp.lambda_temporal = data_storage.load_array(brUserLambda);

		
		BufferedReader brMovieMap = data_storage.file_handle_read(matrix_path+"movie_map");
		m.map_id1 = data_storage.load_map(brMovieMap);
		BufferedReader brUserMap = data_storage.file_handle_read(matrix_path+"user_map");
		m.map_id2 = data_storage.load_map(brUserMap);
		
		pmp.number_of_keywords = pmp.keywords_given_topics.length;
		pmp.number_of_topics = pmp.keywords_given_topics[0].length;
		pmp.number_of_documents = pmp.topics_given_documents[0].length;
		pmp.number_of_timeslices = pmp.topics_given_timeslice[0].length;
		pmp.context_number_of_topics=pmp.topics_given_timeslice.length;
		
		
		
		m.reverse_map1 = map2reversemap(m.map_id1);
		m.reverse_map2 = map2reversemap(m.map_id2);
	}
	
	
	
	// same as Topic_Varying_model version
	public static void loadTopics(Topic_Varying_model_Cluster pmp,matrix_and_map_2 m,Path PathDef){
		String matrix_path = PathDef.MATRIX_PATH;
		BufferedReader brMovieTopic = data_storage.file_handle_read(matrix_path+"movie_topic");
		pmp.keywords_given_topics = data_storage.load_matrix(brMovieTopic);
		
		
		BufferedReader tMovieTopic = data_storage.file_handle_read(matrix_path+"temporal_movie_topic");
		pmp.keywords_given_context_topics=data_storage.load_matrix(tMovieTopic);
		BufferedReader brTopicUser = data_storage.file_handle_read(matrix_path+"topic_user");
		pmp.topics_given_documents = data_storage.load_matrix(brTopicUser);
		
		BufferedReader brMovieTemporal = data_storage.file_handle_read(matrix_path+"topic_temporal");
		pmp.topics_given_timeslice=data_storage.load_matrix(brMovieTemporal);
		BufferedReader brUserLambda = data_storage.file_handle_read(matrix_path+"user_temporal_lambda");
		pmp.lambda_temporal = data_storage.load_array(brUserLambda);

		
		BufferedReader brMovieMap = data_storage.file_handle_read(matrix_path+"movie_map");
		m.map_id1 = data_storage.load_map(brMovieMap);
		BufferedReader brUserMap = data_storage.file_handle_read(matrix_path+"user_map");
		m.map_id2 = data_storage.load_map(brUserMap);
		
		pmp.number_of_keywords = pmp.keywords_given_topics.length;
		pmp.number_of_topics = pmp.keywords_given_topics[0].length;
		pmp.number_of_documents = pmp.topics_given_documents[0].length;
		pmp.number_of_timeslices = pmp.topics_given_timeslice[0].length;
		pmp.context_number_of_topics=pmp.topics_given_timeslice.length;
		
		
		
		m.reverse_map1 = map2reversemap(m.map_id1);
		m.reverse_map2 = map2reversemap(m.map_id2);
	}
	
	
	
	
	public static void loadTopics(Topic_Varying_model_Background pmp, matrix_and_map_2 m,Path PathDef){
		String matrix_path = PathDef.MATRIX_PATH;
		BufferedReader brMovieTopic = data_storage.file_handle_read(matrix_path+"movie_topic");
		pmp.keywords_given_topics = data_storage.load_matrix(brMovieTopic);
		
		
		BufferedReader tMovieTopic = data_storage.file_handle_read(matrix_path+"temporal_movie_topic");
		pmp.keywords_given_context_topics=data_storage.load_matrix(tMovieTopic);
		BufferedReader brTopicUser = data_storage.file_handle_read(matrix_path+"topic_user");
		pmp.topics_given_documents = data_storage.load_matrix(brTopicUser);
		
		BufferedReader brMovieTemporal = data_storage.file_handle_read(matrix_path+"topic_temporal");
		pmp.topics_given_timeslice=data_storage.load_matrix(brMovieTemporal);
		BufferedReader brUserLambda = data_storage.file_handle_read(matrix_path+"user_temporal_lambda");
		pmp.lambda_temporal = data_storage.load_array(brUserLambda);

		
		// keyword given background
		BufferedReader brMovieBackground = data_storage.file_handle_read(matrix_path+"keywords_given_background");
		pmp.keywords_given_background = data_storage.load_array(brMovieBackground);
		// lambda_temporal
		BufferedReader brLambdaBackground = data_storage.file_handle_read(matrix_path+"background_lambda");
		pmp.lambda_background = data_storage.load_single_parameter(brLambdaBackground);
		
		BufferedReader brMovieMap = data_storage.file_handle_read(matrix_path+"movie_map");
		m.map_id1 = data_storage.load_map(brMovieMap);
		BufferedReader brUserMap = data_storage.file_handle_read(matrix_path+"user_map");
		m.map_id2 = data_storage.load_map(brUserMap);
		
		pmp.number_of_keywords = pmp.keywords_given_topics.length;
		pmp.number_of_topics = pmp.keywords_given_topics[0].length;
		pmp.number_of_documents = pmp.topics_given_documents[0].length;
		pmp.number_of_timeslices = pmp.topics_given_timeslice[0].length;
		pmp.context_number_of_topics=pmp.topics_given_timeslice.length;
		
		
		
		m.reverse_map1 = map2reversemap(m.map_id1);
		m.reverse_map2 = map2reversemap(m.map_id2);
	}
	
	
	
	public static void loadTopics(Topic_Varying_model_Cluster_Background pmp, matrix_and_map_2 m,Path PathDef){
		String matrix_path = PathDef.MATRIX_PATH;
		
		BufferedReader brMovieTopic = data_storage.file_handle_read(matrix_path+"movie_topic");
		pmp.keywords_given_topics = data_storage.load_matrix(brMovieTopic);
		
		
		BufferedReader tMovieTopic = data_storage.file_handle_read(matrix_path+"temporal_movie_topic");
		pmp.keywords_given_context_topics=data_storage.load_matrix(tMovieTopic);
		BufferedReader brTopicUser = data_storage.file_handle_read(matrix_path+"topic_user");
		pmp.topics_given_documents = data_storage.load_matrix(brTopicUser);
		
		BufferedReader brMovieTemporal = data_storage.file_handle_read(matrix_path+"topic_temporal");
		pmp.topics_given_timeslice=data_storage.load_matrix(brMovieTemporal);
		BufferedReader brUserLambda = data_storage.file_handle_read(matrix_path+"user_temporal_lambda");
		pmp.lambda_temporal = data_storage.load_array(brUserLambda);

		// keyword given background
		BufferedReader brMovieBackground = data_storage.file_handle_read(matrix_path+"keywords_given_background");
		pmp.keywords_given_background = data_storage.load_array(brMovieBackground);
		// lambda_temporal
		BufferedReader brLambdaBackground = data_storage.file_handle_read(matrix_path+"background_lambda");
		pmp.lambda_background = data_storage.load_single_parameter(brLambdaBackground);
		
		BufferedReader brMovieMap = data_storage.file_handle_read(matrix_path+"movie_map");
		m.map_id1 = data_storage.load_map(brMovieMap);
		BufferedReader brUserMap = data_storage.file_handle_read(matrix_path+"user_map");
		m.map_id2 = data_storage.load_map(brUserMap);

		
		
		pmp.number_of_keywords = pmp.keywords_given_topics.length;
		pmp.number_of_topics = pmp.keywords_given_topics[0].length;
		pmp.number_of_documents = pmp.topics_given_documents[0].length;
		pmp.number_of_timeslices = pmp.topics_given_timeslice[0].length;
		pmp.context_number_of_topics=pmp.topics_given_timeslice.length;
		
		
		
		m.reverse_map1 = map2reversemap(m.map_id1);
		m.reverse_map2 = map2reversemap(m.map_id2);
	}
	
	
	
	public static void loadTopics(Topic_Varying_model_Sharing_TemporalParameter pmp,matrix_and_map_2 m,Path PathDef){
		String matrix_path = PathDef.MATRIX_PATH;
		BufferedReader brMovieTopic = data_storage.file_handle_read(matrix_path+"movie_topic");
		pmp.keywords_given_topics = data_storage.load_matrix(brMovieTopic);
		
		
		BufferedReader tMovieTopic = data_storage.file_handle_read(matrix_path+"temporal_movie_topic");
		pmp.keywords_given_context_topics=data_storage.load_matrix(tMovieTopic);
		BufferedReader brTopicUser = data_storage.file_handle_read(matrix_path+"topic_user");
		pmp.topics_given_documents = data_storage.load_matrix(brTopicUser);
		
		BufferedReader brMovieTemporal = data_storage.file_handle_read(matrix_path+"topic_temporal");
		pmp.topics_given_timeslice=data_storage.load_matrix(brMovieTemporal);
		
		
		BufferedReader brUserLambda = data_storage.file_handle_read(matrix_path+"user_temporal_lambda");
		pmp.temporal_parameter = data_storage.load_single_parameter(brUserLambda);

		
		BufferedReader brMovieMap = data_storage.file_handle_read(matrix_path+"movie_map");
		m.map_id1 = data_storage.load_map(brMovieMap);
		BufferedReader brUserMap = data_storage.file_handle_read(matrix_path+"user_map");
		m.map_id2 = data_storage.load_map(brUserMap);
		
		pmp.number_of_keywords = pmp.keywords_given_topics.length;
		pmp.number_of_topics = pmp.keywords_given_topics[0].length;
		pmp.number_of_documents = pmp.topics_given_documents[0].length;
		pmp.number_of_timeslices = pmp.topics_given_timeslice[0].length;
		pmp.context_number_of_topics=pmp.topics_given_timeslice.length;
		
		
		
		m.reverse_map1 = map2reversemap(m.map_id1);
		m.reverse_map2 = map2reversemap(m.map_id2);
	}
	
	
	public static void loadTopics(Topic_Sharing_model pmp,matrix_and_map_2 m,Path PathDef){
		String matrix_path = PathDef.MATRIX_PATH;
		BufferedReader brMovieTopic = data_storage.file_handle_read(matrix_path+"movie_topic");
		pmp.keywords_given_topics = data_storage.load_matrix(brMovieTopic);
		BufferedReader brTopicUser = data_storage.file_handle_read(matrix_path+"topic_user");
		pmp.topics_given_documents = data_storage.load_matrix(brTopicUser);
		
		BufferedReader brMovieTemporal = data_storage.file_handle_read(matrix_path+"topic_temporal");
		pmp.topics_given_timeslice=data_storage.load_matrix(brMovieTemporal);
		BufferedReader brUserLambda = data_storage.file_handle_read(matrix_path+"user_temporal_lambda");
		pmp.lambda_temporal = data_storage.load_array(brUserLambda);
	
		
		BufferedReader brMovieMap = data_storage.file_handle_read(matrix_path+"movie_map");
		m.map_id1 = data_storage.load_map(brMovieMap);
		BufferedReader brUserMap = data_storage.file_handle_read(matrix_path+"user_map");
		m.map_id2 = data_storage.load_map(brUserMap);
		pmp.number_of_keywords = pmp.keywords_given_topics.length;
		pmp.number_of_topics = pmp.keywords_given_topics[0].length;
		pmp.number_of_documents = pmp.topics_given_documents[0].length;
		pmp.number_of_timeslices = pmp.topics_given_timeslice[0].length;
			
		m.reverse_map1 = map2reversemap(m.map_id1);
		m.reverse_map2 = map2reversemap(m.map_id2);
	}
	
	
	
	public static void loadTopics(Topic_Sharing_model_Background pmp,matrix_and_map_2 m,Path PathDef){
		String matrix_path = PathDef.MATRIX_PATH;
		BufferedReader brMovieTopic = data_storage.file_handle_read(matrix_path+"movie_topic");
		pmp.keywords_given_topics = data_storage.load_matrix(brMovieTopic);
		BufferedReader brTopicUser = data_storage.file_handle_read(matrix_path+"topic_user");
		pmp.topics_given_documents = data_storage.load_matrix(brTopicUser);
		
		BufferedReader brMovieTemporal = data_storage.file_handle_read(matrix_path+"topic_temporal");
		pmp.topics_given_timeslice=data_storage.load_matrix(brMovieTemporal);
		BufferedReader brUserLambda = data_storage.file_handle_read(matrix_path+"user_temporal_lambda");
		pmp.lambda_temporal = data_storage.load_array(brUserLambda);
	
		
		// keyword given background
		BufferedReader brMovieBackground = data_storage.file_handle_read(matrix_path+"keywords_given_background");
		pmp.keywords_given_background = data_storage.load_array(brMovieBackground);
		// lambda_temporal
		BufferedReader brLambdaBackground = data_storage.file_handle_read(matrix_path+"background_lambda");
		pmp.lambda_background = data_storage.load_single_parameter(brLambdaBackground);
		
		
		BufferedReader brMovieMap = data_storage.file_handle_read(matrix_path+"movie_map");
		m.map_id1 = data_storage.load_map(brMovieMap);
		BufferedReader brUserMap = data_storage.file_handle_read(matrix_path+"user_map");
		m.map_id2 = data_storage.load_map(brUserMap);
		
		
		pmp.number_of_keywords = pmp.keywords_given_topics.length;
		pmp.number_of_topics = pmp.keywords_given_topics[0].length;
		pmp.number_of_documents = pmp.topics_given_documents[0].length;
		pmp.number_of_timeslices = pmp.topics_given_timeslice[0].length;
			
		m.reverse_map1 = map2reversemap(m.map_id1);
		m.reverse_map2 = map2reversemap(m.map_id2);
	}
	
	
	// same as Topic_Sharying_model version
	public static void loadTopics(Topic_Sharing_model_Cluster pmp, matrix_and_map_2 m, Path PathDef){
		String matrix_path = PathDef.MATRIX_PATH;
		BufferedReader brMovieTopic = data_storage.file_handle_read(matrix_path+"movie_topic");
		pmp.keywords_given_topics = data_storage.load_matrix(brMovieTopic);
		BufferedReader brTopicUser = data_storage.file_handle_read(matrix_path+"topic_user");
		pmp.topics_given_documents = data_storage.load_matrix(brTopicUser);
		
		BufferedReader brMovieTemporal = data_storage.file_handle_read(matrix_path+"topic_temporal");
		pmp.topics_given_timeslice=data_storage.load_matrix(brMovieTemporal);
		BufferedReader brUserLambda = data_storage.file_handle_read(matrix_path+"user_temporal_lambda");
		pmp.lambda_temporal = data_storage.load_array(brUserLambda);
	
		
		BufferedReader brMovieMap = data_storage.file_handle_read(matrix_path+"movie_map");
		m.map_id1 = data_storage.load_map(brMovieMap);
		BufferedReader brUserMap = data_storage.file_handle_read(matrix_path+"user_map");
		m.map_id2 = data_storage.load_map(brUserMap);
		pmp.number_of_keywords = pmp.keywords_given_topics.length;
		pmp.number_of_topics = pmp.keywords_given_topics[0].length;
		pmp.number_of_documents = pmp.topics_given_documents[0].length;
		pmp.number_of_timeslices = pmp.topics_given_timeslice[0].length;
			
		m.reverse_map1 = map2reversemap(m.map_id1);
		m.reverse_map2 = map2reversemap(m.map_id2);
	}
	
	
	
	public static void loadTopics(Topic_Sharing_model_Sharing_TemporalParameter pmp, matrix_and_map_2 m, Path PathDef){
		String matrix_path = PathDef.MATRIX_PATH;
		BufferedReader brMovieTopic = data_storage.file_handle_read(matrix_path+"movie_topic");
		pmp.keywords_given_topics = data_storage.load_matrix(brMovieTopic);
		BufferedReader brTopicUser = data_storage.file_handle_read(matrix_path+"topic_user");
		pmp.topics_given_documents = data_storage.load_matrix(brTopicUser);
		
		BufferedReader brMovieTemporal = data_storage.file_handle_read(matrix_path+"topic_temporal");
		pmp.topics_given_timeslice=data_storage.load_matrix(brMovieTemporal);
		BufferedReader brUserLambda = data_storage.file_handle_read(matrix_path+"user_temporal_lambda");
		pmp.temporal_parameter = data_storage.load_single_parameter(brUserLambda);
	
		
		BufferedReader brMovieMap = data_storage.file_handle_read(matrix_path+"movie_map");
		m.map_id1 = data_storage.load_map(brMovieMap);
		BufferedReader brUserMap = data_storage.file_handle_read(matrix_path+"user_map");
		m.map_id2 = data_storage.load_map(brUserMap);
		pmp.number_of_keywords = pmp.keywords_given_topics.length;
		pmp.number_of_topics = pmp.keywords_given_topics[0].length;
		pmp.number_of_documents = pmp.topics_given_documents[0].length;
		pmp.number_of_timeslices = pmp.topics_given_timeslice[0].length;
			
		m.reverse_map1 = map2reversemap(m.map_id1);
		m.reverse_map2 = map2reversemap(m.map_id2);
	}
	
	
	
	public static void loadTopics(PLSA pmp,matrix_and_map_2 m,Path PathDef){
		String matrix_path = PathDef.MATRIX_PATH;
		BufferedReader brMovieTopic = data_storage.file_handle_read(matrix_path+"movie_topic");
		pmp.keywords_given_topics = data_storage.load_matrix(brMovieTopic);
		BufferedReader brTopicUser = data_storage.file_handle_read(matrix_path+"topic_user");
		pmp.topics_given_documents = data_storage.load_matrix(brTopicUser);
		
		BufferedReader brMovieMap = data_storage.file_handle_read(matrix_path+"movie_map");
		m.map_id1 = data_storage.load_map(brMovieMap);
		BufferedReader brUserMap = data_storage.file_handle_read(matrix_path+"user_map");
		BufferedReader background = data_storage.file_handle_read(matrix_path+"movie_background_smoothing");
		pmp.back_ground = data_storage.load_array(background);
		
		m.map_id2 = data_storage.load_map(brUserMap);
		pmp.number_of_keywords = pmp.keywords_given_topics.length;
		pmp.number_of_topics = pmp.keywords_given_topics[0].length;
		pmp.number_of_documents = pmp.topics_given_documents[0].length;
		//pmp.number_of_timeslices = pmp.keywords_given_temporal[0].length;
		m.reverse_map1 = map2reversemap(m.map_id1);
		m.reverse_map2 = map2reversemap(m.map_id2);
	}
	
	
	
	
	
	// for topic_varying_model_cluster 
	public static void storeTopics(Topic_Varying_model_Cluster pmp,matrix_and_map_2 m,
									int round, boolean is_store_for_viewing,Path PathDef){
		int folder = (round+1)*10;
		String matrix_path = PathDef.MATRIX_PATH+folder+"/";
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswMovieTopic2 = data_storage.file_handle(matrix_path+"temporal_movie_topic");
		data_storage.store_matrix2(oswMovieTopic2, pmp.keywords_given_context_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_matrix2(oswMovieTemporal, pmp.topics_given_timeslice);
		
		data_storage.store_array2(oswUserLambda, pmp.lambda_temporal);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswMovieTopic2 = data_storage.file_handle(matrix_path+"temporal_movie_topic");
			data_storage.store_matrix(oswMovieTopic2, pmp.keywords_given_context_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.topics_given_timeslice);
			
			data_storage.store_array(oswUserLambda, pmp.lambda_temporal);
		}
		
		
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	// for topic_varying_model_cluster 
	public static void storeTopics(Topic_Varying_model_Cluster pmp,matrix_and_map_2 m,
									boolean is_store_for_viewing,Path PathDef){
		
		String matrix_path = PathDef.MATRIX_PATH;
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswMovieTopic2 = data_storage.file_handle(matrix_path+"temporal_movie_topic");
		data_storage.store_matrix2(oswMovieTopic2, pmp.keywords_given_context_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_matrix2(oswMovieTemporal, pmp.topics_given_timeslice);
		
		data_storage.store_array2(oswUserLambda, pmp.lambda_temporal);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswMovieTopic2 = data_storage.file_handle(matrix_path+"temporal_movie_topic");
			data_storage.store_matrix(oswMovieTopic2, pmp.keywords_given_context_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.topics_given_timeslice);
			
			data_storage.store_array(oswUserLambda, pmp.lambda_temporal);
		}
		
		
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	
	
	// for topic_Sharing_model_cluster 
	public static void storeTopics(Topic_Sharing_model_Cluster pmp, matrix_and_map_2 m,
									int round,boolean is_store_for_viewing,Path PathDef){
		int folder = (round+1)*10;
		String matrix_path = PathDef.MATRIX_PATH+folder+"/";
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_array2(oswUserLambda, pmp.lambda_temporal);
		data_storage.store_matrix2(oswMovieTemporal, pmp.topics_given_timeslice);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.topics_given_timeslice);
			data_storage.store_array(oswUserLambda, pmp.lambda_temporal);
		}
		
	
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	// for topic_Sharing_model_cluster 
	public static void storeTopics(Topic_Sharing_model_Cluster pmp,matrix_and_map_2 m, 
									boolean is_store_for_viewing,Path PathDef){
		String matrix_path = PathDef.MATRIX_PATH;
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_matrix2(oswMovieTemporal, pmp.topics_given_timeslice);
		
		data_storage.store_array2(oswUserLambda, pmp.lambda_temporal);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.topics_given_timeslice);
			
			data_storage.store_array(oswUserLambda, pmp.lambda_temporal);
		}
		
		
		
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}


	
	
	// for topic_varying_model_cluster_background
	public static void storeTopics(Topic_Varying_model_Cluster_Background pmp,matrix_and_map_2 m,
									boolean is_store_for_viewing,Path PathDef){
		
		String matrix_path = PathDef.MATRIX_PATH;
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		OutputStreamWriter oswBackgroundLambda = data_storage.file_handle(matrix_path+"background_lambda");
		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswMovieTopic2 = data_storage.file_handle(matrix_path+"temporal_movie_topic");
		data_storage.store_matrix2(oswMovieTopic2, pmp.keywords_given_context_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_matrix2(oswMovieTemporal, pmp.topics_given_timeslice);
		
		OutputStreamWriter oswMovieBackground = data_storage.file_handle(matrix_path+"keywords_given_background");
		data_storage.store_array2(oswMovieBackground, pmp.keywords_given_background);
		
		data_storage.store_array2(oswBackgroundLambda, pmp.lambda_background);
		
		data_storage.store_array2(oswUserLambda, pmp.lambda_temporal);

		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswMovieTopic2 = data_storage.file_handle(matrix_path+"temporal_movie_topic");
			data_storage.store_matrix(oswMovieTopic2, pmp.keywords_given_context_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.topics_given_timeslice);
			
			OutputStreamWriter oswMovieBackground = data_storage.file_handle(matrix_path+"keywords_given_background");
			data_storage.store_array(oswMovieBackground, pmp.keywords_given_background);
			
			data_storage.store_array(oswBackgroundLambda, pmp.lambda_background);
			
			data_storage.store_array(oswUserLambda, pmp.lambda_temporal);
		}
		
		
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	
	// for topic_varying_model_cluster_background 
	public static void storeTopics(Topic_Varying_model_Cluster_Background pmp,matrix_and_map_2 m,
									int round, boolean is_store_for_viewing,Path PathDef){
		int folder = (round+1)*10;
		String matrix_path = PathDef.MATRIX_PATH+folder+"/";
		OutputStreamWriter oswUserLambda = data_storage.file_handle(matrix_path+"user_temporal_lambda");
		OutputStreamWriter oswBackgroundLambda = data_storage.file_handle(matrix_path+"background_lambda");

		if(is_store_for_viewing)
		{
		OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
		data_storage.store_matrix2(oswMovieTopic, pmp.keywords_given_topics);
		
		OutputStreamWriter oswMovieTopic2 = data_storage.file_handle(matrix_path+"temporal_movie_topic");
		data_storage.store_matrix2(oswMovieTopic2, pmp.keywords_given_context_topics);
		
		OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
		data_storage.store_matrix2(oswTopicUser, pmp.topics_given_documents);
		
		OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
		data_storage.store_matrix2(oswMovieTemporal, pmp.topics_given_timeslice);
		
		OutputStreamWriter oswMovieBackground = data_storage.file_handle(matrix_path+"keywords_given_background");
		data_storage.store_array2(oswMovieBackground, pmp.keywords_given_background);
		
		data_storage.store_array2(oswUserLambda, pmp.lambda_temporal);
		data_storage.store_array2(oswBackgroundLambda, pmp.lambda_background);
		}
		else
		{
			OutputStreamWriter oswMovieTopic = data_storage.file_handle(matrix_path+"movie_topic");
			data_storage.store_matrix(oswMovieTopic, pmp.keywords_given_topics);
			
			OutputStreamWriter oswMovieTopic2 = data_storage.file_handle(matrix_path+"temporal_movie_topic");
			data_storage.store_matrix(oswMovieTopic2, pmp.keywords_given_context_topics);
			
			OutputStreamWriter oswTopicUser = data_storage.file_handle(matrix_path+"topic_user");
			data_storage.store_matrix(oswTopicUser, pmp.topics_given_documents);
			
			OutputStreamWriter oswMovieTemporal = data_storage.file_handle(matrix_path+"topic_temporal");
			data_storage.store_matrix(oswMovieTemporal, pmp.topics_given_timeslice);
			
			OutputStreamWriter oswMovieBackground = data_storage.file_handle(matrix_path+"keywords_given_background");
			data_storage.store_array(oswMovieBackground, pmp.keywords_given_background);
			
			data_storage.store_array(oswUserLambda, pmp.lambda_temporal);
			data_storage.store_array(oswBackgroundLambda, pmp.lambda_background);
		}
		
		
		//OutputStreamWriter oswUserLabel = data_storage.file_handle(matrix_path+"user_rating");
		//data_storage.store_matrix(oswUserLabel, pmp.count_keywords_documents);
		OutputStreamWriter oswMovieMap = data_storage.file_handle(matrix_path+"movie_map");
		data_storage.store_map(oswMovieMap, m.reverse_map1, m.size1);
		
		OutputStreamWriter oswUserMap = data_storage.file_handle(matrix_path+"user_map");
		data_storage.store_map(oswUserMap, m.reverse_map2, m.size2);
	}
	
	
}
