package 排序算法33.插入排序;

public class InsertSort {
    public static int[] sort(int[] arr,int l,int r){
        for(int i=l;i<=r;i++){
            for(int j=i;j>l&&arr[j]<arr[j-1];j--){
                swap(arr,j,j-1);
            }
        }
        return arr;
    }

    public static void swap(int[] arr,int i,int j){
        int temp=arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    }
}
