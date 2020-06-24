import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.List;

public class InfluxDBUtil {
    private static String host="192.168.0.138";
    private static int port=8086;
    private static String db = "my";
    private static String url = "http://"+host+":"+port;

    private static InfluxDB influxDB ;

    static {
        influxDB = InfluxDBFactory.connect(url);
        influxDB.setDatabase(db);
    }

    public static void write(){
        influxDB.write(
                Point.measurement("cpu").tag("myname", "congzhizhi")
                        .tag("host","192.168.0.138")
                        .tag("port","8081")
                        .addField("value", 10)
                        .build()
        );


    }

    public static void query(){
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

    public static void showLineProtocol(){
        String line = Point.measurement("demo_api").tag("name", "hello")
                .addField("rt", 3).addField("times", 145) .build().lineProtocol();

        System.out.println(line);
    }




    public static void main(String[] args) {
//        write();
        query();
    }


}
