package 栈;

public class Test {
    public static void main(String[] args){
        StackImplementByArray<Integer> st=new StackImplementByArray<>(50);
        st.stack(10);
        st.push(3);
        st.push(4);
        st.push(5);
        //st.push(7);
        //st.push(2);
        System.out.println(st.pop()+","+st.pop()+","+st.pop());
        System.out.println(st.isEmpty());
        System.out.println(st.size());
    }
}
