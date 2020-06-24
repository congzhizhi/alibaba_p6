package lambda;

public class PersonCallBackImpl implements PersonCallback {
    @Override
    public void callback(String name) {
        System.out.println("源码之路:"+name);
    }
}
