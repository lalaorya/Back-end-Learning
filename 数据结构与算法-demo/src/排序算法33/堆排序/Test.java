package 排序算法33.堆排序;
import  java.util.*;
public class Test {
    public static void main(String[] args){
        int[] arr={-3,9,1,0,6,9,9,23,44,4,2,5,-1,4,-2,-2,-44,8,7};
        Sort.heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
