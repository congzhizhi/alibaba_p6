package faced.general;

// 子系统
public class SubSystemA {
    public SubSystemC doA() {
        System.out.println("doing A stuff");
        return new SubSystemC();
    }
}