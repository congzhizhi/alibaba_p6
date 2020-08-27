public interface IAsynService {

    public void doProcess();

    default void doFinish(){

    }
}
