package algorithm_czz;

import java.util.Arrays;

public class 冒泡排序 {

    public static void main(String[] args) {
        int data[]={9,8,7,6,5,4,3,2,1};

        for (int i = data.length-1; i > 0 ; i--) {
            for (int j = 0; j <i ; j++) {
                if ( data[j] >data[j+1]){
                    int tmp = data[j];
                    data[j] = data[j+1];
                    data[j+1] = tmp;
                }

            }
        }


        System.out.println(Arrays.toString(data));


    }
}
