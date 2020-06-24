package faced.general;

// 外观角色 Facade
public class Facade {
    private SubSystemA a = new SubSystemA();
    private SubSystemB b = new SubSystemB();
    private SubSystemC c = new SubSystemC();

    public static void main(String[] args) {
        SubSystemA a = new SubSystemA();
    }
    // 对外接口
    public void doA() {
        this.a.doA();
    }

    // 对外接口
    public void doB() {
        this.b.doB();
    }

    // 对外接口
    public void doC() {
        this.c.doC();
    }
}