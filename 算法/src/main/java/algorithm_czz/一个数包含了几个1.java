package algorithm_czz;

public class 一个数包含了几个1 {


    public static void main(String[] args) {

        int count = 0;
        int N = 15;

        while (N!=0){
            int rightOne = N & (~N +1);
            count++;
            N^=rightOne;
        }
        System.out.println(count);



    }
}
