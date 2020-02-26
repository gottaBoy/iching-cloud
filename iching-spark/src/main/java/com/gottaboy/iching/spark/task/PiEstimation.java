//package com.youfan.task;
//
//import org.apache.spark.api.java.JavaPairRDD;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.sql.SparkSession;
//import org.apache.spark.sql.sources.In;
//import scala.Tuple2;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.regex.Pattern;
//
//public class PiEstimation {
//
//    private static final Integer NUM_SAMPLES = 100;
//    public static void main(String[] args) throws Exception {
////        if (args.length < 1) {
////            System.err.println("Usage: JavaWordCount <file>");
////            System.exit(1);
////        }
//
//        SparkSession spark = SparkSession
//                .builder()
//                .master("local[2]")
//                .appName("JavaWordCount")
//                .getOrCreate();
//        List<Integer> l = new ArrayList<>(NUM_SAMPLES);
//        for (int i = 0; i < NUM_SAMPLES; i++) {
//            l.add(i);
//        }
//
//        long count = spark.sparkContext().parallelize(l).filter(i -> {
//            double x = Math.random();
//            double y = Math.random();
//            return x*x + y*y < 1;
//        }).count();
//        System.out.println("Pi is roughly " + 4.0 * count / NUM_SAMPLES);
//
//        spark.stop();
//    }
//}