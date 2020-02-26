package com.gottaboy.iching.spark.mllib;

// $example on$
import scala.Tuple2;

import org.apache.spark.api.java.*;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import org.apache.spark.SparkConf;
// $example off$

public class JavaRecommendationExample {
    private static final String path = "/Users/minyi/web/ideaSpace/iching-cloud/iching-spark/ml-100k/u.data";
    private static final String output = "/Users/minyi/web/ideaSpace/iching-cloud/iching-spark/output/";

    public static void main(String[] args) {
        // $example on$
        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("Java Collaborative Filtering Example");
        JavaSparkContext jsc = new JavaSparkContext(conf);

        // Load and parse the data
        JavaRDD<String> data = jsc.textFile(path);
        JavaRDD<Rating> ratings = data.map(s -> {
            // "196	242	3 881250949"
            String[] sarray = s.split(" ");
            System.out.println(sarray[0]);
            return new Rating(Integer.parseInt(sarray[0]),
                    Integer.parseInt(sarray[1]),
                    Double.parseDouble(sarray[2]));
        });

        // Build the recommendation model using ALS
        int rank = 10;
        int numIterations = 10;
        MatrixFactorizationModel model = ALS.train(JavaRDD.toRDD(ratings), rank, numIterations, 0.01);

        // Evaluate the model on rating data
        JavaRDD<Tuple2<Object, Object>> userProducts =
                ratings.map(r -> new Tuple2<>(r.user(), r.product()));
        JavaPairRDD<Tuple2<Integer, Integer>, Double> predictions = JavaPairRDD.fromJavaRDD(
                model.predict(JavaRDD.toRDD(userProducts)).toJavaRDD()
                        .map(r -> new Tuple2<>(new Tuple2<>(r.user(), r.product()), r.rating()))
        );
        JavaRDD<Tuple2<Double, Double>> ratesAndPreds = JavaPairRDD.fromJavaRDD(
                ratings.map(r -> new Tuple2<>(new Tuple2<>(r.user(), r.product()), r.rating())))
                .join(predictions).values();
        double MSE = ratesAndPreds.mapToDouble(pair -> {
            double err = pair._1() - pair._2();
            return err * err;
        }).mean();
        System.out.println("Mean Squared Error = " + MSE);

        // Save and load model
        model.save(jsc.sc(), output + "myCollaborativeFilter");
        MatrixFactorizationModel sameModel = MatrixFactorizationModel.load(jsc.sc(),
                output + "myCollaborativeFilter");
        // $example off$

        jsc.stop();
    }
}