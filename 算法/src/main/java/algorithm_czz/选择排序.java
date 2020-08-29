package algorithm_czz;

import java.util.Arrays;

public class 选择排序 {
    public static void main(String[] args) {

        int data[]={9,8,7,6,5,4,3,2,1};

        for (int i = 0; i < data.length; i++) {
            int miniIndex  = i;
            for (int j = i+1; j <data.length ; j++) {
                miniIndex = data[j]<data[i]?j:i;
                int tmp = data[i];
                data[i] = data[miniIndex];
                data[miniIndex] = tmp;
            }
        }

        System.out.println(Arrays.toString(data));
    }
}
