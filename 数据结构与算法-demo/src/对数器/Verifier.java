package 对数器;

import 排序算法33.归并排序.MargeSort2;

import java.util.Arrays;

/*
    对数器的概念和使用
0，有一个你想要测的方法a，
1，实现一个绝对正确但是复杂度不好的方法b，
2，实现一个随机样本产生器
3，实现比对的方法
4，把方法a和方法b比对很多次来验证方法a是否正确。
5，如果有一个样本使得比对出错，打印样本分析是哪个方法出
错
6，当样本数量很多时比对测试依然正确，可以确定方法a已经正确
 */
public class Verifier {

    //随机数组发生器
    public static int[] getRandomArray(int size,int value){
        int[] arr=new int[(int)(size*Math.random())+1];
        for(int i=0;i<arr.length;i++){
            arr[i]=(int)(value*Math.random()+1)-(int)(value*Math.random()+1);
        }
        return arr;
    }

    //绝对正确的方法(举个栗子
    public static void rightMethod(int[] arr){
        Arrays.sort(arr);
    }

    //要测的方法
    /*
        在这里添加
     */
    public static int[] sort(int[] arr){
        return sort(arr,0,arr.length-1);
    }
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

    public static boolean isEquals(int[] arr1,int[] arr2){
        if(arr1==null&&arr2!=null){
            return false;
        }
        if(arr1!=null&&arr2==null){
            return false;
        }
        if(arr1==null&&arr2==null){
            return true;
        }
        if(arr1.length!=arr2.length){
            return false;
        }
        for(int i=0;i<arr1.length;i++){
            if(arr1[i]!=arr2[i]){
                return false;
            }
        }
        return true;
    }

    //复制copy数组
    public static int[] copyArray(int[] arr){
        int[] arr2=new int[arr.length];
        for(int i=0;i<arr.length;i++){
            arr2[i]=arr[i];
        }
        return arr2;
    }

    public static void main(String[] args){
        int times=50000;
        boolean result=true;
        int[] arr4=null;
        for(int i=0;i<times;i++){
            int[] arr=getRandomArray(1000,50);
            int[] arr2=copyArray(arr);
            int[] arr3=copyArray(arr);
            //sort(arr);
            MargeSort2.sort(arr);
            rightMethod(arr2);
            if(!isEquals(arr,arr2)){
                result=false;
                break;
            }
            arr4=arr;
        }
        System.out.println(result?"nice!":"faking ");
        System.out.print(Arrays.toString(arr4));
    }
}
