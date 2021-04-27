package 排序算法33.插入排序;
import java.util.*;
public class Test {
    public static void main(String[] args){
        int[] arr={-3,4,9,6,1,-4,6,3,9,1,0,-9};
        InsertSort.sort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

}
