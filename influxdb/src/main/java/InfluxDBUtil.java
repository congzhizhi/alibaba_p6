import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

public class InfluxDBUtil {
    private static String host="192.168.0.138";
    private static int port=8086;
    private static String db = "my";
    private static String url = "http://"+host+":"+port+"/";

    private static InfluxDB influxDB ;

    static {
        influxDB = InfluxDBFactory.connect(url);
        influxDB.setDatabase(db);
    }

    public static void insert(){

    }

    public static void query(){

    }



    public static void main(String[] args) {
        String line = Point.measurement("demo_api").tag("name", "hello")
                .addField("rt", 3).addField("times", 145) .build().lineProtocol();

        System.out.println(line);
    }


}
