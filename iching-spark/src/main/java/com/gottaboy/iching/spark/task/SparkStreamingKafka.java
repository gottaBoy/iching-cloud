package com.gottaboy.iching.spark.task;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import scala.Tuple2;

import java.util.*;
import java.util.regex.Pattern;

public class SparkStreamingKafka {

    private static final Pattern SPACE = Pattern.compile(" ");

    public static void main(String[] args) throws Exception{

        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "192.168.0.41:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "iching.group1");
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);

        SparkConf sparkConf = new SparkConf().setAppName("SparkStreamingKafka").setMaster("local[2]");
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        jsc.setLogLevel("ERROR");
        jsc.setCheckpointDir("./checkpoint");
        JavaStreamingContext ssc = new JavaStreamingContext(jsc, Durations.seconds(1));

        Collection<String> topics = Arrays.asList("pvlog");
        JavaInputDStream<ConsumerRecord<String, String>> stream =
                KafkaUtils.createDirectStream(
                        ssc,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams)
                );

        // 注意这边的stream里的参数本身是个ConsumerRecord对象
        JavaPairDStream<String, Integer> counts = stream
                .flatMap(x -> Arrays.asList(x.value().split(" ")).iterator())
                .mapToPair(x -> new Tuple2<String, Integer>(x, 1))
                .reduceByKey((x, y) -> x + y);
        counts.print();

        JavaPairDStream<String, Integer> result = counts.updateStateByKey(new Function2<List<Integer>, Optional<Integer>, Optional<Integer>>() {
            @Override
            public Optional<Integer> call(List<Integer> values, Optional<Integer> state) throws Exception {
                /**
                 * values:经过分组最后 这个key所对应的value，如：[1,1,1,1,1]
                 * state:这个key在本次之前之前的状态
                 */
                Integer updateValue = 0;
                if (state.isPresent()) {
                    updateValue = state.get();
                }

                for (Integer value : values) {
                    updateValue += value;
                }
                return Optional.of(updateValue);
            }
        });
        result.print();

        ssc.start();
        ssc.awaitTermination();
        ssc.close();
    }
}
