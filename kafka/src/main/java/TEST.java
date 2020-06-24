public class TEST {


    public static void main(String[] args) {

        compare com = a->{return a+2;};
        System.out.println(com.com(8));
    }

    interface compare{
        public int com(int a);
    }
}
