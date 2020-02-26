//package com.youfan.task;
//
//import java.util.*;
//import java.util.regex.Pattern;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.common.serialization.StringDeserializer;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.streaming.api.java.*;
//import org.apache.spark.streaming.kafka010.ConsumerStrategies;
//import org.apache.spark.streaming.kafka010.KafkaUtils;
//import org.apache.spark.streaming.kafka010.LocationStrategies;
//import org.apache.spark.streaming.Durations;
//import scala.Tuple2;
//
//public class WordCount {
//
//    private static final String producttopic = "pvlog";
//    private static final String zookeepadress = "192.168.0.41";
//    private static final int numthreads = 1;
//    private static final String group = "product";
//    private static final Pattern SPACE = Pattern.compile(" ");
//    private static final String brokers = "192.168.0.41";
//    private static final String groupId = "product";
//    private static final String topics = "pvlog";
//
//    public static void main(String[] args) throws Exception {
////        if (args.length < 3) {
////            System.err.println("Usage: JavaDirectKafkaWordCount <brokers> <groupId> <topics>\n" +
////                    "  <brokers> is a list of one or more Kafka brokers\n" +
////                    "  <groupId> is a consumer group name to consume from topics\n" +
////                    "  <topics> is a list of one or more kafka topics to consume from\n\n");
////            System.exit(1);
////        }
//
////        StreamingExamples.setStreamingLogLevels();
//
////        String brokers = args[0];
////        String groupId = args[1];
////        String topics = args[2];
//
//        // Create context with a 2 seconds batch interval
//        SparkConf sparkConf = new SparkConf().setAppName("WordCount").setMaster("local[2]");
////        .setMaster("spark://master");
//
//        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.seconds(2));
//
//        Set<String> topicsSet = new HashSet<>(Arrays.asList(topics.split(",")));
//        Map<String, Object> kafkaParams = new HashMap<>();
//        kafkaParams.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
//        kafkaParams.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//        kafkaParams.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        kafkaParams.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//
//        Map<String, Integer> topicMap = new HashMap<String, Integer>();
//        topicMap.put(producttopic, numthreads);
//
//        // Create direct kafka stream with brokers and topics
//        JavaInputDStream<ConsumerRecord<Object, Object>> messages = KafkaUtils.createDirectStream(
//                jssc,
//                LocationStrategies.PreferConsistent(),
//                topicMap);
//
//        // Get the lines, split them into words, count the words and print
//        JavaDStream<String> lines =  messages.map(s -> String.valueOf(s.value()));
//        JavaDStream<String> words = lines.flatMap(x -> Arrays.asList(SPACE.split(x)).iterator());
//        JavaPairDStream<String, Integer> wordCounts = words.mapToPair(s -> new Tuple2<>(s, 1))
//                .reduceByKey((i1, i2) -> i1 + i2);
//        wordCounts.print();
//
//        // Start the computation
//        jssc.start();
//        jssc.awaitTermination();
//    }
//}