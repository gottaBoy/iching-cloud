//package com.youfan.task;
//
//import com.alibaba.fastjson.JSONObject;
//import com.youfan.analy.input.Productscanlog;
//import com.youfan.analy.result.PindaoAnaly;
//import com.youfan.analy.result.UservisitState;
//import com.youfan.dao.UserStateDao;
//import com.youfan.util.Hbaseutil;
//import com.youfan.util.ReaderPropertiesUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaPairRDD;
//import org.apache.spark.api.java.function.*;
//import org.apache.spark.streaming.Duration;
//import org.apache.spark.streaming.Durations;
//import org.apache.spark.streaming.api.java.JavaDStream;
//import org.apache.spark.streaming.api.java.JavaPairDStream;
//import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
//import org.apache.spark.streaming.api.java.JavaStreamingContext;
//import org.apache.spark.streaming.kafka010.KafkaUtils;
//import scala.Tuple2;
//
//import java.util.*;
//
//import static org.apache.zookeeper.ZooDefs.OpCode.create;
//import static org.apache.zookeeper.ZooDefs.OpCode.ping;
//
///**
// * minyi
// */
//public class ProudctAnaly {
//    private static final String producttopic = ReaderPropertiesUtil.getkey("producttopic");
//    private static final String zookeepadress = ReaderPropertiesUtil.getkey("zookeepadress");
//    private static final int numthreads = Integer.valueOf(ReaderPropertiesUtil.getkey("numthreads"));
//    private static final String group = "product";
//
//    public static void main(String[] args) throws  Exception {
//
//        SparkConf sparkConf = new SparkConf().setAppName("ProudctAnaly").setMaster("local[2]");
//        // Create the context with 2 seconds batch size
//        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, new Duration(2000));
//
//        Map<String, Integer> topicMap = new HashMap<String, Integer>();
//        topicMap.put(producttopic, numthreads);
//
//        JavaPairReceiverInputDStream<String, String> messages =
//                KafkaUtils.createRDD().createStream(jssc, zookeepadress, group, topicMap);
//
//        JavaDStream<String> lines = messages.map(new Function<Tuple2<String, String>, String>() {
//            public String call(Tuple2<String, String> tuple2) throws Exception {
//                String data = tuple2._2();
//                return data;
//            }
//        });
//
//        JavaPairDStream<Long,PindaoAnaly> pairmap = lines.flatMapToPair(new PairFlatMapFunction<String,Long,PindaoAnaly>(){
//
//            public Iterator<Tuple2<Long,PindaoAnaly>> call(String data) throws Exception {
//                List<Tuple2<Long,PindaoAnaly>> list = new ArrayList<Tuple2<Long,PindaoAnaly>>();
//                Productscanlog productscanlog = JSONObject.parseObject(data, Productscanlog.class);
//                long userid = productscanlog.getUserid();//用户id
//                long pindaoid = productscanlog.getPindaoid();//频道id
//                long visittime = productscanlog.getTimestamp();//访问时间
//                UservisitState uservisitState = UserStateDao.getUserVisitState(userid,pindaoid,visittime);
//                boolean isfirsthour = uservisitState.isFirsthousr();
//                boolean isfirstday = uservisitState.isFirstday();
//                boolean isfirstmonth = uservisitState.isFirstmonth();
//                PindaoAnaly pindaoAnaly = new PindaoAnaly();
//                pindaoAnaly.setPindaoid(pindaoid);
//                pindaoAnaly.setHouruv(isfirsthour?1l:0l);
//                pindaoAnaly.setDayuv(isfirstday?1l:0l);
//                pindaoAnaly.setMonthuv(isfirstmonth?1l:0l);
//                Tuple2<Long,PindaoAnaly> tuple = new Tuple2<Long, PindaoAnaly>(pindaoid,pindaoAnaly);
//                list.add(tuple);
//                return list.iterator();
//            }
//        });
//
//        JavaPairDStream<Long,PindaoAnaly> javaPairreduce = pairmap.reduceByKey(new Function2<PindaoAnaly, PindaoAnaly, PindaoAnaly>(){
//            public PindaoAnaly call(PindaoAnaly v1, PindaoAnaly v2) throws Exception {
//                long pindaoid = v1.getPindaoid();
//                long pv1 = v1.getPvcount();
//                long houruv1 = v1.getHouruv();
//                long dayuv1 = v1.getDayuv();
//                long monthuv1 = v1.getMonthuv();
//
//                long pv2 = v2.getPvcount();
//                long houruv2 = v2.getHouruv();
//                long dayuv2 = v2.getDayuv();
//                long monthuv2 = v2.getMonthuv();
//
//                PindaoAnaly pindaoAnaly = new PindaoAnaly();
//                pindaoAnaly.setPindaoid(pindaoid);
//                pindaoAnaly.setPvcount(pv1+pv2);
//                pindaoAnaly.setHouruv(houruv1+houruv2);
//                pindaoAnaly.setDayuv(dayuv1+dayuv2);
//                pindaoAnaly.setMonthuv(monthuv1+monthuv2);
//                return pindaoAnaly;
//
//            }
//        });
//
//        javaPairreduce.foreachRDD(new VoidFunction<JavaPairRDD<Long,PindaoAnaly>>(){
//            public void call(JavaPairRDD<Long,PindaoAnaly> pindaoAnalyJavaPairRDD) throws Exception {
//                    List<Tuple2<Long,PindaoAnaly>> list = pindaoAnalyJavaPairRDD.collect();
//                    for(Tuple2<Long,PindaoAnaly> tuple :list){
//                        long pindaoid = tuple._1();
//                        PindaoAnaly pindaoAnaly = tuple._2();
//                        try {
//                            String pv = Hbaseutil.get("pindaoanaly",pindaoid+"","info","pv");
//                            String houruv = Hbaseutil.get("pindaoanaly",pindaoid+"","info","houruv");
//                            String dayuv = Hbaseutil.get("pindaoanaly",pindaoid+"","info","dayuv");
//                            String monthuv = Hbaseutil.get("pindaoanaly",pindaoid+"","info","monthuv");
//                            if(StringUtils.isBlank(pv)){
//                                Map<String,String> datamap = new HashMap<String, String>();
//                                datamap.put("pv",pindaoAnaly.getPvcount()+"");
//                                datamap.put("houruv",pindaoAnaly.getHouruv()+"");
//                                datamap.put("dayuv",pindaoAnaly.getDayuv()+"");
//                                datamap.put("monthuv",pindaoAnaly.getMonthuv()+"");
//                                Hbaseutil.put("pindaoanaly",pindaoid+"","info",datamap);
//                            }else{
//                                long pvlong = Long.valueOf(pv);
//                                long houruvlong = Long.valueOf(houruv);
//                                long dayuvlong = Long.valueOf(dayuv);
//                                long monthuvlong = Long.valueOf(monthuv);
//
//                                pvlong += pindaoAnaly.getPvcount();
//                                houruvlong += pindaoAnaly.getHouruv();
//                                dayuvlong += pindaoAnaly.getDayuv();
//                                monthuvlong += pindaoAnaly.getMonthuv();
//                                Map<String,String> datamap = new HashMap<String, String>();
//                                datamap.put("pv",pvlong+"");
//                                datamap.put("houruv",houruvlong+"");
//                                datamap.put("dayuv",dayuvlong+"");
//                                datamap.put("monthuv",monthuvlong+"");
//                                Hbaseutil.put("pindaoanaly",pindaoid+"","info",datamap);
//                            }
//                    }catch (Exception e ){
//                            e.printStackTrace();
//                    }
//            }
//        }
//     });
//
//        jssc.start();
//        jssc.awaitTermination();
//
//    }
//}
