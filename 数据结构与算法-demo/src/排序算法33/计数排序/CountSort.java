package 排序算法33.计数排序;

//基于桶排序
//以下计数排序只能排序正数，日后再改进
public class CountSort {
    public static void bucketSort(int[] arr){
        int max=0;
        int min=0;
        for(int i=1;i<arr.length;i++){
            max=arr[i]>arr[max]?i:max;
            min=arr[i]<arr[min]?i:min;
        }
        int[] bucket=new int[arr[max]-arr[min]+1];
        for(int i=0;i<arr.length;i++){
            bucket[arr[i]]++;
        }
        int j=0;
        for(int i=0;i<bucket.length;i++){
            while(bucket[i]!=0){
                arr[j++]=i;
                bucket[i]--;
            }
        }
    }
}
