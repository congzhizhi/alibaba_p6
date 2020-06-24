package lambda;

@FunctionalInterface
public interface PersonCallback {
    void callback(String name);

    default void testDefault() {
        System.out.println("default");
    }

    static void testStatic() {
        System.out.println("static");
    }

}
