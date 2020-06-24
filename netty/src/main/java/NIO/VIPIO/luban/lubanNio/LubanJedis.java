package NIO.VIPIO.luban.lubanNio;

public class LubanJedis {

    LubanSocket lubanSocket=new LubanSocket("127.0.0.1",6379);

    public String set(String key, String value){
        lubanSocket.send(commandUtil(Resp.command.SET,key.getBytes(),value.getBytes()));
        return lubanSocket.read();
    }


    public String get(String key){
        lubanSocket.send(commandUtil(Resp.command.GET,key.getBytes()));
        return lubanSocket.read();
    }


    public String incr(String key){
        lubanSocket.send(commandUtil(Resp.command.INCR,key.getBytes()));
        return lubanSocket.read();
    }



    public static String commandUtil(Resp.command command,byte[]... bytes){
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(Resp.star).append(1+bytes.length).append(Resp.line);
        stringBuilder.append(Resp.StringLength).append(command.toString().length()).append(Resp.line);
        stringBuilder.append(command.toString()).append(Resp.line);
        for (byte[] aByte : bytes) {
            stringBuilder.append(Resp.StringLength).append(aByte.length).append(Resp.line);
            stringBuilder.append(new String(aByte)).append(Resp.line);
        }
        return stringBuilder.toString();
    }


    public static void main(String[] args) {
        LubanJedis lubanJedis=new LubanJedis();
        System.out.println(lubanJedis.set("taibai2", "222222"));
        System.out.println(lubanJedis.set("taibai1", "333333"));
//        System.out.println(lubanJedis.get("taibai"));
        System.out.println(lubanJedis.incr("lock"));
    }

}
