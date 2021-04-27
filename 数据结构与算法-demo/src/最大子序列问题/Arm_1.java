package 最大子序列问题;
/*
分治思想，递归实现
时间复杂度为O(n*log n)
 */
public class Arm_1 {
    public static int sum(int[] arr,int left,int right){
        if(left==right){
            return arr[left];
        }
        int center=(left+right)/2;
        int leftsum=sum(arr,left,center);
        int rightsum=sum(arr,center+1,right);
        int centerleft=arr[center];
        int centerright=arr[center+1];
        int thissum=0;
        for(int i=center;i>=left;i--){
            thissum=thissum+arr[i];
            if(thissum>centerleft){
                centerleft=thissum;
            }
        }
        thissum=0;
        for(int i=center+1;i<=right;i++){
            thissum=thissum+arr[i];
            if(thissum>centerright){
                centerright=thissum;
            }
        }
        int j=0;
        return (j=(leftsum>rightsum?leftsum:rightsum))>(centerleft+centerright)?j:centerleft+centerright;

    }
}
