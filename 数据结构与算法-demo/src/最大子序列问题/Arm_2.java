package 最大子序列问题;

public class Arm_2 {
    public int sum(int[] arr){
        int thissum=0;
        int maxsum=arr[0];
        for(int i=0;i<arr.length;i++){
            thissum=thissum+arr[i];
            if(thissum>maxsum){
                maxsum=thissum;
            }
            else if(thissum<0){
                thissum=0;
            }
        }
        return maxsum;
    }
}
