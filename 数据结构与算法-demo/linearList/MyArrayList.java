package linearList;
/*
ADT原型：
InitList(L): 初始化操作，建立一个空的线性表L。
ListEmpty(L): 判断线性表是否为空表，若线性表为空，返回true，否则返回false。
ClearList(L): 将线性表清空
GetElem(L,i,e): 将线性表L中的第i个位置元素值返回给e。
LocateElem(L,e): 在线性表L中查找与给定值e相等的元素，如果查找成功，返回该元素在表中序号表示成功；否则，返回0表示失败。
ListInsert(L,i,e): 在线性表L中第i个位置插入新元素e。
ListDelete(L,i,e): 删除线性表L中第i个位置元素，并用e返回其值。
ListLength(L): 返回线性表L的元素个数。
 */

public class MyArrayList<AnyType> {
    public int AMOUNT=10;//初始长度
    public static int index;//表位置
    AnyType[] myList;
    public MyArrayList(){
        initList();
    }

    //初始化顺序表
    public void initList(){
        myList=(AnyType[])new Object[AMOUNT];
        index=0;
    }

    //判断顺序表是否为空
    public boolean listEmpty(){
        if(index==0){
            return true;
        }
        return true;
    }

    //清空顺序表
    public boolean clearList(){
        myList=null;
        index=0;
        return true;
    }

    //返回i位置的元素
    public AnyType get(int i){
        if(i<0||i>=index){
            throw  new ArrayIndexOutOfBoundsException();
        }
        return myList[i];
    }

    //在i位置加入元素，即插入操作，这里我没有用insert命名
    public void add(int i,AnyType a){
        if(i<0||i>index){
            throw new ArrayIndexOutOfBoundsException();
        }
        if (i==index){
            largeList();
        }
        for(int k=index;k>i;k--){
            myList[k]=myList[k-1];
        }
        myList[i]=a;
        index++;
    }

    //在结尾增添元素a
    public void add(AnyType a){
        add(index,a);
    }

    //为i位置元素重新赋值
    public AnyType set(AnyType a,int i){
        if(i<0||i>=index){
            throw new ArrayIndexOutOfBoundsException();
        }
        AnyType old=myList[i];
        myList[i]=a;
        return old;
    }

    //打印遍历顺序表
    public void print(){
        String s="[";
        for(int i=0;i<index;i++){
            s=s+myList[i];
            s=s+" ,";
        }
        System.out.println(s);
    }

    //查找a元素是否在表中，返回位置，没有返回0
    public int locateElem(AnyType a){
        for(int i=0;i<index;i++){
            if(a==myList[i]){
                return i+1;
            }
        }
        return 0;
    }

    //返回表长
    public int length(){
        return index;
    }

    //删除i位置元素
    public AnyType delete(int i){
        if(i<0||i>=index){
            throw new ArrayIndexOutOfBoundsException();
        }
        AnyType old=myList[i];
        for(int k=i;k<index;k++){
            myList[k]=myList[k+1];
        }
        index--;
        return old;
    }

    //扩大表的最大长度
    public void largeList(){
        AnyType[] newList=(AnyType[])new Object[2*length()+1];
        for(int i=0;i<index;i++){
            newList[i]=myList[i];
        }
        myList=newList;
    }

}
/*
            @黄浩杰牛逼！！！！
 */