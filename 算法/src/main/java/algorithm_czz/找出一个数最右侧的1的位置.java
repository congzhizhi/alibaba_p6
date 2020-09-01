package algorithm_czz;

public class 找出一个数最右侧的1的位置 {


    public static void main(String[] args) {
        int a = 24;
        int b = a & (~a +1);

        System.out.println(b);
    }
}
