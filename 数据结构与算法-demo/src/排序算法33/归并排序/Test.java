package 排序算法33.归并排序;
import java.util.*;
public class Test {
    public static void main(String[] args){
        int[] arr={-3,4,9,6,1,-4,6,3,9,8,66,5,4,0,-9,8,7};
        MargeSort2.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
