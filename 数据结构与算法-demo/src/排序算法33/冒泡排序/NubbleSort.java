package 排序算法33.冒泡排序;

public class NubbleSort {
    public static int[] sort(int[] arr,int l,int r){
        for(int i=r;r>0;r--){
            for(int j=l;j<r;j++){
                if(arr[j]>arr[j+1]){
                    swap(arr,j,j+1);
                }
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
