package 排序算法33.堆排序;

public class HeapSort {
    public static void heapSort(int[] arr,int l,int r){
        if(arr==null||arr.length<2){
            return;
        }
        //建立大根堆
        for(int i=l;i<=r;i++){
            heapInsert(arr,i);
        }
        //heapify过程
        heapify(arr,l,r);
    }
    //建立大根堆(自下往上
    public static void heapInsert(int[] arr,int index){
        while(arr[index]>arr[(index-1)/2]){
            swap(arr,index,(index-1)/2);
        }
        index=(index-1)/2;
    }

    //heapify（自上往下
    public static void heapify(int[] arr,int l,int r){
        //int heapsize=r-l+1;
        //int index=l;
        while(r>l) {
            int index=l;
            swap(arr, l, r--);
            int left=(l*2)+1;
            while(left<=r){
                int largest = left + 1 <=r && arr[left + 1] > arr[left] ? left + 1 : left;
                largest=arr[index]<=arr[largest]?largest:index;
                if(largest==index){
                    break;
                }
                swap(arr,largest,index);
                left=(left*2)+1;
                index=largest;
            }
        }

    }
    public static void swap(int[] arr,int i,int j){
        int temp=arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    }
}
