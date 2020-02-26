package com.gottaboy.iching.spark.ml;

import java.io.Serializable;
import java.util.regex.Pattern;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.ml.evaluation.RegressionEvaluator;
import org.apache.spark.ml.recommendation.ALS;
import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * http://bigdataer.net/?p=569
 */
public class JavaALSExample {

    private static final Pattern SPLIT = Pattern.compile("\t");
    private static final String path = "/Users/minyi/web/ideaSpace/iching-cloud/iching-spark/ml-100k/u.data";
    private static final String output = "/Users/minyi/web/ideaSpace/iching-cloud/iching-spark/output/";

    public static void main(String[] args) throws Exception {
//        splitData();
        alsMl();
    }

    public static void splitData(){
        String s = "62\t257\t2\t879372434";
        Rating rating = Rating.parseRating(s);
    }

    public static void alsMl(){
        SparkSession spark = SparkSession
                .builder()
                .master("local[1]")
                .appName("JavaALSExample")
                .getOrCreate();

        JavaRDD<Rating> ratingsRDD = spark.read().textFile(path).javaRDD().map(s -> Rating.parseRating(s));

        Dataset<Row> ratings = spark.createDataFrame(ratingsRDD, Rating.class);
        Dataset<Row>[] splits = ratings.randomSplit(new double[]{0.8, 0.2});
        Dataset<Row> training = splits[0];
        Dataset<Row> test = splits[1];
        training.printSchema();
        test.printSchema();

        // Build the recommendation model using ALS on the training data
        ALS als = new ALS()
                .setMaxIter(5)
                .setRegParam(0.01)
                .setUserCol("userId")
                .setItemCol("movieId")
                .setRatingCol("rating");
        ALSModel model = als.fit(training);

        // Evaluate the model by computing the RMSE on the test data
        // Note we set cold start strategy to 'drop' to ensure we don't get NaN evaluation metrics
        model.setColdStartStrategy("drop");
        Dataset<Row> predictions = model.transform(test);

        RegressionEvaluator evaluator = new RegressionEvaluator()
                .setMetricName("rmse")
                .setLabelCol("rating")
                .setPredictionCol("prediction");
        Double rmse = evaluator.evaluate(predictions);
        System.out.println("Root-mean-square error = " + rmse);

        // Generate top 10 movie recommendations for each user
        Dataset<Row> userRecs = model.recommendForAllUsers(10);
        // Generate top 10 user recommendations for each movie
        Dataset<Row> movieRecs = model.recommendForAllItems(10);

        userRecs.write().json(output);
        movieRecs.write().json(output);

        userRecs.show();
        movieRecs.show();
        spark.stop();
    }

    public static class Rating implements Serializable {

        private int userId;
        private int movieId;
        private float rating;
        private long timestamp;

        public Rating() {}

        public Rating(int userId, int movieId, float rating, long timestamp) {
            this.userId = userId;
            this.movieId = movieId;
            this.rating = rating;
            this.timestamp = timestamp;
        }

        public int getUserId() {
            return userId;
        }

        public int getMovieId() {
            return movieId;
        }

        public float getRating() {
            return rating;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public static Rating parseRating(String str) {
            String[] fields = str.split("\t");
            if (fields.length != 4) {
                throw new IllegalArgumentException("Each line must contain 4 fields");
            }

            int userId = Integer.parseInt(fields[0]);
            int movieId = Integer.parseInt(fields[1]);
            float rating = Float.parseFloat(fields[2]);
            long timestamp = Long.parseLong(fields[3]);
            return new Rating(userId, movieId, rating, timestamp);
        }

    }
}