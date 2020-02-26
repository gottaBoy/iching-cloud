//package com.youfan.util;
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.hbase.HBaseConfiguration;
//import org.apache.hadoop.hbase.TableName;
//import org.apache.hadoop.hbase.client.*;
//import org.apache.hadoop.hbase.util.Bytes;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.Set;
//
///**
// * Created by Administrator on 2018/11/9 0009.
// * create 'userscanlog','info'
// * create 'pindaoanaly','info'
// */
//public class Hbaseutil {
//
//        private static final Configuration conf = HBaseConfiguration.create();
//        private static Connection conn = null;
//        static{
//            conf.set("hbase.rootdir","hdfs://t1:9000/hbase");
//            //使用eclipse时必须添加这个，否则无法定位
//            conf.set("hbase.zookeeper.quorum","t1,t2,t3");
//            conf.set("hbase.client.scanner.timeout.period", "6000");
//            conf.set("hbase.rpc.timeout", "6000");
//            try {
//                conn = ConnectionFactory.createConnection(conf);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        /**
//         * 插入数据
//         */
//        public static void put(String tablename,String rowkey,String columfamliy,Map<String,String> datamap) throws Exception {
//            // 获得table对象
//            Table table = conn.getTable(TableName.valueOf(tablename));
//            // 将字符串转换成byte[]
//            byte[] rowkeybytes = Bytes.toBytes(rowkey);
//            Put put = new Put(rowkeybytes);
//            Set<Map.Entry<String,String>> set = datamap.entrySet();
//            for(Map.Entry<String,String> entry:set){
//                String key = entry.getKey();
//                String value = entry.getValue();
//                put.addColumn(Bytes.toBytes(columfamliy), Bytes.toBytes(key), Bytes.toBytes(value));
//            }
//            table.put(put);
//            table.close();
//            System.out.println("ok");
//        }
//
//
//    /**
//     * 插入数据
//     */
//    public static void put(String tablename,String rowkey,String columfamliy,String colum,String value) throws Exception {
//        // 获得table对象
//        Table table = conn.getTable(TableName.valueOf(tablename));
//        // 将字符串转换成byte[]
//        byte[] rowkeybytes = Bytes.toBytes(rowkey);
//        Put put = new Put(rowkeybytes);
//        put.addColumn(Bytes.toBytes(columfamliy), Bytes.toBytes(colum), Bytes.toBytes(value));
//        table.put(put);
//        table.close();
//        System.out.println("ok");
//    }
//
//
//    /**
//     * 查询数据
//     */
//    public static String get(String tablename,String rowkey,String columfamliy,String colum) throws Exception {
//        // 获得table对象
//        Table table = conn.getTable(TableName.valueOf(tablename));
//        // 将字符串转换成byte[]
//        byte[] rowkeybytes = Bytes.toBytes(rowkey);
//        Get get = new Get(rowkeybytes);
//        Result result =table.get(get);
//        byte[] value = result.getValue(columfamliy.getBytes(),colum.getBytes());
//        if(value !=null){
//            String reusltstring = new String(value);
//            return reusltstring;
//        }
//
//
//        table.close();
//        return "";
//    }
//}
