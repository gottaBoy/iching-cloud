//package com.youfan.dao;
//
//import com.youfan.analy.result.UservisitState;
//import com.youfan.util.DateUtil;
//import com.youfan.util.Hbaseutil;
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// *
// */
//public class UserStateDao {
//
//    public static UservisitState getUserVisitState(long userid,long pingdaoid,long visittime) {
//        UservisitState uservisitState = new UservisitState();
//        try {
//            String firstvisitime = Hbaseutil.get("userscanlog",userid+"=="+pingdaoid,"info","firstvisittime");
//            if(StringUtils.isBlank(firstvisitime)){
//                uservisitState.setNewuser(true);
//                uservisitState.setFirstday(true);
//                uservisitState.setFirsthousr(true);
//                uservisitState.setFirstmonth(true);
//                Map<String,String> datamap = new HashMap<String,String>();
//                datamap.put("firstvisittime",visittime+"");
//                datamap.put("lastvisittime",visittime+"");
//                Hbaseutil.put("userscanlog",userid+"=="+pingdaoid,"info",datamap);
//            }else{
//                String lastvisittime = Hbaseutil.get("userscanlog",userid+"=="+pingdaoid,"info","lastvisittime");
//                long lastvisittimelong = Long.valueOf(lastvisittime);
//
//                long hourtime = DateUtil.getbydateformt(visittime,"yyyyMMdd");//小时
//
//                long daytime =  DateUtil.getbydateformt(visittime,"yyyyMMdd");//天
//
//                long monthtime =   DateUtil.getbydateformt(visittime,"yyyyMMdd");//月
//
//                if(lastvisittimelong < hourtime){
//                    uservisitState.setFirsthousr(true);
//                }
//                if(lastvisittimelong < daytime){
//                    uservisitState.setFirstday(true);
//                }
//                if(lastvisittimelong < monthtime){
//                    uservisitState.setFirstmonth(true);
//                }
//                Hbaseutil.put("userscanlog",userid+"=="+pingdaoid,"info","lastvisittime",visittime+"");
//            }
//            uservisitState.setTimestamp(visittime);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return uservisitState;
//    }
//}
