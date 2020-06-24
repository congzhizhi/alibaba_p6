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
      /* gupao.IHelloService helloService=new gupao.HelloServiceImpl();

       gupao.RpcProxyServer proxyServer=new gupao.RpcProxyServer();
       proxyServer.publisher(helloService,8080);*/

        ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig.class);
        ((AnnotationConfigApplicationContext) context).start();
    }
}
