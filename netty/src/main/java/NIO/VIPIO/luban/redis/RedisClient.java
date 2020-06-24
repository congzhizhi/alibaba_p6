package NIO.VIPIO.luban.redis;


import redis.clients.jedis.Jedis;

//模拟jedis
public class RedisClient {

    private LubanSocket lubanSocket;

    public RedisClient(String ip,int prot) {
        this.lubanSocket=new LubanSocket(ip,prot);
    }

    public String set(String key, String value){
        lubanSocket.send(commandStrUtil(Resp.command.SET,key.getBytes(),value.getBytes()));
        return lubanSocket.read();
    }


    public void close(){
        lubanSocket.close();
    }


    public String get(String key){
        lubanSocket.send(commandStrUtil(Resp.command.GET,key.getBytes()));
        return lubanSocket.read();
    }


    public String incr(String key){
        lubanSocket.send(commandStrUtil(Resp.command.INCR,key.getBytes()));
        return lubanSocket.read();
    }


    public String commandStrUtil(Resp.command command, byte[]... bytes){
        StringBuilder stringBuilder=new StringBuilder();
        //拼接*3，set key value，总共3个，bytes代表键和值参数，注意拼接完要加回车换行
        stringBuilder.append(Resp.star).append(1+bytes.length).append(Resp.crlf);
        //拼接SET的长度，$3
        stringBuilder.append(Resp.lengthStart).append(command.toString().getBytes().length).append(Resp.crlf);
        //拼接SET字符串
        stringBuilder.append(command.toString()).append(Resp.crlf);
        //拼接键和值
        for (byte[] aByte : bytes) {
            stringBuilder.append(Resp.lengthStart).append(aByte.length).append(Resp.crlf);
            stringBuilder.append(new String(aByte)).append(Resp.crlf);
        }
        return stringBuilder.toString();
    }


    public static void main(String[] args) {
        RedisClient redisClient=new RedisClient("localhost",6379);
        System.out.println(redisClient.set("yuanma", "123456"));
        System.out.println(redisClient.get("yuanma"));
        redisClient.close();
    }
}
