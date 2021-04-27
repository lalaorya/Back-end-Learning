package 排序算法33.快速排序;

public class QuickSort {
    public static void quickSort(int[] arr,int l,int r){
        if(l<r) {
            //加上下面这一行代码可以改进快排
            swap(arr, l + (int) (Math.random() * (r - l + 1)), r);
            int[] par = partition(arr, l, r);
            quickSort(arr,l,par[0]);
            quickSort(arr,par[1],r);
        }
    }

    //分区
    public static int[] partition(int[] arr,int l,int r){
        int index=l;
        int left=l-1;
        int more=r+1;
        int a=arr[r];
        while(index<more){
            if(arr[index]<a){
                swap(arr,index,++left);
                index++;
            }
            else if(arr[index]>a){
                swap(arr,index,--more);
            }
            else
                index++;

        }
        return new int[]{left,more};
    }

    public static void swap(int[] arr,int i,int j){
        int temp=arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    }
}
