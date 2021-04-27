package 排序算法33.快速排序;
import java.util.*;
public class Test {
    public static void main(String[] args){
        int[] arr={-3,4,-9,6,-1,44,2,1,5,2,-4,6,3,9,1,0,-9};
        QuickSort.quickSort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }
}
