package 递归问题.小和问题;
/*
    在一个数组中，每一个数左边比当前数小的数累加起来，叫做这个数组的小和。求一个数组 的小和。
例子： [1,3,4,2,5]
        1左边比1小的数，没有； 3左边比3小的数，1； 4左边比4小的数，1、3； 2左边比2小的数，
       1； 5左边比5小的数，1、3、4、2； 所以小和为1+1+3+1+1+3+4+2=16

 */
public class SmallSum {
    public static int sum(int[] arr){
        return sum(arr,0,arr.length-1);
    }
    public static int sum(int[] arr,int l,int r){
        if(l==r){
            return 0;
        }
        int mid=(l+r)/2;
        int lenthSum=sum(arr,l,mid);
        int rightSum=sum(arr,mid+1,r);
        int midSum=marge(arr,l,mid,r);
        return lenthSum+rightSum+midSum;
    }
    public static int marge(int[] arr,int l,int mid,int r){
        int sum=0;
        int[] help=new int[r-l+1];
        int i=0;
        int p1=l;
        int p2=mid+1;
        while(p1<=mid&&p2<=r){
            sum+=arr[p1]<arr[p2]?(arr[p1]*(r-p2+1)):0;
            help[i++]=arr[p1]<arr[p2]?arr[p1++]:arr[p2++];
        }
        while(p1<=mid){
            help[i++]=arr[p1++];
        }
        while(p2<=r){
            help[i++]=arr[p2++];
        }
        for(int j=0;j<i;j++){
            arr[j+l]=help[j];
        }
        return sum;

    }
}
