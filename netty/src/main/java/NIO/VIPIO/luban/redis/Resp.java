package NIO.VIPIO.luban.redis;

public class Resp {
    /**
     * redis网络通信协议，比如set("name","congzhizhi")
     * *3
     * $3
     * set
     * $4
     * name
     * $10
     * congzhizhi
     * 其中，*3表示发了3个参数，$3表示下面的参数3个字符，以此类推
     *
     *
     */
    public static final String star="*";
    public static final String crlf="\r\n";
    public static final String lengthStart="$";

    public static enum command{
        SET,GET,INCR
    }
}
