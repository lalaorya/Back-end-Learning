package 排序算法33.选择排序;

public class SelectSort {
    public static int[] sort(int[] arr,int l,int r){
        for(int i=l;i<r;i++){
            int minIndex=i;
            for(int j=i;j<=r;j++){
                minIndex=arr[minIndex]<arr[j]?minIndex:j;
            }
            swap(arr,i,minIndex);
        }
        return arr;
    }
    public static void swap(int[] arr,int i,int j){
        int temp=arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    }
}
