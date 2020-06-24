package gupao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        /*gupao.RpcProxyClient rpcProxyClient=new gupao.RpcProxyClient();

        gupao.IHelloService iHelloService=rpcProxyClient.clientProxy
                (gupao.IHelloService.class,"localhost",8080);

        String result=iHelloService.sayHello("Mic");
        System.out.println(result);*/

        ApplicationContext context=new
                AnnotationConfigApplicationContext(SpringConfig.class);
        RpcProxyClient rpcProxyClient=context.getBean(RpcProxyClient.class);

        /*gupao.IHelloService iHelloService=rpcProxyClient.clientProxy
                (gupao.IHelloService.class,"localhost",8080);*/

        IPaymentService iPaymentService=rpcProxyClient.clientProxy(IPaymentService.class,
                "localhost",8080);

        iPaymentService.doPay();
    }
}
