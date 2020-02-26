package com.gottaboy.iching.spark.task;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class JavaWordCount {

    private static final Pattern SPACE = Pattern.compile(" ");
    private static final String filePath = "/Users/minyi/web/ideaSpace/iching-cloud/youfanSSAnaly/src/main/java/com/youfan/task/JavaWordCount.txt";
    private static final String saveFilePath = "/Users/minyi/web/ideaSpace/iching-cloud/youfanSSAnaly/src/main/java/com/youfan/task/JavaWordCount";
    public static void main(String[] args) throws Exception {
//        if (args.length < 1) {
//            System.err.println("Usage: JavaWordCount <file>");
//            System.exit(1);
//        }

        SparkSession spark = SparkSession
                .builder()
                .master("local[2]")
                .appName("JavaWordCount")
                .getOrCreate();
        JavaRDD<String> lines = spark.read().textFile(filePath).javaRDD();
        JavaRDD<String> words = lines.flatMap(s -> Arrays.asList(SPACE.split(s)).iterator());

        JavaPairRDD<String, Integer> ones = words.mapToPair(s -> new Tuple2<>(s, 1));
        JavaPairRDD<String, Integer> counts = ones.reduceByKey((i1, i2) -> i1 + i2);

        List<Tuple2<String, Integer>> output = counts.collect();
        for (Tuple2<?,?> tuple : output) {
            System.out.println(tuple._1() + ": " + tuple._2());
        }
        counts.saveAsTextFile(saveFilePath);
        spark.stop();
    }
}