package 排序算法33.冒泡排序;

import 排序算法33.冒泡排序.NubbleSort;

import java.util.Arrays;

public class Test {
    public static void main(String[] args){
        int[] arr={-3,4,9,6,1,-4,6,3,9,1,0,-9};
        NubbleSort.sort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }
}
