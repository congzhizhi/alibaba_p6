import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DaoThread {



    public static ExecutorService executor = Executors.newSingleThreadExecutor();
    public static void process(IAsynService iAsynService){
        executor.execute(()->{
            try {
                System.out.println("开始数据库操作----------");
                Thread.sleep(500);
                System.out.println("数据库操作结束----------");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            iAsynService.doProcess();
            MainProcess.finish(iAsynService::doFinish);

        });
    }
}
