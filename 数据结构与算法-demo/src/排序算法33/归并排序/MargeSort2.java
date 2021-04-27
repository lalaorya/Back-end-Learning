package 排序算法33.归并排序;

public class MargeSort2 {
    public static void sort(int[] arr){
        int[] help=new int[arr.length];
        sort(arr,0,arr.length-1,help);
    }
    public static void sort(int[] arr,int l,int r,int[] help){
        if(l==r){
            return;
        }
        int mid=(l+r)/2;
        sort(arr,l,mid,help);
        sort(arr,mid+1,r,help);
        marge(arr,l,mid,r,help);
    }

    public static void marge(int[] arr,int l,int mid,int r,int[] help){
        int i=0;
        int p1=l;
        int p2=mid+1;
        while(p1<=mid&&p2<=r){
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

    }
}
