package algorithm_czz;

public class 二分法 {

    public static void main(String[] args) {

//        System.out.println(exists(4));
        System.out.println(nearestIndex(999));


    }

    public static boolean  exists( int data){
        int[] sortedArr = new int[]{1,2,3,3,5,6,7,8,9,10,10,11,12,14,16,19,24,188,222,333,444,555};


        int size = sortedArr.length - 1;
        int L = 0;
        int M = 0;
        int R = size ;

        while(L < R){
            M = L + ((R - L) >> 1) ;
            if (data==sortedArr[M]){
                return true;
            }
            else if (data<sortedArr[M]){
                R = M - 1;
            }else if(data>sortedArr[M]){
                L = M + 1;
            }

        }

        return  sortedArr[L] == data;
    }



    //找到>=value的最左位置

    public static int  nearestIndex( int data){
        int[] sortedArr = new int[]{1,2,3,3,5,6,6,6,6,10,10,11,12,14,16,19,24,188,222,333,444,555};


        int size = sortedArr.length - 1;
        int L = 0;
        int M = 0;
        int R = size ;
        int index = -1; // 记录最左的对号
        while(L <= R){
            M = L + ((R - L) >> 1) ;
            if (data>sortedArr[M]){
                L = M+1;
            }
            else if (data<=sortedArr[M]){
                index = M;
                R = M - 1;
            }

        }

        return  index;
}

}
