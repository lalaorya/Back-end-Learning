package src.最大子序列问题;

public class Test {
    public static void main(String[] args){
        最大子序列问题.Arm_1 arm_1=new 最大子序列问题.Arm_1();
        最大子序列问题.Arm_2 arm_2=new 最大子序列问题.Arm_2();

        System.out.println(arm_1.sum(arr,0,7));
        System.out.println(arr.length);
        System.out.println(arm_2.sum(arr));
    }

    @org.junit.Test
    public void test(){
        int[] arr={-6,-4,-9,-1,-1,-3,-7,-1};
        最大子序列问题.Arm_1.sum(arr,0,7);
    }


}
