package 递归问题.二分法求最大值_1;

public class Test {
    public static void main(String[] args){
        int[] arr={2,4,1,6,9,4,1,2,-3,2,1,11,2,3};
        System.out.print(GetMax.getMax(arr,0,arr.length-1));
    }
}
