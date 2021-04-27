package 递归问题.二分法求最大值_1;

public class GetMax {
    public static  int getMax(int[] arr,int l,int r){
        if(l==r){
            return arr[l];
        }
        int mid=(l+r)/2;
        int leftMax=getMax(arr,l,mid);
        int rightMax=getMax(arr,mid+1,r);
        return Math.max(leftMax,rightMax);
    }
}
