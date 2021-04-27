package 排序算法33.堆排序;

public class Sort {
    public static void heapSort(int[] arr){
        if(arr.length<2||arr==null){
            return;
        }
        //建立大根堆
        for(int i=0;i<arr.length;i++){
            heapInsert(i,arr);
        }
        //heapify
        int heapsize=arr.length;
        while(heapsize>0){
            swap(arr,0,--heapsize);
            heapify(0,heapsize,arr);
        }
    }

    public static void heapInsert(int index,int[] arr){
        while(arr[(index-1)/2]<arr[index]){
            swap(arr,index,(index-1)/2);
            index=(index-1)/2;
        }
    }

    public static void heapify(int index,int heapsize,int[] arr){
        while((index*2)+1<heapsize){
            int larget=index*2+2<heapsize&&arr[(index*2)+2]>arr[index*2+1]?index*2+2:index*2+1;
            larget=arr[larget]>arr[index]?larget:index;
            if(larget==index){
                break;
            }
            swap(arr,larget,index);
            index=larget;
        }
    }

    public static void swap(int[] arr,int i,int j){
        int temp=arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    }
}
