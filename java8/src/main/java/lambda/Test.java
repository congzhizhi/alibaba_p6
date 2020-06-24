package lambda;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Test {
    public static void main(String[] args) {
        Optional<String> test = Optional.ofNullable("abcD");

        //过滤值的长度小于3的Optional对象
        Optional<String> less3 = test.filter((value) -> value.length() < 3);
        //打印结果
        System.out.println(less3.orElse("不符合条件，不打印值！"));
    }
}
