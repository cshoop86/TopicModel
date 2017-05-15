package input_output_interface;

import java.io.OutputStreamWriter;
import java.util.*;

import cluster.UserCluster;

import ancillary.matrix_and_map_2;
import ancillary.sort_major;
import basic.*;
import parser.movieInfoParser.movieList;
//import parser.GetGluemovieInfoParser.movieList;
import model.BasicPLSA.*;
import model.Fashion.Itembased.Item_based_Fashion_Context;
import model.Fashion.TopicSharing.Topic_Sharing_model;
import model.Fashion.TopicSharing.Topic_Sharing_model_Cluster;
import model.Fashion.TopicVarying.Topic_Varying_model;
import model.Fashion.TopicVarying.Topic_Varying_model_Cluster;
import model.Fashion.TopicVarying.Topic_Varying_model_Cluster_Background;
import model.Social.model_topic_sharing_cluster_netplsa;
//import PLSA_model.PLSA_movie_parallel;
//import PLSA_model.Topic_based_context1;

public class model_output {
    public static void outputMovieInfo(movieList ml, int listId, OutputStreamWriter osw) {
        try {
            osw.write(ml.movieInfoList.get(listId).name + " " + ml.movieInfoList.get(listId).year + " ");
            ArrayList<Integer> types = ml.movieInfoList.get(listId).type;
            int size = types.size();
            for (int i = 0; i < size; i++) {
                if (i == 0)
                    osw.write(ml.typeList[types.get(i)].typename + "");
                else
                    osw.write("|" + ml.typeList[types.get(i)].typename);
            }
            osw.write(" ");
            osw.write("Starting timestamp:" + ml.movieInfoList.get(listId).firstratetime + " ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void outputTopic(matrix_and_map_2 m, Topic_Sharing_model pmp, movieList ml, Path PathDef) {
        try {
            OutputStreamWriter osw = data_storage.file_handle(PathDef.OUTPUT_PATH + "movieTopic/movie_topic");
            int numberOfMovies = pmp.number_of_keywords;
            int numberOfTopics = pmp.number_of_topics;
            sort_major sm = new sort_major();
            myarray[][] ma = new myarray[numberOfTopics][numberOfMovies];
            sm.sort_first_dimension(pmp.keywords_given_topics, m.reverse_map1, numberOfMovies, ma);
            final int SIZE_OF_OUTPUT = 20;
            for (int t = 0; t < numberOfTopics; t++) {
                osw.write("Topic " + t + ":\n");
                for (int i = 0; i < SIZE_OF_OUTPUT; i++) {
                    int movieId = ma[t][i].key;
                    double score = ma[t][i].value;
                    int listId = ml.movieIdMap.get(movieId);
                    outputMovieInfo(ml, listId, osw);
                    osw.write(score + "");
                    osw.write("\n");
                }
                osw.write("\n");
                osw.flush();
            }
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // for Topic Sharing model Cluster
    public static void outputTopic(matrix_and_map_2 m, Topic_Sharing_model_Cluster pmp,
                                   movieList ml, Path PathDef) {
        try {
            OutputStreamWriter osw = data_storage.file_handle(PathDef.OUTPUT_PATH + "movieTopic/movie_topic");
            int numberOfMovies = pmp.number_of_keywords;
            int numberOfTopics = pmp.number_of_topics;
            sort_major sm = new sort_major();
            myarray[][] ma = new myarray[numberOfTopics][numberOfMovies];
            sm.sort_first_dimension(pmp.keywords_given_topics, m.reverse_map1, numberOfMovies, ma);
            final int SIZE_OF_OUTPUT = 20;
            for (int t = 0; t < numberOfTopics; t++) {
                osw.write("Topic " + t + ":\n");
                for (int i = 0; i < SIZE_OF_OUTPUT; i++) {
                    int movieId = ma[t][i].key;
                    double score = ma[t][i].value;
                    int listId = ml.movieIdMap.get(movieId);
                    outputMovieInfo(ml, listId, osw);
                    osw.write(score + "");
                    osw.write("\n");
                }
                osw.write("\n");
                osw.flush();
            }
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void outputTopic2(matrix_and_map_2 m, Topic_Varying_model pmp, movieList ml, Path PathDef) {
        try {

            outputTopic(m, pmp, ml, PathDef);
            OutputStreamWriter osw = data_storage.file_handle(PathDef.OUTPUT_PATH + "movieTopic/fashion_movie_topic");
            int numberOfMovies = pmp.number_of_keywords;
            int numberOfTopics = pmp.context_number_of_topics;
            sort_major sm = new sort_major();
            myarray[][] ma = new myarray[numberOfTopics][numberOfMovies];
            sm.sort_first_dimension(pmp.keywords_given_context_topics, m.reverse_map1, numberOfMovies, ma);
            final int SIZE_OF_OUTPUT = 20;
            for (int t = 0; t < numberOfTopics; t++) {
                osw.write("Topic " + t + ":\n");
                for (int i = 0; i < SIZE_OF_OUTPUT; i++) {
                    int movieId = ma[t][i].key;
                    double score = ma[t][i].value;
                    int listId = ml.movieIdMap.get(movieId);
                    outputMovieInfo(ml, listId, osw);
                    osw.write(score + "");
                    osw.write("\n");
                }
                osw.write("\n");
                osw.flush();
            }
            osw.close();

            System.out.println("output fashion_movie_topic: the topic distribution over fashion context movies");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void outputTopic2(matrix_and_map_2 m, Topic_Varying_model_Cluster pmp, movieList ml, Path PathDef) {
        try {

            outputTopic(m, pmp, ml, PathDef);
            OutputStreamWriter osw = data_storage.file_handle(PathDef.OUTPUT_PATH + "movieTopic/fashion_movie_topic");
            int numberOfMovies = pmp.number_of_keywords;
            int numberOfTopics = pmp.context_number_of_topics;
            sort_major sm = new sort_major();
            myarray[][] ma = new myarray[numberOfTopics][numberOfMovies];
            sm.sort_first_dimension(pmp.keywords_given_context_topics, m.reverse_map1, numberOfMovies, ma);
            final int SIZE_OF_OUTPUT = 20;
            for (int t = 0; t < numberOfTopics; t++) {
                osw.write("Topic " + t + ":\n");
                for (int i = 0; i < SIZE_OF_OUTPUT; i++) {
                    int movieId = ma[t][i].key;
                    double score = ma[t][i].value;
                    int listId = ml.movieIdMap.get(movieId);
                    outputMovieInfo(ml, listId, osw);
                    osw.write(score + "");
                    osw.write("\n");
                }
                osw.write("\n");
                osw.flush();
            }
            osw.close();

            System.out.println("output fashion_movie_topic: the topic distribution over fashion context movies");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void outputTopic2(matrix_and_map_2 m, Topic_Varying_model_Cluster_Background pmp, movieList ml, Path PathDef) {
        try {

            outputTopic(m, pmp, ml, PathDef);
            OutputStreamWriter osw = data_storage.file_handle(PathDef.OUTPUT_PATH + "movieTopic/fashion_movie_topic");
            int numberOfMovies = pmp.number_of_keywords;
            int numberOfTopics = pmp.context_number_of_topics;
            sort_major sm = new sort_major();
            myarray[][] ma = new myarray[numberOfTopics][numberOfMovies];
            sm.sort_first_dimension(pmp.keywords_given_context_topics, m.reverse_map1, numberOfMovies, ma);
            final int SIZE_OF_OUTPUT = 20;
            for (int t = 0; t < numberOfTopics; t++) {
                osw.write("Topic " + t + ":\n");
                for (int i = 0; i < SIZE_OF_OUTPUT; i++) {
                    int movieId = ma[t][i].key;
                    double score = ma[t][i].value;
                    int listId = ml.movieIdMap.get(movieId);
                    outputMovieInfo(ml, listId, osw);
                    osw.write(score + "");
                    osw.write("\n");
                }
                osw.write("\n");
                osw.flush();
            }
            osw.close();

            System.out.println("output fashion_movie_topic: the topic distribution over fashion context movies");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void outputTopic(matrix_and_map_2 m, PLSA pmp, movieList ml, Path PathDef) {
        try {
            OutputStreamWriter osw = data_storage.file_handle(PathDef.OUTPUT_PATH + "movieTopic/movie_topic");
            int numberOfMovies = pmp.number_of_keywords;
            int numberOfTopics = pmp.number_of_topics;
            sort_major sm = new sort_major();
            myarray[][] ma = new myarray[numberOfTopics][numberOfMovies];
            sm.sort_first_dimension(pmp.keywords_given_topics, m.reverse_map1, numberOfMovies, ma);
            final int SIZE_OF_OUTPUT = 20;
            for (int t = 0; t < numberOfTopics; t++) {
                osw.write("Topic " + t + ":\n");
                for (int i = 0; i < SIZE_OF_OUTPUT; i++) {
                    int movieId = ma[t][i].key;
                    double score = ma[t][i].value;
                    int listId = ml.movieIdMap.get(movieId);
                    outputMovieInfo(ml, listId, osw);
                    osw.write(score + "");
                    osw.write("\n");
                }

                osw.write("\n");
                osw.flush();

            }

            osw.close();
            osw = data_storage.file_handle(PathDef.OUTPUT_PATH + "movieTopic/top_movies");
            HashMap<Integer, Double> movies = new HashMap<Integer, Double>();
            for (int i_k = 0; i_k < pmp.back_ground.length; i_k++) {
                movies.put(i_k, pmp.back_ground[i_k]);
            }
            //Arrays.sort(pmp.back_ground);

            List<Map.Entry<Integer, Double>> rankedmovies = RankingMap(movies);
            for (Map.Entry<Integer, Double> ent : rankedmovies) {
                //int listId = ml.movieIdMap.get(ent.getKey());
                outputMovieInfo(ml, ent.getKey(), osw);
                osw.write(" " + pmp.popularity[ent.getKey()] + " ");
                osw.write(" " + ent.getValue() + " ");
                osw.write("\n");

                //osw.flush();
            }

            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Map.Entry<Integer, Double>> RankingMap(HashMap<Integer, Double> moviepopularity) {
        List<Map.Entry<Integer, Double>> data = new ArrayList<Map.Entry<Integer, Double>>(moviepopularity.entrySet());
        Collections.sort(data, new Comparator<Map.Entry<Integer, Double>>() {
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                if (o2.getValue() != null && o1.getValue() != null && (o2.getValue() > o1.getValue())) {
                    return 1;
                } else {
                    return -1;
                }

            }
        });

        return data;

    }

    public static void outputTopic(matrix_and_map_2 m, Item_based_Fashion_Context pmp, movieList ml, Path PathDef) {
        try {
            OutputStreamWriter osw = data_storage.file_handle(PathDef.OUTPUT_PATH + "movieTopic/movie_topic");
            int numberOfMovies = pmp.number_of_keywords;
            int numberOfTopics = pmp.number_of_topics;
            sort_major sm = new sort_major();
            myarray[][] ma = new myarray[numberOfTopics][numberOfMovies];
            sm.sort_first_dimension(pmp.keywords_given_topics, m.reverse_map1, numberOfMovies, ma);
            final int SIZE_OF_OUTPUT = 20;
            for (int t = 0; t < numberOfTopics; t++) {
                osw.write("Topic " + t + ":\n");
                for (int i = 0; i < SIZE_OF_OUTPUT; i++) {
                    int movieId = ma[t][i].key;
                    double score = ma[t][i].value;
                    int listId = ml.movieIdMap.get(movieId);
                    outputMovieInfo(ml, listId, osw);
                    osw.write(score + "");
                    osw.write("\n");
                }
                osw.write("\n");
                osw.flush();
            }
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void outputTop_kmovies(matrix_and_map_2 m, PLSA pmp, movieList ml, Calender c, Path PathDef) {
        try {
            OutputStreamWriter osw = data_storage.file_handle(PathDef.OUTPUT_PATH + "movieGlobal/top_k_movies");
            //int numberOfMovies = pmp.number_of_keywords;
            //int numberOfTimeslices = pmp.number_of_timeslices;
            double[] background = pmp.back_ground;
            HashMap<Integer, Double> bk = new HashMap<Integer, Double>();
            for (int i = 0; i < background.length; i++) {
                bk.put(i, background[i]);
            }

            List<Map.Entry<Integer, Double>> list = RankingMap(bk);

            for (Map.Entry<Integer, Double> ent : list) {
                int movieId = ent.getKey();
                double score = ent.getValue();
                if (ml.movieIdMap.containsKey(movieId)) {
                    int listId = ml.movieIdMap.get(movieId);
                    outputMovieInfo(ml, listId, osw);
                    osw.write(" ");
                    osw.write(score + "");
                    osw.write("\n");
                }

            }
            osw.write("\n");
            osw.flush();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void outputMovieOnTime(matrix_and_map_2 m, Item_based_Fashion_Context pmp, movieList ml, Calender c, Path PathDef) {
        try {
            OutputStreamWriter osw = data_storage.file_handle(PathDef.OUTPUT_PATH + "movieTemporal/movieTemporal");
            int numberOfMovies = pmp.number_of_keywords;
            int numberOfTimeslices = pmp.number_of_timeslices;
            sort_major sm = new sort_major();
            myarray[][] ma = new myarray[numberOfTimeslices][numberOfMovies];
            sm.sort_first_dimension(pmp.keywords_given_temporal, m.reverse_map1, pmp.keywords_given_temporal.length, ma);
            final int SIZE_OF_OUTPUT = 20;
            for (int t = 0; t < numberOfTimeslices; t++) {
                osw.write("Time: " + c.slice2date(t) + "\n");
                for (int i = 0; i < SIZE_OF_OUTPUT; i++) {
                    int movieId = ma[t][i].key;
                    double score = ma[t][i].value;
                    int listId = ml.movieIdMap.get(movieId);
                    outputMovieInfo(ml, listId, osw);
                    osw.write(score + "");
                    osw.write("\n");
                }
                osw.write("\n");
                osw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //this function is designed to output the temporal movie
    public static void outputMovieTemporal(int movieIdArray[], Item_based_Fashion_Context pmp, movieList ml, Path PathDef) {
        try {
            OutputStreamWriter osw = data_storage.file_handle(PathDef.OUTPUT_PATH + "movieTemporal/movieTemporalDistribution");
            int numberOfMovies = pmp.number_of_keywords;
            int numberOfTimeslices = pmp.number_of_timeslices;
            for (int i = 0; i < numberOfMovies; i++) {
                int movieId = movieIdArray[i];
                int listId = ml.movieIdMap.get(movieId);
                outputMovieInfo(ml, listId, osw);//output movie info

                for (int t = 0; t < numberOfTimeslices; t++) {//putput its distribution on time
                    osw.write(pmp.keywords_given_temporal[i][t] + " ");
                }
                osw.write("\n");
                osw.flush();
            }
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void outputUserInterest(int userIdArray[], Item_based_Fashion_Context pmp, Path PathDef) {
        try {
            OutputStreamWriter osw = data_storage.file_handle(PathDef.OUTPUT_PATH + "movieTopic/UserInterest");
            int numberOfUsers = pmp.number_of_documents;
            int numberOfTopics = pmp.number_of_topics;
            for (int i = 0; i < numberOfUsers; i++) {
                osw.write("User " + userIdArray[i] + ":");
                osw.write("Temporal:" + pmp.lambda_temporal[i] + " " + "Stable:");
                for (int j = 0; j < numberOfTopics; j++) {
                    osw.write(" " + pmp.topics_given_documents[j][i]);
                }
                osw.write("\n");
                osw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //////////////////
    public static void outputCluster(ArrayList<myarray> user_lambda, matrix_and_map_2 movieUser,
                                     movieList ml, Topic_Varying_model_Cluster pmp, UserCluster uc,
                                     Path PathDef) {
        try {
            outputCluster2(user_lambda, movieUser, ml, pmp, uc, PathDef);

            OutputStreamWriter osw = data_storage.file_handle(PathDef.OUTPUT_PATH + "user_lambda");

            int size = user_lambda.size();

            int userId = 0;
            for (int i = 0; i < size; i++) {
                if (user_lambda.get(i).key >= movieUser.size2)
                    System.out.println("User Id error ! ");

                userId = movieUser.reverse_map2[user_lambda.get(i).key];
                osw.write(" " + userId + " " + user_lambda.get(i).value + "\n");

                ArrayList<Item> it_list = pmp.user_item_graph.get(user_lambda.get(i).key);
                for (Item it : it_list) {
                    int movieId = movieUser.reverse_map1[it.item_id];
                    osw.write(" " + movieId
                            + " " + it.slice_id + " "
                            + ml.getMovie(movieId).firstratetime + " " + it.number
                            + " " + uc.getFashion(it.slice_id, it.item_id) + "\n");
                }

                osw.flush();
            }
            osw.close();

            System.out.println(" cluster user lambda file complete ");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void outputCluster2(ArrayList<myarray> user_lambda, matrix_and_map_2 movieUser,
                                      movieList ml, Topic_Varying_model_Cluster pmp, UserCluster uc,
                                      Path PathDef) {
        try {
            OutputStreamWriter osw = data_storage.file_handle(PathDef.OUTPUT_PATH + "cluster_user");

            int[] cluster_size = new int[uc.cluster_num];
            for (int i_c = 0; i_c < uc.cluster_num; i_c++) {
                cluster_size[i_c] = 0;
            }
            for (int i_d = 0; i_d < uc.user_num; i_d++) {
                cluster_size[uc.user_to_cluster[i_d]]++;
            }

            for (int i_c = 0; i_c < uc.cluster_num; i_c++) {
                osw.write("cluster " + i_c + " " + cluster_size[i_c] + "\n");
            }

            osw.close();

            System.out.println(" cluster user file complete ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // for - background model

    public static void outputBackground(Topic_Varying_model_Cluster_Background pmp,
                                        Path PathDef) {
        try {
            OutputStreamWriter osw
                    = data_storage.file_handle(PathDef.MATRIX_PATH + "keywords_given_background");

            int size = pmp.number_of_keywords;
            osw.write(size + "\n");
            for (int i = 0; i < size; i++) {
                osw.write(pmp.keywords_given_background[i] + "\n");
            }
            osw.flush();
            osw.close();

            System.out.println(" output background done ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
