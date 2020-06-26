import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.InfluxDBIOException;
import org.influxdb.dto.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class InfluxDBUtil {
    private static String host="192.168.0.138";
    private static int port=8086;
    private static String db = "my";
    private static String url = "http://"+host+":"+port;

    private static InfluxDB influxDB ;

    static {
            connect();
    }

    public static void connect()throws InfluxDBIOException  {
        //如果InfluxDB关闭，发请求时会报InfluxDBIOException异常
        //此时，不需要重连，influxDB启动后，发送的请求会自动链接
        influxDB = InfluxDBFactory.connect(url);
        influxDB.setDatabase(db);
        if (!isConnected()){
            throw new InfluxDBIOException(new IOException("链接失败"));
        }

    }

    public static void close(){
        influxDB.close();
    }
    public static boolean isConnected(){
        boolean isConnected = false;
        try {
            Pong pong =influxDB.ping();
            if (pong!=null && pong.isGood()){
                isConnected = true;
            }
        } catch (Exception e) {
            isConnected = false;
            e.printStackTrace();
        }
        return isConnected;

    }

    public static void write() throws Exception{
        BatchPoints batchPoints =BatchPoints.database("my").retentionPolicy("default")
                .consistency(InfluxDB.ConsistencyLevel.ALL).build();
        for (int i=0;i<10000;i++){
            Point point = Point.measurement("cpu").tag("myname", "congzhizhi").time(1893015197740000000L +i, TimeUnit.NANOSECONDS)
                    .tag("host","192.168.0.138")
                    .tag("port","8081")
                    .addField("value", 12)
                    .build();
            batchPoints.point(point);
        }
        influxDB.write(batchPoints);


    }

    public static void query() throws Exception{
        //默认查询是select * from mydb.autogen.demo_api
        QueryResult rs = influxDB.query(new Query("select * from cpu"));
        if (!rs.hasError() && !rs.getResults().isEmpty()) {
            List<QueryResult.Result> results =  rs.getResults();
            for (QueryResult.Result result : results) {
                List<List<Object>> values =  result.getSeries().get(0).getValues();
                for (List<Object> value : values){
                    System.out.println(value.get(1));
                }

            }

//            rs.getResults().forEach(System.out::println);

        }

    }

    public static void showLineProtocol() throws Exception{
        String line = Point.measurement("demo_api").tag("name", "hello")
                .addField("rt", 3).addField("times", 145) .build().lineProtocol();

        System.out.println(line);
    }




    public static void main(String[] args)  {

        try {
            query();
        }catch (InfluxDBIOException e){
            e.printStackTrace();
            System.out.println("链接异常");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
