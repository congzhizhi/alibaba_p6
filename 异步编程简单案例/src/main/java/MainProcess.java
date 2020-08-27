import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainProcess {



    public static ExecutorService executor = Executors.newSingleThreadExecutor();
    public static void process(){

        LoginService loginService = new LoginService();


        executor.execute(()->{
            System.out.println("主线程运行");
            loginService.login("congzhizhi","123456",userEntity -> {
                System.out.println("用户ID："+userEntity.getId());
                return null;
            });
            System.out.println("主线程结束");
        });
    }

    public static void finish(Runnable r){
        executor.submit(r);

    }

    public static void main(String[] args) {
        process();
    }
}
