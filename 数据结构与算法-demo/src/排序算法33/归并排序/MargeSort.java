package 排序算法33.归并排序;

public class MargeSort {
    public static int size=0;
    public static void sort(int[] arr,int l,int r){
       // size=arr.length;
        if(l==r){
            return;
        }
        int mid=l+((r-l)>>1);
        sort(arr,l,mid);
        sort(arr,mid+1,r);
        marge(arr,l,mid,r);
    }
    public static int[] help=new int[9];
    public static void marge(int[] arr,int l,int mid,int r){
        int i=l;
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
        for(int j=l;j<=r;j++){
            arr[j]=help[j];
        }

    }
}
